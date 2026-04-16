package com.demo.core.common.mvi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

/**
 * Core MVI — Abstract base ViewModel that wires the standard MVI data-flow:
 *
 *   UI  ──intent──▶  onIntent()  ──reduce──▶  _state (StateFlow)
 *                                         ──effect──▶ _effect (Channel)
 *
 * Subclasses only need to:
 *  1. Provide an [initialState].
 *  2. Implement [handleIntent] to update state via [setState] or emit effects
 *     via [sendEffect].
 *
 * @param S Feature-specific state type implementing [UiState].
 * @param I Feature-specific intent type implementing [UiIntent].
 * @param E Feature-specific one-shot effect type implementing [UiEffect].
 */
abstract class BaseViewModel<S : UiState, I : UiIntent, E : UiEffect>(
    initialState: S
) : ViewModel() {

    // ── State ─────────────────────────────────────────────────────────────────
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    // ── Effects (one-shot, consumed exactly once) ─────────────────────────────
    private val _effect = Channel<E>(Channel.BUFFERED)
    val effect = _effect.receiveAsFlow()

    // ── Public entry-point ────────────────────────────────────────────────────

    /** The UI calls this for every user action. */
    fun onIntent(intent: I) = handleIntent(intent)

    // ── Protected API for subclasses ──────────────────────────────────────────

    /** Process the [intent] and mutate state / emit effects accordingly. */
    protected abstract fun handleIntent(intent: I)

    /** Atomically update the current state using a [reducer] lambda. */
    protected fun setState(reducer: S.() -> S) {
        _state.update { it.reducer() }
    }

    /** Emit a one-shot [effect] to the UI. Must be called from a coroutine. */
    protected suspend fun sendEffect(effect: E) {
        _effect.send(effect)
    }

    /** Convenience read of the current state snapshot. */
    protected val currentState: S
        get() = _state.value
}


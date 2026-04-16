package com.demo.core.common.mvi

/**
 * Core MVI — Marker interface for all UI state classes.
 *
 * Every feature's state data class should implement this interface so that
 * [BaseViewModel] can enforce a single type-parameter constraint.
 */
interface UiState


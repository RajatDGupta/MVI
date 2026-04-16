package com.demo.core.common.mvi

/**
 * Core MVI — Marker interface for all one-shot side-effect classes.
 *
 * Effects represent events that should be consumed exactly once (e.g. navigation,
 * showing a snackbar). They are delivered via a [kotlinx.coroutines.channels.Channel]
 * inside [BaseViewModel] to guarantee at-most-once delivery.
 */
interface UiEffect


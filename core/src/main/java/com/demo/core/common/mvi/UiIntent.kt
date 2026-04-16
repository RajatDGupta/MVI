package com.demo.core.common.mvi

/**
 * Core MVI — Marker interface for all user intent / action classes.
 *
 * Each feature defines a sealed class that implements [UiIntent].
 * The [BaseViewModel] consumes these through [BaseViewModel.onIntent].
 */
interface UiIntent


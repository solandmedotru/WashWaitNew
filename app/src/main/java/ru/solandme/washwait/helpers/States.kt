package ru.solandme.washwait.helpers

sealed class ForecastState {
    class DefaultState: ForecastState()
    class LoadingState: ForecastState()
    class LoadedState<T>(val date: List<T>): ForecastState()
    class EmptyForecastState: ForecastState()
    class ErrorState<T>(val message: T): ForecastState()
}
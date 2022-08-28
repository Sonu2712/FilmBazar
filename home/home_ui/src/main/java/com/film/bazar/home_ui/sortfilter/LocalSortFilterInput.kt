package com.film.bazar.home_ui.sortfilter

import com.film.bazar.home_domain.MovieFilterType
import com.film.bazar.home_domain.MovieSort

class LocalSortFilterInput(
    var valueFrom1: Double = 0.0,
    var valueTo1: Double = 0.0,
    var selectedSort: MovieFilterType = MovieFilterType.Nothing
) {

    fun saveAmountRange(valueFrom: Double, valueTo: Double) {
        valueFrom1 = valueFrom
        valueTo1 = valueTo
    }

    fun setLocalSort(sort: MovieFilterType) {
        selectedSort = sort
    }

    val sortFilterData = MovieSort(
        valueFrom = valueFrom1,
        valueTo = valueTo1,
        selectedSort = selectedSort
    )

    fun setData(movieSort: MovieSort) {
        setLocalSort(movieSort.selectedSort)
        saveAmountRange(movieSort.valueFrom, movieSort.valueTo)
    }
}
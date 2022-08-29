package com.film.bazar.home_ui.sortfilter

import com.film.bazar.home_domain.MovieSortFilter

class LocalSortFilterInput(
    var valueFrom1: Double = 0.0,
    var valueTo1: Double = 0.0,
    var selectedSort: MovieSortFilter = MovieSortFilter.ADDED_NOTHING
) {

    fun saveAmountRange(valueFrom: Double, valueTo: Double) {
        valueFrom1 = valueFrom
        valueTo1 = valueTo
    }

    fun setLocalSort(sort: MovieSortFilter) {
        selectedSort = sort
    }
}
package com.film.bazar.home_domain

class MovieSortFilter internal constructor(
    val id: Int,
    val label: String
) {
    override fun toString(): String {
        return label
    }

    fun isRecentlyAdded(): Boolean {
        return this == RECENTLY_ADDED
    }

    fun isPopularityHighToLow(): Boolean = this == POPULARITY_HIGH_LOW
    fun isPopularityLowToHigh(): Boolean = this == POPULARITY_LOW_HIGH
    fun isDaysLeftHighToLow(): Boolean = this == DAYS_LEFT_HIGH_LOW
    fun isDaysLeftLowToHigh(): Boolean = this == DAYS_LEFT_LOW_HIGH
    fun isNothing(): Boolean = this == ADDED_NOTHING

    companion object {
        val ADDED_NOTHING = MovieSortFilter(id = 0, label = "Nothing")
        val RECENTLY_ADDED = MovieSortFilter(id = 1, label = "Recently added")
        val POPULARITY_HIGH_LOW = MovieSortFilter(id = 2, label = "Popularity - high to low")
        val POPULARITY_LOW_HIGH = MovieSortFilter(id = 3, label = "Popularity - low to high")
        val DAYS_LEFT_HIGH_LOW =
            MovieSortFilter(id = 4, label = "Day’s left for investment - high to low")
        val DAYS_LEFT_LOW_HIGH =
            MovieSortFilter(id = 5, label = "Day’s left for investment - low to high")

        val allOngoingProjectFilterOptions = listOf(
            RECENTLY_ADDED,
            POPULARITY_HIGH_LOW,
            POPULARITY_LOW_HIGH,
            DAYS_LEFT_HIGH_LOW,
            DAYS_LEFT_LOW_HIGH
        )

        val allPastProjectFilterOptions = listOf(
            RECENTLY_ADDED,
            POPULARITY_HIGH_LOW,
            POPULARITY_LOW_HIGH
        )

        fun getInstance(id: Int): MovieSortFilter {
            return when (id) {
                RECENTLY_ADDED.id -> RECENTLY_ADDED
                POPULARITY_HIGH_LOW.id -> POPULARITY_HIGH_LOW
                POPULARITY_LOW_HIGH.id -> POPULARITY_LOW_HIGH
                DAYS_LEFT_HIGH_LOW.id -> DAYS_LEFT_HIGH_LOW
                DAYS_LEFT_LOW_HIGH.id -> DAYS_LEFT_LOW_HIGH
                else -> ADDED_NOTHING
            }
        }
    }
}
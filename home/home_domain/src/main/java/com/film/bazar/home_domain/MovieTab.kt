package com.film.bazar.home_domain

class MovieTab internal constructor(
    val label: String
) {
    override fun toString(): String {
        return label
    }

    fun isOnGoingProject(): Boolean {
        return this == ONGOING_PROJECT
    }

    fun isPastProject(): Boolean {
        return this == PAST_PROJECT
    }

    companion object {
        val ONGOING_PROJECT = MovieTab("Ongoing Projects")
        val PAST_PROJECT = MovieTab("Past Projects")

        val allProjectTab = listOf(ONGOING_PROJECT, PAST_PROJECT)

        fun getInstance(label: String): MovieTab {
            return when {
                label.equals(ONGOING_PROJECT.label, true) -> ONGOING_PROJECT
                label.equals(PAST_PROJECT.label, true) -> ONGOING_PROJECT
                else -> ONGOING_PROJECT
            }
        }
    }
}
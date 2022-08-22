package com.film.bazar.home_domain

data class MovieInfo(
    val imgUrl : String,
    val title: String,
    val directorName : String,
    val noOfDaysLeft : Int,
    val noOfPeopleInvt : Double,
    val perFoundProgress : Int,
    val targetAmount : Double,
    val targetGoal : Double,
    val orderAction : String
)
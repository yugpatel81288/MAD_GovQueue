package com.example.madgovqueue

data class Report(
    val id: Int,
    val officeName: String,
    val crowdStatus: String,
    val peopleWaiting: String,
    val reportTime: String
)
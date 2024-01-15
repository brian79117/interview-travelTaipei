package com.example.travel_taipei.api.datamodel

import java.io.Serializable

data class File(
    val src: String,
    val subject: String,
    val ext: String
): Serializable

package com.example.travel_taipei.api.datamodel

import java.io.Serializable

data class News(
    val id: Int,
    val title: String,
    val description: String,
    val begin : String,
    val end : String,
    val posted: String,
    val modified: String,
    val url: String,
    val files: ArrayList<File>,
    val links: ArrayList<Link>
): Serializable

package com.example.travel_taipei.api.datamodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

data class Attractions(
    val id: Int,
    val name: String,
    val name_zh: String,
    val open_status: Int,
    val introduction: String,
    val open_time: String,
    val zipcode: String,
    val distric: String,
    val address: String,
    val tel: String,
    val fax: String,
    val email: String,
    val months: String,
    val nlat: String,
    val elong: String,
    val official_site: String,
    val facebook: String,
    val ticket: String,
    val remind: String,
    val staytime: String,
    val modified: String,
    val url: String,
    val category: ArrayList<Category>,
    val target: ArrayList<Target>,
    val service: ArrayList<Service>,
    val friendly: ArrayList<Friendly>,
    val images: ArrayList<File>,
    val files: ArrayList<File>,
    val links: ArrayList<Link>
): Serializable

data class Category(
    val id: Int,
    val name: String
) : Serializable

data class Target(
    val id: Int,
    val name: String
): Serializable

data class Service(
    val id: Int,
    val name: String
): Serializable

data class Friendly(
    val id: Int,
    val name: String
): Serializable

@Parcelize
data class AttractionsDetail(
    val name: String,
    val openTime: String,
    val address: String,
    val phone: String,
    val url: String,
    val content: String,
    val images: ArrayList<String>
) : Parcelable
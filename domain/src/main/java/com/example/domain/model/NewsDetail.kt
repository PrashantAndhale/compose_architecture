package com.example.domain.model

import com.google.gson.annotations.SerializedName


data class NewsDetails(
    @SerializedName("place_of_publication") var placeOfPublication: String? = null,
    @SerializedName("lccn") var lccn: String? = null,
    @SerializedName("start_year") var startYear: String? = null,
    @SerializedName("place") var place: ArrayList<String> = arrayListOf(),
    @SerializedName("name") var name: String? = null,
    @SerializedName("publisher") var publisher: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("end_year") var endYear: String? = null,
    @SerializedName("issues") var issues: List<Issues> = arrayListOf(),
    @SerializedName("subject") var subject: ArrayList<String> = arrayListOf()
)

data class Issues(

    @SerializedName("url") var url: String? = null,
    @SerializedName("date_issued") var dateIssued: String? = null

)
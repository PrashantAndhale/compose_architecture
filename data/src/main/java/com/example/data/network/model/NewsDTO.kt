package com.example.data.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsPaperDTO(
    @SerializedName("newspapers") var newspapers: ArrayList<NewspapersDTO> = arrayListOf()
)

data class NewspapersDTO(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("lccn") var lccn: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("title") var title: String? = null
)
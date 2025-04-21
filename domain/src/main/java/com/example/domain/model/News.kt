package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

data class NewsPaper(
    @SerializedName("newspapers") var newspapers: ArrayList<Newspapers> = arrayListOf()
)

@Serializable
@Entity
data class Newspapers(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @SerializedName("lccn") var lccn: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("state") var state: String? = null,
    @SerializedName("title") var title: String? = null
)
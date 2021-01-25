package com.kugemi.poemwriter.model.server_dto

import com.google.gson.annotations.SerializedName

data class ServerRhyme (
    @SerializedName("word") val word : String,
    @SerializedName("freq") val freq : Int,
    @SerializedName("score") val score : Int,
    @SerializedName("flags") val flags : String,
    @SerializedName("syllables") val syllables : Int
)
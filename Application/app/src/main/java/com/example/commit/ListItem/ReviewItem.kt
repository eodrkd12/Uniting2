package com.example.commit.ListItem

import java.io.Serializable

data class ReviewItem (
    val nickname:String?,
    val date: String?,
    val starpoint:String?,
    val content: String?
) : Serializable
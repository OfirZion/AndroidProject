package com.example.androidproject.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseAuth
import kotlinx.serialization.Serializable

@Entity(tableName = "posts")
@Serializable
class LibraryPost(
    @PrimaryKey
    override var id: String = "",
    var owner: String = FirebaseAuth.getInstance().uid ?: "",
    var title: String = "",
    var content: String = "",
    var image: String = "",
    var version: Double = 0.0,
    var postedAt: Long = System.currentTimeMillis(),
    override var updatedAt: Long = System.currentTimeMillis(),
    var links: MutableList<String> = mutableListOf(),
    var tags: MutableList<String> = mutableListOf(),
    var ratings: MutableList<Float> = mutableListOf(),
    var deleted: Boolean = false
) : BaseDocument()
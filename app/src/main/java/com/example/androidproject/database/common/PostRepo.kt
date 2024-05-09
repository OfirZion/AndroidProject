package com.example.androidproject.database.common

import android.net.Uri
import com.example.androidproject.models.LibraryPost
import kotlin.jvm.Throws

interface PostRepo {
    @Throws(Exception::class)
    suspend fun createPost(post: LibraryPost, imageUri: Uri? = null): LibraryPost
    @Throws(Exception::class)
    suspend fun updatePost(post: LibraryPost, imageUri: Uri? = null): LibraryPost
    @Throws(Exception::class)
    suspend fun deletePost(post: LibraryPost): LibraryPost
}
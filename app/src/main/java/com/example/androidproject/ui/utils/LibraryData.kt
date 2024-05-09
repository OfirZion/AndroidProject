package com.example.androidproject.ui.utils

import com.example.androidproject.models.LibraryPost
import com.example.androidproject.models.OtherUser
import com.example.androidproject.models.User


open class LibraryData(
    var allPosts: List<LibraryPost>? = null,
    val allUsers: List<OtherUser>? = null,
    val currentUser: User? = null,
) {
    fun isAllAvailable(): Boolean {
        return allPosts != null && allUsers != null && currentUser != null
    }

    open fun toPopulated(): LibraryDataPopulated? {
        return if (isAllAvailable()) {
            LibraryDataPopulated(allPosts!!, allUsers!!, currentUser!!)
        } else null
    }

    fun copy(
        allPosts: List<LibraryPost>? = null,
        allUsers: List<OtherUser>? = null,
        currentUser: User? = null,
    ): LibraryData {
        return LibraryData(
            allPosts ?: this.allPosts,
            allUsers ?: this.allUsers,
            currentUser ?: this.currentUser
        )
    }
}

class SearchLibraryData(data: LibraryData) : LibraryData(
    data.allPosts,
    data.allUsers,
    data.currentUser,
) {
    val allPostsCopy = data.allPosts?.toList() ?: listOf()
}


data class LibraryDataPopulated(
    val allPosts: List<LibraryPost> = listOf(),
    val allUsers: List<OtherUser> = listOf(),
    val currentUser: User,
)
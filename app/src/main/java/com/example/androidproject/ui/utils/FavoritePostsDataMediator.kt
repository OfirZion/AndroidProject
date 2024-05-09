package com.example.androidproject.ui.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.androidproject.models.LibraryPost
import com.example.androidproject.models.OtherUser
import com.example.androidproject.models.User
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FavoritePostsDataMediator(
    private val allPosts: LiveData<List<LibraryPost>?>,
    private val allUsers: LiveData<List<OtherUser>?>,
    private val currentUser: LiveData<User?>,
) : ReadOnlyProperty<Any?, MediatorLiveData<LibraryData>> {


    private val _libraryData = LibraryData()
    private val libraryData by lazy {
        MediatorLiveData<LibraryData>().apply {
            value = LibraryData()
            addSource(allPosts) { posts ->
                val currentData = value ?: _libraryData
                posts?.let {
                    value = currentData.copy(allPosts = posts)
                }
            }

            addSource(allUsers) { users ->
                val currentData = value ?: _libraryData
                users?.let {
                    value = currentData.copy(allUsers = users)
                }
            }

            addSource(currentUser) { user ->
                val currentData = value ?: _libraryData
                val posts = allPosts.value ?: currentData.allPosts ?: listOf()
                user ?: return@addSource
                val likedMapping = HashSet(user.favoritePosts)
                user.let {
                    value = currentData.copy(
                        currentUser = user,
                        allPosts = posts.filter { post ->
                            likedMapping.contains(post.id)
                        })
                }
            }
        }
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): MediatorLiveData<LibraryData> {
        return libraryData
    }

}
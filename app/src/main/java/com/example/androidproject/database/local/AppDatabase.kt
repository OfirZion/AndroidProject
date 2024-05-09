package com.example.androidproject.database.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.androidproject.models.LibraryPost
import com.example.androidproject.models.OtherUser
import com.example.androidproject.models.User

@Database(entities = [LibraryPost::class, User::class, OtherUser::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentUserDao() : CurrentUserDao
    abstract fun otherUserDao() : OtherUserDao
    abstract fun libraryPostsDao() : LibraryPostDao


    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context) : AppDatabase {
            if(instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "jsLibsDb")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

}
package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun repost(id: Long)
    fun removeById(id: Long)
    fun save(post:Post)


}

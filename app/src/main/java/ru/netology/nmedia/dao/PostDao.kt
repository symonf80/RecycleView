package ru.netology.nmedia.dao

import ru.netology.nmedia.repository.Post

interface PostDao {
    fun getAll():List<Post>
    fun save(post: Post):Post
    fun likeById(id:Long)
    fun removeById(id:Long)
    fun repost(id: Long)
}
package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.repository.Post

@Entity
class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val repost: Int,
    val views: Int,
    val likedByMe: Boolean,
    val video: String?
) {
    fun toDto() = Post(id, author, content, published, likes, repost, views, likedByMe, video)

    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            author = post.author,
            content = post.content,
            published = post.published,
            likes = post.likes,
            repost = post.repost,
            views = post.views,
            likedByMe = post.likedByMe,
            video = post.video
        )
    }
}
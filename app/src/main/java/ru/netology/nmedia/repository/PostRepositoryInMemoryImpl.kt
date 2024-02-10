package ru.netology.nmedia.repository

import android.nfc.Tag
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId: Long = 1
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет,это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.Затем появились курсы по дизайну,разработке,аналитике и управлению.Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим,что в каждом уже есть сила,которая заставляет хотеть больше,целиться выше,бежать быстрее.Наша миссия - помочь встать на путь роста и начать цепочку перемен - http//netolo.gy/fyb",
            published = "21 мая в 18:36",
            likes = 999,
            repost = 555,
            views = 2100,
            likedByMe = false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет,это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.Затем появились курсы по дизайну,разработке,аналитике и управлению.Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим,что в каждом уже есть сила,которая заставляет хотеть больше,целиться выше,бежать быстрее.Наша миссия - помочь встать на путь роста и начать цепочку перемен - http//netolo.gy/fyb",
            published = "22 мая в 18:36",
            likes = 888,
            repost = 7000,
            views = 2100,
            likedByMe = false,
            video = null
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет,это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.Затем появились курсы по дизайну,разработке,аналитике и управлению.Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим,что в каждом уже есть сила,которая заставляет хотеть больше,целиться выше,бежать быстрее.Наша миссия - помочь встать на путь роста и начать цепочку перемен - http//netolo.gy/fyb",
            published = "23 мая в 18:36",
            likes = 777,
            repost = 15998,
            views = 2100,
            likedByMe = false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий",
            content = "Привет,это новая Нетология!Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу.Затем появились курсы по дизайну,разработке,аналитике и управлению.Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим,что в каждом уже есть сила,которая заставляет хотеть больше,целиться выше,бежать быстрее.Наша миссия - помочь встать на путь роста и начать цепочку перемен - http//netolo.gy/fyb",
            published = "24 мая в 18:36",
            likes = 666,
            repost = 7000,
            views = 2100,
            likedByMe = false,
            video = null
        ),
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun repost(id: Long) {
        posts = posts.map {
            if (it.id != id) it else
                it.copy(repost = it.repost + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(id = nextId++, published = "Now", author = "Netology")
            ) + posts

        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(content = post.content)
            }
        }

        data.value = posts
    }


}
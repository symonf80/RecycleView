package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.repository.Post
import ru.netology.nmedia.service.Service

class PostsAdapter(

    private val onLike: (Post) -> Unit,
    private val onShare: (Post) -> Unit
) : RecyclerView.Adapter<PostViewHolder>() {

    var list: List<Post> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context))
        return PostViewHolder(view, onLike, onShare)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}
class PostViewHolder(

    private val binding: CardPostBinding,
    private val onLike: (Post) -> Unit,
    private val onShare: (Post) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val service = Service()
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            tvlikes.text = service.counter(post.likes)
            tvShare.text = service.counter(post.repost)
            tvViews.text = service.counter(post.views)
            likes.setImageResource(
                if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.likes
            )

            likes.setOnClickListener {
                onLike(post)

            }
            share.setOnClickListener {
                onShare(post)
            }
        }
    }
}
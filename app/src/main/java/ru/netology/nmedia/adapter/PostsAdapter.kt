package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.repository.Post
import ru.netology.nmedia.service.Service
import ru.netology.nmedia.view.MainActivity

interface OnInteractionListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
    fun onPlay(post:Post)
}


class PostsAdapter(

    private val onInteractionListener: OnInteractionListener


) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener

) : RecyclerView.ViewHolder(binding.root) {

    private val service = Service()
    fun bind(post: Post) {
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            likes.text = service.counter(post.likes)
            share.text = service.counter(post.repost)
            tvViews.text = service.counter(post.views)
            likes.isChecked = post.likedByMe

            likes.setOnClickListener {
                onInteractionListener.onLike(post)

            }
            share.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            play.setOnClickListener {
                onInteractionListener.onPlay(post)
            }
            video.setOnClickListener {
                onInteractionListener.onPlay(post)
            }
            video.viewTreeObserver.apply {
                if (post.video?.isNotEmpty() == true) {
                    play.visibility = View.VISIBLE
                    video.visibility = View.VISIBLE
                }else{
                    play.visibility = View.GONE
                    video.visibility = View.GONE
                }
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }
        }
    }
}

object PostDiffCallback : DiffUtil.ItemCallback<Post>() {

    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem
}
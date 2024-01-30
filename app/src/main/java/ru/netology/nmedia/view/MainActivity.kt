package ru.netology.nmedia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.service.Service
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.repository.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.AndroidUtils.focusAndShowKeyboard
import ru.netology.nmedia.viewModel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.repost(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onEdit(post: Post) {
                binding.group.visibility = View.VISIBLE
                viewModel.edit(post)
            }
        })
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.currentList.size < posts.size && adapter.currentList.size > 0
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }
        viewModel.edited.observe(this) { post ->
            if (post.id != 0L) {
                binding.edit.setText(post.content)
                binding.edit.focusAndShowKeyboard()
            }

        }
        binding.save.setOnClickListener {
            binding.group.visibility = View.GONE
            val text = binding.edit.text.toString().trim()
            if (text.isEmpty()) {
                Toast.makeText(this, R.string.empty_post, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            viewModel.changeContentAndSave(text)

            binding.edit.setText("")
            binding.edit.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }
        binding.close.setOnClickListener {
            binding.group.visibility = View.GONE
            binding.edit.setText("")
            binding.edit.clearFocus()
            AndroidUtils.hideKeyboard(it)
        }

    }
}
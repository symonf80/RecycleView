package ru.netology.nmedia.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentOnePostBinding

import ru.netology.nmedia.service.Service
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.view.NewPostFragment.Companion.textArg
import ru.netology.nmedia.viewModel.PostViewModel


class OnePostFragment : Fragment() {
    private val service = Service()

    companion object {
        var Bundle.idArg: String? by StringArg

    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOnePostBinding.inflate(
            inflater, container, false
        )


        val postId = arguments?.idArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id != postId } ?: return@observe
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                likes.text = service.counter(post.likes)
                share.text = service.counter(post.repost)
                tvViews.text = service.counter(post.views)
                likes.isChecked = post.likedByMe
                if (post.video?.isNotEmpty() == true) {
                    play.visibility = View.VISIBLE
                    video.visibility = View.VISIBLE
                } else {
                    play.visibility = View.GONE
                    video.visibility = View.GONE
                }

                likes.setOnClickListener {
                    viewModel.likeById(post.id)

                }
                share.setOnClickListener {
                    viewModel.repost(post.id)
                }
                play.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    val viewIntent = Intent.createChooser(intent, R.string.external_link.toString())
                    startActivity(viewIntent)
                }
                video.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.video))
                    val viewIntent = Intent.createChooser(intent, R.string.external_link.toString())
                    startActivity(viewIntent)
                }


                menu.setOnClickListener {
                    PopupMenu(it.context, it).apply {
                        inflate(R.menu.options_post)
                        setOnMenuItemClickListener { item ->
                            when (item.itemId) {
                                R.id.remove -> {
                                    viewModel.removeById(post.id)
                                    findNavController().navigateUp()
                                    true
                                }

                                R.id.edit -> {
                                    viewModel.edit(post)

                                    findNavController().navigate(
                                        R.id.action_onePostFragment_to_newPostFragment,
                                        Bundle().apply {
                                            textArg = content.text.toString()
                                        })
                                    true
                                }

                                else -> false
                            }
                        }
                    }.show()
                }
            }
        }

        return binding.root
    }

}
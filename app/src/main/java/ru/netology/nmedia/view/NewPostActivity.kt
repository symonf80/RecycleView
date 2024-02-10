package ru.netology.nmedia.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.R.id.cancel
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.repository.Post
import ru.netology.nmedia.view.NewPostActivity.Companion.NEW_POST_CONTENT_KEY
import ru.netology.nmedia.viewModel.PostViewModel
import ru.netology.nmedia.viewModel.empty

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editText = intent.getStringExtra(CONTENT_KEY)
        binding.edit.setText(editText)
        binding.edit.requestFocus()

        binding.ok.setOnClickListener {
            val text = binding.edit.text.toString()
            if (text.isNotBlank()) {
                setResult(RESULT_OK, Intent().apply { putExtra(NEW_POST_CONTENT_KEY, text) })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }


        binding.bottomAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                cancel -> {
                    val text = binding.edit.text.toString()
                    if (text.isNotBlank()) {
                        setResult(
                            RESULT_OK,
                            Intent().apply { putExtra(NEW_POST_CONTENT_KEY, text) })
                    }
                    finish()
                    true
                }

                else -> {
                    setResult(RESULT_CANCELED)
                    false
                }

            }
        }

    }


    companion object {
        const val CONTENT_KEY = "content"
        const val NEW_POST_CONTENT_KEY = "newPostContent"

    }
}

object NewPostContract : ActivityResultContract<String?, String?>() {
    override fun createIntent(context: Context, input: String?) =
        Intent(context, NewPostActivity::class.java).putExtra(NewPostActivity.CONTENT_KEY, input)


    override fun parseResult(resultCode: Int, intent: Intent?) =
        intent?.getStringExtra(NEW_POST_CONTENT_KEY)
}




package vancore.all_in_one.content.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.all_in_one.databinding.ActivityContentBinding
import dagger.hilt.android.AndroidEntryPoint
import vancore.all_in_one.content.data.ContentItem
import vancore.all_in_one.content.data.ContentType
import vancore.all_in_one.five_skills.ui.BrowserActivity


@AndroidEntryPoint
class ContentActivity : AppCompatActivity(), ContentClickListener {

    private lateinit var binding: ActivityContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContentBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onStart() {
        super.onStart()

        val contentList = listOf(
                ContentItem("My Game", "Funny, little Game!", ContentType.MyGame),
                ContentItem("5 Skills", "Share your skills with an awesome community", ContentType.FiveSkills),
                ContentItem("Title 3", "Description 3", ContentType.Type3),
                ContentItem("Title 4", "Description 4", ContentType.Type4)
        )

        //I use viewbinding here, for other classes or maybe in the future: ToDo use databinding
        with(binding) {

            rvContent.adapter = ContentAdapter(contentList, this@ContentActivity)

        }
    }

    override fun onContentClicked(contentType: ContentType?) {
        when (contentType) {
            ContentType.MyGame -> {
                val intent = Intent(this, BrowserActivity::class.java).apply {
                    putExtra("name", "value")
                }
                this.startActivity(intent)
            }
            ContentType.FiveSkills -> {
                val intent = Intent(this, BrowserActivity::class.java).apply {
                    putExtra("name", "value")
                }
                this.startActivity(intent)
            }
            ContentType.Type3 -> TODO()
            ContentType.Type4 -> TODO()
            null -> TODO()
        }

    }
}
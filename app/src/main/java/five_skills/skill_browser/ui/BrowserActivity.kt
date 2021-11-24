package five_skills.skill_browser.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import shared.models.SkillItem
import javax.inject.Inject

@AndroidEntryPoint
class BrowserActivity : AppCompatActivity(), BrowserItemClickListener {

    private val skillList = listOf(
        SkillItem("Skill Title 1", "Skill Description 1", 1),
        SkillItem("Skill Title 2", "Skill Description 1", 1),
        SkillItem("Skill Title 3", "Skill Description 1", 1),
        SkillItem("Skill Title 4", "Skill Description 1", 1),
        SkillItem("Skill Title 5", "Skill Description 1", 1),
        SkillItem("Skill Title 6", "Skill Description 1", 1),
        SkillItem("Skill Title 7", "Skill Description 1", 1),
        SkillItem("Skill Title 8", "Skill Description 1", 1),
        SkillItem("Skill Title 9", "Skill Description 1", 1),
        SkillItem("Skill Title 10", "Skill Description 1", 1),
        SkillItem("Skill Title 11", "Skill Description 1", 1),
        SkillItem("Skill Title 12", "Skill Description 1", 1),
        SkillItem("Skill Title 13", "Skill Description 1", 1),
        SkillItem("Skill Title 14", "Skill Description 1", 1),
        SkillItem("Skill Title 15", "Skill Description 1", 1),
        SkillItem("Skill Title 16", "Skill Description 1", 1),
        SkillItem("Skill Title 17", "Skill Description 1", 1),
    )

    @Inject
    lateinit var browserViewModel: BrowserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Text("Hello")
            //SkillList(skillList)
            //SkillEntry(skillList[0])
        }
    }

    override fun onStart() {
        super.onStart()
        browserViewModel.doSomething()
    }

    override fun onBrowserItemClicked(item: SkillItem) {
        // do some stuff
    }

}

@Composable
fun SkillList(list: List<SkillItem>) {
    //RecyclerView()
//    LazyColumn {
//        items(list) {
//
//        }
//    }
}

@Composable
fun SkillEntry(skillItem: SkillItem) {
    Text(text = skillItem.skillTitle)
    Text(text = skillItem.skillDescription)
}

@Preview
@Composable
fun SkillListPreview() {
    SkillList(listOf(
        SkillItem("Skill Title 1", "Skill Description 1", 1),
        SkillItem("Skill Title 2", "Skill Description 1", 1),
        SkillItem("Skill Title 3", "Skill Description 1", 1)
    ))
}

@Preview
@Composable
fun SkillEntryPreview() {
    SkillEntry(SkillItem("Skill Title 1", "Skill Description 1", 1))
}
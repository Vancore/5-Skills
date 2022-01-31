package vancore.five_skills.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import vancore.five_skills.FiveSkillsViewModel
import vancore.five_skills.R
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.ui.theme.Rose600

@Composable
fun SearchResultScreen(
    viewModel: FiveSkillsViewModel,
    subcategoryName: String,
    subcategoryId: String,
    categoryId: String,
    subcategoryImageUrl: String,
    categoryImageUrl: String,
    onSingleItemClicked: (SkillItem) -> Unit
) {
    val searchSkillsState by viewModel.searchSkillsState.collectAsState()
    val skillList = searchSkillsState.currentList

    Scaffold(topBar = { TopBarSearchResult(subcategoryTitle = subcategoryName) }) {
        LazyColumn(contentPadding = PaddingValues(8.dp)) {
            items(items = searchSkillsState.currentList) { skillItem ->
                SearchResultItem(skillItem = skillItem, onItemClicked = { onSingleItemClicked(it) })
            }
        }
    }
}

@Composable
fun TopBarSearchResult(
    subcategoryTitle: String = ""
) {
    Box(
        Modifier
            .background(color = MaterialTheme.colors.primary)
            .height(200.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 32.dp, end = 32.dp, start = 32.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(4.dp, MaterialTheme.colors.secondary, CircleShape)
            )
            FiveSkillsSubTitleText(
                titleText = subcategoryTitle,
                alignment = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(4.dp, MaterialTheme.colors.secondary, CircleShape)
            )
        }
    }
}

@Composable
fun SearchResultItem(
    skillItem: SkillItem,
    onItemClicked: (SkillItem) -> Unit
) {
    Box(modifier = Modifier
        .height(200.dp)
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(10.dp))
        .clickable { onItemClicked(skillItem) }
    ) { // rounded corners
        Row {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = MaterialTheme.colors.primary)
                    .fillMaxSize()
                    .padding(8.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    FiveSkillsSubTitleText(
                        titleText = skillItem.title,
                        modifier = Modifier.weight(1f)
                    )
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Self-Rating",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                            Text(
                                text = skillItem.selfRating.toString(),
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                        Spacer(modifier = Modifier.padding(8.dp))
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "User-Rating",
                                fontSize = 14.sp,
                                color = MaterialTheme.colors.onBackground
                            )
                            Text(
                                text = "-",
                                color = MaterialTheme.colors.onBackground
                            )
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Rose600)
                    .fillMaxSize()
            ) {
                GlideImage(
                    imageModel = skillItem.backgroundImageUrl,
                    modifier = Modifier.fillMaxSize(),
                    loading = {
                        ConstraintLayout(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            val indicator = createRef()
                            CircularProgressIndicator(
                                modifier = Modifier.constrainAs(indicator) {
                                    top.linkTo(parent.top)
                                    bottom.linkTo(parent.bottom)
                                    start.linkTo(parent.start)
                                    end.linkTo(parent.end)
                                }
                            )
                        }
                    },
                    previewPlaceholder = R.drawable.detail_placeholder,
                    // shows an error text message when request failed.
                    failure = {
                        Text(text = "image request failed.")
                    })
            }
        }
    }
}

@Preview(
    name = "TopBar SearchResult - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "TopBar SearchResult - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TopBarSearchResultPreview() {
    FiveSkillsTheme {
        Scaffold(topBar = { TopBarSearchResult(subcategoryTitle = "Very long Category Title") }) {
            LazyColumn(contentPadding = PaddingValues(8.dp)) {
                items(
                    items = arrayListOf(
                        SkillItem(
                            title = "Skill Item Title",
                            selfRating = 3.0
                        )
                    )
                ) { skillItem ->
                    SearchResultItem(skillItem = skillItem, onItemClicked = { })
                }
            }
        }
    }
}
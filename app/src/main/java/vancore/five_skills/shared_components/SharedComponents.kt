package vancore.five_skills.shared_components

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.glide.GlideImage
import vancore.five_skills.FiveSkillsScreen
import vancore.five_skills.R
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.ui.theme.FiveSkillsTheme
import vancore.five_skills.ui.theme.Rose600
import vancore.five_skills.ui.theme.TransparentBlack50

@Composable
fun CategoryListEntry(
    descriptionText: String,
    iconURL: String,
    itemClicked: () -> Unit = {}
) {

    // can be used for expanding
    // -> e.g. in Text, maxLines = if (isClicked ...)
    var isClicked by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                isClicked = !isClicked
                itemClicked()
            }
            .padding(vertical = 20.dp, horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = descriptionText,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.subtitle1,
        )

        Spacer(modifier = Modifier.width(8.dp))

        FiveSkillsGlideIcon(iconURL = iconURL)
    }
}

@Composable
fun FiveSkillsGlideIcon(
    iconURL: String,
    iconSize: Dp = 40.dp,
    iconBorderColor: Color = MaterialTheme.colors.onPrimary,
    borderSize: Dp = 4.dp
) {
    Box(
        modifier = Modifier
            .size(iconSize + borderSize * 2)
            .clip(CircleShape)
            .border(BorderStroke(borderSize, iconBorderColor), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        GlideImage(
            imageModel = iconURL,
            modifier = Modifier
                .size(iconSize)
                .clip(CircleShape),
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
            previewPlaceholder = R.drawable.barbell,
            // shows an error text message when request failed.
            failure = {
                Text(text = "image request failed.")
            })
    }
}

@Composable
fun FiveSkillsGlideImage(imageUrl: String, modifier: Modifier) {
    GlideImage(
        imageModel = imageUrl,
        modifier = modifier,
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

@Composable
fun TopBar(
    //allScreens: List<FiveSkillsScreen>,
    onOptionSelected: (FiveSkillsScreen) -> Unit = {}
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 48.dp)
        ) {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp),
                color = MaterialTheme.colors.onPrimary
            )

            IconButton(
                onClick = { onOptionSelected(FiveSkillsScreen.Profile) },
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(

                    imageVector = Icons.Default.PersonOutline,
                    contentDescription = FiveSkillsScreen.Profile.name,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
            IconButton(
                onClick = { onOptionSelected(FiveSkillsScreen.FiveSkillsSettings) },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = FiveSkillsScreen.FiveSkillsSettings.name,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
        FiveSkillsDivider()
    }
}

@Composable
fun TopBarWithImage(
    backgroundImageUrl: String = "",
    profileImageUrl: String = "",
    titleText: String = ""
) {
    Box(
        Modifier
            .background(color = Rose600)
            .height(240.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {

        FiveSkillsGlideImage(imageUrl = backgroundImageUrl, modifier = Modifier.fillMaxSize())
        Box(
            modifier = Modifier
                .height(48.dp)
                .background(color = TransparentBlack50)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FiveSkillsSubTitleText(titleText = titleText, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.padding(16.dp))
                FiveSkillsGlideIcon(
                    iconURL = profileImageUrl,
                    iconSize = 32.dp,
                    iconBorderColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun TopBarSubCategory(
    categoryTitle: String = ""
) {
    Box(
        Modifier
            .background(color = Color.Transparent)
            .height(200.dp)
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .border(4.dp, MaterialTheme.colors.secondary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            FiveSkillsSubTitleText(
                titleText = categoryTitle,
                alignment = TextAlign.Center,
                modifier = Modifier.padding(12.dp)
            )
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
                    .border(4.dp, MaterialTheme.colors.secondary, CircleShape),
            )
            FiveSkillsSubTitleText(
                titleText = subcategoryTitle,
                alignment = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(4.dp, MaterialTheme.colors.secondary, CircleShape),
            )
        }
    }
}

@Composable
fun FiveSkillsDivider() {
    Divider(
        color = MaterialTheme.colors.onPrimary,
        thickness = 0.5.dp,
        modifier = Modifier
            .alpha(0.7f)
            .padding(vertical = 16.dp)
    )
}

@ExperimentalComposeUiApi
@Composable
fun FiveSkillsTextInput(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit = {},
    onImeAction: () -> Unit = {},
    keyboardOption: KeyboardOptions,
    placeHolder: String,
    label: String = "",
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        placeholder = { Text(text = placeHolder) },
        keyboardOptions = keyboardOption,
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}

@Composable
fun FiveSkillsErrorText(
    errorText: String,
    paddingVertical: Dp = 8.dp,
    paddingHorizontal: Dp = 24.dp
) {
    Text(
        text = errorText,
        color = MaterialTheme.colors.error,
        modifier = Modifier.padding(vertical = paddingVertical, horizontal = paddingHorizontal)
    )
}

@ExperimentalComposeUiApi
@Composable
fun FiveSkillsRatingBar(
    modifier: Modifier = Modifier,
    rating: Int,
    onRatingSelected: (String) -> Unit
) {
    var ratingState by remember {
        mutableStateOf(rating)
    }

    var selected by remember {
        mutableStateOf(false)
    }
    val size by animateDpAsState(
        targetValue = if (selected) 64.dp else 48.dp,
        spring(Spring.DampingRatioMediumBouncy)
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "star",
                modifier = modifier
                    .width(size)
                    .height(size)
                    .pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> {
                                selected = true
                                ratingState = i
                                onRatingSelected(i.toString())
                            }
                            MotionEvent.ACTION_UP -> {
                                selected = false
                            }
                        }
                        true
                    },
                tint = if (i <= ratingState) MaterialTheme.colors.secondary else MaterialTheme.colors.onPrimary
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun FiveSkillsRatingBarNoSelection(
    modifierStars: Modifier = Modifier,
    modifierView: Modifier = Modifier,
    rating: Int
) {
    val size = 24.dp

    Row(
        modifier = modifierView.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "star",
                modifier = modifierStars
                    .width(size)
                    .height(size),
                tint = if (i <= rating) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
fun SkillListItem(
    skillItem: SkillItem,
    isSelected: Boolean,
    onSkillClicked: (SkillItem) -> Unit
) {
    // Accompanist FlowRow maybe, to not need to set 52 DP fixed
    Row(
        modifier = Modifier
            .height(52.dp)
            .fillMaxWidth()
            .clickable { onSkillClicked(skillItem) },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(2.dp)
                .background(
                    color = if (isSelected) {
                        MaterialTheme.colors.secondary
                    } else {
                        MaterialTheme.colors.onPrimary
                    }
                )
                .alpha(0.7f)
                .padding(start = 16.dp, end = 8.dp)
                .clip(RoundedCornerShape(2.dp))
        )
        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Text(
            text = skillItem.title, maxLines = 1, textAlign = TextAlign.Start,
            style = MaterialTheme.typography.body1,
            fontSize = 16.sp,
            color = MaterialTheme.colors.onBackground,
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Self-Rating",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = skillItem.selfRating.toString(),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
        }

        Spacer(modifier = Modifier.padding(horizontal = 8.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 8.dp, end = 16.dp)
        ) {
            Text(
                text = "Ranking",
                style = MaterialTheme.typography.body1,
                fontSize = 12.sp,
                color = MaterialTheme.colors.onBackground
            )
            Text(
                text = skillItem.ranking.toString(),
                style = MaterialTheme.typography.body1,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground
            )
        }
    }
}

@Composable
fun AddSkillButton(
    onAddSkillClicked: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(color = MaterialTheme.colors.primary)
            .height(52.dp)
            .width(52.dp)
            .clickable { onAddSkillClicked() }
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Skill",
            modifier = Modifier.align(
                Alignment.Center
            ),
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Composable
fun FiveSkillsTitleText(titleText: String) {
    val styledText = buildAnnotatedString {
        append(titleText)
        append(AnnotatedString(text = ".", spanStyle = SpanStyle(MaterialTheme.colors.secondary)))
    }
    Text(
        text = styledText,
        modifier = Modifier
            .padding(top = 40.dp, bottom = 24.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h1,
        fontSize = 40.sp,
        color = MaterialTheme.colors.onPrimary
    )
}

@Composable
fun FiveSkillsFullStopText(titleText: String, modifier: Modifier) {
    val styledText = buildAnnotatedString {
        append(titleText)
        append(AnnotatedString(text = ".", spanStyle = SpanStyle(MaterialTheme.colors.secondary)))
    }
    Text(
        text = styledText,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.body1,
        fontSize = 16.sp,
        color = MaterialTheme.colors.onBackground,
        modifier = modifier
    )
}

@Composable
fun FiveSkillsBodyText(
    titleText: String,
    alignment: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colors.onBackground,
    modifier: Modifier
) {
    Text(
        text = titleText,
        textAlign = alignment,
        style = MaterialTheme.typography.body1,
        fontSize = 16.sp,
        color = color,
        modifier = modifier
    )
}

@Composable
fun FiveSkillsSubTitleText(
    modifier: Modifier = Modifier,
    titleText: String,
    alignment: TextAlign = TextAlign.Start,
    color: Color = MaterialTheme.colors.onBackground
) {
    Text(
        text = titleText,
        textAlign = alignment,
        style = MaterialTheme.typography.body1,
        fontSize = 20.sp,
        color = color,
        modifier = modifier
    )
}

@Composable
fun FiveSkillsBodyCenter(titleText: String, color: Color = MaterialTheme.colors.onBackground) {
    FiveSkillsBodyText(
        titleText = titleText,
        alignment = TextAlign.Center,
        modifier = Modifier.padding(),
        color = color
    )
}

//region: Preview

@Preview(
    name = "Title Text - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Title Text - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun TitleTextPreview() {
    FiveSkillsTheme {
        FiveSkillsTitleText("Five Skills")
    }
}

@Preview(
    name = "Skill Item - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Skill Item - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun SkillListItemPreview() {
    FiveSkillsTheme {
        val skillItem = SkillItem(
            userId = "something",
            title = "Skill Item Title",
            selfRating = 4.0,
            ranking = 1
        )
        SkillListItem(skillItem = skillItem, isSelected = false) {}
    }
}


@Preview(
    name = "Top Bar - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Top Bar - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun FiveSkillsTopBar() {
    FiveSkillsTheme {
        TopBar()
    }
}

@Preview(
    name = "Error Text - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
)
@Preview(
    name = "Error Text - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true
)
@Composable
fun FiveSkillsErrorText() {
    FiveSkillsTheme {
        FiveSkillsErrorText(errorText = "This is some error text.")
    }
}

@Preview(
    name = "Add Skill Button - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "AddSkillButton - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun AddSkillButtonPreview() {
    FiveSkillsTheme {
        AddSkillButton()
    }
}

@Preview(
    name = "TopBar with image - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "TopBar with image - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TopBarWithImagePreview() {
    FiveSkillsTheme {
        TopBarWithImage(titleText = "Hello Moto")
    }
}

@Preview(
    name = "TopBar Subcategory - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "TopBar Subcategory - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun TopBarSubcategoryPreview() {
    FiveSkillsTheme {
        Scaffold(topBar = { TopBarSubCategory(categoryTitle = "Very long Category Title") }) {}
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
        Scaffold(topBar = { TopBarSearchResult(subcategoryTitle = "Very long Category Title") }) {}
    }
}

@ExperimentalComposeUiApi
@Preview(
    name = "RatingBar - Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "RatingBar - Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Composable
fun RatingBarNoSelectionPreview() {
    FiveSkillsTheme {
        FiveSkillsRatingBarNoSelection(rating = 4)
    }
}

//endregion
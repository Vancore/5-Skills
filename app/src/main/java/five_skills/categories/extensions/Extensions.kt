package five_skills.categories.extensions

import com.google.firebase.firestore.QuerySnapshot
import five_skills.shared.models.CategoryItem

fun QuerySnapshot.getCategoryItems(): List<CategoryItem> {
  val listOfSkills = mutableListOf<CategoryItem>()
  for (category in this.documents) {
    listOfSkills.add(
      CategoryItem(
        id = category["id"].toString().toInt(),
        name = category["name"].toString()
      )
    )
  }
  return listOfSkills
}
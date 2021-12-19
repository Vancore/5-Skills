package vancore.five_skills

import com.google.firebase.firestore.QuerySnapshot
import vancore.five_skills.components.CategoryItem
import vancore.five_skills.components.SubCategoryItem

fun QuerySnapshot.getCategoryItems(): List<CategoryItem> {
    val listOfSkills = mutableListOf<CategoryItem>()
    for (category in this.documents) {
        listOfSkills.add(
            CategoryItem(
                id = category["id"].toString().toInt(),
                firebaseId = category.id,
                name = category["name"].toString()
            )
        )
    }
    return listOfSkills
}

fun QuerySnapshot.getSubCategoryItems(): List<SubCategoryItem> {
    val listOfSubCategories = mutableListOf<SubCategoryItem>()
    for (category in this.documents) {
        listOfSubCategories.add(
            SubCategoryItem(
                id = category["id"].toString().toInt(),
                firebaseId = category.id,
                name = category["name"].toString()
            )
        )
    }
    return listOfSubCategories
}
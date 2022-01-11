package vancore.five_skills

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem

fun QuerySnapshot.getCategoryItems(): List<CategoryItem> {
    val listOfSkills = mutableListOf<CategoryItem>()
    for (category in this.documents) {
        listOfSkills.add(
            CategoryItem(
                id = category["id"].toString().toInt(),
                firebaseId = category.id,
                name = category["name"].toString(),
                iconURL = category["iconURL"].toString()
            )
        )
    }
    return listOfSkills
}

fun QuerySnapshot.getSubCategoryItems(): List<SubcategoryItem> {
    val listOfSubCategories = mutableListOf<SubcategoryItem>()
    for (subCategory in this.documents) {
        listOfSubCategories.add(
            SubcategoryItem(
                id = subCategory["id"].toString().toInt(),
                firebaseId = subCategory.id,
                name = subCategory["name"].toString(),
                iconURL = subCategory["iconURL"].toString(),
                backgroundURL = subCategory["backgroundURL"].toString()
            )
        )
    }
    return listOfSubCategories
}

fun QuerySnapshot.getSkillListFromUser(): ArrayList<SkillItem> {
    val listOfSkills = arrayListOf<SkillItem>()
    for (skill in this.documents) {
        val toString = if (skill["selfRating"] != null) skill["selfRating"].toString().toDouble() else 0.0
        listOfSkills.add(
            SkillItem(
                userId = (this.query as CollectionReference).parent?.id ?: "userID not found",
                skillId = skill.id,
                title = skill["title"].toString(),
                description = skill["description"].toString(),
                selfRating = toString,
                ranking = skill["ranking"].toString().toInt()
            )
        )
    }
    return listOfSkills
}
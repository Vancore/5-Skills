package vancore.five_skills

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
                name = category["name"].toString()
            )
        )
    }
    return listOfSkills
}

fun QuerySnapshot.getSubCategoryItems(): List<SubcategoryItem> {
    val listOfSubCategories = mutableListOf<SubcategoryItem>()
    for (category in this.documents) {
        listOfSubCategories.add(
            SubcategoryItem(
                id = category["id"].toString().toInt(),
                firebaseId = category.id,
                name = category["name"].toString()
            )
        )
    }
    return listOfSubCategories
}

fun DocumentSnapshot.getSkillListFromUser(): ArrayList<SkillItem> {
    val listOfSkills = arrayListOf<SkillItem>()
    val fireBaseDocumentList = (this.data?.get("5 Skills") as Iterable<*>).toList()
    // ToDo: Adjust Firebase objects to SkillItem
    for (skill in fireBaseDocumentList) {
        listOfSkills.add(
            SkillItem(
                userId = this.id,
                title = (skill as HashMap<*, *>)["skillTitle"].toString(),
                selfRating = skill["skillLevel"].toString().toDouble(),
                ranking = skill["skillLevel"].toString().toInt()
            )
        )
    }
    return listOfSkills
}
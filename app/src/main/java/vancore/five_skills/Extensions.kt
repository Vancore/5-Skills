package vancore.five_skills

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import vancore.five_skills.data.FiveSkillsRemoteRepository.Companion.CATEGORY_ID
import vancore.five_skills.data.FiveSkillsRemoteRepository.Companion.SUBCATEGORY_ID
import vancore.five_skills.data.FiveSkillsRemoteRepository.Companion.USER_ID
import vancore.five_skills.data.models.CategoryItem
import vancore.five_skills.data.models.SkillItem
import vancore.five_skills.data.models.SubcategoryItem
import vancore.five_skills.data.models.User

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

fun QuerySnapshot.getSkills(): ArrayList<SkillItem> {
    val listOfSkills = arrayListOf<SkillItem>()
    for (skill in this.documents) {
        listOfSkills.add(
            SkillItem(
                userId = skill[USER_ID].toString(),
                skillId = skill.id,
                title = skill["title"].toString(),
                description = skill["description"].toString(),
                selfRating = skill["selfRating"]?.toString()?.toDouble() ?: 0.0,
                ranking = skill["ranking"].toString().toInt(),
                subcategoryId = skill[SUBCATEGORY_ID].toString(),
                categoryId = skill[CATEGORY_ID].toString(),
            )
        )
    }
    return listOfSkills
}

fun QuerySnapshot.getUserList(): ArrayList<User> {
    val listOfSkills = arrayListOf<User>()
    for (user in this.documents) {
        listOfSkills.add(
            User(
                firebaseId = user.id,
                email = user["email"].toString()
            )
        )
    }
    return listOfSkills
}
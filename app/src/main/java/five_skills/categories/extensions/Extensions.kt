package five_skills.five_skills.browse_categories.extensions

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import five_skills.categories.data.CategoriesRemoteRepository
import five_skills.shared.models.CategoryItem

fun DocumentSnapshot.getCategoryItems(): List<CategoryItem> {
  val listOfSkills = mutableListOf<CategoryItem>()
  val fiveSkillsList = (this.data?.get(CategoriesRemoteRepository.CATEGORIES) as Iterable<*>).toList()
  for (skill in fiveSkillsList) {
    listOfSkills.add(
      CategoryItem(
        id = (skill as HashMap<*, *>)["skillTitle"].toString(),
        name = skill["skillDescription"].toString()
      )
    )
  }
  return listOfSkills
}

fun QuerySnapshot.getCategoryItems(): List<CategoryItem> {
  val listOfSkills = mutableListOf<CategoryItem>()
//  val fiveSkillsList = (this.documents?.get(BrowserRemoteRepository.CATEGORIES) as Iterable<*>).toList()
//  for (skill in fiveSkillsList) {
//    listOfSkills.add(
//      CategoryItem(
//        id = (skill as HashMap<*, *>)["skillTitle"].toString(),
//        name = skill["skillDescription"].toString()
//      )
//    )
//  }
  return listOfSkills
}
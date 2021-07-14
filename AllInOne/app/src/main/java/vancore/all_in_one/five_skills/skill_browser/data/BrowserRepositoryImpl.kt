package vancore.all_in_one.five_skills.skill_browser.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BrowserRepositoryImpl @Inject constructor(
    private val remoteRepository: BrowserRemoteRepository,
    private val localRepository: BrowserLocalRepository
) : BrowserRepository {
    override fun browserMethod() {
    }
}
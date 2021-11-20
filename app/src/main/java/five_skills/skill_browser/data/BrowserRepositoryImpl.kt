package five_skills.skill_browser.data

import five_skills.skill_browser.data.BrowserLocalRepository
import five_skills.skill_browser.data.BrowserRemoteRepository
import five_skills.skill_browser.data.BrowserRepository
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
package moura.silva.com.data.store

import io.reactivex.Completable
import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsCache
import moura.silva.com.data.repository.ProjectsDataStore
import javax.inject.Inject

open class ProjectsCacheDataStore @Inject constructor(private val projectsCache: ProjectsCache) :
        ProjectsDataStore{

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getProjects()
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {

        //add this lather
        //   .andThen(projectsCache.setLastCacheTime(System.currentTimeMillis()))
        return  projectsCache.saveProjects(projects)

    }

    override fun clearProjects(): Completable {
        return projectsCache.clearProjects()
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsCache.getBookmarkedProjects()
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return projectsCache.setProjectAsBookmarked(projectId)
    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return  projectsCache.setProjectAsNotBookmarked(projectId)
    }

}
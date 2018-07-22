package moura.silva.com.domain.repository

import io.reactivex.Completable
import io.reactivex.Observable
import moura.silva.com.domain.model.Project

interface ProjectRepository {
    fun getProjects() : Observable<List<Project>>

    fun bookmarkProject(projectId : String) : Completable

    fun unbookedProject(projectId: String) : Completable

    fun getBookmarkedProjects(): Observable<List<Project>>
}
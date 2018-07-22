package moura.silva.com.data.repository

import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity

interface ProjectsRemote{

    fun getProjects(): Observable<List<ProjectEntity>>

}
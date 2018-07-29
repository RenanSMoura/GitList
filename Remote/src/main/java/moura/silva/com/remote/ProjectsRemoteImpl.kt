package moura.silva.com.remote

import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsRemote
import moura.silva.com.remote.mapper.ProjectsResponseModelMapper
import moura.silva.com.remote.service.GitHubTrendingService
import javax.inject.Inject

class ProjectsRemoteImpl @Inject constructor(
        private val gitHubTrendingService: GitHubTrendingService,
        private val mapper: ProjectsResponseModelMapper) : ProjectsRemote {

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return gitHubTrendingService.searchRepository(
                "language:kotlin", "start", "desc")
                .map {
                    it.items.map {
                        mapper.mapFromModel(it)
                    }
                }
    }
}
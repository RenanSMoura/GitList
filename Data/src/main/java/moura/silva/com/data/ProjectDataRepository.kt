package moura.silva.com.data

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import moura.silva.com.data.mapper.ProjectMapper
import moura.silva.com.data.repository.ProjectsCache
import moura.silva.com.data.store.ProjectsDataStoreFactory
import moura.silva.com.domain.model.Project
import moura.silva.com.domain.repository.ProjectRepository
import javax.inject.Inject

class ProjectDataRepository @Inject constructor(
        private val mapper : ProjectMapper,
        private val cache : ProjectsCache,
        private val factory : ProjectsDataStoreFactory)
    : ProjectRepository {
    override fun getProjects(): Observable<List<Project>> {
        return Observable.zip(cache.areProjectsCached().toObservable(),
                cache.isProjectsCacheExpired().toObservable(),
                BiFunction<Boolean,Boolean,Pair<Boolean,Boolean>> { areCached, isExpired ->
                    Pair(areCached,isExpired)
                })
                .flatMap {
                    factory.getDataStore(it.first,it.second).getProjects()
                }.flatMap { projects ->
                    factory.getCacheDataStore()
                            .saveProjects(projects)
                            .andThen(Observable.just(projects))
                }.map { it.map { mapper.mapFromEntity(it) } }

    }

    override fun bookmarkProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsBookmarked(projectId)
    }

    override fun unbookedProject(projectId: String): Completable {
        return factory.getCacheDataStore().setProjectAsNotBookmarked(projectId)
    }

    override fun getBookmarkedProjects(): Observable<List<Project>> {
        return factory.getCacheDataStore().getBookmarkedProjects().map { it.map { mapper.mapFromEntity(it) } }
    }
}
package moura.silva.com.cache

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import moura.silva.com.cache.db.ProjectsDatabase
import moura.silva.com.cache.mapper.CachedProjectMapper
import moura.silva.com.cache.model.Config
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsCache
import javax.inject.Inject

class ProjectCacheImpl @Inject constructor(
        private val projectsDatabase: ProjectsDatabase,
        private val mapper: CachedProjectMapper
) : ProjectsCache {
    override fun clearProjects(): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().deleteProjects()
            Completable.complete()
        }
    }

    override fun saveProjects(projects: List<ProjectEntity>): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().insertProjects(
                    projects.map { mapper.mapToCached(it) }
            )
            Completable.complete()

        }
    }

    override fun getProjects(): Observable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects().toObservable()
                .map {
                    it.map {
                        mapper.mapFromCached(it)
                    }
                }
    }

    override fun getBookmarkedProjects(): Observable<List<ProjectEntity>> {
        return projectsDatabase.cachedProjectsDao().getBookmarkedProjects().toObservable()
                .map {
                    it.map { mapper.mapFromCached(it) }
                }
    }

    override fun setProjectAsBookmarked(projectId: String): Completable {
        return Completable.defer {
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(true,projectId)
            Completable.complete()
        }

    }

    override fun setProjectAsNotBookmarked(projectId: String): Completable {
        return Completable.defer{
            projectsDatabase.cachedProjectsDao().setBookmarkedStatus(false,projectId)
            Completable.complete()
        }
    }

    override fun areProjectsCached(): Single<Boolean> {
        return  projectsDatabase.cachedProjectsDao().getProjects().isEmpty.map {
                !it
        }
    }

    override fun setLastCacheTime(lastCache: Long): Completable {
        return Completable.defer {
            projectsDatabase.configDao().insertConfig(Config("1",lastCache))
            Completable.complete()
        }
    }

    override fun isProjectsCacheExpired(): Single<Boolean> {
        val currentTime =System.currentTimeMillis()
        val expirationTime = (60 * 60 * 1000).toLong()
        return projectsDatabase.configDao().getConfig().single(Config("1",lastCacheTime =  0))
                .map { currentTime - it.lastCacheTime > expirationTime }
    }
}
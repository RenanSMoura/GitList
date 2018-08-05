package moura.silva.com.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import moura.silva.com.cache.db.ProjectsDatabase
import moura.silva.com.cache.mapper.CachedProjectMapper
import moura.silva.com.cache.test.moura.silva.com.cache.test.factory.ProjectDataFactory
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment


@RunWith(RobolectricTestRunner::class)
class ProjectCacheImplTest{

    @get:Rule var instantTaskExecutorRule  =  InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java
    ).allowMainThreadQueries().build()

    private val entityMapper = CachedProjectMapper()

    private val cache = ProjectCacheImpl(database,entityMapper)


    @Test
    fun clearTablesComplete(){
        cache.clearProjects().test().assertComplete()
    }

    @Test
    fun saveProjectsComplete(){
        val listOfProjectEntities = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(listOfProjectEntities).test().assertComplete()
    }

    @Test
    fun getProjectsReturnData(){
        val listOfProjectEntities = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(listOfProjectEntities).test()

        val testObserver = cache.getProjects().test()
        testObserver.assertValue(listOfProjectEntities)
    }

    @Test
    fun getBookmarkedProjectsReturnData(){
        val bookmarkProject = ProjectDataFactory.makeBookmarkedProjectEntity()
        val listOfProjectEntities = listOf(ProjectDataFactory.makeProjectEntity(),bookmarkProject)
        cache.saveProjects(listOfProjectEntities).test()

        val testObserver = cache.getBookmarkedProjects().test()
        testObserver.assertValue(listOf(bookmarkProject))
    }

    @Test
    fun setBookmarkedProjectsCompletes(){
        val list = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(list).test()
        cache.setProjectAsBookmarked(list[0].id).test().assertComplete()
    }

    @Test
    fun setNotBookmarkedProjectsCompletes(){
        val list = listOf(ProjectDataFactory.makeBookmarkedProjectEntity())
        cache.saveProjects(list).test()
        cache.setProjectAsNotBookmarked(list[0].id).test().assertComplete()
    }

    @Test
    fun areProjectsCacheReturnData(){
        val list = listOf(ProjectDataFactory.makeProjectEntity())
        cache.saveProjects(list).test()
        cache.areProjectsCached().test().assertValue(true)
    }

    @Test
    fun setLastCachedTimeComplete(){
        cache.setLastCacheTime(1000L).test().assertComplete()
    }

    @Test
    fun isProjectCacheExpiredReturnsExpired(){
        cache.isProjectsCacheExpired().test().assertValue(true)
    }

    @Test
    fun isNotProjectCacheExpiredReturnsExpired(){
        cache.setLastCacheTime(System.currentTimeMillis()).test()
        cache.isProjectsCacheExpired().test().assertValue(false)
    }
}
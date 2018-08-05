package moura.silva.com.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import moura.silva.com.cache.db.ProjectsDatabase
import moura.silva.com.cache.test.moura.silva.com.cache.test.factory.ProjectDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class CachedProjectsDaoTest{


    @Rule
    @JvmField
    var instantTaskExecutorRule  =  InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java
    ).allowMainThreadQueries().build()



    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun getProjectsReturnData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().getProjects().test().assertValue(listOf(project))
    }

    @Test
    fun deleteProjectsClearsData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().deleteProjects()
        database.cachedProjectsDao().getProjects().test().assertValue(emptyList())
    }

    @Test
    fun getBookmarkedProjectsReturnData(){
        val project = ProjectDataFactory.makeCachedProject()
        val bookmarkedProject = ProjectDataFactory.makeBookmarkedCachedProject()

        database.cachedProjectsDao().insertProjects(listOf(project,bookmarkedProject))
        database.cachedProjectsDao().getBookmarkedProjects().test().assertValue(listOf(bookmarkedProject))
    }

    @Test
    fun saveBookmarkedProjectsReturnData(){
        val project = ProjectDataFactory.makeCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkedStatus(true,projectId = project.id)
        project.isBookmarked = true
        database.cachedProjectsDao().getBookmarkedProjects().test().assertValue(listOf(project))
    }

    @Test
    fun saveNotBookmarkedProjectsReturnData(){
        val project = ProjectDataFactory.makeBookmarkedCachedProject()
        database.cachedProjectsDao().insertProjects(listOf(project))
        database.cachedProjectsDao().setBookmarkedStatus(false,projectId = project.id)
        project.isBookmarked = false
        database.cachedProjectsDao().getBookmarkedProjects().test().assertValue(emptyList())
    }
}
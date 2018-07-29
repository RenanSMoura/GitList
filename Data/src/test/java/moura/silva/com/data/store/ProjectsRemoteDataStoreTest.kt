package moura.silva.com.data.store

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsRemote
import moura.silva.com.data.test.factory.DataFactory
import moura.silva.com.data.test.factory.ProjectFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsRemoteDataStoreTest{

    private val remote = mock< ProjectsRemote>()
    private val store = ProjectsRemoteDataStore(remote)

    @Test
    fun getProjectsComplete(){
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test().assertComplete()
    }


    @Test
    fun getProjectsReturnData(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubGetProjects(Observable.just(data))
        val storeComplete = store.getProjects().test()
        storeComplete.assertValue(data)
    }

    @Test
    fun getProjectsCallsRemote(){
        stubGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects()
        verify(remote).getProjects()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun saveProjectsThrowsException(){
        store.saveProjects(listOf()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun clearProjectsThrowsException(){
        store.clearProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun getBookmarkedProjectsThrowsException(){
        store.getBookmarkedProjects().test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setBookmarkedProjectsThrowsException(){
        store.setProjectAsBookmarked(DataFactory.randomString()).test()
    }

    @Test(expected = UnsupportedOperationException::class)
    fun setAsNotBookmarkedProjectsThrowsException(){
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test()
    }


    private fun stubGetProjects(observable: Observable<List<ProjectEntity>>){
        whenever(store.getProjects()).thenReturn(observable)
    }

}
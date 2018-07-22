package moura.silva.com.data.store

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsCache
import moura.silva.com.data.test.factory.ProjectFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ProjectsCacheDataStoreTest{

    private val cache = mock<ProjectsCache>()
    private val store = ProjectsCacheDataStore(cache)

    @Test
    fun getProjectsComplete(){
         stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
         val testObserver = store.getProjects().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnData(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsCacheGetProjects(Observable.just(data))
        val testObserver = store.getProjects().test()
        testObserver.assertValue(data)
    }

    @Test
    fun getProjectsCallCacheSource(){
        stubProjectsCacheGetProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getProjects().test()
        verify(cache).getProjects()
    }

    @Test
    fun saveProjectsCompletes(){
        stubProjectsSave(Completable.complete())
        val testCompletable = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testCompletable.assertComplete()
    }

    @Test
    fun saveProjectsCallCache(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsSave(Completable.complete())
        store.saveProjects(data).test()
        verify(cache).saveProjects(data)
    }

    private fun stubProjectsCacheGetProjects(observable : Observable<List<ProjectEntity>> ){
        whenever(cache.getProjects()).thenReturn(observable)
    }

    private fun stubProjectsSave(complete : Completable){
        whenever(cache.saveProjects(any())).thenReturn(complete)
    }

}
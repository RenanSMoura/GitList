package moura.silva.com.data.store

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.repository.ProjectsCache
import moura.silva.com.data.test.factory.DataFactory
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
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        val testCompletable = store.saveProjects(listOf(ProjectFactory.makeProjectEntity())).test()
        testCompletable.assertComplete()
    }

    @Test
    fun saveProjectsCallCache(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubProjectsSave(Completable.complete())
        stubProjectsCacheSetLastCacheTime(Completable.complete())
        store.saveProjects(data).test()
        verify(cache).saveProjects(data)
    }

    @Test
    fun clearProjectsCompletes(){
        stubProjectClearProjects(Completable.complete())
        val testCompletable = store.clearProjects().test()
        testCompletable.assertComplete()
    }

    @Test
    fun clearProjectsCallsCacheStore(){
        stubProjectClearProjects(Completable.complete())
        store.clearProjects().test()
        verify(cache).clearProjects()
    }

    @Test
    fun getBookmarkedProjectsCompletes(){
        stubGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getBookmarkedProjects().test().assertComplete()
    }

    @Test
    fun getBookmarkedProjectsCallsCacheStore(){
        stubGetBookmarkedProjects(Observable.just(listOf(ProjectFactory.makeProjectEntity())))
        store.getBookmarkedProjects().test()
        verify(cache).getBookmarkedProjects()
    }

    @Test
    fun getBookmarkedProjectsReturnData(){
        val data = listOf(ProjectFactory.makeProjectEntity())
        stubGetBookmarkedProjects(Observable.just(data))
        val testObeserver = store.getBookmarkedProjects().test()
        testObeserver.assertValue(data)
    }

    @Test
    fun setProjectAsBookmardkedCompletes(){
        stubGetProjectsCacheSetProjectAsBookmarked(Completable.complete())
        store.setProjectAsBookmarked(DataFactory.randomString()).test().assertComplete()
    }

    @Test
    fun setProjectAsBookmardkedCallsCache(){
        val projectId = DataFactory.randomString()
        stubGetProjectsCacheSetProjectAsBookmarked(Completable.complete())
        store.setProjectAsBookmarked(projectId).test().assertComplete()
        verify(cache).setProjectAsBookmarked(projectId)
    }

    @Test
    fun setProjectAsNotBookmardkedCompletes(){
        stubGetProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
        store.setProjectAsNotBookmarked(DataFactory.randomString()).test().assertComplete()
    }

    @Test
    fun setProjectAsNotBookmardkedCallsCache(){
        val projectId = DataFactory.randomString()
        stubGetProjectsCacheSetProjectAsNotBookmarked(Completable.complete())
        store.setProjectAsNotBookmarked(projectId).test().assertComplete()
        verify(cache).setProjectAsNotBookmarked(projectId)
    }


    private fun stubProjectsCacheGetProjects(observable : Observable<List<ProjectEntity>> ){
        whenever(cache.getProjects()).thenReturn(observable)
    }

    private fun stubProjectsSave(complete : Completable){
        whenever(cache.saveProjects(any())).thenReturn(complete)
    }

    private fun stubProjectClearProjects(complete: Completable){
        whenever(cache.clearProjects()).thenReturn(complete)
    }

    private fun stubGetBookmarkedProjects(observable: Observable<List<ProjectEntity>>){
        whenever(cache.getBookmarkedProjects()).thenReturn(observable)
    }

    private fun stubGetProjectsCacheSetProjectAsBookmarked(completable: Completable){
        whenever(cache.setProjectAsBookmarked(any())).thenReturn(completable)
    }

    private fun stubGetProjectsCacheSetProjectAsNotBookmarked(completable: Completable){
        whenever(cache.setProjectAsNotBookmarked(any())).thenReturn(completable)
    }

    private fun stubProjectsCacheSetLastCacheTime(completable: Completable){
        whenever(cache.setLastCacheTime(any())).thenReturn(completable)
    }
}
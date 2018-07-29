package moura.silva.com.remote

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.remote.mapper.ProjectsResponseModelMapper
import moura.silva.com.remote.model.ProjectModel
import moura.silva.com.remote.model.ProjectsResponseModel
import moura.silva.com.remote.service.GitHubTrendingService
import moura.silva.com.remote.test.factory.ProjectDataFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ProjectsRemoteImplTest{

    private val mapper = mock<ProjectsResponseModelMapper>()
    private val service = mock<GitHubTrendingService>()
    private val remote = ProjectsRemoteImpl(service,mapper)

    @Test
    fun getProjectsComplete(){
        stubGetProjectsComplete(Observable.just(ProjectDataFactory.makeProjectResponse()))
        stubGetProjectsCompleteWhenCallsMapperFromModel(any(),ProjectDataFactory.makeProjectEntity())
        remote.getProjects().test().assertComplete()
    }

    @Test
    fun getProjectsCallServer(){
        stubGetProjectsComplete(Observable.just(ProjectDataFactory.makeProjectResponse()))
        stubGetProjectsCompleteWhenCallsMapperFromModel(any(),ProjectDataFactory.makeProjectEntity())
        remote.getProjects()
        verify(service).searchRepository(any(), any(), any())
    }

    @Test
    fun getProjectsReturnsData(){
        val data = ProjectDataFactory.makeProjectResponse()
        stubGetProjectsComplete(Observable.just(data))
        val entities = mutableListOf<ProjectEntity>()
        data.items.forEach {
            val entity = ProjectDataFactory.makeProjectEntity()
            entities.add(entity)

            stubGetProjectsCompleteWhenCallsMapperFromModel(it,entity)
        }
        val testObserver = remote.getProjects().test()
        testObserver.assertValue(entities)
    }
    @Test
    fun getProjectsCallServiceWithCorrectParameters(){
        stubGetProjectsComplete(Observable.just(ProjectDataFactory.makeProjectResponse()))
        stubGetProjectsCompleteWhenCallsMapperFromModel(any(),ProjectDataFactory.makeProjectEntity())
        remote.getProjects()
        verify(service).searchRepository("language:kotlin", "start", "desc")
    }

    private fun stubGetProjectsComplete(observable : Observable<ProjectsResponseModel>){
        whenever(service.searchRepository(any(), any(), any())).thenReturn(observable)
    }
    private fun stubGetProjectsCompleteWhenCallsMapperFromModel(model: ProjectModel,entity : ProjectEntity){
        whenever(mapper.mapFromModel(model)).thenReturn(entity)
    }


}
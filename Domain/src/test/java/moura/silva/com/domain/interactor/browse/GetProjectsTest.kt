package moura.silva.com.domain.interactor.browse

import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Observable
import moura.silva.com.domain.executor.PostExecutionThread
import moura.silva.com.domain.model.Project
import moura.silva.com.domain.repository.ProjectRepository
import moura.silva.com.domain.test.ProjectDataFactory
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetProjectsTest{

    private lateinit var getProjects: GetProjects
    @Mock lateinit var projectsRepository: ProjectRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getProjects = GetProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getProjects())
                .thenReturn(observable)
    }
}
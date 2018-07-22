package moura.silva.com.domain.interactor.bookmark

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

class GetBookmarkProjectsTest{

    private lateinit var getBookmarkedProjects: GetBookmarkProjects
    @Mock lateinit var projectsRepository: ProjectRepository
    @Mock lateinit var postExecutionThread: PostExecutionThread

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getBookmarkedProjects = GetBookmarkProjects(projectsRepository, postExecutionThread)
    }

    @Test
    fun getBookmarkedProjectsCompletes() {
        stubGetProjects(Observable.just(ProjectDataFactory.makeProjectList(2)))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertComplete()
    }

    @Test
    fun getBookmarkedProjectsReturnsData() {
        val projects = ProjectDataFactory.makeProjectList(2)
        stubGetProjects(Observable.just(projects))
        val testObserver = getBookmarkedProjects.buildUseCaseObservable().test()
        testObserver.assertValue(projects)
    }

    private fun stubGetProjects(observable: Observable<List<Project>>) {
        whenever(projectsRepository.getBookmarkedProjects())
                .thenReturn(observable)
    }

}


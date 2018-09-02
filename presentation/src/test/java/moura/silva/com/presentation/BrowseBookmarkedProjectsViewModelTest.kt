package moura.silva.com.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import moura.silva.com.domain.interactor.bookmark.GetBookmarkProjects
import moura.silva.com.domain.model.Project
import moura.silva.com.presentation.factory.DataFactory
import moura.silva.com.presentation.factory.ProjectDataFactory
import moura.silva.com.presentation.mapper.ProjectViewMapper
import moura.silva.com.presentation.model.ProjectView
import moura.silva.com.presentation.state.ResourceState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor

@RunWith(JUnit4::class)
class BrowseBookmarkedProjectsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    var getBookmarkProjects = mock<GetBookmarkProjects>()
    var mapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseBookmarkedProjectsViewModel(getBookmarkProjects,mapper)

    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()


    @Test
    fun fetchProjectsExecute(){
        projectViewModel.fetchProjects()
        verify(getBookmarkProjects, times(1)).execute(any(), eq(null))
    }


    @Test
    fun fetchProjectsReturnSuccessState(){
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectsViews = ProjectDataFactory.makeProjectViewList(2)
        mockMapperMapToViewResponse(projectsViews.first(),projects.first())
        mockMapperMapToViewResponse(projectsViews.last(),projects.last())

        projectViewModel.fetchProjects()
        verify(getBookmarkProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(ResourceState.SUCCESS,projectViewModel.getProjects().value?.status)

    }

    @Test
    fun fetchProjectsReturnSuccessData(){
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectsViews = ProjectDataFactory.makeProjectViewList(2)
        mockMapperMapToViewResponse(projectsViews.first(),projects.first())
        mockMapperMapToViewResponse(projectsViews.last(),projects.last())

        projectViewModel.fetchProjects()
        verify(getBookmarkProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)
        assertEquals(projectsViews,projectViewModel.getProjects().value?.data)

    }

    @Test
    fun fetchProjectsReturnSuccessError(){
        projectViewModel.fetchProjects()
        verify(getBookmarkProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())
        assertEquals(ResourceState.ERROR,projectViewModel.getProjects().value?.status)

    }


    @Test
    fun fetchProjectsReturnSuccessErrorMessage(){
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()
        verify(getBookmarkProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))
        assertEquals(errorMessage,projectViewModel.getProjects().value?.message)

    }


    private fun mockMapperMapToViewResponse(projectView : ProjectView, project: Project) {
        whenever(mapper.mapToView(project)).thenReturn(projectView)
    }


}
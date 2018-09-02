package moura.silva.com.presentation

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import io.reactivex.observers.DisposableObserver
import junit.framework.Assert.assertEquals
import moura.silva.com.domain.interactor.bookmark.BookmarkProject
import moura.silva.com.domain.interactor.bookmark.UnbookmarkProject
import moura.silva.com.domain.interactor.browse.GetProjectsUseCase
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
class BrowseProjectsViewModelTest{

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    var getProjects = mock<GetProjectsUseCase>()
    var bookmarkedProjects = mock<BookmarkProject>()
    var unbookmarkProject = mock <UnbookmarkProject>()
    var projectMapper = mock<ProjectViewMapper>()
    var projectViewModel = BrowseProjectsViewModel(getProjects,bookmarkedProjects,unbookmarkProject,
            projectMapper)


    @Captor
    val captor = argumentCaptor<DisposableObserver<List<Project>>>()


    @Test
    fun fetchProjectsExecutesUseCase(){
        projectViewModel.fetchProjects()

        verify(getProjects,times(1)).execute(any(), eq(null))
    }



    @Test
    fun fetchProjectsReturnSuccess(){
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectsViews = ProjectDataFactory.makeProjectViewList(2)
        mockMapperMapToViewResponse(projectsViews.first(),projects.first())
        mockMapperMapToViewResponse(projectsViews.last(),projects.last())

        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(ResourceState.SUCCESS,projectViewModel.getProjects().value?.status)

    }



    @Test
    fun fetchProjectsReturnData(){
        val projects = ProjectDataFactory.makeProjectList(2)
        val projectsViews = ProjectDataFactory.makeProjectViewList(2)
        mockMapperMapToViewResponse(projectsViews.first(),projects.first())
        mockMapperMapToViewResponse(projectsViews.last(),projects.last())

        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(projects)

        assertEquals(projectsViews,projectViewModel.getProjects().value?.data)

    }

    @Test
    fun fetchProjectsReturnError(){
        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assertEquals(ResourceState.ERROR,projectViewModel.getProjects().value?.status)

    }

    @Test
    fun fetchProjectsReturnMessageForError(){
        val errorMessage = DataFactory.randomString()
        projectViewModel.fetchProjects()

        verify(getProjects).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assertEquals(errorMessage,projectViewModel.getProjects().value?.message)

    }

    private fun mockMapperMapToViewResponse(projectView : ProjectView, project: Project) {
        whenever(projectMapper.mapToView(project)).thenReturn(projectView)
    }
}

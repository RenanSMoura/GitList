package moura.silva.com.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.observers.DisposableObserver
import moura.silva.com.domain.interactor.bookmark.BookmarkProject
import moura.silva.com.domain.interactor.bookmark.UnbookmarkProject
import moura.silva.com.domain.interactor.browse.GetProjectsUseCase
import moura.silva.com.domain.model.Project
import moura.silva.com.presentation.mapper.ProjectViewMapper
import moura.silva.com.presentation.model.ProjectView
import moura.silva.com.presentation.state.Resource
import moura.silva.com.presentation.state.ResourceState
import javax.inject.Inject

class BrowseProjectsViewModel @Inject constructor(
        private val getProjectsUseCase: GetProjectsUseCase,
        private val bookmarkProject: BookmarkProject,
        private val unbookmarkProject: UnbookmarkProject,
        private val mapper: ProjectViewMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getProjectsUseCase.dispose()
        super.onCleared()
    }


    fun getProjects() : LiveData<Resource<List<ProjectView>>>{
        return liveData
    }

    fun fetchProjects() {
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        return getProjectsUseCase.execute(ProjectsSubscriber())
    }

    fun bookmarkProject(projectId : String){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        bookmarkProject.execute(BookmarkProjectSubscriber(),
                BookmarkProject.Params.forProject(projectId = projectId))
    }

    fun unbookmarkProject(projectId : String){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        unbookmarkProject.execute(BookmarkProjectSubscriber(),
                UnbookmarkProject.Params.forProject(projectId = projectId))
    }


    inner  class ProjectsSubscriber : DisposableObserver<List<Project>>(){
        override fun onComplete() {}

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,t.map {
                mapper.mapToView(it)
            },null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR,null, e.localizedMessage))
        }
    }

    inner class BookmarkProjectSubscriber : DisposableCompletableObserver(){
        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR,null,e.localizedMessage))
        }

        override fun onComplete() {
            liveData.postValue(Resource(ResourceState.SUCCESS,liveData.value?.data,null))
        }

    }

}
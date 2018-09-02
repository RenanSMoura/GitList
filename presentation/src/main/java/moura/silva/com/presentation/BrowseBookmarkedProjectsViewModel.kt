
package moura.silva.com.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.observers.DisposableObserver
import moura.silva.com.domain.interactor.bookmark.GetBookmarkProjects
import moura.silva.com.domain.model.Project
import moura.silva.com.presentation.mapper.ProjectViewMapper
import moura.silva.com.presentation.model.ProjectView
import moura.silva.com.presentation.state.Resource
import moura.silva.com.presentation.state.ResourceState
import javax.inject.Inject

class BrowseBookmarkedProjectsViewModel
    @Inject constructor(
            private val getBookmarkProject: GetBookmarkProjects,
            private val mapper : ProjectViewMapper
    ) : ViewModel(){


    private val liveData : MutableLiveData<Resource<List<ProjectView>>> = MutableLiveData()

    override fun onCleared() {
        getBookmarkProject.dispose()
        super.onCleared()
    }



    fun getProjects() : LiveData<Resource<List<ProjectView>>>{
        return liveData
    }

    fun fetchProjects(){
        liveData.postValue(Resource(ResourceState.LOADING,null,null))
        return getBookmarkProject.execute(ProjectsSubscriber())
    }



    inner class ProjectsSubscriber : DisposableObserver<List<Project>>(){
        override fun onComplete() {}

        override fun onNext(t: List<Project>) {
            liveData.postValue(Resource(ResourceState.SUCCESS,t.map{
                mapper.mapToView(it)
            },null))
        }

        override fun onError(e: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR,null,e.localizedMessage))
        }

    }





}
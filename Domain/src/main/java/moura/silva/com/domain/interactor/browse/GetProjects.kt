package moura.silva.com.domain.interactor.browse

import io.reactivex.Observable
import moura.silva.com.domain.executor.PostExecutionThread
import moura.silva.com.domain.interactor.ObservableUseCase
import moura.silva.com.domain.model.Project
import moura.silva.com.domain.repository.ProjectRepository
import javax.inject.Inject

class GetProjects @Inject constructor(
        private val projectRepository : ProjectRepository,
        postExecutionThread: PostExecutionThread)
    : ObservableUseCase<List<Project>, Nothing>(postExecutionThread){


    override fun buildUseCaseObservable(params: Nothing?): Observable<List<Project>> {
       return  projectRepository.getProjects()
    }
}
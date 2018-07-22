package moura.silva.com.domain.interactor.bookmark

import io.reactivex.Completable
import moura.silva.com.domain.executor.PostExecutionThread
import moura.silva.com.domain.interactor.CompletableUseCase
import moura.silva.com.domain.repository.ProjectRepository
import javax.inject.Inject

class BookmarkProject @Inject constructor(
        private val projectRepository: ProjectRepository,
        postExecutionThread: PostExecutionThread
) : CompletableUseCase<BookmarkProject.Params>(postExecutionThread){

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if(params == null) throw IllegalArgumentException(" Params cant be null")
         return projectRepository.bookmarkProject(params.projectId)
    }

    data class Params constructor(val projectId: String){
        companion object {
            fun forProject (projectId: String) : Params {
                return Params(projectId)
            }
        }
    }


}
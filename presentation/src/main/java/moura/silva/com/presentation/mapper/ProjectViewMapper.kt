package moura.silva.com.presentation.mapper

import moura.silva.com.domain.model.Project
import moura.silva.com.presentation.model.ProjectView
import javax.inject.Inject

class ProjectViewMapper @Inject constructor() : Mapper<ProjectView,Project> {
    override fun mapToView(type: Project): ProjectView {
        with( type){
            return ProjectView(id,name,fullName,starCount,dateCreated,ownerName,ownerAvatar,isBookmarked)
        }
    }

}
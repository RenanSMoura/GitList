package moura.silva.com.remote.mapper

import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.remote.model.ProjectModel

class ProjectsResponseModelMapper : ModelMapper<ProjectModel,ProjectEntity> {


    override fun mapFromModel(model: ProjectModel): ProjectEntity {
        return ProjectEntity(model.id,model.name,model.fullName,model.starCount.toString(),
                model.dateCreated,model.owner.ownerName,model.owner.ownerAvatar)
    }
}
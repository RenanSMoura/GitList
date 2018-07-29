package moura.silva.com.cache.mapper

import moura.silva.com.cache.model.CachedProject
import moura.silva.com.data.model.ProjectEntity

class CachedProjectMapper : CachedMapper<CachedProject,ProjectEntity> {

    override fun mapToCached(type: ProjectEntity): CachedProject {
        return CachedProject(type.id,type.name,type.fullName,type.starCount,type.dateCreated,
                type.ownerName,type.ownerAvatar,type.isBookmarked)
    }

    override fun mapFromCached(type: CachedProject): ProjectEntity {
        return ProjectEntity(type.id,type.name,type.fullName,type.starCount,type.dateCreated,
                type.ownerName,type.ownerAvatar,type.isBookmarked)
    }
}
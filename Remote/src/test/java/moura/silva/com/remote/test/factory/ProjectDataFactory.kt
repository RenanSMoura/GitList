package moura.silva.com.remote.test.factory

import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.remote.model.OwnerModel
import moura.silva.com.remote.model.ProjectModel
import moura.silva.com.remote.model.ProjectsResponseModel
import moura.silva.com.remote.test.factory.DataFactory.randomInt
import moura.silva.com.remote.test.factory.DataFactory.randomString

object ProjectDataFactory {

    fun makeOwner() : OwnerModel{
        return OwnerModel(randomString(), randomString())
    }

    fun makeProject() : ProjectModel{
        return ProjectModel(randomString(), randomString(),
                randomString(), randomInt(), randomString(), makeOwner())
    }

    fun makeProjectEntity() : ProjectEntity{
        return ProjectEntity(randomString(), randomString(), randomString(), randomString(),
                randomString(), randomString(), randomString(), true)
    }

    fun makeProjectResponse() : ProjectsResponseModel{
        return ProjectsResponseModel(listOf(makeProject(), makeProject()))
    }
}
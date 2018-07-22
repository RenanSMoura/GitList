package moura.silva.com.data.test.factory

import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.test.factory.DataFactory.randomBoolean
import moura.silva.com.data.test.factory.DataFactory.randomString
import moura.silva.com.domain.model.Project

object ProjectFactory{

    fun makeProjectEntity() = ProjectEntity(randomString(), randomString(), randomString(), randomString(),
            randomString(), randomString(), randomString(), randomBoolean())

    fun makeProject () = Project(randomString(), randomString(), randomString(), randomString(), randomString(),
            randomString(), randomString(), randomBoolean())
}
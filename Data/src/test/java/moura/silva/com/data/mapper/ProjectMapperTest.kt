package moura.silva.com.data.mapper

import moura.silva.com.data.model.ProjectEntity
import moura.silva.com.data.test.factory.ProjectFactory
import moura.silva.com.domain.model.Project
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class ProjectMapperTest{
    private val mapper = ProjectMapper()

    @Test
    fun mapFromEntityMapsData(){
        val entity = ProjectFactory.makeProjectEntity()
        val model = mapper.mapFromEntity(entity)
        assertEqualsData(entity,model)
    }

    @Test
    fun mapToEntityMapsData(){
        val model = ProjectFactory.makeProject()
        val entity = mapper.mapToEntity(model)
        assertEqualsData(entity,model)
    }

    private fun assertEqualsData(entity : ProjectEntity, model : Project){
        assertEquals(entity.id,model.id)
        assertEquals(entity.name,model.name)
        assertEquals(entity.fullName, model.fullName)
        assertEquals(entity.starCount,model.starCount)
        assertEquals(entity.dateCreated,model.dateCreated)
        assertEquals(entity.ownerName,model.ownerName)
        assertEquals(entity.ownerAvatar,model.ownerAvatar)
        assertEquals(entity.isBookmarked,model.isBookmarked)

    }
}
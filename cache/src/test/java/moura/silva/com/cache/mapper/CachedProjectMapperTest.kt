package moura.silva.com.cache.mapper

import moura.silva.com.cache.model.CachedProject
import moura.silva.com.cache.test.moura.silva.com.cache.test.factory.ProjectDataFactory
import moura.silva.com.data.model.ProjectEntity
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals


@RunWith(JUnit4::class)
class CachedProjectMapperTest{

    private val mapper = CachedProjectMapper()


    @Test
    fun mapFromCacheMapsData(){
        val model = ProjectDataFactory.makeCachedProject()
        val entity = mapper.mapFromCached(model)
        assertEqualData(model, entity)
    }

    @Test
    fun mapToCacheMapsData(){
        val entity = ProjectDataFactory.makeBookmarkedProjectEntity()
        val model = mapper.mapToCached(entity)
        assertEqualData(model, entity)
    }



    private fun assertEqualData(model : CachedProject,entity : ProjectEntity){
        assertEquals(model.id,entity.id)
        assertEquals(model.name,entity.name)
        assertEquals(model.fullName,entity.fullName)
        assertEquals(model.dateCreated,entity.dateCreated)
        assertEquals(model.starCount,entity.starCount)
        assertEquals(model.ownerAvatar,entity.ownerAvatar)
        assertEquals(model.ownerName,entity.ownerName)
        assertEquals(model.isBookmarked,entity.isBookmarked)

    }
}
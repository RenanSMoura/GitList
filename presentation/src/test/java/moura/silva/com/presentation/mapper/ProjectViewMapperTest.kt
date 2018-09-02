package moura.silva.com.presentation.mapper

import junit.framework.Assert.assertEquals
import moura.silva.com.domain.model.Project
import moura.silva.com.presentation.factory.ProjectDataFactory
import moura.silva.com.presentation.model.ProjectView
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class ProjectViewMapperTest{

    private val mapper = ProjectViewMapper()


    @Test
    fun mapToViewMapsData(){
        val project = ProjectDataFactory.makeProject()
        val projectView = mapper.mapToView(project)
        assertEqualData(project,projectView)

    }

    private fun assertEqualData(project: Project, projectView: ProjectView){
        assertEquals(project.id, projectView.id)
        assertEquals(project.name, projectView.name)
        assertEquals(project.fullName, projectView.fullName)
        assertEquals(project.dateCreated, projectView.dateCreated)
        assertEquals(project.starCount, projectView.starCount)
        assertEquals(project.ownerAvatar, projectView.ownerAvatar)
        assertEquals(project.ownerName, projectView.ownerName)
        assertEquals(project.isBookmarked, projectView.isBookmarked)

    }
}
package moura.silva.com.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import moura.silva.com.cache.db.ProjectConstants
import moura.silva.com.cache.db.ProjectConstants.DELETE_PROJECTS
import moura.silva.com.cache.db.ProjectConstants.QUERY_BOOKMARKED_PROJECTS
import moura.silva.com.cache.db.ProjectConstants.QUERY_UPDATE_BOOKMARKED_STATUS
import moura.silva.com.cache.model.CachedProject

@Dao
abstract class CachedProjectsDao {

    @Query(ProjectConstants.QUERY_PROJECTS)
    abstract fun getProjects() : Flowable<List<CachedProject>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertProjects(project: List<CachedProject>)

    @Query(DELETE_PROJECTS)
    abstract fun deleteProjects()

    @Query(QUERY_BOOKMARKED_PROJECTS)
    abstract fun getBookmarkedProjects() : Flowable<List<CachedProject>>


    @Query(QUERY_UPDATE_BOOKMARKED_STATUS)
    abstract fun setBookmarkedStatus( isBookmarked : Boolean, projectId : String)

}
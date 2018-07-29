package moura.silva.com.cache.model
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import moura.silva.com.cache.db.ProjectConstants
import moura.silva.com.cache.db.ProjectConstants.TABLE_NAME

@Entity(tableName = TABLE_NAME )
data class CachedProject(
        @PrimaryKey
        @ColumnInfo(name = ProjectConstants.COLUMN_PROJECT_ID)
        var id : String,
        var name : String,
        var fullName : String,
        var starCount : String,
        var dateCreated : String,
        var ownerName : String,
        var ownerAvatar : String,
        @ColumnInfo(name = ProjectConstants.COLUMN_IS_BOOKMARKED)
        var isBookmarked : Boolean
){
}
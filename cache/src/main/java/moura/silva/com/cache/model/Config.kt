package moura.silva.com.cache.model

import android.arch.persistence.room.Entity
import moura.silva.com.cache.db.ConfigConstants

@Entity(tableName = ConfigConstants.TABLE_NAME)
class Config(val id : String ,val lastCacheTime : Long) {
}
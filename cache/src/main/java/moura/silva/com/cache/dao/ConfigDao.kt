package moura.silva.com.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import moura.silva.com.cache.db.ConfigConstants
import moura.silva.com.cache.model.Config

@Dao
abstract class ConfigDao {


    @Query(ConfigConstants.QUERY_CONFIG)
    abstract fun getConfig() : Single<Config>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertConfig(config: Config)
}
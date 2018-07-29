package moura.silva.com.cache.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import moura.silva.com.cache.dao.CachedProjectsDao
import moura.silva.com.cache.dao.ConfigDao
import moura.silva.com.cache.model.CachedProject
import moura.silva.com.cache.model.Config
import javax.inject.Inject

@Database(entities = [(CachedProject::class),(Config::class)],version = 1)
abstract class ProjectsDatabase @Inject constructor() : RoomDatabase(){

    abstract fun cachedProjectsDao() : CachedProjectsDao
    abstract fun configDao() : ConfigDao

    private var INSTANCE : ProjectsDatabase? = null
    private val lock = Any()

    fun getInstance(context: Context) : ProjectsDatabase{
            return when(INSTANCE){
                null -> synchronized(lock){
                    when(INSTANCE){
                        null -> Room.databaseBuilder(context.applicationContext,ProjectsDatabase
                                ::class.java,"projects.db").build()

                        else ->  throw  IllegalArgumentException("Must have db implementation ")

                    }
                }
                else -> INSTANCE as ProjectsDatabase
            }

    }

}
package moura.silva.com.cache.dao

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import moura.silva.com.cache.db.ProjectsDatabase
import moura.silva.com.cache.test.moura.silva.com.cache.test.factory.ConfigDataFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class ConfigDaoTest{


    @Rule
    @JvmField
    var instantTaskExecutorRule  =  InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.applicationContext,
            ProjectsDatabase::class.java
    ).allowMainThreadQueries().build()

    @After
    fun closeDb(){
        database.close()
    }

    @Test
    fun saveConfigurationSavesData(){
        val config = ConfigDataFactory.makeCachedConfig()
        database.configDao().insertConfig(config)
        database.configDao().getConfig().test().assertValue(config)
    }
}
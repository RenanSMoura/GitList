package moura.silva.com.data.store

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import kotlin.test.assertEquals

open class ProjectsDataStoreFactoryTest {

    private val cacheStore = mock<ProjectsCacheDataStore>()
    private val remoteStore = mock<ProjectsRemoteDataStore>()
    private val factory = ProjectsDataStoreFactory(cacheStore,remoteStore)


    @Test
    fun getDataStoreReturnsStoreWhenCacheExpired(){
        assertEquals(remoteStore,factory.getDataStore(true,true))
    }

    @Test
    fun getDataStoreReturnsStoreWhenProjectsNotCached(){
        assertEquals(remoteStore,factory.getDataStore(false,false))
    }

    @Test
    fun getDataStoreReturnsCacheStoreProjects(){
        assertEquals(cacheStore,factory.getDataStore(true,false))
    }

    @Test
    fun getDataStoreReturnCachedStore(){
        assertEquals(cacheStore,factory.getCacheDataStore())
    }
}
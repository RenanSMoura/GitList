package moura.silva.com.cache.mapper

interface CachedMapper<C,E> {

    fun mapFromCached(type : C) : E
    fun mapToCached(type : E) : C

}
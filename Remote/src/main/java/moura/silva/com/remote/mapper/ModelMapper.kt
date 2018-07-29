package moura.silva.com.remote.mapper

interface ModelMapper<in M, out E> {
    fun mapFromModel(model : M) : E
}
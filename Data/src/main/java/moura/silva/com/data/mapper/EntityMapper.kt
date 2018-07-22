package moura.silva.com.data.mapper


//E = Entity from DATA layer
// E = Entity from DOMAIN layer
interface EntityMapper<E,D> {

    fun mapFromEntity(entity : E) : D
    fun mapToEntity (domain : D) : E
}
package console.models

interface DDStore {
    fun findAll(): List<DDModel>
    fun findOne(id: Long): DDModel?
    fun create(DDModel: DDModel)
    fun update(DDModel: DDModel)
    fun delete(DDModel: DDModel)
}
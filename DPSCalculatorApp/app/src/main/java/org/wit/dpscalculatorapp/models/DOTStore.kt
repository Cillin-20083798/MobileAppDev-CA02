package console.models

interface DOTStore {
    fun findAll(): List<DOTModel>
    fun findOne(id: Long): DOTModel?
    fun create(DOT: DOTModel)
    fun update(DOTModel: DOTModel)
    fun delete(DOTModel: DOTModel)
}
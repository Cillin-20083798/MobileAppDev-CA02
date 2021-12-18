
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import console.models.DDModel

import console.models.DDStore
import console.models.DOTModel
import console.models.DOTStore
import org.wit.dpscalculatorapp.helpers.exists
import org.wit.dpscalculatorapp.helpers.read
import org.wit.dpscalculatorapp.helpers.write
import java.lang.Math.floor
import java.util.*



val JSON_FILE_DD = "dd.json"
val gsonBuilderDD = GsonBuilder().setPrettyPrinting().create()
val listTypeDD = object : TypeToken<java.util.ArrayList<DDModel>>() {}.type



class DDJSONStore (private val context : Context){

    private fun generateRandomId(): Long {
        return Random().nextLong()
    }

    var dds = mutableListOf<DDModel>()

    init {
        if (exists(context, JSON_FILE_DD)) {
            deserialize()
        }
    }

    fun findAll(): MutableList<DDModel> {
        return dds
    }

    fun findOne(id: Long) : DDModel? {
        var foundDot: DDModel? = dds.find { p -> p.id == id }
        return foundDot
    }

    fun create(DD: DDModel) {
        DD.id = generateRandomId()
        dds.add(DD)
        serialize()
    }

    fun update(DD: DDModel) {
        var foundDD = findOne(DD.id!!)
        if (foundDD != null) {
            foundDD.name = DD.name
            foundDD.damagePerHit = DD.damagePerHit
            foundDD.timeBetweenAttacks = DD.timeBetweenAttacks
            foundDD.numberOfProjectiles = DD.numberOfProjectiles
            foundDD.reloadSpeed = DD.reloadSpeed
            foundDD.magSize = DD.magSize

            foundDD.dps60 = calculateDPS(foundDD, 60f)
            foundDD.dps5 = calculateDPS(foundDD, 5f)
            println("\n-------------------------Get DPS Calc---------------------------\n")
        }
        println("\n-------------------------Update DD---------------------------\n")
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilderDD.toJson(dds, listTypeDD)
        write(context, JSON_FILE_DD, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_DD)
        dds = Gson().fromJson(jsonString, listTypeDD)
    }

    fun delete(DD: DDModel) {
        dds.remove(DD)
        serialize()
    }

    fun searchDDByName(name: String) : List<DDModel>?{
        var resultArray = listOf<DDModel>().toMutableList()
        dds.forEach {
            if (it.name.contains(name)) {
                resultArray.add(it)
            }
        }
        return resultArray
    }

    fun searchDDForHighDPS(value : Float) : List<DDModel> {
        var highestDPSArray = listOf<DDModel>().toMutableList()

        dds.forEach {
            if (it.dps60!! >= value) {
                highestDPSArray.add(it)
            }
        }
        return highestDPSArray
    }

    fun calculateDPS (dd: DDModel, time: Float) : Float {
        /**var damagePerHit: Float = 0f,
        var timeBetweenAttacks: Float = 0f,
        var numberOfProjectiles: Float = 0f,
        var reloadSpeed: Float = 0f,
        var magSize: Float = 0f,**/


        if(dd.magSize == 0){
            var totalAttacks = kotlin.math.floor(time / dd.timeBetweenAttacks!!) //floor this
            println(totalAttacks)
            return totalAttacks * (dd.damagePerHit!! * dd.numberOfProjectiles!!) / time
        }else{
            var timeToShootMag = dd.magSize!! * dd.timeBetweenAttacks!!

            var totalBullets = kotlin.math.floor(time / dd.timeBetweenAttacks!!)

            var reloads = totalBullets / dd.magSize!!


            var timeReloading = timeToShootMag * reloads

            var totalAttacks = time / dd.timeBetweenAttacks!! - timeReloading

            return (totalAttacks * (dd.damagePerHit?.times(dd.numberOfProjectiles!!)!!) / time).toFloat()
        }

    }



}

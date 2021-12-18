
import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import console.models.DOTModel
import console.models.DOTStore
import org.wit.dpscalculatorapp.helpers.exists
import org.wit.dpscalculatorapp.helpers.read
import org.wit.dpscalculatorapp.helpers.write
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt


class DOTJSONStore (private val context : Context){

    val JSON_FILE_DOT = "dots.json"
    val gsonBuilderDot = GsonBuilder().setPrettyPrinting().create()
    val listTypeDot = object : TypeToken<java.util.ArrayList<DOTModel>>() {}.type

    private fun generateRandomId(): Long {
        return Random().nextLong()
    }

    var dots = mutableListOf<DOTModel>()

    init {
        if (exists(context, JSON_FILE_DOT)) {
            deserialize()
        }
    }

    fun findAll(): MutableList<DOTModel> {
        return dots
    }

    fun findOne(id: Long) : DOTModel? {
        var foundDot: DOTModel? = dots.find { p -> p.id == id }
        return foundDot
    }

    fun create(DOT: DOTModel) {
        DOT.id = generateRandomId()
        DOT.dps60 = calculateDPS(DOT, 60f)
        DOT.dps5 = calculateDPS(DOT, 5f)
        dots.add(DOT)
        serialize()
    }

    fun update(DOT: DOTModel) {
        var foundDOT = findOne(DOT.id!!)
        if (foundDOT != null) {
            foundDOT.name = DOT.name
            foundDOT.damagePerTick = DOT.damagePerTick
            foundDOT.initialDamage = DOT.initialDamage
            foundDOT.duration = DOT.duration
            foundDOT.percentIncreasePerTick = DOT.percentIncreasePerTick

            foundDOT.dps60 = calculateDPS(foundDOT, 60f)
            foundDOT.dps5 = calculateDPS(foundDOT, 5f)
        }
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilderDot.toJson(dots, listTypeDot)
        write(context, JSON_FILE_DOT, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_DOT)
        dots = Gson().fromJson(jsonString, listTypeDot)
    }

    fun delete(DOT: DOTModel) {
        dots.remove(DOT)
        serialize()
    }

    fun searchDOTByName(name: String) : List<DOTModel>?{
        var resultArray = listOf<DOTModel>().toMutableList()
        dots.forEach {
            if (it.name.contains(name)) {
                resultArray.add(it)
            }
        }
        return resultArray
    }

    fun searchDOTForHighDPS(value : Float) : List<DOTModel> {
        var highestDPSArray = listOf<DOTModel>().toMutableList()

        dots.forEach {
            if (it.dps60!! >= value) {
                highestDPSArray.add(it)
            }
        }
        return highestDPSArray
    }

    fun calculateDPS (dot: DOTModel, time: Float) : Float{

        /**var tickTime: Float = 0f,
        var damagePerTick: Float = 0f,
        var initialDamage: Float = 0f,
        var duration: Float = 0f,
        var percentIncreasePerTick: Float = 0f,**/
        var totalDamage = 0f;
        var currentTickDamage = dot.damagePerTick
        var totalDotDamage = 0f
        var totalTicksPerDuration = Math.floor(((1 / dot.tickTime!!) * dot.duration!!).toDouble())
        var iterateCount = 0f
        var totalTicksLeft = 0f

        if(dot.duration == 0f) { return dot.initialDamage!!}

        if(time >= dot.duration!!) {
            //Gets the total amount of times the dot will cycle
            iterateCount = Math.floor((time / dot.duration!!).toDouble()).toFloat() // floor this //6




            //Calc each dot value
            for (i in 1..totalTicksPerDuration.roundToInt()) {
                currentTickDamage = currentTickDamage?.plus(currentTickDamage * dot.percentIncreasePerTick!!)
                totalDotDamage += currentTickDamage!! // 2000
            }

            totalDamage = (totalDotDamage + dot.initialDamage!!) * iterateCount //13200
        }

        //Finding the remainder ticks
        currentTickDamage = dot.damagePerTick
        totalTicksLeft = (((time / dot.duration!!) - iterateCount) * totalTicksPerDuration).toFloat()
        if (totalTicksLeft == 0f) return totalDamage / time


        //Calc each tick
        for (i in 1 .. totalTicksLeft.roundToInt()) {
            println(i)
            currentTickDamage = currentTickDamage?.plus(currentTickDamage * dot.percentIncreasePerTick!!)
            totalDotDamage += currentTickDamage!!
        }

        return (totalDamage + totalDotDamage + dot.initialDamage!!) / time


    }
}
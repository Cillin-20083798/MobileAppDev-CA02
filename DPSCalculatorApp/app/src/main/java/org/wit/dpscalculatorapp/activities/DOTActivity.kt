package org.wit.dpscalculatorapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isInvisible
import com.google.android.material.snackbar.Snackbar
import console.models.DOTModel
import org.wit.dpscalculatorapp.R
import org.wit.dpscalculatorapp.databinding.ActivityDotactivityBinding
import org.wit.dpscalculatorapp.main.MainApp
import timber.log.Timber

class DOTActivity : AppCompatActivity() {



    
    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityDotactivityBinding
        var DOT = DOTModel()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dotactivity)

        var edit = false

        var goodParse = true

        binding = ActivityDotactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var app: MainApp = application as MainApp


        binding.removebtn.alpha = 0f

        if (intent.hasExtra("dot_edit")) {
            edit = true
            DOT = intent.extras?.getParcelable("dot_edit")!!
            binding.name.setText(DOT.name)
            binding.tickTime.setText("" + DOT.tickTime)
            binding.damagePerTick.setText("" + DOT.damagePerTick)
            binding.duration.setText("" + DOT.duration)
            binding.initialDamage.setText("" + DOT.initialDamage)
            binding.percentIncreasePerTick.setText("" + DOT.percentIncreasePerTick)
            binding.btnAdd.setText(R.string.save_edit)

            binding.removebtn.alpha = 1f

            binding.removebtn.setOnClickListener() {
                app.dots.delete(DOT)
                finish()
            }

        }



        binding.btnAdd.setOnClickListener() {
            DOT.name = binding.name.text.toString()

            DOT.tickTime = (binding.tickTime.text.toString()).toFloatOrNull()
            if(DOT.tickTime == null){
                goodParse = false;
            }
            DOT.damagePerTick = (binding.damagePerTick.text.toString()).toFloatOrNull()
            if(DOT.damagePerTick == null){
                goodParse = false;
            }
            DOT.initialDamage = (binding.initialDamage.text.toString()).toFloatOrNull()
            if(DOT.initialDamage == null){
                goodParse = false;
            }
            DOT.duration = (binding.duration.text.toString()).toFloatOrNull()
            if(DOT.duration == null){
                goodParse = false;
            }
            DOT.percentIncreasePerTick = (binding.percentIncreasePerTick.text.toString()).toFloatOrNull()
            if(DOT.percentIncreasePerTick == null){
                goodParse = false;
            }

            if (DOT.name.isEmpty() || !goodParse) {
                Snackbar.make(it, "@strings/invalidInput", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.dots.update(DOT.copy())
                } else {
                    app.dots.create(DOT.copy())
                }
            }

            Timber.i("add Button Pressed")
            setResult(RESULT_OK)
            finish()
        }



    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_dps, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
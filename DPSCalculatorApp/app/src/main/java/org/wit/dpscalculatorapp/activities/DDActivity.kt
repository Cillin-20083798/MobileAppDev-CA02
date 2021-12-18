package org.wit.dpscalculatorapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import console.models.DDModel
import org.wit.dpscalculatorapp.R
import org.wit.dpscalculatorapp.databinding.ActivityDdactivityBinding
import org.wit.dpscalculatorapp.main.MainApp
import timber.log.Timber

class DDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var binding: ActivityDdactivityBinding
        var DD = DDModel()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dotactivity)

        var edit = false

        var goodParse = true

        binding = ActivityDdactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        var app: MainApp = application as MainApp

        binding.removebtn.alpha = 0f

        if (intent.hasExtra("dd_edit")) {
            edit = true
            DD = intent.extras?.getParcelable("dd_edit")!!
            binding.name.setText(DD.name)
            binding.damagePerHit.setText("" + DD.damagePerHit)
            binding.timeBetweenAttacks.setText("" + DD.timeBetweenAttacks)
            binding.numberOfProjectiles.setText("" + DD.numberOfProjectiles)
            binding.reloadSpeed.setText("" + DD.reloadSpeed)
            binding.magSize.setText("" + DD.magSize)
            binding.btnAdd.setText(R.string.save_edit)

            binding.removebtn.alpha = 1f

            binding.removebtn.setOnClickListener() {
                app.dds.delete(DD)
                finish()
            }

        }


        binding.btnAdd.setOnClickListener() {
            DD.name = binding.name.text.toString()

            DD.damagePerHit = (binding.damagePerHit.text.toString()).toFloatOrNull()
            if (DD.damagePerHit == null) {
                println("\n-------------------------Update 1---------------------------\n")
                goodParse = false;
            }
            DD.timeBetweenAttacks = (binding.timeBetweenAttacks.text.toString()).toFloatOrNull()
            if (DD.timeBetweenAttacks == null) {
                println("\n-------------------------Update 2---------------------------\n")
                goodParse = false;
            }
            DD.numberOfProjectiles = (binding.numberOfProjectiles.text.toString()).toFloatOrNull()
            if (DD.numberOfProjectiles == null) {
                println("\n-------------------------Update 3---------------------------\n")
                goodParse = false;
            }
            DD.reloadSpeed = (binding.reloadSpeed.text.toString()).toFloatOrNull()
            if (DD.reloadSpeed == null) {
                println("\n-------------------------Update 4---------------------------\n")
                goodParse = false;
            }
            DD.magSize = (binding.magSize.text.toString()).toIntOrNull()
            if (DD.magSize == null) {
                println("\n-------------------------Update 5---------------------------\n")
                goodParse = false;
            }

            if (DD.name.isEmpty() || !goodParse) {
                Snackbar.make(it, "@strings/invalidInput", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                if (edit) {
                    app.dds.update(DD.copy())
                } else {
                    app.dds.create(DD.copy())
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
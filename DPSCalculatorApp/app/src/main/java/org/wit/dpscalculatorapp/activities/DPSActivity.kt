package org.wit.dpscalculatorapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import org.wit.dpscalculatorapp.R
import org.wit.dpscalculatorapp.databinding.ActivityDamageBinding
import org.wit.dpscalculatorapp.main.MainApp
import timber.log.Timber.i

class DPSActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDamageBinding

    lateinit var app: MainApp


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityDamageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp
        binding.DamageOverTime.setOnClickListener(){launchDOTActivity()}
        binding.DirectDamage.setOnClickListener(){launchDDActivity()}

        i("Placemark Activity started...")
    }

    fun launchDOTActivity(){
        val intent = Intent(this, DOTActivity::class.java)
        startActivity(intent)
    }

    fun launchDDActivity(){
        val intent = Intent(this, DDActivity::class.java)
        startActivity(intent)
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
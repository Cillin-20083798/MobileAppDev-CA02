package org.wit.dpscalculatorapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import console.models.DDModel
import console.models.DOTModel
import org.wit.dpscalculatorapp.databinding.ActivityDamageListBinding
import org.wit.dpscalculatorapp.R
import org.wit.dpscalculatorapp.adapters.DDAdapter
import org.wit.dpscalculatorapp.adapters.DOTAdapter
import org.wit.dpscalculatorapp.adapters.DDListener
import org.wit.dpscalculatorapp.adapters.DOTListener

import org.wit.dpscalculatorapp.main.MainApp


class DPSListActivity : AppCompatActivity(), DDListener, DOTListener {

    lateinit var app: MainApp
    private lateinit var binding: ActivityDamageListBinding
    private lateinit var refreshIntentLauncher : ActivityResultLauncher<Intent>
    lateinit var dotDisplay : List<DOTModel>
    lateinit var ddDisplay : List<DDModel>

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDamageListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        app = application as MainApp

        dotDisplay = app.dots.findAll()
        ddDisplay = app.dds.findAll()

        val layoutManager1 = LinearLayoutManager(this)
        val layoutManager2 = LinearLayoutManager(this)

        binding.searchDOTName.setOnClickListener(){searchNameDOTs()}
        binding.searchDOTDPS.setOnClickListener(){searchDPSDOT()}

        binding.searchDDName.setOnClickListener(){searchNameDD()}
        binding.searchDDDPS.setOnClickListener(){searchDPSDD()}

        binding.DOTView.layoutManager = layoutManager1
        binding.DDView.layoutManager = layoutManager2

        binding.DOTView.adapter = DOTAdapter(app.dots.findAll(), this)
        binding.DDView.adapter = DDAdapter(app.dds.findAll(), this)

        loadData()

        registerRefreshCallback()
    }

    private fun searchNameDOTs() {
        val term = binding.valSearchDOT.text.toString()

        dotDisplay = app.dots.searchDOTByName(term)!!
        loadData()
    }

    private fun searchDPSDOT() {

        val term = binding.valSearchDOT.text.toString().toFloatOrNull()

        if(term == null){
            dotDisplay = app.dots.findAll()
            loadData()
            return
        }



        dotDisplay = app.dots.searchDOTForHighDPS(term)
        loadData()
    }

    private fun searchNameDD() {
        val term = binding.valSearchDD.text.toString()

        ddDisplay = app.dds.searchDDByName(term)!!
        loadData()
    }

    private fun searchDPSDD() {
        val term = binding.valSearchDD.text.toString().toFloatOrNull()

        if(term == null){
            ddDisplay = app.dds.findAll()
            loadData()
            return
        }

        ddDisplay = app.dds.searchDDForHighDPS(term)
        loadData()
    }




    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> {
                val launcherIntent = Intent(this, DPSActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadData() }
    }


    private fun loadData(){
        loadDots()
        loadDDs()
    }

    private fun loadDots() {
        showDOTs(dotDisplay)
    }

    private fun loadDDs() {
        showDDs(ddDisplay)
    }

    fun showDOTs (dots: List<DOTModel>) {
        binding.DOTView.adapter = DOTAdapter(dots, this)
        binding.DOTView.adapter?.notifyDataSetChanged()
    }

    fun showDDs (dds: List<DDModel>) {
        binding.DDView.adapter = DDAdapter(dds, this)
        binding.DDView.adapter?.notifyDataSetChanged()
    }

    override fun onDDClick(dd: DDModel) {
        val launcherIntent = Intent(this, DDActivity::class.java)
        launcherIntent.putExtra("dd_edit", dd)
        refreshIntentLauncher.launch(launcherIntent)
    }

    override fun onDOTClick(dot: DOTModel) {
        val launcherIntent = Intent(this, DOTActivity::class.java)
        launcherIntent.putExtra("dot_edit", dot)
        refreshIntentLauncher.launch(launcherIntent)
    }
}
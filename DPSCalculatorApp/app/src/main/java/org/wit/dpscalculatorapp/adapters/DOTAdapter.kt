package org.wit.dpscalculatorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

import console.models.DDModel
import console.models.DOTModel
import org.wit.dpscalculatorapp.databinding.CardDamageBinding


interface DOTListener {
    fun onDOTClick(dot: DOTModel)
}


class DOTAdapter constructor(private var dots: List<DOTModel>,
                             private val listener: DOTListener) :
    RecyclerView.Adapter<DOTAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDamageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dot = dots[holder.adapterPosition]
        holder.bind(dot, listener)
    }

    override fun getItemCount(): Int = dots.size

    class MainHolder(private val binding : CardDamageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dot: DOTModel, listener: DOTListener) {
            binding.name.text = dot.name
            binding.dps60.text = dot.dps60.toString()
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onDOTClick(dot) }
        }
    }
}
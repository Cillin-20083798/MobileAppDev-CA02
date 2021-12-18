package org.wit.dpscalculatorapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.wit.dpscalculatorapp.databinding.CardDamageBinding
import console.models.DDModel



interface DDListener {
    fun onDDClick(dd: DDModel)
}


class DDAdapter constructor(private var dds: List<DDModel>,
                             private val listener: DDListener) :
    RecyclerView.Adapter<DDAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardDamageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val dd = dds[holder.adapterPosition]
        holder.bind(dd, listener)
    }

    override fun getItemCount(): Int = dds.size

    class MainHolder(private val binding : CardDamageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dd: DDModel, listener: DDListener) {
            binding.name.text = dd.name
            binding.dps60.text = dd.dps60.toString()
            //Picasso.get().load(placemark.image).resize(200,200).into(binding.imageIcon)
            binding.root.setOnClickListener { listener.onDDClick(dd) }
        }
    }
}
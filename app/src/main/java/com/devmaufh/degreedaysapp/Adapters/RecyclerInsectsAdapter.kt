package com.devmaufh.degreedaysapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devmaufh.degreedaysapp.Entities.Insect
import com.devmaufh.degreedaysapp.R

class RecyclerInsectsAdapter internal constructor(context: Context):RecyclerView.Adapter<RecyclerInsectsAdapter.InsectsViewHolder>(){
    private val inflater:LayoutInflater= LayoutInflater.from(context)
    private var insectsList= emptyList<Insect>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InsectsViewHolder {
        val itemView=inflater.inflate(R.layout.recycler_item,parent,false)
        return InsectsViewHolder(itemView)
    }

    override fun getItemCount()=insectsList.size

    override fun onBindViewHolder(holder: InsectsViewHolder, position: Int) {
        val currentInsect=insectsList[position]
        if(position%2==0){
            //holder.card.cardElevation =0.0F //(0..30).random()+ 0.0F
        }
        holder.tvname.text=currentInsect.name

        holder.card.setOnLongClickListener {
            Toast.makeText(inflater.context, "Este es un click largo", Toast.LENGTH_SHORT).show();
            true
        }
    }

    fun setInsectList(insectsList:List<Insect>){
        this.insectsList=insectsList
        notifyDataSetChanged()
    }

    inner class InsectsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val card:CardView=itemView.findViewById(R.id.itemCardContainer)
        val image:ImageView=itemView.findViewById(R.id.item_image)
        val tvname:TextView=itemView.findViewById(R.id.item_tvName)
    }
}

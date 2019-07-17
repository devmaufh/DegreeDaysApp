package com.devmaufh.degreedaysapp.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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
        if(currentInsect.insect_id%1==0){
            holder.image.setImageResource(R.drawable.insect_1)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.pastel_blue))
        }
        if(currentInsect.insect_id%2==0){
            holder.image.setImageResource(R.drawable.insect_2)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.pastel_salmon))
        }
        if(currentInsect.insect_id%3==0){
            holder.image.setImageResource(R.drawable.insect_3)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.blue))
        }
        if(currentInsect.insect_id%4==0){
            holder.image.setImageResource(R.drawable.insect_4)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.otro_blue))
        }
        if(currentInsect.insect_id%5==0){
            holder.image.setImageResource(R.drawable.insect_8)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.otro))
        }
        if(currentInsect.insect_id%6==0){
            holder.image.setImageResource(R.drawable.insect_6)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.morado))
        }
        if(currentInsect.insect_id%6==0){
            holder.image.setImageResource(R.drawable.insect_7)
            holder.card.setBackgroundColor(ContextCompat.getColor(inflater.context,R.color.colorAccent))
        }

        holder.tvname.text=currentInsect.name
        holder.tvAc.text="125"
        holder.tvHoy.text="35"
    }

    fun setInsectList(insectsList:List<Insect>){
        this.insectsList=insectsList
        notifyDataSetChanged()
    }

    inner class InsectsViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val card:CardView=itemView.findViewById(R.id.itemCardContainer)
        val image:ImageView=itemView.findViewById(R.id.item_image)
        val tvname:TextView=itemView.findViewById(R.id.item_tvName)
        val tvAc:TextView=itemView.findViewById(R.id.item_tvAc)
        val tvHoy:TextView=itemView.findViewById(R.id.item_tvAc)
    }
}

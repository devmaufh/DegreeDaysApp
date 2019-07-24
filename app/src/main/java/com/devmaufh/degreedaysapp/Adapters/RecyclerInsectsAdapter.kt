package com.devmaufh.degreedaysapp.Adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devmaufh.degreedaysapp.Activities.HomeActivity.Companion.deleteInsectActivityRequestCode
import com.devmaufh.degreedaysapp.Activities.InsectDetailsActivity
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
            Log.w("DEGREE TU ",currentInsect.tu.toString())
            Log.w("DEGREE TL ",currentInsect.tl.toString())




            var intentDetails=Intent(inflater.context,InsectDetailsActivity::class.java)
            intentDetails.putExtra(InsectDetailsActivity.EXTRA_INSECT_ID,currentInsect.insect_id)
            intentDetails.putExtra(InsectDetailsActivity.EXTRA_INSECT_NAME,currentInsect.name)
            intentDetails.putExtra(InsectDetailsActivity.EXTRA_INSECT_TU,currentInsect.tu)
            intentDetails.putExtra(InsectDetailsActivity.EXTRA_INSECT_TL,currentInsect.tl)
            intentDetails.putExtra(InsectDetailsActivity.EXTRA_INSECT_RD,currentInsect.registration_date)
            //inflater.context.startActivity(intentDetails)
            val context =inflater.context as Activity
            context.startActivityForResult(intentDetails,deleteInsectActivityRequestCode)
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

package com.devmaufh.degreedaysapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerInsectsAdapter extends RecyclerView.Adapter<RecyclerInsectsAdapter.ViewHolder> {
    List<InsectEntity> insectEntityList;
    private int layout;
    Context context;
    RecyclerClickListener listener;

    public RecyclerInsectsAdapter(List<InsectEntity> insects,int layout,RecyclerClickListener listener){
        this.insectEntityList=insects;
        this.layout=layout;
        this.listener=listener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(layout,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context=parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(insectEntityList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return insectEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvName,tvAc;
        public ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName=(TextView)itemView.findViewById(R.id.rvItem_title);
            tvAc=(TextView)itemView.findViewById(R.id.rvItem_UC);
            img=(ImageView)itemView.findViewById(R.id.rvItem_img);
        }
        public void bind(final InsectEntity insectEntity,final RecyclerClickListener listener){
            tvName.setText(insectEntity.getName());
            tvAc.setText("326.14 "+context.getResources().getString(R.string.acomulado));
            img.setImageResource(AdditionalMethods.getRandomImageDrawable());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onCardInsectClick(insectEntity,getAdapterPosition());
                }
            });
        }
    }
    public interface RecyclerClickListener{
        void onCardInsectClick(InsectEntity insectEntity, int position);
    }
}

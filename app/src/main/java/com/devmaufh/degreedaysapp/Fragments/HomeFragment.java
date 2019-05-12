package com.devmaufh.degreedaysapp.Fragments;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.coderfolk.multilamp.customView.MultiLamp;
import com.coderfolk.multilamp.model.Target;
import com.coderfolk.multilamp.shapes.Circle;
import com.devmaufh.degreedaysapp.Activities.Registro;
import com.devmaufh.degreedaysapp.Adapters.RecyclerInsectsAdapter;
import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FloatingActionButton fabNew;
    private List<InsectEntity> insectsList;
    private SharedPreferences preferences;
    //RecyclerView
    RecyclerView rvInsects;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;


    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        preferences=getActivity().getSharedPreferences(AdditionalMethods.PREFERENCES_THEME  ,Context.MODE_PRIVATE);
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        databaseTest();
        bindUI(view);
        fabNew.setOnClickListener(v -> { //Starts Registro activity :D
            startActivity(new Intent(getContext(), Registro.class));
        });
        initialtips();
        populateRecycler(view);
        AdditionalMethods.testDatesList();
        return view;
    }
    private void bindUI(View view) {
        fabNew=(FloatingActionButton)view.findViewById(R.id.frh_fabAdd);
        rvInsects=(RecyclerView)view.findViewById(R.id.frh_rvInsects);

    }
    private DatabaseViewModel mInsectViewModel;
    private void databaseTest(){
        mInsectViewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        mInsectViewModel.getmAllDates().observe(this, new Observer<List<DatesEntity>>() {
            @Override
            public void onChanged(List<DatesEntity> datesEntities) {
                for(DatesEntity d:datesEntities){
                    Log.d("HomeFragment->databaseTest", "ID: "+d.getId()+"\t DATE: "+d.getDate());
                }
            }
        });
    }
    private void initialtips(){
        MultiLamp multiLamp= new MultiLamp(getActivity());
        ArrayList<Target> targets= new ArrayList<Target>();
        targets.add(new Target(fabNew,"Aquí puedes agregar\n nuevos registros",MultiLamp.LEFT,new Circle(40)));
        multiLamp.build(targets);
    }
    private void populateRecycler(final View v){
        insectsList= new ArrayList<>();
        for (int i=0;i<15;i++){
            InsectEntity insectEntity= new InsectEntity();
            insectEntity.setName("INSECTO NO: "+i);
            insectEntity.setInitialDate(510);
            insectEntity.setUmbralS(29);
            insectEntity.setUmbralI(52);
            insectsList.add(insectEntity);
        }
        layoutManager= new GridLayoutManager(v.getContext(),2);
        mAdapter= new RecyclerInsectsAdapter(insectsList,R.layout.rv_item,(insectEntity, position) -> {
            Toast.makeText(getContext(), "Click al insecto con el nombre: "+insectEntity.getName(), Toast.LENGTH_SHORT).show();
        });
        rvInsects.setLayoutManager(layoutManager);
        rvInsects.setAdapter(mAdapter);
    }

}

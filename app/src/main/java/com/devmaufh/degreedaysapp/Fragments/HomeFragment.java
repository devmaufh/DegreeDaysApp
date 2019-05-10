package com.devmaufh.degreedaysapp.Fragments;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.devmaufh.degreedaysapp.Activities.Registro;
import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.MainActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    private FloatingActionButton fabNew;
    private ArrayList<InsectEntity> insectsList;

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        databaseTest();
        bindUI(view);
        fabNew.setOnClickListener(v -> { //Starts Registro activity :D
            startActivity(new Intent(getContext(), Registro.class));
            for(InsectEntity insectEntity: insectsList){
                Log.w("ROOM","\tID: "+insectEntity.getId());
                Log.w("ROOM","\tNombre: "+insectEntity.getName());
                Log.w("ROOM","\tFecha inicio: "+insectEntity.getInitialDate());
                Log.w("ROOM","\tUmbral Superior"+insectEntity.getUmbralS());
                Log.w("ROOM","\tUmbral inferior"+insectEntity.getUmbralI());

            }
        });
        return view;
    }
    private void bindUI(View view) {
        fabNew=(FloatingActionButton)view.findViewById(R.id.frh_fabAdd);
    }
    private DatabaseViewModel mInsectViewModel;
    private void databaseTest(){
        mInsectViewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        mInsectViewModel.getmAllInsects().observe(this, new Observer<List<InsectEntity>>() {
            @Override
            public void onChanged(@Nullable List<InsectEntity> insectEntities) {
                insectsList=new ArrayList<>(insectEntities);
                for(InsectEntity insectEntity: insectEntities){
                }
            }
        });
    }
}

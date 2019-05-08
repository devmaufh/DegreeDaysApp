package com.devmaufh.degreedaysapp.Fragments;
import android.app.DatePickerDialog;
import android.os.Bundle;

import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.MainActivity;
import com.google.android.material.button.MaterialButton;
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

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // UI elements
    private TextInputLayout txtLayoutDate;
    private TextInputEditText txtEdDate;
    private TextInputLayout txtLayoutName;
    private TextInputEditText txtEdName;
    private TextInputLayout txtLayoutUs;
    private TextInputEditText txtEdUs;
    private TextInputLayout txtLayoutUi;
    private TextInputEditText txtEdUi;
    private MaterialButton btnInfo;
    private MaterialButton btnAceptar;
    //Database
    DatabaseViewModel viewModel;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_home, container, false);
        bindUI(view);
        btnInfo.setOnClickListener(v -> {
            Toast.makeText(getContext(),R.string.info,Toast.LENGTH_LONG).show();
        });
        txtEdDate.setShowSoftInputOnFocus(false);
        txtEdDate.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getDate();
                return false;
            }
        });
        btnAceptar.setOnClickListener(v->{
            if(validateField()){
                String name=txtEdName.getText().toString();
                String date=txtEdDate.getText().toString();
                double us=Double.parseDouble(txtEdUs.getText().toString());
                double ui=Double.parseDouble(txtEdUi.getText().toString());
                InsectEntity insect=new InsectEntity();
                insect.setInitialDate(date);
                insect.setName(name);
                insect.setUmbralI(ui);
                insect.setUmbralS(us);
                insert(insect);
            }
        });
        databaseTest();
        return view;
    }
    private void bindUI(View view){
        txtLayoutDate=(TextInputLayout) view.findViewById(R.id.fr_tiLayoutDate);
        txtEdDate=(TextInputEditText)view.findViewById(R.id.fr_edDate);
        txtLayoutName=(TextInputLayout) view.findViewById(R.id.fr_tiLayoutName);
        txtEdName=(TextInputEditText)view.findViewById(R.id.fr_edName);
        txtLayoutUs=(TextInputLayout) view.findViewById(R.id.fr_tiLayoutUS);
        txtEdUs=(TextInputEditText)view.findViewById(R.id.fr_edUS);
        txtLayoutUi=(TextInputLayout) view.findViewById(R.id.fr_tiLayoutUF);
        txtEdUi=(TextInputEditText)view.findViewById(R.id.fr_edUF);
        btnInfo=(MaterialButton)view.findViewById(R.id.fr_ayuda);
        btnAceptar=(MaterialButton)view.findViewById(R.id.fr_aceptar);
    }
    private boolean validateField(){
        if(!TextUtils.isEmpty(txtEdName.getText())&&txtEdName.getText().length()<=40){
            if(!TextUtils.isEmpty(txtEdDate.getText())){
                if(!TextUtils.isEmpty(txtEdUs.getText()) && txtEdUs.getText().length()<=3) {
                    if(!TextUtils.isEmpty(txtEdUi.getText()) && txtEdUi.getText().length()<=3) {
                        return true;
                    }
                    else{
                        txtLayoutUi.setError(getResources().getText(R.string.errorCampoVacio));
                        txtEdUi.requestFocus();
                        return false;
                    }
                }
                else{
                    txtLayoutUs.setError(getResources().getText(R.string.errorCampoVacio));
                    txtEdUs.requestFocus();
                    return false;
                }
            }
            else{
                txtLayoutDate.setError(getResources().getText(R.string.errorCampoVacio));
                return false;
            }
        }
        else{
            txtLayoutName.setError(getResources().getText(R.string.errorCampoVacio));
            txtEdName.requestFocus();
            return false;
        }
    }

    private void getDate(){
        DatePickerDialog DatePicker= new DatePickerDialog(getContext());
        DatePicker.setOnDateSetListener((view, year, month1, dayOfMonth) -> {
            Toast.makeText(getContext(), "DIA: "+dayOfMonth+"\t MES: "+month1+"\t AÑO: "+year, Toast.LENGTH_LONG).show();
            txtEdDate.setText(dayOfMonth+"/"+(month1+1)+"/"+year);
            DatePicker.dismiss();
        });
        DatePicker.show();
    }
    private void insert(InsectEntity insect){
        viewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        viewModel.insertInsect(insect);
    }
    private DatabaseViewModel mInsectViewModel;

    private void databaseTest(){
        mInsectViewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
          mInsectViewModel.getmAllInsects().observe(this, new Observer<List<InsectEntity>>() {
            @Override
            public void onChanged(@Nullable List<InsectEntity> insectEntities) {
                for(InsectEntity insectEntity: insectEntities){
                    Toast.makeText(getContext(), "Testing database :'v "+insectEntity.getName(), Toast.LENGTH_LONG).show();
                    Log.w("ROOM P",insectEntity.getName());
                }
            }
        });
    }
}

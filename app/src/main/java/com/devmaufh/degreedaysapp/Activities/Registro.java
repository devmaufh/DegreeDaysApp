package com.devmaufh.degreedaysapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Registro extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        bindUI();
        setBackButton();
        btnInfo.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(),R.string.info,Toast.LENGTH_LONG).show();
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


    }
    private void bindUI(){
        txtLayoutDate=(TextInputLayout) findViewById(R.id.fr_tiLayoutDate);
        txtEdDate=(TextInputEditText)findViewById(R.id.fr_edDate);
        txtLayoutName=(TextInputLayout)findViewById(R.id.fr_tiLayoutName);
        txtEdName=(TextInputEditText)findViewById(R.id.fr_edName);
        txtLayoutUs=(TextInputLayout)findViewById(R.id.fr_tiLayoutUS);
        txtEdUs=(TextInputEditText)findViewById(R.id.fr_edUS);
        txtLayoutUi=(TextInputLayout)findViewById(R.id.fr_tiLayoutUF);
        txtEdUi=(TextInputEditText)findViewById(R.id.fr_edUF);
        btnInfo=(MaterialButton)findViewById(R.id.fr_ayuda);
        btnAceptar=(MaterialButton)findViewById(R.id.fr_aceptar);
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
        DatePickerDialog DatePicker= new DatePickerDialog(getApplicationContext());
        DatePicker.setOnDateSetListener((view, year, month1, dayOfMonth) -> {
            Toast.makeText(getApplicationContext(), "DIA: "+dayOfMonth+"\t MES: "+month1+"\t AÑO: "+year, Toast.LENGTH_LONG).show();
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
                    Toast.makeText(getApplicationContext(), "Testing database :'v "+insectEntity.getName(), Toast.LENGTH_LONG).show();
                    Log.w("ROOM P",insectEntity.getName());
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void setBackButton(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
        getSupportActionBar().setTitle(R.string.Registrar);
    }
}

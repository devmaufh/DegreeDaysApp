package com.devmaufh.degreedaysapp.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.Database.DataRepository;
import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
public class Registro extends AppCompatActivity  {
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
    int valor=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        bindUI();
        setBackButton();
        btnInfo.setOnClickListener(v -> {
            Toast.makeText(Registro.this,R.string.info,Toast.LENGTH_LONG).show();
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
        Calendar now=Calendar.getInstance();

        com.wdullaer.materialdatetimepicker.date.DatePickerDialog dpd= com.wdullaer.materialdatetimepicker.date.DatePickerDialog
                .newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String date = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                        checkIfExist(date);

                    }
                },
                        now.get(Calendar.YEAR), // Initial year selection
                        now.get(Calendar.MONTH), // Initial month selection
                        now.get(Calendar.DAY_OF_MONTH) // Inital day selection
                );
        dpd.setTitle("Selecciona una fecha");
        dpd.setVersion(com.wdullaer.materialdatetimepicker.date.DatePickerDialog.Version.VERSION_2 );
        dpd.setMaxDate(now);
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimaryDark));
        dpd.show(getSupportFragmentManager(),"DatePickerDialog");
    }
    private void insert(InsectEntity insect){
        viewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        viewModel.insertInsect(insect);
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
    private void checkIfExist(String date){
        viewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        Toast.makeText(this, date, Toast.LENGTH_SHORT).show();
        viewModel.getDateByDate(date, new DataRepository.SelectDateByDate.AsyncResponse() {
            @Override
            public void response(int output) {
                if(output==0){
                    Toast.makeText(Registro.this, "Esta fecha no tiene registros de temperatura, selecciona otra", Toast.LENGTH_SHORT).show();
                }else{
                    txtEdDate.setText(date);
                }
            }
        });
    }

}

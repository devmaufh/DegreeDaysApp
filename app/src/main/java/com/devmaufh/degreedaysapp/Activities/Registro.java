package com.devmaufh.degreedaysapp.Activities;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.Database.DataRepository;
import com.devmaufh.degreedaysapp.Database.DatabaseViewModel;
import com.devmaufh.degreedaysapp.Entities.DatesEntity;
import com.devmaufh.degreedaysapp.Entities.InsectEntity;
import com.devmaufh.degreedaysapp.R;
import com.devmaufh.degreedaysapp.Utilities.AdditionalMethods;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
public class Registro extends AppCompatActivity  {
    // UI elements
    private SharedPreferences preferences;

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
        preferences=getSharedPreferences(AdditionalMethods.PREFERENCES_THEME,MODE_PRIVATE);
        setTheme(AdditionalMethods.getTheme(preferences));

        if(TextUtils.isEmpty(preferences.getString("theme",""))){
            Toast.makeText(this, "TEMA VACIO", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "EL TEMA ES: "+preferences.getString("theme",""), Toast.LENGTH_SHORT).show();
        }

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
                if(!checkTemps(us,ui)) {
                    InsectEntity insect = new InsectEntity();
                    insect.setInitialDate(txtEdDate.getId());
                    insect.setName(name);
                    insect.setUmbralI(ui);
                    insect.setUmbralS(us);
                    insert(insect);
                }else{
                    txtEdUs.requestFocus();
                    Toast.makeText(this, getResources().getString(R.string.errorUmbralSupUmbralInf), Toast.LENGTH_SHORT).show();
                }
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
        dpd.setVersion(DatePickerDialog.Version.VERSION_1 );
        dpd.setMaxDate(now);
        dpd.show(getSupportFragmentManager(),"DatePickerDialog");
    }
    private void insert(InsectEntity insect){
        viewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        viewModel.insertInsect(insect);
        finish();
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
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.close);
        getSupportActionBar().setTitle(R.string.Registrar);
    }
    private void checkIfExist(String date){
        viewModel= ViewModelProviders.of(this).get(DatabaseViewModel.class);
        viewModel.getDateByDate2(date, new DataRepository.GetDateByDate.AsyncDate() {
            @Override
            public void response(DatesEntity entity) {
                txtEdDate.setText(date);
                txtEdDate.setId(entity.getId());
            }
            @Override
            public void error(String error) {
                Toast.makeText(Registro.this, getResources().getString(R.string.sinRegistros)+" de temperatura para esta fecha, selecciona otra", Toast.LENGTH_LONG).show();
            }
        });
    }
    private boolean checkTemps(double x, double y){
        return y>x;
    }
}

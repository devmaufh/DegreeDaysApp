package com.devmaufh.degreedaysapp.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import com.devmaufh.degreedaysapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

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

    private boolean checkTemps(double x, double y){
        return y>x;
    }
}

package com.devmaufh.degreedaysapp.Fragments;
import android.app.DatePickerDialog;
import android.os.Bundle;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.devmaufh.degreedaysapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {
    // UI elements
    private TextInputLayout txtLayoutDate;
    private TextInputEditText txtEdDate;
    private MaterialButton btnInfo;
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
        return view;
    }
    private void bindUI(View view){
        txtLayoutDate=(TextInputLayout) view.findViewById(R.id.fr_tiLayoutDate);
        txtEdDate=(TextInputEditText)view.findViewById(R.id.fr_edDate);
        btnInfo=(MaterialButton)view.findViewById(R.id.fr_ayuda);
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

}

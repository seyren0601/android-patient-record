package com.example.patientrecord;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Them_BuoiKham extends AppCompatActivity {
    ArrayList<String> listBenh;
    ArrayList<Benh> arraylist_benh;
    ArrayList<Thuoc> arraylist_thuoc;
    Benh benh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_buoikham);
        EditText edt_icd = findViewById(R.id.add_buoikham_icd);
        edt_icd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String icd = ((AutoCompleteTextView)findViewById(R.id.add_buoikham_icd)).getText().toString();
                    for(Benh val:arraylist_benh){
                        if(val.mA_BENH.equals(icd)){
                            benh = val;
                            break;
                        }
                    }
                    if(benh != null) ((TextView)findViewById(R.id.add_buoikham_tenbenh)).setText(benh.teN_BENH);
                }
            }
        });

        EditText edt_cannang = findViewById(R.id.add_buoikham_cannang);
        EditText edt_chieucao = findViewById(R.id.add_buoikham_chieucao);

        edt_cannang.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CalculateBMI();
                }
            }
        });
        edt_chieucao.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    CalculateBMI();
                }
            }
        });

        EditText edt_NgayTaiKham = findViewById(R.id.add_buoikham_ngaytaikham);

        edt_NgayTaiKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay_dialog();
            }
        });
        edt_NgayTaiKham.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus) ChonNgay_dialog();
            }
        });

        CallAPI_LoadBenh();
        CallAPI_LoadThuoc();
    }

    public void ChonNgay_dialog(){
        Calendar calendar = Calendar.getInstance();
        int ngay = Integer.valueOf(calendar.getTime().getDate());
        int thang = Integer.valueOf(calendar.getTime().getMonth());
        int nam = Integer.valueOf(calendar.getTime().getYear());

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                EditText edt_NgayTaiKham = findViewById(R.id.add_buoikham_ngaytaikham);
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                edt_NgayTaiKham.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    void CalculateBMI(){
        EditText edt_cannang = findViewById(R.id.add_buoikham_cannang);
        EditText edt_chieucao = findViewById(R.id.add_buoikham_chieucao);
        EditText edt_bmi = findViewById(R.id.add_buoikham_bmi);
        if(!TextUtils.isEmpty(edt_cannang.getText().toString()) && !TextUtils.isEmpty(edt_chieucao.getText().toString()) ){
            String input_cannang = edt_cannang.getText().toString();
            input_cannang = input_cannang.replace(',','.');
            float cannang = Float.parseFloat(input_cannang);

            String input_chieucao = edt_chieucao.getText().toString();
            input_chieucao = input_chieucao.replace(',','.');
            float chieucao = Float.parseFloat(input_chieucao);

            float bmi = (float)(cannang / Math.pow(chieucao,2));

            edt_bmi.setText((String.format("%.2f", bmi)));
        }

    }

    void CallAPI_LoadBenh(){
        APIService.apiservice.GetAllBenh().enqueue(new Callback<ArrayList<Benh>>() {
            @Override
            public void onResponse(Call<ArrayList<Benh>> call, Response<ArrayList<Benh>> response) {
                if(response.isSuccessful()){
                    listBenh = new ArrayList<String>();
                    ArrayList<Benh> list_response = response.body();
                    arraylist_benh = list_response;
                    for(Benh benh:list_response){
                        listBenh.add(benh.mA_BENH);
                    }
                    AutoCompleteTextView autoText = findViewById(R.id.add_buoikham_icd);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(Activity_Them_BuoiKham.this,
                            android.R.layout.simple_dropdown_item_1line, listBenh);
                    autoText.setAdapter(adapter);
                    autoText.setThreshold(2);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Benh>> call, Throwable t) {

            }
        });
    }

    void CallAPI_LoadThuoc(){
        APIService.apiservice.GetAllThuoc().enqueue(new Callback<ArrayList<Thuoc>>() {
            @Override
            public void onResponse(Call<ArrayList<Thuoc>> call, Response<ArrayList<Thuoc>> response) {
                if(response.isSuccessful()){
                    arraylist_thuoc = response.body();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Thuoc>> call, Throwable t) {

            }
        });
    }
}

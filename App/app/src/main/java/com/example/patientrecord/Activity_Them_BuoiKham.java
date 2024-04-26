package com.example.patientrecord;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patientrecord.Classes.Benh;
import com.example.patientrecord.Classes.Thuoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Them_BuoiKham extends AppCompatActivity {
    ArrayList<String> listBenh;
    ArrayList<Benh> arraylist_benh;
    Benh benh;
    ArrayList<Thuoc> arraylist_thuoc;
    ArrayList<String> listTenThuoc;
    ArrayList<String> listHoatChat;
    Thuoc thuoc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_buoikham);

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

        ImageButton btn_them_lieuthuoc = findViewById(R.id.btn_them_lieuthuoc);
        btn_them_lieuthuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_Them_LieuThuoc();
            }
        });

        CallAPI_LoadBenh();
        CallAPI_LoadThuoc();
    }

    void Dialog_Them_LieuThuoc(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_lieuthuoc);

        TextView text_mathuoc = dialog.findViewById(R.id.dialog_lieuthuoc_mathuoc);
        text_mathuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(thuoc != null){
                    Intent viewIntent = new Intent("android.intent.action.VIEW",
                            Uri.parse("https://drugbank.vn/thuoc/" + thuoc.teN_THUOC + "&" + thuoc.id));
                    startActivity(viewIntent);
                }
            }
        });

        NumberPicker sang = dialog.findViewById(R.id.dialog_lieuthuoc_sang);
        sang.setMinValue(0);
        sang.setMaxValue(10);
        NumberPicker trua = dialog.findViewById(R.id.dialog_lieuthuoc_trua);
        trua.setMinValue(0);
        trua.setMaxValue(10);
        NumberPicker chieu = dialog.findViewById(R.id.dialog_lieuthuoc_chieu);
        chieu.setMinValue(0);
        chieu.setMaxValue(10);
        NumberPicker toi = dialog.findViewById(R.id.dialog_lieuthuoc_toi);
        toi.setMinValue(0);
        toi.setMaxValue(10);

        AutoCompleteTextView autoText_TenThuoc = dialog.findViewById(R.id.dialog_lieuthuoc_tenthuoc);
        AutoCompleteTextView autoText_HoatChat = dialog.findViewById(R.id.dialog_lieuthuoc_hoatchat);

        ArrayAdapter<String> adapter_tenthuoc = new ArrayAdapter<String>(dialog.getContext(),
                android.R.layout.simple_dropdown_item_1line, listTenThuoc);
        autoText_TenThuoc.setAdapter(adapter_tenthuoc);
        autoText_TenThuoc.setThreshold(3);

        ArrayAdapter<String> adapter_hoatchat = new ArrayAdapter<String>(dialog.getContext(),
                android.R.layout.simple_dropdown_item_1line, listHoatChat);
        autoText_HoatChat.setAdapter(adapter_hoatchat);
        autoText_HoatChat.setThreshold(3);

        autoText_TenThuoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String TenThuoc = autoText_TenThuoc.getText().toString();
                for(Thuoc val:arraylist_thuoc){
                    if(val.teN_THUOC.equals(TenThuoc)){
                        thuoc = val;
                        break;
                    }
                }
                String thuoc_id = thuoc.id;
                SpannableString content = new SpannableString(thuoc_id);
                content.setSpan(new UnderlineSpan(), 0, thuoc_id.length(), 0);
                text_mathuoc.setText(content);
                autoText_HoatChat.setText(thuoc.hoaT_CHAT);
            }
        });
        autoText_HoatChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String HoatChat = autoText_HoatChat.getText().toString();
                for(Thuoc val:arraylist_thuoc){
                    if(val.hoaT_CHAT.equals(HoatChat)){
                        thuoc = val;
                        break;
                    }
                }
                String thuoc_id = thuoc.id;
                SpannableString content = new SpannableString(thuoc_id);
                content.setSpan(new UnderlineSpan(), 0, thuoc_id.length(), 0);
                text_mathuoc.setText(content);
                autoText_TenThuoc.setText(thuoc.teN_THUOC);
            }
        });


        dialog.show();
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
                    autoText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String icd = ((AutoCompleteTextView)findViewById(R.id.add_buoikham_icd)).getText().toString();
                            for(Benh val:arraylist_benh){
                                if(val.mA_BENH.equals(icd)){
                                    benh = val;
                                    break;
                                }
                            }
                            TextView view_tenbenh = findViewById(R.id.add_buoikham_tenbenh);
                            if(benh != null) {
                                String ten_benh = benh.teN_BENH;
                                SpannableString content = new SpannableString(ten_benh);
                                content.setSpan(new UnderlineSpan(), 0, ten_benh.length(), 0);
                                view_tenbenh.setText(content);
                                view_tenbenh.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent viewIntent = new Intent("android.intent.action.VIEW",
                                                Uri.parse("https://icd.kcb.vn/#/search/query-global?q=" + benh.mA_BENH));
                                        startActivity(viewIntent);
                                    }
                                });
                            }
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Benh>> call, Throwable t) {
                Toast.makeText(Activity_Them_BuoiKham.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void CallAPI_LoadThuoc(){
        APIService.apiservice.GetAllThuoc().enqueue(new Callback<ArrayList<Thuoc>>() {
            @Override
            public void onResponse(Call<ArrayList<Thuoc>> call, Response<ArrayList<Thuoc>> response) {
                if(response.isSuccessful()){
                    arraylist_thuoc = response.body();
                    listTenThuoc = new ArrayList<String>();
                    listHoatChat = new ArrayList<String>();
                    for(Thuoc thuoc:arraylist_thuoc){
                        listTenThuoc.add(thuoc.teN_THUOC);
                        listHoatChat.add(thuoc.hoaT_CHAT);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Thuoc>> call, Throwable t) {

            }
        });
    }
}

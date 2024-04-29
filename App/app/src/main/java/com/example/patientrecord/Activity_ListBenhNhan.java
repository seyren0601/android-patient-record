package com.example.patientrecord;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patientrecord.Adapters.BenhNhanAdapter;
import com.example.patientrecord.Classes.BenhNhan;

import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_ListBenhNhan extends AppCompatActivity {
    ListView listview_benhnhan;
    ArrayList<BenhNhan> arraylist_benhnhan;
    ArrayList<String> list_tenbenhnhan;
    BenhNhanAdapter adapter_benhnhan;
    AutoCompleteTextView auto_text;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_list_benhnhan);
        setTitle("Danh sách bệnh nhân");

        listview_benhnhan = (ListView)findViewById(R.id.ListView_BenhNhan);
        arraylist_benhnhan = new ArrayList<BenhNhan>();
        list_tenbenhnhan = new ArrayList<String>();
        adapter_benhnhan = new BenhNhanAdapter(this, R.layout.layout_viewitem, arraylist_benhnhan);
        listview_benhnhan.setAdapter(adapter_benhnhan);

        auto_text = (AutoCompleteTextView)findViewById(R.id.list_benhnhan_autocomplete);
        auto_text.setThreshold(2);
        ArrayAdapter<String> adapter_tenbenhnhan = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, list_tenbenhnhan);
        auto_text.setAdapter(adapter_tenbenhnhan);

        auto_text.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String query = auto_text.getText().toString();
                CallAPI_Find(query);
            }
        });
        auto_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString() == ""){
                    CallAPI_Load();
                }
                else{

                }
            }
        });

        listview_benhnhan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_ListBenhNhan.this, Activity_BenhNhan.class);
                BenhNhan selectedItem = arraylist_benhnhan.get(position);
                intent.putExtra("BenhNhan", selectedItem);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        CallAPI_Load();
    }

    public void CallAPI_Load(){
        APIService.apiservice.getData().enqueue(new Callback<List<BenhNhan>>(){
            @Override
            public void onResponse(Call<List<BenhNhan>> call, Response<List<BenhNhan>> response){
                if (response.isSuccessful()) {
                    List<BenhNhan> lists = response.body();
                    arraylist_benhnhan.clear();
                    list_tenbenhnhan.clear();
                    if(lists != null){
                        for(BenhNhan benhnhan:lists){
                            String dob_string = benhnhan.ngaY_SINH;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date dob = new Date();
                            try {
                                dob = format.parse(dob_string);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            benhnhan.ngaY_SINH = format.format(dob);
                            arraylist_benhnhan.add(benhnhan);
                            list_tenbenhnhan.add(benhnhan.ten);
                        }
                    }
                    Toast.makeText(Activity_ListBenhNhan.this, "Load danh sách thành công", Toast.LENGTH_SHORT).show();
                    adapter_benhnhan.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(Activity_ListBenhNhan.this, "Load danh sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<BenhNhan>> call, Throwable t){
                Toast.makeText(Activity_ListBenhNhan.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void CallAPI_Find(String Query){
        APIService.apiservice.FindBenhNhan(Query).enqueue(new Callback<List<BenhNhan>>(){
            @Override
            public void onResponse(Call<List<BenhNhan>> call, Response<List<BenhNhan>> response){
                if (response.isSuccessful()) {
                    List<BenhNhan> lists = response.body();
                    arraylist_benhnhan.clear();
                    list_tenbenhnhan.clear();
                    if(lists != null){
                        for(BenhNhan benhnhan:lists){
                            String dob_string = benhnhan.ngaY_SINH;
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                            Date dob = new Date();
                            try {
                                dob = format.parse(dob_string);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            benhnhan.ngaY_SINH = format.format(dob);
                            arraylist_benhnhan.add(benhnhan);
                            list_tenbenhnhan.add(benhnhan.ten);
                        }
                    }
                    Toast.makeText(Activity_ListBenhNhan.this, "Load danh sách thành công", Toast.LENGTH_SHORT).show();
                    adapter_benhnhan.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(Activity_ListBenhNhan.this, "Load danh sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<BenhNhan>> call, Throwable t){
                Toast.makeText(Activity_ListBenhNhan.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

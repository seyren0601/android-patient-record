package com.example.patientrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.patientrecord.Adapters.BuoiKhamAdapter;
import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.Classes.BuoiKham;

import java.util.ArrayList;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_LichSuKham extends AppCompatActivity {
    BenhNhan benhNhan;
    ListView listview_buoikham;
    ArrayList<BuoiKham> list_buoikham;
    BuoiKhamAdapter adapter_buoikham;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lichsukham);

        Intent intent = getIntent();
        benhNhan = (BenhNhan)intent.getSerializableExtra("BenhNhan");
        setTitle("Lịch sử khám [" + benhNhan.ten + "]");

        listview_buoikham = findViewById(R.id.lichsukham_buoikham);
        list_buoikham = new ArrayList<BuoiKham>();
        adapter_buoikham = new BuoiKhamAdapter(this, R.layout.layout_viewitem_buoikham, list_buoikham);
        listview_buoikham.setAdapter(adapter_buoikham);

        CallAPI_LoadBuoiKham();

        listview_buoikham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BuoiKham buoiKham = list_buoikham.get(position);
                Intent intent = new Intent(Activity_LichSuKham.this, Activity_BuoiKham.class);
                intent.putExtra("BenhNhan", benhNhan);
                intent.putExtra("BuoiKham", buoiKham);
                startActivity(intent);
            }
        });
    }

    void CallAPI_LoadBuoiKham(){
        APIService.apiservice.GetAllBuoiKham(benhNhan.id).enqueue(new Callback<ArrayList<BuoiKham>>() {
            @Override
            public void onResponse(Call<ArrayList<BuoiKham>> call, Response<ArrayList<BuoiKham>> response) {
                if(response.isSuccessful()){
                    for(BuoiKham buoiKham: response.body()){
                        list_buoikham.add(buoiKham);
                    }
                    adapter_buoikham.notifyDataSetChanged();
                }
                Toast.makeText(Activity_LichSuKham.this, response.message(), Toast.LENGTH_LONG).show();
            }
            @Override
            public void onFailure(Call<ArrayList<BuoiKham>> call, Throwable t) {
                Toast.makeText(Activity_LichSuKham.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

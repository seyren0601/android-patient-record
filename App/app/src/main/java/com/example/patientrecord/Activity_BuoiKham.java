package com.example.patientrecord;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.patientrecord.Adapters.DonThuocAdapter;
import com.example.patientrecord.Classes.Benh;
import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.Classes.BuoiKham;
import com.example.patientrecord.Classes.LieuThuoc;
import com.example.patientrecord.Classes.Thuoc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_BuoiKham extends AppCompatActivity {
    BenhNhan benhNhan;
    BuoiKham buoiKham;
    DonThuocAdapter adapter;
    ListView listview;
    ArrayList<LieuThuoc> list_lieuthuoc;
    Benh benh;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_buoikham);
        setTitle("Buổi khám");

        list_lieuthuoc = new ArrayList<LieuThuoc>();

        listview = findViewById(R.id.add_buoikham_toathuoc);
        adapter = new DonThuocAdapter(this, R.layout.layout_viewitem_toathuoc_readonly, list_lieuthuoc);
        listview.setAdapter(adapter);

        Intent intent = getIntent();
        benhNhan = (BenhNhan)intent.getSerializableExtra("BenhNhan");
        ((TextView)findViewById(R.id.add_buoikham_ten)).setText(benhNhan.ten);
        if(benhNhan.gioI_TINH) ((TextView)findViewById(R.id.add_buoikham_gioitinh)).setText("Nam");
        else ((TextView)findViewById(R.id.add_buoikham_gioitinh)).setText("Nữ");
        ((TextView)findViewById(R.id.add_buoikham_tuoi)).setText(benhNhan.tuoi + " tuổi");

        buoiKham = (BuoiKham)intent.getSerializableExtra("BuoiKham");
        CallAPI_LoadBenh();

        ((EditText)findViewById(R.id.add_buoikham_lido)).setText(buoiKham.lY_DO_DEN_KHAM);
        ((EditText)findViewById(R.id.add_buoikham_lido)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_mach)).setText(String.valueOf(buoiKham.mach));
        ((EditText)findViewById(R.id.add_buoikham_mach)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_nhiptho)).setText(String.valueOf(buoiKham.nhiP_THO));
        ((EditText)findViewById(R.id.add_buoikham_nhiptho)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_huyetap)).setText(String.valueOf(buoiKham.huyeT_AP));
        ((EditText)findViewById(R.id.add_buoikham_huyetap)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_chieucao)).setText(String.valueOf(buoiKham.chieU_CAO));
        ((EditText)findViewById(R.id.add_buoikham_chieucao)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_cannang)).setText(String.valueOf(buoiKham.caN_NANG));
        ((EditText)findViewById(R.id.add_buoikham_cannang)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_bmi)).setText(String.valueOf(buoiKham.bmi));
        ((EditText)findViewById(R.id.add_buoikham_bmi)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_nhietdo)).setText(String.valueOf(buoiKham.thaN_NHIET));
        ((EditText)findViewById(R.id.add_buoikham_nhietdo)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_trieuchung)).setText(buoiKham.trieU_CHUNG_LS);
        ((EditText)findViewById(R.id.add_buoikham_trieuchung)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_benhphu)).setText(buoiKham.icD_BENH_PHU);
        ((EditText)findViewById(R.id.add_buoikham_benhphu)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_loidan)).setText(buoiKham.loI_DAN_KHAM);
        ((EditText)findViewById(R.id.add_buoikham_loidan)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_ketqua)).setText(buoiKham.keT_QUA_DIEU_TRI);
        ((EditText)findViewById(R.id.add_buoikham_ketqua)).setEnabled(false);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        try{
            date = simpleDateFormat.parse(buoiKham.ngaY_TAI_KHAM);
        }
        catch(ParseException e){

        }
        ((EditText)findViewById(R.id.add_buoikham_ngaytaikham)).setText(simpleDateFormat.format(date));
        ((EditText)findViewById(R.id.add_buoikham_ngaytaikham)).setEnabled(false);

        ((EditText)findViewById(R.id.add_buoikham_songay)).setText(String.valueOf(buoiKham.sO_NGAY_LAY_THUOC) + " ngày");
        ((EditText)findViewById(R.id.add_buoikham_songay)).setEnabled(false);

        CallAPI_LoadLieuThuoc();

        findViewById(R.id.btn_them_lieuthuoc).setVisibility(View.GONE);
        ((AppCompatButton)findViewById(R.id.add_buoikham_btn_them)).setText("Thoát");
        ((AppCompatButton)findViewById(R.id.add_buoikham_btn_them)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView view_tenbenh = findViewById(R.id.add_buoikham_tenbenh);
        view_tenbenh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW",
                        Uri.parse("https://icd.kcb.vn/#/search/query-global?q=" + benh.mA_BENH));
                startActivity(viewIntent);
            }
        });
    }

    void UpdateToView(){
        ((EditText)findViewById(R.id.add_buoikham_icd)).setText(buoiKham.icd);
    }

    void CallAPI_LoadBenh(){
        APIService.apiservice.GetBenh(buoiKham.icd).enqueue(new Callback<Benh>() {
            @Override
            public void onResponse(Call<Benh> call, Response<Benh> response) {
                benh = response.body();
                ((EditText)findViewById(R.id.add_buoikham_icd)).setText(benh.mA_BENH);
                ((EditText)findViewById(R.id.add_buoikham_icd)).setEnabled(false);

                ((TextView)findViewById(R.id.add_buoikham_tenbenh)).setText(benh.teN_BENH);
            }

            @Override
            public void onFailure(Call<Benh> call, Throwable t) {

            }
        });
    }

    void CallAPI_LoadLieuThuoc(){
        APIService.apiservice.GetLieuThuoc(buoiKham.id).enqueue(new Callback<ArrayList<LieuThuoc>>() {
            @Override
            public void onResponse(Call<ArrayList<LieuThuoc>> call, Response<ArrayList<LieuThuoc>> response) {
                if(response.isSuccessful()){
                    ArrayList<LieuThuoc> list_response = response.body();
                    for(LieuThuoc lieuThuoc:list_response){
                        list_lieuthuoc.add(lieuThuoc);
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<LieuThuoc>> call, Throwable t) {

            }
        });
    }
}

package com.example.patientrecord;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Details_BuoiKhamActivity extends AppCompatActivity {
    //ListView listview_buoikham;
    //ArrayList<BuoiKham> arraylist_buoikham;
    //BuoiKhamAdapter adapter_buoikham;
    BenhNhan benhNhan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_benhnhan);

        Intent intent = getIntent();
        benhNhan = (BenhNhan)intent.getSerializableExtra("BenhNhan");
        UpdateToView();

        Button btn_saveChanges = (Button)findViewById(R.id.details_btn_SaveChanges);
        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                benhNhan.ten = ((EditText)findViewById(R.id.details_edtHoTen)).getText().toString();
                benhNhan.bhyt = ((EditText)findViewById(R.id.details_edtBHYT)).getText().toString();
                benhNhan.ngaY_SINH = ((EditText)findViewById(R.id.details_edtNgaySinh)).getText().toString();
                benhNhan.sdt = ((EditText)findViewById(R.id.details_edtPhone)).getText().toString();
                benhNhan.nghE_NGHIEP = ((EditText)findViewById(R.id.details_edtNgheNghiep)).getText().toString();
                if( ((RadioGroup)findViewById(R.id.details_grpGioiTinh)).getCheckedRadioButtonId() == R.id.details_rdNam)
                    benhNhan.gioI_TINH = Boolean.TRUE;
                else benhNhan.gioI_TINH = Boolean.FALSE;
                CallAPI_Update(benhNhan);
            }
        });
        //listview_benhnhan = (ListView)findViewById(R.id.ListView_BenhNhan);
        //arraylist_benhnhan = new ArrayList<>();
        //adapter_benhnhan = new BenhNhanAdapter(this, R.layout.layout_viewitem, arraylist_benhnhan);
        //listview_benhnhan.setAdapter(adapter_benhnhan);

        //CallAPI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_buoikham, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuAdd_BuoiKham){
            //DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    void UpdateToView() {
        EditText details_hoten = findViewById(R.id.details_edtHoTen);
        details_hoten.setText(benhNhan.ten);

        EditText details_bhyt = findViewById(R.id.details_edtBHYT);
        if(benhNhan.bhyt != null) details_bhyt.setText(benhNhan.bhyt);

        EditText details_ngaysinh = findViewById(R.id.details_edtNgaySinh);
        details_ngaysinh.setText(benhNhan.ngaY_SINH);

        EditText details_tuoi = findViewById(R.id.details_edtTuoi);
        details_tuoi.setText(String.valueOf(benhNhan.tuoi));

        RadioGroup details_gioitinh = findViewById(R.id.details_grpGioiTinh);
        if(benhNhan.gioI_TINH) details_gioitinh.check(R.id.details_rdNam);
        else details_gioitinh.check(R.id.details_rdNu);

        EditText details_sdt = findViewById(R.id.details_edtPhone);
        if(benhNhan.sdt != null) details_sdt.setText(benhNhan.sdt);

        EditText details_nghenghiep = findViewById(R.id.details_edtNgheNghiep);
        if(benhNhan.nghE_NGHIEP != null) details_nghenghiep.setText(benhNhan.nghE_NGHIEP);
    }

    void CallAPI_Update(BenhNhan benhnhan){
        APIService.apiservice.updateBenhNhan(benhnhan).enqueue(new Callback<BenhNhan>() {
            @Override
            public void onResponse(Call<BenhNhan> call, Response<BenhNhan> response) {
                if(response.isSuccessful()){
                    benhNhan = response.body();
                    String dob_string = benhNhan.ngaY_SINH;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Date dob = new Date();
                    try {
                        dob = format.parse(dob_string);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                    benhNhan.ngaY_SINH = format.format(dob);
                    UpdateToView();
                    Toast.makeText(Details_BuoiKhamActivity.this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Details_BuoiKhamActivity.this, "ERROR1", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BenhNhan> call, Throwable t) {
                Toast.makeText(Details_BuoiKhamActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

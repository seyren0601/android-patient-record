package com.example.patientrecord;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_BenhNhan extends AppCompatActivity {
    BenhNhan benhNhan;
    ListView listview_buoikham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_benhnhan);

        setTitle("HỒ SƠ");

        Intent intent = getIntent();
        benhNhan = (BenhNhan)intent.getSerializableExtra("BenhNhan");
        listview_buoikham = findViewById(R.id.chitiet_list_buoikham);
        UpdateToView();

        EditText edt_NgaySinh = findViewById(R.id.chitiet_edit_ngaysinh);
        edt_NgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChonNgay_dialog();
            }
        });
        edt_NgaySinh.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus) ChonNgay_dialog();
            }
        });

        AppCompatButton btn_edit = findViewById(R.id.btn_edit_benhnhan);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bhyt = ((EditText)findViewById(R.id.chitiet_edit_bhyt)).getText().toString();
                if(!TextUtils.isEmpty(bhyt)) benhNhan.bhyt = bhyt;
                else benhNhan.bhyt = null;

                EditText edit_ngaysinh = findViewById(R.id.chitiet_edit_ngaysinh);
                benhNhan.ngaY_SINH = edit_ngaysinh.getText().toString();

                String sdt = ((EditText)findViewById(R.id.chitiet_edit_sdt)).getText().toString();
                if(!TextUtils.isEmpty(sdt)) benhNhan.sdt = sdt;
                else benhNhan.sdt = null;

                String cccd = ((EditText)findViewById(R.id.chitiet_edit_cccd)).getText().toString();
                if(!TextUtils.isEmpty(cccd)) benhNhan.cccd = cccd;
                else benhNhan.cccd = null;

                String diachi = ((EditText)findViewById(R.id.chitiet_edit_diachi)).getText().toString();
                if(!TextUtils.isEmpty(diachi)) benhNhan.diA_CHI = diachi;
                else benhNhan.diA_CHI = null;

                String nghenghiep = ((EditText)findViewById(R.id.chitiet_edit_nghenghiep)).getText().toString();
                if(!TextUtils.isEmpty(nghenghiep)) benhNhan.nghE_NGHIEP = nghenghiep;
                else benhNhan.nghE_NGHIEP = null;

                CallAPI_Update(benhNhan);
                OutEdit();
            }
        });
    }

    public void ChonNgay_dialog(){
        Calendar calendar = Calendar.getInstance();
        int ngay = Integer.valueOf(benhNhan.ngaY_SINH.substring(8, 10));
        int thang = Integer.valueOf(benhNhan.ngaY_SINH.substring(5, 7)) - 1;
        int nam = Integer.valueOf(benhNhan.ngaY_SINH.substring(0, 4));

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                EditText edt_NgaySinh = findViewById(R.id.chitiet_edit_ngaysinh);
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                edt_NgaySinh.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuEdit_BenhNhan){
            ToEdit();
        }
        return super.onOptionsItemSelected(item);
    }

    void ToEdit(){
        AppCompatButton btn_edit = findViewById(R.id.btn_edit_benhnhan);
        AppCompatButton btn_them_buoikham = findViewById(R.id.btn_them_buoikham);
        btn_edit.setVisibility(View.VISIBLE);
        btn_them_buoikham.setVisibility(View.GONE);

        findViewById(R.id.chitiet_bhyt).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_bhyt).setVisibility(View.VISIBLE);
        if(benhNhan.bhyt != null) ((EditText)findViewById(R.id.chitiet_edit_bhyt)).setText(benhNhan.bhyt);

        findViewById(R.id.chitiet_ngaysinh).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_ngaysinh).setVisibility(View.VISIBLE);
        ((EditText)findViewById(R.id.chitiet_edit_ngaysinh)).setText(benhNhan.ngaY_SINH);

        findViewById(R.id.chitiet_sdt).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_sdt).setVisibility(View.VISIBLE);
        if(benhNhan.sdt != null) ((EditText)findViewById(R.id.chitiet_edit_sdt)).setText(benhNhan.sdt);

        findViewById(R.id.chitiet_cccd).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_cccd).setVisibility(View.VISIBLE);
        if(benhNhan.cccd != null) ((EditText)findViewById(R.id.chitiet_edit_cccd)).setText(benhNhan.cccd);

        findViewById(R.id.chitiet_diachi).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_diachi).setVisibility(View.VISIBLE);
        if(benhNhan.diA_CHI!= null) ((EditText)findViewById(R.id.chitiet_edit_diachi)).setText(benhNhan.diA_CHI);

        findViewById(R.id.chitiet_nghenghiep).setVisibility(View.GONE);
        findViewById(R.id.chitiet_edit_nghenghiep).setVisibility(View.VISIBLE);
        if(benhNhan.nghE_NGHIEP != null) ((EditText)findViewById(R.id.chitiet_edit_nghenghiep)).setText(benhNhan.nghE_NGHIEP);

        listview_buoikham.setVisibility(View.GONE);
    }

    void OutEdit(){
        AppCompatButton btn_edit = findViewById(R.id.btn_edit_benhnhan);
        AppCompatButton btn_them_buoikham = findViewById(R.id.btn_them_buoikham);
        btn_edit.setVisibility(View.GONE);
        btn_them_buoikham.setVisibility(View.VISIBLE);

        findViewById(R.id.chitiet_bhyt).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_bhyt).setVisibility(View.GONE);
        if(benhNhan.bhyt != null) ((TextView)findViewById(R.id.chitiet_bhyt)).setText(benhNhan.bhyt);

        findViewById(R.id.chitiet_ngaysinh).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_ngaysinh).setVisibility(View.GONE);
        ((TextView)findViewById(R.id.chitiet_ngaysinh)).setText(benhNhan.ngaY_SINH);

        findViewById(R.id.chitiet_sdt).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_sdt).setVisibility(View.GONE);
        if(benhNhan.sdt != null) ((TextView)findViewById(R.id.chitiet_sdt)).setText(benhNhan.sdt);

        findViewById(R.id.chitiet_cccd).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_cccd).setVisibility(View.GONE);
        if(benhNhan.cccd != null) ((TextView)findViewById(R.id.chitiet_cccd)).setText(benhNhan.cccd);

        findViewById(R.id.chitiet_diachi).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_diachi).setVisibility(View.GONE);
        if(benhNhan.diA_CHI != null) ((TextView)findViewById(R.id.chitiet_diachi)).setText(benhNhan.diA_CHI);

        findViewById(R.id.chitiet_nghenghiep).setVisibility(View.VISIBLE);
        findViewById(R.id.chitiet_edit_nghenghiep).setVisibility(View.GONE);
        if(benhNhan.nghE_NGHIEP != null) ((TextView)findViewById(R.id.chitiet_nghenghiep)).setText(benhNhan.nghE_NGHIEP);

        listview_buoikham.setVisibility(View.VISIBLE);
    }

    void UpdateToView() {
        TextView hoten = findViewById(R.id.chitiet_ten);
        hoten.setText(benhNhan.ten);

        TextView gioitinh = findViewById(R.id.chitiet_gioitinh);
        if(benhNhan.gioI_TINH) gioitinh.setText("Nam");
        else gioitinh.setText("Nữ");

        TextView id = findViewById(R.id.chitiet_id);
        id.setText(String.valueOf(benhNhan.id));

        TextView bhyt = findViewById(R.id.chitiet_bhyt);
        if(benhNhan.bhyt != null) bhyt.setText(benhNhan.bhyt);
        else bhyt.setText("N/A");

        TextView ngaysinh = findViewById(R.id.chitiet_ngaysinh);
        ngaysinh.setText(benhNhan.ngaY_SINH);

        TextView tuoi = findViewById(R.id.chitiet_tuoi);
        tuoi.setText(String.valueOf(benhNhan.tuoi));

        TextView sdt = findViewById(R.id.chitiet_sdt);
        if(benhNhan.sdt != null) sdt.setText(benhNhan.sdt);
        else sdt.setText("N/A");

        TextView cccd = findViewById(R.id.chitiet_cccd);
        if(benhNhan.cccd != null) cccd.setText(benhNhan.cccd);
        else cccd.setText("N/A");

        TextView diachi = findViewById(R.id.chitiet_diachi);
        if(benhNhan.diA_CHI != null) diachi.setText(benhNhan.diA_CHI);
        else diachi.setText("N/A");

        TextView nghenghiep = findViewById(R.id.chitiet_nghenghiep);
        if(benhNhan.nghE_NGHIEP != null) nghenghiep.setText(benhNhan.nghE_NGHIEP);
        else nghenghiep.setText("N/A");
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
                    Toast.makeText(Activity_BenhNhan.this, "Lưu thay đổi thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Activity_BenhNhan.this, "Parse failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BenhNhan> call, Throwable t) {
                Toast.makeText(Activity_BenhNhan.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

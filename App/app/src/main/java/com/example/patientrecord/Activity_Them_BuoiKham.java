package com.example.patientrecord;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.patientrecord.Adapters.DonThuocAdapter;
import com.example.patientrecord.Classes.Benh;
import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.Classes.BuoiKham;
import com.example.patientrecord.Classes.BuoiKhamRequest;
import com.example.patientrecord.Classes.LieuThuoc;
import com.example.patientrecord.Classes.Thuoc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Them_BuoiKham extends AppCompatActivity {
    BenhNhan benhNhan;
    String ngayKham;
    ArrayList<String> listBenh;
    ArrayList<Benh> arraylist_benh;
    Benh benh;
    ArrayList<Thuoc> arraylist_thuoc;
    ArrayList<String> listTenThuoc;
    ArrayList<String> listHoatChat;
    Thuoc thuoc;
    DonThuocAdapter adapter;
    ArrayList<LieuThuoc> list_lieuthuoc;
    ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_buoikham);
        setTitle("Thêm buổi khám");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        benhNhan = (BenhNhan)intent.getSerializableExtra("BenhNhan");
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        ngayKham = simpleDateFormat.format(calendar.getTime());
        Toast.makeText(Activity_Them_BuoiKham.this, ngayKham, Toast.LENGTH_LONG).show();

        list_lieuthuoc = new ArrayList<LieuThuoc>();

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

        listview = findViewById(R.id.add_buoikham_toathuoc);
        adapter = new DonThuocAdapter(this, R.layout.layout_viewitem_toathuoc, list_lieuthuoc);
        listview.setAdapter(adapter);

        AppCompatButton btn_them = findViewById(R.id.add_buoikham_btn_them);
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallAPI_AddBuoiKham();
            }
        });

        CallAPI_LoadBenh();
        CallAPI_LoadThuoc();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoText_TenThuoc.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
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
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(autoText_HoatChat.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
            }
        });

        Button btn_add = dialog.findViewById(R.id.dialog_lieuthuoc_btn_them);
        Button btn_close = dialog.findViewById(R.id.dialog_lieuthuoc_btn_huy);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker sang = dialog.findViewById(R.id.dialog_lieuthuoc_sang);
                NumberPicker trua = dialog.findViewById(R.id.dialog_lieuthuoc_trua);
                NumberPicker chieu = dialog.findViewById(R.id.dialog_lieuthuoc_chieu);
                NumberPicker toi = dialog.findViewById(R.id.dialog_lieuthuoc_toi);

                String ma_thuoc = text_mathuoc.getText().toString();
                int lieu_sang = sang.getValue();
                int lieu_trua = trua.getValue();
                int lieu_chieu = chieu.getValue();
                int lieu_toi = toi.getValue();

                LieuThuoc lieuthuoc = new LieuThuoc(ma_thuoc, lieu_sang, lieu_trua, lieu_chieu, lieu_toi);
                list_lieuthuoc.add(lieuthuoc);
                adapter.notifyDataSetChanged();

                dialog.dismiss();
            }
        });
        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void ChonNgay_dialog(){
        Calendar calendar = Calendar.getInstance();
        int ngay = Integer.valueOf(calendar.getTime().getDate());
        int thang = Integer.valueOf(calendar.getTime().getMonth());
        int nam = Integer.valueOf(calendar.getTime().getYear() + 1900);

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

    void CallAPI_AddBuoiKham(){
        String icd = ((EditText)findViewById(R.id.add_buoikham_icd)).getText().toString();
        String ly_do = ((EditText)findViewById(R.id.add_buoikham_lido)).getText().toString();
        int mach = Integer.parseInt(((EditText)findViewById(R.id.add_buoikham_mach)).getText().toString());
        int nhip_tho = Integer.parseInt(((EditText)findViewById(R.id.add_buoikham_nhiptho)).getText().toString());
        int huyet_ap = Integer.parseInt(((EditText)findViewById(R.id.add_buoikham_huyetap)).getText().toString());
        float chieu_cao = Float.parseFloat(((EditText)findViewById(R.id.add_buoikham_chieucao)).getText().toString());
        int can_nang = Integer.parseInt(((EditText)findViewById(R.id.add_buoikham_cannang)).getText().toString());
        float bmi = Float.parseFloat(((EditText)findViewById(R.id.add_buoikham_bmi)).getText().toString());
        float than_nhiet = Float.parseFloat(((EditText)findViewById(R.id.add_buoikham_nhietdo)).getText().toString());
        String trieu_chung = ((EditText)findViewById(R.id.add_buoikham_trieuchung)).getText().toString();
        String benh_phu = ((EditText)findViewById(R.id.add_buoikham_benhphu)).getText().toString();
        String loi_dan = ((EditText)findViewById(R.id.add_buoikham_loidan)).getText().toString();
        String ket_qua = ((EditText)findViewById(R.id.add_buoikham_ketqua)).getText().toString();
        String ngay_tai_kham = ((EditText)findViewById(R.id.add_buoikham_ngaytaikham)).getText().toString();
        int so_ngay = Integer.parseInt(((EditText)findViewById(R.id.add_buoikham_songay)).getText().toString());

        BuoiKham buoi_kham = new BuoiKham(benhNhan.id, ngayKham, icd, ly_do, mach, nhip_tho, huyet_ap, chieu_cao, can_nang, bmi, than_nhiet,
                                         trieu_chung, benh_phu, loi_dan, ket_qua, ngay_tai_kham, so_ngay);
        BuoiKhamRequest request = new BuoiKhamRequest(buoi_kham, list_lieuthuoc);
        APIService.apiservice.AddBuoiKham(request).enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.isSuccessful()){
                    finish();
                }
                Toast.makeText(Activity_Them_BuoiKham.this, response.message(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(Activity_Them_BuoiKham.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void DeleteView(View v){
        TextView text_tenThuoc = v.findViewById(R.id.viewTenThuoc);
        String tenThuoc = text_tenThuoc.toString();
    }
}

package com.example.patientrecord;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.patientrecord.Classes.BenhNhan;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ListView listview_benhnhan;
    ArrayList<BenhNhan> arraylist_benhnhan;
    BenhNhanAdapter adapter_benhnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Trang chủ");

        listview_benhnhan = (ListView)findViewById(R.id.ListView_BenhNhan);
        arraylist_benhnhan = new ArrayList<>();
        adapter_benhnhan = new BenhNhanAdapter(this, R.layout.layout_viewitem, arraylist_benhnhan);
        listview_benhnhan.setAdapter(adapter_benhnhan);

        listview_benhnhan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Activity_BenhNhan.class);
                BenhNhan selectedItem = arraylist_benhnhan.get(position);
                intent.putExtra("BenhNhan", selectedItem);
                startActivity(intent);
            }
        });

        CallAPI();
    }

    @Override
    public void onResume(){
        super.onResume();
        CallAPI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_add_benhnhan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.menuAdd){
            DialogThem();
        }
        else if(id == R.id.menuSearch){

        }
        return super.onOptionsItemSelected(item);
    }

    public void CallAPI(){
        APIService.apiservice.getData().enqueue(new Callback<List<BenhNhan>>(){
            @Override
            public void onResponse(Call<List<BenhNhan>> call, Response<List<BenhNhan>> response){
                if (response.isSuccessful()) {
                    List<BenhNhan> lists = response.body();
                    arraylist_benhnhan.clear();
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
                        }
                    }
                    Toast.makeText(MainActivity.this, "Load danh sách thành công", Toast.LENGTH_SHORT).show();
                    adapter_benhnhan.notifyDataSetChanged();
                }
                else{
                    Toast.makeText(MainActivity.this, "Load danh sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<BenhNhan>> call, Throwable t){
                Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void DialogThem(){
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_benhnhan);

        AppCompatButton btnAdd = dialog.findViewById(R.id.btnAddBenhnhan);
        AppCompatButton btnHuy = dialog.findViewById(R.id.btnHuyAddBenhnhan);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edtTen_dialog = dialog.findViewById(R.id.edtHoTen);
                EditText edtBHYT_dialog = dialog.findViewById(R.id.edtBHYT);
                EditText edtNgaySinh_dialog = dialog.findViewById(R.id.edtNgaySinh);
                RadioGroup grp_gioitinh = dialog.findViewById(R.id.grpGioiTinh);

                String Ten = String.valueOf(edtTen_dialog.getText());
                String BHYT = null;
                if(!edtBHYT_dialog.getText().toString().equals("")){
                    BHYT = String.valueOf(edtBHYT_dialog.getText());
                }
                String Ngay_Sinh = String.valueOf(edtNgaySinh_dialog.getText());
                Boolean gioitinh;
                if(grp_gioitinh.getCheckedRadioButtonId() == R.id.rdNam) gioitinh = true;
                else gioitinh = false;

                BenhNhan benhnhan = new BenhNhan(Ten, Ngay_Sinh, BHYT, gioitinh);

                APIService.apiservice.createBenhNhan(benhnhan).enqueue(new Callback<Integer>()
                {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response){
                        if(response.isSuccessful() && response.code() == 200){
                            dialog.dismiss();
                            Toast.makeText(MainActivity.this, response.body().toString(), Toast.LENGTH_LONG).show();
                            CallAPI();
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t){
                        Toast.makeText(MainActivity.this, "failed", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
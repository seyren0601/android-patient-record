package com.example.patientrecord.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.patientrecord.Activity_BenhNhan;
import com.example.patientrecord.Activity_Them_BuoiKham;
import com.example.patientrecord.Classes.Benh;
import com.example.patientrecord.Classes.BuoiKham;
import com.example.patientrecord.Classes.LieuThuoc;
import com.example.patientrecord.Classes.Thuoc;
import com.example.patientrecord.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BuoiKhamAdapter extends BaseAdapter {
    public Activity_BenhNhan Context;
    public int Layout;
    public List<BuoiKham> ListBuoiKham;

    public BuoiKhamAdapter(Activity_BenhNhan context, int layout, List<BuoiKham> listBuoiKham){
        Context = context;
        Layout = layout;
        ListBuoiKham = listBuoiKham;
    }

    public BuoiKhamAdapter(){}

    public class ViewHolder{
        public TextView NgayKham;
        public TextView ChanDoan;
        public TextView NgayTaiKham;
    }

    @Override
    public int getCount(){
        return ListBuoiKham.size();
    }

    @Override
    public Object getItem(int i){ return ListBuoiKham.get(i); }

    @Override
    public long getItemId(int i){
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(Layout, null);
            holder.NgayKham = (TextView)view.findViewById(R.id.view_buoikham_ngaykham);
            holder.ChanDoan = (TextView)view.findViewById(R.id.view_buoikham_chandoan);
            holder.NgayTaiKham = (TextView)view.findViewById(R.id.view_buoikham_taikham);
            view.setTag(holder);
        }
        else{
            holder =(ViewHolder)view.getTag();
        }

        BuoiKham buoiKham = ListBuoiKham.get(i);
        APIService.apiservice.GetBenh(buoiKham.icd).enqueue(new Callback<Benh>() {
            @Override
            public void onResponse(Call<Benh> call, Response<Benh> response) {
                Benh benh = response.body();
                holder.ChanDoan.setText("Chẩn đoán: " + benh.teN_BENH);
            }
            @Override
            public void onFailure(Call<Benh> call, Throwable t) {

            }
        });

        String ngay_string = buoiKham.ngay;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date ngay_kham = new Date();
        try {
            ngay_kham = format.parse(ngay_string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.NgayKham.setText("Ngày: " + format.format(ngay_kham));

        String ngaytaikham_string = buoiKham.ngaY_TAI_KHAM;
        Date ngayTaiKham = new Date();
        try {
            ngayTaiKham = format.parse(ngaytaikham_string);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        holder.NgayTaiKham.setText("Tái khám: " + format.format(ngayTaiKham));
        return view;
    }
}


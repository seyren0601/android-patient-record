package com.example.patientrecord.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.patientrecord.Activity_Them_BuoiKham;
import com.example.patientrecord.Classes.LieuThuoc;
import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.Classes.Thuoc;
import com.example.patientrecord.R;

import java.util.List;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonThuocAdapter extends BaseAdapter {
    public Activity_Them_BuoiKham Context;
    public int Layout;
    public List<LieuThuoc> ListLieuThuoc;

    public DonThuocAdapter(Activity_Them_BuoiKham context, int layout, List<LieuThuoc> listLieuThuoc){
        Context = context;
        Layout = layout;
        ListLieuThuoc = listLieuThuoc;
    }

    public DonThuocAdapter(){}

    public class ViewHolder{
        public TextView TenThuoc;
        public TextView LieuThuoc;
    }

    @Override
    public int getCount(){
        return ListLieuThuoc.size();
    }

    @Override
    public Object getItem(int i){ return ListLieuThuoc.get(i); }

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
            holder.TenThuoc = (TextView)view.findViewById(R.id.viewTenThuoc);
            holder.LieuThuoc = (TextView)view.findViewById(R.id.viewLieuThuoc);
            view.setTag(holder);
        }
        else{
            holder =(ViewHolder)view.getTag();
        }

        LieuThuoc lieuthuoc = ListLieuThuoc.get(i);
        APIService.apiservice.getThuoc(lieuthuoc.ID_Thuoc).enqueue(new Callback<Thuoc>() {
            @Override
            public void onResponse(Call<Thuoc> call, Response<Thuoc> response) {
                Thuoc thuoc = response.body();
                holder.TenThuoc.setText(thuoc.teN_THUOC);
            }

            @Override
            public void onFailure(Call<Thuoc> call, Throwable t) {

            }
        });
        String lieu = "";
        if(lieuthuoc.Lieu_Sang > 0) lieu += "Sáng: " + lieuthuoc.Lieu_Sang + "  ";
        if(lieuthuoc.Lieu_Trua > 0) lieu += "Trưa: " + lieuthuoc.Lieu_Sang + "  ";
        if(lieuthuoc.Lieu_Chieu > 0) lieu += "Chiều: " + lieuthuoc.Lieu_Sang + "  ";
        if(lieuthuoc.Lieu_Toi > 0) lieu += "Tối: " + lieuthuoc.Lieu_Sang + "  ";
        holder.LieuThuoc.setText(lieu);
        return view;
    }
}

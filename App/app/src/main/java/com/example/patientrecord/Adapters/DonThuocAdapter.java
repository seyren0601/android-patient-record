package com.example.patientrecord.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    public AppCompatActivity Context;
    public int Layout;
    public List<LieuThuoc> ListLieuThuoc;

    public DonThuocAdapter(AppCompatActivity context, int layout, List<LieuThuoc> listLieuThuoc){
        Context = context;
        Layout = layout;
        ListLieuThuoc = listLieuThuoc;
    }

    public DonThuocAdapter(){}

    public class ViewHolder{
        public String ThuocID;
        public TextView TenThuoc;
        public TextView LieuThuoc;
        public ImageView imageDelete;
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
            holder.imageDelete = (ImageView)view.findViewById(R.id.viewXoa);
            view.setTag(holder);
        }
        else{
            holder =(ViewHolder)view.getTag();
        }

        LieuThuoc lieuthuoc = ListLieuThuoc.get(i);
        APIService.apiservice.getThuoc(lieuthuoc.iD_THUOC).enqueue(new Callback<Thuoc>() {
            @Override
            public void onResponse(Call<Thuoc> call, Response<Thuoc> response) {
                Thuoc thuoc = response.body();
                holder.TenThuoc.setText(thuoc.teN_THUOC);
                holder.ThuocID = thuoc.id;
            }

            @Override
            public void onFailure(Call<Thuoc> call, Throwable t) {

            }
        });
        String lieu = "";
        if(lieuthuoc.lieU_SANG > 0) lieu += "Sáng: " + lieuthuoc.lieU_SANG + "  ";
        if(lieuthuoc.lieU_TRUA > 0) lieu += "Trưa: " + lieuthuoc.lieU_TRUA + "  ";
        if(lieuthuoc.lieU_CHIEU > 0) lieu += "Chiều: " + lieuthuoc.lieU_CHIEU + "  ";
        if(lieuthuoc.lieU_TOI > 0) lieu += "Tối: " + lieuthuoc.lieU_TOI + "  ";
        holder.LieuThuoc.setText(lieu);

        if (holder.imageDelete != null) {
            holder.imageDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(Context, "in toast", Toast.LENGTH_SHORT).show();
                    for(LieuThuoc lieuThuoc:ListLieuThuoc){
                        if(lieuThuoc.iD_THUOC.equals(holder.ThuocID)){
                            ListLieuThuoc.remove(lieuThuoc);
                            notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        return view;
    }
}

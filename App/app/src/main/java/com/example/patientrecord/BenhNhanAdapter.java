package com.example.patientrecord;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import api.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BenhNhanAdapter extends BaseAdapter {
    public MainActivity Context;
    public int Layout;
    public List<BenhNhan> ListBenhNhan;

    public BenhNhanAdapter(MainActivity context, int layout, List<BenhNhan> listBenhNhan){
        Context = context;
        Layout = layout;
        ListBenhNhan = listBenhNhan;
    }

    public BenhNhanAdapter(){}

    public class ViewHolder{
        public TextView ID;
        public TextView Ten;
        public TextView GioiTinh;
        public TextView NgaySinh;
        public ImageView imgViewDelete;
    }

    @Override
    public int getCount(){
        return ListBenhNhan.size();
    }

    @Override
    public Object getItem(int i){ return ListBenhNhan.get(i); }

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
            holder.ID = (TextView)view.findViewById(R.id.viewID);
            holder.Ten = (TextView)view.findViewById(R.id.viewTen);
            holder.GioiTinh = (TextView)view.findViewById(R.id.viewGioiTinh);
            holder.NgaySinh = (TextView)view.findViewById(R.id.viewNgaySinh);
            view.setTag(holder);
        }
        else{
            holder =(ViewHolder)view.getTag();
        }
        BenhNhan benhnhan = ListBenhNhan.get(i);
        holder.ID.setText(String.valueOf(benhnhan.id));
        holder.Ten.setText(benhnhan.ten);
        if(benhnhan.gioI_TINH){
            holder.GioiTinh.setText("Nam");
        }
        else{
            holder.GioiTinh.setText("Nữ");
        }
        holder.NgaySinh.setText(benhnhan.ngaY_SINH);
        return view;
    }
}

package com.example.patientrecord.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.MainActivity;
import com.example.patientrecord.R;

import java.util.List;

public class BenhNhanAdapter extends BaseAdapter {
    public AppCompatActivity Context;
    public int Layout;
    public List<BenhNhan> ListBenhNhan;

    public BenhNhanAdapter(AppCompatActivity context, int layout, List<BenhNhan> listBenhNhan){
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

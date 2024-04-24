package com.example.patientrecord;

import android.content.DialogInterface;
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
            holder.imgViewDelete = (ImageView)view.findViewById(R.id.right_view);
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
        SwipeLayout swipe = (SwipeLayout)view.findViewById(R.id.swipe_layout);
        holder.imgViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Context)
                .setTitle("Xóa bệnh nhân")
                .setMessage("Bạn có muốn xóa bệnh nhân này không?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        APIService.apiservice.deleteBenhNhan(benhnhan.id).enqueue(new Callback<Boolean>() {
                            @Override
                            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                                if(response.isSuccessful()){
                                    Context.CallAPI();
                                    Toast.makeText(Context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Context, "Xóa thành công, lỗi API", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onFailure(Call<Boolean> call, Throwable t) {

                                Toast.makeText(Context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                            }
                        });
                        swipe.close();
                    }
                }).show();
            }
        });
        return view;
    }
}

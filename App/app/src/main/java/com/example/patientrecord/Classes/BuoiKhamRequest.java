package com.example.patientrecord.Classes;

import java.util.ArrayList;

public class BuoiKhamRequest {
    public BuoiKham buoiKham;
    public ArrayList<LieuThuoc> list_lieuthuoc;
    public BuoiKhamRequest(BuoiKham buoi_kham, ArrayList<LieuThuoc> list){
        buoiKham = buoi_kham;
        list_lieuthuoc = list;
    }
}

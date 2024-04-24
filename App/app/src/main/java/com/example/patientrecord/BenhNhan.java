package com.example.patientrecord;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class BenhNhan implements Serializable {
    int id;
    @Nullable
    String bhyt;
    String ten;
    String ngaY_SINH;
    int tuoi;
    Boolean gioI_TINH;
    @Nullable
    String nghE_NGHIEP;
    @Nullable
    String sdt;
    @Nullable
    String diA_CHI;
    @Nullable
    String cccd;
    public BenhNhan(String ten, String ngaysinh, String bhyt, Boolean gioitinh){
        this.ten = ten;
        ngaY_SINH = ngaysinh;
        this.bhyt = bhyt;
        gioI_TINH = gioitinh;
    }
}

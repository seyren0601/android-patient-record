package com.example.patientrecord.Classes;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Date;

public class BenhNhan implements Serializable {
    public int id;
    @Nullable
    public String bhyt;
    public String ten;
    public String ngaY_SINH;
    public int tuoi;
    public Boolean gioI_TINH;
    @Nullable
    public String nghE_NGHIEP;
    @Nullable
    public String sdt;
    @Nullable
    public String diA_CHI;
    @Nullable
    public String cccd;
    public BenhNhan(String ten, String ngaysinh, String bhyt, Boolean gioitinh){
        this.ten = ten;
        ngaY_SINH = ngaysinh;
        this.bhyt = bhyt;
        gioI_TINH = gioitinh;
    }
}

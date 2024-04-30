package com.example.patientrecord.Classes;

public class LieuThuoc {
    public int iD_BUOI_KHAM;
    public String iD_THUOC;
    public int lieU_SANG;
    public int lieU_TRUA;
    public int lieU_CHIEU;
    public int lieU_TOI;

    public LieuThuoc(String id_thuoc, int lieu_sang, int lieu_trua, int lieu_chieu, int lieu_toi){
        this.iD_THUOC = id_thuoc;
        this.lieU_SANG = lieu_sang;
        this.lieU_TRUA = lieu_trua;
        this.lieU_CHIEU = lieu_chieu;
        this.lieU_TOI = lieu_toi;
    }
}

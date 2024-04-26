package com.example.patientrecord.Classes;

public class LieuThuoc {
    public int ID;
    public String ID_Thuoc;
    public int Lieu_Sang;
    public int Lieu_Trua;
    public int Lieu_Chieu;
    public int Lieu_Toi;

    public LieuThuoc(String id_thuoc, int lieu_sang, int lieu_trua, int lieu_chieu, int lieu_toi){
        this.ID_Thuoc = id_thuoc;
        this.Lieu_Sang = lieu_sang;
        this.Lieu_Trua = lieu_trua;
        this.Lieu_Chieu = lieu_chieu;
        this.Lieu_Toi = lieu_toi;
    }
}

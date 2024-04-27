package com.example.patientrecord.Classes;

public class BuoiKham {
    public int ID;
    public int iD_BENH_NHAN;
    public String ngay;
    public String icd;
    public String lY_DO_DEN_KHAM;
    public int mach;
    public int nhiP_THO;
    public int huyeT_AP;
    public float chieU_CAO;
    public int caN_NANG;
    public float bmi;
    public float thaN_NHIET;
    public String trieU_CHUNG_LS;
    public String icD_BENH_PHU;
    public String loI_DAN_KHAM;
    public String keT_QUA_DIEU_TRI;
    public String ngaY_TAI_KHAM;
    public int sO_NGAY_LAY_THUOC;

    public BuoiKham(int iD_BENH_NHAN, String ngay, String icd, String ly_do, int mach, int nhip_tho, int huyet_ap, float chieu_cao,
                    int can_nang, float bmi, float than_nhiet, String trieu_chung, String benh_phu, String loi_dan, String ket_qua,
                    String ngay_tai_kham, int so_ngay){
        this.iD_BENH_NHAN = iD_BENH_NHAN;
        this.icd = icd;
        this.ngay = ngay;
        this.lY_DO_DEN_KHAM = ly_do;
        this.mach = mach;
        nhiP_THO = nhip_tho;
        huyeT_AP = huyet_ap;
        chieU_CAO = chieu_cao;
        caN_NANG = can_nang;
        this.bmi = bmi;
        thaN_NHIET = than_nhiet;
        trieU_CHUNG_LS = trieu_chung;
        icD_BENH_PHU = benh_phu;
        loI_DAN_KHAM = loi_dan;
        keT_QUA_DIEU_TRI = ket_qua;
        ngaY_TAI_KHAM = ngay_tai_kham;
        sO_NGAY_LAY_THUOC = so_ngay;
    }
}

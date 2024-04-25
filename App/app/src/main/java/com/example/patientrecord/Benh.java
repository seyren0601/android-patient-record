package com.example.patientrecord;

import androidx.annotation.Nullable;

public class Benh {
    public String mA_BENH;
    public String teN_BENH;
    public String mA_LOAI_BENH;

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this.mA_BENH == ((Benh)obj).mA_BENH) return true;
        else return false;
    }
}

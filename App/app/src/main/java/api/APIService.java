package api;

import com.example.patientrecord.Classes.Benh;
import com.example.patientrecord.Classes.BenhNhan;
import com.example.patientrecord.Classes.Thuoc;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface APIService {
    Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();
    APIService apiservice = new Retrofit.Builder()
            .baseUrl("http://192.168.1.5:80/").addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);

    @GET("thuoc")
    Call<Thuoc> getThuoc(@Query("id") String id_thuoc);
    @GET("benhnhan/all")
    Call<List<BenhNhan>> getData();

    @GET("benh/all")
    Call<ArrayList<Benh>> GetAllBenh();

    @GET("thuoc/all")
    Call<ArrayList<Thuoc>> GetAllThuoc();

    @GET("benh")
    Call<ArrayList<Benh>> FindBenhByTen(@Query("icd") String icd);

    @POST("update/benhnhan")
    Call<BenhNhan> updateBenhNhan(@Body BenhNhan benhnhan);

    @PUT("them/benhnhan")
    Call<Integer> createBenhNhan(@Body BenhNhan benhnhan);

    @DELETE("delete/benhnhan")
    Call<Boolean> deleteBenhNhan(@Query("ID") int id_benhnhan);
}
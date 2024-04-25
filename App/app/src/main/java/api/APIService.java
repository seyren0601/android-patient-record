package api;

import com.example.patientrecord.Benh;
import com.example.patientrecord.BenhNhan;
import com.example.patientrecord.Thuoc;
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
            .baseUrl("http://10.0.2.2:5154/").addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
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
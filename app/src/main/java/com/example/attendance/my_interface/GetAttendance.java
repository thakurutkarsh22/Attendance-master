package com.example.attendance.my_interface;

import com.example.attendance.models.AttendanceModel;
import com.example.attendance.models.ClassDetails;
import com.example.attendance.models.PostAttendanceModel;
import com.example.attendance.models.Token;
import com.example.attendance.models.UserDetails;
import com.google.gson.JsonArray;

import org.json.JSONArray;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetAttendance {
    @FormUrlEncoded
    @POST("api/auths")
    Call<Token> login(@Field("Login") String username, @Field("Password") String password);

    @GET("api/Attendance")
    Call<ArrayList<AttendanceModel>> getAttendanceData(
            @Header("Authorization") String auth,
            @Query("date") String date,
            @Query("Class_id") String Class_id
    );


    @GET("api/me")
    Call<UserDetails> getUserData(
            @Header("Authorization") String auth
    );


    @GET("api/class")
    Call<ArrayList<ClassDetails>> getClassData(
            @Header("Authorization") String auth,
            @Query("User_id") String ID
    );


    @POST("api/Attendance")
    Call<String> sendAttendance(
            @Header("Authorization") String auth,
            @Body JsonArray list
    );

}

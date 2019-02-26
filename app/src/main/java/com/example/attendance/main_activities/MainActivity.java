package com.example.attendance.main_activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.SharedPreferenceConfig;
import com.example.attendance.adapter.AttendanceAdapter;
import com.example.attendance.models.AttendanceModel;
import com.example.attendance.models.ClassDetails;
import com.example.attendance.models.PostAttendanceModel;
import com.example.attendance.models.Token;
import com.example.attendance.models.UserDetails;
import com.example.attendance.my_interface.GetAttendance;
import com.example.attendance.network.RetrofitInstance;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView_attendance;
    ArrayList<AttendanceModel> attendanceModel = new ArrayList();
    ArrayList<ClassDetails> classDetails = new ArrayList();
    AttendanceAdapter attendanceAdapter;
    Button btn_save, get_attnd;
    TextView txt_present, txtClass, txtDate;
    DatePickerDialog datePickerDialog;
    ProgressDialog pd;
    ClassDetails model;
    private SharedPreferenceConfig preferenceConfig;
    String token, userid;
    ClassDetails classDetail;
    DateTime dateTime, currentDate;
    Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_save = (Button) findViewById(R.id.btn_save);
        txt_present = (TextView) findViewById(R.id.txt_present);
        txtClass = (TextView) findViewById(R.id.txt_class);
        //  logout_btn=(Button)findViewById(R.id.logout_btn);
        txtDate = (TextView) findViewById(R.id.txt_Date);
        dateTime = new DateTime();
        currentDate = new DateTime();
        txtDate.setText(dateTime.toString("dd/MM/yyyy"));
        btnSend = (Button) findViewById(R.id.btn_save);
        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
        token = preferenceConfig.getStringPref(SharedPreferenceConfig.Token);
        userid = preferenceConfig.getStringPref(SharedPreferenceConfig.Userid);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAttendance();
            }
        });
       /* logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                preferenceConfig.logOut();

            }
        });
*/
        /*btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = 0;
                int count1 = 0;
                ArrayList<AttendanceModel> data = attendanceAdapter.attendanceModels;

                for (int i = 0; i < data.size(); i++) {
                    AttendanceModel list = data.get(i);
                    if (list.isAttendanceMark()) {
                        count++;
                    } else if (!list.isAttendanceMark()) {
                        count1++;
                    }
                }

                txt_present.setText("Total Present: " + count + " | Total Absent:" + count1);

            }
        });*/
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        getClass(token, userid);
      /*  pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        getAttendance(token);*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                showDialigAttentance();
                break;
        }
        return (super.onOptionsItemSelected(item));
    }

    private void showDialigAttentance() {
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.dialog_box, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(layout);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final Spinner pickclass = (Spinner) layout.findViewById(R.id.stdclass);
        final ArrayAdapter<ClassDetails> adapter = new ArrayAdapter<ClassDetails>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, classDetails);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pickclass.setAdapter(adapter);
        int position = -1;
        for (int i = 0; i < classDetails.size(); i++) {
            if (classDetails.get(i).get_id().equals(classDetail.get_id())) {
                position = i;
                break;
            }
        }
        if (position != -1)
            pickclass.setSelection(position);
        for (ClassDetails classDetails : classDetails) {
            if (classDetails.get_id().equals(classDetail.get_id())) {

            }
        }
        pickclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                classDetail = (ClassDetails) parent.getItemAtPosition(position);
                if (classDetail != null) {
                    txtClass.setText(classDetail.getClassName());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        final TextView pickdate = (TextView) layout.findViewById(R.id.pick_date);
        pickdate.setText(dateTime.toString("dd/MM/yyyy"));
        pickdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        dateTime = new DateTime(year, monthOfYear + 1, dayOfMonth, 0, 0);
                        pickdate.setText(dateTime.toString("dd/MM/yyyy"));


                    }
                }, dateTime.getYear(), dateTime.getMonthOfYear() - 1, dateTime.getDayOfMonth());
                datePickerDialog.getDatePicker().setMaxDate(currentDate.toLocalDateTime().toDate().getTime());
                datePickerDialog.show();
            }
        });
        get_attnd = (Button) layout.findViewById(R.id.get_attnd);

        get_attnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                txtDate.setText(dateTime.toString("dd/MM//yyyy"));
                pd = new ProgressDialog(MainActivity.this);
                pd.setMessage("loading");
                pd.show();

                getAttendance(token, dateTime.toString("dd/MM/yyyy"), classDetail.get_id());

            }

        });

    }

    private void getClass(final String token, final String userid) {
        GetAttendance userService = RetrofitInstance.getRetrofitInstance().create(GetAttendance.class);
        Call<ArrayList<ClassDetails>> call = userService.getClassData(token, userid);
        call.enqueue(new Callback<ArrayList<ClassDetails>>() {
            @Override
            public void onResponse(Call<ArrayList<ClassDetails>> call, Response<ArrayList<ClassDetails>> response) {
                if (response.isSuccessful()) {
                    ArrayList<ClassDetails> list = response.body();
                    if (list != null && list.size() > 0) {
                        classDetails.clear();
                        classDetails.addAll(list);
                        classDetail = list.get(0);
                        txtClass.setText(classDetail.getClassName());
                        pd.dismiss();
                        getAttendance(token, dateTime.toString("dd/MM/yyyy"), classDetail.get_id());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Class not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ClassDetails>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getAttendance(final String token, String date_n, String Class_id) {
        GetAttendance userService = RetrofitInstance.getRetrofitInstance().create(GetAttendance.class);
        Call<ArrayList<AttendanceModel>> call = userService.getAttendanceData(token, date_n, Class_id);
        call.enqueue(new Callback<ArrayList<AttendanceModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AttendanceModel>> call, Response<ArrayList<AttendanceModel>> response) {
                if (response.isSuccessful()) {
                    ArrayList<AttendanceModel> attendanceModel = response.body();
                    if (attendanceModel != null) {
                        generateAttendance(attendanceModel);
                        pd.dismiss();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                pd.dismiss();
            }


            @Override
            public void onFailure(Call<ArrayList<AttendanceModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveAttendance() {
        if (attendanceAdapter != null) {
            ArrayList<AttendanceModel> list = attendanceAdapter.attendanceModels;
            //This is post attendance
            ArrayList<PostAttendanceModel> postAttendanceModels = new ArrayList<>();

            /*for(int i=0;i<list.size();i++)
            {
                PostAttendanceModel postAttendanceModel = new PostAttendanceModel();
                postAttendanceModel.setStudent_id(list.get(i).getStudend_id());
                postAttendanceModels.get(i).getDateOfAttendance();
                postAttendanceModels.get(i).getClass_id();
            }
            */

            for (AttendanceModel attendanceModel : list) {
                PostAttendanceModel postAttendanceModel = new PostAttendanceModel();
                postAttendanceModel.setAttendanceMark(String.valueOf(attendanceModel.isAttendanceMark()));
                postAttendanceModel.setDateOfAttendance((dateTime.toString("MM/dd/yyyy")));
                postAttendanceModel.setClass_id(attendanceModel.getClass_id());
                postAttendanceModel.setStudent_id(attendanceModel.getStudend_id());
                postAttendanceModels.add(postAttendanceModel);
            }

            JsonArray jsonElements = (JsonArray) new Gson().toJsonTree(postAttendanceModels);
            GetAttendance userService = RetrofitInstance.getRetrofitInstance().create(GetAttendance.class);
            Call<String> call = userService.sendAttendance(token, jsonElements);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    pd.dismiss();
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Attedance marked.", Toast.LENGTH_SHORT).show();
                    } else
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }


    /**
     * Method to generate List of notice using RecyclerView with custom adapter
     */
    private void generateAttendance(ArrayList<AttendanceModel> attendanceModel) {
        recyclerView_attendance = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView_attendance.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        attendanceAdapter = new AttendanceAdapter(MainActivity.this, attendanceModel);
        recyclerView_attendance.setAdapter(attendanceAdapter);

    }


    /* preferenceConfig = new SharedPreferenceConfig(getApplicationContext());
    String email = preferenceConfig.getEmail();
        if (email != null) {
            textView1.setText(email);
        } else {
            textView1.setText("Email not found");
        }*/

}

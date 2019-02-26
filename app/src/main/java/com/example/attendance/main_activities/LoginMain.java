package com.example.attendance.main_activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.SharedPreferenceConfig;
import com.example.attendance.models.AttendanceModel;
import com.example.attendance.models.Token;
import com.example.attendance.models.UserDetails;
import com.example.attendance.my_interface.GetAttendance;
import com.example.attendance.network.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginMain extends AppCompatActivity {

    EditText Email, Password;
    Button Login_btn, Btn_create;
    ProgressDialog pd;
    ArrayList<AttendanceModel> attendanceModel = new ArrayList();

    private SharedPreferenceConfig preferenceConfig;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.
        assert getSupportActionBar() != null;
        getSupportActionBar().hide();
        setContentView(R.layout.login);

        preferenceConfig = new SharedPreferenceConfig(getApplicationContext());

        if (preferenceConfig.getStringPref(SharedPreferenceConfig.Token) != null) {
            Intent in = new Intent(LoginMain.this, MainActivity.class);
            startActivity(in);
            finish();
        }

        Email = (EditText) findViewById(R.id.mail);
        Password = (EditText) findViewById(R.id.password);
        Login_btn = (Button) findViewById(R.id.login_btn);
        Btn_create = (Button) findViewById(R.id.btn_create);

        Login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = Email.getText().toString().trim();
                String pass = Password.getText().toString().trim();
                if (validate(mail, pass)) {
                    doLogin(mail, pass);
                    pd = new ProgressDialog(LoginMain.this);
                    pd.setMessage("loading");
                    pd.show();
                }
            }
        });

        /*Btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginMain.this, MainActivity.class);
                startActivity(in);
            }
        });
*/
    }

    public boolean validate(String mail, String pass) {
        if (mail.isEmpty()) {
            Email.setError("Field can't be empty");
            Email.requestFocus();
            return false;
        } /*else if (!Patterns.EMAIL_ADDRESS.matcher(mail).matches()) {
            Email.setError("Please Enter a Valid E-mail_input Id");
            Email.requestFocus();
            return false;*//*
        }*/ else if (pass.isEmpty()) {
            Password.setError("Field can't be empty");
            Password.requestFocus();
            return false;
        } else
            return true;

    }

    private void doLogin(final String username, final String password) {
        GetAttendance userService = RetrofitInstance.getRetrofitInstance().create(GetAttendance.class);
        Call<Token> call = userService.login(username, password);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token token = response.body();
                    if (token != null && token.getToken() != null) {
                        String stringToken = "bearer " + token.getToken();
                        getUser(stringToken);
                    } else {
                        Toast.makeText(getApplicationContext(), token.getDescription(), Toast.LENGTH_LONG).show();
                        pd.dismiss();

                    }
                } else
                    Toast.makeText(LoginMain.this, "Try Again Later", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginMain.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getUser(final String token) {
        GetAttendance userService = RetrofitInstance.getRetrofitInstance().create(GetAttendance.class);
        Call<UserDetails> call = userService.getUserData(token);
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {
                if (response.isSuccessful()) {
                    UserDetails userDetails = response.body();
                    if (userDetails != null) {
                        preferenceConfig.setStringPref(SharedPreferenceConfig.Token, token);
                        preferenceConfig.setStringPref(SharedPreferenceConfig.Userid, userDetails.getId());
                        preferenceConfig.setStringPref(SharedPreferenceConfig.UserName, userDetails.getName());
                        Intent intent = new Intent(LoginMain.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    Toast.makeText(LoginMain.this, "Login failed", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                Toast.makeText(LoginMain.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

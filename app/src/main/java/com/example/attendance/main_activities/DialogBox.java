package com.example.attendance.main_activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.attendance.R;

public class DialogBox extends AppCompatActivity {

    Context context;

    public DialogBox(Context context) {
        this.context = context;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_box);
    }
}

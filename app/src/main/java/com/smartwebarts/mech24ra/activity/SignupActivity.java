package com.smartwebarts.mech24ra.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

public class SignupActivity extends AppCompatActivity {
    EditText Et_login_email,et_login_pass,et_login_name;
    RelativeLayout Btn_Sign_in;
    TextView tv_login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_pass = (EditText) findViewById(R.id.et_login_pass);
        et_login_name = (EditText) findViewById(R.id.et_login_name);
       // tv_login_email = (TextView) findViewById(R.id.tv_login_email);
        Btn_Sign_in = (RelativeLayout) findViewById(R.id.btn_Sign_up);

Btn_Sign_in.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        makeLoginRequest();
    }
});

    }
    private void makeLoginRequest() {
        final String UserName = Et_login_email.getText().toString().trim();
        final String UserPasswrd = et_login_pass.getText().toString().trim();
        final String Username = et_login_name.getText().toString().trim();


        if (UtilMethods.INSTANCE.isNetworkAvialable(this)){
            et_login_pass.setEnabled(false);
            UtilMethods.INSTANCE.signup(this,Username,"",UserName,UserPasswrd, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    startDashboard(message);
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(SignupActivity.this, from, Toast.LENGTH_SHORT).show();
                    et_login_pass.setEnabled(true);
                }
            });

        } else {
            Toast.makeText(this, "Check your Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void startDashboard(final String message) {
        class StartDashboard extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                AppSharedPreferences preferences = new AppSharedPreferences();
                preferences.setLoginDetails(SignupActivity.this, message);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(SignupActivity.this, LogInActivity.class);
                startActivity(intent);
                finish();
            }
        }

        new StartDashboard().execute();
    }


    public void gotoLogin(View view) {
        Intent intent=new Intent(SignupActivity.this,LogInActivity.class);
        startActivity(intent);
    }
}
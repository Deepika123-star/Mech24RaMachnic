package com.smartwebarts.mech24ra.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.changepassword.ChangePasswordFragment;
import com.smartwebarts.mech24ra.retrofit.OTPModel;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

public class LogInActivity extends AppCompatActivity {

    EditText Et_login_email,et_login_pass;
    RelativeLayout Btn_Sign_in;
    TextView tv_login_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_log_in);


        Et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_pass = (EditText) findViewById(R.id.et_login_pass);
        tv_login_email = (TextView) findViewById(R.id.tv_login_email);
        Btn_Sign_in = (RelativeLayout) findViewById(R.id.btn_Sign_in);

        Btn_Sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Et_login_email.getText().toString().isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please enter valid ID", Toast.LENGTH_SHORT).show();
                }
              else if (et_login_pass.getText().toString().isEmpty()) {
                    Toast.makeText(LogInActivity.this, "Please enter valid Password", Toast.LENGTH_SHORT).show();
                } else {
//                    progressDialog.show();
                    makeLoginRequest();

                }
            }
        });


    }


    private void makeLoginRequest() {
        final String UserName = Et_login_email.getText().toString().trim();
        final String UserPasswrd = et_login_pass.getText().toString().trim();


        if (UtilMethods.INSTANCE.isNetworkAvialable(this)){
            et_login_pass.setEnabled(false);
            UtilMethods.INSTANCE.Login(this, UserName, UserPasswrd, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    startDashboard(message);
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(LogInActivity.this, from, Toast.LENGTH_SHORT).show();
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
                preferences.setLoginDetails(LogInActivity.this, message);
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

        new StartDashboard().execute();
    }


    public void gotoSigup(View view) {
        Intent in =new Intent(LogInActivity.this,SignupActivity.class);
        startActivity(in);
    }

    public void forgotPassword(View view) {
       OpenDialogFwd(view);
    }
    public void OpenDialogFwd(View v1) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.forgotpass, null);

        EditText edMobileFwp = (EditText) view.findViewById(R.id.ed_mobile_fwp);
        TextInputLayout tilMobileFwp=(TextInputLayout)view.findViewById(R.id.til_mobile_fwp);
        Button FwdokButton = (Button) view.findViewById(R.id.okButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton)) {
                    return;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String number = edMobileFwp.getText().toString();
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton)) {
                    return;
                }

                if (UtilMethods.INSTANCE.isNetworkAvialable(LogInActivity.this)) {

                    UtilMethods.INSTANCE.otp(LogInActivity.this, edMobileFwp.getText().toString().trim(), new mCallBackResponse() {
                        @Override
                        public void success(String from, String message) {
                            OTPModel otpModel = new Gson().fromJson(message, OTPModel.class);
                            Intent intent = new Intent(LogInActivity.this, ChangePasswordFragment.class);
                            intent.putExtra(ChangePasswordFragment.OTP, otpModel);
                            intent.putExtra(ChangePasswordFragment.NUMBER, number);
                            startActivity(intent);
                        }

                        @Override
                        public void fail(String from) {

                        }
                    });

                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(LogInActivity.this);
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public boolean validateMobileFwp(EditText edMobileFwp, TextInputLayout tilMobileFwp, Button FwdokButton){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError(getString(R.string.err_msg_mobile));
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().length()==10)){
            tilMobileFwp.setError(getString(R.string.err_msg_mobile_length));
            requestFocus(edMobileFwp);
            return false;
        }else {
            tilMobileFwp.setErrorEnabled(false);
            FwdokButton.setEnabled(true);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }


}

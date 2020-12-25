package com.smartwebarts.mech24ra.changepassword;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.retrofit.OTPModel;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ChangePasswordFragment extends AppCompatActivity implements View.OnClickListener {

    TextInputEditText password;
    TextInputEditText new_password;
    TextInputEditText con_password;
    TextInputLayout newPasswordLayout;
    Button btnPaymentSubmit;
    OTPModel otpModel;
    String number;
    public static final String OTP = "otp";
    public static final String NUMBER = "number";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_change_password);
        otpModel = (OTPModel) getIntent().getExtras().get(OTP);
        number = getIntent().getExtras().getString(NUMBER, number);
        GetId();
    }

    private void GetId() {
        password = (TextInputEditText) findViewById(R.id.password);
        new_password = (TextInputEditText) findViewById(R.id.new_password);
        con_password = (TextInputEditText) findViewById(R.id.con_password);
        newPasswordLayout = (TextInputLayout) findViewById(R.id.passwordLayout);
        btnPaymentSubmit = (Button) findViewById(R.id.btnPaymentSubmit);

        SetListener();
    }

    private void SetListener() {
        btnPaymentSubmit.setOnClickListener(this);
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                validateMobileFwp(password, newPasswordLayout, btnPaymentSubmit);
            }
        });
    }

    public boolean validateMobileFwp(TextInputEditText edMobileFwp, TextInputLayout tilMobileFwp, Button FwdokButton){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError("Please Enter OTP");
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().length()==4)){
            tilMobileFwp.setError("Enter OTP of 4 digits");
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().equals(otpModel.getOtp()))){
            tilMobileFwp.setError("OTP not match");
            requestFocus(edMobileFwp);
            return false;
        } else {
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


    @Override
    public void onClick(View v) {
        if (v==btnPaymentSubmit){
            if (validateMobileFwp(password, newPasswordLayout, btnPaymentSubmit))
                if (validationForm("") == 0)
                if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

                    UtilMethods.INSTANCE.changePassword(this, new_password.getText().toString(),number,new mCallBackResponse() {
                        @Override
                        public void success(String from, String message) {
                            new SweetAlertDialog(ChangePasswordFragment.this, SweetAlertDialog.SUCCESS_TYPE)
                                    .setTitleText("Password Changed Successfully")
                                    .setContentText("Successfull")
                                    .setConfirmText("Login to Continue")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            sDialog.dismissWithAnimation();
                                            finish();
                                        }
                                    })
                                    .show();
                        }

                        @Override
                        public void fail(String from) {

                        }
                    });

                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(this);
                }
        }
    }

    private int validationForm(String s) {
        int flag = 0;
        if(password.getText() !=null&&password.getText().toString().trim().length()>0){
        }else {
            password.setError("Please enter valid password!!");
            password.requestFocus();
            flag++;
        }
        if( new_password.getText() !=null && new_password.getText().toString().trim().length()>0 &&
                con_password.getText() !=null && con_password.getText().toString().trim().length()>0 &&
                new_password.getText().toString().equalsIgnoreCase(con_password.getText().toString())){
        }else {
            con_password.setError("Please enter same confirm password!!");
            con_password.requestFocus();
            flag++;
        }

        return flag;
    }
}

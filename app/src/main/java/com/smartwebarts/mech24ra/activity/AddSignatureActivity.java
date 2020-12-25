package com.smartwebarts.mech24ra.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.smartwebarts.mech24ra.MainActivity;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.retrofit.My_order_model;
import com.smartwebarts.mech24ra.retrofit.OTPModel;
import com.smartwebarts.mech24ra.retrofit.UpDAteData;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;
import com.williamww.silkysignature.views.SignaturePad;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddSignatureActivity extends AppCompatActivity {

    private SignaturePad mSignaturePad;
    private Toolbar toolbar;
    private Bitmap bm;
    private My_order_model strId;
    public static final String ID = "id";
    private TextView tvName;
    private File file;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_signature);

        strId = (My_order_model) getIntent().getExtras().get(ID);
        toolbar = findViewById(R.id.toolbar);
        tvName = findViewById(R.id.name);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                bm = mSignaturePad.getSignatureBitmap();
            }

            @Override
            public void onClear() {
                //Event triggered when the pad is cleared
                bm = null;
            }
        });
    }

    public void reset(View view) {
//        mSignaturePad.clear();
        tvName.setText("");
    }

    public void submit(View view) {

        if (tvName.getText().toString().isEmpty()) {
            tvName.setError("Name Required");
            return;
        }



        verifyotp(view);

//        convertImagetoString(tvName.getText().toString().trim());
    }
//
    private void verifyotp(final View v) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(AddSignatureActivity.this)) {

            //final String mobileNumber = tvName.getText().toString();
            UtilMethods.INSTANCE.upDateData(AddSignatureActivity.this,tvName.getText().toString(),strId.getOrder_request_id(), new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    UpDAteData otpModel = new Gson().fromJson(message, UpDAteData.class);
                    if (otpModel.getStatus().equalsIgnoreCase("success")) {
                        Toast.makeText(AddSignatureActivity.this, "Your Service Completed...!", Toast.LENGTH_SHORT).show();
                       // OpenDialogFwd(otpModel);
                        tvName.setText("");
                        UtilMethods.INSTANCE.upDateStatus(AddSignatureActivity.this, strId.getOrderId(), new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                Toast.makeText(AddSignatureActivity.this, "Your Status is Completed...!", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void fail(String from) {
                                Toast.makeText(AddSignatureActivity.this, "Your Status is not Completed...!", Toast.LENGTH_SHORT).show();

                            }
                        });

                    } else {
                        Toast.makeText(AddSignatureActivity.this, "Your Service  not Completed...!"+otpModel.getMessage() , Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void fail(String from) {

                }
            });

        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(AddSignatureActivity.this);
        }
    }

    public void OpenDialogFwd(OTPModel otpModel) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.enterotp, null);

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
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton, otpModel)) {
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
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton, otpModel)) {
                    return;
                }

                orderUpdate();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public boolean validateMobileFwp(EditText edMobileFwp, TextInputLayout tilMobileFwp, Button FwdokButton, OTPModel otpModel){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError("Can not be Empty");
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().length()==4)){
            tilMobileFwp.setError("Length should be 4");
            requestFocus(edMobileFwp);
            return false;
        }else if (!(edMobileFwp.getText().toString().trim().equalsIgnoreCase(otpModel.getOtp()))){
            tilMobileFwp.setError("Otp not match");
            requestFocus(edMobileFwp);
            return false;
        }{
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

    public void convertImagetoString(String signby) {

        Bitmap bm = mSignaturePad.getSignatureBitmap();
        class Converter extends AsyncTask<Void, Void, byte[]> {

            @Override
            protected byte[] doInBackground(Void... voids) {

                //create a file to write bitmap data
                Date date = new Date();
                SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
                String filename = fmt.format(date);
                file = new File(AddSignatureActivity.this.getCacheDir(), filename);
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
//                 String temp= Base64.encodeToString(b, Base64.DEFAULT);
                return b;
            }

            @Override
            protected void onPostExecute(byte[] aVoid) {
//                 orderUpdate(aVoid);
                super.onPostExecute(aVoid);
            }
        }

        new Converter().execute();
    }

//    private void orderUpdate(String aVoid, String signby) {
//
//        final Dialog dialog = new Dialog(AddSignatureActivity.this);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.default_progress_dialog);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
//        DoubleBounce doubleBounce = new DoubleBounce();
//        progressBar.setIndeterminateDrawable(doubleBounce);
//
//        class  Upload extends  AsyncTask<String, String, StringBuilder> {
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                dialog.show();
//
//            }
//
//            @Override
//            protected StringBuilder doInBackground(String... strings) {
//
//                try {
//                    HttpClient httpClient = new DefaultHttpClient();
//                    HttpPost httpPost = new HttpPost("https://gobinto.com/api.php");
//                    MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//                    entity.addPart("orderupdate", new StringBody("1"));
//                    entity.addPart("orderid", new StringBody(strId));
//                    entity.addPart("image", new StringBody(aVoid));
//                    entity.addPart("signedby", new StringBody(signby));
//                    httpPost.setEntity(entity);
//                    httpClient.execute(httpPost);
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                } catch (ClientProtocolException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                return null;
//            }
//        }
//    }


    public void orderUpdate(/*byte[] image*/) {
        //write the bytes in file
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(this.file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            fos.write(image);
//            fos.flush();
//            fos.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), reqFile);

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            UtilMethods.INSTANCE.OrderUpdate2(this, strId.getOrderId(), tvName.getText().toString().trim(), new mCallBackResponse() {

                @Override
                public void success(String from, String message) {

                    SweetAlertDialog dialog = new SweetAlertDialog(AddSignatureActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                    dialog.setCancelable(false);
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.setContentText("Updated Successfully");
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Intent intent = new Intent(AddSignatureActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                    });
                    dialog.show();
                }

                @Override
                public void fail(String from) {
                    Toast.makeText(AddSignatureActivity.this, getResources().getString(R.string.no_rcord_found), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "Check your Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
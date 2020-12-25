package com.smartwebarts.mech24ra.retrofit;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.gson.Gson;
import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.R;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public enum UtilMethods {

    INSTANCE;
My_order_model order_request;
    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void internetNotAvailableMessage(Context context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog .setContentText("Internet Not Available");
        dialog.show();
    }

    public boolean isValidMobile(String mobile) {

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String mobilePattern = "^(?:0091|\\\\+91|0)[7-9][0-9]{9}$";
        String mobileSecPattern = "[7-9][0-9]{9}$";

        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public void Login(final Context context, String mobile, String password, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<LoginResponse> call = git.login(mobile,"user",password);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    dialog.dismiss();
                   String strResponse = new Gson().toJson(response.body());
                   Log.e("strResponse",strResponse);
                   if (response.body()!=null) {
                       if (response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                           callBackResponse.success("", strResponse);
                       }
                       else {
                           callBackResponse.fail(response.body().getStatus());
                       }
                   } else {
                       callBackResponse.fail("Invalid UserId or Password");
                   }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void TodayOrder(final Context context, String id, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<My_order_model>> call = git.TodayOrder("1", id);
            call.enqueue(new Callback<List<My_order_model>>() {
                @Override
                public void onResponse(Call<List<My_order_model>> call, Response<List<My_order_model>> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0 ) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
//                            callBackResponse.fail("No orders");
                            callBackResponse.success("", strResponse);
                        }
                    } else {
//                        callBackResponse.fail("No orders");
                        callBackResponse.success("", strResponse);
                    }
                }

                @Override
                public void onFailure(Call<List<My_order_model>> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


    public void setActive(final Context context, String userid, String status, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<StatusModel> call = git.setActive("1", status, userid);
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage()!=null ) {
                            callBackResponse.success("", response.body().getMessage());
                        }
                        else {
                            callBackResponse.fail("No orders");
//                            callBackResponse.success("", strResponse);
                        }
                    } else {
                        callBackResponse.fail("No orders");
//                        callBackResponse.success("", strResponse);
                    }
                }

                @Override
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


    public void getActive(final Context context, String userid, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<StatusModel> call = git.getActive("1", userid);
            call.enqueue(new Callback<StatusModel>() {
                @Override
                public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage()!=null) {
                            callBackResponse.success("", response.body().getMessage());
                        }
                        else {
//                            callBackResponse.fail("No orders");
                            callBackResponse.success("", strResponse);
                        }
                    } else {
//                        callBackResponse.fail("No orders");
                        callBackResponse.success("", strResponse);
                    }
                }

                @Override
                public void onFailure(Call<StatusModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


    public void returnProductByOrderId(final Context context, String orderid, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<MessageModel> call = git.returnProductByOrderId( orderid);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMsg()!=null && response.body().getMsg().equalsIgnoreCase("sucess") ) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
//                            callBackResponse.fail("No orders");
                            callBackResponse.fail(response.body().getMsg());
                        }
                    } else {
//                        callBackResponse.fail("No orders");
                        callBackResponse.fail(response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void returnProductByProductID(final Context context, String productID, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<MessageModel> call = git.returnProductByProId( productID);
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMsg()!=null && response.body().getMsg().equalsIgnoreCase("sucess") ) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
//                            callBackResponse.fail("No orders");
                            callBackResponse.fail(response.body().getMsg());
                        }
                    } else {
//                        callBackResponse.fail("No orders");
                        callBackResponse.fail(response.body().getMsg());
                    }
                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void OrderDetails(final Context context, String order_request_id, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<OrderDetailModel>> call = git.OrderDetails( order_request_id);
            call.enqueue(new Callback<List<OrderDetailModel>>() {
                @Override
                public void onResponse(Call<List<OrderDetailModel>> call, Response<List<OrderDetailModel>> response) {

                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0 ) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail("No orders");
                        }
                    } else {
                        callBackResponse.fail("No orders");
                    }
                }

                @Override
                public void onFailure(Call<List<OrderDetailModel>> call, Throwable t) {
                    dialog.dismiss();
                    callBackResponse.fail(t.getMessage());
                }
            });

        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
        }

    }

    public void OrderUpdate(final Context context, String orderid, MultipartBody.Part image, String signedby, Bitmap bm, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        Date date = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
        String s = fmt.format(date);

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OrderUpdateModel> call = git.OrderUpdate("1", orderid, image, signedby);
            call.enqueue(new Callback<OrderUpdateModel>() {
                @Override
                public void onResponse(Call<OrderUpdateModel> call, Response<OrderUpdateModel> response) {

                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail("No orders");
                        }
                    } else {
                        callBackResponse.fail("No orders");
                    }
                }

                @Override
                public void onFailure(Call<OrderUpdateModel> call, Throwable t) {
                    dialog.dismiss();
                    callBackResponse.fail(t.getMessage());
                }
            });

        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
        }

    }

    public void OrderUpdate2(final Context context, String orderid, String signedby, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

//        Date date = new Date();
//        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddhhmmss");
//        String s = fmt.format(date);

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OrderUpdateModel> call = git.OrderUpdate2("1", orderid, signedby);
            call.enqueue(new Callback<OrderUpdateModel>() {
                @Override
                public void onResponse(Call<OrderUpdateModel> call, Response<OrderUpdateModel> response) {

                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().getMessage() != null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail("No orders");
                        }
                    } else {
                        callBackResponse.fail("No orders");
                    }
                }

                @Override
                public void onFailure(Call<OrderUpdateModel> call, Throwable t) {
                    dialog.dismiss();
                    callBackResponse.fail(t.getMessage());
                }
            });

        } catch (Exception e) {
            dialog.dismiss();
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
        }

    }

    public void OrderHistory(final Context context, String userid, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<List<My_order_model>> call = git.OrderHistory("1", userid);
            call.enqueue(new Callback<List<My_order_model>>() {
                @Override
                public void onResponse(Call<List<My_order_model>> call, Response<List<My_order_model>> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body().size()>0 ) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail("No orders");
                        }
                    } else {
                        callBackResponse.fail("No orders");
                    }
                }

                @Override
                public void onFailure(Call<List<My_order_model>> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void order(final Context context, DeliveryProductDetails data, String totalamount, String discount, String deliverycharge, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OrderedResponse> call = git.order(data.getId(),
                    data.getQty(), data.getProId(), data.getAmount(), data.getName(), data.getUnit(), data.getUnit_in()
                    ,data.getThumbnail(), data.getMobile(), data.getOrderid(), "1", data.getPaymentmethod(), data.getAddress(),
                    data.getLandmark(), data.getPincode(), data.getUserdate(), data.getUsertime(), totalamount, discount, deliverycharge);
            call.enqueue(new Callback<OrderedResponse>() {
                @Override
                public void onResponse(Call<OrderedResponse> call, Response<OrderedResponse> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(Call<OrderedResponse> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void verifyotp(final Context context,  String mobile, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OTPModel> call = git.verifyotp( mobile, "1");
            call.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);

                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }
    public void upDateData(final Context context, String recieved_by, String order_id, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            AppSharedPreferences preferences = new AppSharedPreferences();

            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);

            Call<UpDAteData> call = git.updateData(order_id,"order_request",preferences.getLoginUserApiKey(context),preferences.getLoginUserLoginId(context),recieved_by);
            call.enqueue(new Callback<UpDAteData>() {
                @Override
                public void onResponse(Call<UpDAteData> call, Response<UpDAteData> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);

                        }
                        else {
                            callBackResponse.fail(response.body().getStatus());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(Call<UpDAteData> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }
    public void upDateStatus(final Context context, String order_id, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            AppSharedPreferences preferences = new AppSharedPreferences();

            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);

            Call<UpDAteStatus> call = git.updateStatus(preferences.getLoginUserApiKey(context),preferences.getLoginUserLoginId(context),order_id);
            call.enqueue(new Callback<UpDAteStatus>() {
                @Override
                public void onResponse(Call<UpDAteStatus> call, Response<UpDAteStatus> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);

                        }
                        else {
                            callBackResponse.fail(response.body().getStatus());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(Call<UpDAteStatus> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

    public void updateAccessToken(Context context, String token,final mCallBackResponse callBackResponse) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();
            try {

                AppSharedPreferences preferences = new AppSharedPreferences();
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<MessageModel2> call = git.updateAccessToken(preferences.getLoginUserLoginId(context),preferences.getLoginUserLoginId(context),preferences.getLoginUserApiKey(context),"user",token);

                call.enqueue(new Callback<MessageModel2>() {
                    @Override
                    public void onResponse(@NotNull Call<MessageModel2> call, @NotNull Response<MessageModel2> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null && response.body().getStatus()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                            callBackResponse.success("", response.body().getStatus());
                        } else {
                            callBackResponse.fail(""+response.body().getStatus());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<MessageModel2> call, @NotNull Throwable t) {
//                        callBackResponse.fail(t.getMessage());
//                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
//                callBackResponse.fail(e.getMessage());
//                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }

    public void signup(final Context context, String firstname, String lastname,String mobile,String password, final mCallBackResponse callBackResponse) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context))
        {
            final Dialog dialog = getProgressDialog(context);
            dialog.show();

            try {
                EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
                Call<SignUpModel> call = git.signup("user", "vendor",firstname+" "+lastname,mobile,password,"pending");
                call.enqueue(new Callback<SignUpModel>() {
                    @Override
                    public void onResponse(Call<SignUpModel> call, Response<SignUpModel> response) {
                        dialog.dismiss();
                        String strResponse = new Gson().toJson(response.body());
                        Log.e("strResponse",strResponse);
                        if (response.body()!=null) {
                            if (response.body()!=null && response.body().getStatus().equalsIgnoreCase("success")) {
                                callBackResponse.success("", response.body().getMessage());
                            }
                            else {
                                callBackResponse.fail(response.body().getMessage());
                            }
                        } else {
                            callBackResponse.fail("Invalid UserId or Password");
                        }
                    }

                    @Override
                    public void onFailure(Call<SignUpModel> call, Throwable t) {
                        callBackResponse.fail(t.getMessage());
                        dialog.dismiss();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                callBackResponse.fail(e.getMessage());
                dialog.dismiss();
            }
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }

    }
    private Dialog getProgressDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        return dialog;
    }


    public void otp(final Context context,  String mobile, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OTPModel> call = git.otp( mobile, "1");
            call.enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid UserId or Password");
                    }
                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }


    public void changePassword(final Context context, String number, String newpassword, final mCallBackResponse callBackResponse) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.default_progress_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.progress);
        DoubleBounce doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);
        dialog.show();

        try {
            EndPointInterface git = APIClient.getClient().create(EndPointInterface.class);
            Call<OTPModel2> call = git.changePassword( number,newpassword);
            call.enqueue(new Callback<OTPModel2>() {
                @Override
                public void onResponse(Call<OTPModel2> call, Response<OTPModel2> response) {
                    dialog.dismiss();
                    String strResponse = new Gson().toJson(response.body());
                    Log.e("strResponse",strResponse);
                    if (response.body()!=null) {
                        if (response.body()!=null && response.body().getMessage().equalsIgnoreCase("success")) {
                            callBackResponse.success("", strResponse);
                        }
                        else {
                            callBackResponse.fail(response.body().getMessage());
                        }
                    } else {
                        callBackResponse.fail("Invalid Mobile Number");
                    }
                }

                @Override
                public void onFailure(Call<OTPModel2> call, Throwable t) {
                    callBackResponse.fail(t.getMessage());
                    dialog.dismiss();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            callBackResponse.fail(e.getMessage());
            dialog.dismiss();
        }

    }

}

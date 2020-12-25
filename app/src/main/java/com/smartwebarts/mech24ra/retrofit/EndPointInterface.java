package com.smartwebarts.mech24ra.retrofit;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface EndPointInterface {

    @POST("controller/api/logiin_logout/LoginByApp.php")
    @FormUrlEncoded
    Call<LoginResponse> login(@Field("userid") String userid,
                              @Field("tbname") String tbname,
                              @Field("password") String password);

    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<List<My_order_model>> TodayOrder(@Field("homepage") String homepage,
                                          @Field("id") String userid);

    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<StatusModel> setActive(@Field("updatestatus") String updatestatus,
                                @Field("status") String status,
                                          @Field("userid") String userid);
    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<StatusModel> getActive(@Field("homepagestatus") String homepage,
                                          @Field("id") String userid);


    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<List<OrderDetailModel>> OrderDetails(
                                       @Field("order_request_id") String orderid);





    @Multipart
    @POST("controller/api/common/api.php")
    Call<OrderUpdateModel> OrderUpdate(@Part("orderupdate") String orderupdate,
                                       @Part("orderid") String orderid,
                                       @Part MultipartBody.Part image,
                                       @Part("signedby") String signedby);

    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<OrderUpdateModel> OrderUpdate2(@Field("orderupdate") String orderupdate,
                                        @Field("orderid") String orderid,
                                        @Field("signedby") String signedby);

    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<List<My_order_model>> OrderHistory(@Field("orderhistory") String homepage,
                                            @Field("id") String userid);

    @GET("API/Returnproduct_byorderid/{id}")
    Call<MessageModel> returnProductByOrderId(@Path("id") String userid);

    @GET("API/returnproductbyid/{id}")
    Call<MessageModel> returnProductByProId(@Path("id") String userid);


    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<OrderedResponse> order(@Field("id") String id,
                                @Field("qty") String qty,
                                @Field("proId") String proId,
                                @Field("amount") String amount,
                                @Field("name") String name,
                                @Field("unit") String unit,
                                @Field("unit_in") String unit_in,
                                @Field("thumbnail") String thumbnail,
                                @Field("mobile") String mobile,
                                @Field("orderid") String orderid,
                                @Field("addcheckout") String checkout,
                                @Field("paymentmethod") String paymentmethod,
                                @Field("address") String address,
                                @Field("landmark") String landmark,
                                @Field("pincode") String pincode,
                                @Field("userdate") String userdate,
                                @Field("usertime") String usertime,
                                @Field("totalamount") String totalamount,
                                @Field("discount") String discount,
                                @Field("deliverycharge") String deliverycharge
    );



    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<OTPModel> verifyotp(@Field("mobile") String mobile,
                             @Field("verifyotp") String verifyotp);

    @POST("controller/api/common/update.php")
    @FormUrlEncoded
    Call<UpDAteData> updateData(@Field("id") String id,
                              @Field("tbname") String tbname,
                              @Field("api_key") String api_key,
                              @Field("loginid") String loginid,
                              @Field("recieved_by") String recieved_by);

    @POST("controller/api/common/updatestatus.php")
    @FormUrlEncoded
    Call<UpDAteStatus>updateStatus(@Field("api_key") String api_key,
                                @Field("loginid") String loginid,
                                @Field("orderid") String orderid);






    @POST("controller/api/common/insert.php")
    @FormUrlEncoded
    Call<SignUpModel> signup(@Field("tbname") String tbname,
                             @Field("role") String role,
                             @Field("name") String name,
                             @Field("contact") String mobile,
                             @Field("password") String password,
                             @Field("status") String status
    );

    @POST("controller/api/common/update.php")
    @FormUrlEncoded
    Call<MessageModel2> updateAccessToken(@Field("id") String id,
                                         @Field("loginid") String loginid,
                                         @Field("api_key") String api_key,
                                         @Field("tbname") String tbname,
                                         @Field("access_token") String access_token);


    @POST("controller/api/common/forget_password.php")
    @FormUrlEncoded
    Call<OTPModel2> changePassword(
                                  @Field("password") String password,
                                  @Field("contact") String contact);


    @POST("controller/api/common/api.php")
    @FormUrlEncoded
    Call<OTPModel> otp(@Field("mobile") String mobile,
                       @Field("sms") String sms);


}

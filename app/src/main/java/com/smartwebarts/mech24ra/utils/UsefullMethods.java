package com.smartwebarts.mech24ra.utils;

import android.app.Activity;

import com.github.dhaval2404.imagepicker.ImagePicker;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UsefullMethods {

    public static void showMessage(Activity from, int type, String title, String content, String confirmationtext, MyClick callBackResponse) {
        new SweetAlertDialog(from, type)
                .setTitleText(title)
                .setContentText(content)
                .setConfirmText(confirmationtext)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();

                        if (callBackResponse !=null)
                            callBackResponse.onClick();
                    }
                })
                .show();
    }

    public static void takePicture(Activity context, int code){
        ImagePicker.Companion.with(context)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start(code);
    }

}

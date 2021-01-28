package com.smartwebarts.mech24ra.activity.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.smartwebarts.mech24ra.AppSharedPreferences;
import com.smartwebarts.mech24ra.R;
import com.smartwebarts.mech24ra.retrofit.AddressModel;
import com.smartwebarts.mech24ra.retrofit.UtilMethods;
import com.smartwebarts.mech24ra.retrofit.mCallBackResponse;
import com.smartwebarts.mech24ra.utils.MyClick;
import com.smartwebarts.mech24ra.utils.MyGlide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.seatgeek.placesautocomplete.DetailsCallback;
import com.seatgeek.placesautocomplete.OnPlaceSelectedListener;
import com.seatgeek.placesautocomplete.PlacesAutocompleteTextView;
import com.seatgeek.placesautocomplete.model.AddressComponent;
import com.seatgeek.placesautocomplete.model.AddressComponentType;
import com.seatgeek.placesautocomplete.model.Place;
import com.seatgeek.placesautocomplete.model.PlaceDetails;
import com.smartwebarts.mech24ra.utils.UsefullMethods;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.smartwebarts.mech24ra.retrofit.APIClient.USER_PROFILE_IMAGE;
import static com.smartwebarts.mech24ra.utils.UsefullMethods.takePicture;

public class MyProfileFragment extends AppCompatActivity implements View.OnClickListener {

    TextView Username, UserMobile, userEmail, userAddress, shopName, gstNumber, personalinfobtn;
    ImageView shopPic, gstPic, userProfilePic, adhaarPic, mechanicPic, refresh;
    private FloatingActionButton fabAddPhoto, fabAddGalleryPhoto, fabAddGstPhoto, fabAddMechanicPhoto, fabAddAdhaarPhoto;
    AppSharedPreferences preferences;
    private TextView mechanicName, adhaarNumber;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_profile_fragment);
        init();
        addListeners();
        setProfileData();
    }

    private void setProfileData() {
        gstNumber.setText(preferences.getLoginVehicle(this));
        Username.setText(preferences.getLoginUserName(this));
        UserMobile.setText(preferences.getLoginMobile(this));
        userEmail.setText(preferences.getLoginEmail(this));
        userAddress.setText(preferences.getLoginUserAddress(this));
        shopName.setText("Driving License");
        gstNumber.setText(preferences.getLoginVehicle(this)!=null?preferences.getLoginVehicle(this).equalsIgnoreCase("NA")?"Vehicle Info":preferences.getLoginVehicle(this):"Vehicle Info");
        personalinfobtn.setText(preferences.getLoginUserStatus(this));
        MyGlide.profile(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserImage(this)), userProfilePic);
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserVehicleImage(this)), shopPic);
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserLicense(this)), gstPic);
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserAdhaarImage(this)), adhaarPic);
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserMechanicImage(this)), mechanicPic);
    }

    private Activity requireActivity() {
        return this;
    }

    private void addListeners() {
        fabAddGalleryPhoto.setOnClickListener(this);
        gstPic.setOnClickListener(this);
        fabAddPhoto.setOnClickListener(this);
        fabAddGstPhoto.setOnClickListener(this);
        fabAddAdhaarPhoto.setOnClickListener(this);
        fabAddMechanicPhoto.setOnClickListener(this);
        userAddress.setOnClickListener(this);
        refresh.setOnClickListener(this);
    }

    private void init() {
        Username = findViewById(R.id.Username);
        UserMobile = findViewById(R.id.UserMobile);
        userEmail = findViewById(R.id.userEmail);
        userAddress = findViewById(R.id.userAddress);
        shopName = findViewById(R.id.shopName);
        personalinfobtn = findViewById(R.id.personalinfobtn);
        gstNumber = findViewById(R.id.gstNumber);
        mechanicName = findViewById(R.id.mechanicName);
        adhaarNumber = findViewById(R.id.adhaarNumber);

        shopPic = findViewById(R.id.shopPic);
        gstPic = findViewById(R.id.backPic);
        userProfilePic = findViewById(R.id.userProfilePic);
        adhaarPic = findViewById(R.id.adhaarPic);
        mechanicPic = findViewById(R.id.mechanicPic);
        refresh = findViewById(R.id.refresh);

        fabAddPhoto = findViewById(R.id.fab_add_photo);
        fabAddGalleryPhoto = findViewById(R.id.fab_add_gallery_photo);
        fabAddGstPhoto = findViewById(R.id.fab_add_gst_photo);
        fabAddAdhaarPhoto = findViewById(R.id.fab_add_adhaar_photo);
        fabAddMechanicPhoto = findViewById(R.id.fab_add_mechanic_photo);

        preferences = new AppSharedPreferences();
    }


    @Override
    public void onClick(View v) {
        if (v == fabAddGstPhoto ) {
            takePicture(this, 101);
        } if( v == fabAddGalleryPhoto ) {
            takePicture(this, 102);
        } if( v == fabAddPhoto) {
            takePicture(this, 103);
        } if( v == fabAddAdhaarPhoto ) {
            takePicture(this, 104);
        } if( v == fabAddMechanicPhoto) {
            takePicture(this, 105);
        } if( v == userAddress) {
            OpenDialogFwd(v);
        } if( v == refresh) {
            RotateAnimation rotate = new RotateAnimation(0, 180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(2000);
            rotate.setInterpolator(new LinearInterpolator());
            refresh.startAnimation(rotate);
            updateProfile();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            switch (requestCode) {
                case 101: {
                    MyGlide.with(requireActivity(), data.getData(), gstPic);
                    try {
                        File file = new File(data.getData().getPath());
                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "driving_license", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 102: {
                    MyGlide.with(requireActivity(), data.getData(), shopPic);

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "shopimage",  new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 103: {
                    MyGlide.profile(requireActivity(), data.getData(), userProfilePic);

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "image", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);
                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 104: {
                    MyGlide.with(requireActivity(), data.getData(), adhaarPic);

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "adhaarimage",  new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 105: {
                    MyGlide.with(requireActivity(), data.getData(), mechanicPic);

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "gstimage", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);
                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }
            }
        }
    }

    public void updateProfile() {
        UtilMethods.INSTANCE.userdetail(requireActivity(), new mCallBackResponse() {
            @Override
            public void success(String from, String message) {
                setProfileData();
            }

            @Override
            public void fail(String from) {

            }
        });
    }

    public void OpenDialogFwd(View v1) {

        LayoutInflater inflater = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialogaddress, null);

        TextInputEditText txtArea = view.findViewById(R.id.txtArea);
        TextInputEditText txtCity = view.findViewById(R.id.txtCity);
        TextInputEditText txtHouse = view.findViewById(R.id.txtHouse);
        TextInputEditText txtPin = view.findViewById(R.id.txtPincode);
        Button FwdokButton = (Button) view.findViewById(R.id.okButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);
        PlacesAutocompleteTextView placesAutocomplete = view.findViewById(R.id.places_autocomplete);

        placesAutocomplete.setOnPlaceSelectedListener(
                new OnPlaceSelectedListener() {
                    @Override
                    public void onPlaceSelected(final Place place) {
                        // do something awesome with the selected place
                        placesAutocomplete.getDetailsFor(place, new DetailsCallback() {
                            @Override
                            public void onSuccess(PlaceDetails details) {
                                Log.d("test", "details " + details);
//                                mStreet.setText(details.name);
                                for (AddressComponent component : details.address_components) {
                                    for (AddressComponentType type : component.types) {
                                        switch (type) {
                                            case STREET_NUMBER:
                                                break;
                                            case ROUTE:
                                                break;
                                            case NEIGHBORHOOD:
                                                break;
                                            case SUBLOCALITY_LEVEL_1:
                                                break;
                                            case SUBLOCALITY:
                                                txtArea.append(" "+component.long_name);
                                                break;
                                            case LOCALITY:
                                                txtCity.setText(component.long_name);
                                                break;
                                            case ADMINISTRATIVE_AREA_LEVEL_1:
//                                                txtArea.setText(component.short_name);
                                                break;
                                            case ADMINISTRATIVE_AREA_LEVEL_2:
                                                break;
                                            case COUNTRY:
                                                break;
                                            case POSTAL_CODE:
                                                txtPin.setText(component.long_name);
                                                break;
                                            case POLITICAL:
                                                break;
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Throwable throwable) {

                            }
                        });
                    }
                }
        );
        final Dialog dialog = new Dialog(requireActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (!validate()) {
                    return;
                }

                if (UtilMethods.INSTANCE.isNetworkAvialable(requireActivity())) {


                    UtilMethods.INSTANCE.setAddress(requireActivity(),
                            txtArea.getText().toString().trim(),
                            txtCity.getText().toString().trim(),
                            txtHouse.getText().toString().trim(),
                            txtPin.getText().toString().trim(),
                            placesAutocomplete.getText().toString().trim(),
                            new mCallBackResponse() {
                                @Override
                                public void success(String from, String message) {
                                    getAddress((TextView) v1);
                                }

                                @Override
                                public void fail(String from) {

                                }
                            });

                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(requireActivity());
                }

                dialog.dismiss();
            }

            private boolean validate() {

                if (txtArea.getText().toString().isEmpty()){
                    txtArea.setError("Field Required");
                    return false;
                }
                if (txtPin.getText().toString().isEmpty()){
                    txtPin.setError("Field Required");
                    return false;
                }
                if (txtCity.getText().toString().isEmpty()){
                    txtCity.setError("Field Required");
                    return false;
                }
                if (txtHouse.getText().toString().isEmpty()){
                    txtHouse.setError("Field Required");
                    return false;
                }
                if (placesAutocomplete.getText().toString().isEmpty()){
                    placesAutocomplete.setError("Field Required");
                    return false;
                }
                return true;
            }
        });

        dialog.show();
    }

    private void getAddress(TextView address) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(requireActivity())) {
            UtilMethods.INSTANCE.getaddress(requireActivity()
                    , new mCallBackResponse() {
                        @Override
                        public void success(String from, String message) {
                            Type type = new TypeToken<List<AddressModel>>(){}.getType();
                            List<AddressModel> list = new Gson().fromJson(message, type);
                            AddressModel addressModel = list.get(0);
                            try {
                                if (addressModel.getAddress()!=null) {
                                    address.setText(String.format("%s, %s, %s", addressModel.getAddress(), addressModel.getCity(), addressModel.getPincode()));
                                } else {
                                    address.setText(getString(R.string.add_an_address));
                                }
                            } catch (Exception ignored) {
                                address.setText(getString(R.string.add_an_address));
                            }

                        }

                        @Override
                        public void fail(String from) {
                            address.setText(getString(R.string.add_an_address));
                        }
                    });
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(requireActivity());
        }
    }

    public void back(View view) {
        onBackPressed();
    }
}
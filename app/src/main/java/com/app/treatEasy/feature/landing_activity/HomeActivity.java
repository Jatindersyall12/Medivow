package com.app.treatEasy.feature.landing_activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.treatEasy.R;
import com.app.treatEasy.apputils.AppConstants;
import com.app.treatEasy.baseui.BaseActivity;
import com.app.treatEasy.booking.BookingActivity;
import com.app.treatEasy.feature.edit_profile.EditProfileActivity;
import com.app.treatEasy.feature.family_member.FamilyMemberActivity;
import com.app.treatEasy.feature.home_screen.HomeFragment;
import com.app.treatEasy.feature.login_module.TermAndConditionActivity;
import com.app.treatEasy.feature.login_module.login_screen.LoginActivity;
import com.app.treatEasy.feature.profile.ProfileActivity;
import com.app.treatEasy.feature.scan_qr.ScanQRCodeActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.home.AboutActivity;
import com.app.treatEasy.home.PrivacyActivity;
import com.app.treatEasy.home.RefundActivity;
import com.app.treatEasy.notification.NotificationActivity;
import com.app.treatEasy.payment.PaymentHistoryActivity;
import com.app.treatEasy.payment.PaymentRes;
import com.app.treatEasy.payment.RechargeHistoryActivity;
import com.app.treatEasy.preference.AppPreferences;
import com.app.treatEasy.search.SearchActivity;
import com.app.treatEasy.state.CityAdapter;
import com.app.treatEasy.state.CityResponseModel;
import com.app.treatEasy.state.CitySendModel;
import com.app.treatEasy.state.RetrofitClient;
import com.app.treatEasy.state.SpinAdapter;
import com.app.treatEasy.state.StateResponseModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vinod Kumar (Aug 2019).
 */

public class HomeActivity extends BaseActivity implements AppConstants,
        NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private FragmentManager fragmentManager;
    private Spinner spState, spCity, spDoctor;
    private ImageButton imgButton;
    List<StateResponseModel.Datum> stateList;
    List<CityResponseModel.Datum> cityList;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.nav_view);
        imgButton = findViewById(R.id.imgButton);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        drawerLayout = findViewById(R.id.drawer_layout);

        navigationView.setNavigationItemSelectedListener(this);

        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ScanQRCodeActivity.class);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                                     @Override
                                                                     public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                                         switch (item.getItemId()) {
                                                                             case R.id.home:
                                                                                 loadFragment(new HomeFragment());
                                                                                 break;
                                                                             case R.id.profile:
                                                                                 switchActivity(ProfileActivity.class);
                                                                                 break;
                                                                             case R.id.setting:
                                                                                 drawerLayout.open();
                                                                                 break;
                                                                             case R.id.path:
                                                                                 switchActivity(RechargeHistoryActivity.class);
                                                                                 break;
                                                                         }
                                                                         return false;
                                                                     }
                                                                 }
        );
        findViewById(R.id.toolbar_up_btn_fragment).setOnClickListener(v -> openDrawer());
        findViewById(R.id.user_image).setOnClickListener(v -> switchActivity(ProfileActivity.class));
        findViewById(R.id.imgSearch).setOnClickListener(v -> switchActivity(SearchActivity.class));
        findViewById(R.id.imgNotification).setOnClickListener(v -> switchActivity(NotificationActivity.class));
        findViewById(R.id.img_user).setOnClickListener(v -> showCallDialog());
        // AppPreferences.getPreferenceInstance(getActivity()).setPayment(response.body().getData().getWalletAmount());
        findViewById(R.id.toolbar_text).setOnClickListener(v -> showCallDialog());

        try {
            if (!AppPreferences.getPreferenceInstance(this).getUserName().equals(""))
                setUpToolBar(AppPreferences.getPreferenceInstance(this).getUserName(), false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        navigationView.getHeaderView(0).findViewById(R.id.navigation_header_container).setOnClickListener(this);

        if (!AppPreferences.getPreferenceInstance(this).getUserImage().equals("")) {
            //  if (!parseUserData().profileImage.isEmpty())
            Log.d("imageUrl", AppPreferences.getPreferenceInstance(this).getUserImage());
            setImage(AppPreferences.getPreferenceInstance(this).getUserImage(), navigationView.getHeaderView(0).findViewById(R.id.user_image_header));
        }

        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_name)).setText(AppPreferences.getPreferenceInstance(this).getUserName());
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_user_name)).setText(AppPreferences.getPreferenceInstance(this).getUserName());
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tv_email)).setText(AppPreferences.getPreferenceInstance(this).getMobile());
        ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderAmount)).setText(AppPreferences.getPreferenceInstance(this).getPayment());
        ((Button) navigationView.getHeaderView(0).findViewById(R.id.btnAddMore)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(WalletActivity.class);
            }
        });
        ((LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.llLogOut)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showLogoutDialog();

            }
        });

        ((LinearLayout) navigationView.getHeaderView(0).findViewById(R.id.llHeader)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivity(EditProfileActivity.class);
            }
        });

        setNavigationDrawer();
        openLauncherFragment();
    }

    private void openLauncherFragment() {
        // loadFragment(new HomeFragment());
        setNavigationDrawer();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        openLauncherFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        NavigationMenuForBuyer(menuItem);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    private void NavigationMenuForBuyer(MenuItem menuItem) {
        Fragment fragment = fragmentManager.getFragments().get(0);
        switch (menuItem.getItemId()) {

            case R.id.buyer_dashboard:
                if (!(fragment instanceof HomeFragment))
                    fragment = new HomeFragment();
                break;
            case R.id.add_family_member:
                switchActivity(FamilyMemberActivity.class);
                break;
            case R.id.mnBooking:
                switchActivity(BookingActivity.class);
                break;
            case R.id.mnAbout:
                switchActivity(AboutActivity.class);
                break;
            case R.id.mnRefundCondition:
                switchActivity(RefundActivity.class);
                break;
            case R.id.mnPrivacyPolicy:
                switchActivity(PrivacyActivity.class);
                break;
            case R.id.mnTermCondition:
                switchActivity(TermAndConditionActivity.class);
                break;
            case R.id.wallet:
                //switchActivity(WalletActivity.class);
                break;
            case R.id.mnWalletHistory:
                switchActivity(RechargeHistoryActivity.class);
                break;
            case R.id.profile:
                switchActivity(ProfileActivity.class);
                break;
            case R.id.mnPaymentHistory:
                switchActivity(PaymentHistoryActivity.class);
                break;
           /* case R.id.mnRichargeHistory:
                switchActivity(RechargeHistoryActivity.class);
                break;*/
            case R.id.changeLocation:
                showLocationDialog();
                // switchActivity(ProfileActivity.class);
                break;
            case R.id.help:
                showCallDialog();
               /* AppPreferences.getPreferenceInstance(this).clearPreferenceData();
                switchActivity(LoginActivity.class);
                finish();*/
                break;
        }
        if (fragment != null && fragmentManager.getFragments().get(0) != fragment) {
            loadFragment(fragment);
        }
        drawerLayout.closeDrawer(GravityCompat.START);
    }

    /**
     * THis method opens the navigation menu drawer.
     */
    public void openDrawer() {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void loadFragment(Fragment fragment) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.navigation_header_container:

                Fragment fragment = fragmentManager.getFragments().get(0);
                /*if (!(fragment instanceof UserProfileFragment))
                    fragment = new UserProfileFragment();*/

                if (fragmentManager.getFragments().get(0) != fragment) {
                    loadFragment(fragment);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }
    }

    /**
     * THis method will load the given fragment with provided data
     *
     * @param bundle
     * @param fragment
     */
    public void openFragmentWithData(Bundle bundle, Fragment fragment) {

        fragment.setArguments(bundle);
        loadFragment(fragment);
    }

    private void setNavigationDrawer() {
        navigationView.getMenu().clear();
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        navigationView.inflateMenu(R.menu.menu_nav);
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = fragmentManager.getFragments().get(0);
        if ((fragment instanceof HomeFragment))
            super.onBackPressed();
        else {
            fragment = new HomeFragment();
            loadFragment(fragment);
        }
    }

    @SuppressLint("ResourceType")
    public void showCallDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(HomeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_help_call);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "9643691869"));
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    public void showLocationDialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(HomeActivity.this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_location);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);

        TextView tvSelectState = (TextView) dialog.findViewById(R.id.tvSelectState);
        TextView tvSelectCity = (TextView) dialog.findViewById(R.id.tvSelectCity);

        spState = dialog.findViewById(R.id.spState);
        spCity = dialog.findViewById(R.id.spCity);

        tvSelectState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectState.setVisibility(View.GONE);
                spState.setVisibility(View.VISIBLE);
                spState.performClick();
            }
        });

        tvSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSelectCity.setVisibility(View.GONE);
                spCity.setVisibility(View.VISIBLE);
                spCity.performClick();
            }
        });
        getStateList();


        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getCityList(stateList.get(i).getValue());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String location = cityList.get(i).getId();
                String city = cityList.get(i).getCityName();
                AppPreferences.getPreferenceInstance(HomeActivity.this).setUserLocation(location);
                AppPreferences.getPreferenceInstance(HomeActivity.this).setCityNAme(city);
                //  getDoctorList(location);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityList.size() > 0 && cityList != null) {
                    loadFragment(new HomeFragment());
                    dialog.dismiss();
                } else {
                    Toast.makeText(HomeActivity.this, "City Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }

    public void getStateList() {
        stateList = new ArrayList<>();
        Call<StateResponseModel> call = RetrofitClient.getInstance().getMyApi().getStateList();
        call.enqueue(new Callback<StateResponseModel>() {
            @Override
            public void onResponse(Call<StateResponseModel> call, Response<StateResponseModel> response) {
                stateList = response.body().getData();
                if (stateList != null && stateList.size() > 0) {
                    SpinAdapter adapter = new SpinAdapter(HomeActivity.this, stateList);
                    spState.setAdapter(adapter);
                    //  getCityList(stateList.get(0).getValue());
                }
            }

            @Override
            public void onFailure(Call<StateResponseModel> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getCityList(String value) {
        cityList = new ArrayList<>();
        showProgressDialog();
        CitySendModel citySend = new CitySendModel();
        citySend.setStateid(value);
        Call<CityResponseModel> call = RetrofitClient.getInstance().getMyApi().getCityList(value);
        call.enqueue(new Callback<CityResponseModel>() {
            @Override
            public void onResponse(Call<CityResponseModel> call, Response<CityResponseModel> response) {
                dismissProgressDialog();
                cityList = response.body().getData();
                if (cityList != null && cityList.size() > 0) {
                    CityAdapter adapter = new CityAdapter(HomeActivity.this, cityList);
                    spCity.setAdapter(adapter);
                    // user_state_layout.setVisibility(View.GONE);
                    // user_city_layout.setVisibility(View.VISIBLE);
                } else {
                    //  Toast.makeText(getActivity(), "No city found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CityResponseModel> call, Throwable t) {
                dismissProgressDialog();
                // Toast.makeText(getActivity(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFragment(new HomeFragment());
        getPayment(AppPreferences.getPreferenceInstance(this).getUserId());
        //  ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderAmount)).setText(AppPreferences.getPreferenceInstance(this).getPayment());
    }

    public void getPayment(String userId) {
        showProgressDialog();

        Call<PaymentRes> call = RetrofitClient.getInstance().getMyApi().getPayment(userId);
        call.enqueue(new Callback<PaymentRes>() {
            @Override
            public void onResponse(Call<PaymentRes> call, Response<PaymentRes> response) {
                dismissProgressDialog();
                if (response.body().getStatusCode() == 200) {
                    ((TextView) navigationView.getHeaderView(0).findViewById(R.id.tvHeaderAmount)).setText(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<PaymentRes> call, Throwable t) {
                dismissProgressDialog();
                Toast.makeText(getApplicationContext(), "An error has occured", Toast.LENGTH_LONG).show();
            }
        });
    }

    @SuppressLint("ResourceType")
    public void showLogoutDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.CustomDialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_delete_and_logout);

        Button callButton = (Button) dialog.findViewById(R.id.btnCall);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancel);
        TextView tvHeader = (TextView) dialog.findViewById(R.id.tvTitle);
        tvHeader.setText(getResources().getString(R.string.logout_text));

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppPreferences.getPreferenceInstance(HomeActivity.this).clearPreferenceData();
                switchActivity(LoginActivity.class);
                finish();
            }
        });

        dialog.show();
    }
}
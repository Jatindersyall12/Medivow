package com.app.treatEasy.feature.login_module.login_screen;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.feature.login_module.StateModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.feature.login_module.signup_newuser.SignUpModel;
import com.app.treatEasy.networking.ApiService;
import com.app.treatEasy.networking.BaseNetworkSubscriber;
import com.app.treatEasy.networking.Resource;
import com.app.treatEasy.preference.AppPreferences;


import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class LoginRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<LoginModel>> mLoginLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<UserDetailModel>> mSignUpLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<StateModel>>> mStateLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<UserProfileModel>> mOtpVerifyLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<UserProfileModel>> mResendOtpLiveData = new MutableLiveData<>();

    @Inject
    LoginRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void loginAction(LoginModel loginModel) {
        LoginModel.PostLoginModel model = new LoginModel.PostLoginModel();
        model.mobile = loginModel.mobileNumber;
        mLoginLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.login(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<LoginModel>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<LoginModel> res) {
                                super.onNext(res);
                                mLoginLiveData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mLoginLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<LoginModel>> getLoginData() {
        return mLoginLiveData;
    }
    public void getAllState() {
        mStateLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getState().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<StateModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<StateModel>> res) {
                                super.onNext(res);
                                mStateLiveData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mStateLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }

    public MutableLiveData<Resource<ArrayList<StateModel>>> getStateData() {
        return mStateLiveData;
    }

    public void signUpAction(UserDetailModel signUpModel) {
        SignUpModel.PostSignUpModel model = new SignUpModel.PostSignUpModel();
        model.fullName = signUpModel.userName;
        model.mobile = signUpModel.phone;
        model.dob = signUpModel.dateOfBirth;
        model.gsm_token = AppPreferences.getPreferenceInstance(mApplication).getFCMToken();
        model.pincode = signUpModel.pincode;
        mSignUpLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.signup(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<UserDetailModel>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<UserDetailModel> factsModels) {
                                super.onNext(factsModels);
                                mSignUpLiveData.setValue(Resource.success(factsModels.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mSignUpLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }

    public MutableLiveData<Resource<UserDetailModel>> getSignUpData() {
        return mSignUpLiveData;
    }

    public void verifyOTPAction(UserProfileModel verifyModel) {
        UserProfileModel.PostOtpModel model = new UserProfileModel.PostOtpModel();
        model.otp = verifyModel.otp;
        model.mobile = verifyModel.mobileNumber;
        mOtpVerifyLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.verifyOTP(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<UserProfileModel>(mApplication) {
                            @Override
                            public void onNext(UserProfileModel otpVerifyModel) {
                                super.onNext(otpVerifyModel);
                                mOtpVerifyLiveData.setValue(Resource.success(otpVerifyModel));
                            }
                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mOtpVerifyLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));
                            }
                        }));
    }
    public MutableLiveData<Resource<UserProfileModel>> getVerifyOTP() {
        return mOtpVerifyLiveData;
    }

    public void resendOTPAction(UserProfileModel.PostOtpModel verifyModel) {

        mResendOtpLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.resendOTP(verifyModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<UserProfileModel>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<UserProfileModel> factsModels) {
                                super.onNext(factsModels);
                                mResendOtpLiveData.setValue(Resource.success(factsModels.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mResendOtpLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }

    public MutableLiveData<Resource<UserProfileModel>> getResendOTPData() {
        return mResendOtpLiveData;
    }
}

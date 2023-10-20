package com.app.treatEasy.feature.login_module.otp_verification;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.feature.login_module.login_screen.LoginRepository;
import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

public class OtpVerifyViewModel extends AndroidViewModel {

    private LoginRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<UserProfileModel>> mMediatorLiveData = new MediatorLiveData<>();
    private MutableLiveData<Resource<UserProfileModel>> mResendLiveData = new MediatorLiveData<>();


    @Inject
    OtpVerifyViewModel(@NonNull Application application, LoginRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getVerifyOTP(), listResource -> {

            switch (listResource.mStatus) {
                case LOADING:
                    mMediatorLiveData.setValue(Resource.loading(null));
                    break;

                case SUCCESS:
                    mMediatorLiveData.setValue(Resource.success(listResource.mData));
                    break;

                case ERROR:
                    mMediatorLiveData.setValue(Resource.error(listResource.mMessage, null));
                    break;
            }
        });

        mMediatorLiveData.addSource(mRepository.getResendOTPData(), listResource -> {

            switch (listResource.mStatus) {
                case LOADING:
                    mResendLiveData.setValue(Resource.loading(null));
                    break;

                case SUCCESS:
                    mResendLiveData.setValue(Resource.success(listResource.mData));
                    break;

                case ERROR:
                    mResendLiveData.setValue(Resource.error(listResource.mMessage, null));
                    break;
            }
        });
    }


    public MutableLiveData<Resource<UserProfileModel>> getVerifyOTPData() {
        return mMediatorLiveData;

    }


    public void verifyOtpAction(UserProfileModel model) {
        mRepository.verifyOTPAction(model);
    }

    public void resendOTPAction(UserProfileModel.PostOtpModel verifyModel) {
        mRepository.resendOTPAction(verifyModel);
    }

    public MutableLiveData<Resource<UserProfileModel>> getResendOTPData() {
        return mResendLiveData;
    }
}

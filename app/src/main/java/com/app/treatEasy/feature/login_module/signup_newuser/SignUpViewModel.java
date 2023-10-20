package com.app.treatEasy.feature.login_module.signup_newuser;

import android.app.Application;

import com.app.treatEasy.feature.login_module.login_screen.LoginRepository;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.networking.Resource;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

public class SignUpViewModel extends AndroidViewModel {

    private LoginRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<UserDetailModel>> mMediatorLiveData = new MediatorLiveData<>();

    @Inject
    SignUpViewModel(@NonNull Application application, LoginRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getSignUpData(), listResource -> {

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
    }


    public MutableLiveData<Resource<UserDetailModel>> getUserDetail() {
        return mMediatorLiveData;

    }

    public void onClick(UserDetailModel loginUser) {
        mMediatorLiveData.setValue(Resource.validation(loginUser));
    }

    public void signUpAction(UserDetailModel model) {
        mRepository.signUpAction(model);
    }

}

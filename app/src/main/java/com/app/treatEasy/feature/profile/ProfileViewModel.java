package com.app.treatEasy.feature.profile;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

public class ProfileViewModel extends AndroidViewModel {

    private ProfileRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<UserProfileModel>> mMediatorLiveData = new MediatorLiveData<>();


    @Inject
    ProfileViewModel(@NonNull Application application, ProfileRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getUserProfileData(), listResource -> {

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


    public MutableLiveData<Resource<UserProfileModel>> getUserProfileData() {
        return mMediatorLiveData;
    }

    public void userProfileAction(String userId) {
        mRepository.userProfileAction(userId);
    }

}

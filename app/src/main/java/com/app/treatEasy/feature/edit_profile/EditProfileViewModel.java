package com.app.treatEasy.feature.edit_profile;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.feature.profile.ProfileRepository;
import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

public class EditProfileViewModel extends AndroidViewModel {

    private ProfileRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<UserProfileModel>> mMediatorLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Resource<UserProfileModel>> mUpdateProfileLiveData = new MediatorLiveData<>();
    private MediatorLiveData<Resource<BaseResponseModel>> mUploadLiveData = new MediatorLiveData<>();


    @Inject
    EditProfileViewModel(@NonNull Application application, ProfileRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mUploadLiveData.addSource(mRepository.getUploadProfileImageData(), listResource -> {

            switch (listResource.mStatus) {
                case LOADING:
                    mUploadLiveData.setValue(Resource.loading(null));
                    break;

                case SUCCESS:
                    mUploadLiveData.setValue(Resource.success(listResource.mData));
                    break;

                case ERROR:
                    mUploadLiveData.setValue(Resource.error(listResource.mMessage, null));
                    break;
            }
        });

        mUpdateProfileLiveData.addSource(mRepository.getUpdateProfileData(), listResource -> {

            switch (listResource.mStatus) {
                case LOADING:
                    mUpdateProfileLiveData.setValue(Resource.loading(null));
                    break;

                case SUCCESS:
                    mUpdateProfileLiveData.setValue(Resource.success(listResource.mData));
                    break;

                case ERROR:
                    mUpdateProfileLiveData.setValue(Resource.error(listResource.mMessage, null));
                    break;
            }
        });
    }

    public MutableLiveData<Resource<BaseResponseModel>> getUploadProfileImageData() {
        return mUploadLiveData;
    }

    public void uploadProfileImageAction(String userId, Uri uri) {
        mRepository.uploadProfileImageAction(userId, uri);
    }

    public void uploadGovtImageAction(String userId, Uri uri) {
        mRepository.uploadGovtImageAction(userId, uri);
    }

    public void updateProfileAction(UserProfileModel.PostUpdateProfile userProfileModel) {
        mRepository.updateProfileAction(userProfileModel);
    }

    public MutableLiveData<Resource<UserProfileModel>> getUpdateProfileData() {
        return mUpdateProfileLiveData;
    }

}

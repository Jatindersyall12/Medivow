package com.app.treatEasy.feature.family_member.add_family_member;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

public class AddFamilyViewModel extends AndroidViewModel {

    private AddFamilyRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<BaseResponseModel>> mMediatorLiveData = new MediatorLiveData<>();


    @Inject
    AddFamilyViewModel(@NonNull Application application, AddFamilyRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getAddFamilyMemberData(), listResource -> {

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


    public void addFamilyMember(String userId, String memberName, String dob, String relationship, Uri uri) {
        mRepository.addFamilyMember(userId, memberName, dob, relationship, uri);
    }

    public MutableLiveData<Resource<BaseResponseModel>> getAddFamilyMemberData() {
        return mMediatorLiveData;
    }

}

package com.app.treatEasy.feature.home_screen;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel {

    private HomeRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<ArrayList<BannerModel>>> mMediatorLiveData = new MediatorLiveData<>();

    @Inject
    HomeViewModel(@NonNull Application application, HomeRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }
    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getAllBannerData(), listResource -> {

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

    public MutableLiveData<Resource<ArrayList<BannerModel>>> getAllBannerData() {
        return mMediatorLiveData;

    }


    public void getAllBanner() {
        mRepository.getAllBanner();
    }
}

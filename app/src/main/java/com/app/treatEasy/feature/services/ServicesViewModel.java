package com.app.treatEasy.feature.services;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesViewModel extends AndroidViewModel {

    private ServicesRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<ArrayList<ServicesModel>>> mMediatorLiveData = new MediatorLiveData<>();


    @Inject
    ServicesViewModel(@NonNull Application application, ServicesRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getSurgeryPackagesData(), listResource -> {

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


    public MutableLiveData<Resource<ArrayList<ServicesModel>>> getServicesData() {
        return mMediatorLiveData;
    }

    public void getServices() {
        mRepository.getServices();
    }

}

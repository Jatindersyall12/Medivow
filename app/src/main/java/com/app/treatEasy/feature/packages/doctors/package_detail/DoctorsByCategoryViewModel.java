package com.app.treatEasy.feature.packages.doctors.package_detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.feature.packages.SurgeryPackagesRepository;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class DoctorsByCategoryViewModel extends AndroidViewModel {

    private SurgeryPackagesRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<ArrayList<DoctorsByCategoryModel>>> mMediatorLiveData = new MediatorLiveData<>();


    @Inject
    DoctorsByCategoryViewModel(@NonNull Application application, SurgeryPackagesRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getDoctorsByCategoryListData(), listResource -> {

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


    public MutableLiveData<Resource<ArrayList<DoctorsByCategoryModel>>> getPackageDetailListData() {
        return mMediatorLiveData;
    }

    public void getPackageDetailList(String id,String catId) {
        mRepository.getDoctorsByCategoryList(id,catId);
    }

}

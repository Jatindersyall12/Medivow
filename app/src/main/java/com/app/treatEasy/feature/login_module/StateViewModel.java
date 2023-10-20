package com.app.treatEasy.feature.login_module;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.feature.login_module.login_screen.LoginRepository;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

public class StateViewModel extends AndroidViewModel {
    private LoginRepository mRepository;
    private Application mAppContext;

    private MediatorLiveData<Resource<ArrayList<StateModel>>> mMediatorLiveData = new MediatorLiveData<>();
  @Inject
    public StateViewModel(@NonNull Application application, LoginRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getStateData(), listResource -> {

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
    public MutableLiveData<Resource<ArrayList<StateModel>>> getStateDetail() {
        return mMediatorLiveData;
    }

    public void getAllState() {
        mRepository.getAllState();
    }
}

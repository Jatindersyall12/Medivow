package com.app.treatEasy.feature.wallet;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

public class WalletViewModel extends AndroidViewModel {

    private WalletRepository mRepository;
    private Application mAppContext;
    private MediatorLiveData<Resource<WalletModel>> mMediatorLiveData = new MediatorLiveData<>();


    @Inject
    WalletViewModel(@NonNull Application application, WalletRepository repository) {
        super(application);
        mRepository = repository;
        mAppContext = application;
        observeData();
    }

    private void observeData() {
        mMediatorLiveData.addSource(mRepository.getAddMoneyToWalletData(), listResource -> {

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


    public void addMoneyToWallet(WalletModel.PostAddMoneyToWallet model) {
        mRepository.addMoneyToWallet(model);
    }

    public MutableLiveData<Resource<WalletModel>> getAddMoneyToWalletData() {
        return mMediatorLiveData;
    }

}

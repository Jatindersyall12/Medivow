package com.app.treatEasy.feature.wallet;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.networking.ApiService;
import com.app.treatEasy.networking.BaseNetworkSubscriber;
import com.app.treatEasy.networking.Resource;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class WalletRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<WalletModel>> mAddMoneyToWalletData = new MutableLiveData<>();

    @Inject
    WalletRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void addMoneyToWallet(WalletModel.PostAddMoneyToWallet model) {
        mAddMoneyToWalletData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.addMoneyToWallet(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<WalletModel>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<WalletModel> res) {
                                super.onNext(res);
                                mAddMoneyToWalletData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mAddMoneyToWalletData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<WalletModel>> getAddMoneyToWalletData() {
        return mAddMoneyToWalletData;
    }

}

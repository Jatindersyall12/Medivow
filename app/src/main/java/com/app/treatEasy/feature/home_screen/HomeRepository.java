package com.app.treatEasy.feature.home_screen;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.networking.ApiService;
import com.app.treatEasy.networking.BaseNetworkSubscriber;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class HomeRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<ArrayList<BannerModel>>> mBannerListData = new MutableLiveData<>();

    @Inject
    HomeRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void getAllBanner() {
        mBannerListData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getBanner().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<BannerModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<BannerModel>> res) {
                                super.onNext(res);
                                mBannerListData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mBannerListData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }
    public MutableLiveData<Resource<ArrayList<BannerModel>>> getAllBannerData() {
        return mBannerListData;
    }
}

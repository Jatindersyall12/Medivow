package com.app.treatEasy.feature.packages;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.feature.packages.doctor_detail.DoctorsDetailModel;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryModel;
import com.app.treatEasy.feature.packages.package_detail.PackageDetailModel;
import com.app.treatEasy.networking.ApiService;
import com.app.treatEasy.networking.BaseNetworkSubscriber;
import com.app.treatEasy.networking.Resource;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class SurgeryPackagesRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<ArrayList<SurgeryPackagesModel>>> mSurgeryPackagesLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<PackageDetailModel>>> mPackageDetailLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<DoctorsByCategoryModel>>> mDoctorsCategoryLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<ArrayList<DoctorsDetailModel>>> mDoctorDetailLiveData = new MutableLiveData<>();

    @Inject
    SurgeryPackagesRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void getServices() {
        mSurgeryPackagesLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getServices().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<SurgeryPackagesModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<SurgeryPackagesModel>> res) {
                                super.onNext(res);
                                mSurgeryPackagesLiveData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mSurgeryPackagesLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<ArrayList<SurgeryPackagesModel>>> getSurgeryPackagesData() {
        return mSurgeryPackagesLiveData;
    }

    public void getPackageDetailList(String id) {
        PackageDetailModel.PostPackageDetailModel model = new PackageDetailModel.PostPackageDetailModel();
        model.cateid = id;
        mPackageDetailLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getServicesDetail(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<PackageDetailModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<PackageDetailModel>> res) {
                                super.onNext(res);
                                mPackageDetailLiveData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mPackageDetailLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<ArrayList<PackageDetailModel>>> getPackageDetailListData() {
        return mPackageDetailLiveData;
    }

    public void getDoctorsByCategoryList(String id,String catId) {
        DoctorsByCategoryModel.PostDoctorByCategoryModel model = new DoctorsByCategoryModel.PostDoctorByCategoryModel();
        model.pkgcateid = id;
        model.pkgid = catId;
       // model.pincode = "";

        mDoctorsCategoryLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getDoctorByCategory(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<DoctorsByCategoryModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<DoctorsByCategoryModel>> res) {
                                super.onNext(res);
                                mDoctorsCategoryLiveData.setValue(Resource.success(res.mData));
                            }
                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }
                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mDoctorsCategoryLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<ArrayList<DoctorsByCategoryModel>>> getDoctorsByCategoryListData() {
        return mDoctorsCategoryLiveData;
    }

    public void getDoctorsDetail(String id) {
        DoctorsDetailModel.PostDoctorDetailModel model = new DoctorsDetailModel.PostDoctorDetailModel();
        model.doctorsid = id;
        model.pincode = "";

        mDoctorDetailLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getHospitalListByDoctorsId(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel<ArrayList<DoctorsDetailModel>>>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel<ArrayList<DoctorsDetailModel>> res) {
                                super.onNext(res);
                                mDoctorDetailLiveData.setValue(Resource.success(res.mData));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mDoctorDetailLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<ArrayList<DoctorsDetailModel>>> getDoctorsDetailData() {
        return mDoctorDetailLiveData;
    }
}

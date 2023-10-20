package com.app.treatEasy.feature.profile;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.networking.ApiService;
import com.app.treatEasy.networking.BaseNetworkSubscriber;
import com.app.treatEasy.networking.Resource;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/*Created by Vinod Kumar (Aug 2019)*/

/*This class is responsible for processing(fetch and save) of facts data used in application...*/
public class ProfileRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<UserProfileModel>> mLoginLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<BaseResponseModel>> mUploadImageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<BaseResponseModel>> mUploadGovtImageLiveData = new MutableLiveData<>();
    private final MutableLiveData<Resource<UserProfileModel>> mUpdateProfileLiveData = new MutableLiveData<>();

    @Inject
    ProfileRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void userProfileAction(String userId) {
        UserProfileModel.PostGetProfile model = new UserProfileModel.PostGetProfile();
        model.userid = userId;
        mLoginLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.getProfile(model).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<UserProfileModel>(mApplication) {
                            @Override
                            public void onNext(UserProfileModel res) {
                                super.onNext(res);
                                mLoginLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mLoginLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<UserProfileModel>> getUserProfileData() {
        return mLoginLiveData;
    }

    public void updateProfileAction(UserProfileModel.PostUpdateProfile userProfileModel) {
        mUpdateProfileLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.updateProfile(userProfileModel).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<UserProfileModel>(mApplication) {
                            @Override
                            public void onNext(UserProfileModel res) {
                                super.onNext(res);
                                mUpdateProfileLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mUpdateProfileLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }

    public MutableLiveData<Resource<UserProfileModel>> getUpdateProfileData() {
        return mUpdateProfileLiveData;
    }

    public void uploadProfileImageAction(String userId, Uri uri) {
        File mImageFile = new File(Objects.requireNonNull(uri.getPath()));
        RequestBody mImageRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                mImageFile);
        MultipartBody.Part mImageBody = MultipartBody.Part.createFormData("files",
                mImageFile.getName(), mImageRequestFile);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);


        mUploadImageLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.uploadProfileImage(userIdBody, mImageBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel res) {
                                super.onNext(res);
                                mUploadImageLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mUploadImageLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }

    public void uploadGovtImageAction(String userId, Uri uri) {
        File mImageFile = new File(Objects.requireNonNull(uri.getPath()));
        RequestBody mImageRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                mImageFile);
        MultipartBody.Part mImageBody = MultipartBody.Part.createFormData("files",
                mImageFile.getName(), mImageRequestFile);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);


        mUploadImageLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.uploadGovtIdImage(userIdBody, mImageBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel res) {
                                super.onNext(res);
                                mUploadImageLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mUploadImageLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<BaseResponseModel>> getUploadProfileImageData() {
        return mUploadImageLiveData;
    }

    public void uploadGovtProfileImageAction(String userId, Uri uri) {
        File mImageFile = new File(Objects.requireNonNull(uri.getPath()));
        RequestBody mImageRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                mImageFile);
        MultipartBody.Part mImageBody = MultipartBody.Part.createFormData("files",
                mImageFile.getName(), mImageRequestFile);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);


        mUploadGovtImageLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.uploadProfileImage(userIdBody, mImageBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel res) {
                                super.onNext(res);
                                mUploadGovtImageLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mUploadGovtImageLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<BaseResponseModel>> getUploadGovtImageData() {
        return mUploadGovtImageLiveData;
    }

}

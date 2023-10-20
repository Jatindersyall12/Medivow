package com.app.treatEasy.feature.family_member.add_family_member;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.MutableLiveData;

import com.app.treatEasy.baseui.BaseRepository;
import com.app.treatEasy.baseui.BaseResponseModel;
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
public class AddFamilyRepository extends BaseRepository {
    private final ApiService mApiService;
    private final Application mApplication;
    private final MutableLiveData<Resource<BaseResponseModel>> mAddMemberLiveData = new MutableLiveData<>();

    @Inject
    AddFamilyRepository(ApiService apiService, Application application) {
        mApiService = apiService;
        mApplication = application;
    }

    public void addFamilyMember(String userId, String memberName, String dob, String relationship, Uri uri) {
        File mImageFile = new File(Objects.requireNonNull(uri.getPath()));
        RequestBody mImageRequestFile = RequestBody.create(MediaType.parse("multipart/form-data"),
                mImageFile);
        MultipartBody.Part mImageBody = MultipartBody.Part.createFormData("files",
                mImageFile.getName(), mImageRequestFile);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId);
        RequestBody nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), memberName);
        RequestBody dobBody = RequestBody.create(MediaType.parse("multipart/form-data"), dob);
        RequestBody relationshipBody = RequestBody.create(MediaType.parse("multipart/form-data"), relationship);


        mAddMemberLiveData.setValue(Resource.loading(null));
        addSubscription(
                mApiService.addFamilyMember(userIdBody, nameBody, dobBody, relationshipBody, mImageBody).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new BaseNetworkSubscriber<BaseResponseModel>(mApplication) {
                            @Override
                            public void onNext(BaseResponseModel res) {
                                super.onNext(res);
                                mAddMemberLiveData.setValue(Resource.success(res));
                            }

                            @Override
                            public void onComplete() {
                                super.onComplete();
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                mAddMemberLiveData.setValue(Resource.error(e.getLocalizedMessage(), null));

                            }
                        }));
    }


    public MutableLiveData<Resource<BaseResponseModel>> getAddFamilyMemberData() {
        return mAddMemberLiveData;
    }
}

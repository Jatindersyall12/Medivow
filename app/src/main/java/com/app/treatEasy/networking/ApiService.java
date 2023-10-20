package com.app.treatEasy.networking;

/*Created by Vinod Kumar (Aug 2019)*/

import com.app.treatEasy.baseui.BaseResponseModel;
import com.app.treatEasy.feature.home_screen.BannerModel;
import com.app.treatEasy.feature.login_module.StateModel;
import com.app.treatEasy.feature.login_module.login_screen.LoginModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserDetailModel;
import com.app.treatEasy.feature.login_module.otp_verification.UserProfileModel;
import com.app.treatEasy.feature.login_module.signup_newuser.SignUpModel;
import com.app.treatEasy.feature.packages.SurgeryPackagesModel;
import com.app.treatEasy.feature.packages.doctor_detail.DoctorsDetailModel;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryModel;
import com.app.treatEasy.feature.packages.package_detail.PackageDetailModel;
import com.app.treatEasy.feature.services.ServicesModel;
import com.app.treatEasy.feature.wallet.WalletModel;

import java.util.ArrayList;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/*This is the service interface in which all the method define which will used for data...*/
public interface ApiService {

    @POST("GetStateList")
    Observable<BaseResponseModel<ArrayList<StateModel>>> getState();

    @POST("getLoginWithMobile")
    Observable<BaseResponseModel<LoginModel>> login(@Body LoginModel.PostLoginModel model);

    @POST("Registration")
    Observable<BaseResponseModel<UserDetailModel>> signup(@Body SignUpModel.PostSignUpModel model);

    @POST("verifyOTP")
    Observable<UserProfileModel> verifyOTP(@Body UserProfileModel.PostOtpModel model);

    @POST("resendotp")
    Observable<BaseResponseModel<UserProfileModel>> resendOTP(@Body UserProfileModel.PostOtpModel model);

    @POST("getprofile")
    Observable<UserProfileModel> getProfile(@Body UserProfileModel.PostGetProfile model);

    @POST("UpdateProfile")
    Observable<UserProfileModel> updateProfile(@Body UserProfileModel.PostUpdateProfile model);

    @Multipart
    @POST("UpdatePatientPhoto")
    Observable<BaseResponseModel> uploadProfileImage(
            @Part(value = "patientid") RequestBody patientId,
            @Part MultipartBody.Part files
    );

    @Multipart
    @POST("UpdatePatientGovtID")
    Observable<BaseResponseModel> uploadGovtIdImage(
            @Part(value = "patientid") RequestBody patientId,
            @Part MultipartBody.Part files
    );

    @POST("AddAmountToWallet")
    Observable<BaseResponseModel<WalletModel>> addMoneyToWallet(@Body WalletModel.PostAddMoneyToWallet model);

    @POST("GetBanner")
    Observable<BaseResponseModel<ArrayList<BannerModel>>> getBanner();

    @POST("Getpackagecategory")
    Observable<BaseResponseModel<ArrayList<SurgeryPackagesModel>>> getServices();

    @POST("GetPackageDetailbyid")
    Observable<BaseResponseModel<ArrayList<PackageDetailModel>>> getServicesDetail(@Body PackageDetailModel.PostPackageDetailModel model);

    @Multipart
    @POST("UpdatePatientMember")
    Observable<BaseResponseModel> addFamilyMember(
            @Part(value = "patientid") RequestBody patientId,
            @Part(value = "membername") RequestBody memberName,
            @Part(value = "dob") RequestBody dob,
            @Part(value = "relationship") RequestBody relationship,
            @Part MultipartBody.Part files
    );

    @POST("GetdoctorslistByCateId")
    Observable<BaseResponseModel<ArrayList<DoctorsByCategoryModel>>> getDoctorByCategory(@Body DoctorsByCategoryModel.PostDoctorByCategoryModel model);

    @POST("GetHospitalListByDoctorsId")
    Observable<BaseResponseModel<ArrayList<DoctorsDetailModel>>> getHospitalListByDoctorsId(@Body DoctorsDetailModel.PostDoctorDetailModel model);

    @POST("GetTreatEasyService")
    Observable<BaseResponseModel<ArrayList<ServicesModel>>> getTreatEasyService();

}

package com.app.treatEasy.state;

import com.app.treatEasy.appointment.ClientDetailRes;
import com.app.treatEasy.appointment.GetAmountToPayRes;
import com.app.treatEasy.appointment.TokenModel;
import com.app.treatEasy.appointmentlist.AppointmentListResponse;
import com.app.treatEasy.appservice.ServiceDetailRes;
import com.app.treatEasy.banner.BannerResponseModel;
import com.app.treatEasy.banner.BannerSendModel;
import com.app.treatEasy.booking.BookingResponse;
import com.app.treatEasy.doctorsDetail.DoctorReviewRes;
import com.app.treatEasy.doctorsDetail.DoctorsDetailRes;
import com.app.treatEasy.doctorsDetail.PrimeDoctorRes;
import com.app.treatEasy.doctorscat.DoctersListResponse;
import com.app.treatEasy.doctorscat.DoctorsListSend;
import com.app.treatEasy.feature.add_money.UpdateWalletRes;
import com.app.treatEasy.feature.appointment_success.AppointmentSuccessModel;
import com.app.treatEasy.feature.edit_family_member.EditMemberModel;
import com.app.treatEasy.feature.edit_profile.UpdateResponse;
import com.app.treatEasy.feature.family_member.AddMemberModel;
import com.app.treatEasy.feature.family_member.DeleteMemberModel;
import com.app.treatEasy.feature.family_member.MemberDetailResponse;
import com.app.treatEasy.feature.packages.HospitalModel.DoctorsSend;
import com.app.treatEasy.feature.packages.HospitalModel.HospitalResponse;
import com.app.treatEasy.feature.packages.HospitalModel.HospitalSendModel;
import com.app.treatEasy.feature.packages.PackageDeatil;
import com.app.treatEasy.feature.packages.SurgeryPackagesModel;
import com.app.treatEasy.feature.packages.package_detail.PackageCatDetail;
import com.app.treatEasy.feature.services.ServicesModel;
import com.app.treatEasy.home.DoctorResponseModel;
import com.app.treatEasy.home.DoctorSendModel;
import com.app.treatEasy.new_feature.BookingRes;
import com.app.treatEasy.new_feature.doctors.DoctorsDetail;
import com.app.treatEasy.new_feature.home.HomeResponse;
import com.app.treatEasy.new_feature.login.LoginResponse;
import com.app.treatEasy.notification.NotificationDetailRes;
import com.app.treatEasy.notification.NotificationRes;
import com.app.treatEasy.payment.CheckSumRes;
import com.app.treatEasy.payment.DoctorFeeDetailRes;
import com.app.treatEasy.payment.MakePaymentRes;
import com.app.treatEasy.payment.PaymentDetailRes;
import com.app.treatEasy.payment.PaymentDoctorRes;
import com.app.treatEasy.payment.PaymentHistoryRes;
import com.app.treatEasy.payment.PaymentRes;
import com.app.treatEasy.payment.RechargeHistoryRes;
import com.app.treatEasy.payment.RechargeReqRes;
import com.app.treatEasy.payment.surgery_package.PaymentPackageFeeRes;
import com.app.treatEasy.payment.surgery_package.paymentPackageRes;
import com.app.treatEasy.profile.GetProfileResponse;
import com.app.treatEasy.profile.doctorprofile.DoctorProfileRes;
import com.app.treatEasy.search.SearchDoctorRes;
import com.app.treatEasy.search.SearchHospitalRes;
import com.app.treatEasy.search.SearchResModel;
import com.app.treatEasy.search.SerachResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    //String BASE_URL = "https://www.appmentro.com/taruna/treateasy/api/v1/";
    //String BASE_URL = "https://www.appmentro.com/taruna/treateasy/api/";//// dev url
    String BASE_URL = "https://www.treateasy.co.in/admin/api/";  // live url

    @Headers("Authorization: treateasy2022")                       ///login
    @FormUrlEncoded
    @POST("getLoginWithMobile")
    Call<LoginResponse> login(@Field("mobile") String mobile,
                              @Field("gsm_token") String gsm_token);

    @Headers("Authorization: treateasy2022")                       ///otp verification
    @FormUrlEncoded
    @POST("verifyOTP")
    Call<LoginResponse> otpVerification(@Field("mobile") String mobile,
                                        @Field("otp") String otp);

    @Headers("Authorization: treateasy2022")                       ///otp verification
    @FormUrlEncoded
    @POST("GetClientByDoctor")
    Call<ClientDetailRes> getClientDoctor(@Field("userid") String userid,
                                          @Field("doctor_id") String doctor_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("resendotp")
    Call<LoginResponse> otpResend(@Field("mobile") String mobile);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetDoctorDetails")
    Call<DoctorsDetail> doctorDeatil(@Field("userid") String userid,
                                     @Field("doctor_id") String doctor_id);

    @Headers("Authorization: treateasy2022")                       ///login
    @FormUrlEncoded
    @POST("Registration")
        //// registration
    Call<LoginResponse> signUp(@Field("fullName") String fullName,
                               @Field("mobile") String mobile,
                               @Field("dob") String dob,
                               @Field("gsm_token") String gsm_token,
                               @Field("state_id") String state_id,
                               @Field("city_id") String city_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("BookSurgeryPackage")
        //// booking
    Call<BookingRes> bookSurgery(@Field("userid") String userid,
                                 @Field("package_id") String package_id,
                                 @Field("client_id") String client_id,
                                 @Field("doctor_id") String doctor_id,
                                 @Field("member_id") String member_id,
                                 @Field("surgery_date") String surgery_date,
                                 @Field("amount") String amount);

    @Headers("Authorization: treateasy2022")
    @GET("GetStateList")
    Call<StateResponseModel> getStateList();    /////state

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetCityByStateId")
    Call<CityResponseModel> getCityList(@Field("stateid") String stateid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("Home")
    Call<HomeResponse> getHome(@Field("userid") String userid,
                               @Field("city_id") String city_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("getprofile")
    Call<GetProfileResponse> getProfile(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetTreatEasyServices")
    Call<ServicesModel> getService(@Field("userid") String userid,
                                   @Field("city_id") String city_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetTreatEasyServiceDetails")
    Call<ServiceDetailRes> getServiceDetail(@Field("userid") String userid,
                                            @Field("service_id") String service_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetSurgeryPackageCategories")
    Call<SurgeryPackagesModel> getServicePackage(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPatientMember")
    Call<MemberDetailResponse> getFamilyMember(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("DeletePatientMember")
    Call<DeleteMemberModel> deleteFamilyMember(@Field("userid") String userid,
                                               @Field("member_id") String member_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPatientMemberDetail")
    Call<EditMemberModel> getFamilyMemberDetail(@Field("userid") String userid,
                                                @Field("member_id") String member_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPackageListByCategoryID")
    Call<PackageCatDetail> getServicePackageDetail(@Field("userid") String userid,
                                                   @Field("package_cid") String package_cid,
                                                   @Field("city_id") String city_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPackageDetail")
    Call<PackageDeatil> getPackageDetail(@Field("userid") String userid,
                                         @Field("package_id") String package_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetHospitalDetails")
    Call<DoctorsDetailRes> getHospitalDetail(@Field("userid") String userid,
                                             @Field("hospital_id") String hospital_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetBookings")
    Call<BookingResponse> getBookingDetail(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("CreateWalletRechargeRequest")
    Call<RechargeReqRes> createRicharge(@Field("userid") String userid,
                                        @Field("amount") String amount);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetWalletAmount")
    Call<PaymentRes> getPayment(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPaymentHistory")
    Call<PaymentHistoryRes> getPaymentHistory(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAmountToPayDetail")
    Call<DoctorFeeDetailRes> getPaymentDoctorFeeDetail(@Field("userid") String userid,
                                                       @Field("client_id") String client_id,
                                                       @Field("doctor_id") String doctor_id,
                                                       @Field("type") String type);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAmountToPayDetail")
    Call<PaymentPackageFeeRes> getPaymentPackageFeeDetail(@Field("userid") String userid,
                                                          @Field("client_id") String client_id,
                                                          @Field("booking_id") String booking_id,
                                                          @Field("doctor_id") String doctor_id,
                                                          @Field("type") String type,
                                                          @Field("payment_status") String payment_status);


    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAmountToPayDetail")
    Call<GetAmountToPayRes> getAmountToPayAppointment(@Field("userid") String userid,
                                                      @Field("client_id") String client_id,
                                                      @Field("doctor_id") String doctor_id,
                                                      @Field("type") String type);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAppointmentDetail")
    Call<AppointmentSuccessModel> getAppointmentDetail(@Field("userid") String userid,
                                                       @Field("appointment_id") String appointment_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetWalletRechagreHistory")
    Call<RechargeHistoryRes> getRechargeHistory(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("PaytmChecksum")
    Call<CheckSumRes> checkSum(@Field("userid") String userid,
                               @Field("mobile_no") String mobile_no,
                               @Field("recharge_id") String recharge_id,
                               @Field("txn_amount") String txn_amount);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("ScanQR")
    Call<PaymentDetailRes> paytmDetail(@Field("userid") String userid,
                                       @Field("client_id") String client_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetClientDataForPayment")
    Call<PaymentDoctorRes> paymentDoctorList(@Field("userid") String userid,
                                             @Field("client_id") String client_id,
                                             @Field("type") String type);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetClientDataForPayment")
    Call<paymentPackageRes> paymentPackageList(@Field("userid") String userid,
                                               @Field("client_id") String client_id,
                                               @Field("type") String type);


    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAppointmentList")
    Call<AppointmentListResponse> getAppointmentList(@Field("userid") String userid,
                                                     @Field("type") String type);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("MakeClientPayment")
    Call<MakePaymentRes> makePayment(@Field("userid") String userid,
                                     @Field("client_id") String client_id,
                                     @Field("doctor_id") String doctor_id,
                                     @Field("booking_id") String booking_id,
                                     @Field("payment_for") String payment_for,
                                     @Field("payment_status") String payment_status,
                                     @Field("amount") String amount,
                                     @Field("token_no") String token_no,
                                     @Field("member_id") String member_id,
                                     @Field("description") String description,
                                     @Field("approximate_time") String approximate_time
                                     );

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetDoctorDetails")
    Call<DoctorProfileRes> getDoctorProfile(@Field("userid") String userid,
                                            @Field("doctor_id") String doctor_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("UpdateWalletWithRecharge")
    Call<UpdateWalletRes> updateWallet(@Field("userid") String userid,
                                       @Field("recharge_id") String recharge_id,
                                       @Field("txn_id") String txn_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("PostDoctorReview")
    Call<DoctorReviewRes> writeReview(@Field("userid") String userid,
                                      @Field("doctor_id") String doctor_id,
                                      @Field("rating") int rating,
                                      @Field("comment") String comment);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetNotifications")
    Call<NotificationRes> getNotification(@Field("userid") String userid);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetNotificationDetail")
    Call<NotificationDetailRes> getNotificationDetail(@Field("userid") String userid,
                                                      @Field("notification_id") String notification_id);

    @Headers("Authorization: treateasy2022")
    @Multipart
    @POST("UpdateProfile")
    Call<UpdateResponse> updateProfile(@Part("userid") RequestBody userid,
                                       @Part("full_name") RequestBody full_name,
                                       @Part("dob") RequestBody dob,
                                       @Part("email") RequestBody email,
                                       @Part("gender") RequestBody gender,
                                       @Part MultipartBody.Part file,
                                       @Part("address") RequestBody address,
                                       @Part("city_id") RequestBody city_id,
                                       @Part("state_id") RequestBody state_id,
                                       @Part("profession") RequestBody profession,
                                       @Part("health_status") RequestBody health_status,
                                       @Part("company_name") RequestBody company_name,
                                       @Part("policy_number") RequestBody policy_number,
                                       @Part MultipartBody.Part govFile);

    @Headers("Authorization: treateasy2022")
    @Multipart
    @POST("AddPatientMember")
    Call<AddMemberModel> addMember(@Part("userid") RequestBody userid,
                                   @Part("membername") RequestBody membername,
                                   @Part MultipartBody.Part file,
                                   @Part("dob") RequestBody dob,
                                   @Part("relationship") RequestBody relationship,
                                   @Part("gender") RequestBody gender);


    @Headers("Authorization: treateasy2022")
    @Multipart
    @POST("EditPatientMember")
    Call<AddMemberModel> updateMember(@Part("userid") RequestBody userid,
                                      @Part("member_id") RequestBody member_id,
                                      @Part("membername") RequestBody membername,
                                      @Part MultipartBody.Part file,
                                      @Part("dob") RequestBody dob,
                                      @Part("relationship") RequestBody relationship,
                                      @Part("gender") RequestBody gender);


    @Headers("Authorization: sadas21321")
    @POST("GetBanner")
    Call<BannerResponseModel> getBannerList(@Body BannerSendModel send);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetPrimeDoctors")
    Call<PrimeDoctorRes> getDoctorList(@Field("userid") String userid,
                                       @Field("city_id") String city_id);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("GetAppointmentToken")
    Call<TokenModel> getToken(@Field("userid") String userid,
                              @Field("doctor_id") String doctor_id,
                              @Field("client_id") String client_id);

    @Headers("Authorization: sadas21321")
    @POST("GetPackageDetailbyid")
    Call<DoctersListResponse> getCategoryList(@Body DoctorsListSend send);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("Search")
    Call<SearchDoctorRes> getSearchForDoctor(@Field("userid") String userid,
                                             @Field("search_keyword") String search_keyword,
                                             @Field("city_id") String city_id,
                                             @Field("search_for") String search_for);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("Search")
    Call<SearchResModel> getSearchForPackage(@Field("userid") String userid,
                                             @Field("search_keyword") String search_keyword,
                                             @Field("city_id") String city_id,
                                             @Field("search_for") String search_for);

    @Headers("Authorization: treateasy2022")
    @FormUrlEncoded
    @POST("Search")
    Call<SearchHospitalRes> getSearchForHospital(@Field("userid") String userid,
                                                 @Field("search_keyword") String search_keyword,
                                                 @Field("city_id") String city_id,
                                                 @Field("search_for") String search_for);

    @Headers("Authorization: sadas21321")
    @POST("GetHospitalListByPKGId")
    Call<HospitalResponse> getHospital(@Body HospitalSendModel send);

    @Headers("Authorization: sadas21321")
    @POST("GetdoctorslistByclientId")
    Call<DoctorResponseModel> getDoctorsByClientId(@Body DoctorsSend send);
}

package com.app.treatEasy.dagger.component;

import com.app.treatEasy.BaseAppApplication;
import com.app.treatEasy.SurgeryPackegeDetailActivity;
import com.app.treatEasy.booking.BookingActivity;
import com.app.treatEasy.feature.edit_profile.EditProfileActivity;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyMemberActivity;
import com.app.treatEasy.feature.home_screen.HomeFragment;
import com.app.treatEasy.feature.landing_activity.HomeActivity;
import com.app.treatEasy.feature.login_module.TermAndConditionActivity;
import com.app.treatEasy.feature.login_module.login_screen.LoginActivity;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerificationActivity;
import com.app.treatEasy.feature.login_module.signup_newuser.SignUpActivity;
import com.app.treatEasy.feature.packages.HospitalListActivity;
import com.app.treatEasy.feature.packages.SurgeryPackagesActivity;
import com.app.treatEasy.feature.packages.doctor_detail.DoctorsByCategoryDetailActivity;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryActivity;
import com.app.treatEasy.feature.packages.package_detail.PackageDetailActivity;
import com.app.treatEasy.feature.profile.ProfileActivity;
import com.app.treatEasy.feature.services.ServicesActivity;
import com.app.treatEasy.feature.wallet.PaymentDetailActivity;
import com.app.treatEasy.feature.wallet.SuccessActivity;
import com.app.treatEasy.feature.wallet.WalletActivity;
import com.app.treatEasy.home.AboutActivity;
import com.app.treatEasy.home.PrivacyActivity;
import com.app.treatEasy.home.RefundActivity;
import com.app.treatEasy.new_feature.doctors.DoctorDeatil;
import com.app.treatEasy.notification.NotificationActivity;
import com.app.treatEasy.payment.PaymentHistoryActivity;
import com.app.treatEasy.payment.RechargeHistoryActivity;
import com.app.treatEasy.search.SearchActivity;

/*Created by Vinod Kumar (Aug 2019)*/

/*Interface here Dagger find out the component type in that dagger have to inject dependencies... */

interface Injector {

    void provideIn(BaseAppApplication applicationContext);

    void provideIn(HomeActivity activity);
    void provideIn(TermAndConditionActivity activity);
    void provideIn(PrivacyActivity activity);
    void provideIn(RefundActivity activity);
    void provideIn(AboutActivity activity);
    void provideIn(PaymentHistoryActivity activity);
    void provideIn(SuccessActivity activity);
    void provideIn(RechargeHistoryActivity activity);
    void provideIn(DoctorDeatil activity);

    void provideIn(LoginActivity activity);

    void provideIn(SearchActivity activity);

    void provideIn(SignUpActivity signUpActivity);

    void provideIn(OtpVerificationActivity otpVerificationActivity);

    void provideIn(ProfileActivity profileActivity);

    void provideIn(EditProfileActivity editProfileActivity);
    void provideIn(PaymentDetailActivity paymentDetailActivity);

    void provideIn(WalletActivity walletActivity);

    void provideIn(HomeFragment homeFragment);

    void provideIn(SurgeryPackagesActivity surgeryPackagesActivity);

    void provideIn(AddFamilyMemberActivity addFamilyMemberActivity);

    void provideIn(PackageDetailActivity packageDetailActivity);

    void provideIn(DoctorsByCategoryActivity doctorsActivity);

    void provideIn(HospitalListActivity hospitalListActivity);
    void provideIn(BookingActivity bookingActivity);
    void provideIn(NotificationActivity notificationActivity);

    void provideIn(DoctorsByCategoryDetailActivity doctorsByCategoryDetailActivity);
    void provideIn(SurgeryPackegeDetailActivity doctorsByCategoryDetailActivity);

    void provideIn(ServicesActivity servicesActivity);
}

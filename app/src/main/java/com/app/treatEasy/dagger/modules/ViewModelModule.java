package com.app.treatEasy.dagger.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.app.treatEasy.baseui.BaseViewModelFactory;
import com.app.treatEasy.dagger.annotations.ViewModelKey;
import com.app.treatEasy.feature.edit_profile.EditProfileViewModel;
import com.app.treatEasy.feature.family_member.add_family_member.AddFamilyViewModel;
import com.app.treatEasy.feature.home_screen.HomeViewModel;
import com.app.treatEasy.feature.login_module.StateViewModel;
import com.app.treatEasy.feature.login_module.login_screen.LoginViewModel;
import com.app.treatEasy.feature.login_module.otp_verification.OtpVerifyViewModel;
import com.app.treatEasy.feature.login_module.signup_newuser.SignUpViewModel;
import com.app.treatEasy.feature.packages.SurgeryPackagesViewModel;
import com.app.treatEasy.feature.packages.doctor_detail.DoctorsDetailViewModel;
import com.app.treatEasy.feature.packages.doctors.package_detail.DoctorsByCategoryViewModel;
import com.app.treatEasy.feature.packages.package_detail.PackageDetailViewModel;
import com.app.treatEasy.feature.profile.ProfileViewModel;
import com.app.treatEasy.feature.services.ServicesViewModel;
import com.app.treatEasy.feature.wallet.WalletViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/*Created by Vinod Kumar (Aug 2019)*/

/*Module class that will provide view model class object to inject via Dagger...*/
@Module
public abstract class ViewModelModule {


    // add more ViewModels
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(BaseViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel loginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel signUpViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(StateViewModel.class)
    abstract ViewModel stateViewModel(SignUpViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OtpVerifyViewModel.class)
    abstract ViewModel otpVerifyViewModel(OtpVerifyViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel profileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(EditProfileViewModel.class)
    abstract ViewModel editProfileViewModel(EditProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(WalletViewModel.class)
    abstract ViewModel walletViewModel(WalletViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel.class)
    abstract ViewModel homeViewModel(HomeViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SurgeryPackagesViewModel.class)
    abstract ViewModel surgeryPackagesViewModel(SurgeryPackagesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(AddFamilyViewModel.class)
    abstract ViewModel addFamilyViewModel(AddFamilyViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PackageDetailViewModel.class)
    abstract ViewModel packageDetailViewModel(PackageDetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DoctorsByCategoryViewModel.class)
    abstract ViewModel doctorsByCategoryViewModel(DoctorsByCategoryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DoctorsDetailViewModel.class)
    abstract ViewModel doctorsDetailViewModel(DoctorsDetailViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ServicesViewModel.class)
    abstract ViewModel servicesViewModel(ServicesViewModel viewModel);
}

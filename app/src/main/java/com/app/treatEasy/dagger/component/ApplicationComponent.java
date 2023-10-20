package com.app.treatEasy.dagger.component;


import com.app.treatEasy.dagger.modules.ApiModule;
import com.app.treatEasy.dagger.modules.ApplicationModule;
import com.app.treatEasy.dagger.modules.NetworkModule;
import com.app.treatEasy.dagger.modules.ViewModelModule;
import com.app.treatEasy.dagger.scopes.AppScope;

import javax.inject.Singleton;

import dagger.Component;
/*Created by Vinod Kumar (Aug 2019)*/

/*Component class that define all the modules used by Dagger ...*/
@Singleton
@AppScope
@Component(modules = {
        ApplicationModule.class,
        ViewModelModule.class,
        ApiModule.class,
        NetworkModule.class
})
public interface ApplicationComponent extends Injector {
}

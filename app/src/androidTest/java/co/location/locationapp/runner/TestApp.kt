package co.location.locationapp.runner

import android.app.Activity
import android.app.Application
import co.location.locationapp.di.DaggerTestAppComponent
import co.location.locationapp.di.MockAppModule
import co.location.locationapp.di.MockDatabaseModule
import co.location.locationapp.di.MockNetModule
import co.location.locationapp.util.Constant
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TestApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerTestAppComponent.builder()
                .mockAppModule(MockAppModule(this))
                .mockNetModule(MockNetModule(Constant.BASE_URL))
                .mockDatabaseModule(MockDatabaseModule())
                .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
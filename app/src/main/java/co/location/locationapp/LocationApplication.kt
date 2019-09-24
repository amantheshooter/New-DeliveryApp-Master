package co.location.locationapp

import android.app.Activity
import android.app.Application
import co.location.locationapp.di.component.DaggerAppComponent
import co.location.locationapp.di.modules.AppModule
import co.location.locationapp.di.modules.DatabaseModule
import co.location.locationapp.di.modules.NetModule
import co.location.locationapp.utils.Constants
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


open class LocationApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .netModule(NetModule())
                .databaseModule(DatabaseModule())
                .build().inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

}
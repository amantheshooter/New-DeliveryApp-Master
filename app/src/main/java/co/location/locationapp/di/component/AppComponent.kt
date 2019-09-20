package co.location.locationapp.di.component

import co.location.locationapp.LocationApplication
import co.location.locationapp.di.modules.AppModule
import co.location.locationapp.di.modules.BuildersModule
import co.location.locationapp.di.modules.DatabaseModule
import co.location.locationapp.di.modules.NetModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class,
    AppModule::class, NetModule::class, DatabaseModule::class, BuildersModule::class])
interface AppComponent {
    fun inject(app: LocationApplication)
}
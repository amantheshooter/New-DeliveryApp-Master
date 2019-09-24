package co.location.locationapp.di.modules

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import co.location.locationapp.ui.home.viewmodel.HomeViewModelFactory
import co.location.locationapp.utils.OpenForTesting
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
open class AppModule(val app: Application) {

    @Provides
    @Singleton
    fun provideApplication(): Application = app


    @Provides
    @Singleton
    fun provideHomeViewModelFactory(
            factory: HomeViewModelFactory): ViewModelProvider.Factory = factory

}
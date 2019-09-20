package co.location.locationapp.di.modules

import co.location.locationapp.ui.home.HomeActivity
import co.location.locationapp.utils.OpenForTesting
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeActivity(): HomeActivity
}
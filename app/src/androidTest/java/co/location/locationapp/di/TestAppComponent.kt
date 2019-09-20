package co.location.locationapp.di

import co.location.locationapp.di.component.AppComponent
import co.location.locationapp.di.modules.BuildersModule
import co.location.locationapp.runner.TestApp
import co.location.locationapp.util.BaseTest
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton

@Component(modules = [AndroidInjectionModule::class, BuildersModule::class,
    MockAppModule::class, MockNetModule::class, MockDatabaseModule::class])
interface TestAppComponent : AppComponent {
    fun inject(app: TestApp)
    fun inject(app: BaseTest)
}
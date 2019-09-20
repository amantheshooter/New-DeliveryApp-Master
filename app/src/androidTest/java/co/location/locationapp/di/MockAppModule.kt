package co.location.locationapp.di

import android.app.Application
import co.location.locationapp.di.modules.AppModule
import dagger.Module

@Module
class MockAppModule(app: Application) : AppModule(app = app) {


}
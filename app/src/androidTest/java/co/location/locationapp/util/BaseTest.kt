package co.location.locationapp.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import co.location.locationapp.di.*
import co.location.locationapp.runner.TestApp
import com.squareup.okhttp.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before

abstract class BaseTest {

    private lateinit var mockServer: MockWebServer
    private lateinit var testAppComponent: TestAppComponent


    @Before
    open fun setUp() {
        this.configureMockServer()
        this.configureDi()
    }

    @After
    open fun tearDown() {
        this.stopMockServer()
    }

    open fun configureDi() {
        val context = ApplicationProvider.getApplicationContext<Context>() as TestApp
        this.testAppComponent = DaggerTestAppComponent.builder()
                .mockAppModule(MockAppModule(context))
                .mockNetModule(MockNetModule(if (isMockServerEnabled()) mockServer.url("/").toString() else Constant.BASE_URL))
                .mockDatabaseModule(MockDatabaseModule())
                .build()
        this.testAppComponent.inject(this)
    }


    abstract fun isMockServerEnabled(): Boolean

    open fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }


    open fun stopMockServer() {
        if (isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

}
package ru.kpfu.itis.imageflow

import android.app.Application
import ru.kpfu.itis.core.provider.ApiKeyProvider
import ru.kpfu.itis.imageflow.di.AppComponent
import ru.kpfu.itis.imageflow.di.AppModule
import ru.kpfu.itis.imageflow.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class ImageFlowApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    @Inject lateinit var apiKeyProvider: ApiKeyProvider

    override fun onCreate() {
        super.onCreate()

        appComponent.inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(object : Timber.Tree() {
                override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                    // TODO: отправить в Crashlytics / Logcat только ERROR
                }
            })
        }

        Timber.d("ImageFlow initialized")
        Timber.d("API Key valid: ${apiKeyProvider.isValid()}")
    }
}
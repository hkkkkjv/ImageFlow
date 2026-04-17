package ru.kpfu.itis.imageflow.di

import dagger.Component
import ru.kpfu.itis.imageflow.ImageFlowApp
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: ImageFlowApp)
}
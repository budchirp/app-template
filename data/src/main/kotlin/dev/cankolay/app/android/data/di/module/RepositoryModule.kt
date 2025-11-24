package dev.cankolay.app.android.data.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.cankolay.app.android.data.repository.api.PostRepositoryImpl
import dev.cankolay.app.android.data.repository.application.SettingsStateRepositoryImpl
import dev.cankolay.app.android.domain.repository.api.PostRepository
import dev.cankolay.app.android.domain.repository.application.SettingsStateRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindPostRepository(
        impl: PostRepositoryImpl
    ): PostRepository

    @Binds
    abstract fun bindSettingsStateRepository(
        impl: SettingsStateRepositoryImpl
    ): SettingsStateRepository
}
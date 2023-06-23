package com.dicoding.core.di

import androidx.room.Room
import com.dicoding.core.BuildConfig
import com.dicoding.core.data.repository.AxaRepositoryImpl
import com.dicoding.core.data.repository.DeveloperRepositoryImpl
import com.dicoding.core.data.repository.GameRepositoryImpl
import com.dicoding.core.data.repository.GenreRepositoryImpl
import com.dicoding.core.data.source.local.data_source.LocalDeveloperSource
import com.dicoding.core.data.source.local.data_source.LocalGameSource
import com.dicoding.core.data.source.local.data_source.LocalGenreSource
import com.dicoding.core.data.source.local.data_source.LocalPostSource
import com.dicoding.core.data.source.local.room.GameDatabase
import com.dicoding.core.data.source.remote.data_source.RemoteAxaSource
import com.dicoding.core.data.source.remote.data_source.RemoteDeveloperSource
import com.dicoding.core.data.source.remote.data_source.RemoteGameSource
import com.dicoding.core.data.source.remote.data_source.RemoteGenreSource
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.data.source.remote.network.AxaService
import com.dicoding.core.domain.repository.AxaRepository
import com.dicoding.core.domain.repository.DeveloperRepository
import com.dicoding.core.domain.repository.GameRepository
import com.dicoding.core.domain.repository.GenreRepository
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val hostname = BuildConfig.HOSTNAME
val certification = CertificatePinner.Builder()
    .add(hostname, BuildConfig.CERTIFICATE_PINNING_1)
    .add(hostname, BuildConfig.CERTIFICATE_PINNING_2)
    .add(hostname, BuildConfig.CERTIFICATE_PINNING_3)
    .build()

val listCoreModule = listOf(
    // Database Module
    module {
        factory { get<GameDatabase>().gameDAO() }
        factory { get<GameDatabase>().developerDAO() }
        factory { get<GameDatabase>().genreDAO() }
        factory { get<GameDatabase>().postDAO() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.DB_PASSPHRASE.toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                GameDatabase::class.java, BuildConfig.DB_NAME
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    },
    // Network Module
    module {
        single {
            OkHttpClient.Builder()
//                .certificatePinner(certification)
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
        }
        single {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofit.create(ApiService::class.java)
        }
        single {
            val retrofitAxa = Retrofit.Builder()
                .baseUrl(AxaService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(get())
                .build()
            retrofitAxa.create(AxaService::class.java)
        }
    },
    // Repository Module
    module {
        single { LocalGameSource(get()) }
        single { LocalDeveloperSource(get()) }
        single { LocalGenreSource(get()) }
        single { LocalPostSource(get()) }
        single { RemoteGameSource(get()) }
        single { RemoteDeveloperSource(get()) }
        single { RemoteGenreSource(get()) }
        single { RemoteAxaSource(get()) }
        single<GameRepository> {
            GameRepositoryImpl(
                get(),
                get()
            )
        }
        single<DeveloperRepository> {
            DeveloperRepositoryImpl(
                get(),
                get()
            )
        }
        single<GenreRepository> {
            GenreRepositoryImpl(
                get(),
                get()
            )
        }
        single<AxaRepository> {
            AxaRepositoryImpl(
                get(),
                get()
            )
        }
    }
)
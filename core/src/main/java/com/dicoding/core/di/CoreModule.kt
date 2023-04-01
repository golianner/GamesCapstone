package com.dicoding.core.di

import androidx.room.Room
import com.dicoding.core.data.repository.DeveloperRepositoryImpl
import com.dicoding.core.data.repository.GameRepositoryImpl
import com.dicoding.core.data.repository.GenreRepositoryImpl
import com.dicoding.core.data.source.local.data_source.LocalDeveloperSource
import com.dicoding.core.data.source.local.data_source.LocalGameSource
import com.dicoding.core.data.source.local.data_source.LocalGenreSource
import com.dicoding.core.data.source.local.room.GameDatabase
import com.dicoding.core.data.source.remote.data_source.RemoteDeveloperSource
import com.dicoding.core.data.source.remote.data_source.RemoteGameSource
import com.dicoding.core.data.source.remote.data_source.RemoteGenreSource
import com.dicoding.core.data.source.remote.network.ApiService
import com.dicoding.core.domain.repository.DeveloperRepository
import com.dicoding.core.domain.repository.GameRepository
import com.dicoding.core.domain.repository.GenreRepository
import com.dicoding.core.utils.AppExecutors
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

const val hostname = "api.rawg.io"
val certificatePinner = CertificatePinner.Builder()
    .add(hostname, "sha256/Vt5/77IBRU8Ic76wffoVpn2hrTRotDK+cuASoGoEzS0=")
    .add(hostname, "sha256/hS5jJ4P+iQUErBkvoWBQOd1T7VOAYlOVegvv1iMzpxA=")
    .add(hostname, "sha256/Y9mvm0exBk1JoQ57f9Vm28jKo5lFm/woKcVxrYxu80o=")
    .build()

val listCoreModule = listOf(
    // Database Module
    module {
        factory { get<GameDatabase>().gameDAO() }
        factory { get<GameDatabase>().developerDAO() }
        factory { get<GameDatabase>().genreDAO() }
        single {
            val passphrase: ByteArray = SQLiteDatabase.getBytes("golianner".toCharArray())
            val factory = SupportFactory(passphrase)
            Room.databaseBuilder(
                androidContext(),
                GameDatabase::class.java, "Game.db"
            ).fallbackToDestructiveMigration()
                .openHelperFactory(factory)
                .build()
        }
    },
    // Network Module
    module {
        single {
            OkHttpClient.Builder()
                .certificatePinner(certificatePinner)
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
    },
    // Repository Module
    module {
        single { LocalGameSource(get()) }
        single { LocalDeveloperSource(get()) }
        single { LocalGenreSource(get()) }
        single { RemoteGameSource(get()) }
        single { RemoteDeveloperSource(get()) }
        single { RemoteGenreSource(get()) }
        factory { AppExecutors() }
        single<GameRepository> {
            GameRepositoryImpl(
                get(),
                get(),
                get()
            )
        }
        single<DeveloperRepository> {
            DeveloperRepositoryImpl(
                get(),
                get(),
                get()
            )
        }
        single<GenreRepository> {
            GenreRepositoryImpl(
                get(),
                get(),
                get()
            )
        }
    }
)
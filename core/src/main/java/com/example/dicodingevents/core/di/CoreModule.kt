package com.example.dicodingevents.core.di

import androidx.room.Room
import com.example.dicodingevents.core.BuildConfig
import com.example.dicodingevents.core.data.EventRepository
import com.example.dicodingevents.core.data.source.local.LocalDataSource
import com.example.dicodingevents.core.data.source.local.room.EventDatabase
import com.example.dicodingevents.core.data.source.remote.RemoteDataSource
import com.example.dicodingevents.core.data.source.remote.network.ApiService
import com.example.dicodingevents.core.domain.repository.IEventRepository
import com.example.dicodingevents.core.utils.AppExecutors
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

val databaseModule = module {
    factory { get<EventDatabase>().eventDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("example".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            EventDatabase::class.java, "Event.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single {
        val hostname = "event-api.dicoding.dev"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/+IraAH0jcQbS9+TlpfQ/h45Ab+p9uZWrVdJiogOQMoE=")
            .add(hostname, "sha256/K7rZOrXHknnsEhUH8nLL4MZkejquUuIvOIr6tCa0rbo=")
            .build()
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IEventRepository> { EventRepository(get(), get(), get()) }
}
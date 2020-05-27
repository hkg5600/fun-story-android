package com.example.fun_story.di

import androidx.room.Room
import com.example.data.db.FeedDatabase
import com.example.data.db.SaveDatabase
import com.example.data.feed.FeedApi
import com.example.data.feed.FeedLocalDataSource
import com.example.data.feed.FeedRemoteDataSource
import com.example.data.feed.FeedRepositoryImpl
import com.example.data.token.TokenApi
import com.example.data.token.TokenRepositoryImpl
import com.example.data.prefs.SharedPreferenceStorage
import com.example.data.token.TokenRemoteDataSource
import com.example.domain.feed.FeedRepository
import com.example.domain.feed.GetFeedDataUseCase
import com.example.domain.feed.GetFeedListUseCase
import com.example.domain.feed.SaveFeedListUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.fun_story.BASE_URL
import com.example.domain.network.NetworkManager
import com.example.domain.token.*
import com.example.fun_story.ui.feed.FeedFragment
import com.example.fun_story.ui.feed.FeedViewModel
import com.example.fun_story.ui.feed_detail.FeedDetailViewModel
import com.example.fun_story.ui.main.MainViewModel
import com.example.fun_story.ui.save.SaveFragment
import com.example.fun_story.ui.save.SaveViewModel
import com.example.fun_story.ui.splash.SplashViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val retrofit: Retrofit = Retrofit
    .Builder()
    .baseUrl(BASE_URL)
    .client(
        OkHttpClient.Builder()
            .addInterceptor(TokenManager.getAuthenticationInterceptor())
            .build()
    )
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .build()

private val tokenApi = retrofit.create(TokenApi::class.java)
private val feedApi = retrofit.create(FeedApi::class.java)

val networkModule = module {
    single { tokenApi }
    single { feedApi }
}

val repositoryModule = module {
    factory<TokenRepository> { TokenRepositoryImpl(get(), get()) }
    factory<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    //factory<SaveRepository> { SaveRepositoryImpl(get()) }
}

val dataSourceModule = module {
    factory { TokenRemoteDataSource(get()) }
    factory { FeedRemoteDataSource(get()) }
    factory { FeedLocalDataSource(get()) }
    //factory { SaveLocalDataSource(get()) }
}

val useCaseModule = module {
    factory { GetTokenUseCase(get()) }
    factory { VerifyTokenUserCase(get()) }
    factory { RefreshTokenUseCase(get()) }
    factory { GetFeedListUseCase(get()) }
    factory { GetNetworkStateUseCase(get()) }
    factory { SaveFeedListUseCase(get()) }
    factory { GetFeedDataUseCase(get()) }
}

val viewModelModule = module {
    factory { MainViewModel() }
    factory { SplashViewModel(get(), get(), get()) }
    factory { FeedViewModel(get(), get(), get()) }
    factory { SaveViewModel() }
    factory { FeedDetailViewModel(get()) }
}

val dbModule = module {
    single { SharedPreferenceStorage(androidContext()) }
    single { NetworkManager(androidContext()) }
    single {
        Room.databaseBuilder(androidApplication(), FeedDatabase::class.java, "feed_local_db")
            .build()
    }
    single {
        Room.databaseBuilder(androidApplication(), SaveDatabase::class.java, "save_local_db")
            .build()
    }
}

val fragmentModule = module {
    single { FeedFragment() }
    single { SaveFragment() }
}

val moduleList = listOf(
    viewModelModule,
    dataSourceModule,
    useCaseModule,
    repositoryModule,
    networkModule,
    dbModule,
    fragmentModule
)
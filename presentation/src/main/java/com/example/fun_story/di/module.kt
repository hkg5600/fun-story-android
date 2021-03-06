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
import com.example.data.save.SaveApi
import com.example.data.save.SaveLocalDataSource
import com.example.data.save.SaveRemoteDataSource
import com.example.data.save.SaveRepositoryImpl
import com.example.data.token.TokenRemoteDataSource
import com.example.data.user.*
import com.example.domain.feed.FeedRepository
import com.example.domain.feed_detail.GetFeedDataUseCase
import com.example.domain.feed.GetFeedListUseCase
import com.example.domain.feed.PostFeedUseCase
import com.example.domain.feed.SaveFeedListUseCase
import com.example.domain.feed_detail.DeleteFeedUseCase
import com.example.domain.feed_detail.SaveFeedUseCase
import com.example.domain.network.GetNetworkStateUseCase
import com.example.fun_story.BASE_URL
import com.example.domain.network.NetworkManager
import com.example.domain.save.DeleteSavedFeedUseCase
import com.example.domain.save.GetSaveFeedListUseCase
import com.example.domain.save.SaveRepository
import com.example.domain.token.*
import com.example.domain.user.*
import com.example.fun_story.ui.feed.FeedFragment
import com.example.fun_story.ui.feed.FeedViewModel
import com.example.fun_story.ui.feed_detail.FeedDetailViewModel
import com.example.fun_story.ui.follower.FollowerViewModel
import com.example.fun_story.ui.user.UserViewModel
import com.example.fun_story.ui.info.InfoFragment
import com.example.fun_story.ui.info.InfoViewModel
import com.example.fun_story.ui.main.MainViewModel
import com.example.fun_story.ui.save.SaveFragment
import com.example.fun_story.ui.save.SaveViewModel
import com.example.fun_story.ui.splash.SplashViewModel
import com.example.fun_story.ui.start.StartViewModel
import com.example.fun_story.ui.start.join.JoinFragment
import com.example.fun_story.ui.start.join.JoinViewModel
import com.example.fun_story.ui.start.login.LoginFragment
import com.example.fun_story.ui.start.login.LoginViewModel
import com.example.fun_story.ui.write.WriteViewModel
import com.example.fun_story.ui.write.category.WriteCategoryFragment
import com.example.fun_story.ui.write.content.WriteContentFragment
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
private val saveApi = retrofit.create(SaveApi::class.java)
private val userApi = retrofit.create(UserApi::class.java)

val networkModule = module {
    single { tokenApi }
    single { feedApi }
    single { saveApi }
    single { userApi }
}

val repositoryModule = module {
    factory<TokenRepository> { TokenRepositoryImpl(get(), get()) }
    factory<FeedRepository> { FeedRepositoryImpl(get(), get()) }
    factory<SaveRepository> { SaveRepositoryImpl(get(), get()) }
    factory<UserRepository> { UserRepositoryImpl(get(), get(), get()) }
}

val dataSourceModule = module {
    factory { TokenRemoteDataSource(get()) }
    factory { FeedRemoteDataSource(get()) }
    factory { FeedLocalDataSource(get()) }
    factory { SaveLocalDataSource(get()) }
    factory { SaveRemoteDataSource(get()) }
    factory { UserRemoteDataSource(get()) }
}

val useCaseModule = module {
    factory { GetTokenUseCase(get()) }
    factory { VerifyTokenUserCase(get()) }
    factory { RefreshTokenUseCase(get()) }
    factory { GetFeedListUseCase(get()) }
    factory { GetNetworkStateUseCase(get()) }
    factory { SaveFeedListUseCase(get())}
    factory { SaveFeedUseCase(get()) }
    factory { GetFeedDataUseCase(get()) }
    factory { GetSaveFeedListUseCase(get()) }
    factory { DeleteSavedFeedUseCase(get()) }
    factory { GetUserInfoUseCase(get()) }
    factory { GetMyInfoUseCase(get()) }
    factory { DeleteFeedUseCase(get()) }
    factory { FollowUserUseCase(get()) }
    factory { GetFollowerUesCase(get()) }
    factory { LoginUseCase(get()) }
    factory { JoinUseCase(get()) }
    factory { RemoveTokenUseCase(get()) }
    factory { PostFeedUseCase(get()) }
}

val viewModelModule = module {
    factory { MainViewModel() }
    factory { SplashViewModel(get(), get(), get(), get()) }
    factory { FeedViewModel(get(), get(), get()) }
    factory { SaveViewModel(get(), get()) }
    factory { FeedDetailViewModel(get(), get(), get()) }
    factory { UserViewModel(get(), get(), get(), get(), get()) }
    factory { InfoViewModel(get(), get()) }
    factory { FollowerViewModel(get()) }
    factory { StartViewModel() }
    factory { LoginViewModel(get()) }
    factory { JoinViewModel(get()) }
    factory { WriteViewModel(get(), get()) }
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
    factory { FeedFragment() }
    factory { SaveFragment() }
    factory { InfoFragment() }
    factory { LoginFragment()}
    factory { JoinFragment() }
    factory { WriteCategoryFragment() }
    factory { WriteContentFragment() }
}

val mapperModule = module {
    factory { JoinMapper(get()) }
    factory { LoginMapper(get()) }
}

val moduleList = listOf(
    viewModelModule,
    dataSourceModule,
    useCaseModule,
    repositoryModule,
    networkModule,
    dbModule,
    fragmentModule,
    mapperModule
)
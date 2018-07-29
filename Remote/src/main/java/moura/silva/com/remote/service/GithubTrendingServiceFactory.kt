package moura.silva.com.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit

class GithubTrendingServiceFactory {

    fun makeGithubTrendingService(isDebug: Boolean) : GitHubTrendingService{
        val okHttpClient = makeOkHttpClient(makeLogginInterceptor(isDebug))
        return makeGitHubTrandingService(okHttpClient)
    }

    private fun makeGitHubTrandingService(okHttpClient :OkHttpClient ) : GitHubTrendingService{
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/")
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        return retrofit.create(GitHubTrendingService::class.java)

    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient{
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120,TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .build()
    }


    private fun makeLogginInterceptor(isDebug : Boolean) : HttpLoggingInterceptor{
        val loggin = HttpLoggingInterceptor()
        loggin.level = if(isDebug){
            HttpLoggingInterceptor.Level.BODY
        }else{
            HttpLoggingInterceptor.Level.NONE
        }
        return loggin
    }
}
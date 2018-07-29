package moura.silva.com.remote.service

import io.reactivex.Observable
import moura.silva.com.remote.model.ProjectsResponseModel
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubTrendingService {

    @GET("search/repositories")
    fun searchRepository(@Query("q") query: String,
                         @Query("sort") sortBy : String,
                         @Query("order") order : String) : Observable<ProjectsResponseModel>
}
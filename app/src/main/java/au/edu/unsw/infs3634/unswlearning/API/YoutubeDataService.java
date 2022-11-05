package au.edu.unsw.infs3634.unswlearning.API;

import java.util.ArrayList;

import au.edu.unsw.infs3634.unswlearning.Lesson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YoutubeDataService {
    @Headers("Authorization: ")
    @GET("search?part=snippet&maxResults=1&key=AIzaSyCzb5q_BZybAA9uwyhHyaRza-to41cZmlo")
    Call<YoutubeDataResponse> getVideoByName(@Query("q") String q);
}

package au.edu.unsw.infs3634.unswlearning.API;

import java.util.ArrayList;

import au.edu.unsw.infs3634.unswlearning.Lesson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YoutubeDataService {
    @Headers("Authorization: ") // backup key: "AIzaSyCvTBA8V3W0z7v8m9aTMj_U0EoEEpKIFEk" // "AIzaSyCzb5q_BZybAA9uwyhHyaRza-to41cZmlo"
    @GET("search?part=snippet&maxResults=1&key=AIzaSyCzb5q_BZybAA9uwyhHyaRza-to41cZmlo")
    Call<YoutubeDataResponse> getVideoByName(@Query("q") String q);
}

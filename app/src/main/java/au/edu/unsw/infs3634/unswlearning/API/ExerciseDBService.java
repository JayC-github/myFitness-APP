package au.edu.unsw.infs3634.unswlearning.API;

import java.util.ArrayList;

import au.edu.unsw.infs3634.unswlearning.Lesson;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This Interface is for called methods to get exercise data from Exercise Database API
 * by given target muscle area
 */
public interface ExerciseDBService {
    @Headers("X-RapidAPI-Key: e03f9eca93msh9033f0ab5b6deedp10422djsn9f01ea6f3970")
    @GET("v1/exercises")
    Call<ArrayList<Lesson>> getExerciseByBody(@Query("muscle") String muscle);

    @Headers("X-RapidAPI-Key: e03f9eca93msh9033f0ab5b6deedp10422djsn9f01ea6f3970")
    @GET("v1/exercises")
    Call<ArrayList<Lesson>> getExerciseByName(@Query("name") String name);

}

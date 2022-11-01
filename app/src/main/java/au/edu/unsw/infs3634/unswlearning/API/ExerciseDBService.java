package au.edu.unsw.infs3634.unswlearning.API;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExerciseDBService {
    @GET("exercises/bodyPart/{bodyPart}")
    Call<ExerciseDBResponse> getExercisebyBody(@Path("bodyPart") String bodyPart);
}

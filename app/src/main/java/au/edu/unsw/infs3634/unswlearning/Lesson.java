package au.edu.unsw.infs3634.unswlearning;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

// import javax.annotation.Generated;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataResponse;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// @Generated("jsonschema2pojo")
//lesson object declaration
public class Lesson {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("muscle")
    @Expose
    private String muscle;
    @SerializedName("equipment")
    @Expose
    private String equipment;
    @SerializedName("difficulty")
    @Expose
    private String difficulty;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    // try to get an image for the exercise
    private String image_url;


    public Lesson(String name, String type, String muscle, String equipment, String difficulty,
                  String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    //getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage_url() {
        return this.image_url;
    }

    public void setImage_url(String image_url) { this.image_url = image_url; }

    // this was used to generate lesson but useless now
    public static ArrayList<Lesson> getLesson() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        return lessons;
    }

    //method to return searched lesson based on string query
    public static Lesson findLesson(String query) {
        ArrayList<Lesson> lessons = Lesson.getLesson();
        for(final Lesson lesson: lessons) {
            if(lesson.getName().toLowerCase().equals(query.toLowerCase())) {
                return lesson;
            }
        }
        return null;
    }

}

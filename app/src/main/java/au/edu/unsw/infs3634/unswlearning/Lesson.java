package au.edu.unsw.infs3634.unswlearning;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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

/**
 * Lesson object declaration
 */
@Entity // make java class an entity
public class Lesson {
    // use name as the primary key
    @PrimaryKey // specify primary key
    @NonNull
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
    // give each exercise a groupType to distinguished them
    private String group;
    // try to get and store image url for the exercise
    private String image_url;
    // try to get and store video id for the exercise
    private String videoId;

    /**
     * constructor for lesson
     * @param name          name of lesson
     * @param type          type of lesson
     * @param muscle        lesson muscle group
     * @param equipment     equipment needed
     * @param difficulty    lesson difficulty level
     * @param instructions  instructions for lesson
     */
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

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    /**
     * method to generate lesson
     * @return arraylist of lessons
     */
    public static ArrayList<Lesson> getLesson() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        return lessons;
    }

    //method to return searched lesson based on string query

    /**
     * method to return searched lesson based on string query
     * @param query     provided query
     * @return          searched lesson
     */
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

package au.edu.unsw.infs3634.unswlearning;

import java.util.ArrayList;

// import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

// @Generated("jsonschema2pojo")
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
    // Youtube VideoID
    private String videoLink;


    public Lesson(String name, String type, String muscle, String equipment, String difficulty,
                  String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.videoLink = getYoutubeVideo();
    }


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

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    // use this method to generate youtube video ID based on the given name
    public String getYoutubeVideo() {
        return "DHi6NeBPujg";
    }


    // this was used to generate lesson but useless now
    public static ArrayList<Lesson> getLesson() {
        ArrayList<Lesson> lessons = new ArrayList<>();
        return lessons;
    }

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

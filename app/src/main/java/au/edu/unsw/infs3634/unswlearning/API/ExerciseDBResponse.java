// Not sure if this class actually help to load the data from ExerciseDBAPI
// this one is kinda useless too
package au.edu.unsw.infs3634.unswlearning.API;

// import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

// This is one is kinda useless for now then

//@Generated("jsonschema2pojo")
public class ExerciseDBResponse {
    @SerializedName("data")
    @Expose
    private List<ExerciseData> data = null;

    public List<ExerciseData> getData() {
        return data;
    }
    public void setData(List<ExerciseData> data) {
        this.data = data;
    }

}
package au.edu.unsw.infs3634.unswlearning.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class is for loading and getting the required data from YouTube Data API
 */
public class Thumbnails {
    @SerializedName("high")
    @Expose
    private High high;

    public High getHigh() {
        return high;
    }

    public void setHigh(High high) {
        this.high = high;
    }

}

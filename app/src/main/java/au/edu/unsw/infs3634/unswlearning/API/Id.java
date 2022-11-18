package au.edu.unsw.infs3634.unswlearning.API;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * This class is for getting the Youtube Video ID to load in YouTube Player from YouTube Data API
 */
public class Id {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("videoId")
    @Expose
    private String videoId;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

}

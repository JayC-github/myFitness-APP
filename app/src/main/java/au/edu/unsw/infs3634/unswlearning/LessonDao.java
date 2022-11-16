package au.edu.unsw.infs3634.unswlearning;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface LessonDao {
    // return list of lesson from the table
    @Query("SELECT * FROM Lesson")
    List<Lesson> getLessons();

    @Query("SELECT * FROM Lesson where name == :name")
    Lesson getLesson(String name);

    /**
     * Updating only image_url
     * By lesson name
     */
    @Query("UPDATE Lesson SET image_url = :image_url WHERE name = :name")
    void updateImage(String image_url, String name);

    @Query("UPDATE Lesson SET videoId = :video_id WHERE name = :name")
    void updateVideo(String video_id, String name);

    @Query("SELECT * FROM Lesson WHERE `group` == :group")
    List<Lesson> getLessonGroup(String group);

    @Query("SELECT COUNT(*) FROM Lesson WHERE `group` == :group")
    int getLessonGroupNum(String group);

    @Query("SELECT COUNT(*) FROM Lesson")
    int getLessonNum();

    @Query("DELETE FROM Lesson")
    void deleteAll();

    @Delete
    void deleteLesson(Lesson... lessons);

    // insert list of lesson into lesson table
    @Insert
    void insertLessons(Lesson... lessons);
}

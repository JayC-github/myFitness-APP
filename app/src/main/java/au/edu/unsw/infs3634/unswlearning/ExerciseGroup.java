package au.edu.unsw.infs3634.unswlearning;

import java.sql.Array;
import java.util.ArrayList;

public class ExerciseGroup {

    private String name;

    public ExerciseGroup(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public static ArrayList<ExerciseGroup> getExerciseGroup() {
        ArrayList<ExerciseGroup> groups = new ArrayList<>();
        groups.add(new ExerciseGroup("abdominals"));
        groups.add(new ExerciseGroup("back"));
        groups.add(new ExerciseGroup("biceps"));
        groups.add(new ExerciseGroup("calves"));
        groups.add(new ExerciseGroup("chest"));
        groups.add(new ExerciseGroup("forearms"));
        groups.add(new ExerciseGroup("hamstring"));
        groups.add(new ExerciseGroup("triceps"));
        return groups;

    }

    public static ExerciseGroup findGroup(String query) {
        ArrayList<ExerciseGroup> groups = ExerciseGroup.getExerciseGroup();
        for (final ExerciseGroup group: groups) {
            if(group.getName().toLowerCase().equals(query.toLowerCase())) {
                return group;
            }
        }
        return null;
    }



}

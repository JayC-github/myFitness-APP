package au.edu.unsw.infs3634.unswlearning;

import java.sql.Array;
import java.util.ArrayList;

//exercise group object declaration
public class ExerciseGroup {

    private String name;

    //getters and setters
    public ExerciseGroup(String name) {
        this.name = name;
    }

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    //hardcoded exercise groups for prototype, chose most important groups
    public static ArrayList<ExerciseGroup> getExerciseGroup() {
        ArrayList<ExerciseGroup> groups = new ArrayList<>();
        groups.add(new ExerciseGroup("abdominals"));
        groups.add(new ExerciseGroup("abductors"));
        groups.add(new ExerciseGroup("biceps"));
        groups.add(new ExerciseGroup("calves"));
        groups.add(new ExerciseGroup("chest"));
        groups.add(new ExerciseGroup("forearms"));
        groups.add(new ExerciseGroup("glutes"));
        groups.add(new ExerciseGroup("hamstring"));
        groups.add(new ExerciseGroup("lats"));
        groups.add(new ExerciseGroup("lowerback"));
        groups.add(new ExerciseGroup("middleback"));
        groups.add(new ExerciseGroup("neck"));
        groups.add(new ExerciseGroup("quadriceps"));
        groups.add(new ExerciseGroup("traps"));
        groups.add(new ExerciseGroup("triceps"));

        return groups;

    }

    //method to return searched group based on string query
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

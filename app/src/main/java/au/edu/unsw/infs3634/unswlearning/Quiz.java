package au.edu.unsw.infs3634.unswlearning;

import java.util.ArrayList;

/**
 * quiz object declaration
 */
public class Quiz {
    // id of the quiz
    private int quizID;
    // name of the quiz
    private String name;
    // history score of the quiz
    private int history[];
    // question library
    private String questions[];
    // option library
    private String options[];
    // answer library
    private int answers[];

    /**
     * constructor for quiz
     * @param quizID        id of quiz
     * @param name          name of quiz
     * @param questions     string array of quiz questions
     * @param options       string array of options for quiz
     * @param answers       int array of the correct answers
     */
    public Quiz(int quizID, String name, String[] questions, String[] options, int [] answers) {
        this.quizID = quizID;
        this.name = name;
        this.questions = questions;
        this.options = options;
        this.answers = answers;
    }

    public int getQuizID() {
        return quizID;
    }

    public void setQuizID(int quizID) {
        this.quizID = quizID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getQuestions() {
        return questions;
    }

    public void setQuestions(String[] questions) {
        this.questions = questions;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    // return array of questions with solution

    /**
     * method to setup all the quizzes in the recyclerview with answers and options
     * @return arraylist of all the quizzes
     */
    public static ArrayList<Quiz> getQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        // manually generate a bunch of questions
        // with solutions

        String questions1[]  = {
                "Who's the best basketball player?",
                "What's the most popular sport in the world?",
                "How to live a healthy life?",
        };

        String options1[] = {
                "Kobe", "Yao", "Michael", "Lebron",
                "Basketball", "Swimming", "Cross Country", "Football",
                "Eat junk food", "Sleep late", "Exercise", "Smoke & drink",
        };

        int answers1[] = {0, 3, 2};

        String bicepsQuiz1[] = {
                "Which exercise is most effective at improving bicep strength?",
                "What is the most commonly used equipment in bicep exercises?",
                "How many bicep curls should a beginner do in a set?",
                "How many sets of bicep curls should a beginner do?",
                "What part of your body are you most likely to injure when performing bicep exercises?"
        };

        String bicepsOptions1[] = {
                "Hammer Curls", "Barbell Curls", "Concentration Curls", "EZ-Bar Curls",
                "Barbells", "Dumbbells", "EZ-Curl Bar", "No equipment needed",
                "5", "10", "15", "20",
                "2", "5", "10", "20",
                "Forearm", "Bicep", "Chest", "Elbow",
        };

        int bicepsAnswers1[] = {0, 1, 1, 2, 3};

        String bicepsQuiz2[] = {
                "Which of the following is a beginner level bicep exercise?",
                "Which of the following is an intermediate level bicep exercise?",
                "Which of the following is a health benefit of training biceps?",
                "When performing hammer curls, how far away from you should each dumbbell be held?",
                "When performing Incline Hammer Curls, what equipment is recommended to be used alongside dumbbells?"
        };

        String bicepsOptions2[] = {
                "Biceps Curl to Shoulder Press", "EZ-Bar Curls", "Zottman Curls", "Hammer Curls",
                "Flexor Incline Dumbbell Curls", "Wide-Grip Barbell Curls", "Incline Hammer Curls", "Concentration Curls",
                "Look massive", "Flex on people", "Improve upper body strength", "Increase grip strength",
                "As close as possible", "Arm's length", "As far away as possible", "Dumbbells aren't needed for this exercise",
                "Barbell", "EZ-Curl Bar", "Incline Bench", "No other equipment needed"
        };

        int bicepsAnswers2[] = {0, 3, 2, 1, 2};

        String chestQuiz1[] = {
                "Which exercise is most effective at improving chest strength?",
                "What is the most commonly used equipment in chest exercises?",
                "Which of the following is a beginner level chest exercise?",
                "Which of the following is a health benefit of training chest muscles?",
                "What part of your body are you most likely to injure when performing chest exercises?"
        };

        String chestOptions1[] = {
                "Dumbbell Bench Press", "Pushups", "Chest Dip", "Close-Grip Bench Press",
                "Barbell", "Cable", "Dumbbell", "No equipment needed",
                "Chest Dip", "Pushups", "Dumbbell Bench Press", "Bodyweight Flyes",
                "Burn Calories", "Improve Posture", "Flex on people", "Look big in Tshirts",
                "Chest", "Arms", "Legs", "Back"
        };

        int chestAnswers1[] = {0, 2, 3, 1, 3};


        // add quizzes
        quizzes.add(new Quiz(1, "sample", questions1, options1, answers1));
        quizzes.add(new Quiz(2, "Bicep quiz1", bicepsQuiz1, bicepsOptions1, bicepsAnswers1));
        quizzes.add(new Quiz(3, "Bicep quiz2", bicepsQuiz2, bicepsOptions2, bicepsAnswers2));
        quizzes.add(new Quiz(4, "Chest quiz1", chestQuiz1, chestOptions1, chestAnswers1));

        return quizzes;
    }

    /**
     * method to get the quizzes for specific modules
     * @param module        module that determines which quizzes are searched for
     * @return              arraylist of corresponding quizzes
     */
    public static ArrayList<Quiz> getQuizzes_on_module(String module) {
        ArrayList<Quiz> quizzes_list = new ArrayList<>();
        ArrayList<Quiz> quizzes = getQuizzes();
        for (Quiz quiz: quizzes) {
            // if the quiz name has the module name
            if (quiz.getName().contains(module)) {
                quizzes_list.add(quiz);
            }
        }

        return  quizzes_list;
    }


    /**
     * method to get quiz information for quiz detail
     * @param quizName      quiz name to search for quiz detail
     * @return              corresponding quiz to quiz name
     */
    public static Quiz getQuiz(String quizName) {
        ArrayList<Quiz> quizzes = getQuizzes();
        for (Quiz quiz: quizzes) {
            if (quiz.getName().toLowerCase().equals(quizName.toLowerCase())) {
                return quiz;
            }
        }
        return null;
    }

}

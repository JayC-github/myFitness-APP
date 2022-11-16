package au.edu.unsw.infs3634.unswlearning;

import java.util.ArrayList;

public class Quiz {
    // id of the quiz
    private int quizID;
    // name of the quiz
    private String name;
    // history score of the quiz
    private int history[];
    // total number of questions in the quiz
    // private String question_number;
    // question library
    private String questions[];
    // option library
    private String options[];
    // answer library
    private int answers[];

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
    public static ArrayList<Quiz> getQuizzes() {
        ArrayList<Quiz> quizzes = new ArrayList<>();
        // manually generate a bunch of questions
        // with solutions

        String questions1[]  = {
                "Who's the best",
                "Which sport do you play",
                "Why life's so hard",
        };

        String options1[] = {
                "KOBE", "YAO", "Michael", "JO MAMA",
                "Basketball", "Swimming", "Cross Country", "Football",
                "Work", "Study", "Exercise", "Lazy",
        };

        int answers1[] = {0, 3, 2};


        // add quizzes
        quizzes.add(new Quiz(1, "abdominals", questions1, options1, answers1));
        quizzes.add(new Quiz(2, "abdominals", questions1, options1, answers1));

        return  quizzes;
    }

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

    // this is for quiz detail to get the quiz info
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

package au.edu.unsw.infs3634.unswlearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
/**
 * adapter for quiz recyclerview
 */
public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Quiz> quizList, quizListFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_DIFFICULTY = 2;

    /**
     * constructor for quiz adapter
     * @param quizList                  list of quizzes
     * @param recyclerViewInterface     the quiz recyclerview interface
     */
    public QuizAdapter(List<Quiz> quizList, RecyclerViewInterface recyclerViewInterface) {
        this.quizList = quizList;
        this.quizListFiltered = quizList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    /**
     * method to help construct list rows in the RecyclerView
     * @param parent    parent view
     * @param viewType  viewType of parent
     * @return          the updated ViewHolder
     */
    @NonNull
    @Override
    public QuizAdapter.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_list_row, parent, false);
        return new QuizViewHolder(view, recyclerViewInterface);
    }

    /**
     * method to update each list row item with corresponding note name and last updated time
     * @param holder    viewholder for quiz adapter
     * @param position  position of corresponding lesson in the recyclerview list
     */
    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.QuizViewHolder holder, int position) {
        Quiz quiz = quizListFiltered.get(position);
        holder.quizName.setText(quiz.getName());
        holder.itemView.setTag(quiz.getName());
    }

    /**
     * method to return size of dataset
     * @return      size of dataset
     */
    @Override
    public int getItemCount() {
        return quizListFiltered.size();
    }

    /**
     * find handle to view items from quiz_list_row.xml layout
     */
    class QuizViewHolder extends RecyclerView.ViewHolder {
        private TextView quizName;

        public QuizViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            quizName = itemView.findViewById(R.id.tvQuizName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recyclerViewInterface != null) {
                        recyclerViewInterface.onItemClick((String) itemView.getTag());
                    }
                }
            });


        }
    }


}

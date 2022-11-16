package au.edu.unsw.infs3634.unswlearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.QuizViewHolder> {
    private List<Quiz> quizList, quizListFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_DIFFICULTY = 2;

    public QuizAdapter(List<Quiz> quizList, RecyclerViewInterface recyclerViewInterface) {
        this.quizList = quizList;
        this.quizListFiltered = quizList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public QuizAdapter.QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_home_list_row, parent, false);
        return new QuizViewHolder(view, recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizAdapter.QuizViewHolder holder, int position) {
        Quiz quiz = quizListFiltered.get(position);
        holder.quizName.setText(quiz.getName());
        holder.itemView.setTag(quiz.getName());
    }

    @Override
    public int getItemCount() {
        return quizListFiltered.size();
    }

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

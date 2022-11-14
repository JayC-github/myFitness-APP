package au.edu.unsw.infs3634.unswlearning;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataResponse;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//adapter for lesson recyclerview
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> implements Filterable {

    //declared context for lesson adapter
    Context mContextLessons;
    private List<Lesson> mLessons, mLessonsFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;

    //constructor method
    public LessonAdapter(Context context, List<Lesson> lessons, RecyclerViewInterface rvInterface) {
        mContextLessons = context;
        mLessons = lessons;
        mLessonsFiltered = lessons;
        recyclerViewInterface = rvInterface;
    }


    @NonNull
    @Override
    public LessonAdapter.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set list row for lesson recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_home_list_row, parent, false);
        return new LessonViewHolder(view, recyclerViewInterface);
    }


    //assign value ot each row in recyclerview based on position
    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        //update lesson detail
        Lesson lesson = mLessonsFiltered.get(position);
        holder.tvLessonName.setText(lesson.getName());
        holder.tvLessonDifficulty.setText(lesson.getDifficulty());
        holder.itemView.setTag(lesson.getName());

        // https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
        // for the lesson to get the image first

        // Use another API to get the pic URL link of the name
        /** execute an API call to get the youtube video id based on searched (muscle)*/
        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        YoutubeDataService service2 = retrofit2.create(YoutubeDataService.class);
        // add an exercise keyword to get the actual exercise video haha
        Call<YoutubeDataResponse> youtubeCall = service2.getVideoByName(lesson.getName() + " Exercise");
    
        youtubeCall.enqueue(new Callback<YoutubeDataResponse>() {
            @Override
            public void onResponse(Call<YoutubeDataResponse> call, Response<YoutubeDataResponse> response) {
                Log.d("lESSON.JAVA TEST", "Second API success");
                Log.d("lESSON.JAVA TEST", response.toString());
                Log.d("lESSON.JAVA TEST", String.valueOf(response.body()));
                // to handle exceed quota
                if (response.code() == 200) {
                    String image_url = response.body().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl();
                    Log.d("lESSON.JAVA TEST", image_url);
                    Picasso.get().load(image_url).into(holder.ivLesson);
                } else {
                    holder.ivLesson.setImageResource(mContextLessons.getResources().getIdentifier("biceps",
                            "drawable", "au.edu.unsw.infs3634.unswlearning"));
                }
            }

            @Override
            public void onFailure(Call<YoutubeDataResponse> call, Throwable t) {
                Log.d("lESSON.JAVA TEST", "API failure");
                Log.d("lESSON.JAVA TEST", t.toString());
            }
        });
    }

    //return number of items in recyclerview
    @Override
    public int getItemCount() {
        return mLessonsFiltered.size();
    }

    //returns updated list when searched
    @Override
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mLessonsFiltered = mLessons;
                } else {
                    ArrayList<Lesson> filteredList = new ArrayList<>();
                    for (Lesson lesson: mLessons) {
                        if (lesson.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(lesson);
                        }
                    }
                    mLessonsFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mLessonsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mLessonsFiltered = (ArrayList<Lesson>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    //set data to the adapter
    public void setData(List<Lesson> lessons) {
        mLessons.clear();
        mLessons.addAll(lessons);
        notifyDataSetChanged();
    }

    //find handle to view items from lesson_home_list_row.xml layout
    public static class LessonViewHolder extends RecyclerView.ViewHolder {
        ImageView ivLesson;
        TextView tvLessonName;
        TextView tvLessonDifficulty;

        public LessonViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            ivLesson = itemView.findViewById(R.id.ivLesson);
            tvLessonName = itemView.findViewById(R.id.tvLessonName);
            tvLessonDifficulty = itemView.findViewById(R.id.tvLessonDifficulty);
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

    //sort methods for list
    public void sort(final int sortMethod) {
        if (mLessonsFiltered.size() > 0) {
            Collections.sort(mLessonsFiltered, new Comparator<Lesson>() {
                @Override
                public int compare(Lesson l1, Lesson l2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return l1.getName().compareTo(l2.getName());
                    }
                    // By default sort the list by coin name
                    return l1.getName().compareTo(l2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}

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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataResponse;
import au.edu.unsw.infs3634.unswlearning.API.YoutubeDataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
/**
 * Adapter class for lesson recyclerview
 */
public class LessonAdapter extends RecyclerView.Adapter<LessonAdapter.LessonViewHolder> implements Filterable {
    private static final String TAG = "LessonAdapter";

    //declared context for lesson adapter
    Context mContextLessons;
    private List<Lesson> mLessons, mLessonsFiltered;
    private RecyclerViewInterface recyclerViewInterface;
    public static final int SORT_METHOD_NAME = 1;
    public static final int SORT_METHOD_LEVEL = 2; // remove this later
    // a lesson table in database
    private MainDatabase lessonDb;

    /**
     * constructor for lesson adapter
     * @param context           declared context for lessons
     * @param lessons           list of lessons
     * @param rvInterface       the lesson recyclerview interface
     */
    public LessonAdapter(Context context, List<Lesson> lessons, RecyclerViewInterface rvInterface) {
        mContextLessons = context;
        mLessons = lessons;
        mLessonsFiltered = lessons;
        recyclerViewInterface = rvInterface;
    }


    /**
     * method to help construct list rows in the RecyclerView
     * @param parent    parent view
     * @param viewType  viewType of parent
     * @return          the updated ViewHolder
     */
    @NonNull
    @Override
    public LessonAdapter.LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //set list row for lesson recyclerview
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_list_row, parent, false);
        return new LessonViewHolder(view, recyclerViewInterface);
    }

    /**
     * method to update each list row item with corresponding lesson name and image
     * @param holder    viewholder for lesson adapter
     * @param position  position of corresponding lesson in the recyclerview list
     */
    @Override
    public void onBindViewHolder(@NonNull LessonAdapter.LessonViewHolder holder, int position) {
        //update lesson detail
        Lesson lesson = mLessonsFiltered.get(position);
        holder.tvLessonName.setText(lesson.getName());
        holder.tvLessonDifficulty.setText(StringUtils.capitalize(lesson.getDifficulty()));
        holder.itemView.setTag(lesson.getName());

        // https://stackoverflow.com/questions/2471935/how-to-load-an-imageview-by-url-in-android
        // check if lesson has image_url
        // if image_url is empty, then try to use the API to get the image url
        // then update it into the database
        if (lesson.getImage_url() == null || lesson.getVideoId() == null) {
            Log.d(TAG, "ImageURL or lessonID is null, need to call API and update db");
            lessonDb = Room.databaseBuilder(mContextLessons, MainDatabase.class, "main-database").fallbackToDestructiveMigration().build();

            // Use API to get the pic URL link of the name
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
                    Log.d(TAG, "Youtube API success");
                     Log.d(TAG, response.toString());
                     Log.d(TAG, String.valueOf(response.body()));
                    // to handle exceed quota
                    if (response.code() == 200) {
                        String image_url = response.body().getItems().get(0).getSnippet().getThumbnails().getHigh().getUrl();
                        String videoId = response.body().getItems().get(0).getId().getVideoId();
                        Log.d(TAG, image_url);
                        Log.d(TAG, videoId);
                        // update the data in database too
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                lessonDb.lessonDao().updateImage(image_url, lesson.getName());
                                lessonDb.lessonDao().updateVideo(videoId, lesson.getName());
                            }
                        });
                        // also load it to the holder view
                        Picasso.get().load(image_url).into(holder.ivLesson);
                    } else {
                        holder.ivLesson.setImageDrawable(ContextCompat.getDrawable(mContextLessons, R.drawable.img_fail));
                    }
                }

                @Override
                public void onFailure(Call<YoutubeDataResponse> call, Throwable t) {
                    Log.d(TAG, "API failure");
                    Log.d(TAG, t.toString());
                }
            });

        } else { // store in the database already, just load it
            Log.d(TAG, "Not null, load image url into the view");
            Picasso.get().load(lesson.getImage_url()).into(holder.ivLesson);
        }
    }

    /**
     * method to return size of dataset
     * @return      size of dataset
     */
    @Override
    public int getItemCount() {
        return mLessonsFiltered.size();
    }

    /**
     * method to return and update recyclerview with filtered list of lessons when searched
     * @return      filterResults with values from filtered arrayList
     */
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

    /**
     * set data to the adapter
     * @param lessons       list of lessons to be set to adapter
     */
    public void setData(List<Lesson> lessons) {
        mLessons.clear();
        mLessons.addAll(lessons);
        notifyDataSetChanged();
    }

    /**
     * find handle to view items from lesson_list_row.xml layout
     */
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

    /**
     * method for sorting lessons and updating the recyclerview
     * @param sortMethod    method to sort by name alphabetically or level
     */
    public void sort(final int sortMethod) {
        if (mLessonsFiltered.size() > 0) {
            Collections.sort(mLessonsFiltered, new Comparator<Lesson>() {
                @Override
                public int compare(Lesson l1, Lesson l2) {
                    if (sortMethod == SORT_METHOD_NAME) {
                        return l1.getName().compareTo(l2.getName());
                    } else if (sortMethod == SORT_METHOD_LEVEL) {
                        return l1.getDifficulty().compareTo(l2.getDifficulty());
                    }
                    // By default sort the list by coin name
                    return l1.getName().compareTo(l2.getName());
                }

            });
        }
        notifyDataSetChanged();
    }
}

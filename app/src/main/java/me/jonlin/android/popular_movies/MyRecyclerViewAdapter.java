package me.jonlin.android.popular_movies;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import me.jonlin.android.popular_movies.model.Movie;

/*
Referenced
https://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        List<Movie> movies = null;
        if(MainActivity.favorite_movie_selected) {
            //mContext base class is activity.
            Log.d("testt", "mainviewmodel get instance");
            movies = MainViewModel.getInstance(((Activity) mContext).getApplication()).getValue();
        }
            else
            movies = MoviesSingleton.getInstance();

        if(movies.isEmpty())
            return;

        Picasso.with(mContext).load(MainActivity.BASE_IMAGE_URL + movies.get(position).getPosterThumbnail()).into(holder.myTextView);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return MoviesSingleton.getInstance().size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.image_iv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting
    // at click position
    Movie getItem(int id) {
        return MoviesSingleton.getInstance().get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
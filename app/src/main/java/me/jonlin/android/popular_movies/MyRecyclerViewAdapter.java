package me.jonlin.android.popular_movies;

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
Zhttps://stackoverflow.com/questions/40587168/simple-android-grid-example-using-recyclerview-with-gridlayoutmanager-like-the
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
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
        List<Movie> movies = MoviesSingleton.getInstance();
    //    for(int i = 0; i < movies.size(); i++)
      //      Log.d("testt", "i = " + i + "| "+ movies.size() + "| " + MoviesSingleton.getInstance().size() + movies.get(i).getPosterThumbnail());
        if(movies.isEmpty())
            return;


        Picasso.with(mContext).load(MainActivity.BASE_IMAGE_URL + movies.get(position).getPosterThumbnail()).into(holder.myTextView);
        //Test works on other sandwich jpg
//        Picasso.with(mContext).load("http://image.tmdb.org/t/p/original/c01Y4suApJ1Wic2xLmaq1QYcfoZ.jpg").into(holder.myTextView);


        //             posterImageURL = BASE_IMAGE_URL + movies.get(position).getPosterThumbnail();
//        Log.d("testt", "position: " + position);
//        Log.d("testt", "come inside" + posterImageURL);
        /*
        if(!movies.isEmpty() && position < movies.size()) {
                Log.d("testt", position + " | " + movies.size());
                String posterImageURL = BASE_IMAGE_URL + movies.get(position).getPosterThumbnail();
//            String posterImageURL = BASE_IMAGE_URL + movies.get(0).getPosterThumbnail();

                Log.d("testt", "come inside" + posterImageURL);
                Picasso.with(mContext).load("https://upload.wikimedia.org/wikipedia/commons/c/ca/Bosna_mit_2_Bratw%C3%BCrsten.jpg").into(holder.myTextView);
        }
        else {
            Log.d("testt", "position: " + position);
     //       holder.myTextView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.stockimg));
              holder.myTextView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_launcher_background));
              holder.myTextView.setVisibility(View.GONE);
        }
*/

        //TODO piccaoso redo
//        holder.myTextView.setImage(mData[position]);

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

    // convenience method for getting data at click position
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
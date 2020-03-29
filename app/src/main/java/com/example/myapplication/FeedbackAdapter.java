package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.Feedback;

import java.util.List;

import uz.jamshid.library.ExactRatingBar;

public class  FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    Context mContext;
    private List<Feedback> feedbackList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView userName, comment, starCount;
        public ImageView profImage;
        public ExactRatingBar ratingBar;
        public MyViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userName);
            comment = view.findViewById(R.id.comment);
            ratingBar = view.findViewById(R.id.rating_bar);
            starCount = view.findViewById(R.id.star_count);
            profImage = view.findViewById(R.id.profile_image);
        }
    }

    public FeedbackAdapter(List<Feedback> feedbackList, Context context) {
        this.feedbackList =feedbackList;
        this.mContext = context;
    }


    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_feedback_row
                        , parent, false);

        return new FeedbackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeedbackAdapter.MyViewHolder holder, int position) {

        Feedback feedback =feedbackList.get(position);

        holder.userName.setText(feedback.getUserName());
        holder.starCount.setText(feedback.getRatings());
        holder.comment.setText(feedback.getReview());
        if(Float.parseFloat(feedback.getRatings())> 4.9){
            holder.ratingBar.setStar(4.9999f);
        }else{
            holder.ratingBar.setStar(Float.parseFloat(feedback.getRatings()));
        }
        Glide.with(mContext).load(feedback.getImageURL()).into(holder.profImage);
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}

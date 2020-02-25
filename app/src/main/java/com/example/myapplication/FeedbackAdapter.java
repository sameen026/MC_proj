package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Feedback;

import java.util.List;

import uz.jamshid.library.ExactRatingBar;

public class  FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private List<Feedback> feedbackList;
    private FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView userName;
        public TextView comment, starCount;
        public ExactRatingBar ratingBar;
        public FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner;
        public MyViewHolder(View view, FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner) {
            super(view);
            userName = view.findViewById(R.id.userName);
            comment = view.findViewById(R.id.comment);
            ratingBar = view.findViewById(R.id.rating_bar);
            starCount = view.findViewById(R.id.star_count);
            this.onFeedbackClickListner=onFeedbackClickListner;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onFeedbackClickListner.onFeedbackClick(getAdapterPosition());
        }

        public interface OnFeedbackClickListner{
            void onFeedbackClick(int position);
        }
    }

    public FeedbackAdapter(List<Feedback> feedbackList, FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner) {
        this.feedbackList =feedbackList;
        this.onFeedbackClickListner=onFeedbackClickListner;
    }


    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_feedback_row
                        , parent, false);

        return new FeedbackAdapter.MyViewHolder(itemView,onFeedbackClickListner);
    }

    @Override
    public void onBindViewHolder(FeedbackAdapter.MyViewHolder holder, int position) {

        Feedback feedback =feedbackList.get(position);

        holder.userName.setText(feedback.getUserId());
        if(Float.parseFloat(feedback.getRatings())> 4.9){
            holder.ratingBar.setStar(4.9999f);
        }else{
            holder.ratingBar.setStar(Float.parseFloat(feedback.getRatings()));
        }
        holder.starCount.setText(feedback.getRatings());
        holder.comment.setText(feedback.getReview());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }
}

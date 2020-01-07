package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private List<Feedback> feedbackList;
    private FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner;

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public TextView comments;
        public FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner;
        public MyViewHolder(View view, FeedbackAdapter.MyViewHolder.OnFeedbackClickListner onFeedbackClickListner) {
            super(view);
            name = (TextView) view.findViewById(R.id.user_name_tv);
            comments = (TextView) view.findViewById(R.id.comment_tv);
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
                .inflate(R.layout.activity_plaza_list_row
                        , parent, false);

        return new FeedbackAdapter.MyViewHolder(itemView,onFeedbackClickListner);
    }

    @Override
    public void onBindViewHolder(FeedbackAdapter.MyViewHolder holder, int position) {

        Feedback feedback =feedbackList.get(position);

        holder.name.setText(feedback.getUserId());
        holder.comments.setText(feedback.getReview());
    }

    @Override
    public int getItemCount() {
        return feedbackList.size();
    }

}

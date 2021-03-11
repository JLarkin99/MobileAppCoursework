package com.example.mpacoursework;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class feedAdapter extends RecyclerView.Adapter<feedAdapter.ViewHolder>{
    private ArrayList<cardViewContents> mFeedList;
    private OnItemClickListener mListener;

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            imageView = itemView.findViewById(R.id.cardImageView);
            textView = itemView.findViewById(R.id.cardTextView);
            
            itemView.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v){
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
               }
            });

        }

    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public feedAdapter(ArrayList<cardViewContents> feedList){
        mFeedList = feedList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_feed,parent,false);
        ViewHolder vh = new ViewHolder(v, mListener);
        return vh;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cardViewContents currentItem = mFeedList.get(position);

        holder.imageView.setImageResource(currentItem.getImageResource());
        holder.textView.setText(currentItem.getText());


    }

    @Override
    public int getItemCount() {
        return mFeedList.size();
    }
}


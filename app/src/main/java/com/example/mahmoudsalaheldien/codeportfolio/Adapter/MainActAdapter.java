package com.example.mahmoudsalaheldien.codeportfolio.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahmoudsalaheldien.codeportfolio.VarListClass.NoteList;
import com.example.mahmoudsalaheldien.codeportfolio.R;

import java.util.List;

public class MainActAdapter extends RecyclerView.Adapter<MainActAdapter.MyViewHolder>{
    List<NoteList> codeList;

    public MainActAdapter(List codeList) {
        this.codeList = codeList;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView titleCode_TextView_recycleView_row,
                typeCode_recycleView_row;
        private ImageView rationImage_ImageView_recycView_row;

        public MyViewHolder(View itemView) {
            super(itemView);
            titleCode_TextView_recycleView_row = itemView.findViewById(R.id.titleCode_TextView_recycleView_row);
            typeCode_recycleView_row = itemView.findViewById(R.id.typeCode_recycleView_row);
            rationImage_ImageView_recycView_row = itemView.findViewById(R.id.rationImage_ImageView_recycView_row);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_recyclerview_row, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        NoteList noteList = codeList.get(position);
        if (noteList != null) {
            holder.titleCode_TextView_recycleView_row.setText(noteList.titleCode);
            holder.typeCode_recycleView_row.setText(noteList.typeCode);
            switch (noteList.ratingCode) {
                case 4:
                    holder.rationImage_ImageView_recycView_row.setImageResource(R.drawable.five_emoji);
                    break;
                case 3:
                    holder.rationImage_ImageView_recycView_row.setImageResource(R.drawable.four_emoji);
                    break;
                case 2:
                    holder.rationImage_ImageView_recycView_row.setImageResource(R.drawable.three_emoji);
                    break;
                case 1:
                    holder.rationImage_ImageView_recycView_row.setImageResource(R.drawable.two_emoji);
                    break;
                case 0:
                    holder.rationImage_ImageView_recycView_row.setImageResource(R.drawable.one_emoji);
            }
        }
    }

    @Override
    public int getItemCount() {
        return codeList.size();
    }

}

package com.sainfe.todowithspot.view.all;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;
import com.sainfe.todowithspot.R;
import com.sainfe.todowithspot.model.Todo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Todo> list = new ArrayList<>();
    private OnItemLongClickEventListener mItemClickListener;

    public interface OnItemLongClickEventListener {
        void onItemLongClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkbox;

        ViewHolder(View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.to_do_list);
            checkbox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mItemClickListener.onItemLongClick(view, position);
                    }
                    return false;
                }
            });
        }
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        CheckBox checkBox;
        TextView textView;

        DateViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.to_do_list);
            textView = itemView.findViewById(R.id.text_date);
            checkBox.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mItemClickListener.onItemLongClick(view, position);
                    }
                    return false;
                }
            });
        }
    }


    public AllAdapter(ArrayList<Todo> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view;
        // ?????? ?????? ??????
        if (viewType == 0) {
            view = inflater.inflate(R.layout.item_todo, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        } else {
            // ?????? ?????? ????????????
            view = inflater.inflate(R.layout.item_todo_with_date, parent, false);
            DateViewHolder vh = new DateViewHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // final??? ???????????? ??????????????? ?????? ?????????(T/F)??? ????????? ?????????
        final Todo item = list.get(position);
        if(holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            // ?????? ??????????????? ???????????? null??? ???????????????
            viewHolder.checkbox.setOnCheckedChangeListener(null);

            // ?????? ???????????? getter??? ?????? ???????????? ????????? ??????, setter??? ?????? ??? ?????? ????????? ?????? ??????????????? set??????
            viewHolder.checkbox.setChecked(item.getDone());

            viewHolder.checkbox.setText(item.getContent());
            // ??????????????? ???????????? ?????? ?????? ????????? ??????
            viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                    item.setDone(isChecked);
                    if (isChecked)
                        buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    else buttonView.setPaintFlags(0);
                }
            });
        } else if(holder instanceof DateViewHolder) {
            DateViewHolder viewHolder = (DateViewHolder) holder;
            // ?????? ??????????????? ???????????? null??? ???????????????
            viewHolder.textView.setText("" + getDateSimpleFormat(item.getTime()));
            viewHolder.checkBox.setOnCheckedChangeListener(null);

            // ?????? ???????????? getter??? ?????? ???????????? ????????? ??????, setter??? ?????? ??? ?????? ????????? ?????? ??????????????? set??????
            viewHolder.checkBox.setChecked(item.getDone());

            viewHolder.checkBox.setText(item.getContent());
            // ??????????????? ???????????? ?????? ?????? ????????? ??????
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                    item.setDone(isChecked);
                    if (isChecked)
                        buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    else buttonView.setPaintFlags(0);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return 1;
        } else {
            if (getDateSimpleFormat(list.get(position).getTime()) > getDateSimpleFormat(list.get(position - 1).getTime())) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    public int getDateSimpleFormat(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return Integer.parseInt(sdf.format(timestamp.toDate()));
    }

    public void setOnItemLongClickListener(OnItemLongClickEventListener listener) {
        mItemClickListener = listener;
    }
}

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
        // 일반 투두 일때
        if (viewType == 0) {
            view = inflater.inflate(R.layout.item_todo, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        } else {
            // 날짜 변경 투두일때
            view = inflater.inflate(R.layout.item_todo_with_date, parent, false);
            DateViewHolder vh = new DateViewHolder(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // final로 선언해야 체크박스의 체크 상태값(T/F)이 바뀌지 않는다
        final Todo item = list.get(position);
        if(holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            // 먼저 체크박스의 리스너를 null로 초기화한다
            viewHolder.checkbox.setOnCheckedChangeListener(null);

            // 모델 클래스의 getter로 체크 상태값을 가져온 다음, setter를 통해 이 값을 아이템 안의 체크박스에 set한다
            viewHolder.checkbox.setChecked(item.getDone());

            viewHolder.checkbox.setText(item.getContent());
            // 체크박스의 상태값을 알기 위해 리스너 부착
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
            // 먼저 체크박스의 리스너를 null로 초기화한다
            viewHolder.textView.setText("" + getDateSimpleFormat(item.getTime()));
            viewHolder.checkBox.setOnCheckedChangeListener(null);

            // 모델 클래스의 getter로 체크 상태값을 가져온 다음, setter를 통해 이 값을 아이템 안의 체크박스에 set한다
            viewHolder.checkBox.setChecked(item.getDone());

            viewHolder.checkBox.setText(item.getContent());
            // 체크박스의 상태값을 알기 위해 리스너 부착
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

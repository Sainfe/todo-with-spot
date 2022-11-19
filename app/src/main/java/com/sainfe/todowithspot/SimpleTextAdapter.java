package com.sainfe.todowithspot;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SimpleTextAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Item> list = new ArrayList<>();
    private OnItemLongClickEventListener mItemClickListener;

    public interface OnItemLongClickEventListener{
        void onItemLongClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CheckBox checkbox;

        ViewHolder(View itemView){
            super(itemView);

            checkbox = itemView.findViewById(R.id.to_do_list);

            checkbox.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View view) {
                    final int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        mItemClickListener.onItemLongClick(view, position);
                    }
                    return false;
                }
            });
        }
    }

    SimpleTextAdapter(ArrayList<Item> list){
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.activity_recycler_view_item, parent, false) ;
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;

        return vh ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // final로 선언해야 체크박스의 체크 상태값(T/F)이 바뀌지 않는다
        final Item item = list.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;
        // 먼저 체크박스의 리스너를 null로 초기화한다
        viewHolder.checkbox.setOnCheckedChangeListener(null);

        // 모델 클래스의 getter로 체크 상태값을 가져온 다음, setter를 통해 이 값을 아이템 안의 체크박스에 set한다
        viewHolder.checkbox.setChecked(item.getSelected());

        viewHolder.checkbox.setText(item.text);
        // 체크박스의 상태값을 알기 위해 리스너 부착
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked)
            {
                item.setSelected(isChecked);
                if(isChecked) buttonView.setPaintFlags(buttonView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                else buttonView.setPaintFlags(0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setOnItemLongClickListener(OnItemLongClickEventListener listener){
        mItemClickListener = listener;
    }
}

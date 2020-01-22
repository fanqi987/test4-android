package com.test.helloworld.test4android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.test.helloworld.test4android.R;
import com.test.helloworld.test4android.bean.Bean;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<Bean> beanList;
    Context c;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;
        View view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
        }
    }

    public RecyclerViewAdapter(List<Bean> list, Context c) {
        beanList = list;
        this.c = c;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //todo 解析布局时，必须关联到parent，以及不关联根布局，否则子布局的宽高会出错。
        View v = LayoutInflater.from(c).inflate(R.layout.bean, parent, false);
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(v);
        viewHolder.imageView = v.findViewById(R.id.bean_img);
        viewHolder.textView = v.findViewById(R.id.bean_text);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.imageView.setImageResource(beanList.get(position).getImgId());

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "点击了图像" + position, Toast.LENGTH_SHORT).show();
            }
        });
        holder.textView.setText(beanList.get(position).getName());

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c, "点击了view " + position, Toast.LENGTH_SHORT).show();
                Navigation.findNavController(holder.view).navigate(R.id.action_fragment2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return beanList.size();
    }
}

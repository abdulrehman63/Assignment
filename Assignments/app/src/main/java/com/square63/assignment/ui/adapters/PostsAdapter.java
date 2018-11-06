package com.square63.assignment.ui.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.square63.assignment.R;
import com.square63.assignment.databinding.ItemListPostBinding;
import com.square63.assignment.webapi.responses.HintModel;

import java.util.ArrayList;

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.Viewholder> {

    private final ArrayList<HintModel> data;
    private Context context;
    private LayoutInflater layoutInflater;
    private ItemListPostBinding binding;

    public PostsAdapter(ArrayList<HintModel> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null)
            layoutInflater = LayoutInflater.from(parent.getContext());
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.item_list_post, parent, false);
        return new Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.itemListPostBinding.setHintModel(data.get(position));

    }

    public void updateData(ArrayList<HintModel> hintModelArrayList) {
        data.addAll(hintModelArrayList);
        notifyDataSetChanged();

    }

    public void updateItem(int position, HintModel hintModel) {
        data.get(position).setSelected(hintModel.isSelected());
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class Viewholder extends RecyclerView.ViewHolder {

        ItemListPostBinding itemListPostBinding;

        public Viewholder(ItemListPostBinding itemListPostBinding) {
            super(itemListPostBinding.getRoot());
            this.itemListPostBinding = itemListPostBinding;

        }

    }
}

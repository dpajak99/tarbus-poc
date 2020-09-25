package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.ScheduleMediator;

import java.util.ArrayList;
import java.util.List;

public class ScheduleItemAdapter extends RecyclerView.Adapter<ScheduleItemAdapter.ViewHolder> {

  private List<ScheduleMediator> array = new ArrayList<>();

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tvDepartue;
    public TextView tvVariants;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvDepartue = itemView.findViewById(R.id.tv_departue);
      tvVariants = itemView.findViewById(R.id.tv_variants);
    }
  }

  public void update(List<ScheduleMediator> array) {
    this.array.clear();
    this.array.addAll(array);
    this.notifyDataSetChanged();
  }


  @NonNull
  @Override
  public ScheduleItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
    Context context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_schedule_item, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ScheduleMediator scheduleMediator = array.get(position);
    holder.tvDepartue.setText(scheduleMediator.getDepartue());
    holder.tvVariants.setText(scheduleMediator.getVariantsShortcut());
  }

  @Override
  public int getItemCount() {
    return array.size();
  }


}

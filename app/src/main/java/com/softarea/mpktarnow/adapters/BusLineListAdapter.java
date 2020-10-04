package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;

import java.util.ArrayList;
import java.util.List;

public class BusLineListAdapter extends RecyclerView.Adapter<BusLineListAdapter.ViewHolder> {
  private List<String> lines = new ArrayList<>();
  private Context context;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView value;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      value = itemView.findViewById(R.id.tv_line);
    }
  }

  public void update(List<String> lines) {
    this.lines.clear();
    this.lines.addAll(lines);
    this.notifyDataSetChanged();
  }

  public BusLineListAdapter() {

  }

  @NonNull
  @Override
  public BusLineListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_line, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    String line = lines.get(position);
    holder.value.setText(line);
  }

  @Override
  public int getItemCount() {
    return lines.size();
  }


}

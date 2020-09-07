package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;

import java.util.ArrayList;
import java.util.List;

public class ResultRouteLineAdapter extends RecyclerView.Adapter<ResultRouteLineAdapter.ViewHolder> {

  private List<Integer> lines = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView value;
    public ImageView walk;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      value = itemView.findViewById(R.id.route_line_line);
      walk = itemView.findViewById(R.id.route_line_walk);
    }
  }

  public void update(List<Integer> lines) {
    this.lines.addAll(lines);
    this.notifyDataSetChanged();
  }


  @NonNull
  @Override
  public ResultRouteLineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_route_line, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    int line = lines.get(position);
    if( line != -1 ) {
      holder.value.setVisibility(View.VISIBLE);
      holder.value.setText(String.valueOf(line));
    } else {
      holder.walk.setVisibility(View.VISIBLE);
    }
  }

  @Override
  public int getItemCount() {
    return lines.size();
  }


}

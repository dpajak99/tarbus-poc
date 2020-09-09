package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchBusListAdapter extends RecyclerView.Adapter<SearchBusListAdapter.ViewHolder> {

  private List<SearchResultPoint> searchResults = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {

    public TextView tvRouteLine;
    public TextView tvBusStopName;
    public TextView tvBusStopArrive;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      tvRouteLine = itemView.findViewById(R.id.route_line_line);
      tvBusStopName = itemView.findViewById(R.id.busstop_name);
      tvBusStopArrive = itemView.findViewById(R.id.busstop_arrive);
    }
  }

  public void update(List<SearchResultPoint> searchResults) {
    this.searchResults.addAll(searchResults);
    this.notifyDataSetChanged();
  }


  @NonNull
  @Override
  public SearchBusListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_search_list_busstop, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    SearchResultPoint searchResult = searchResults.get(position);
    holder.tvBusStopName.setText(searchResult.getBusStopName());
    holder.tvBusStopArrive.setText(TimeUtils.sec2HHMM(searchResult.getTimeInSec1())); //to jest okej
  }

  @Override
  public int getItemCount() {
    return searchResults.size();
  }


}

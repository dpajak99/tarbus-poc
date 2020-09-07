package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.utils.MathUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class ResultRouteBusStopsAdapter extends RecyclerView.Adapter<ResultRouteBusStopsAdapter.ViewHolder> {

  private List<SearchResultPoint> searchResults = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout layoutBusSection;
    public LinearLayout layoutWalkSection;
    public LinearLayout busDetails;
    public TextView tvWalkDestination;
    public TextView tvWalkTime;
    public TextView tvRouteDepartue;
    public TextView tvRouteLine;
    public ImageView icoBusPoint;
    public TextView tvBusStopName;
    public TextView tvBusStopArrive;

    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      layoutBusSection = itemView.findViewById(R.id.route_list_bus_section);
      layoutWalkSection = itemView.findViewById(R.id.route_list_walk_section);
      busDetails = itemView.findViewById(R.id.busDetails);

      tvWalkTime = itemView.findViewById(R.id.walk_time);
      tvWalkDestination = itemView.findViewById(R.id.walk_destination);
      tvRouteLine = itemView.findViewById(R.id.route_line_line);
      icoBusPoint = itemView.findViewById(R.id.buspoint_icon);
      tvBusStopName = itemView.findViewById(R.id.busstop_name);
      tvBusStopArrive = itemView.findViewById(R.id.busstop_arrive);
      tvRouteDepartue = itemView.findViewById(R.id.route_departue);

    }
  }

  public void update(List<SearchResultPoint> searchResults) {
    this.searchResults.addAll(searchResults);
    this.notifyDataSetChanged();
  }


  @NonNull
  @Override
  public ResultRouteBusStopsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                  int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_route_list_item, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    SearchResultPoint searchResult = searchResults.get(position);
    Log.i("TEST", searchResult.toString());
    if (searchResult.getBusLine().length() == 0 || (searchResult.getBusLine().length() != 0 && searchResult.isChangeBus())) {
      int status = 1;
      if (searchResult.isEnterBus()) {
        status = -1;
      }
      holder.layoutWalkSection.setVisibility(View.VISIBLE);

      if( position == 0) {
        holder.tvWalkTime.setText(MathUtils.calcDistanse(searchResult.getLat(), searchResult.getLng(), searchResults.get(position + 1).getLat(), searchResults.get(position + 1).getLng()) +  " metrów");
      } else {
        holder.tvWalkTime.setText(MathUtils.calcDistanse(searchResult.getLat(), searchResult.getLng(), searchResults.get(position - 1).getLat(), searchResults.get(position - 1).getLng()) + " metrów");
      }


      holder.tvWalkDestination.setText(searchResults.get(position + status).getBusStopName());
      if (searchResult.getBusLine().length() != 0 && searchResult.isChangeBus()) {
        holder.tvWalkDestination.setText(searchResults.get(position).getBusStopName());
      }
    }

    if (position != 0 && position != getItemCount() - 1) {
      if (searchResults.get(position + 1).isEnterBus() || searchResult.isEnterBus()) {
        holder.layoutBusSection.setVisibility(View.VISIBLE);
        holder.icoBusPoint.setImageResource(R.drawable.bs_point_end);
        holder.tvBusStopName.setText(searchResult.getBusStopName());
        holder.tvBusStopArrive.setText(TimeUtils.sec2HHMM(searchResult.getTimeInSec1())); //to jest okej

        if( searchResult.isEnterBus() ) {
          holder.busDetails.setVisibility(View.VISIBLE);
          holder.tvRouteLine.setText(searchResult.getBusLine());
          holder.tvRouteDepartue.setText(TimeUtils.calcDelayValue(searchResult.getTimeInSec2()));
          holder.icoBusPoint.setImageResource(R.drawable.bs_point_start);
        }
      }
    }
  }

  @Override
  public int getItemCount() {
    return searchResults.size();
  }


}

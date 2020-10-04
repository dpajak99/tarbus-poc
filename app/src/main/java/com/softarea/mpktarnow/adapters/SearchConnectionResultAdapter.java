package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.activities.MainActivity;
import com.softarea.mpktarnow.model.SearchResult;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.utils.StringUtils;
import com.softarea.mpktarnow.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchConnectionResultAdapter extends RecyclerView.Adapter<SearchConnectionResultAdapter.ViewHolder> {

  private List<SearchResult> searchResults = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView foundRoute;
    public RecyclerView foundRouteList;
    public TextView tvRouteTime;
    public View showRoute;
    public boolean isClicked;


    private String startTime;
    private String endTime;

    public TextView busStops;
    public LinearLayout goToMap;
    public ResultRouteLineAdapter resultRouteLineAdapter;
    public ResultRouteBusStopsAdapter resultRouteBusStopsAdapter;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      foundRoute = itemView.findViewById(R.id.tv_found_route);
      foundRouteList = itemView.findViewById(R.id.rv_found_route_list);
      tvRouteTime = itemView.findViewById(R.id.route_time);
      showRoute = itemView.findViewById(R.id.show_route);
      isClicked = false;
      goToMap = itemView.findViewById(R.id.go_to_map);
      busStops = itemView.findViewById(R.id.tv_bus_stops);
    }
  }

  public void updateArticles(List<SearchResult> searchResults) {
    this.searchResults.addAll(searchResults);
    this.notifyDataSetChanged();
  }


  @NonNull
  @Override
  public SearchConnectionResultAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                     int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    View listItem = layoutInflater.inflate(R.layout.item_search_result, parent, false);
    return new ViewHolder(listItem);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    SearchResult searchResult = searchResults.get(position);
    List<Integer> track = new ArrayList<>();
    track.add(-1);

    String busStopsList = "";

    for (int i = 0; i < searchResult.getData().size(); i++) {
      SearchResultPoint searchResultPoint = searchResult.getData().get(i);
      if( i == 1 ) {
        holder.startTime = TimeUtils.sec2HHMM(searchResultPoint.getTimeInSec1());
      } else if( i == searchResult.getData().size() - 2) {
        holder.endTime = TimeUtils.sec2HHMM(searchResultPoint.getTimeInSec1());
        holder.tvRouteTime.setText( holder.startTime + " - " + holder.endTime);
      }
      busStopsList = StringUtils.join(busStopsList, searchResultPoint.getBusStopName(), "\n");
      if (searchResultPoint.isEnterBus()) {
        if (i == 1) {
          track.add(Integer.parseInt(searchResultPoint.getBusLine()));
        } else {
          track.add(-1);
        }
      }
      if (searchResultPoint.isChangeBus()) {
        track.add(Integer.parseInt(searchResultPoint.getBusLine()));
      }
    }

    holder.busStops.setText(busStopsList);

    holder.resultRouteLineAdapter = new ResultRouteLineAdapter();
    holder.foundRoute.setHasFixedSize(true);
    holder.foundRoute.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    holder.foundRoute.setAdapter(holder.resultRouteLineAdapter);
    holder.resultRouteLineAdapter.update(track);

    holder.resultRouteBusStopsAdapter = new ResultRouteBusStopsAdapter();
    holder.foundRouteList.setHasFixedSize(true);
    holder.foundRouteList.setLayoutManager(new LinearLayoutManager(activity));
    holder.foundRouteList.setAdapter(holder.resultRouteBusStopsAdapter);
    holder.resultRouteBusStopsAdapter.update(searchResult.getData());

    holder.goToMap.setOnClickListener(view -> {
      if (!holder.isClicked) {
        holder.foundRouteList.setVisibility(View.VISIBLE);
        holder.isClicked = true;
      } else {
        holder.foundRouteList.setVisibility(View.GONE);
        holder.isClicked = false;
      }
    });

    holder.showRoute.setOnClickListener(view -> {
      Bundle result = new Bundle();
      MainActivity.searchConnectionList = searchResult.getData();
      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_search_connection_map, result);
    });

  }

  @Override
  public int getItemCount() {
    return searchResults.size();
  }


}

package com.softarea.mpktarnow.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.model.SearchResult;
import com.softarea.mpktarnow.model.SearchResultPoint;
import com.softarea.mpktarnow.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SearchConnectionResultAdapter extends RecyclerView.Adapter<SearchConnectionResultAdapter.ViewHolder> {

  private List<SearchResult> searchResults = new ArrayList<>();
  private Context context;
  private FragmentActivity activity;

  public static class ViewHolder extends RecyclerView.ViewHolder {
    public RecyclerView foundRoute;
    public RecyclerView foundRouteList;
    public TextView busStops;
    public ResultRouteLineAdapter resultRouteLineAdapter;
    public ResultRouteBusStopsAdapter resultRouteBusStopsAdapter;


    public ViewHolder(@NonNull View itemView) {
      super(itemView);
      foundRoute = itemView.findViewById(R.id.tv_found_route);
      foundRouteList = itemView.findViewById(R.id.rv_found_route_list);
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
    for(int i = 0; i < searchResult.getData().size(); i++) {
      SearchResultPoint searchResultPoint = searchResult.getData().get(i);
      busStopsList = StringUtils.join(busStopsList, searchResultPoint.getBusStopName(), "\n");
      if(searchResultPoint.isEnterBus()) {
        if(i == 1) {
          track.add(Integer.parseInt(searchResultPoint.getBusLine()));
        } else {
          track.add(-1);
        }
      }
      if(searchResultPoint.isChangeBus()) {
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
    Log.i("TEST", "Size: " + searchResult.getData().size());
    holder.resultRouteBusStopsAdapter.update(searchResult.getData());

   /* holder.busStop.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putInt("id", busStop.getId());

      Navigation.findNavController(holder.itemView).navigate(R.id.navigation_bus_stop_details, result);
    });*/
  }

  @Override
  public int getItemCount() {
    return searchResults.size();
  }


}

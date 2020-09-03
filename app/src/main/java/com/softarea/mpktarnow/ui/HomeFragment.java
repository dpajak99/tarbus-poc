package com.softarea.mpktarnow.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.softarea.mpktarnow.R;
import com.softarea.mpktarnow.adapters.ScheduleAdapter;
import com.softarea.mpktarnow.dao.MpkDAO;
import com.softarea.mpktarnow.database.model.ScheduleTokenDB;
import com.softarea.mpktarnow.model.BusStop;
import com.softarea.mpktarnow.utils.DatabaseUtils;

import java.util.List;


public class HomeFragment extends Fragment {
  public static ScheduleAdapter scheduleAdapter;
  public static ProgressDialog pd;
  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    setHasOptionsMenu(true);

    Button button = root.findViewById(R.id.button_map);
    button.setOnClickListener(view -> {
      Bundle result = new Bundle();
      result.putString("key", "busStopList");

      NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
      navController.navigate(R.id.navigation_map, result);
    });


    RecyclerView scheduleList = root.findViewById(R.id.list_schedules);
    scheduleAdapter = new ScheduleAdapter(getActivity());
    scheduleList.setHasFixedSize(true);
    scheduleList.setLayoutManager(new LinearLayoutManager(getActivity()));
    scheduleList.setAdapter(scheduleAdapter);

    if(DatabaseUtils.getDatabase(getContext()).scheduleTokenDBDAO().getScheduleTokenCount() == 0 ) {
      pd = new ProgressDialog(getContext());
      MpkDAO.getAndSaveCities(getContext());

      DatabaseUtils.getDatabase(getContext()).scheduleTokenDBDAO().setScheduleToken(new ScheduleTokenDB(0, 20));
    } else {
      List<BusStop> busStops = DatabaseUtils.getDatabase(getContext()).dbBusStopDAO().getAll();
      scheduleAdapter.updateArticles(busStops);
    }

    SearchView searchView = root.findViewById(R.id.search_view);
    searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        scheduleAdapter.getFilter().filter(newText);
        return false;
      }
    });

    return root;
  }
}

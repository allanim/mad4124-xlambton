package ca.lambton.allan.xlambton.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ca.lambton.allan.xlambton.R;
import ca.lambton.allan.xlambton.database.model.Mission;

public class MissionListAdapter extends ArrayAdapter<Mission> {

    private final Context context;
    private final List<Mission> missions;


    public MissionListAdapter(Context context, List<Mission> missions) {
        super(context, R.layout.mission_item, missions);
        this.context = context;
        this.missions = missions;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mission mission = missions.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        // set grid item
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.mission_item, parent, false);
        }

        TextView name = view.findViewById(R.id.mission_name);
        name.setText("Mission: " + mission.getDescription());

        TextView date = view.findViewById(R.id.mission_date);
        date.setText("Date: " + mission.getDate());

        TextView status = view.findViewById(R.id.mission_status);
        status.setText("Status: " + mission.getStatus());

        return view;
    }
}

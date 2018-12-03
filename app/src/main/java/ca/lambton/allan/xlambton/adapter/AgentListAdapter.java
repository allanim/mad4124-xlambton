package ca.lambton.allan.xlambton.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ca.lambton.allan.xlambton.R;
import ca.lambton.allan.xlambton.database.model.Agent;

public class AgentListAdapter extends ArrayAdapter<Agent> {

    private final Context context;
    private final List<Agent> agents;


    public AgentListAdapter(Context context, List<Agent> agents) {
        super(context, R.layout.list_item, agents);
        this.context = context;
        this.agents = agents;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Agent agent = agents.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        // set grid item
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        ImageView image = view.findViewById(R.id.item_photo);
        String dirAppPhoto = agent.getPhoto();
        if (dirAppPhoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(dirAppPhoto);
            Bitmap lowdefbitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
            image.setImageBitmap(lowdefbitmap);
        }

        TextView name = view.findViewById(R.id.item_name);
        name.setText(agent.getName());

        TextView level = view.findViewById(R.id.item_level);
        level.setText("Level: " + agent.getLevel());

        return view;
    }

}

package ca.lambton.allan.xlambton.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.lambton.allan.xlambton.R;
import ca.lambton.allan.xlambton.database.model.Agent;

public class AgentProfileAdapter extends ArrayAdapter<AgentProfileAdapter.Contents> {

    private final Context context;
    private final List<Contents> contentsList;

    public AgentProfileAdapter(Context context, Agent agent) {
        super(context, R.layout.contents_item);
        this.context = context;
        this.contentsList = convertContents(agent);
        addAll(contentsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Contents content = contentsList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        // set grid item
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.contents_item, parent, false);
        }

        TextView key = view.findViewById(R.id.key);
        key.setText(content.getKey());

        TextView value = view.findViewById(R.id.value);
        value.setText(content.getValue());

        return view;
    }

    private List<Contents> convertContents(Agent agent) {
        List<Contents> contentsList = new ArrayList<>();
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_NAME), agent.getName()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_LEVEL), agent.getLevel()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_AGENCY), agent.getAgency()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_WEB_SITE), agent.getWebSite()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_COUNTRY), agent.getCountry()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_PHONE), agent.getPhone()));
        contentsList.add(new Contents(toCamelCase(Agent.COLUMN_ADDRESS), agent.getAddress()));
        return contentsList;
    }

    public class Contents {
        private String key;
        private String value;

        public Contents(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    private String toCamelCase(final String column) {
        StringBuilder ret = new StringBuilder(column.length());
        for (final String word : column.split("_")) {
            if (!word.isEmpty()) {
                ret.append(word.substring(0, 1).toUpperCase());
                ret.append(word.substring(1).toLowerCase());
            }
            if (!(ret.length() == column.length()))
                ret.append(" ");
        }
        return ret.toString().trim();
    }
}

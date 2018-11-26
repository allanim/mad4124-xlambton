package ca.lambton.allan.xlambton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import ca.lambton.allan.xlambton.adapter.AgentListAdapter;
import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class AgentListActivity extends AppCompatActivity {

    private AgentRepository repository;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        // DAO
        repository = new AgentRepository(this);

        // List
        listView = findViewById(R.id.agent_list);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // TODO
        });
    }

    private void loadData() {
        List<Agent> agentList = repository.getAll();
        AgentListAdapter adapter = new AgentListAdapter(this, agentList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}

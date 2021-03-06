package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import ca.lambton.allan.xlambton.adapter.AgentListAdapter;
import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class AgentListActivity extends AppCompatActivity {

    private AgentRepository repository;
    private ListView listView;
    private String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_list);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // DAO
        repository = new AgentRepository(this);

        // search keyword
        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");

        // List
        listView = findViewById(R.id.agent_list);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Agent agent = (Agent) listView.getItemAtPosition(position);
            Intent goToProfile = new Intent(this, AgentProfileActivity.class);
            goToProfile.putExtra("id", agent.getId());
            startActivity(goToProfile);
        });
    }

    private void loadData() {
        List<Agent> agentList = repository.search(keyword);
        AgentListAdapter adapter = new AgentListAdapter(this, agentList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

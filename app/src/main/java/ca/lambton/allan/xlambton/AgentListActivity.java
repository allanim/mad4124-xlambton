package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
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
            // TODO
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
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                if (keyword != null) {
                    startActivity(new Intent(this, SearchActivity.class));
                    finish();
                } else {
                    NavUtils.navigateUpFromSameTask(this);
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

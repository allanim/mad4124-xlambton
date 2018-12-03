package ca.lambton.allan.xlambton;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import ca.lambton.allan.xlambton.adapter.MissionListAdapter;
import ca.lambton.allan.xlambton.database.model.Mission;
import ca.lambton.allan.xlambton.database.repository.MissionRepository;

public class MissionListActivity extends AppCompatActivity {

    private MissionRepository repository;
    private ListView missionList;

    private int agentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // dao
        repository = new MissionRepository(this);

        // get id
        agentId = getIntent().getIntExtra("id", 0);
        if (agentId == 0) {
            onBackPressed();
            return;
        }

        // set mission list
        missionList = findViewById(R.id.mission_list);
        registerForContextMenu(missionList);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadList() {
        // get missions
        List<Mission> missionList = repository.getAll(agentId);

        // load list
        MissionListAdapter adapter = new MissionListAdapter(this, missionList);
        this.missionList.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        loadList();
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_mission, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Mission mission = (Mission) missionList.getItemAtPosition(info.position);

        // on going
        MenuItem onGoing = menu.add("Going");
        onGoing.setOnMenuItemClickListener(item -> {
            mission.setStatus("On going");
            update(mission);
            return false;
        });

        // done
        MenuItem done = menu.add("Done");
        done.setOnMenuItemClickListener(item -> {
            mission.setStatus("Done");
            update(mission);
            return false;
        });

        // Cancel
        MenuItem cancel = menu.add("Cancel");
        cancel.setOnMenuItemClickListener(item -> {
            mission.setStatus("Cancelled");
            update(mission);
            return false;
        });

        if (mission.getStatus() != null) {
            if (mission.getStatus().equalsIgnoreCase("On going")) {
                onGoing.setVisible(false);
            } else if (mission.getStatus().equalsIgnoreCase("Done")) {
                onGoing.setVisible(false);
                done.setVisible(false);
                cancel.setVisible(false);
            } else if (mission.getStatus().equalsIgnoreCase("Cancelled")) {
                onGoing.setVisible(false);
                done.setVisible(false);
                cancel.setVisible(false);
            }
        }

        // delete
        MenuItem delete = menu.add("Delete");
        delete.setOnMenuItemClickListener(item -> {

            // delete
            repository.delete(mission);

            Toast.makeText(this,
                    "Delete mission " + mission.getDescription(), Toast.LENGTH_SHORT).show();
            loadList();
            return false;
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void update(Mission mission) {
        mission.setDate(null);
        repository.update(mission);
        Toast.makeText(this,
                "Update mission " + mission.getDescription(), Toast.LENGTH_SHORT).show();
        loadList();
    }

    private void showDialog() {
        final EditText input = new EditText(this);
        input.setSingleLine(true);
        input.setTextColor(Color.BLACK);
        input.setHint("Input Mission!");
        input.setHintTextColor(Color.BLACK);
        input.setPadding(64, 64, 64, 64);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(input)
                .setPositiveButton("Ok", (dialog, id) -> {
                    // add mission
                    Mission mission = new Mission();
                    mission.setDescription(input.getText().toString());
                    mission.setAgentId(agentId);
                    mission.setStatus("TODO");

                    repository.insert(mission);
                    dialog.dismiss();
                    loadList();
                })
                .setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        builder.create().show();
    }
}

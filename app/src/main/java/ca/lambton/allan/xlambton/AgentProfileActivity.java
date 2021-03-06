package ca.lambton.allan.xlambton;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import ca.lambton.allan.xlambton.adapter.AgentProfileAdapter;
import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class AgentProfileActivity extends AppCompatActivity {

    private AgentRepository repository;
    private ListView listView;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_profile);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // DAO
        repository = new AgentRepository(this);

        // List
        listView = findViewById(R.id.agent_profile);

        // search keyword
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_form_ok:
                Intent intent = new Intent(this, AgentFormActivity.class);
                Agent agent = repository.getOne(id);
                intent.putExtra("agent", agent);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        // get data
        Agent agent = repository.getOne(id);

        // set list
        AgentProfileAdapter adapter = new AgentProfileAdapter(this, agent);
        listView.setAdapter(adapter);

        if (agent.getPhoto() != null) {
            ImageView mPhoto = findViewById(R.id.item_photo);
            Bitmap bitmap = BitmapFactory.decodeFile(agent.getPhoto());
            Bitmap lowDefBitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            mPhoto.setImageBitmap(lowDefBitmap);
            mPhoto.setTag(agent.getPhoto());
        }

        // phone
        ImageView phone = findViewById(R.id.pf_phone);
        if (notEmpty(agent.getPhone())) {
            phone.setOnClickListener(v -> {
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CALL_PHONE}, 111);
                } else {
                    Intent itemCall = new Intent(Intent.ACTION_CALL);
                    itemCall.setData(Uri.parse("tel:" + agent.getPhone()));
                    startActivity(itemCall);
                }
            });
        } else {
            phone.setVisibility(View.GONE);
        }

        // SMS
        ImageView sms = findViewById(R.id.pf_sms);
        if (notEmpty(agent.getPhone())) {
            sms.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("smsto:" + agent.getPhone()));
                intent.putExtra("sms_body", "Hi " + agent.getName() + "!\n");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            });
        } else {
            sms.setVisibility(View.GONE);
        }

        // website
        ImageView website = findViewById(R.id.pf_website);
        if (notEmpty(agent.getWebSite())) {
            website.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                String site = agent.getWebSite();
                if (!(site.startsWith("http://") || site.startsWith("https://"))) {
                    site = "http://" + site;
                }
                intent.setData(Uri.parse(site));
                startActivity(intent);
            });
        } else {
            website.setVisibility(View.GONE);
        }

        // map
        ImageView map = findViewById(R.id.pf_map);
        if (notEmpty(agent.getAddress())) {
            map.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=" + agent.getAddress()));
                startActivity(intent);
            });
        } else {
            map.setVisibility(View.GONE);
        }

        // photo
        ImageView photo = findViewById(R.id.pf_photo);
        if (notEmpty(agent.getPhone())) {
            photo.setOnClickListener(v -> {
                Intent intent = new Intent(this, SendPhotoActivity.class);
                intent.putExtra("agent", agent);
                startActivity(intent);
            });

        } else {
            photo.setVisibility(View.GONE);
        }

        // mission
        ImageView mission = findViewById(R.id.pf_mission);
        mission.setOnClickListener(v -> {
            Intent intent = new Intent(this, MissionListActivity.class);
            intent.putExtra("id", agent.getId());
            startActivity(intent);
        });
    }

    private boolean notEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

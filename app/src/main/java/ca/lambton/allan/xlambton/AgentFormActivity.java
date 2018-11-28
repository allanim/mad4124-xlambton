package ca.lambton.allan.xlambton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;

import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class AgentFormActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 990;
    private String photoPath;

    private AgentRepository repository;
    private AgentFormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_form);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // element
        helper = new AgentFormHelper(this);

        // DAO
        repository = new AgentRepository(this);

        Intent intent = getIntent();
        Agent agent = (Agent) intent.getSerializableExtra("agent");
        if (agent != null) {
            helper.fillForm(agent);
        }

        // photo
        Button buttonFormPhoto = findViewById(R.id.button_form_photo);
        buttonFormPhoto.setOnClickListener(v -> {
            photoPath = getExternalFilesDir(null)
                    + "/" + System.currentTimeMillis() + ".jpg";

            // action camera
            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, converter(photoPath));
            startActivityForResult(intentCamera, CAMERA_CODE);
        });
    }

    private Uri converter(String filePath) {
        File file = new File(filePath);

        if (Build.VERSION.SDK_INT < 24) {
            return Uri.fromFile(file);
        } else {
            return FileProvider.getUriForFile(this,
                    "ca.lambton.allan.xlambton.provider", file);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_CODE) {
                helper.loadImage(photoPath);
            }
        }
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
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.menu_form_ok:
                if (helper.valid()) {
                    Agent agent = helper.getAgent();
                    if (agent.getId() == null) {
                        repository.insert(agent);
                    } else {
                        repository.update(agent);
                    }

                    Toast.makeText(AgentFormActivity.this,
                            "Saved Agent: " + agent.getName(), Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

package ca.lambton.allan.xlambton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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

        // element
        helper = new AgentFormHelper(this);

        // DAO
        repository = new AgentRepository(this);

        Intent intent = getIntent();
        Agent agent = (Agent) intent.getSerializableExtra("agent");

        // photo
        Button buttonFormPhoto = findViewById(R.id.button_form_photo);
        buttonFormPhoto.setOnClickListener(v -> {
            photoPath = getExternalFilesDir(null)
                    + "/" + System.currentTimeMillis() + ".jpg";
            File filePhoto = new File(photoPath);

            // photoUri
            Uri photoUri = FileProvider.getUriForFile(AgentFormActivity.this,
                    "ca.lambton.allan.xlambton.provider", filePhoto);

            Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

            startActivityForResult(intentCamera, CAMERA_CODE);
        });
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
            case R.id.menu_form_ok:
                Agent agent = helper.getAgent();

                if (agent.getId() == null) {
                    repository.insert(agent);
                } else {
                    repository.update(agent);
                }

                Toast.makeText(AgentFormActivity.this,
                        "Student" + agent.getName() + " Saved", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

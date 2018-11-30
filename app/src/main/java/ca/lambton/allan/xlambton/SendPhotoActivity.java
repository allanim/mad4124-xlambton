package ca.lambton.allan.xlambton;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ca.lambton.allan.xlambton.adapter.SendPhotoAdapter;
import ca.lambton.allan.xlambton.database.model.Agent;

public class SendPhotoActivity extends AppCompatActivity {

    private static final int CAMERA_CODE = 900;
    private static final int SMS_CODE = 888;

    private List<String> photoList;
    private GridView gridView;
    private Button sendPhoto;
    private String tempTakenPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_photo);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // init photo, total 4 photos
        photoList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            photoList.add("");
        }

        // get agent
        Agent agent = (Agent) getIntent().getSerializableExtra("agent");

        // set grid
        gridView = findViewById(R.id.grid_photo);
        gridView.setOnItemClickListener((parent, view, position, id) -> takePhoto(position));

        // set send photo
        sendPhoto = findViewById(R.id.send_photo);
        sendPhoto.setText("Send photo(s) to " + agent.getName());
        sendPhoto.setOnClickListener(v -> {
            if (!hasPhoto()) {
                return;
            }


            Intent sendIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            sendIntent.addCategory(Intent.CATEGORY_DEFAULT);
//            sendIntent.setData(Uri.parse("mmsto:" + agent.getPhone()));
            sendIntent.setType("image/*");

            sendIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, agent.getPhone());
            sendIntent.putExtra("address", agent.getPhone());
            sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi " + agent.getName() + "!\n");
            sendIntent.putExtra("sms_body", "Hi " + agent.getName() + "!\n");

            sendIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, converter(getPhotos()));
            sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivity(sendIntent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void loadGrid() {
        gridView.setAdapter(new SendPhotoAdapter(this, photoList));
    }

    private void loadSendButton() {
        boolean hasPhoto = hasPhoto();
        sendPhoto.setEnabled(hasPhoto);
        sendPhoto.setAlpha(hasPhoto ? 1.0f : 0.4f);
    }

    private boolean hasPhoto() {
        boolean hasPhoto = false;
        for (String path : photoList) {
            if (!path.isEmpty()) {
                hasPhoto = true;
            }
        }
        return hasPhoto;
    }

    private String[] getPhotos() {
        List<String> hasPhoto = new ArrayList<>();
        for (String path : photoList) {
            if (!path.isEmpty()) {
                hasPhoto.add(path);
            }
        }
        return hasPhoto.toArray(new String[0]);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadGrid();
        loadSendButton();
    }

    public void takePhoto(int position) {
        tempTakenPhoto = getExternalFilesDir(null)
                + "/" + System.currentTimeMillis() + ".jpg";

        // action camera
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, converter(tempTakenPhoto));
        startActivityForResult(intentCamera, CAMERA_CODE + position);
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

    private ArrayList<Uri> converter(String... filePaths) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for (String filePath : filePaths) {
            uriList.add(converter(filePath));
        }
        return uriList;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode >= CAMERA_CODE) {

                // update photo
                int position = requestCode - CAMERA_CODE;
                photoList.set(position, tempTakenPhoto);

                // refresh
                loadGrid();
                loadSendButton();
            }
        }
    }
}

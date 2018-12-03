package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;
import ca.lambton.allan.xlambton.utils.JsonUtils;

import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    private static final int SMS_PERMISSION_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check sms receiver
        if (!hasReadSmsPermission()) {
            // request sms permission
            requestReadAndSendSmsPermission();
        }

        new Handler().postDelayed(() -> {
            // init data
            initData();

            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }

    // check permission
    private boolean hasReadSmsPermission() {
        return ContextCompat.checkSelfPermission(this, READ_SMS) == PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, RECEIVE_SMS) == PERMISSION_GRANTED;
    }

    // request sms permission
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, READ_SMS)) {
            Log.d(this.getClass().getSimpleName(),
                    "shouldShowRequestPermissionRationale(), no permission requested");
            return;
        }
        ActivityCompat.requestPermissions(this,
                new String[]{READ_SMS, RECEIVE_SMS}, SMS_PERMISSION_CODE);
    }

    // init data
    private void initData() {
        AssetManager assetManager = getAssets();
        AgentRepository repository = new AgentRepository(this);

        // get & set Agent data
        int agentCount = repository.getCount();
        Log.i(this.getClass().getSimpleName(), "AGENT COUNT : " + agentCount);
        if (agentCount == 0) {
            try {
                InputStream agentListFile = assetManager.open("Agents.json");
                AgentList agentList = JsonUtils.readJson(agentListFile, AgentList.class);
                for (Agent agent : agentList) {
                    repository.insert(agent);
                    Log.i(this.getClass().getSimpleName(),
                            "ADD Agent Data : " + agent.getUsername());
                }
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), e.getMessage());
            }
        }
    }

    public static class AgentList extends ArrayList<Agent> {
    }
}

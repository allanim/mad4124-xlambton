package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import ca.lambton.allan.xlambton.database.model.User;
import ca.lambton.allan.xlambton.database.repository.UserRepository;
import ca.lambton.allan.xlambton.utils.JsonUtils;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            updateData();

            Intent i = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        }, SPLASH_TIME_OUT);
    }

    private void updateData() {
        AssetManager assetManager = getAssets();
        UserRepository repository = new UserRepository(this);

        // get & set user data
        int userCount = repository.getCount();
        Log.i(this.getClass().getSimpleName(), "USER COUNT : " + userCount);
        if (userCount == 0) {
            try {
                InputStream userListFile = assetManager.open("Users.json");
                UserList userList = JsonUtils.readJson(userListFile, UserList.class);
                for (User user : userList) {
                    repository.insert(user);
                    Log.i(this.getClass().getSimpleName(), "ADD User Data : " + user.getName());
                }
            } catch (IOException e) {
                Log.e(this.getClass().getSimpleName(), e.getMessage());
            }
        }
    }


    public static class UserList extends ArrayList<User> {
    }
}

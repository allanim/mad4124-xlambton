package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.repository.AgentRepository;
import ca.lambton.allan.xlambton.utils.SharedPreferencesUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);

        // login
        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> {
            // validation
            if (username.getText().toString().isEmpty()) {
                username.setError("Please input the username");
                username.requestFocus();
                return;
            } else if (!isUsernameValid(username.getText().toString())) {
                username.setError("Invalid the username");
                username.requestFocus();
                return;
            }
            if (password.getText().toString().isEmpty()) {
                password.setError("Please input the Password");
                password.requestFocus();
                return;
            } else if (!isPasswordValid(password.getText().toString())) {
                password.setError("Invalid the password");
                password.requestFocus();
                return;
            }

            // login process
            AgentRepository repository = new AgentRepository(LoginActivity.this);
            Agent entity = repository.login(username.getText().toString(), password.getText().toString());

            // fail
            if (entity == null) {
                username.setError("Invalid username or password");
                Toast.makeText(LoginActivity.this,
                        "Invalid username or password", Toast.LENGTH_LONG).show();
            }
            // success
            else {

                // save login info
                SharedPreferencesUtils.instance(LoginActivity.this)
                        .editor()
                        .putInt("ID", entity.getId())
                        .putInt("MID", entity.getId())
                        .putString("USERNAME", entity.getUsername())
                        .apply();

                // go to the main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // remove id
        SharedPreferencesUtils.instance(LoginActivity.this)
                .editor()
                .remove("ID")
                .remove("USERNAME")
                .apply();

        username.setText("");
        username.setError(null);
        password.setText("");
        password.setError(null);
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

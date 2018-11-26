package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.lambton.allan.xlambton.database.model.User;
import ca.lambton.allan.xlambton.database.repository.UserRepository;
import ca.lambton.allan.xlambton.utils.SharedPreferencesUtils;

public class LoginActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.name);
        password = findViewById(R.id.password);

        // login
        Button login = findViewById(R.id.login_button);
        login.setOnClickListener(v -> {
            // validation
            if (name.getText().toString().isEmpty()) {
                name.setError("Please input the name");
                name.requestFocus();
                return;
            } else if (!isNameValid(name.getText().toString())) {
                name.setError("Invalid the name");
                name.requestFocus();
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
            UserRepository repository = new UserRepository(LoginActivity.this);
            User entity = repository.login(name.getText().toString(), password.getText().toString());

            // fail
            if (entity == null) {
                name.setError("Invalid name or password");
                Toast.makeText(LoginActivity.this,
                        "Invalid name or password", Toast.LENGTH_LONG).show();
            }
            // success
            else {

                // save login info
                SharedPreferencesUtils.instance(LoginActivity.this)
                        .editor()
                        .putInt("ID", entity.getId())
                        .putString("NAME", entity.getName())
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
                .remove("NAME")
                .apply();

        name.setText("");
        name.setError(null);
        password.setText("");
        password.setError(null);
    }

    private boolean isNameValid(String name) {
        return name.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
}

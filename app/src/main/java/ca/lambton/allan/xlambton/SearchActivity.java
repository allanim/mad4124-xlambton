package ca.lambton.allan.xlambton;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import ca.lambton.allan.xlambton.database.repository.AgentRepository;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // action bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // dao
        AgentRepository repository = new AgentRepository(this);

        EditText mSearch = findViewById(R.id.search_name);
        Button btnSearch = findViewById(R.id.btn_search);

        // click button
        btnSearch.setOnClickListener(v -> {
            String keyword = mSearch.getText().toString();

            // valid
            if (keyword.isEmpty()) {
                mSearch.setError(getResources().getString(R.string.error_field_required));
                mSearch.requestFocus();
                return;
            } else if (repository.searchCount(keyword) == 0) {
                mSearch.setError(getResources().getString(R.string.no_result));
                mSearch.requestFocus();
                return;
            }

            Intent intent = new Intent(this, AgentListActivity.class);
            intent.putExtra("keyword", keyword);
            startActivity(intent);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

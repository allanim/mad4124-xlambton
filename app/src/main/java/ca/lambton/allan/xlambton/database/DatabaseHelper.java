package ca.lambton.allan.xlambton.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ca.lambton.allan.xlambton.database.model.Agent;
import ca.lambton.allan.xlambton.database.model.Mission;
import ca.lambton.allan.xlambton.database.model.User;

/**
 * Database Helper
 *
 * @author Allan Im
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "C0724540_xlambton";

    // Table list
    private static final String[] tableNames = {
            User.TABLE_NAME,
            Agent.TABLE_NAME,
            Mission.TABLE_NAME
    };

    private static final String[] createTableQueries = {
            User.CREATE_TABLE,
            Agent.CREATE_TABLE,
            Mission.CREATE_TABLE
    };

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        for (String createTableQuery : createTableQueries) {
            Log.d("DB.CREATE", "SQL : " + createTableQuery);
            sqLiteDatabase.execSQL(createTableQuery);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("DB.UPGRADE", "Called onUpgrade");

        for (String tableName : tableNames) {
            // Drop older table if existed
            sqLiteDatabase.execSQL(String.format("DROP TABLE IF EXISTS %s", tableName));
        }

        // Create tables again
        onCreate(sqLiteDatabase);
    }
}

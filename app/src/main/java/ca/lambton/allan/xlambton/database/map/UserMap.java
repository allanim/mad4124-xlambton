package ca.lambton.allan.xlambton.database.map;

import android.content.ContentValues;
import android.database.Cursor;

import ca.lambton.allan.xlambton.database.model.User;


public class UserMap implements AndroidContentMap<User> {

    public ContentValues toContentValues(final User basicInfo) {

        ContentValues values = new ContentValues();
        values.put(User.COLUMN_ID, basicInfo.getId());
        values.put(User.COLUMN_NAME, basicInfo.getName());
        values.put(User.COLUMN_PASSWORD, basicInfo.getPassword());

        return values;
    }

    public User toEntity(final Cursor cursor) {

        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex(User.COLUMN_ID)));
        user.setName(cursor.getString(cursor.getColumnIndex(User.COLUMN_NAME)));
        user.setPassword(cursor.getString(cursor.getColumnIndex(User.COLUMN_PASSWORD)));

        return user;
    }
}

package ca.lambton.allan.xlambton.database.map;

import android.content.ContentValues;
import android.database.Cursor;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import ca.lambton.allan.xlambton.database.model.Mission;


public class MissionMap implements AndroidContentMap<Mission> {

    public ContentValues toContentValues(final Mission basicInfo) {

        ContentValues values = new ContentValues();
        values.put(Mission.COLUMN_ID, basicInfo.getId());
        values.put(Mission.COLUMN_AGENT_ID, basicInfo.getAgentId());
        values.put(Mission.COLUMN_DESCRIPTION, basicInfo.getDescription());
        values.put(Mission.COLUMN_STATUS, basicInfo.getStatus());

        if (basicInfo.getDate() != null) {
            values.put(Mission.COLUMN_DATE, basicInfo.getDate());
        }

        return values;
    }

    public Mission toEntity(final Cursor cursor) {

        Mission mission = new Mission();
        mission.setId(cursor.getInt(cursor.getColumnIndex(Mission.COLUMN_ID)));
        mission.setAgentId(cursor.getInt(cursor.getColumnIndex(Mission.COLUMN_AGENT_ID)));
        mission.setDescription(cursor.getString(cursor.getColumnIndex(Mission.COLUMN_DESCRIPTION)));
        mission.setStatus(cursor.getString(cursor.getColumnIndex(Mission.COLUMN_STATUS)));

        String datetime = cursor.getString(cursor.getColumnIndex(Mission.COLUMN_DATE));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        try {
            Date date = dateFormat.parse(datetime);
            DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.getDefault());
//            df.setTimeZone(TimeZone.getTimeZone("EST"));
            mission.setDate(df.format(date));
        } catch (ParseException ignored) {
            ignored.printStackTrace();
        }

        return mission;
    }
}

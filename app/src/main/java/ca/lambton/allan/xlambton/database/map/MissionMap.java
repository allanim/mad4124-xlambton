package ca.lambton.allan.xlambton.database.map;

import android.content.ContentValues;
import android.database.Cursor;

import ca.lambton.allan.xlambton.database.model.Mission;


public class MissionMap implements AndroidContentMap<Mission> {

    public ContentValues toContentValues(final Mission basicInfo) {

        ContentValues values = new ContentValues();
        values.put(Mission.COLUMN_ID, basicInfo.getId());
        values.put(Mission.COLUMN_AGENT_ID, basicInfo.getAgentId());
        values.put(Mission.COLUMN_DESCRIPTION, basicInfo.getDescription());
        values.put(Mission.COLUMN_DATE, basicInfo.getDate());
        values.put(Mission.COLUMN_STATUS, basicInfo.getStatus());

        return values;
    }

    public Mission toEntity(final Cursor cursor) {

        Mission mission = new Mission();
        mission.setId(cursor.getInt(cursor.getColumnIndex(Mission.COLUMN_ID)));
        mission.setAgentId(cursor.getInt(cursor.getColumnIndex(Mission.COLUMN_AGENT_ID)));
        mission.setDescription(cursor.getString(cursor.getColumnIndex(Mission.COLUMN_DESCRIPTION)));
        mission.setDate(cursor.getString(cursor.getColumnIndex(Mission.COLUMN_DATE)));
        mission.setStatus(cursor.getString(cursor.getColumnIndex(Mission.COLUMN_STATUS)));

        return mission;
    }
}

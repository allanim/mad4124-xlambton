package ca.lambton.allan.xlambton.database.map;

import android.content.ContentValues;
import android.database.Cursor;

import ca.lambton.allan.xlambton.database.model.Agent;


public class AgentMap implements AndroidContentMap<Agent> {

    public ContentValues toContentValues(final Agent basicInfo) {

        ContentValues values = new ContentValues();
        values.put(Agent.COLUMN_ID, basicInfo.getId());
        values.put(Agent.COLUMN_USERNAME, basicInfo.getUsername());
        values.put(Agent.COLUMN_PASSWORD, basicInfo.getPassword());
        values.put(Agent.COLUMN_NAME, basicInfo.getName());
        values.put(Agent.COLUMN_PHOTO, basicInfo.getPhoto());
        values.put(Agent.COLUMN_LEVEL, basicInfo.getLevel());
        values.put(Agent.COLUMN_AGENCY, basicInfo.getAgency());
        values.put(Agent.COLUMN_WEB_SITE, basicInfo.getWebSite());
        values.put(Agent.COLUMN_ADDRESS, basicInfo.getAddress());
        values.put(Agent.COLUMN_COUNTRY, basicInfo.getCountry());
        values.put(Agent.COLUMN_PHONE, basicInfo.getPhone());

        return values;
    }

    public Agent toEntity(final Cursor cursor) {

        Agent agent = new Agent();
        agent.setId(cursor.getInt(cursor.getColumnIndex(Agent.COLUMN_ID)));
        agent.setUsername(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_USERNAME)));
        agent.setPassword(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_PASSWORD)));
        agent.setName(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_NAME)));
        agent.setPhoto(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_PHOTO)));
        agent.setLevel(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_LEVEL)));
        agent.setAgency(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_AGENCY)));
        agent.setWebSite(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_WEB_SITE)));
        agent.setAddress(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_ADDRESS)));
        agent.setCountry(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_COUNTRY)));
        agent.setPhone(cursor.getString(cursor.getColumnIndex(Agent.COLUMN_PHONE)));

        return agent;
    }
}

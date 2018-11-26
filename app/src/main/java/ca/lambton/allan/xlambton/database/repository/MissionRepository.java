package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;

import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.map.MissionMap;
import ca.lambton.allan.xlambton.database.model.Mission;

import static ca.lambton.allan.xlambton.database.model.Mission.COLUMN_ID;
import static ca.lambton.allan.xlambton.database.model.Mission.TABLE_NAME;

public class MissionRepository extends Repository<Mission, Integer> {

    public MissionRepository(Context context) {
        super(context);
    }

    @Override
    protected String tableName() {
        return TABLE_NAME;
    }

    @Override
    protected String primaryKeyColumnName() {
        return COLUMN_ID;
    }

    @Override
    protected AndroidContentMap<Mission> map() {
        return new MissionMap();
    }

}

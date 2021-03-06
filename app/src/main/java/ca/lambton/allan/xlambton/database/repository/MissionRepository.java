package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;

import java.util.List;

import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.map.MissionMap;
import ca.lambton.allan.xlambton.database.model.Mission;

import static ca.lambton.allan.xlambton.database.model.Mission.COLUMN_AGENT_ID;
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


    /**
     * get data by agent id
     *
     * @param agentId ID
     * @return Mission list
     */
    public List<Mission> getAll(Integer agentId) {
        return get("where " + COLUMN_AGENT_ID + " = " + agentId + " order by " + COLUMN_ID + " DESC");
    }


}

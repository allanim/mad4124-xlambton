package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;

import ca.lambton.allan.xlambton.database.map.AgentMap;
import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.model.Agent;

import static ca.lambton.allan.xlambton.database.model.Agent.COLUMN_ID;
import static ca.lambton.allan.xlambton.database.model.Agent.TABLE_NAME;

public class AgentRepository extends Repository<Agent, Integer> {

    public AgentRepository(Context context) {
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
    protected AndroidContentMap<Agent> map() {
        return new AgentMap();
    }

}

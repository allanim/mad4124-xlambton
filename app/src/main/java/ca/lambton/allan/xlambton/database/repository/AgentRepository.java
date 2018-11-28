package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;

import java.util.List;

import ca.lambton.allan.xlambton.database.map.AgentMap;
import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.model.Agent;

import static ca.lambton.allan.xlambton.database.model.Agent.COLUMN_ID;
import static ca.lambton.allan.xlambton.database.model.Agent.COLUMN_NAME;
import static ca.lambton.allan.xlambton.database.model.Agent.COLUMN_PASSWORD;
import static ca.lambton.allan.xlambton.database.model.Agent.COLUMN_USERNAME;
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

    /**
     * Check Login
     *
     * @param username username
     * @param password password
     * @return If collect information, To return agent
     */
    public Agent login(String username, String password) {
        String sql = "where " + COLUMN_USERNAME + " = " + wrap(username)
                + " and " + COLUMN_PASSWORD + " = " + wrap(password);
        List<Agent> entities = get(sql);
        return entities.size() > 0 ? entities.get(0) : null;
    }

    /**
     * Check exist email
     *
     * @param username username
     * @return true or false
     */
    public boolean existUsername(String username) {
        String sql = "where " + COLUMN_USERNAME + " = " + wrap(username);
        List<Agent> entities = get(sql);
        return entities.size() > 0;
    }

    /**
     * Search Name
     *
     * @param keyword keyword
     * @return Agent list
     */
    public List<Agent> search(String keyword) {
        List<Agent> entities;
        if (keyword != null) {
            String sql = "where " + COLUMN_NAME + " like " + wrap("%" + keyword + "%");
            entities = get(sql);
        } else {
            entities = getAll();
        }
        return entities;
    }

    /**
     * count
     */
    public int searchCount(String keyword) {
        return search(keyword).size();
    }
}

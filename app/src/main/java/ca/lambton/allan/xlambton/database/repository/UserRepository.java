package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;

import java.util.List;

import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.map.UserMap;
import ca.lambton.allan.xlambton.database.model.User;

import static ca.lambton.allan.xlambton.database.model.User.COLUMN_ID;
import static ca.lambton.allan.xlambton.database.model.User.COLUMN_NAME;
import static ca.lambton.allan.xlambton.database.model.User.COLUMN_PASSWORD;
import static ca.lambton.allan.xlambton.database.model.User.TABLE_NAME;

public class UserRepository extends Repository<User, Integer> {

    public UserRepository(Context context) {
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
    protected AndroidContentMap<User> map() {
        return new UserMap();
    }


    /**
     * Check Login
     *
     * @param email    email
     * @param password password
     * @return If collect information, To return user
     */
    public User login(String email, String password) {
        String sql = "where " + COLUMN_NAME + " = " + wrap(email)
                + " and " + COLUMN_PASSWORD + " = " + wrap(password);
        List<User> entities = get(sql);
        return entities.size() > 0 ? entities.get(0) : null;
    }

    /**
     * Check exist email
     *
     * @param email email
     * @return true or false
     */
    public boolean existEmail(String email) {
        String sql = "where " + COLUMN_NAME + " = " + wrap(email);
        List<User> entities = get(sql);
        return entities.size() > 0;
    }
}

package ca.lambton.allan.xlambton.database.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.lambton.allan.xlambton.database.DatabaseHelper;
import ca.lambton.allan.xlambton.database.map.AndroidContentMap;
import ca.lambton.allan.xlambton.database.model.SingleIdEntity;


/**
 * Database Repository
 *
 * @param <E>  Entity Object
 * @param <ID> Entity ID type
 * @author Allan Im
 */
public abstract class Repository<E extends SingleIdEntity, ID> {

    private DatabaseHelper databaseHelper;
    private String tableName;
    private String primaryKeyColumnName;
    private AndroidContentMap<E> map;

    public Repository(Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.tableName = tableName();
        this.primaryKeyColumnName = primaryKeyColumnName();
        this.map = map();
    }

    /**
     * Gets Table name
     *
     * @return The table name
     */
    protected abstract String tableName();

    /**
     * Gets Primary key column name
     *
     * @return The column name
     */
    protected abstract String primaryKeyColumnName();

    /**
     * Gets mapping logical
     *
     * @return map
     */
    protected abstract AndroidContentMap<E> map();

    /**
     * Insert data
     *
     * @param entity Entity Object
     * @return ROW ID
     */
    public long insert(E entity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(tableName, null, map.toContentValues(entity));
        db.close();
        return rowId;
    }

    /**
     * Update data
     *
     * @param entity Entity Object
     * @return update count, 1 is success, -1 is error, 0 is no update
     */
    public int update(E entity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = db.update(tableName, map.toContentValues(entity)
                , primaryKeyColumnName + " = ?"
                , new String[]{String.valueOf(entity.getId())});
        db.close();
        return count;
    }

    /**
     * Delete data
     *
     * @param entity Entity Object
     * @return delete count, 1 is success, -1 is error, 0 is no delete
     */
    public int delete(E entity) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = db.delete(tableName, primaryKeyColumnName + " = ?"
                , new String[]{String.valueOf(entity.getId())});
        db.close();
        return count;
    }

    /**
     * Delete data
     *
     * @param id Entity ID
     * @return delete count, 1 is success, -1 is error, 0 is no delete
     */
    public int delete(ID id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = db.delete(tableName, primaryKeyColumnName + " = ?"
                , new String[]{String.valueOf(id)});
        db.close();
        return count;
    }

    /**
     * Gets all data count
     *
     * @return count
     */
    public int getCount() {
        String countQuery = "SELECT  * FROM " + tableName;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    /**
     * Select data
     *
     * @param id Entity ID
     * @return Entity Object
     */
    public E getOne(ID id) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " WHERE id = ?"
                , new String[]{String.valueOf(id)});

        E entity = null;
        if (cursor.moveToFirst()) {
            entity = map.toEntity(cursor);
        }
        cursor.close();

        return entity;
    }

    /**
     * Gets All data
     *
     * @return List of Entity
     */
    public List<E> getAll() {
        return get("");
    }

    /**
     * Gets data by condition
     *
     * @param condition 'where', 'order by', 'group by'...
     * @return List of Entity
     */
    public List<E> get(String condition) {
        condition = (condition == null ? "" : condition);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName + " " + condition, null);

        List<E> entities = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                E entity = map.toEntity(cursor);
                entities.add(entity);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return entities;
    }

    /**
     * Drop table data
     *
     * @return delete count
     */
    public int deleteAll() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int count = db.delete(tableName, null, null);
        db.close();
        return count;
    }

    /**
     * Wrapping String column data
     *
     * @param data data
     * @return Wrapping quot
     */
    public String wrap(String data) {
        return "\"" + data + "\"";
    }


}

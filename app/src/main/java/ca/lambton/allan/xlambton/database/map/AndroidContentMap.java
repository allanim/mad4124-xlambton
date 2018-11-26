package ca.lambton.allan.xlambton.database.map;

import android.content.ContentValues;
import android.database.Cursor;

import ca.lambton.allan.xlambton.database.model.SingleIdEntity;

/**
 * Mapping Android Content and Entity
 *
 * @author Allan Im
 */
public interface AndroidContentMap<E extends SingleIdEntity> {

    /**
     * Entity to ContentValues
     *
     * @param entity entity
     * @return ContentValues
     */
    ContentValues toContentValues(final E entity);

    /**
     * Cursor to Entity
     *
     * @param cursor cursor
     * @return Entity
     */
    E toEntity(final Cursor cursor);
}

package ca.lambton.allan.xlambton.database.model;

import java.io.Serializable;

/**
 * Single Id
 *
 * @author Allan Im
 */
public interface SingleIdEntity<ID> extends Serializable {

    ID getId();
}

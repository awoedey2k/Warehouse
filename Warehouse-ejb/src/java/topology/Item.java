/*
 * Actual item implementation
 */
package topology;

import java.util.Date;

/**
 *
 * @author Martin Lofaj
 * TODO: entire Item impl.
 */
public class Item implements IItem {

    @Override
    public int getAmount() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getExpiration() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date getStorageTime() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Position getPosition() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
/*
 * Impl. of Shelf, the basic storage unit.
 * This is the part of the task coordinator pattern. 
 * Taking the place of task coorinator.
 */

package topology.resource.management;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import persistence.Database;

/**
 * Implementácia úložného objektu polička.
 * @author Martin Lofaj
 */
public class Shelf implements IShelf {

    private List<IItem> items;
    private int id;
    private int capacity;
    private int usedSpace;
    private Database db;
    private IItem preparedItem=null;
        
    public static final int DEFAULT_CAPACITY = 20;

    /**
     * Vytvorí poličku so zadaným identifikátorom. Ak polička už existuje
     * jej obsah sa načíta z databázy. Nastavia sa všetky potrebné členské premenné
     * triedy a aktualizuje premenná obsahujúúca informaciu o použitom mieste.
     * Veľkosť poličky je predvolená.
     * @param id identifikátor poličky.
     */
    public Shelf(int id) {
        this.id = id;
        this.capacity = DEFAULT_CAPACITY;
        try {
            db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            List<IItem> loadedItems = db.getShelf(id);
            if(loadedItems.size() > 0) {
                items = new ArrayList<IItem>(loadedItems);
            } else {
                items = new ArrayList<IItem>(capacity);
            }
        } catch (NamingException ex) {
            Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateUsedSpace();
    }
    /**
     * Vytvorí poličku so zadaným identifikátorom. Ak polička už existuje
     * jej obsah sa načíta z databázy. Nastavia sa všetky potrebné členské premenné
     * triedy a aktualizuje premenná obsahujúúca informaciu o použitom mieste.
     * Veľkosť poličky je nastavená na základe parametra.
     * @param id identfikátor poličky.
     * @param capacity maximálna kapacita poličky.
     */
    public Shelf(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        try {
            db = (Database) new InitialContext().lookup("java:global/Warehouse/Warehouse-ejb/Database");
            List<IItem> loadedItems = db.getShelf(id);
            if(loadedItems.size() > 0) {
                items = new ArrayList<IItem>(loadedItems);
            } else {
                items = new ArrayList<IItem>(capacity);
            }
        } catch (NamingException ex) {
            Logger.getLogger(Shelf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        updateUsedSpace();
    }
            
    @Override
    public List<IItem> getItems() {
        return items;
    }

    @Override
    public int getCapacity() {
        return capacity;
    }

    @Override
    public int getItemCount(String type) {
        int counter = 0;
        for(IItem it : items) {
            if(it.getType().equals(type)) {
                counter += it.getAmount();
            }
        }
        return counter;
    }

    @Override
    public int getFreeSpace() {
        return capacity - usedSpace;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public void insertItem(IItem item) {
            items.add(item);
            System.out.println("[insert] amount je " + item.getAmount());
            db.updateShelf(items, id);
            updateUsedSpace();
    }

       
    @Override
    public IItem removeItem(IItem item) {
        if(!items.isEmpty()) {
            if(!items.remove(item)) {
                item = null;
            }
            db.updateShelf(items, id);
        }
        updateUsedSpace();
        
        return item;
    }
    
    @Override
    public List<IItem> remove() {
        List<IItem> retItems = items;
        db.removeShelf(id);
        items = null;
        id = -1;
        capacity = -1;
        
        return retItems;
    }
    
    /**
     * Vypočíta použité miesto na poličke.
     */
    private void updateUsedSpace() {
        for(IItem item : items) {
            usedSpace += item.getAmount();
        }
    }

    @Override
    public void prepare(IItem item) throws TaskFailureException {
        System.out.println("Preparing........ "+item.getAmount()+"");
        if(getFreeSpace() >= item.getAmount()) {
            preparedItem = item;
            usedSpace += item.getAmount();
            System.out.println("[shelf prepare] amount " + preparedItem.getAmount());
        } else {
            throw new TaskFailureException(); 
        }
    }

    @Override
    public void commit() {
        insertItem(preparedItem);
    }

    @Override
    public void abort() {
        usedSpace -= preparedItem.getAmount();
        preparedItem = null;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;

/**
 *
 * @author Mao
 */
public class Aisle extends AbstractComponent implements IStorageComponent {
    private String code;
    private int capacity;
    private List<Rack> racks;
    private Boolean isReady;
   
    public Aisle() {
    }
    
    public Aisle(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
        isReady = false;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getCode() {
        return code;
    }

    public List<Rack> getRacks() throws Exception {
        if (isReady) {
            return racks;
        } else {
            throw new Exception("Aisle is not inicialized!");
        }        
    }
    
    @Override
    public void init(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        racks = new ArrayList();
        isReady = true;
    }

    @Override
    public void suspend() {
        isReady = false;
    }

    @Override
    public void resume() {
        isReady = true;
    }

    @Override
    public String info() {
        return "This is an Aisle for Racks.\nCode: " + code + "\n" + "Capacity: " + capacity;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public int addComponent(Object object) {
       if(racks.size() < capacity) {
            racks.add((Rack)object);  
            return 1;
        } else return 0;         
    }

    @Override
    public int removeComponent(Rack rack) {
        racks.remove(rack);
        return 1;
    }
}

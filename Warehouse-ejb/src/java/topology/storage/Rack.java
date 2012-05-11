/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package topology.storage;

import java.util.ArrayList;
import java.util.List;
import topology.configuration.AbstractComponent;
import topology.resource.management.Shelf;

/**
 *
 * @author Mao
 */
public class Rack extends AbstractComponent {
    private String code;
    private int capacity;
    private List<Shelf> shelfs;
    private Boolean isReady;

    public Rack() {
    }
    
    public Rack(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        shelfs = new ArrayList();
        isReady = false;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public String getCode() {
        return code;
    }

    public List<Shelf> getShelfs() throws Exception {
        if (isReady) {
            return shelfs;
        } else {
            throw new Exception("Rack is not inicialized!");
        }
    }

    @Override
    public void init(String code, int capacity) {
        this.code = code;
        this.capacity = capacity;
        shelfs = new ArrayList();
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
        return "This is a Rack for Shelfs.\nCode: " + code + "\n" + "Capacity: " + capacity;
    }
}

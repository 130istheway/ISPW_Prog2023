package model.dao.login;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;

public class OrderedProperties extends Properties {
    private final Vector<Object> propertyOrder = new Vector<>();

    @Override
    public synchronized Object put(Object key, Object value) {
        if (!propertyOrder.contains(key)) {
            propertyOrder.add(key);
        }
        return super.put(key, value);
    }

    @Override
    public synchronized Object remove(Object key) {
        propertyOrder.remove(key);
        return super.remove(key);
    }

    @Override
    public synchronized Enumeration<Object> propertyNames() {
        return propertyOrder.elements();
    }
}

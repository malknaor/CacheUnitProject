package com.hit.dao;

import com.hit.dm.DataModel;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @param <T>
 */
public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {
    private final String filePath;
    private Map<Long, DataModel<T>> daoMap;
    private boolean toInitialize;
    private int capacity;

    /**
     * @param filePath - file path
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        this.daoMap = new HashMap();
        this.toInitialize = true;
    }

    public DaoFileImpl(String filePath, int capacity) {
        this.filePath = filePath;
        this.daoMap = new HashMap();
        this.capacity = capacity;
    }

    @Override
    public void save(DataModel<T> entity) {
        try {
            readMapFromFile();
            if (entity != null) {
                this.daoMap.put(entity.getDataModelId(), entity);
                writeMapToFile();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DataModel<T> entity) throws IllegalArgumentException {
        if (entity == null) {
            throw new IllegalArgumentException();
        }

        try {
            readMapFromFile();
            this.daoMap.remove(entity.getDataModelId(), entity);
            writeMapToFile();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DataModel<T> find(Long id) throws IllegalArgumentException {
        DataModel<T> retValue = null;

        if (id == null) {
            throw new IllegalArgumentException("Illegal argument.");
        }

        try {
            readMapFromFile();
            retValue = this.daoMap.get(id);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return retValue;
    }

    /**
     * Write the current map to the datasource file
     * @throws IOException -
     */
    private void writeMapToFile() throws IOException {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(this.filePath, false))) {
            if (this.toInitialize) {
                this.toInitialize = false;
            }

            outputStream.writeObject(this.daoMap);
        }
    }

    /**
     * Read the map from the datasource file
     * @throws IOException -
     * @throws ClassNotFoundException -
     */
    private void readMapFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.filePath))) {
            if (this.toInitialize) {
                synchronized (this) {
                    initializeDAO(); // used to initialize the dao if needed
                }
                writeMapToFile();
            }

            this.daoMap = (HashMap<Long, DataModel<T>>) inputStream.readObject();
        }
    }

    /**
     * Used to Initialize the dataresource file  and put values when needed.
     * This is not necessary
     */
    private void initializeDAO(){
        for (int i = 0; i < 1000; i++){
            this.daoMap.put((long)i, new DataModel<T>((long)i, (T) ("#" + i + "#")));
        }
    }

    /**
     * Returns a string representation of the object. In general, the
     * {@code toString} method returns a string that
     * "textually represents" this object. The result should
     * be a concise but informative representation that is easy for a
     * person to read.
     * It is recommended that all subclasses override this method.
     * <p>
     * The {@code toString} method for class {@code Object}
     * returns a string consisting of the name of the class of which the
     * object is an instance, the at-sign character `{@code @}', and
     * the unsigned hexadecimal representation of the hash code of the
     * object. In other words, this method returns a string equal to the
     * value of:
     * <blockquote>
     * <pre>
     * getClass().getName() + '@' + Integer.toHexString(hashCode())
     * </pre></blockquote>
     *
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        String tostring = "";

        for (DataModel<T> dm: this.daoMap.values()) {
            tostring += dm.toString() + "\n";
        }

        return tostring;
    }
}
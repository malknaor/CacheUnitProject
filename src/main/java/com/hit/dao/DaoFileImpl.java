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

    public DaoFileImpl(String filePath, int capacity){
        this.filePath = filePath;
        this.capacity = capacity;
    }

    @Override
    public void save(DataModel<T> entity) {
        try {
            if (this.toInitialize) {
                writeMapToFile();
            }

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
            if (this.toInitialize) {
                writeMapToFile();
            }

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

        if (id == null){
            throw new IllegalArgumentException("");
        }

        try {
            if (this.toInitialize) {
                writeMapToFile();
            }

            readMapFromFile();
            retValue = this.daoMap.get(id);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return retValue;
    }


    /**
     * @throws IOException
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
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readMapFromFile() throws IOException, ClassNotFoundException {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(this.filePath))) {
            this.daoMap = (HashMap<Long, DataModel<T>>) inputStream.readObject();
        }
    }
}
package com.hit.dao;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

/**
 *
 * @param <T>
 */
public class DaoFileImpl<T> implements IDao<Long, T> {
    private String filePath;
    private BufferedReader reader;
    private OutputStream outStream;
    private Boolean hasInitialized;

    /**
     * C'tor
     * @param filePath - file path
     */
    public DaoFileImpl(String filePath) {
        this.filePath = filePath;
        this.hasInitialized = false;
    }

    @Override
    public void save(T entity) {
        if (!hasInitialized){
            initializeIOStream();
        }


    }

    @Override
    public void delete(T entity) throws IllegalArgumentException {
        if (!hasInitialized){
            initializeIOStream();
        }


    }

    @Override
    public T find(Long id) throws IllegalArgumentException {
        if (!hasInitialized){
            initializeIOStream();
        }



        return null;
    }

    /**
     * Initialize I/O stream
     */
    private void initializeIOStream() {
        try {
            this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.filePath)));
            this.outStream = new FileOutputStream(this.filePath);
        }
        catch (FileNotFoundException ex){
            // Implement later + Ask nissim
        }
    }
}
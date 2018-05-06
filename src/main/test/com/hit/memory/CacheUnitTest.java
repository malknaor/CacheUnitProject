package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import org.junit.Test;

import static org.junit.Assert.*;

public class CacheUnitTest {
    private String filePath;
    private IAlgoCache algo;
    private IDao dao;

    public CacheUnitTest(String filePath) {
        this.filePath = filePath;
    }

    @Test
    public void getDataModels() {

    }
}
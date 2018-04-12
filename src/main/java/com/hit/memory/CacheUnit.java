package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import java.io.IOException;
import java.io.Serializable;

/**
 *
 * @param <T>
 */
public class CacheUnit<T> {
    private IAlgoCache algo;
    private IDao dao;

    /**
     *
     * @param algo -
     * @param dao -
     */
    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Serializable, DataModel<T>> dao) {
        this.algo = algo;
        this.dao = dao;
    }

    /**
     *
     * @param ids -
     * @return -
     * @throws ClassNotFoundException -
     * @throws IOException -
     */
    public DataModel<T>[] getDataModels(Long[] ids) throws ClassNotFoundException, IOException {
        DataModel<T>[] dataModels;

        for (Long id : ids) {
            if ()
        }
    }
}

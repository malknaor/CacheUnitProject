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
    private IAlgoCache<Long, DataModel<T>> algo;
    private IDao<Serializable, DataModel<T>> dao;

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
     * @param ids - List
     * @return - DataMadel[]
     * @throws ClassNotFoundException -
     * @throws IOException -
     */
    public DataModel<T>[] getDataModels(Long[] ids) throws ClassNotFoundException, IOException {
        DataModel<T>[] dataModelArr = new DataModel[ids.length];
        int i = 0;

        DataModel<T> value;

        for (Long id: ids) {
            value = algo.putElement(id, null);

            //  if - value != null => cache is full => retrieve the DM with DAO and put the DM to ALGO.
            //  else - value == null => cache is not full OR the item exist in ALGO =>
            //  check if the ID exist add to the ARR.
            //  else not the cache not full and you need to retrieve the DM with DAO and put in ALGO.

            if (value == null) { // The Cache is not full OR the item exist in ALGO
                value = algo.getElement(id);

                if (value != null) {
                    dataModelArr[i++] = value;
                } else {
                    value = doIfCacheIsFull(id);
                    dataModelArr[i++] = value;
                }
            } else { // The Cache is full
                value = doIfCacheIsFull(id);
                dataModelArr[i++] = value;
            }
        }

        return dataModelArr;
    }

    private DataModel<T> doIfCacheIsFull(Long id){
        DataModel<T> value = dao.find(id);
        DataModel<T> tempValue = algo.putElement(id, value);

        if (tempValue != null) {
            dao.save(tempValue);
        }

        return value;
    }
}
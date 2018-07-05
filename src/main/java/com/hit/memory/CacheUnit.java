package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;

/**
 * @param <T>
 */
public class CacheUnit<T> {
    private IAlgoCache<Long, DataModel<T>> algo;
    private IDao<Long, DataModel<T>> dao;
    private int countSwaps;

    /**
     * @param algo - Cache paging algorithm and manage them
     * @param dao  - Operate as a hard drive
     */
    public CacheUnit(IAlgoCache<Long, DataModel<T>> algo, IDao<Long, DataModel<T>> dao) {
        this.algo = algo;
        this.dao = dao;
        countSwaps = 0;
    }

    /**
     * @param ids - ids to identify requested values
     * @return DataMadel<T>[] - requested values
     */
    public DataModel<T>[] getDataModels(Long[] ids) {
        DataModel<T>[] dataModelArr = new DataModel[ids.length];
        int i = 0;

        DataModel<T> value;

        for (Long id : ids) {
            value = algo.putElement(id, null);
            if (value == null) { //  if - value == null => cache is not full OR the item exist in ALGO =>
                value = algo.getElement(id); //  check if the ID exist add to the ARR.

                if (value != null) { // the ID exist in cache
                    dataModelArr[i++] = new DataModel<T>(value.getDataModelId(), value.getContent());
                } else { //  else the cache not full and you need to retrieve the DM with DAO and put in ALGO.

                    value = retrieveDMFromDAO(id);
                    if (value != null) {
                        dataModelArr[i++] = new DataModel<T>(value.getDataModelId(), value.getContent());
                    }
                }
            } else { //  else - value != null => cache is full => retrieve the DM with DAO and put the DM to ALGO.
                value = retrieveDMFromDAO(id);
                dataModelArr[i++] = new DataModel<T>(value.getDataModelId(), value.getContent());
            }
        }

        if (dataModelArr[0] == null) {
            dataModelArr = null;
        }

        return (DataModel<T>[])dataModelArr;
    }

    /**
     * this will retrieve the DataModel from the DAO and store it in ALGO if needed
     * @param id
     * @return
     */
    private DataModel<T> retrieveDMFromDAO(Long id) {
        DataModel<T> value = dao.find(id);
        DataModel<T> tempValue = null;

        // store the pair (id, value) in cache if necessary
        if (value != null) {
            dao.delete(value);
            tempValue = algo.putElement(id, value);

            // store the DataModel (value) in the DAO in case ALGO is full
            if (tempValue != null) {
                countSwaps++;
                dao.save(tempValue);
            }
        }

        return value;
    }

    public int getCountSwaps() {
        return countSwaps;
    }

    public void saveAllDMsInDao(){
        DataModel<T> dm;

        for (int i = 111111; i < 111121; i++) {
            dm = algo.putElement((long)i, new DataModel((long)i, "empty"));
            if (dm != null) {
                dao.save(dm);
            }
        }
    }
}
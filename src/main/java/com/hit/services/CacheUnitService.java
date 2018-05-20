package com.hit.services;

import com.hit.algorithm.SecondChanceAlgoImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import java.util.ArrayList;
import java.util.List;

public class CacheUnitService<T> {
    private CacheUnit<T> cacheUnit;

    public CacheUnitService() {
        this.cacheUnit = new CacheUnit<T>(new SecondChanceAlgoImpl<Long, DataModel<T>>(10),
                new DaoFileImpl<T>("src/main/resources/datasource.txt"));
    }

    public boolean delete(DataModel<T>[] dataModels) {
        DataModel<T>[] dms = getFilteredDataModels(dataModels);

        synchronized (this) {
            for (DataModel<T> dm : dms) {
                dm = null;
            }
        }

        return dataModels.length == dms.length;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return getFilteredDataModels(dataModels);
    }

    public boolean update(DataModel<T>[] dataModels) {
        DataModel<T>[] dms = getFilteredDataModels(dataModels);

        synchronized (this) {
            for (DataModel<T> dm : dms) {
                for (DataModel<T> dam : dataModels) {
                    if (dm.getDataModelId().equals(dam.getDataModelId())) {
                        dm.setContent(dam.getContent());
                        break;
                    }
                }
            }
        }

        return dataModels.length == dms.length;
    }

    private synchronized DataModel<T>[] getFilteredDataModels(DataModel<T>[] dataModels) {
        List<Long> ids = new ArrayList<Long>();

        for (DataModel<T> dm : dataModels) {
            ids.add(dm.getDataModelId());
        }

        return this.cacheUnit.getDataModels((Long[]) ids.toArray());
    }
}
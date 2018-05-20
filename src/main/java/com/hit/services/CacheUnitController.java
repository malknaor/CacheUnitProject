package com.hit.services;

import com.hit.dm.DataModel;

public class CacheUnitController<T> {
    private CacheUnitService<T> cacheUnitService;

    public CacheUnitController() {
        this.cacheUnitService = new CacheUnitService<T>();
    }

    public boolean delete(DataModel<T>[] dataModels) {
        return this.cacheUnitService.delete(dataModels);
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        return this.cacheUnitService.get(dataModels);
    }

    public boolean update(DataModel<T>[] dataModels) {
        return this.cacheUnitService.update(dataModels);
    }
}

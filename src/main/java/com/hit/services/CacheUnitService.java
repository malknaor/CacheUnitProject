package com.hit.services;

import com.hit.algorithm.SecondChanceAlgoImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dm.DataModel;
import com.hit.memory.CacheUnit;
import com.hit.server.HandleRequest;

import java.util.ArrayList;
import java.util.List;

public class CacheUnitService<T> {
    private CacheUnit<T> cacheUnit;
    private final int CACHE_CAPACITY = 10;
    private String algorithmName;
    private int countRequests;
    private List<String> statisticsLines;
    private String statistics;

    public CacheUnitService() {
        cacheUnit = new CacheUnit<T>(new SecondChanceAlgoImpl<Long, DataModel<T>>(CACHE_CAPACITY),
                new DaoFileImpl<T>("src/main/resources/datasource.txt"));
        algorithmName = "Second Chance Algorithm";
        countRequests = 0;
        statisticsLines = new ArrayList<>();
        initStatisticsLines();
    }

    private void initStatisticsLines() {
        statisticsLines.add("Capacity: " + CACHE_CAPACITY + ".\n");
        statisticsLines.add("Algorithm: " + algorithmName + ".\n");
        statisticsLines.add("Total number of requests: ");
        statisticsLines.add("Total number of DataModels (GET|DELETE|UPDATE) requests: ");
        statisticsLines.add("Total number of DataModels (from Cache to Disk) swaps: ");
    }

    public boolean delete(DataModel<T>[] dataModels) {
        boolean deleteSucceed = true;
        DataModel<T>[] dms = getFilteredDataModels(dataModels);

        countRequests++;
        try {
            if (dms != null) {
                for (DataModel<T> dm : dms) {
                    dm = null;
                }
            } else {
                deleteSucceed = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            deleteSucceed = false;
        }

        return deleteSucceed;
    }

    public DataModel<T>[] get(DataModel<T>[] dataModels) {
        countRequests++;
        return getFilteredDataModels(dataModels);
    }

    public boolean update(DataModel<T>[] dataModels) {
        boolean updateSucceed = true;
        DataModel<T>[] dms = getFilteredDataModels(dataModels);

        countRequests++;
        try {
            if (dms != null) {
                for (DataModel<T> dm : dms) {
                    for (DataModel<T> dam : dataModels) {
                        if (dm.getDataModelId().equals(dam.getDataModelId())) {
                            dm.setContent(dam.getContent());

                            break;
                        }
                    }
                }
            } else {
                updateSucceed = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateSucceed = false;
        }

        return updateSucceed;
    }

    private DataModel<T>[] getFilteredDataModels(DataModel<T>[] dataModels) {
        List<Long> ids = new ArrayList<Long>();

        for (DataModel<T> dm : dataModels) {
            ids.add(dm.getDataModelId());
        }

        Long[] arr = new Long[ids.size()];
        ids.toArray(arr);

        DataModel<T>[] dmArr = this.cacheUnit.getDataModels(arr);
        return dmArr;
    }

    public String getCacheUnitStatistics() {
        String[] lines = new String[statisticsLines.size()];
        statisticsLines.toArray(lines);

        statistics = lines[0] + lines[1] + lines[2] + HandleRequest.countRequests + ".\n"
                + lines[3] + countRequests + ".\n" + lines[4] + cacheUnit.getCountSwaps() + ".\n";

        return statistics;
    }
}
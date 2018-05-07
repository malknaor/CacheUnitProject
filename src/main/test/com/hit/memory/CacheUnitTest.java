package com.hit.memory;

import com.hit.algorithm.IAlgoCache;
import com.hit.algorithm.LRUAlgoCacheImpl;
import com.hit.dao.DaoFileImpl;
import com.hit.dao.IDao;
import com.hit.dm.DataModel;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;

public class CacheUnitTest {
    @Test
    public void getDataModels() {

        /*IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<>(5);
        IDao<Long, DataModel<String>> dao = new DaoFileImpl<>("C:\\JAVA TEST\\TestFile.txt");

        DataModel<String> dm1 = new DataModel<String>((long)1, "One");
        DataModel<String> dm2 = new DataModel<String>((long)2, "Two");
        DataModel<String> dm3 = new DataModel<String>((long)3, "Three");
        DataModel<String> dm4 = new DataModel<String>((long)4, "Four");
        DataModel<String> dm5 = new DataModel<String>((long)5, "Five");
        DataModel<String> dm6 = new DataModel<String>((long)6, "Six");
        DataModel<String> dm7 = new DataModel<String>((long)7, "Seven");

        DataModel<String>[] dataModels = new DataModel[] {dm3, dm5, dm1, dm2, dm4, dm7, dm6};

        dao.save(dm1);
        dao.save(dm2);
        dao.save(dm3);
        dao.save(dm4);
        dao.save(dm5);
        dao.save(dm6);
        dao.save(dm7);

        CacheUnit<String> cacheUnit = new CacheUnit<>(algo, dao);
        DataModel<String>[] retDataModels = null;

        Long[] ids = {dm3.getDataModelId(), dm5.getDataModelId(), dm1.getDataModelId(),
                dm2.getDataModelId(), dm4.getDataModelId(), dm7.getDataModelId(), dm6.getDataModelId()*//*, (long)9*//*};
        try {
            retDataModels = cacheUnit.getDataModels(ids);
        } catch (ClassNotFoundException | IOException e) {
            System.out.println(" ");
        }

        if (retDataModels != null) {
            for (int i = 0; i < 7; i++) {
                assertEquals(dataModels[i], retDataModels[i]);
            }
        }*/

        IAlgoCache<Long, DataModel<String>> algo = new LRUAlgoCacheImpl<>(5);
        IDao<Long, DataModel<String>> dao = new DaoFileImpl<>("C:\\JAVA TEST\\TestFile.txt");
        CacheUnit<String> cacheUnit = new CacheUnit<>(algo, dao);

        Long[] ids = new Long[] {0L, 1L, 2L, 3L, 4L, 5L};
        DataModel<String>[] dataModels = new DataModel[6];

        for (int i = 0; i < dataModels.length; i++) {
            dataModels[i] = new DataModel<>(ids[i], ids.toString());
        }

        for (DataModel<String> dataModel : dataModels) {
            dao.save(dataModel);
        }

        DataModel<String>[] retDataModels = null;
        try {
            retDataModels = cacheUnit.getDataModels(ids);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < dataModels.length; i++) {
            assertEquals(dataModels[i], retDataModels[i]);
        }
    }
}
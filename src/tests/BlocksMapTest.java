package tests;

import model.BlocksMap.BlocksMap;
import model.blocks.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

public class BlocksMapTest {

    @Test
    public void add() {
        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);

        BlocksMap actual = new BlocksMap();

        actual.add(bm.getId(), bm);
        actual.add(cm.getId(), cm);
        actual.add(dm.getId(), dm);
        actual.add(sm.getId(), sm);
        actual.add(em.getId(), em);

        HashMap<Integer, BaseBlockModel> expected = new HashMap<>();
        expected.put(bm.getId(), bm);
        expected.put(cm.getId(), cm);
        expected.put(dm.getId(), dm);
        expected.put(sm.getId(), sm);
        expected.put(em.getId(), em);
        Assert.assertEquals(expected, actual.getHashMap());
    }

    @Test
    public void getClassByID() {
        ConditionModel expected = new ConditionModel(0);
        BlockModel bm = new BlockModel(1);
        BlockModel bm1 = new BlockModel(2);
        expected.setYesID(bm.getId());
        expected.setNoID(bm1.getId());
        BlocksMap tmp = new BlocksMap();
        tmp.add(expected.getId(), expected);

        ConditionModel actual = (ConditionModel) tmp.getClassByID(expected.getId());
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void delete() {
        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);
        int actual = 0;
        BlocksMap blocksMap = new BlocksMap();

        blocksMap.add(bm.getId(), bm);
        blocksMap.add(cm.getId(), cm);
        blocksMap.add(dm.getId(), dm);
        blocksMap.add(sm.getId(), sm);
        blocksMap.add(em.getId(), em);

        blocksMap.delete(bm);
        blocksMap.delete(sm);
        for (int i = 0; i < blocksMap.returnBlocks().size(); i++)
            if (blocksMap.returnBlocks().get(i).getId() != -1)
                actual += 1;

        Assert.assertEquals(3, actual); //3 объекта осталось
    }

    @Test
    public void returnBlocks() {
        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);

        BlocksMap actual = new BlocksMap();

        actual.add(bm.getId(), bm);
        actual.add(cm.getId(), cm);
        actual.add(dm.getId(), dm);
        actual.add(sm.getId(), sm);
        actual.add(em.getId(), em);

        ArrayList<BaseBlockModel> expected = new ArrayList<>();
        expected.add(bm);
        expected.add(cm);
        expected.add(dm);
        expected.add(sm);
        expected.add(em);
        Assert.assertEquals(expected, actual.returnBlocks());
    }

    @Test
    public void returnIDs() {
        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);

        BlocksMap actual = new BlocksMap();

        actual.add(bm.getId(), bm);
        actual.add(cm.getId(), cm);
        actual.add(dm.getId(), dm);
        actual.add(sm.getId(), sm);
        actual.add(em.getId(), em);

        HashMap<Integer, BaseBlockModel> tmo = new HashMap<>();
        tmo.put(bm.getId(), bm);
        tmo.put(cm.getId(), cm);
        tmo.put(dm.getId(), dm);
        tmo.put(sm.getId(), sm);
        tmo.put(em.getId(), em);
        ArrayList<Integer> expected = new ArrayList<>(tmo.keySet());

        Assert.assertEquals(expected, actual.returnIDs());
    }

    @Test
    public void setMap() {

        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);

        HashMap<Integer, BaseBlockModel> expected = new HashMap<>();

        expected.put(bm.getId(), bm);
        expected.put(cm.getId(), cm);
        expected.put(dm.getId(), dm);
        expected.put(sm.getId(), sm);
        expected.put(em.getId(), em);

        BlocksMap actual = new BlocksMap();
        actual.setMap(expected);

        Assert.assertEquals(expected, actual.getHashMap());
    }

    @Test
    public void getMap() {
        BlockModel bm = new BlockModel(0);
        ConditionModel cm = new ConditionModel(1);
        DisplayModel dm = new DisplayModel(2);
        StartModel sm = new StartModel(3);
        EndModel em = new EndModel(4);

        BlocksMap tmp = new BlocksMap();

        tmp.add(bm.getId(), bm);
        tmp.add(cm.getId(), cm);
        tmp.add(dm.getId(), dm);
        tmp.add(sm.getId(), sm);
        tmp.add(em.getId(), em);

        HashMap<Integer, BaseBlockModel> expected = new HashMap<>();
        expected.put(bm.getId(), bm);
        expected.put(cm.getId(), cm);
        expected.put(dm.getId(), dm);
        expected.put(sm.getId(), sm);
        expected.put(em.getId(), em);

        HashMap<Integer, BaseBlockModel> actual = tmp.getHashMap();

        Assert.assertEquals(expected, actual);

    }
}
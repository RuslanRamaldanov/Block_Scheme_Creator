package model.BlocksMap;

import model.blocks.BaseBlockModel;
import model.blocks.ConditionModel;

import java.util.ArrayList;
import java.util.HashMap;

public class BlocksMap {
    private HashMap<Integer, BaseBlockModel> hashMap = new HashMap<>();

    public void add(Integer ID, BaseBlockModel baseBlockModel) {
        hashMap.put(ID, baseBlockModel);
    }

    public BaseBlockModel getClassByID(Integer ID) {
        return hashMap.getOrDefault(ID, null);
    }

    public void delete(BaseBlockModel bbm) {
        int ID = bbm.getId();
        if(hashMap != null) {
            for (Integer key : hashMap.keySet()) {
                if (hashMap.get(key).getConnections().contains(ID)) {
                    try {
                        hashMap.get(key).deleteConnection(ID);
                        if (hashMap.get(key) instanceof ConditionModel cm) {
                            if (cm.getYesId() == ID)
                                cm.setYesID(-1);
                            if (cm.getNoId() == ID)
                                cm.setNoID(-1);
                            hashMap.put(key, cm);
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        System.out.println(hashMap.get(key));
                    }
                }
            }
        }

        bbm.setId(-1);
        hashMap.remove(-1);
    }

    public ArrayList<BaseBlockModel> returnBlocks() {
        return new ArrayList<>(hashMap.values());
    }

    public ArrayList<Integer> returnIDs() {
        return new ArrayList<>(hashMap.keySet());
    }
    public void setMap(HashMap<Integer, BaseBlockModel> tmp) {hashMap = tmp;}

    public HashMap<Integer, BaseBlockModel> getHashMap() {
        return hashMap;
    }
}

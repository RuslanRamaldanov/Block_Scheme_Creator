package model.blocks;

import java.util.ArrayList;

public class ConditionModel extends BaseBlockModel {
    private int yesID = -1;
    private int noID = -1;

    public ConditionModel(int ID) {
        super(ID);
        super.setType("Condition");
        refs = null;
    }


    @Override
    public void setConnection(Integer otherID) throws RuntimeException {
        System.out.println("This is condition model!");
    }

    @Override
    public ArrayList<Integer> getConnections() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(yesID);
        arrayList.add(noID);
        return arrayList;
    }

    @Override
    public void deleteConnection(int ID) {
        if(yesID == ID)
            yesID = -1;
        if(noID == ID)
            noID = -1;
    }

    public void setYesID(int id) {
        yesID = id;
    }
    public void setNoID(int id) {
        noID = id;
    }

    public int getYesId() {
        return yesID;
    }

    public int getNoId() {
        return noID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("type:").append(this.getType()).
                append("\n").append("text:").
                append(this.getText()).
                append("\n").append("xCord:").
                append(this.getXCord()).
                append("\n").append("yCord:").
                append(this.getYCord()).
                append("\n").append("Height:").
                append(this.getH()).append("\n").
                append("Width:").
                append(this.getW()).
                append("\n").append("ID:").
                append(this.getId()).append("\n").
                append("yesID:").append(yesID).append('\n').
                append("noID:").append(noID).
                append("\n--------\n");
        return sb.toString();

    }
    @Override
    public String checkThisBlock() {
        if(yesID != -1 & noID != -1)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have YES branch or NO branch";
    }
    public boolean equals(Object o) {
        if (!this.getClass().equals(o.getClass()))
            return false;
        ConditionModel bbm = (ConditionModel) o;
        if (!this.getType().equals(bbm.getType()))
            return false;
        if (this.xCord != bbm.xCord)
            return false;
        if (this.yCord != bbm.yCord)
            return false;
        if (this.height != bbm.height)
            return false;
        if (this.width != bbm.width)
            return false;
        if (this.ID != bbm.ID)
            return false;
        if(this.yesID != bbm.yesID)
            return false;
        return this.noID == bbm.noID;
    }
}

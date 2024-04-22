package model.blocks;

public class CycleModel extends BaseBlockModel {
    public CycleModel(int ID) {
        super(ID);
        super.setType("Cycle");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() != 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections\n";
    }


}

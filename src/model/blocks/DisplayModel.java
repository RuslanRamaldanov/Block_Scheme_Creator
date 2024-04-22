package model.blocks;

public class DisplayModel extends BaseBlockModel {
    public DisplayModel(int ID) {
        super(ID);
        super.setType("Display");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() != 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections\n";
    }
}

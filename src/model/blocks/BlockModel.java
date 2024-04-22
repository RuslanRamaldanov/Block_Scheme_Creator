package model.blocks;

public class BlockModel extends BaseBlockModel {
    public BlockModel(int ID) {
        super(ID);
        super.setType("Block");
    }

    @Override
    public String checkThisBlock() {
        if(refs.size() != 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections\n";
    }
}

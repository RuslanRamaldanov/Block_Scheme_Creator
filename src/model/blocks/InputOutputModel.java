package model.blocks;

public class InputOutputModel extends BaseBlockModel {
    public InputOutputModel(int ID) {
        super(ID);
        super.setType("InputOutput");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() != 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections\n";
    }
}

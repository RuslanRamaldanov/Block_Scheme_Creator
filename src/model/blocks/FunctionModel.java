package model.blocks;

public class FunctionModel extends BaseBlockModel {
    public FunctionModel(int ID) {
        super(ID);
        super.setType("Function");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() != 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections\n";
    }
}

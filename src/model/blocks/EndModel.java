package model.blocks;

public class EndModel extends BaseBlockModel {
    public EndModel(int ID) {
        super(ID);
        super.setType("End");
        super.setText("конец");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() == 0)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - cant have connections\n";
    }
}

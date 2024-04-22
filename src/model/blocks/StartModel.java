package model.blocks;
import java.util.ArrayList;

public class StartModel extends BaseBlockModel {
    public StartModel(int ID) {
        super(ID);
        super.refs = new ArrayList<>();
        super.setType("Start");
        super.setText("начало");
    }
    @Override
    public String checkThisBlock() {
        if(refs.size() == 1)
            return getType() + " " + ID + " - checked\n";
        else
            return getType() + " " + ID + " - doesn't have enough connections or have more than one connections\n";
    }
}

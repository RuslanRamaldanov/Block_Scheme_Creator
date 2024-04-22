package model.blocks;

import java.util.ArrayList;

public class BaseBlockModel implements blocks_interface {
    protected String text;
    protected String type;
    protected int xCord;
    protected int yCord;
    protected int height;
    protected int width;
    protected int ID;
    protected ArrayList<Integer> refs;


    public BaseBlockModel(int ID) {
        xCord = 0;
        yCord = 0;
        text = " ";
        height = 50;
        width = 152;
        type = " ";
        refs = new ArrayList<>();

        setText(text);
        setCord(xCord, yCord);
        setHW(height, width);
        setType(type);
        setId(ID);


    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCord(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setHW(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public String getText() {
        if (text.endsWith("\n")) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    public int getXCord() {
        return xCord;
    }

    public int getYCord() {
        return yCord;
    }

    public String getType() {
        return type;
    }

    public int getH() {
        return height;
    }

    public int getW() {
        return width;
    }

    public void setConnection(Integer otherID) throws RuntimeException {
        refs.add(otherID);
    }

    public int getId() {
        return ID;
    }

    public void setId(int ID) {
        this.ID = ID;
    }

    public ArrayList<Integer> getConnections() {
        return refs;
    }

    public String getArray() {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        for (Integer ref : refs) {
            sb.append(ref).append(" ");
        }
        return sb.toString();
    }

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
                append("Connections:").
                append(this.getArray()).
                append("\n--------\n");
        return sb.toString();
    }

    public void deleteConnection(int ID) {
        refs.removeIf(num -> num == ID);
    }

    @Override
    public String checkThisBlock() {
        return null;
    }
    @Override
    public boolean equals(Object o) {
        if(!this.getClass().equals(o.getClass()))
            return false;
        BaseBlockModel bbm = (BaseBlockModel) o;
        if(!this.getType().equals(bbm.getType()))
            return false;
        if(this.xCord != bbm.xCord)
            return false;
        if(this.yCord != bbm.yCord)
            return false;
        if(this.height != bbm.height)
            return false;
        if(this.width != bbm.width)
            return false;
        if(this.ID != bbm.ID)
            return false;
        return this.getConnections().equals(bbm.getConnections());
    }
}

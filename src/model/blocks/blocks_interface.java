package model.blocks;

import java.util.ArrayList;

public interface blocks_interface {
    void setText(String text);

    void setCord(int xCord, int yCord);

    void setType(String type);

    void setHW(int height, int width);

    String getText();

    int getXCord();

    int getYCord();

    String getType();

    int getH();

    int getW();

    void setConnection(Integer otherID);

    int getId();

    void setId(int ID);

    ArrayList<Integer> getConnections();
    String getArray();

    @Override
    String toString();
    void deleteConnection(int ID);
    String checkThisBlock();
    @Override
    boolean equals(Object obj);
}

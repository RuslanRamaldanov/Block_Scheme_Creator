package view;

import model.BlocksMap.BlocksMap;
import model.blocks.ConditionModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.HashMap;


public class diagramPanel extends JPanel {
    private BlocksMap elements = new BlocksMap();
    private final ArrayList<itemPanel> blocks = new ArrayList<>();
    private final HashMap<Integer, itemPanel> hashBlocks = new HashMap<>();
    private itemPanel from = null, to = null;
    private itemPanel deleteFrom = null, deleteTo = null;
    private  int maxX, maxY, minX, minY;

    public diagramPanel() {
        super();
        int width = 1500;
        int height = 1500;
        maxY = -1;
        maxX = -1;
        minX = 1500;
        minY = 1500;
        setLayout(null);
        setPreferredSize(new Dimension(width, height));
        setBorder(new LineBorder(Color.gray, 1));
        setBackground(Color.white);
    }

    public void addNewItem(itemPanel item) {
        blocks.add(item);
        item.setBlocks(blocks);
        hashBlocks.put(item.returnItem().getId(), item);
        add(item);
        item.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON3) {
                    JPopupMenu popupMenu = createPopupMenu(item);
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        item.addMouseListener(new lineMouseListener());

        revalidate();
        repaint();
    }

    private JPopupMenu createPopupMenu(itemPanel item) {
        JPopupMenu popup = new JPopupMenu();
        JMenuItem removeElement = new JMenuItem("Удалить элемент");
        removeElement.setBackground(Color.RED);
        removeElement.addActionListener(e -> {
            elements.delete(item.returnItem());
            hashBlocks.remove(item.returnItem().getId());
            blocks.remove(item);
            remove(item);
            revalidate();
            repaint();
        });
        popup.add(removeElement);
        return popup;
    }

    private void draw(int fromID, int toID, Graphics2D g) {
        itemPanel from = hashBlocks.get(fromID);
        itemPanel to = hashBlocks.get(toID);
        if (from != null & to != null) {
            Point p1 = from.getLocation();
            Dimension d1 = from.getSize();
            Point p2 = to.getLocation();
            Dimension d2 = to.getSize();

            // Определение центров сторон панелей
            int p1x = p1.x + d1.width / 2;
            int p1y = p1.y + d1.height / 2;
            int p2x = p2.x + d2.width / 2;
            int p2y = p2.y + d2.height / 2;

            g.drawLine(p1x, p1y, p2x, p1y);
            g.drawLine(p2x, p1y, p2x, p2y);

            int arrowSize = 15;
            double angle = Math.atan2(p2y - p1y, p2x - p1x);
            int x3 = p2x - (int) (arrowSize * Math.cos(angle - Math.PI / 8));
            int y3 = p2y - (int) (arrowSize * Math.sin(angle - Math.PI / 8));
            int x4 = p2x - (int) (arrowSize * Math.cos(angle + Math.PI / 8));
            int y4 = p2y - (int) (arrowSize * Math.sin(angle + Math.PI / 8));

            GeneralPath arrowHead = new GeneralPath();
            arrowHead.moveTo(p2x, p2y - 20);
            arrowHead.lineTo(x3, y3 - 20);
            arrowHead.lineTo(x4, y4 - 20);
            arrowHead.closePath();
            g.fill(arrowHead);
        }
    }

    public void setBlocksMap(BlocksMap bm) {
        elements = bm;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < elements.returnBlocks().size(); i++) {
            if (elements.returnBlocks().get(i) instanceof ConditionModel) {
                if (((ConditionModel) elements.returnBlocks().get(i)).getYesId() != -1)
                    draw(elements.returnBlocks().get(i).getId(), ((ConditionModel) elements.returnBlocks().get(i)).getYesId(), g2);
                if (((ConditionModel) elements.returnBlocks().get(i)).getNoId() != -1)
                    draw(elements.returnBlocks().get(i).getId(), ((ConditionModel) elements.returnBlocks().get(i)).getNoId(), g2);
            } else {
                for (int j = 0; j < elements.returnBlocks().get(i).getConnections().size(); j++) {
                    draw(elements.returnBlocks().get(i).getId(), elements.returnBlocks().get(i).getConnections().get(j), g2);
                }
            }
        }

        for (int i = 1; i < 150; i++) {
            g.setColor(Color.lightGray);
            g.drawLine(i * 10, 0, i * 10, getHeight());
            g.drawLine(0, i * 10, getWidth(), i * 10);
        }
        g.setColor(Color.black);
    }

    private class lineMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            /* FOR CONDITION AND NOT CONDITION BLOCKS */
            if (e.getClickCount() == 2 & !e.isControlDown() & !e.isAltDown() & !e.isShiftDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.green, 1));
                if (from == null)
                    from = tmp;
                else
                    to = tmp;
                if (to != null) {
                    from.returnItem().setConnection(to.returnItem().getId());
                    from.setBorder(new EmptyBorder(1, 1, 1, 1));
                    to.setBorder(new EmptyBorder(1, 1, 1, 1));
                    repaint();
                    revalidate();
                    from = null;
                    to = null;
                }
            }
            /* DELETE FOR ALL BLOCKS*/
            if (e.isControlDown() & e.getClickCount() == 1 & !e.isAltDown() & !e.isShiftDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.red, 1));
                if (deleteFrom == null & !(tmp.returnItem() instanceof ConditionModel))
                    deleteFrom = tmp;
                else if (deleteFrom != null)
                    deleteTo = tmp;
                else {
                    tmp.setBorder(new EmptyBorder(1, 1, 1, 1));
                }
                if (deleteTo != null & deleteFrom != null) {
                    if (deleteFrom.returnItem().getConnections().contains(deleteTo.returnItem().getId())) {
                        deleteFrom.returnItem().deleteConnection(deleteTo.returnItem().getId());
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                        repaint();
                        revalidate();
                    } else {
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                    }
                }
            }
            /* DELETE YES BRANCH */
            if (e.isShiftDown() & e.getClickCount() == 1 & !e.isAltDown() & !e.isControlDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.cyan, 1));
                if (deleteFrom == null & (tmp.returnItem() instanceof ConditionModel))
                    deleteFrom = tmp;
                else if (deleteFrom != null)
                    deleteTo = tmp;
                else {
                    tmp.setBorder(new EmptyBorder(1, 1, 1, 1));
                }
                if (deleteTo != null & deleteFrom != null) {
                    if (((ConditionModel) deleteFrom.returnItem()).getYesId() == deleteTo.returnItem().getId()) {
                        ((ConditionModel) deleteFrom.returnItem()).setYesID(-1);
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                        repaint();
                        revalidate();
                    } else {
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                    }
                }
            }
            /*DELETE NO BRANCH */
            if (e.isControlDown() & e.isShiftDown() & e.getClickCount() == 1 & !e.isAltDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.magenta, 1));
                if (deleteFrom == null & (tmp.returnItem() instanceof ConditionModel))
                    deleteFrom = tmp;
                else if (deleteFrom != null)
                    deleteTo = tmp;
                else {
                    tmp.setBorder(new EmptyBorder(1, 1, 1, 1));
                }
                if (deleteTo != null & deleteFrom != null) {
                    if (((ConditionModel) deleteFrom.returnItem()).getNoId() == deleteTo.returnItem().getId()) {
                        ((ConditionModel) deleteFrom.returnItem()).setNoID(-1);
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                        repaint();
                        revalidate();
                    } else {
                        deleteFrom.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteTo.setBorder(new EmptyBorder(1, 1, 1, 1));
                        deleteFrom = null;
                        deleteTo = null;
                    }
                }
            }
            /* ONLY FOR CONDITION BLOCKS */
            if (e.getClickCount() == 1 & e.isAltDown() & !e.isControlDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.blue, 1));
                if (from == null)
                    from = tmp;
                else
                    to = tmp;
                if (to != null & from != null) {
                    ((ConditionModel) from.returnItem()).setYesID(to.returnItem().getId());
                    from.setBorder(new EmptyBorder(1, 1, 1, 1));
                    to.setBorder(new EmptyBorder(1, 1, 1, 1));
                    repaint();
                    revalidate();
                    from = null;
                    to = null;
                }
            }
            if (e.getClickCount() == 1 & e.isAltDown() & e.isControlDown()) {
                itemPanel tmp = (itemPanel) e.getComponent();
                tmp.setBorder(new LineBorder(Color.ORANGE, 1));
                if (from == null)
                    from = tmp;
                else
                    to = tmp;

                if (to != null & from != null) {
                    ((ConditionModel) from.returnItem()).setNoID(to.returnItem().getId());
                    from.setBorder(new EmptyBorder(1, 1, 1, 1));
                    to.setBorder(new EmptyBorder(1, 1, 1, 1));
                    repaint();
                    revalidate();
                    from = null;
                    to = null;
                }
            }
        }
    }

    public Dimension getPngHW() {

        if (blocks.size() != 0) {
            for(itemPanel block : blocks) {
                if(block.getX() < minX)
                    minX = block.getX();
                if(block.getY() < minY)
                    minY = block.getY();
                if(block.getX() > maxX)
                    maxX = block.getX();
                if(block.getY() > maxY)
                    maxY = block.getY();
            }
        } else {
            minX = 0;
            minY = 0;
            maxX = 0;
            maxY = 0;
        }
        System.out.println(minX + " " + minY);
        return new Dimension(maxX - minX + 200, maxY - minY + 200);
    }
    public int returnMinX() {return minX;}
    public int returnMinY() {return minY;}
}


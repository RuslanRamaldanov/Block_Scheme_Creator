package view;

import model.blocks.BaseBlockModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class itemPanel extends JPanel {
    private final BaseBlockModel item;


    private int screenX, screenY, myX, myY;
    private final int xCord;
    private final int yCord;
    private final JTextArea textArea;
    private ArrayList<itemPanel> blocks;
    private BufferedImage image;

    public itemPanel(BaseBlockModel item) {
        super();
        this.item = item;
        xCord = item.getXCord();
        yCord = item.getYCord();
        setLocation(new Point(xCord, yCord));
        setOpaque(false);
        getImage(item.getType());
        item.setHW(image.getHeight(), image.getWidth() + 20);
        setSize(item.getW(), item.getH());
        setMinimumSize(new Dimension(item.getW(), item.getH()));

        textArea = new JTextArea();
        AbstractDocument doc = (AbstractDocument) textArea.getDocument();
        doc.setDocumentFilter(new LimitedDocumentFilter(64));
        textArea.setText(item.getText());
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        if (item.getType().equals("Start") | item.getType().equals("End"))
            textArea.setEnabled(false);

        textArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                setSize(new Dimension(Math.max(getSize().width, textArea.getSize().getSize().width), Math.max(getSize().height, textArea.getSize().getSize().height + 10)));
                textArea.setBounds(item.getXCord(), item.getYCord(), getWidth() / 2, getHeight() / 2);
                item.setHW(getHeight(), getWidth());
                item.setText(textArea.getText());
                revalidate(); // Перерисовываем контейнер
                repaint();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                setSize(new Dimension(Math.max(getSize().width, textArea.getSize().getSize().width), Math.max(image.getHeight(), textArea.getSize().getSize().height)));
                //textArea.setLocation(new Point(pointer.getX() - 10, pointer.getY() - 10));
                item.setHW(getHeight(), getWidth());
                item.setText(textArea.getText());
                revalidate(); // Перерисовываем контейнер
                repaint();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                setSize(new Dimension(Math.max(getSize().width, textArea.getSize().getSize().width), Math.max(getSize().height, textArea.getSize().getSize().height)));
                //textArea.setLocation(new Point(pointer.getX() - 10, pointer.getY() - 10));
                item.setHW(getHeight(), getWidth());
                item.setText(textArea.getText());
                revalidate(); // Перерисовываем контейнер
                repaint();
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                screenX = e.getX();
                screenY = e.getY();
            }

        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(!e.isControlDown()) {
                    int deltaX = e.getX() - screenX;
                    int deltaY = e.getY() - screenY;
                    setLocation(Math.round((item.getXCord() + deltaX)/10) * 10, Math.round((item.getYCord() + deltaY)/10) * 10);
                    item.setCord(Math.round((item.getXCord() + deltaX)/10) * 10, Math.round((item.getYCord() + deltaY)/10) * 10);
                    repaint();
                    getParent().repaint();
                }
                 else {
                     int deltaX = e.getX() - screenX;
                     int deltaY = e.getY() - screenY;
                     for(itemPanel block : blocks) {
                         block.setLocation(Math.round((block.item.getXCord() + deltaX)/10) * 10, Math.round((block.item.getYCord() + deltaY)/10) * 10);
                         block.item.setCord(Math.round((block.item.getXCord() + deltaX)/10) * 10, Math.round((block.item.getYCord() + deltaY)/10) * 10);
                     }
                }



            }
        });
        add(textArea);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, item.getW(), item.getH(), null);
    }

    private void getImage(String type) {
        try {
            switch (type) {
                case "Block" -> image = ImageIO.read(new File("images/block.png"));
                case "InputOutput" -> image = ImageIO.read(new File("images/io.png"));
                case "Start", "End" -> image = ImageIO.read(new File("images/startend.png"));
                case "Cycle" -> image = ImageIO.read(new File("images/cycle.png"));
                case "Display" -> image = ImageIO.read(new File("images/display.png"));
                case "Function" -> image = ImageIO.read(new File("images/function.png"));
                case "Condition" -> image = ImageIO.read(new File("images/condition.png"));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public BaseBlockModel returnItem() {
        return item;
    }
    public void setBlocks(ArrayList<itemPanel> blocks) {this.blocks = blocks;}
}

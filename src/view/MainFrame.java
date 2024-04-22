package view;

import io.TxtIO;
import model.BlocksMap.BlocksMap;
import model.Check.CheckDiagram;
import model.blocks.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class MainFrame extends JFrame {
    private final int height = 750;
    private final int width = 1200;
    private static int currentID = 0;
    private final JButton blockButton;
    private final JButton startButton;
    private final JButton endButton;
    private final JButton cycleButton;
    private final JButton ioButton;
    private final JButton conditionButton;
    private final JButton functionButton;
    private final JButton txtButton;
    private final JButton pngButton;
    private final JButton checkButton;
    private final JButton displayButton;
    private final JButton loadFromTxt;
    private final JPanel mainPane;
    private final JPanel buttonsPane;
    private diagramPanel diagramPane;
    private itemPanel item;
    private BlocksMap elements = new BlocksMap();

    public MainFrame() {
        super("Diagram creator");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buttonsPane = createButtonsPanel();
        diagramPane = new diagramPanel();
        mainPane = createMainPanel();
        diagramPane.setBlocksMap(elements);

        loadFromTxt = new CommonButton("Upload a save", "images\\buttons\\uploadbutton.png");
        blockButton = new CommonButton("Block", "images\\buttons\\blockbutton.png");
        startButton = new CommonButton("Start", "images\\buttons\\startendbutton.png");
        endButton = new CommonButton("End", "images\\buttons\\startendbutton.png");
        cycleButton = new CommonButton("Cycle", "images\\buttons\\cyclebutton.png");
        ioButton = new CommonButton("Input/Output","images\\buttons\\iobutton.png");
        conditionButton = new CommonButton("Condition", "images\\buttons\\conditionbutton.png");
        functionButton = new CommonButton("Function", "images\\buttons\\functionbutton.png");
        txtButton = new CommonButton("Save in txt", "images\\buttons\\savebutton.png");
        pngButton = new CommonButton("Save in png", "images\\buttons\\pngbutton.png");
        checkButton = new CommonButton("Check diagram", "images\\buttons\\checkbutton.png");
        displayButton = new CommonButton("Display", "images\\buttons\\displaybutton.png");

        blockButton.addActionListener(new blockButtonListener());
        startButton.addActionListener(new startButtonListener());
        endButton.addActionListener(new endButtonListener());
        ioButton.addActionListener(new ioButtonListener());
        cycleButton.addActionListener(new cycleButtonListener());
        functionButton.addActionListener(new functionButtonListener());
        conditionButton.addActionListener(new conditionButtonListener());
        pngButton.addActionListener(new saveInPngButtonListener());
        displayButton.addActionListener(new displayButtonListener());
        txtButton.addActionListener(new saveInTxtButton());
        loadFromTxt.addActionListener(new loadFromTxtButton());
        checkButton.addActionListener(new checkDiagramButton());

        buttonsPane.add(startButton);
        buttonsPane.add(endButton);
        buttonsPane.add(blockButton);
        buttonsPane.add(functionButton);
        buttonsPane.add(conditionButton);
        buttonsPane.add(cycleButton);
        buttonsPane.add(ioButton);
        buttonsPane.add(displayButton);
        buttonsPane.add(checkButton);
        buttonsPane.add(txtButton);
        buttonsPane.add(pngButton);
        buttonsPane.add(loadFromTxt);

        JScrollPane scrollPane = new JScrollPane(diagramPane);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        mainPane.add(buttonsPane);
        mainPane.add(scrollPane);
        getContentPane().add(mainPane);
        setVisible(true);
    }

    private JPanel createMainPanel() {
        return BoxLayoutUtils.createHorizontalPanel();
    }

    private JPanel createButtonsPanel() {
        JPanel pane = new JPanel();
        pane.setPreferredSize(new Dimension(232, height));
        pane.setLayout(new GridLayout(12, 1, 0, 2));
        return pane;
    }

    private class blockButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            BlockModel block = new BlockModel(currentID);
            addElement(block);
            
        }
    }

    private class conditionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ConditionModel block = new ConditionModel(currentID);
            addElement(block);
            
        }
    }

    private class functionButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            FunctionModel block = new FunctionModel(currentID);
            addElement(block);
            
        }
    }

    private class cycleButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            CycleModel block = new CycleModel(currentID);
            addElement(block);
            
        }
    }

    private class ioButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            InputOutputModel block = new InputOutputModel(currentID);
            addElement(block);
            
        }
    }

    private class displayButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            DisplayModel block = new DisplayModel(currentID);
            addElement(block);
            
        }
    }

    private class startButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StartModel block = new StartModel(currentID);
            addElement(block);
            
        }
    }

    private class endButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            EndModel block = new EndModel(currentID);
            addElement(block);
        }
    }

    private class saveInPngButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                diagramPane.getPngHW();
                BufferedImage image = new BufferedImage(diagramPane.getPngHW().width, diagramPane.getPngHW().height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = image.createGraphics();
                g2.clearRect(0, 0, image.getWidth(), image.getHeight());
                diagramPane.paint(g2);
                g2.dispose();
                try {
                    ImageIO.write(image, "png", selectedFile);
                    JOptionPane.showMessageDialog(null, "Image created");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error");
                    ex.printStackTrace();
                }

            }
        }
    }
    private class saveInTxtButton implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                try {
                    TxtIO txtIO = new TxtIO();
                    txtIO.save_in_txt(elements.returnBlocks(), file);
                    JOptionPane.showMessageDialog(null, "The diagram has been saved successfully");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error");
                }
            }
        }
    }
    private class loadFromTxtButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                TxtIO load = new TxtIO();
                elements = new BlocksMap();
                diagramPane.removeAll();
                try {
                    HashMap<Integer, BaseBlockModel> tmp = load.loadFromTxt(selectedFile);
                    elements.setMap(tmp);
                    ArrayList<Integer> keys = elements.returnIDs();
                    itemPanel item;
                    for (Integer key : keys) {
                        item = new itemPanel(elements.getClassByID(key));
                        diagramPane.addNewItem(item);
                    }
                    currentID = Collections.max(elements.returnIDs()) + 1;
                    revalidate();
                    repaint();
                    JOptionPane.showMessageDialog(null, "The diagram has been uploaded successfully");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error");
                    ex.printStackTrace();
                }
                diagramPane.setBlocksMap(elements);
                diagramPane.revalidate();
                diagramPane.repaint();
                revalidate();
                repaint();
            }
        }
    }
    private class checkDiagramButton implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            CheckDiagram ch = new CheckDiagram();
            JOptionPane.showMessageDialog(null, ch.check(elements.returnBlocks()));
        }
    }
    private void addElement(BaseBlockModel block) {
        elements.add(currentID, block);
        currentID += 1;
        item = new itemPanel(block);
        diagramPane.addNewItem(item);
    }

}

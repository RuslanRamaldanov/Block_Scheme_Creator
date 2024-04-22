package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class CommonButton extends JButton {
    public CommonButton(String text, String pathToIcon) {
        super(text);
        //setIcon(new ImageIcon("image.png")); // Установите путь к вашей картинке
//        setHorizontalAlignment(SwingConstants.LEFT);
//        setOpaque(true);
//        setForeground(Color.black);
//        setBackground(Color.WHITE);
//        Border line = new LineBorder(Color.gray);
//        setBorder(line);
        setIcon(new ImageIcon(pathToIcon)); // Установите путь к вашей иконке
        setHorizontalAlignment(SwingConstants.LEFT);
        setForeground(Color.black);
        setBackground(Color.WHITE);
        Border line = new LineBorder(Color.gray);
        setBorder(line);
    }
}

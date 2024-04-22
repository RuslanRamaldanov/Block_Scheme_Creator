package view;
// Класс для удобной работы с блочным расположением
import javax.swing.*;
public class BoxLayoutUtils {
    // задает единое выравнивание по оси X для группы компонентов
    public static void setGroupAlignmentX(JComponent[] cs, float alignment) {
        for (int i=0; i<cs.length; i++) {
            cs[i].setAlignmentX(alignment);
        }
    }
    // задает единое выравнивание по оси Y для группы компонентов
    public static void setGroupAlignmentY(JComponent[] cs, float alignment) {
        for (int i=0; i<cs.length; i++) {
            cs[i].setAlignmentY(alignment);
        }
    }
    // возвращает панель с установленным вертикальным блочным расположением
    public static JPanel createVerticalPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        return p;
    }
    // возвращает панель с установленным горизонтальным блочным расположением
    public static JPanel createHorizontalPanel() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        return p;
    }
}

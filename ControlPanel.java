package ShapeBuilderBeta;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel {

    public JSlider dotSlider;
    public JButton removeAllDots = new JButton("Remove dots");
    public JButton addDots = new JButton("Add dots");
    public JButton dotBetween = new JButton("dot between");
    JTextArea textField = new JTextArea();

    public ControlPanel(ActionListener listener, ChangeListener changeListener) {
        this.setPreferredSize(new Dimension(175,getHeight()));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        initSlider();
        initButton();
        this.dotSlider.addChangeListener(changeListener);
        this.removeAllDots.addActionListener(listener);
        this.addDots.addActionListener(listener);
        this.dotBetween.addActionListener(listener);
        this.add(textField);
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setFont(new Font("Consolas", Font.BOLD, 12));
        textField.setText("""
                Controls:
                
                -leftMouseClick to create or select a dot

                -rightMouseClick to remove a dot

                -Connect dots by selecting two different dots

                -Drag a dot by selecting and drag it around

                -Change dot size by dragging the slider""");

    }

    private void initSlider() {
        dotSlider = new JSlider(10, 100, 10);
        dotSlider.setOrientation(JSlider.VERTICAL);
        dotSlider.setSize(new Dimension(20, 150));

        dotSlider.setPaintTicks(true);
        dotSlider.setMinorTickSpacing(5);

        dotSlider.setPaintTrack(true);
        dotSlider.setMajorTickSpacing(10);
        dotSlider.setLocation(10, 100);
        add(dotSlider);

    }

    private void initButton() {
        add(removeAllDots);
        add(addDots);
       // add(dotBetween);
        removeAllDots.setSize(new Dimension(this.getWidth(), 20));

    }
}

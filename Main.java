package ShapeBuilderBeta;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static JFrame gui;
    public static JPanel mainPanel;
    public static ControlPanel controlPanel;
    public static ConnectTheDots connectTheDots;
    public static final int frameWidth = 860;
    public static final int frameHeight = 640;


    public static void main(String[] args) {


        gui = new JFrame();
        gui.setTitle("Connect The Dots");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        connectTheDots = new ConnectTheDots(30);
        controlPanel = new ControlPanel(connectTheDots, connectTheDots);
        mainPanel.add(connectTheDots, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.WEST);
        gui.add(mainPanel);
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setPreferredSize(new Dimension(frameWidth, frameHeight));

        gui.pack();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);

    }
}

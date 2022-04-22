package ShapeBuilderBeta;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

import java.util.ArrayList;
import java.util.Random;

public class ConnectTheDots extends JLayeredPane implements MouseListener, MouseMotionListener, ActionListener, ChangeListener {

    public int dotSize;
    public Dot currentSelectedDot = null;
    public ArrayList<Dot> dots = new ArrayList<>();
    public ArrayList<Dot> selectedDots = new ArrayList<>();


    public ConnectTheDots(int dotSize) {
        this.setPreferredSize(new Dimension(getSize()));
        this.dotSize = dotSize;
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);

    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        paintDots(g2D);
        paintHoveredDot(g2D);
        paintSelectedDot(g2D);
        paintEdges(g2D);
        paintToolTip(g2D);

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            selectDot(e);
            createEdge();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            createVisualDot(e);
        } else if (SwingUtilities.isRightMouseButton(e)) {
            removeVisualDot(e);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        try {
            Dot dot = (Dot) e.getSource();
            dot.setHovered(false);
            repaint();
        } catch (Exception ignored) {

        }
    }

    public void paintDots(Graphics2D graphics2D) {
        for (Dot dot : dots) {
            if (!dot.isPainted()) {
                //  add(dot);
            }
            graphics2D.setColor(dot.getColor());
            graphics2D.fillOval(dot.getXPoint() - dot.getRadius(), dot.getYPoint() - dot.getRadius(),
                    dot.getRadius() * 2, dot.getRadius() * 2);
            dot.setPainted(true);
        }
        //  System.out.println("Components: " + this.getComponents().length);
        //  System.out.println("Dots: " + dots.size());
    }

    private void paintHoveredDot(Graphics2D graphics2D) {
        for (Dot dot : dots) {
            if (dot.isHovered()) {
                graphics2D.setColor(Color.YELLOW);
                graphics2D.setStroke(new BasicStroke(4));
                graphics2D.drawOval(dot.getXPoint() - dot.getRadius(), dot.getYPoint() - dot.getRadius(),
                        dot.getRadius() * 2, dot.getRadius() * 2);
                // paintToolTip(graphics2D, dot);
            }
        }
    }

    private void paintToolTip(Graphics2D graphics2D) {
        for (Dot dot : dots) {
            if (dot.isHovered() || dot.isSelected()) {
                graphics2D.setColor(Color.BLACK);
                Font f = new Font("Serif", Font.PLAIN, 20);
                graphics2D.setFont(f);
                graphics2D.drawString("X: " + dot.getXPoint() + " Y: " + dot.getYPoint(), dot.getXPoint() + 10, dot.getYPoint() + 30);
            }

        }

    }

    private void paintSelectedDot(Graphics2D graphics2D) {
        for (Dot dot : dots) {
            if (dot.isSelected()) {
                GradientPaint colorGradient = new GradientPaint(dot.getXPoint(), dot.getYPoint(), dot.getColor(),
                        dot.getXPoint() + dotSize / 2, dot.getYPoint() + dotSize / 2, Color.YELLOW);
                graphics2D.setPaint(colorGradient);

                graphics2D.setStroke(new BasicStroke(4));
                graphics2D.fillOval(dot.getXPoint() - dot.getRadius(), dot.getYPoint() - dot.getRadius(),
                        dot.getRadius() * 2, dot.getRadius() * 2);
                graphics2D.setColor(Color.YELLOW);
                graphics2D.drawOval(dot.getXPoint() - dot.getRadius(), dot.getYPoint() - dot.getRadius(),
                        dot.getRadius() * 2, dot.getRadius() * 2);
            }
        }
    }

    private void paintEdges(Graphics2D graphics2D) {
        for (Dot dot : dots) {
            for (Edge edge : dot.getEdges()) {
                Dot from = edge.getFrom();
                Dot to = edge.getTo();
                GradientPaint colorGradient = new GradientPaint(from.getXPoint(), from.getYPoint(), from.getColor(),
                        to.getXPoint()+20, to.getYPoint()+20, to.getColor().brighter().brighter());

                Color c = from.getColor();
                graphics2D.setPaint(colorGradient);
                graphics2D.setStroke(new BasicStroke(4));
                graphics2D.drawLine(from.getXPoint(), from.getYPoint(), to.getXPoint(), to.getYPoint());
            }
        }


    }

    public boolean addDot(Point point) {
        Random rand = new Random();
        Color randColor = new Color(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255));
        Dot newDot = new Dot(point.x, point.y, dotSize, this, this);
        newDot.setColor(randColor);
        if (!checkDotCollide(newDot)) {
            dots.add(newDot);
            return true;
        }
        return false;
    }

    public void createVisualDot(MouseEvent e) {
        try {
            Dot pressedDot = (Dot) e.getSource();

        } catch (Exception ignored) {
            Point p = e.getPoint();
            if (addDot(p)) {
                repaint();
            }
        }

    }

    public void selectDot(MouseEvent e) {
        try {
            Dot pressedDot = getDot(e);
            if (pressedDot.isHovered() && pressedDot.isSelected()) {
                pressedDot.setSelected(false);
                selectedDots.remove(pressedDot);

            } else if (pressedDot.isHovered()) {
                pressedDot.setSelected(true);
                selectedDots.add(pressedDot);

            }
            repaint();

        } catch (Exception ignored) {

        }
    }

    public void createEdge() {
        if(selectedDots.size() < 1){
            return;
        }
        try {
            Dot from = selectedDots.get(0);
            Dot to = selectedDots.get(1);
            Edge edge = new Edge(from, to);
            from.addEdge(edge);
            to.addEdge(edge);
            from.setSelected(false);
            to.setSelected(false);

            selectedDots.clear();
            repaint();

        } catch (Exception ignored) {

        }
    }

    public void setHoveredDots(MouseEvent e) {
        try {
            // Dot dot = (Dot) e.getSource();
            Dot dot = getDot(e);
            if (dot != null) {
                if (!dot.isHovered()) {
                    //     JLabel toolTip = new JLabel();
                    ////    toolTip.setLocation(dot.getPoint());
                    //    toolTip.setText("X: " + dot.getXPoint() + " " + "Y: " + dot.getYPoint());
                    //    toolTip.setSize(100, 50);
                }
                dot.setHovered(true);
                repaint();
            } else {
                for (Dot d : dots) {
                    d.setHovered(false);
                    //  removeAll();
                    // initButton();
                    repaint();
                }
            }
        } catch (Exception ignored) {
            // removeAll();
            // initButton();


        }

    }

    public void dragDot(MouseEvent e) {
        try {
            // Dot draggedDot = (Dot) e.getSource();
            Dot draggedDot = getDot(e);
            int xDelta = e.getPoint().x - draggedDot.getXPoint();
            int yDelta = e.getPoint().y - draggedDot.getYPoint();
            if (draggedDot.isSelected()) {
                Point newP = new Point(draggedDot.getXPoint() + xDelta, draggedDot.getYPoint() + yDelta);
                draggedDot.setPoint(newP);
                repaint();
            }

        } catch (Exception ignored) {
        }
    }

    public void removeVisualDot(MouseEvent e) {
        try {
            //  Dot pressedDot = (Dot) e.getSource();
            Dot pressedDot = getDot(e);
            Point point = pressedDot.getPoint();
            if (removeDot(point)) {
                if (pressedDot.equals(currentSelectedDot)) {
                    currentSelectedDot = null;
                }
                repaint();
            }
        } catch (Exception ignored) {
        }
    }

    public boolean removeDot(Point point) {
        int x1 = point.x;
        int y1 = point.y;
        int r1 = 1;
        for (int i = 0; i < dots.size(); i++) {
            int x2 = dots.get(i).getXPoint();
            int y2 = dots.get(i).getYPoint();
            int r2 = dots.get(i).getRadius();
            double dist = Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            boolean insideDot = dist < (r1 + r2) * (r1 + r2);
            if (insideDot) {
                for (Dot dot : dots) {
                    Edge rmvEdge = dot.getSingleEdge(dots.get(i));
                    if(rmvEdge  != null){
                        dot.removeEdge(rmvEdge);
                    }

                }

                if(!selectedDots.isEmpty() && dots.get(i).equals(selectedDots.get(0))){
                    selectedDots.clear();
                }
                dots.remove(dots.get(i));
                // remove(i);
                return true;
            }
        }
        return false;
    }

    boolean checkDotCollide(Dot toBeAdded) {
        int x1 = toBeAdded.getXPoint();
        int y1 = toBeAdded.getYPoint();
        int r1 = toBeAdded.getRadius();

        for (Dot d : dots) {
            int x2 = d.getXPoint();
            int y2 = d.getYPoint();
            int r2 = d.getRadius();
            double dist = Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
            boolean bool = dist < (r1 + r2) * (r1 + r2);
            //  System.out.println("dist " + dist);
            // System.out.println("rad "  +(r1 + r2) * (r1 + r2));
            if (bool) {
                return true;
            }
        }
        return false;

    }

    public Dot getDot(MouseEvent e) {
        if (dots.isEmpty()) {
            return null;
        }
        try {
            int x1 = e.getPoint().x;
            int y1 = e.getPoint().y;
            int r1 = 1;

            for (Dot d : dots) {
                int x2 = d.getXPoint();
                int y2 = d.getYPoint();
                int r2 = d.getRadius();
                double dist = Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
                boolean bool = dist < (r1 + r2) * (r1 + r2);
                //  System.out.println("dist " + dist);
                // System.out.println("rad "  +(r1 + r2) * (r1 + r2));
                if (bool) {
                    return d;
                }
            }
        } catch (Exception ignored) {

        }
        return null;
    }

    public void removeAllDots(ActionEvent e) {
        if (e.getActionCommand().equals("Remove dots")) {
            dots.clear();
            selectedDots.clear();
            repaint();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        removeAllDots(e);
        addRandomDots(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dragDot(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        setHoveredDots(e);
    }


    private void changeDotSize(ChangeEvent e) {
        try {
            JSlider dotSlider = (JSlider) e.getSource();
            dotSize = dotSlider.getValue();
            currentSelectedDot.setRadius(dotSlider.getValue());
            repaint();

        } catch (Exception ignored) {

        }
    }

    private void addRandomDots(ActionEvent e) {
        Random random = new Random();
        if (e.getActionCommand().equals("Add dots")) {
            for (int i = 0; i < random.nextInt(1000); i++) {
                Random randomXPoint = new Random();
                Random randomYPoint = new Random();
                Point p = new Point(randomXPoint.nextInt(this.getWidth()), randomYPoint.nextInt(this.getHeight()));
                addDot(p);
            }

        }
        repaint();
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        changeDotSize(e);
    }

    public void addBetween(){
        // P1 + t(P2-P1)
    }
}

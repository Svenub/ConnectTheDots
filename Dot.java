package ShapeBuilderBeta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class Dot extends JPanel {

    private int xPoint;
    private int yPoint;
    private int radius;
    private boolean isPainted;
    private boolean isHovered;
    private boolean isSelected;
    private Color color;
    ArrayList<Edge> edges = new ArrayList<>();

    public Dot(int xPoint, int yPoint, int radius, MouseListener listener, MouseMotionListener listener2) {
        this.xPoint = xPoint;
        this.yPoint = yPoint;
        this.radius = radius;
        this.setSize(radius * 2, radius * 2);
        this.setToolTipText("X: " + getXPoint() + " " + "Y: " + getYPoint());
        // this.addMouseListener(this);
        this.setLocation(xPoint - radius, yPoint - radius);
        this.setOpaque(false);
        setListener(listener, listener2);


    }

    private void setListener(MouseListener listener, MouseMotionListener listener2) {
        this.addMouseListener(listener);
        this.addMouseMotionListener(listener2);
    }

    public Point getPoint() {
        return new Point(xPoint, yPoint);
    }

    public void setPoint(Point p){
        xPoint = p.x;
        yPoint = p.y;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getXPoint() {
        return xPoint;
    }

    public int getYPoint() {
        return yPoint;
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public boolean isPainted() {
        return isPainted;
    }

    public void setPainted(boolean painted) {
        isPainted = painted;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isHovered() {
        return isHovered;
    }

    public void setHovered(boolean hovered) {
        isHovered = hovered;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    private boolean edgeAlreadyExist(Edge addEdge) {
        for (Edge edge : edges) {
            if (addEdge.equals(edge) ||
                    (addEdge.getFrom().equals(edge.getTo()) && addEdge.getTo().equals(edge.getFrom()))) {
                return true;
            }
        }
        return false;
    }

    public void addEdge(Edge edge){
        if(!edgeAlreadyExist(edge) ){
            edges.add(edge);
        }
    }

    public void removeEdge(Edge edgeToBeRemoved){
        for(Edge edge : edges){
            if(edgeToBeRemoved.equals(edge)){
                edges.remove(edge);
                break;
            }
        }

    }

    public Edge getSingleEdge(Dot dot){
        for (Edge e : edges){
            if(dot.equals(e.getFrom()) || dot.equals(e.getTo())){
                return e;
            }
        }
        return null;
    }



    @Override
    public String toString() {
        return "Dot{" +
                "x=" + xPoint +
                ", y=" + yPoint +
                '}';
    }

}
package ShapeBuilderBeta;

import java.util.Objects;

public class Edge {

    private Dot from;
    private Dot to;

    public Edge(Dot from, Dot to){
        this.from = from;
        this.to = to;

    }

    public Dot getFrom() {
        return from;
    }

    public Dot getTo() {
        return to;
    }

}

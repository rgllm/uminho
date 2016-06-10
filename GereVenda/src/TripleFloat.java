
public class TripleFloat {
    
    private ParFloat first;
    private float second;

    public TripleFloat(ParFloat first, float second) {
        this.first = first;
        this.second = second;
    }

    public ParFloat getFirst() {
        return first;
    }

    public float getSecond() {
        return second;
    }
    
    public void incSecond() {
        second++;
    }
    
    public void addSecond(Float x) {
        second+=x;
    }
}

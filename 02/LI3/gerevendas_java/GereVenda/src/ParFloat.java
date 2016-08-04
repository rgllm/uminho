final class ParFloat {
    private float first;
    private float second;

    public ParFloat(float first, float second){
        this.first = first;
        this.second = second;
    }

    public float getFirst() {
        return first;
    }

    public float getSecond() {
        return second;
    }
        
    public void incFirst() {
        first++;
    }
    
    public void addFirst(Float x) {
        first+=x;
    }
        
    public void incSecond() {
        second++;
    }
    
    public void addSecond(Float x) {
        second+=x;
    }
    
    
}

package concurrency.example;



public class HitCounter {

    private int counter = 0;

    public int hit() {
        return ++counter;
    }
}

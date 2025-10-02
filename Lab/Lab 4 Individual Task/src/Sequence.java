import java.util.ArrayList;
import java.util.Objects;

public class Sequence {
    private ArrayList<Integer> sequence;

    public Sequence(ArrayList<Integer> sequence) {
        this.sequence = sequence;
    }
    public Sequence() {
        this.sequence = new ArrayList<>();
    }

    public int contains(int element){
        for(int i = 0; i < sequence.size(); i++){
            if(sequence.get(i) == element) return i;
        }
        return -1;
    }
    public ArrayList<Integer> getSequence() {
        return sequence;
    }
    public String getType(){
        boolean isIncreasing = true, isDecreasing = true, isNonIncreasing = true, isNonDecreasing = true, isArithmetical = true, isGeometric = true;
        int commonDifference = sequence.get(1) - sequence.get(0);
        double commonRatio = (double)sequence.get(1) / sequence.get(0);

        for (int i = 1; i < sequence.size(); i++) {
            int prev = sequence.get(i-1), curr = sequence.get(i);

            if (curr <= prev) isIncreasing = false;
            if (curr >= prev) isDecreasing = false;
            if (curr > prev) isNonIncreasing = false;
            if (curr < prev) isNonDecreasing = false;
            if (curr - prev != commonDifference) isArithmetical = false;
            if (prev == 0 || (double)curr / prev != commonRatio) isGeometric = false;
        }
        if(isIncreasing){ return "Increasing"; }
        if(isDecreasing){ return "Decreasing"; }
        if(isNonIncreasing){ return "Non-Increasing"; }
        if(isNonDecreasing){ return "Non-Decreasing"; }
        if(isArithmetical){ return "Arithmetical"; }
        if(isGeometric){ return "Geometric"; }
        return "None";
    }
    public int getMax(){
        int max = Integer.MIN_VALUE;
        for (Integer num : sequence) {
            if (num > max) max = num;
        }
        return max;
    }
    public int getMaxIndex(){
        int max = Integer.MIN_VALUE;
        int max_index = -1;
        for(int i = 0; i < sequence.size(); i++){
            if(sequence.get(i) > max) {
                max = sequence.get(i);
                max_index = i;
            }
        }
        return max_index;
    }
    public int getMin(){
        int min = Integer.MAX_VALUE;
        for (Integer num : sequence) {
            if (num < min) min = num;
        }
        return min;
    }
    public int getMinIndex(){
        int min = Integer.MAX_VALUE;
        int min_index = -1;
        for(int i = 0; i < sequence.size(); i++){
            if(sequence.get(i) < min) {
                min = sequence.get(i);
                min_index = i;
            }
        }
        return min_index;
    }

    public boolean equals(Sequence other){
        if(this.sequence.size() != other.sequence.size()) return false;
        for(int i = 0; i < sequence.size(); i++){
            if(!Objects.equals(sequence.get(i), other.sequence.get(i))) return false;
        }
        return true;
    }
}

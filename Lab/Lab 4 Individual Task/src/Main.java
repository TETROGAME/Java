import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void testMinMax() {
        Sequence normalSequence = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        assert normalSequence.getMax() == 9 : "Expected Max: 9, got: " + normalSequence.getMax();
        assert normalSequence.getMin() == 1 : "Expected Min: 1, got: " + normalSequence.getMin();
        Sequence emptySequence = new Sequence(new ArrayList<>());
        assert emptySequence.getMax() == Integer.MIN_VALUE :  "Expected Max: " + Integer.MIN_VALUE + ", got: " + emptySequence.getMax();
        assert emptySequence.getMin() == Integer.MAX_VALUE : "Expected Min: " + Integer.MAX_VALUE + ", got: " + emptySequence.getMin();
    }

    public static void testMinMaxIndices() {
        Sequence normalSequence = new Sequence(new ArrayList<>(Arrays.asList(1, 5, 3, 7, 2, 8, 4)));
        assert normalSequence.getMaxIndex() == 5 : "Expected Max index: 5, got: " + normalSequence.getMaxIndex();
        assert normalSequence.getMinIndex() == 0 : "Expected Min index: 0, got: " + normalSequence.getMinIndex();
        Sequence emptySequence = new Sequence(new ArrayList<>());
        assert emptySequence.getMaxIndex() == -1 : "Expected Max index: -1, got: " + emptySequence.getMaxIndex();
        assert emptySequence.getMinIndex() == -1 : "Expected Min index: -1, got: " + emptySequence.getMinIndex();
    }

    public static void testContains() {
        Sequence seq = new Sequence(new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50)));
        assert seq.contains(10) == 0 : "Expected index: 0, got: " + seq.contains(10);
        assert seq.contains(30) == 2 : "Expected index: 2, got: " + seq.contains(30);
        assert seq.contains(55) == -1 : "Expected index: -1, got: " + seq.contains(55);
        Sequence emptySequence = new Sequence(new ArrayList<>());
        assert emptySequence.contains(1) == -1 : "Expected index: -1, got: " + emptySequence.contains(1);
    }

    public static void testGetType() {
        Sequence s1 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        ArrayList<String> types1 = s1.getTypes();
        assert types1.contains("Increasing") : "Should contain Increasing";
        assert types1.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types1.contains("Arithmetical") : "Should contain Arithmetical";
        assert types1.size() == 3 : "Should contain only Increasing, Non-Decreasing, Arithmetical";

        Sequence s2 = new Sequence(new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1)));
        ArrayList<String> types2 = s2.getTypes();
        assert types2.contains("Decreasing") : "Should contain Decreasing";
        assert types2.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types2.contains("Arithmetical") : "Should contain Arithmetical";
        assert types2.size() == 3 : "Should contain only Decreasing, Non-Increasing, Arithmetical";

        Sequence s3 = new Sequence(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)));
        ArrayList<String> types3 = s3.getTypes();
        assert types3.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types3.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types3.contains("Arithmetical") : "Should contain Arithmetical";
        assert types3.contains("Geometric") : "Should contain Geometric";
        assert types3.size() == 4 : "Should contain only Non-Increasing, Non-Decreasing, Arithmetical, Geometric";

        // Corrected for your implementation!
        Sequence s4 = new Sequence(new ArrayList<>(Arrays.asList(2, 4, 8, 16)));
        ArrayList<String> types4 = s4.getTypes();
        assert types4.contains("Increasing") : "Should contain Increasing";
        assert types4.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types4.contains("Geometric") : "Should contain Geometric";
        assert types4.size() == 3 : "Should contain only Increasing, Non-Decreasing, Geometric";

        Sequence s5 = new Sequence(new ArrayList<>(Arrays.asList(5, 4, 4, 2)));
        ArrayList<String> types5 = s5.getTypes();
        assert types5.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types5.size() == 1 : "Should contain only Non-Increasing";

        Sequence s6 = new Sequence(new ArrayList<>(Arrays.asList(2, 4, 4, 5)));
        ArrayList<String> types6 = s6.getTypes();
        assert types6.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types6.size() == 1 : "Should contain only Non-Decreasing";

        Sequence s7 = new Sequence(new ArrayList<>(Arrays.asList(2, 2, 2, 2, 2)));
        ArrayList<String> types7 = s7.getTypes();
        assert types7.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types7.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types7.contains("Arithmetical") : "Should contain Arithmetical";
        assert types7.contains("Geometric") : "Should contain Geometric";
        assert types7.size() == 4 : "Sould contain Non-Increasing, Non-Decreasing, Arithmetical, Geometric";

        Sequence s8 = new Sequence(new ArrayList<>(List.of()));
        ArrayList<String> types8 = s8.getTypes();
        assert types8.contains("None") : "Empty sequence should contain None";
        assert types8.size() == 1 : "Should contain only None";

        Sequence s9 = new Sequence(new ArrayList<>(List.of(42)));
        ArrayList<String> types9 = s9.getTypes();
        assert types9.contains("None") : "Single element should contain None";
        assert types9.size() == 1 : "Should contain only None";
    }

    public static void testEquals() {
        Sequence seq1 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3)));
        Sequence seq2 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3)));
        Sequence seq3 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 4)));
        Sequence seq4 = new Sequence(new ArrayList<>(Arrays.asList(1, 2)));
        assert seq1.equals(seq2) : "Sequences should be equal";
        assert !seq1.equals(seq3) : "Sequences should not be equal";
        assert !seq1.equals(seq4) : "Sequences should not be equal (length mismatch)";
    }

    public static void testGetSequence() {
        ArrayList<Integer> initial = new ArrayList<>(Arrays.asList(1, 2, 3));
        Sequence seq = new Sequence(initial);
        assert seq.getSequence().equals(initial) : "Expected sequence to match initial list";
    }

    public static void testAll() {
        testMinMax();
        testMinMaxIndices();
        testContains();
        testGetType();
        testEquals();
        testGetSequence();
    }
    public static void main(String[] args) {
        testAll();
    }
}
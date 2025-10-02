import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void testMinMax() {
        System.out.println("Testing getMax and getMin...");
        Sequence normalSequence = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9)));
        System.out.println("Normal sequence: " + normalSequence.getSequence());
        System.out.println("Max: " + normalSequence.getMax());
        System.out.println("Min: " + normalSequence.getMin());
        assert normalSequence.getMax() == 9 : "Expected Max: 9, got: " + normalSequence.getMax();
        assert normalSequence.getMin() == 1 : "Expected Min: 1, got: " + normalSequence.getMin();
        Sequence emptySequence = new Sequence(new ArrayList<>());
        System.out.println("Empty sequence: " + emptySequence.getSequence());
        System.out.println("Max: " + emptySequence.getMax());
        System.out.println("Min: " + emptySequence.getMin());
        assert emptySequence.getMax() == Integer.MIN_VALUE :  "Expected Max: " + Integer.MIN_VALUE + ", got: " + emptySequence.getMax();
        assert emptySequence.getMin() == Integer.MAX_VALUE : "Expected Min: " + Integer.MAX_VALUE + ", got: " + emptySequence.getMin();
    }

    public static void testMinMaxIndices() {
        System.out.println("Testing getMaxIndex and getMinIndex...");
        Sequence normalSequence = new Sequence(new ArrayList<>(Arrays.asList(1, 5, 3, 7, 2, 8, 4)));
        System.out.println("Normal sequence: " + normalSequence.getSequence());
        System.out.println("Max Index: " + normalSequence.getMaxIndex());
        System.out.println("Min Index: " + normalSequence.getMinIndex());
        assert normalSequence.getMaxIndex() == 5 : "Expected Max index: 5, got: " + normalSequence.getMaxIndex();
        assert normalSequence.getMinIndex() == 0 : "Expected Min index: 0, got: " + normalSequence.getMinIndex();
        Sequence emptySequence = new Sequence(new ArrayList<>());
        System.out.println("Empty sequence: " + emptySequence.getSequence());
        System.out.println("Max Index: " + emptySequence.getMaxIndex());
        System.out.println("Min Index: " + emptySequence.getMinIndex());
        assert emptySequence.getMaxIndex() == -1 : "Expected Max index: -1, got: " + emptySequence.getMaxIndex();
        assert emptySequence.getMinIndex() == -1 : "Expected Min index: -1, got: " + emptySequence.getMinIndex();
    }

    public static void testContains() {
        System.out.println("Testing contains...");
        Sequence seq = new Sequence(new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50)));
        System.out.println("Sequence: " + seq.getSequence());
        System.out.println("Index of 10: " + seq.contains(10));
        System.out.println("Index of 30: " + seq.contains(30));
        System.out.println("Index of 55: " + seq.contains(55));
        assert seq.contains(10) == 0 : "Expected index: 0, got: " + seq.contains(10);
        assert seq.contains(30) == 2 : "Expected index: 2, got: " + seq.contains(30);
        assert seq.contains(55) == -1 : "Expected index: -1, got: " + seq.contains(55);
        Sequence emptySequence = new Sequence(new ArrayList<>());
        System.out.println("Empty sequence: " + emptySequence.getSequence());
        System.out.println("Index of 1: " + emptySequence.contains(1));
        assert emptySequence.contains(1) == -1 : "Expected index: -1, got: " + emptySequence.contains(1);
    }

    public static void testGetType() {
        System.out.println("Testing getTypes...");
        Sequence s1 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        ArrayList<String> types1 = s1.getTypes();
        System.out.println("Sequence: " + s1.getSequence());
        System.out.println("Types: " + types1);
        assert types1.contains("Increasing") : "Should contain Increasing";
        assert types1.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types1.contains("Arithmetical") : "Should contain Arithmetical";
        assert types1.size() == 3 : "Should contain only Increasing, Non-Decreasing, Arithmetical";

        Sequence s2 = new Sequence(new ArrayList<>(Arrays.asList(5, 4, 3, 2, 1)));
        ArrayList<String> types2 = s2.getTypes();
        System.out.println("Sequence: " + s2.getSequence());
        System.out.println("Types: " + types2);
        assert types2.contains("Decreasing") : "Should contain Decreasing";
        assert types2.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types2.contains("Arithmetical") : "Should contain Arithmetical";
        assert types2.size() == 3 : "Should contain only Decreasing, Non-Increasing, Arithmetical";

        Sequence s3 = new Sequence(new ArrayList<>(Arrays.asList(5, 5, 5, 5, 5)));
        ArrayList<String> types3 = s3.getTypes();
        System.out.println("Sequence: " + s3.getSequence());
        System.out.println("Types: " + types3);
        assert types3.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types3.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types3.contains("Arithmetical") : "Should contain Arithmetical";
        assert types3.contains("Geometric") : "Should contain Geometric";
        assert types3.size() == 4 : "Should contain only Non-Increasing, Non-Decreasing, Arithmetical, Geometric";

        Sequence s4 = new Sequence(new ArrayList<>(Arrays.asList(2, 4, 8, 16)));
        ArrayList<String> types4 = s4.getTypes();
        System.out.println("Sequence: " + s4.getSequence());
        System.out.println("Types: " + types4);
        assert types4.contains("Increasing") : "Should contain Increasing";
        assert types4.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types4.contains("Geometric") : "Should contain Geometric";
        assert types4.size() == 3 : "Should contain only Increasing, Non-Decreasing, Geometric";

        Sequence s5 = new Sequence(new ArrayList<>(Arrays.asList(5, 4, 4, 2)));
        ArrayList<String> types5 = s5.getTypes();
        System.out.println("Sequence: " + s5.getSequence());
        System.out.println("Types: " + types5);
        assert types5.contains("Non-Increasing") : "Should contain Non-Increasing";
        assert types5.size() == 1 : "Should contain only Non-Increasing";

        Sequence s6 = new Sequence(new ArrayList<>(Arrays.asList(2, 4, 4, 5)));
        ArrayList<String> types6 = s6.getTypes();
        System.out.println("Sequence: " + s6.getSequence());
        System.out.println("Types: " + types6);
        assert types6.contains("Non-Decreasing") : "Should contain Non-Decreasing";
        assert types6.size() == 1 : "Should contain only Non-Decreasing";

        Sequence s7 = new Sequence(new ArrayList<>(List.of()));
        ArrayList<String> types8 = s7.getTypes();
        System.out.println("Empty sequence: " + s7.getSequence());
        System.out.println("Types: " + types8);
        assert types8.contains("None") : "Empty sequence should contain None";
        assert types8.size() == 1 : "Should contain only None";

        Sequence s8 = new Sequence(new ArrayList<>(List.of(42)));
        ArrayList<String> types9 = s8.getTypes();
        System.out.println("Single element sequence: " + s8.getSequence());
        System.out.println("Types: " + types9);
        assert types9.contains("None") : "Single element should contain None";
        assert types9.size() == 1 : "Should contain only None";
    }

    public static void testEquals() {
        System.out.println("Testing equals...");
        Sequence seq1 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3)));
        Sequence seq2 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 3)));
        Sequence seq3 = new Sequence(new ArrayList<>(Arrays.asList(1, 2, 4)));
        Sequence seq4 = new Sequence(new ArrayList<>(Arrays.asList(1, 2)));
        System.out.println("seq1: " + seq1.getSequence() + ", seq2: " + seq2.getSequence() + ", seq3: " + seq3.getSequence() + ", seq4: " + seq4.getSequence());
        System.out.println("seq1.equals(seq2): " + seq1.equals(seq2));
        System.out.println("seq1.equals(seq3): " + seq1.equals(seq3));
        System.out.println("seq1.equals(seq4): " + seq1.equals(seq4));
        assert seq1.equals(seq2) : "Sequences should be equal";
        assert !seq1.equals(seq3) : "Sequences should not be equal";
        assert !seq1.equals(seq4) : "Sequences should not be equal (length mismatch)";
    }

    public static void testGetSequence() {
        System.out.println("Testing getSequence...");
        ArrayList<Integer> initial = new ArrayList<>(Arrays.asList(1, 2, 3));
        Sequence seq = new Sequence(initial);
        System.out.println("Initial: " + initial + ", Sequence: " + seq.getSequence());
        assert seq.getSequence().equals(initial) : "Expected sequence to match initial list";
    }

    public static void testConstructors() {
        System.out.println("Testing constructors...");
        Sequence seqDefault = new Sequence();
        System.out.println("Default constructor sequence: " + seqDefault.getSequence());
        assert seqDefault.getSequence().isEmpty() : "Default constructor should create empty sequence";

        ArrayList<Integer> list = new ArrayList<>(Arrays.asList(7, 8, 9));
        Sequence seqParam = new Sequence(list);
        System.out.println("Parameterized constructor sequence: " + seqParam.getSequence());
        assert seqParam.getSequence().equals(list) : "Parameterized constructor should match input list";
    }

    public static void testAll() {
        testConstructors();
        testMinMax();
        testMinMaxIndices();
        testContains();
        testGetType();
        testEquals();
        testGetSequence();
    }

    public static void main(String[] args) {
        testAll();
        System.out.println("All tests passed!");
    }
}
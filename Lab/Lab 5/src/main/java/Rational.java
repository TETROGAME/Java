import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.regex.*;
public class Rational implements
        Comparable<Rational>,
        Comparator<Rational>,
        Iterable<Integer> {
    private int numerator;
    private int denominator;

    Rational(){
        this.numerator = 0;
        this.denominator = 1;
    }
    Rational(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }
        this.numerator = numerator;
        this.denominator = denominator;
    }
    Rational(String line){
        Pattern pattern = Pattern.compile("(\\d+)/(\\d+)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            this.numerator = Integer.parseInt(matcher.group(1));
            this.denominator = Integer.parseInt(matcher.group(2));
        }
        else {
            System.err.println("Failed to parse string into fraction, returning generic Rational object instead");
            this.numerator = 0;
            this.denominator = 1;
        }

    }

    public Rational add(Rational r) {
        int newDenominator = MathUtils.lcm(this.numerator, r.numerator);
        int newNumerator = this.numerator * (newDenominator / this.denominator)+
                            r.numerator * (newDenominator / r.denominator);
        return new Rational(newNumerator, newDenominator);
    }
    public Rational subtract(Rational r) {
        int newDenominator = MathUtils.lcm(this.numerator, r.numerator);
        int newNumerator = this.numerator * (newDenominator / this.denominator)-
                r.numerator * (newDenominator / r.denominator);
        return new Rational(newNumerator, newDenominator);
    }
    public Rational multiply(Rational r) {
        return new Rational(this.numerator * r.numerator, this.denominator * r.numerator);
    }
    public Rational divide(Rational r) {
        Rational inverse = new Rational(r.denominator, r.numerator);
        return this.multiply(inverse);
    }


    @Override
    public int compareTo(Rational o) {
        int left = this.numerator * o.denominator;
        int right = o.numerator * this.denominator;
        return Integer.compare(left, right);
    }
    @Override
    public int compare(Rational o1, Rational o2) {
        int left = o1.numerator * o2.denominator;
        int right = o2.numerator * o1.denominator;
        return Integer.compare(left, right);
    }

    @Override
    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < 2;
            }

            @Override
            public Integer next() {
                if (index == 0) {
                    index++;
                    return numerator;
                } else if (index == 1) {
                    index++;
                    return denominator;
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    @Override
    public String toString() {
        return this.numerator + "/" + this.denominator;
    }
}

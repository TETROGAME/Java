import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Rational implements
        Comparable<Rational>,
        Iterable<Integer>,
        Comparator<Rational> {
    private int numerator;
    private int denominator;

    Rational(){
        this.numerator = 0;
        this.denominator = 1;
    }
    Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
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
        return 0;
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
    public int compare(Rational o1, Rational o2) {
        return 0;
    }
}

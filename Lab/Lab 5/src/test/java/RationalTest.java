import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

class RationalTest {

    @Test
    void testDefaultConstructor() {
        Rational r = new Rational();
        Iterator<Integer> it = r.iterator();
        assertTrue(it.hasNext());
        assertEquals(0, it.next());
        assertTrue(it.hasNext());
        assertEquals(1, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testIntConstructorValid() {
        Rational r = new Rational(3, 4);
        assertEquals("3/4", r.toString());
    }

    @Test
    void testIntConstructorZeroDenominatorThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Rational(1, 0));
    }

    @Test
    void testStringConstructorValid() {
        Rational r = new Rational("15/7");
        assertEquals("15/7", r.toString());
    }

    @Test
    void testStringConstructorInvalid() {
        Rational r = new Rational("foo");
        assertEquals("0/1", r.toString());
    }

    @Test
    void testParseValid() {
        Rational r = Rational.parse("3/5");
        assertNotNull(r);
        assertEquals("3/5", r.toString());
    }

    @Test
    void testParseInvalid() {
        Rational r = Rational.parse("word");
        assertNull(r);
    }

    @Test
    void testAdd() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 3);
        Rational sum = r1.add(r2);
        assertEquals("5/6", sum.toString());
    }

    @Test
    void testSubtract() {
        Rational r1 = new Rational(3, 4);
        Rational r2 = new Rational(1, 4);
        Rational diff = r1.subtract(r2);
        assertEquals("2/4", diff.toString());
    }

    @Test
    void testMultiply() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(3, 4);
        Rational prod = r1.multiply(r2);
        assertEquals("6/12", prod.toString());
    }

    @Test
    void testDivide() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(3, 4);
        Rational quot = r1.divide(r2);
        assertEquals("8/9", quot.toString());
    }

    @Test
    void testCompareToEquals() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 2);
        assertEquals(0, r1.compareTo(r2));
    }

    @Test
    void testCompareToLessThan() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 3);
        assertTrue(r1.compareTo(r2) < 0);
    }

    @Test
    void testCompareToGreaterThan() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(1, 2);
        assertTrue(r1.compareTo(r2) > 0);
    }

    @Test
    void testComparatorCompareEquals() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(1, 2);
        Rational comparator = new Rational();
        assertEquals(0, comparator.compare(r1, r2));
    }

    @Test
    void testComparatorCompareLessThan() {
        Rational r1 = new Rational(1, 2);
        Rational r2 = new Rational(2, 3);
        Rational comparator = new Rational();
        assertTrue(comparator.compare(r1, r2) < 0);
    }

    @Test
    void testComparatorCompareGreaterThan() {
        Rational r1 = new Rational(2, 3);
        Rational r2 = new Rational(1, 2);
        Rational comparator = new Rational();
        assertTrue(comparator.compare(r1, r2) > 0);
    }

    @Test
    void testIterable() {
        Rational r = new Rational(7, 8);
        Iterator<Integer> it = r.iterator();
        assertTrue(it.hasNext());
        assertEquals(7, it.next());
        assertTrue(it.hasNext());
        assertEquals(8, it.next());
        assertFalse(it.hasNext());
    }

    @Test
    void testIteratorThrows() {
        Rational r = new Rational(1, 1);
        Iterator<Integer> it = r.iterator();
        it.next();
        it.next();
        assertThrows(NoSuchElementException.class, it::next);
    }
}
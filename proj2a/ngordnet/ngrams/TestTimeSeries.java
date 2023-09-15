package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TestTimeSeries {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        System.out.println(totalPopulation.data());
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertEquals(expectedYears, totalPopulation.years());

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertEquals(expectedTotal.get(i), totalPopulation.data().get(i), 1E-10);
        }
    }

    @Test
    public void randomizedTest() {
        TimeSeries t = new TimeSeries();
        TimeSeries u = new TimeSeries();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
//            int operationNumber = StdRandom.uniform(0, 4);
            int operationNumber = 3;
            if (operationNumber == 0) {
                // years
                int randKey = StdRandom.uniform(0, 100);
                double randVal = StdRandom.uniform(0, 100);
                t.put(randKey, randVal);
                assertEquals(new ArrayList<>(t.keySet()), t.years());
//                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // years
//                System.out.println("size: " + size);
            } else if (operationNumber == 2) {
                // plus
                int tKey = StdRandom.uniform(0, 100);
                System.out.println(tKey);
                double tVal = StdRandom.uniform(0, 100);
                t.put(tKey, tVal);
                int uKey = StdRandom.uniform(0, 100);
                double uVal = StdRandom.uniform(0, 100);
                u.put(uKey, uVal);
                TimeSeries added = t.plus(u);
                System.out.println("t values: " + t.data());
                System.out.println("u values: " + u.data());
                System.out.println(added.data());
            } else if (operationNumber == 3) {
                // divideBy
                // plus
                int tKey = StdRandom.uniform(0, 100);
                System.out.println(tKey);
                double tVal = StdRandom.uniform(1, 100);
                t.put(tKey, tVal);
                int uKey = StdRandom.uniform(0, 100);
                double uVal = StdRandom.uniform(1, 100);
                u.put(tKey, tVal);
                TimeSeries divided = t.dividedBy(u);
                System.out.println(t.data());
                System.out.println(u.data());
                System.out.println(divided.data());
//                System.out.println("getLast() return value: " + lastItem);
            }
        }
    }
    @Test
    public void test2ndConstructor() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(2021, 5000.0);
        catPopulation.put(2022, 1000.0);

        TimeSeries test = new TimeSeries(catPopulation, 1994, 2022);
        test.put(1900, -98.0);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1900, 1994, 2021, 2022));

        assertEquals(expectedYears, test.years());
    }
    @Test
    public void testPlus() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(2021, 5000.0);
        catPopulation.put(2022, 1000.0);

        TimeSeries test = new TimeSeries(catPopulation, 1994, 2022);
        System.out.println(catPopulation);
        System.out.println(test);
        System.out.println(test.plus(catPopulation));
    }
    @Test
    public void testDividedBy() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 5.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);
        catPopulation.put(2021, 5000.0);
        catPopulation.put(2022, 1000.0);

        TimeSeries test = new TimeSeries(catPopulation, 1994, 2022).dividedBy(catPopulation);
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1994, 2021, 2022));

        assertEquals(expectedYears, test.years());

        List<Double> expectedData = new ArrayList<>(Arrays.asList(1.0, 1.0, 1.0));

        assertEquals(expectedData, test.data());
    }
} 
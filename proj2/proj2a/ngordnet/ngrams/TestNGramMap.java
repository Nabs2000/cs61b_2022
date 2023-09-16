package ngordnet.ngrams;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Unit Tests for the NGramMap class.
 *
 * @author Josh Hug
 */
public class TestNGramMap {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(2005, 2006, 2007, 2008));
        List<Double> expectedCounts = new ArrayList<>
                (Arrays.asList(646179.0, 677820.0, 697645.0, 795265.0));

        TimeSeries request2005to2008 = ngm.countHistory("request");

        assertEquals(expectedYears, request2005to2008.years());

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertEquals(expectedCounts.get(i), request2005to2008.data().get(i), 1E-10);
        }

        expectedYears = new ArrayList<>
                (Arrays.asList(2006, 2007));
        expectedCounts = new ArrayList<>
                (Arrays.asList(677820.0, 697645.0));

        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertEquals(expectedYears, request2006to2007.years());

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertEquals(expectedCounts.get(i), request2006to2007.data().get(i), 1E-10);
        }
    }

    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap("./data/ngrams/top_14377_words.csv",
                "./data/ngrams/total_counts.csv");
        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertEquals(136497.0, fishCount.get(1865), 1E-10);
        assertEquals(444924.0, fishCount.get(1922), 1E-10);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertEquals(2563919231.0, totalCounts.get(1865), 1E-10);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertEquals(136497.0 / 2563919231.0, fishWeight.get(1865), 1E-7);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertEquals(75819.0, dogCount.get(1865), 1E-10);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertEquals(expectedFishPlusDogWeight1865, fishPlusDogWeight.get(1865), 1E-10);
    }

    @Test
    public void testConstructor() {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        System.out.println(ngm.countHistory("airport"));
    }

    @Test
    public void testOnSmallFile() {
        NGramMap ngm = new NGramMap("./data/ngrams/very_short.csv", "./data/ngrams/total_counts.csv");
        TimeSeries requestWeight = ngm.weightHistory("request");
        double requestNum = 646179.0 / 2.6609986084E10; // Test for year 2005
        ArrayList<String> test = new ArrayList<>();
        test.add("airport");
        test.add("request");
        TimeSeries airport = ngm.weightHistory("airport");
        TimeSeries request = ngm.weightHistory("request");
        TimeSeries summed = ngm.summedWeightHistory(test, 2005, 2008);
        TimeSeries test1 = new TimeSeries();
        test1.put(2000, 10.0);
        test1.put(2005, 10.0);
        test1.put(2010, 10.0);
        TimeSeries test2 = new TimeSeries();
        test2.put(2000, 5.0);
        test2.put(2005, 10.0);
        test2.put(2010, 15.0);
    }
}
package ngordnet.ngrams;

import java.util.*;
import java.util.Set;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {
    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // need to search for these nodes: startYear <= node.key <= endYear
        Set<Integer> keys = ts.keySet();
        for (int key : keys) {
            if (key >= startYear && key <= endYear) {
                put(key, ts.get(key));
            }
        }

    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        return new ArrayList<>(keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        return new ArrayList<>(values());
    }

    /**
     * Returns the yearwise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        Set<Integer> totalKeys = new TreeSet<>(keySet());
        totalKeys.addAll(ts.keySet()); // Now, the newly created TreeSet has all the keys
        for (int key : totalKeys) {
            if (ts.containsKey(key) && containsKey(key)) {
                newTS.put(key, Double.sum(ts.get(key), get(key)));
            } else {
                if (containsKey(key)) {
                    newTS.put(key, get(key));
                } else {
                    newTS.put(key, ts.get(key));
                }
            }
        }

        return newTS;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. If TS is missing a year that exists in this TimeSeries,
     * throw an IllegalArgumentException. If TS has a year that is not in this TimeSeries, ignore it.
     * Should return a new TimeSeries (does not modify this TimeSeries).
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        List<Integer> keys = years();
        for (int key : keys) {
            if (ts.containsKey(key)) {
                newTS.put(key, get(key) / ts.get(key));
            } else {
                throw new IllegalArgumentException("Key missing in this other TimeSeries.");
            }
        }
        return newTS;
    }
}

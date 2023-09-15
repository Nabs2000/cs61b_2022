package ngordnet.ngrams;

import edu.princeton.cs.algs4.In;

import java.util.*;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 * <p>
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {
    private final HashMap<String, TimeSeries> wordCounts;
    private final TimeSeries totCounts;

    public NGramMap(String wordsFilename, String countsFilename) {
        wordCounts = new HashMap<>();
        totCounts = new TimeSeries();
        In wordFile = new In(wordsFilename);
        In countFile = new In(countsFilename);
        TimeSeries wordTS;
        while (!wordFile.isEmpty()) {
            String word = wordFile.readString();
            if (!wordCounts.containsKey(word)) {
                wordTS = new TimeSeries();
            } else {
                wordTS = wordCounts.get(word);
            }
            int year = wordFile.readInt();
            int countsPerYr = wordFile.readInt();
            wordFile.readInt();
            wordTS.put(year, (double) countsPerYr);
            wordCounts.put(word, wordTS);
        }
        while (!countFile.isEmpty()) {
            String[] line = countFile.readLine().split(",");
            int year = Integer.parseInt(line[0]);
            double countsPerYr = Double.parseDouble(line[1]);
            totCounts.put(year, countsPerYr);
        }
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy,
     * not a link to this NGramMap's TimeSeries. In other words, changes made
     * to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word) {
        return (TimeSeries) wordCounts.get(word).clone();
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other words,
     * changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy".
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries clone = countHistory(word);
        return new TimeSeries(clone, startYear, endYear);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return (TimeSeries) totCounts.clone();
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to
     * all words recorded in that year.
     */
    public TimeSeries weightHistory(String word) {
        return countHistory(word).dividedBy(totalCountHistory());
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries clone = weightHistory(word);
        return new TimeSeries(clone, startYear, endYear);
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        Iterator<String> iter = words.iterator();
        TimeSeries addedWords = weightHistory(iter.next());
        while (iter.hasNext()) {
            addedWords = addedWords.plus(weightHistory(iter.next()));
        }
        return addedWords;
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS
     * between STARTYEAR and ENDYEAR, inclusive of both ends. If a word does not exist in
     * this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        Iterator<String> iter = words.iterator();
        TimeSeries addedWords = weightHistory(iter.next(), startYear, endYear);
        while (iter.hasNext()) {
            addedWords = addedWords.plus(weightHistory(iter.next(), startYear, endYear));
        }
        return addedWords;
    }
}

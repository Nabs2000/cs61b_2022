package ngordnet.main;

import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;

import java.util.*;

public class HyponymHelper {
    private NGramMap ngm;
    private WordnetParser wp;

    public HyponymHelper(NGramMap ngm, WordnetParser wp) {
        this.ngm = ngm;
        this.wp = wp;
    }
    public String returnHyps(List<String> words, int startYr, int endYr, int k) {
        Traverser t = new Traverser(wp);
        HashMap<String, Set<String>> allHyps = new HashMap<>();
        for (String word : words) {
            Set<String> wordHyps = t.getHyponyms(word);
            allHyps.put(word, wordHyps);
        }
        Collection<Set<String>> vals = allHyps.values();
        Iterator<Set<String>> iter = vals.iterator();
        Set<String> currHyps = iter.next();
        while (iter.hasNext()) { //
            Set<String> nextHyps = iter.next();
            currHyps.retainAll(nextHyps);
        }
        if (k == 0) { // Simply return the string if no k is provided
            return currHyps.toString();
        }
        // Otherwise, deal with k
        TreeMap<Double, String> countsPerWord = new TreeMap<>(Collections.reverseOrder());
        for (String word : currHyps) {
            countsPerWord.put(totalWordHistory(word, startYr, endYr), word);
        }
        Set<Double> zeroes = new TreeSet<>();
        zeroes.add(0.0);
        countsPerWord.keySet().removeAll(zeroes); // Remove all instances where value is 0
        Set<String> kHyps = new TreeSet<>(); // Initialize a treeset to hold the hyps
        int count = k;
        Iterator<String> kHypIter = countsPerWord.values().iterator(); // Iterator over words
        while (kHypIter.hasNext() && count > 0) { // Checks if there are items and if k > 0 is satisfied
            kHyps.add(kHypIter.next());
            count--;
        }
        return kHyps.toString();
    }
    public double sum(Collection<Double> nums) {
        double sum = 0.0;
        for (double num : nums) {
            sum += num;
        }
        return sum;
    }

    public double totalWordHistory(String word, int start, int end) {
        TimeSeries counts = ngm.countHistory(word, start, end);
        if ((counts.keySet() == null)) {
            return 0.0;
        }
        Collection<Double> sumCounts = counts.values();
        return sum(sumCounts);
    }
}


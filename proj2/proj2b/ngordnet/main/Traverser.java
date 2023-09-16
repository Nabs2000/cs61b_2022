package ngordnet.main;

import java.util.*;

public class Traverser {
    public WordnetParser wp;
    public DirectedGraph graph;

    public Traverser(WordnetParser wp) {
        this.wp = wp;
        graph = this.wp.getGraph();

    }

    public Set<String> getHyponyms(String word) {
        Set<String> hyps = new TreeSet<>();
//        Set<String> synsets = wp.getSynsets();
        // need to check if word does not exist over here
        // if !containsKey(word) then return new Set<String>();
//        if (!synsets.contains(word)) {
//            return hyps;
//        }
        Set<Integer> nodes = wp.getNodes2(word);
        if (nodes == null || nodes.isEmpty()) {
            return hyps;
        }
        for (int node : nodes) {
            getHyponyms(hyps, node);
        }
        return hyps;
    }

    private void getHyponyms(Set<String> hyps, int parentID) {
        // Base case: If there are no kids
        if (graph.get(parentID).size() == 0) {
            String parentWord = wp.intToWord(parentID);
            List<String> allWords = wp.findAllStrings(parentWord);
            hyps.addAll(allWords);
            return;
        }
        // Recursive case
        String parentWord = wp.intToWord(parentID);
        List<String> allWords = wp.findAllStrings(parentWord);
        hyps.addAll(allWords);
        List<Integer> childIDs = graph.get(parentID);
        for (int id : childIDs) {
            getHyponyms(hyps, id);
        }
    }
}

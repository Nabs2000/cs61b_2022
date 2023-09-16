package ngordnet.main;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.*;

public class WordnetParser {
    private DirectedGraph graph;
    private HashMap<Integer, String> synsetsGraph;
    private HashMap<String, Integer> wordToIntGraph;
    private HashMap<String, Set<Integer>> nodes;
    private Set<Integer> test2;

    public WordnetParser(String synsetFileName, String hypFileName) {
        graph = new DirectedGraph();
        synsetsGraph = new HashMap<>();
        wordToIntGraph = new HashMap<>();
        nodes = new HashMap<>();
        test2 = new HashSet<>();
        In synsetFile = new In(synsetFileName);
        In hypFile = new In(hypFileName);
        while (!synsetFile.isEmpty()) {
            String[] line = synsetFile.readLine().split(",");
            int synsetID = Integer.parseInt(line[0]);
            String synsetName = line[1];
            String[] wordNodes = synsetName.split(" ");
            synsetsGraph.put(synsetID, synsetName);
            wordToIntGraph.put(synsetName, synsetID);
            test2.add(synsetID);
            for (String word: wordNodes) {
                if (!nodes.containsKey(word)) {
                    nodes.put(word, new HashSet<>());
                }
                nodes.get(word).add(synsetID);
            }
        }
        while (!hypFile.isEmpty()) {
            List<String> line = List.of(hypFile.readLine().split(","));
            int mainWord = Integer.parseInt(line.get(0));
            for (int i = 1; i < line.size(); i++) {
                graph.addEdge(mainWord, Integer.parseInt(line.get(i)));
            }
        }
    }

    public String intToWord(int num) {
        return synsetsGraph.get(num);
    }

    public Set<String> getSynsets() {
        Set<String> allSynsets = new HashSet<>(nodes.keySet());
        Set<String> allWords = new HashSet<>();
        for (String word : allSynsets) {
            List<String> strings = findAllStrings(word);
            allWords.addAll(strings);
        }
        return allWords;
    }
    public int wordToInt(String word) {
        return wordToIntGraph.get(word);
    }
    public Set<Integer> getNodes2(String word) {
//        Set<Integer> nodes = new TreeSet<>();
//        for (int key : test2) {
//            String w = intToWord(key);
//            Set<String> temp = Set.of(w.split(" "));
//            if (temp.contains(word)) {
//                nodes.add(key);
//            }
//        }
//        return nodes;
        return nodes.get(word);
    }
    public Set<Integer> getNodesSet(String word) {
        Set<Integer> nodes = new TreeSet<>();
        for (int key : test2) {
            String w = intToWord(key);
            Set<String> temp = Set.of(w.split(" "));
            if (temp.contains(word)) {
                nodes.add(key);
            }
        }
        return nodes;
    }
    public Set<Integer> getNodesList(String word) {
        Set<Integer> nodes = new TreeSet<>();
        for (int key : test2) {
            String w = intToWord(key);
            List<String> temp = List.of(w.split(" "));
            if (temp.contains(word)) {
                nodes.add(key);
            }
        }
        return nodes;
    }

    public DirectedGraph getGraph() {
        return graph;
    }

    public List<String> findAllStrings(String word) {
        if (word.contains(" ")) {
            return List.of(word.split(" "));
        }
        return List.of(word);
    }
    public static void main(String[] args) {
        WordnetParser wp = new WordnetParser("./data/wordnet/synsets16.txt", "./data/wordnet/hyponyms16.txt");
        Traverser t = new Traverser(wp);
        DirectedGraph g = wp.getGraph();
        Set<Integer> nodes = wp.getNodes2("change");
        System.out.println(nodes);
//        Set<String> test = t.getHyponyms("mould");
//        Set<Integer> nodes = wp.getNodes("mould");
//        Set<Integer> nodes2 = wp.getNodes2("mould");
//        System.out.println(nodes);
//        System.out.println(nodes2);
//        Set<String> test2 = t.getHyponyms("mould");
//        System.out.println(test2);
    }
}


package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {
    private final NGramMap ngm;
    public HistoryTextHandler(NGramMap map) {
        ngm = map;
    }
    public String handle(NgordnetQuery q) {
        StringBuilder response = new StringBuilder();
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word: words) {
            response.append(word).append(": ");
            response.append(ngm.weightHistory(word, startYear, endYear).toString()).append("\n");
        }
        return response.toString();
    }
}

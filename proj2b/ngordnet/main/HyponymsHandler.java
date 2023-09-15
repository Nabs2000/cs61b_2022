package ngordnet.main;

import ngordnet.hugbrowsermagic.NgordnetQuery;
import ngordnet.hugbrowsermagic.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.*;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordnetParser wp;
    NGramMap ngm;

    public HyponymsHandler(WordnetParser wp, NGramMap ngm) {
        this.wp = wp;
        this.ngm = ngm;
    }

    public String handle(NgordnetQuery q) {
        int startYr = q.startYear();
        int endYr = q.endYear();
        int k = q.k();
        List<String> words = q.words();
        HyponymHelper hp = new HyponymHelper(ngm, wp);
        return hp.returnHyps(words, startYr, endYr, k);
    }
}

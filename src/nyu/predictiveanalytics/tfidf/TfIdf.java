package nyu.predictiveanalytics.tfidf;

import java.util.ArrayList;


public class TfIdf {
	public double tfCalculator(String[] totalterms, String termToCheck) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        return count / totalterms.length;
    }

    /**
     * Calculates idf of term termToCheck
     * @param docArray : all the terms of all the documents
     * @param termToCheck
     * @return idf(inverse document frequency) score
     */
    public double idfCalculator(ArrayList<String[]> docArray, String termToCheck) {
        double count = 0;
        for (String[] ss : docArray) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        //return 1 + Math.log(docArray.size() / count);
        return Math.log(docArray.size() / count);
    }
}

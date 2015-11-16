package nyu.predictiveanalytics.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.security.AllPermission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nyu.predictiveanalytics.preprocess.StopWordsCleaner;

public class DocParser {
	
    private ArrayList<String[]> docsArray = new ArrayList<>();//contain all words without remove the duplicated
    //private ArrayList<String[]> termsDocsCleanedArray = new ArrayList<>();//contain all words without remove the duplicated
    private ArrayList<double[]> wordCountArray = new ArrayList<>(); // word count array after do steaming and remove stop words 
    
    private ArrayList<String> allTerms = new ArrayList<>(); //to hold all terms
    private ArrayList<String> allTermsCleanedArray; // terms array after do steaming and remove stop words 
    private ArrayList<double[]> tfidfMatrix = new ArrayList<>();

    private String pathToStopWordsFile;
    private String docsFolderPath;
    private ArrayList<String> stopwordsVector = new ArrayList<>();; 
    
    public DocParser(String dfPath) {
    	this(dfPath, "./stop-word-list.txt");
    }
    
    public DocParser(String dfPath, String stwPath) {
    	docsFolderPath = dfPath;
    	pathToStopWordsFile = stwPath;
    	parseFiles();
    }
   
    /**
     * Method to read files and store in array.
     * @param docsFolderPath : source file path
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void parseFiles() {
		try {
			File[] allfiles = new File(docsFolderPath).listFiles();
			BufferedReader in = null;
			for (File f : allfiles) {
				if (f.getName().endsWith(".txt")) {

					in = new BufferedReader(new FileReader(f));

					StringBuilder sb = new StringBuilder();
					String s = null;
					while ((s = in.readLine()) != null) {
						sb.append(s);
					}
					// get every single term
					String[] tokenizedTerms = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");
					for (String term : tokenizedTerms) {
						// don't include duplicated entry
						if (!allTerms.contains(term)) {
							allTerms.add(term);
						}
					}
					docsArray.add(tokenizedTerms);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        initAllTermsCleanedArray();
        initDocsWordCountArray();
        
    }
    
    private void initAllTermsCleanedArray(){
    	//=========== Get a clean word list =============
    	allTermsCleanedArray = new ArrayList<String>(allTerms);
    	removeStopWords(allTermsCleanedArray);
    	// TODO: stemming
    }
    
    private void initDocsWordCountArray(){
    	for (int i = 0; i < docsArray.size(); i++) {
    		double[] initWordCount = new double[allTermsCleanedArray.size()];    		
    		wordCountArray.add(initWordCount);
        }
    	
		doWordCount();
		/*System.out.print("============= Word Count ==============\n");
    	for (int i = 0; i < docsArray.size(); i++) {
			double[] wordCount = wordCountArray.get(i);
			System.out.print("Dimension =" + wordCount.length + "\n[");
    		for(double d: wordCount){
    			System.out.print(d + ", ");
    		}
			System.out.print("] \n");
        }*/
    	
    }


    private void doWordCount() {
    	int index = -1;
        for (int i = 0; i < docsArray.size(); i++) {
        	String[] docWords = docsArray.get(i);
        	for(String word : docWords){
        		index = allTermsCleanedArray.indexOf(word);
        		if (index != -1){
        			(wordCountArray.get(i))[index] += 1;
        		}
        	}
        	
        }
    }
    
    private void removeStopWords(ArrayList<String> wordVector){
    	readStopWordsList();
		// ========= Do cleaning, construct result vector =============
    	//Arrays.asList(wordVector);
    	//String[] tempStr = wordVector.toArray(new String[0]);
    	//ArrayList<String> tempAllWord = new ArrayList<String>(Arrays.asList(tempStr));
    	ArrayList<String> tempAllWord = new ArrayList<String>(wordVector);
		for (String word : tempAllWord) {
			if (stopwordsVector.contains(word)) {
				wordVector.remove(word);
			}
		}
    }
    
    /**
     * Read Stop words list from file
     */
    private void readStopWordsList(){
		try {
			FileReader fr = new FileReader(pathToStopWordsFile);
			BufferedReader br = new BufferedReader(fr);
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				if (!stopwordsVector.contains(sCurrentLine)) {
					stopwordsVector.add(sCurrentLine);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

    /**
     * Method to create termVector according to its tfidf score.
     */
    public ArrayList<double[]> tfIdfCalculator() {
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term requency inverse document frequency        
        for (String[] doc : docsArray) {
            double[] tfidfvectors = new double[allTermsCleanedArray.size()];
            int count = 0;
            for (String terms : allTermsCleanedArray) {
                tf = new TfIdf().tfCalculator(doc, terms);
                idf = new TfIdf().idfCalculator(docsArray, terms);
                tfidf = tf * idf;
                tfidfvectors[count] = tfidf;
                count++;
            }
            tfidfMatrix.add(tfidfvectors);  //storing document vectors;            
        }
        return tfidfMatrix;
    }
    
    public static void main(String[] args){
    	String docPath = "C:\\Users\\DC-IT-Dev\\Desktop\\NYU2015FALL-Course-Material\\PA\\homework-2\\test-article";
    	DocParser docParser = new DocParser(docPath);
    	docParser.tfIdfCalculator();
		
		System.out.print("============= TF-IDF ==============\n");
		for(int i = 0; i < docParser.tfidfMatrix.size(); i++){
			double[] v = docParser.tfidfMatrix.get(i);
			System.out.print("Dimension = " + v.length + "\n");
			System.out.print("[");
			for(double d : v){
				System.out.print(d + ", ");
			}
			System.out.print("] \n");
		}
    }
}

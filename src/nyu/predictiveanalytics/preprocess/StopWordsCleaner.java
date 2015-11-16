package nyu.predictiveanalytics.preprocess;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class StopWordsCleaner {
	
	private ArrayList<String> stopwordsVector;
	
	public ArrayList<String> doCleaning(ArrayList<String> wordVector, String pathToStopWordsFile){
		ArrayList<String> resultVector = new ArrayList<String>();

		//========= Read Stop words from file =============
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

		// ========= Do cleaning, construct result vector =============
		for (String word : wordVector) {
			if (!stopwordsVector.contains(word) && !resultVector.contains(word)) {
				resultVector.add(word);
			}
		}

		return resultVector;
	}

}

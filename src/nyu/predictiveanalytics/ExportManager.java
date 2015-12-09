package nyu.predictiveanalytics;

import java.io.FileWriter;
import java.io.IOException;

public class ExportManager {

	public ExportManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void exportToCsv(Entity[] array_entity) {
		exportToCsv(array_entity, "export-data.csv");
	}
	
	public static void exportToCsv(Entity[] array_entity, String strFileName) {
		try {
			FileWriter writer = new FileWriter(strFileName, false); // clear the data in this file
			
			for (int i = 0; i < array_entity.length; i++) {
				if (i == 0) {
					for (int j = 0; j < array_entity[i].m_featureDimension; j++) {
						if (j == 0)
							writer.append("F" + j);
						else
							writer.append(",F" + j);
					}
					writer.append('\n');
				}
				for (int j = 0; j < array_entity[i].m_featureDimension; j++) {
					if (j == 0) 
						writer.append("" + array_entity[i].m_features[j]);
					else 
						writer.append("," + array_entity[i].m_features[j]);
				}
				writer.append('\n');
			}			
			// generate whatever data you want

			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

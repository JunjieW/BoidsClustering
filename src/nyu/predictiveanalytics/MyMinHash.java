package nyu.predictiveanalytics;

public class MyMinHash implements DistanceMeasure{
	private static int DEFAULT_NUM_OF_HASH_FUNCTIONS = 5;
	
	private boolean useDefaultHashFunctions;
	
	private int m_numOfHashFunc;
	private HashFunction[] m_arrayHashFunctions;
	
	private int[][] m_minHashSignatureTable; // m_minHashTable[#entitis = 2][#hash functions]
	private int[][] m_dimentionHashTable; // m_dimentionHashTable[#dimension][#hash functions]
	
	private int m_numOfdimenstions;
	
	public interface HashFunction{
		public int doHash(int x);
	};
	
	public MyMinHash(){
		useDefaultHashFunctions = true;
		m_numOfHashFunc = DEFAULT_NUM_OF_HASH_FUNCTIONS;
		setDefaultHashFunctions();		
	}
	
	public MyMinHash(HashFunction[] hashFunctions){
		useDefaultHashFunctions = false;		
		m_numOfHashFunc = hashFunctions.length;
		m_arrayHashFunctions = hashFunctions;
	}
	
	// Set Default hash functions
	private void setDefaultHashFunctions(){	
		/*
		 * hash_function = (a*x + b) % dimensions
		 */
		this.m_arrayHashFunctions = new HashFunction[] {
				new HashFunction() { 
					public int doHash(int x) { return (13*x + 17) % m_numOfdimenstions;}},
				new HashFunction() { 
					public int doHash(int x) { return (12*x + 15) % m_numOfdimenstions;}},
				new HashFunction() { 
					public int doHash(int x) { return (8*x + 1) % m_numOfdimenstions;}},
				new HashFunction() {
					public int doHash(int x) { return (14*x + 2) % m_numOfdimenstions;}}, 
				new HashFunction() {
					public int doHash(int x) { return (4*x + 6) % m_numOfdimenstions;}}
			};
		
		
	}

	// Set user-defined hash functions and get functions count
	public void setUserDifinedHashFunctions(HashFunction[] hashFunc){
		//TODO
		
	}
	
	private void initMinHashSignatureTable() {
		m_minHashSignatureTable = new int[2][m_numOfHashFunc];
        for (int i = 0; i < 2; i++) {
        	for (int j = 0; j < m_numOfHashFunc; j++) {
        		m_minHashSignatureTable[i][j] = Integer.MAX_VALUE;
            }
        }
    }
	
	private void computeDimensionHashTable() {
;		m_dimentionHashTable = new int[m_numOfdimenstions][m_numOfHashFunc];
		for(int i = 0; i < m_numOfdimenstions; i++) {
			for (int j = 0; j < m_numOfHashFunc; j++){ 
				m_dimentionHashTable[i][j] = this.m_arrayHashFunctions[j].doHash(i);
			}
		}
	}
	
    private void calculateMinHash(Entity entity, int entityIndex){
    	for(int i = 0; i < m_numOfdimenstions; i++) { // for every dimension of the entity
    		// if the enity's i-th dimension is not zero
    		if( entity.m_features[i] > 0) { 
    			for (int hash_fun_j = 0; hash_fun_j < m_numOfHashFunc; hash_fun_j++){ // for every hash function
    				int h_value = m_dimentionHashTable[i][hash_fun_j]; // get the hash index 
    				if (h_value < m_minHashSignatureTable[entityIndex][hash_fun_j]) { 
    					// use the minimun as hash value for i-
    					m_minHashSignatureTable[entityIndex][hash_fun_j] = h_value;
    				}
    			}
    		}
    	}
    }
    
    private void setNumOfdimenstions(int d){
    	m_numOfdimenstions = d;
    }
	
    public double computeSimilarity(Entity entity_x, Entity entity_y){
    	setNumOfdimenstions(entity_x.m_featureDimension);
        initMinHashSignatureTable();
        computeDimensionHashTable();
        
        calculateMinHash(entity_x, 0);
        calculateMinHash(entity_y, 1);

        int identicalMinHashes = 0;
        for (int i = 0; i < m_numOfHashFunc ; i++){
            if (m_minHashSignatureTable[0][i] == m_minHashSignatureTable[1][i]) {
                identicalMinHashes++;
            }
        }
        
        return (1.0 * identicalMinHashes) / m_numOfHashFunc;
    }
	

    @Override
	public double computeDistance(Entity entity_x, Entity entity_y) {
    	if( entity_x.m_featureDimension != entity_y.m_featureDimension)
    		throw new RuntimeException("[MyMinHash:computeDistance()] Entity_x and entity_y Should have same diemensions");

    	double distance = -1;
    	double similarity = computeSimilarity(entity_x, entity_y);
    	distance = (double)1 - computeSimilarity(entity_x, entity_y);
    	return distance;
	}
}

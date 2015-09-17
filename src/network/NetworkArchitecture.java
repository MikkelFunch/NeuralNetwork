package network;

public class NetworkArchitecture {
	//Neurons at [layer]
	private final int[] NEURONS;
	//Amount of layers
	private final int LAYERS;
	//Neurons in each [layer]
	//private final int[] NEURONS_IN_LAYER;
	
	//private final int TOTAL_NEURONS;
	
	private int totalSynapses;
	
	public NetworkArchitecture(int[] n, int l){
		assert n.length == l;
		
		NEURONS = n;
		LAYERS = l;
		//TOTAL_NEURONS = getTotalNeurons(n);
		
		totalSynapses = countSynapses();
	}
	


	public NetworkArchitecture(int ... n ){
		//Call another constructor instead
		LAYERS = n.length;
		NEURONS = n;
		
		//TOTAL_NEURONS = getTotalNeurons(n);
		
		totalSynapses = countSynapses();
	}
	
	private int countSynapses() {
		int result = 0;
		
		int lastLayersNeurons = NEURONS[0];
		
		
		for (int i = 1; i < NEURONS.length; i++) {//For each layer
			result += NEURONS[i] * lastLayersNeurons;
			lastLayersNeurons = NEURONS[i];
		}
		
		return result;
	}
	
	/*private int getTotalNeurons(int[] neuronsPrLayer){
		int total = 0;
		for (int i = 0; i < neuronsPrLayer.length; i++) {
			total += neuronsPrLayer[i];
		}
		return total;
	}*/

	public int getLayers() {
		return LAYERS;
	}
	
	public int[] getNeurons(){
		return NEURONS;
	}

	public int getNeruonsBeforeLayer(int l){
		int r = 0;
		for (int i = 0; i < l; i++) {
			r += NEURONS[i];
		}
		return r;
	}

	public int getNeuronsInLayer(int l) {
		return NEURONS[l];
	}

	public int getTotalSynapses() {
		return totalSynapses;
	}



	public int getAmountOfInputNeurons() {
		return getNeuronsInLayer(0);
	}
}

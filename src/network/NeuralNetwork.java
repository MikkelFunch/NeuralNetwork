package network;

import java.util.ArrayList;
import java.util.List;

public class NeuralNetwork {
	private NeuronLayer[] layers;
	private Genome genome;
	private NetworkArchitecture architecture;
	private List<Double> inputs;
	private double fitness;
	
	public NeuralNetwork(Genome g, NetworkArchitecture a){
		layers = new NeuronLayer[a.getLayers()];
		genome = g;
		architecture = a;
		inputs = new ArrayList<Double>(a.getAmountOfInputNeurons());
		
		createNetwork();
	}
	
	
	private void createNetwork(){
		//Create layers
		NeuronLayer currentLayer;
		NeuronLayer lastLayer = null;
		
		int synapseNumber = 0;
		
		//For each layer
		for(int i = 0; i < layers.length; i++){
			currentLayer = new NeuronLayer();
			currentLayer.setWeights(genome.getLayeredWeights(i));
			for(int j = 0; j < architecture.getNeurons()[i]; j++){
				Neuron currentNeuron = new Neuron();
				currentNeuron.setBias(genome.getBias(i,j));
				currentNeuron.setLayer(i);
				currentLayer.addNeuron(currentNeuron);
				
				//Create synapses
				if(i != 0){ //any layer but the first
					for(Neuron lastNeuron: lastLayer.getNeurons()){
						Synapse s = new Synapse(currentNeuron, lastNeuron, genome.getWeight(synapseNumber), synapseNumber);
						synapseNumber++;
						lastNeuron.addOutputSynapse(s);
						currentNeuron.addInputSynapse(s);
					}
				}
			}
			
			layers[i] = currentLayer;
			lastLayer = currentLayer;
		}
	}
	
	/**
	 * Compute the network's outputs
	 * @param inputs
	 * @return a list of all the outputs
	 */
	public List<Double> computeOutput(List<Double> inputs) {
		List<Neuron> inputsNeurons = getInputNeurons();
		
		for (int i = 0; i < layers[0].getSize(); i++) {
			inputsNeurons.get(i).inputIntoSynapses(inputsNeurons.get(i).activateNeuron(inputs.get(i)));
		}
		for (int i = 1; i < layers.length; i++) { //-1?
			for(Neuron n: layers[i].getNeurons()) {
				n.output();
			}
		}
		List<Double> result = new ArrayList<Double>();
		for (Neuron n : getOutputNeurons()) {
			result.add(n.getOutput());
		}
		//System.out.println(result);
		return result;
	}
	
	public List<Double> computeOutput() {
		return computeOutput(inputs);
	}

	private List<Neuron> getInputNeurons() {
		return layers[0].getNeurons();
	}
	
	private List<Neuron> getOutputNeurons() {
		return layers[architecture.getLayers() - 1].getNeurons();
	} 
	
	public NeuronLayer[] getNeuronLayers(){
		return layers;
	}

	public void setNeuronLayers(NeuronLayer[] l){
		layers = l;
	}

	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double f){
		fitness = f;
	}

	public Genome getGenome() {
		return genome;
	}


	public NetworkArchitecture getNetworkArchitecture() {
		return architecture;
	}


	public List<Double> getInputs() {
		return inputs;
	}


	public void addInput(List<Double> in) {
		inputs.addAll(in);
	}


	public void addInput(double i) {
		inputs.add(i);
	}


	public void clearInputs() {
		inputs.clear();
	}
	
	public String toString(){
		return "Fitness: " + fitness;
		
	}
}

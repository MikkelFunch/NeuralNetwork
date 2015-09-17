package network;

import java.util.ArrayList;
import java.util.List;

public class NeuronLayer {
	private List<Neuron> neurons;
	private double[] weights;
	
	public NeuronLayer(){
		neurons = new ArrayList<Neuron>();
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public void addNeuron(Neuron n) {
		neurons.add(n);
	}
	
	public void setWeights(double[] w){
		weights = w;
	}

	public int getSize() {
		return neurons.size();
	}
}


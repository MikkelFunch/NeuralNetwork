package network;

public class Synapse {
	private final Neuron START_NEURON;
	private final Neuron END_NEURON;
	private double weight;
	private final int NUMBER;
	private double input;

	public Synapse(Neuron currentNeuron, Neuron lastNeuron, double w, int n) {
		START_NEURON = lastNeuron;
		END_NEURON = currentNeuron;
		weight = w;
		NUMBER = n;
	}

	public void input(double i) {
		input = i;
	}

	public double output() {
		return input * weight;
	}

	public void setWeight(double w) {
		weight = w;
	}

}

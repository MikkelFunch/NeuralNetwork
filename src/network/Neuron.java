package network;

import java.util.ArrayList;
import java.util.List;

import util.MathTool;

public class Neuron {
	private double bias;
	private int layer;
	private List<Synapse> inputSynapses;
	private List<Synapse> outputSynapses;
	
	public Neuron(){
		inputSynapses = new ArrayList<Synapse>();
		outputSynapses = new ArrayList<Synapse>();
	}
	
	public void setBias(double b) {
		bias = b;
	}

	public void setLayer(int l) {
		layer = l;
	}

	public void addInputSynapse(Synapse s) {
		inputSynapses.add(s);		
	}
	
	public void addOutputSynapse(Synapse s) {
		outputSynapses.add(s);		
	}

	/**
	 * Calculates the sigma of the neuron
	 * @param input
	 * @return
	 */
	public double activateNeuron(Double input) {
		double test = MathTool.sigma(input + bias);
		
		/*
0.7685247834
0.7077047141
0.7013746376
		 * 
		 */
		
		
		return MathTool.sigma(input + bias);
	}

	/**
	 * Sends the neurons output into all the synapses
	 * @param input
	 */
	public void inputIntoSynapses(double input) {
		for(Synapse s: outputSynapses) {
			s.input(input);
		}
	}

	/**
	 * collects input and activates it, making it output to synapses
	 */
	public void output() {
		inputIntoSynapses(getOutput());
	}

	/**
	 * Calculates the value of all the input synapses
	 * @return
	 */
	private double getRawInputValue() {
		double result = 0;
		for(Synapse s: inputSynapses) {
			result += s.output();
		}
		return result;
	}

	/**
	 * Returns the neuron's output
	 * @return the neuron's output
	 */
	public Double getOutput() {
		return activateNeuron(getRawInputValue());
	}

	public List<Synapse> getOutputSynapses() {
		return outputSynapses;
	}

}

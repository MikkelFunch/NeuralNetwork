package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.Assert;
import network.NetworkArchitecture;
import network.Genome;
import network.NeuralNetwork;

public class NeuralNetworkTest {

	
	@Test
	public void testNeuralNetwork(){
		NetworkArchitecture e = new NetworkArchitecture(2, 2, 1);
		Genome g = new Genome(e, 0.1, 0.1);
		NeuralNetwork n = new NeuralNetwork(g, e);
		

	List<Double> inputs = new ArrayList<Double>();
		inputs.add(2.0);
		inputs.add(-1.0);
		List<Double> outputs = n.computeOutput(inputs);
		
		System.out.println(outputs.get(0));
	}
	
	@Test
	public void testNeuralNetwork2(){
		NetworkArchitecture e = new NetworkArchitecture(2, 2, 1);
		Genome g = new Genome(e, 0.1, 0.1);
		NeuralNetwork n = new NeuralNetwork(g, e);
		n.getNeuronLayers()[0].getNeurons().get(0).setBias(0.2);
		

		List<Double> inputs = new ArrayList<Double>();
		inputs.add(2.0);
		inputs.add(-1.0);
		List<Double> outputs = n.computeOutput(inputs);
		
		System.out.println(outputs.get(0));
	}
	
	@Test
	public void testNeuralNetwork3() {
		NetworkArchitecture e = new NetworkArchitecture(2, 2, 1);
		Genome g = new Genome(e, 0.1, 0.1);
		NeuralNetwork n = new NeuralNetwork(g, e);
		n.getNeuronLayers()[0].getNeurons().get(0).setBias(0.2);
		n.getNeuronLayers()[0].getNeurons().get(1).setBias(0.9);
		n.getNeuronLayers()[0].getNeurons().get(0).getOutputSynapses().get(0).setWeight(0.7);
	

		List<Double> inputs = new ArrayList<Double>();
		inputs.add(2.0);
		inputs.add(-1.0);
		List<Double> outputs = n.computeOutput(inputs);
		
		System.out.println(outputs.get(0));
	}
	
	/*
	@Test
	public void testNeuralNetwork() {
		fail("Not yet implemented");
	}

	@Test
	public void testCreateNetwork() {
		fail("Not yet implemented");
	}
	*/
}

package evolution;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.genetics.Population;

import network.Genome;
import network.NetworkArchitecture;
import network.NeuralNetwork;

public class Generation {
	private List<NeuralNetwork> networks;
	private int id;
	private int number;
	private NetworkArchitecture architecture;
	private int population;
	
	public Generation(){
		networks = new ArrayList<NeuralNetwork>();
	}
	public Generation(Generation g){
		architecture = g.getNetworkArchitecture();
		population = g.getPopulation();
		networks = new ArrayList<NeuralNetwork>();
	}
	
	public Generation(int i, int n, NetworkArchitecture na, int p){
		id = i;
		number = n;
		architecture = na;
		population = p;
		networks = new ArrayList<NeuralNetwork>();
	}
	
	public Generation(List<NeuralNetwork> nl) {
		networks = nl;
	}
	
	public void addNetwork(NeuralNetwork n){
		//DEBUG
		assert n != null;
		//
		
		networks.add(n);
	}
	
	public List<NeuralNetwork> getNetworks(){
		return networks;
	}

	public int getId() {
		return id;
	}
	
	public int getNumber(){
		return number;
	}
	
	public NetworkArchitecture getNetworkArchitecture(){
		return architecture;
	}
	
	public void setArchitecture(NetworkArchitecture na){
		architecture = na;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int p) {
		population = p;
	}

	public void addNeuralNetworks(List<NeuralNetwork> makeChildren) {
		//DEBUG
		for (NeuralNetwork n : makeChildren) {
			assert n != null;
		}
		//
		networks.addAll(makeChildren);
	}

	public void generatePopulation(NetworkArchitecture e, int populationSize) {
		architecture = e;
		population = populationSize;
		
		for (int i = 0; i < population; i++) {
			Genome g = new Genome(architecture);
			addNetwork(new NeuralNetwork(g, architecture));
		}
	}
	public int getCurrentPopulation() {
		return networks.size();
	}
}

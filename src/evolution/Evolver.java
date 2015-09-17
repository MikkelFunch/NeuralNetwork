package evolution;

import io.IoHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;
import java.util.PrimitiveIterator.OfDouble;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import util.MathTool;
import network.Genome;
import network.NetworkArchitecture;
import network.NeuralNetwork;

public class Evolver {
	private static int saveInterval;
	private static int numberOfElitists;
	private static double mutationChance;
	//private static int amountOfParents;
	private static int numberOfChildren;
	private static double mutationIntensity;
	
	static int saveNumber = 0;
	
	public static void setDefaultParameters(){
		saveInterval = 10;
		//numberOfElitists = 10;
		numberOfElitists = 10;
		mutationChance = 0.1;
		mutationIntensity = 0.2;
		//amountOfParents = 2;
		numberOfChildren = 3;
	}
	
	/**
	 * Mutates all networks
	 * @param g
	 * @return
	 */
	public static Generation evolveGenerationFAST(Generation g){
		Generation newGen = new Generation(g);
		for (NeuralNetwork n : g.getNetworks()) {
			Genome genome = n.getGenome().clone();
			genome.mutate(mutationChance, mutationIntensity);
			
			NeuralNetwork newNetwork = new NeuralNetwork(genome, g.getNetworkArchitecture());
			newGen.addNetwork(newNetwork);
		}
		
		return newGen;
	}
	
	public static Generation evolveGeneration(Generation oldGen){
		NetworkArchitecture architecture = oldGen.getNetworkArchitecture();
		
		TreeSet<NeuralNetwork> networksSorter =  new TreeSet<NeuralNetwork>(new Comparator<NeuralNetwork>() {
			@Override
			public int compare(NeuralNetwork o1, NeuralNetwork o2) {
				return o1.getFitness() < o2.getFitness() ? 1 : -1;
			}
		});
		networksSorter.addAll(oldGen.getNetworks());
		List<NeuralNetwork> sortedNetworks = new ArrayList<NeuralNetwork>(networksSorter);
		//NeuralNetwork[] sortedArray = networksSorter.toArray(new NeuralNetwork[0]);
		System.out.println("1 in old: " + sortedNetworks.get(0).getFitness());
		System.out.println("2 in old: " + sortedNetworks.get(1).getFitness());
		System.out.println("3 in old: " + sortedNetworks.get(2).getFitness());
		System.out.println("4 in old: " + sortedNetworks.get(3).getFitness());
		
		if(sortedNetworks.get(0).getFitness() > 9000){
			sortedNetworks.get(0).getGenome().setNumber(saveNumber);
			IoHandler.saveGenome(sortedNetworks.get(0).getGenome());
			saveNumber++;
		}
		Generation newGen = new Generation(oldGen.getId() + 1, oldGen.getNumber() + 1, oldGen.getNetworkArchitecture(), oldGen.getPopulation());
		
		// WORKING MUTATE
		Random rand = new Random();
		int index = 0;
		while(newGen.getCurrentPopulation() < oldGen.getPopulation() - numberOfElitists){
			Genome genome = sortedNetworks.get(index).getGenome().clone();
			genome.mutate(mutationChance, mutationIntensity);
			NeuralNetwork n = new NeuralNetwork(genome, architecture);
			newGen.addNetwork(n);
			index++;
		}
		
		
		
		//Mutate and crossover WORKING
		/*int index = 0;
		while(newGen.getCurrentPopulation() < oldGen.getPopulation() - numberOfElitists - numberOfChildren){
			Genome genome = sortedNetworks.get(index).getGenome().clone();
			genome.mutate(mutationChance, mutationIntensity);
			NeuralNetwork n = new NeuralNetwork(genome, architecture);
			newGen.addNetwork(n);
			
			newGen.addNeuralNetworks(makeChildren(sortedNetworks.get(index), sortedNetworks.get(index+1)));
			
			
			index++;
		}
		*/
		
		//Working adding elitists
		for (int i = 0; i < numberOfElitists + oldGen.getPopulation() - newGen.getNetworks().size(); i++) {
			NeuralNetwork n = new NeuralNetwork(sortedNetworks.get(i).getGenome().clone(), architecture);
			newGen.addNetwork(n);
		}

			/////////////
			/*
			List<NeuralNetwork> parents = new ArrayList<NeuralNetwork>(amountOfParents);
			for (int j = 0; j < childrenPrNetwork; j++) {
				for (int h = i + 1; h < amountOfParents; h++) {
					parents.add(sortedNetworks.get(i+h));
				}
				newGen.addNeuralNetworks(makeChildren(parents));
				created += childrenPrNetwork;
				parents.clear();
			}
			
			
		}
		*/
		
		
		//sort for fitness
		//decide how many should directly pass
		//decide parents
		//breed new networks
		//decide mutations
		//Add mutation later, when copying is figured out
		
		//DEBUG
		for (NeuralNetwork n : newGen.getNetworks()) {
			assert n != null;
		}
		assert newGen.getCurrentPopulation() == oldGen.getPopulation();
		//
		return newGen;
		
	}
	
	/**
	 * Uses uniform crossover
	 * @param parents
	 * @return
	 */
	private static List<NeuralNetwork> makeChildren(NeuralNetwork p1, NeuralNetwork p2) {
		double[] p1Weights = p1.getGenome().getWeight();
		double[][] p1Bias = p1.getGenome().getBias();
		double[] p2Weights = p2.getGenome().getWeight();
		double[][] p2Bias = p2.getGenome().getBias();
		
		List<Genome> genomes = new ArrayList<Genome>(numberOfChildren);
		for (int i = 0; i < numberOfChildren; i++) {
			Genome g = new Genome(p1.getNetworkArchitecture());
			genomes.add(g);
		}
		
		assert p1.getNetworkArchitecture().getTotalSynapses() == p1Weights.length;
		
		Random rand = new Random();
		for (int i = 0; i < p1.getNetworkArchitecture().getTotalSynapses(); i++) {
			if(rand.nextDouble() < 0.5){
				genomes.get(0).setWeight(i, p1Weights[i]);
				genomes.get(1).setWeight(i, p2Weights[i]);
			} else {
				genomes.get(1).setWeight(i, p1Weights[i]);
				genomes.get(0).setWeight(i, p2Weights[i]);
			}
			genomes.get(2).setWeight(i, (p1Weights[i] + p2Weights[i]) / 2);
		}
		
		for (int i = 0; i < p1Bias.length; i++) {
			for (int j = 0; j < p1Bias[i].length; j++) {
				if(rand.nextDouble() < 0.5){
					genomes.get(0).setBias(i, j, p1Bias[i][j]);
					genomes.get(1).setBias(i, j, p2Bias[i][j]);
				} else {
					genomes.get(1).setBias(i, j, p1Bias[i][j]);
					genomes.get(0).setBias(i, j, p2Bias[i][j]);
				}
				genomes.get(2).setWeight(i, (p1Weights[i] + p2Weights[i]) / 2);
			}
		}
		
		List<NeuralNetwork> children = new ArrayList<NeuralNetwork>(numberOfChildren);
		for (int i = 0; i < numberOfChildren; i++) {
			NeuralNetwork n = new NeuralNetwork(genomes.get(i), p1.getNetworkArchitecture());
			children.add(n);
		}
		return children;
	}
}




/**
 * Make evolution
 * -> The evolver is used when the fitness IS found, so do not think about how the game is simulated and all that
 * -> Make parameters, standard parameters, but simple! Can always be extended.
 * -> Just get something done, make it work. It can always be extended
 * -> AND save every x generation can always be added
 * 
 * Make a controller
 * -> It should give inputs to the current neuralnetwork playing, add later how many times a network should play
 * -> Add the fitness
 * -> run the evolver
 * -> and continue
 * -> Find out how to view the games, and how to speed them up
 * 
 * */

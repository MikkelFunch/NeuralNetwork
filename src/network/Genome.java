package network;

import java.util.Arrays;
import java.util.PrimitiveIterator.OfDouble;
import java.util.Random;
import java.util.stream.DoubleStream;

import util.MathTool;

public class Genome {
	private NetworkArchitecture architecture;
	
	//Contains the weights for the synapses. [synapsenumber]
	private double[] weights;
	
	private double[][] bias;
	private int id;
	private int number;
	
	
	public Genome(NetworkArchitecture e, double[] w, double[][] b){
		architecture = e;
		weights = w;
		bias = b;
	}
	
	public Genome(NetworkArchitecture e, double defaultBias, double defaultWeight){
		architecture = e;
		bias = new double[e.getLayers()][];
		weights = new double[e.getTotalSynapses()];
		
		for (int i = 0; i < bias.length; i++) {
			bias[i] = new double[e.getNeuronsInLayer(i)];
			for (int j = 0; j < e.getNeuronsInLayer(i); j++) {
				bias[i][j] = defaultBias;
			}
		}
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = defaultWeight;
		}
	}
	
	//Create genome with random weights and bias'
	public Genome(NetworkArchitecture e){
		architecture = e;
		bias = new double[e.getLayers()][];
		weights = new double[e.getTotalSynapses()];
		
		Random rand = new Random();
		DoubleStream ds = rand.doubles(-10, 10);
		OfDouble it = ds.iterator();
		for (int i = 0; i < bias.length; i++) {
			bias[i] = new double[e.getNeuronsInLayer(i)];
			for (int j = 0; j < e.getNeuronsInLayer(i); j++) {
				//bias[i][j] = rand.nextDouble();
				bias[i][j] = it.nextDouble();
			}
		}
		
		
		for (int i = 0; i < weights.length; i++) {
			weights[i] = it.nextDouble();
		}
	}


	public double getBias(int l, int n) {
		return bias[l][n];
	}


	public double getWeight(int i) {
		return weights[i];
	}


	public double[] getLayeredWeights(int l) {
		return Arrays.copyOfRange(weights, architecture.getNeruonsBeforeLayer(l), architecture.getNeruonsBeforeLayer(l) + architecture.getNeuronsInLayer(l));
	}

	public int getId() {
		return id;
	}

	public void setId(int i) {
		id = i;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int n) {
		number = n;
	}
	
	@Override
	public Genome clone(){
		
		double[] newW = new double[weights.length];
		Random rand = new Random();
		for (int i = 0; i < weights.length; i++) {
			newW[i] = weights[i];
		}
		
		double[][] newB = new double[bias.length][];
		for (int i = 0; i < bias.length; i++) {
			newB[i] = new double[bias[i].length];
			for (int j = 0; j < bias[i].length; j++) {
				newB[i][j] = bias[i][j];
			}
		}
		Genome clone = new Genome(architecture, newW, newB);
		
		/*double[] newW = new double[weights.length];
		System.arraycopy(weights, 0, newW, 0, weights.length);
		double[][] newB = new double[bias.length][];
		System.arraycopy(bias, 0, newB, 0, bias.length);
		
		Genome clone = new Genome(architecture, newW, newB);
		*/
		
		
		return clone;
	}

	public void mutate(double mutationChance, double intensity) {
		Random rand = new Random();
		for (int i = 0; i < weights.length; i++) {
			if(rand.nextDouble() < mutationChance){
				weights[i] += MathTool.getNormalDistribution() * intensity;
			}
		}
		
		for (int i = 0; i < bias.length; i++) {
			for (int j = 0; j < bias[i].length; j++) {
				if(rand.nextDouble() < mutationChance){
					bias[i][j] += MathTool.getNormalDistribution() * intensity;
				}
			}
		}
	}
	
	/*
	public Genome cloneAndMutate(double mutationChance, double intensity) {
		Genome g = new Genome(architecture);
		double[] newWeights = weights;
		double[][] newBias = bias;
		
		Random rand = new Random();
		for (int i = 0; i < weights.length; i++) {
			if(rand.nextDouble() < mutationChance){
				newWeights[i] += MathTool.getNormalDistribution() * intensity;
			}
		}
		
		for (int i = 0; i < bias.length; i++) {
			for (int j = 0; j < bias[i].length; j++) {
				if(rand.nextDouble() < mutationChance){
					newBias[i][j] += MathTool.getNormalDistribution() * intensity;
				}
			}
		}
		return g;
	}
	*/
	
	@Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Weights");
		sb.append("\n");
		for (int i = 0; i < weights.length; i++) {
			sb.append(weights[i]);
			sb.append("\n");
		}
		
		sb.append("\n");
		sb.append("Bias");
		sb.append("\n");
		for (int i = 0; i < bias.length; i++) {
			for (int j = 0; j < bias[i].length; j++) {
				sb.append(bias[i][j]);
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public double[] getWeight() {
		return weights;
	}

	public double[][] getBias() {
		return bias;
	}

	public void setWeight(int i, double d) {
		weights[i] = d;
	}

	public void setBias(int i, int j, double d) {
		bias[i][j] = d;
	}
}

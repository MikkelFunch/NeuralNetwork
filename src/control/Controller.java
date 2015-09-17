package control;

import java.util.ArrayList;
import java.util.List;

import pacman.Executor;
import pacman.controllers.examples.StarterGhosts;
import pacman.game.Game;
import evolution.Evolver;
import evolution.Generation;
import gamecontrollers.startPacmanController;
import network.NetworkArchitecture;
import network.Genome;
import network.NeuralNetwork;

public class Controller {
	private static final int TRIES_PR_NETWORK = 50;

	public static void main(String [] args){
		System.out.println("Setting evolver settings");
		Evolver.setDefaultParameters();
		
		System.out.println("Start");
		
		NetworkArchitecture e = new NetworkArchitecture(10,3,1);
		Generation g = new Generation();
		g.generatePopulation(e, 150);
		
		Executor exec = new Executor();
		startPacmanController controller = new startPacmanController();
		
		for (int i = 0; i < 10000000; i++) {//run loop
			for (NeuralNetwork n : g.getNetworks()) {
				controller.setCurrentNetwork(n);
				double fitness = 0;
				for (int j = 0; j < TRIES_PR_NETWORK; j++) {
					
					//if(i < 50)
						exec.runGame(controller, new StarterGhosts(), false,0);
					//else
					//	exec.runGame(controller, new StarterGhosts(), true, 20);
					//exec.runGameTimed(controller, new StarterGhosts(), true);
					fitness += n.getFitness();
					//if(fitness / TRIES_PR_NETWORK > 12000){
					//	exec.runGame(controller, new StarterGhosts(), true, 20);
					//}
				}
				n.setFitness(fitness / TRIES_PR_NETWORK);
				
				
				
			}
			
			//System.out.println(g.getNetworks().get(0).getGenome().toString());
			//System.out.println(g.getNetworks().get(0));
			System.out.println("Run: " + i);
			System.out.println("Evolve");
			g = Evolver.evolveGeneration(g);
		}
		
		
		/*
		NetworkArchitecture e = new NetworkArchitecture(3,2,1);
		Genome g = new Genome(e, 0, 0);
		Genome g2 = new Genome(e, 0.5, 0.5);
		Genome g3 = new Genome(e, 1, 1);
		NeuralNetwork nw = new NeuralNetwork(g, e);
		NeuralNetwork nw2 = new NeuralNetwork(g2, e);
		NeuralNetwork nw3 = new NeuralNetwork(g3, e);
		
		List<NeuralNetwork> networkList = new ArrayList<NeuralNetwork>();
		networkList.add(nw);
		networkList.add(nw2);
		networkList.add(nw3);
		
		Generation gen = new Generation(networkList);
		
		
		Genome gRandom = new Genome(e);
		
		
		
		System.out.println("End");
		
		nw.setFitness(0);
		nw2.setFitness(15);
		nw3.setFitness(4);
		Evolver.evolveGeneration(gen);
		*/
		
		/**
		 * run game on all networks
		 * save their fitness
		 * run evolution
		 * run game on new generation
		 */
	}
}

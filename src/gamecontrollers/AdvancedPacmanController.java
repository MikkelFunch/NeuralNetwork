package gamecontrollers;

import java.util.ArrayList;
import java.util.List;

import network.NeuralNetwork;
import pacman.game.Constants.DM;
import pacman.game.Constants.GHOST;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import control.NeuralNetworkController;

public class AdvancedPacmanController extends NeuralNetworkController {

	@Override
	public MOVE getMove(Game game, long timeDue) {
		currentNetwork.setFitness(game.getScore());
		
		return getBestMove(game);
	}

	private MOVE getBestMove(Game g) {
		game = g;
		//find neighbours
		int currentNode = game.getPacmanCurrentNodeIndex();
		int[] neighbours = game.getNeighbouringNodes(currentNode);
		
		int highestNeighbour = -1;
		double highestScore = -1;
		for (int i = 0; i < neighbours.length; i++) {
			double score = getScoreOfNode(neighbours[i]);
			assert score > 0;
			if(highestScore < score){
				highestNeighbour = neighbours[i];
				highestScore = score;
			}
		}
		
		if(highestNeighbour == -1){
			throw new IllegalStateException();
		}
		return game.getNextMoveTowardsTarget(currentNode, highestNeighbour, DM.PATH);
	}

	private double getScoreOfNode(int currentNode) {
		currentNetwork.clearInputs();
		
		inputGhostDistances(currentNode);
		inputPillDistance(currentNode);
		inputPowerPillDistance(currentNode);
		inputGhostEdibleTime(currentNode);
		
		List<Double> output = currentNetwork.computeOutput();
		return output.get(0);
	}

	private void inputGhostEdibleTime(int currentNode) {
		List<Double> inputs = new ArrayList<Double>(4);
		for(GHOST ghost : GHOST.values()){
			if(game.getGhostEdibleTime(ghost) == 0.0){
				inputs.add(-1.0d);
			} else {
				inputs.add(convertTimeToInput(game.getGhostEdibleTime(ghost)));
			}
			
		}
		
		currentNetwork.addInput(inputs);
	}

	private void inputPillDistance(int currentNode) {
		int[] activePills = game.getActivePillsIndices();
		if(activePills.length == 0){
			currentNetwork.addInput(-1);
		} else {
			double distance = game.getDistance(currentNode, game.getClosestNodeIndexFromNodeIndex(currentNode, activePills, DM.PATH), DM.PATH);
			currentNetwork.addInput(convertDistanceToInput(distance));
		}
	}

	private void inputPowerPillDistance(int currentNode) {
		int[] activePowerPills = game.getActivePowerPillsIndices();
		if(activePowerPills.length == 0){
			currentNetwork.addInput(-1);
		} else {
			double distance = game.getDistance(currentNode, game.getClosestNodeIndexFromNodeIndex(currentNode, activePowerPills, DM.PATH), DM.PATH);
			currentNetwork.addInput(convertDistanceToInput(distance));
		}
	}

	private void inputGhostDistances(int currentNode) {
		List<Double> inputs = new ArrayList<Double>(4);
		
		for(GHOST ghost : GHOST.values()){
			int distance = game.getShortestPathDistance(currentNode, game.getGhostCurrentNodeIndex(ghost));
			inputs.add(convertDistanceToInput(distance));
		}
		
		currentNetwork.addInput(inputs);
	}

	public void setCurrentNetwork(NeuralNetwork n){
		currentNetwork = n;
	}
}

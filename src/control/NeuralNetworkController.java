package control;

import network.NeuralNetwork;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public abstract class NeuralNetworkController extends Controller<MOVE> {
	protected NeuralNetwork currentNetwork;
	protected Game game;
	private final static int MAX_DISTANCE = 255;
	
	@Override
	public abstract MOVE getMove(Game game, long timeDue);
	
	protected double convertBooleanToInput(boolean b){
		return b == true ? 1d : 0d;
	}
	
	protected double convertDistanceToInput(double distance){
		return distance / MAX_DISTANCE;
	}
}

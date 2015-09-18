package control;

import network.NeuralNetwork;
import pacman.controllers.Controller;
import pacman.game.Constants.MOVE;
import pacman.game.Game;

public abstract class NeuralNetworkController extends Controller<MOVE> {
	protected NeuralNetwork currentNetwork;
	protected Game game;
	private final static int MAX_DISTANCE = 255;
	private final static int MAX_EDIBLE_TIME = 200;
	
	@Override
	public abstract MOVE getMove(Game game, long timeDue);
	
	protected double convertBooleanToInput(boolean b){
		return b == true ? 1d : 0d;
	}
	
	protected double convertDistanceToInput(double d){
		return d / MAX_DISTANCE;
	}
	
	protected double convertTimeToInput(double t){
		return t / MAX_EDIBLE_TIME;
	}
}

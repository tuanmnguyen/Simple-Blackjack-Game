package view;

import java.util.logging.Level;
import java.util.logging.Logger;

import model.interfaces.GameEngine;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.interfaces.GameEngineCallback;

/**
 * 
 * Skeleton/Partial example implementation of GameEngineCallback showing Java logging behaviour
 * 
 * @author Caspar Ryan
 * @see view.interfaces.GameEngineCallback
 * 
 */
public class GameEngineCallbackImpl implements GameEngineCallback
{
   private final Logger logger = Logger.getLogger(this.getClass().getName());

   public GameEngineCallbackImpl()
   {
	   	// FINE shows dealing output, INFO only shows result
	   	logger.setLevel(Level.FINE);
   }

   @Override
   public void nextCard(Player player, PlayingCard card, GameEngine engine) {
	   	String output = String.format("Card Dealt to %s .. %s", player.getPlayerName(), card.toString());
	   	logger.log(Level.FINE,output);    
   }

   @Override
   public void result(Player player, int result, GameEngine engine) {
	   	String output = String.format("%s, final result=%d", player.getPlayerName(), player.getResult());
	   	logger.log(Level.INFO,output);
   }

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		String output = String.format("Card Dealt to %s .. %s ... YOU BUSTED!", player.getPlayerName(), card.toString());
		logger.log(Level.FINE,output); 
	}
	
	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		String output = String.format("Card Dealt to House .. %s", card.toString());
		logger.log(Level.FINE,output);  
	}
	
	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		String output = String.format("Card Dealt to House .. %s ... HOUSE BUSTED!", card.toString());
		logger.log(Level.FINE,output); 
	}
	
	@Override
	public void houseResult(int result, GameEngine engine) {
		String outputHouseResult = String.format("House, final result=%d", result);
		logger.log(Level.INFO,outputHouseResult);
	    
	    String outputPlayerResults = "Final Player Results";
	    logger.log(Level.INFO,outputPlayerResults);
	    
	    // Print results for all players
	    for(Player player : engine.getAllPlayers())
	    		System.out.println(player.toString());	
	}
}

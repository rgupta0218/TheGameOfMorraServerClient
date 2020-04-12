/*
 * Het Banker NetID: hbanke2 
 * Ria Gupta NetID: rgupta40
 */

import java.io.Serializable;
import java.util.ArrayList;

public class MorraInfo implements Serializable 
{

	private static final long serialVersionUID = 1L;
	
	//which player are you
	private int P1;
	private int P2;
	
	//players gusses
	private int p1Guess;
	private int p2Guess;
	
	//number of games each player won
	private int p1Points;	
	private int p2Points;

	///player moves (1 to 5) fingers
	private int p1Plays;	
	private int p2Plays;	
	
	//checks to see if there are 2 players avaiable
	public Boolean have2players;
	
	//number of clients in the game
	ArrayList<Integer> clientHolder = new ArrayList<Integer>(0);
	
	//keeping track of how many times player 1 and 2 won the rounds
	ArrayList<Integer> player1Winn = new ArrayList<Integer>();;
	ArrayList<Integer> player2Winn = new ArrayList<Integer>();;
	
	MorraInfo()  
	{
		P1 = 0;
		P2 = 0;
		p1Guess=0;
		p2Guess=0;
		p1Points = 0;
		p2Points = 0;
		p1Plays =  0;
		p2Plays =  0;
		have2players = false;
	}
	
	//guess number player 1
	void setP1Guess(int p1)
	{
		this.p1Guess = p1;
	}
	int getP1Guess()
	{
		return this.p1Guess;
	}
	
	//guess number player 2
	void setP2Guess(int p2)
	{
		this.p2Guess = p2;
	}
	
	int getP2Guess()
	{
		return this.p2Guess;
	}
	
	//player 1
	void setP1(int p1)
	{
		this.P1 = p1;
	}
	int getP1()
	{
		return this.P1;
	}
	
	//player 2
	void setP2(int p2)
	{
		this.P2 = p2;
	}
	int getP2()
	{
		return this.P2;
	}
	
	//p1Points
	void setp1Points(int score)
	{
		this.p1Points = score;
	}
	int getp1Points()
	{
		return this.p1Points;
	}
	
	//p2Points
	void setp2Points(int score)
	{
		this.p2Points = score;
	}
	int getp2Points()
	{
		return this.p2Points;
	}
	
	//p1Plays
	void setp1Plays(int move)
	{
		this.p1Plays = move;
	}
	int getp1Plays()
	{
		return this.p1Plays;
	}
	
	//p2Plays
	void setp2Plays(int score)
	{
		this.p2Plays = score;
	}
	int getp2Plays() 
	{
		return this.p2Plays;
	} 
	
	
	//check who won the game, player 1 or player 2
	int winner()
	{
	
		if((getp1Plays() + getp2Plays() == getP1Guess()) && (getp1Plays() + getp2Plays() != getP2Guess()))
		{			
			return 1;	//player 1 wins
		} 
		
		if((getp1Plays() + getp2Plays() == getP2Guess()) && (getp1Plays() + getp2Plays() != getP1Guess()))
		{			
			return 2;	//player 2 wins
		}
		else 
		{
			return 0;	//no one won
		}	
	}
}
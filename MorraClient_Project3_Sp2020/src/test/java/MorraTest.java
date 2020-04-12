import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class MorraTest 
{
	MorraInfo morraInfo;
	
	@BeforeEach
	void init()
	{
		morraInfo = new MorraInfo();
	}
	
	//check if there are 2 players
	@Test
	void checkConnection()
	{
		assertEquals(false, morraInfo.have2players, "should be false where no one is connected");	
	}
	

	//case where no one wins
	@Test
	void checkWinner1()
	{
		morraInfo.setp1Plays(5);
		morraInfo.setp2Plays(2);
		morraInfo.setP1Guess(6);
		morraInfo.setP2Guess(3);
		assertEquals(0, morraInfo.winner(), "No one won");	
	}
	
	//case where player 1 wins
	@Test
	void checkWinner2()
	{
		morraInfo.setp1Plays(7);
		morraInfo.setp2Plays(2);
		morraInfo.setP1Guess(9);
		morraInfo.setP2Guess(3);
		assertEquals(1, morraInfo.winner(), "player 1 should win");	
	}

	//case where player 2 wins
	@Test
	void checkWinner3()
	{
		morraInfo.setp1Plays(5);
		morraInfo.setp2Plays(1);
		morraInfo.setP1Guess(9);
		morraInfo.setP2Guess(6);
		assertEquals(2, morraInfo.winner(), "player 2 should win");	
	}
	
	//case where both players guess the same and 
	@Test
	void checkWinner4()
	{
		morraInfo.setp1Plays(6);
		morraInfo.setp2Plays(1);
		morraInfo.setP1Guess(7);
		morraInfo.setP2Guess(7);
		assertEquals(0, morraInfo.winner(), "no one should win");	
	}
	
	//no one wins
	@Test
	void checkWinner5() 
	{
		morraInfo.setp1Plays(6);
		morraInfo.setp2Plays(2);
		morraInfo.setP1Guess(7);
		morraInfo.setP2Guess(7);
		assertEquals(0, morraInfo.winner(), "no one should win");	
	}
}

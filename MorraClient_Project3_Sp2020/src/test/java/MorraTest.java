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
	
}

package connect4;

public interface BoardInterface {
	
	public void populateBoard();
	public void displayBoard();
	public void clearBoard();
	public void takeTurn();
	public void displayWinner();
	public boolean isFull();
	boolean isWinner(String cp);

	}

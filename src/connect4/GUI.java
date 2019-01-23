package connect4;


	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.GridLayout;
	import java.awt.Image;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.net.URL;

	import javax.imageio.ImageIO;
	import javax.swing.Icon;
	import javax.swing.ImageIcon;
	import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JLabel;
	import javax.swing.JOptionPane;
	import javax.swing.JPanel;

	import connect4.BoardInterface;

	public class GUI extends JFrame{
		
		private Connect4Board jpBoard;//a JPanel containing the board made of buttons
		private DescriptionBoard DescriptionBoard;//a JPanel showing the score made of labels

		private int currPlayer = 1;
		
		public GUI() {
	
			setLayout(new BorderLayout());
			jpBoard = new Connect4Board(); //createC4Board
			add(jpBoard, BorderLayout.CENTER);//add JPanels to JFrame
			
			DescriptionBoard = new DescriptionBoard();
			add(DescriptionBoard, BorderLayout.NORTH);
			setSize(700,700);
			setVisible(true);
			setDefaultCloseOperation(EXIT_ON_CLOSE);
			jpBoard.setBackground(Color.YELLOW);
			
			
		}
		
		
		//  D E S C R I P T I O N   B O A R D   S T U F F
		private static class DescriptionBoard extends JPanel{
			private JLabel lblChamp, lblForChamp, lblChampSpace;
			private static JLabel lblForTurn;
			private JLabel [] genJLabels = {lblForChamp,lblChamp,lblChampSpace,lblForTurn};

			private JPanel jpDescriptionInfo;
			
			public DescriptionBoard(){
				setLayout(new BorderLayout());
				jpDescriptionInfo = new JPanel(new GridLayout(4,3));
				jpDescriptionInfo.setBackground(Color.YELLOW);
	

				
				lblForChamp = new JLabel("The Current Champion is: ");
				lblChamp = new JLabel("----------------------");
				lblChampSpace = new JLabel("   ");
				lblForTurn = new JLabel("Player 1: RED's Turn");
				lblForTurn.setForeground(Color.RED);
				
				
				JLabel [] genJLabels = {lblForChamp,lblChamp,lblChampSpace,lblForTurn};
				for(int i=0; i<genJLabels.length; i++){
					genJLabels[i].setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
					jpDescriptionInfo.add(genJLabels[i]);
				}
				
				
				add(jpDescriptionInfo, BorderLayout.NORTH);	
			}
			
			
			
			public JLabel getLblChamp() {
				return lblChamp;
			}
			private void setLblChamp(String lblChamp) {
				this.lblChamp.setText(lblChamp);
			}
			
			private static void setLblTurn(String turn) {
				lblForTurn.setText(turn+"'s Turn.");
				if ( turn.equalsIgnoreCase("RED") ) {
					lblForTurn.setForeground(Color.RED);
				}
				
				else {
					lblForTurn.setForeground(Color.BLACK);
				}
			}
			
		}
	
		//  C O N N E C T 4   B O A R D   S T U F F
		private class Connect4Board extends JPanel implements BoardInterface, ActionListener{
			private JButton [][] board;
			
			public Connect4Board(){
				setLayout(new GridLayout(6,7));
				displayBoard();
			}

			
			@Override
			public void populateBoard() {
				board = new JButton[6][7];//initialize 2D array 
				for(int row=0; row<board.length; row++){
					for(int col=0; col<board[row].length;col++){
						board[row][col] = new JButton();
						
					}
				}
				
			}
		
			@Override
			public void displayBoard() {//use this method when creating a GUI based
				board = new JButton[6][7];//initialize 2D array
				for(int row=0; row<board.length; row++){
					for(int col=0; col<board[row].length;col++){
						board[row][col] = new JButton();
						board[row][col].setName( ""+col);
						board[row][col].addActionListener(this);//listen
						board[row][col].setEnabled(true);//enable
						add(board[row][col]); //add to the JPanel
						
					}
				}
				
			}
		
			@Override
			public void clearBoard() {
				for(int row=0; row<board.length; row++){
					for(int col=0; col<board[row].length;col++){
						board[row][col].setName( ""+col);
						board[row][col].setEnabled(true);
						board[row][col].setBackground(null);
						board[row][col].setOpaque(true);
						board[row][col].setIcon(null);
					}
				}
			}
		
			@Override
			public void takeTurn() {
				
				if(currPlayer == 1){
					DescriptionBoard.setLblTurn("Player 2: BLACK");
					currPlayer = 2;
					
					
				}
				else{
					DescriptionBoard.setLblTurn("Player 1: RED");
					currPlayer = 1;
				}
				
			}
		
			@Override
			public void displayWinner() {
				// TODO Auto-generated method stub
				//update the JLabel or display using a JOptionPane
				
				
				
				if(currPlayer == 1){
					DescriptionBoard.setLblChamp("Player 1: RED");
				}
				else{
					DescriptionBoard.setLblChamp("Player 2: BLACK");
				}
				
			}
		
			@Override
			public boolean isFull() {
				
				int topRow = 0;
				
				for(int col=0; col<7;col++){
						JButton cellText = board[0][col];
						if(cellText.isEnabled()){
							topRow += 0;
						}
						else {
							topRow ++;
						}
						
				}

				if (topRow == 7) {
					topRow = 0;
					return true;
				}
				
				else {
					topRow = 0;
					return false;
					
				}
			}
		
		
			@Override
			public boolean isWinner(String cp){
				
				//check rows
				for(int row=0; row<board.length; row++){
					int rowCount = 0;//row match counter, resets on next row
					for(int col=0; col<board[row].length; col++){
						if(board[row][col].getName().trim().equalsIgnoreCase(cp)){
							rowCount++;//increment counter
							if(rowCount == 4){
								return true;//found 4 in same row
							}
						}
						
						else {
							rowCount = 0;
						}
					}
				}

				 
				//check columns
				for(int col=0; col<7; col++){
					int colCount=0;
					for(int row=0; row<6; row++){
						if(board[row][col].getName().trim().equalsIgnoreCase(cp)){
							colCount++;
							if(colCount ==4){
								return true;//found 3 in same column
							}
						}
						else {
							colCount = 0;
						}
					}
				}
				 
				
				
				//check diagonal to the right
				int diagCount=0;
				for(int i=0; i<board.length; i++){
					if(board[i][i].getName().trim().equalsIgnoreCase(cp)){
						diagCount++;
						if(diagCount ==4){
							return true;//found 4 same across main diagonal
						}
					}
					else {
						diagCount = 0;
					}
				}
				
				
				int diag2Count=0;
				 
				for(int c=0; c < board.length  ; c++){
					int length = board.length - c + 1;
					//System.out.println( "- - - - - - - -- - - - - - - - -");
					//System.out.println(board.length + " board.length");
					
					for(int i=1; i<length; i++){
						int column = i + c;
						int row = 	i-1 ;		 
						//System.out.println( column + " column");
						//System.out.println( row + " row");
						//System.out.println( " ");
						if(board[row][column].getName().trim().equalsIgnoreCase(cp)){
							diag2Count++;
							if(diag2Count ==4){
								return true;//found 4 same across main diagonal
							}
						}
						else {
							diag2Count = 0;
						}
					}
				}
				
				
				
				
				//check diagonal to the left
				int diagLCount=0;
				for(int i=0; i<6; i++){
					
					int negativeColumn = i;
					int negativeRow = 5 - i;
					
					if(board[negativeRow][negativeColumn].getName().trim().equalsIgnoreCase(cp)){
						
						diagLCount++;
						if(diagLCount ==4){
							return true;//found 4 same across main diagonal
						}
					}
					else {
						diagLCount = 0;
					}
				}
				
				
				int diagL3Count=0;
				for(int i=0; i<5; i++){
					
					int negativeColumn = i;
					int negativeRow = 4 - i;
					
					if(board[negativeRow][negativeColumn].getName().trim().equalsIgnoreCase(cp)){
						
						diagL3Count++;
						if(diagL3Count ==4){
							return true;//found 4 same across main diagonal
						}
					}
					else {
						diagL3Count = 0;
					}
				}
				
				int diagL4Count=0;
				for(int i=0; i<4; i++){
					
					int negativeColumn = i;
					int negativeRow = 3 - i;
					
					if(board[negativeRow][negativeColumn].getName().trim().equalsIgnoreCase(cp)){
						
						diagL4Count++;
						if(diagL4Count ==4){
							return true;//found 4 same across main diagonal
						}
					}
					else {
						diagL4Count = 0;
					}
				}
				
				int diagL2Count=0;
				int columnShift = 0;
				int columnLength = 7;
				for(int c=6; c >= 0  ; c--){
		
						for(int i=1; columnLength > i  || i < c ; i++){
							
							int negative2Column = i + columnShift ;
							int negative2Row = 6 - i;
							
							if(board[negative2Row][negative2Column].getName().trim().equalsIgnoreCase(cp)){
								
								diagL2Count++;
								if(diagL2Count ==4){
									return true;//found 4 same across main diagonal
								}
							}
							else {
								diagL2Count = 0;
							}
							
						}
						
					diagL2Count = 0;	
					columnShift++;
					columnLength--;
				}
				
			
				return false;
				
			}
		
			
			
			
			private void promptPlayAgain() {
				// TODO Auto-generated method stub
				//JOptionPane....
				int yesNo = JOptionPane.showConfirmDialog(null, "Play Again?","Yes or No", JOptionPane.YES_NO_OPTION);
				if(yesNo == JOptionPane.YES_OPTION ){
					//clear board
					clearBoard();
					//make sure the player is displayed...
				}
				else{
					System.exit(EXIT_ON_CLOSE);
				}
			}
			
		

			// G A M E P L A Y
			
			private void markSpot(int column, int row) {
				if (currPlayer == 1) {
					board[row][column].setBackground(Color.WHITE);
					board[row][column].setOpaque(true);
					board[row][column].setEnabled(false);
					board[row][column].setName("RED");
					try {
						Image img = ImageIO.read(getClass().getResource("/redchip.jpg"));
						img = img.getScaledInstance(70, 70,Image.SCALE_FAST);
						board[row][column].setIcon(new ImageIcon(img));
					} 	catch 
						(Exception ex) {
						System.out.println(ex);
					}
					
				}
				else{
					board[row][column].setBackground(Color.WHITE);
					board[row][column].setOpaque(true);
					board[row][column].setEnabled(false);
					board[row][column].setName("BLACK");
					try {
						Image img = ImageIO.read(getClass().getResource("/blackchip.png"));
						img = img.getScaledInstance(70, 70,Image.SCALE_FAST);
						board[row][column].setIcon(new ImageIcon(img));
					} 	catch 
						(Exception ex) {
						System.out.println(ex);
					}
				}
			}
		
			
			
			// WHAT HAPPENS WHEN A BUTTON IS PRESSED ON THE BOARD.
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JButton btnClicked = (JButton) e.getSource();//find out what button/cell was clicked
				
				//System.out.println("Row: "+ btnClicked.getName() );
				
				int gameColumn = Integer.parseInt(btnClicked.getName());
			
				// beginning of first column.
				
				
					if (board[0][gameColumn].isEnabled() == true &&
						board[1][gameColumn].isEnabled() == true &&
						board[2][gameColumn].isEnabled() == true &&
						board[3][gameColumn].isEnabled() == true &&
						board[4][gameColumn].isEnabled() == true &&
						board[5][gameColumn].isEnabled() == true )
					{	
							markSpot(gameColumn, 5);
							
					}
					
					else if (board[0][gameColumn].isEnabled() == true &&
							board[1][gameColumn].isEnabled() == true &&
							board[2][gameColumn].isEnabled() == true &&
							board[3][gameColumn].isEnabled() == true &&
							board[4][gameColumn].isEnabled() == true &&
							board[5][gameColumn].isEnabled() == false )
					{
							
						markSpot(gameColumn, 4);
					}
					
					else if (board[0][gameColumn].isEnabled() == true &&
							board[1][gameColumn].isEnabled() == true &&
							board[2][gameColumn].isEnabled() == true &&
							board[3][gameColumn].isEnabled() == true &&
							board[4][gameColumn].isEnabled() == false &&
							board[5][gameColumn].isEnabled() == false )
					{
					
						markSpot(gameColumn, 3);
					}
					
					else if (board[0][gameColumn].isEnabled() == true &&
							board[1][gameColumn].isEnabled() == true &&
							board[2][gameColumn].isEnabled() == true &&
							board[3][gameColumn].isEnabled() == false &&
							board[4][gameColumn].isEnabled() == false &&
							board[5][gameColumn].isEnabled() == false )
					{
							
						markSpot(gameColumn, 2);
					}
					
					else if (board[0][gameColumn].isEnabled() == true &&
							board[1][gameColumn].isEnabled() == true &&
							board[2][gameColumn].isEnabled() == false &&
							board[3][gameColumn].isEnabled() == false &&
							board[4][gameColumn].isEnabled() == false &&
							board[5][gameColumn].isEnabled() == false )
					{
									
						markSpot(gameColumn, 1);
					}
					
					else if (board[0][gameColumn].isEnabled() == true &&
							board[1][gameColumn].isEnabled() == false &&
							board[2][gameColumn].isEnabled() == false &&
							board[3][gameColumn].isEnabled() == false &&
							board[4][gameColumn].isEnabled() == false &&
							board[5][gameColumn].isEnabled() == false )
					{
							
						markSpot(gameColumn, 0);
					}
					
					else {}
				
					
					
					
				
				String whosTurn;
				
				if (currPlayer ==1){
					whosTurn = "RED";
				}
				
				else {
					whosTurn = "BLACK";
				}
				
				
				
				if(isWinner(whosTurn)){   //check for winner OR isFull   -->
					JOptionPane.showMessageDialog(null, whosTurn + " Wins. ");
					displayWinner();//					display winner
					promptPlayAgain();//					playAgain???
				}
					
					
				if(isFull()){
					JOptionPane.showMessageDialog(null, "DRAW!");
					promptPlayAgain();
				}
				
				takeTurn();			//update current player and the display...take a turn
				
			}
		}
	}


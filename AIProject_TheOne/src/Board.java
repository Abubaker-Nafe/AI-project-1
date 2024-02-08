import java.util.*;

public class Board {

	private char[][] board = new char[8][8];

	public Board() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				this.board[i][j] = '-';
			}
		}
	}

	public void playGame() {
		Scanner scanner = new Scanner(System.in);
		int[] bestMove=new int[3];

		// Menu to choose Player 1 type
		System.out.println("Select Player 1:");
		System.out.println("1. Human");
		System.out.println("2. AI");
		int player1Choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		// Menu to choose Player 2 type
		System.out.println("Select Player 2:");
		System.out.println("1. Human");
		System.out.println("2. AI");
		int player2Choice = scanner.nextInt();
		scanner.nextLine(); // Consume the newline character

		int currentPlayer = 1;

		while (!isGameOver()) {
			if (currentPlayer == 1 && player1Choice == 1) { // Human vs Human
				// Human Player 1
				System.out.println("Player 1, make your move (row):");
				int row = scanner.nextInt() - 1;
				System.out.println("Player 1, make your move (column):");
				int col = scanner.nextInt() - 1;

				if (!is_valid_move(board, row, col)) {
					System.out.println("Invalid move! Try again.");
					continue;
				}

				board[row][col] = 'W';
			} else if (currentPlayer == 1 && player1Choice == 2) { // human vs AI
				// AI Player 1
				System.out.println("AI Player 1 is making a move...");
				bestMove = maxmin(this.board, 8,Integer.MIN_VALUE,Integer.MAX_VALUE, false, -1, -1);
				//bestMove = maxmin(this.board, 2,false, -1, -1);//without alpha beta pruning
				int row = bestMove[0];
				int col = bestMove[1];

				board[row][col] = 'W';
				System.out.println("AI Player 1 played at: " + (row + 1) + ", " + (col + 1));
			} else if (currentPlayer == 2 && player2Choice == 1) {// AI vs Human
				// Human Player 2
				System.out.println("Player 2, make your move (row column):");
				int row = scanner.nextInt() - 1;
				int col = scanner.nextInt() - 1;

				if (!is_valid_move(board, row, col)) {
					System.out.println("Invalid move! Try again.");
					continue;
				}

				board[row][col] = 'B';
			} else { // AI vs AI
				// AI Player 2
				System.out.println("AI Player 2 is making a move...");
				bestMove = maxmin(this.board, 8,Integer.MIN_VALUE,Integer.MAX_VALUE, true, -1, -1);
				//bestMove = maxmin(this.board, 2,true, -1, -1);
				int row = bestMove[0];
				int col = bestMove[1];

				board[row][col] = 'B';
				System.out.println("AI Player 2 played at: " + (row + 1) + ", " + (col + 1));
			}

			displayBoard(this.board);

			currentPlayer = currentPlayer == 1 ? 2 : 1;
		}
		scanner.close();

	}

	public boolean is_valid_move(char[][] Board, int row, int column) {
		// Check if the given cell is empty and within the bounds of the board
		if (row < 0 || row >= 8 || column < 0 || column >= 8 || Board[row][column] != '-') {
			return false;
		}

		// Check if the move is adjacent to a brick on the left or right wall, or next
		// to another brick
		boolean adjacentToBrick = false;

		// Check left wall
		if (column == 0
				&& (row == 0 || row == 1 || row == 2 || row == 3 || row == 4 || row == 5 || row == 6 || row == 7)) {
			adjacentToBrick = true;
		}

		// Check right wall
		if (column == 7
				&& (row == 0 || row == 1 || row == 2 || row == 3 || row == 4 || row == 5 || row == 6 || row == 7)) {
			adjacentToBrick = true;
		}

		// Check left adjacent cell
		if (column > 0 && Board[row][column - 1] != '-') {
			adjacentToBrick = true;
		}

		// Check right adjacent cell
		if (column < 7 && Board[row][column + 1] != '-') {
			adjacentToBrick = true;
		}

		return adjacentToBrick;
	}

	public void makeMove(char[][] board, int i, int j, char player) {
		board[i][j] = player;
	}

	public char[][] copyBoard(char[][] board) {
		char[][] copy = new char[8][8];
		for (int i = 0; i < 8; i++) {
			for(int j=0 ; j<8 ; j++)
				//System.arraycopy(board[i], 0, copy[i], 0, 8);
				copy [i][j] = board[i][j];
		}
		return copy;
	}

	public boolean isPlayer_1_Win() {
		for(int i =0 ; i<8 ; i++) //check row
		{
			for(int j = 0 ; j <= 3 ; j++)
			{
				if (board[i][j] == 'W' && board[i][j + 1] == 'W' && board[i][j + 2] == 'W' && board[i][j + 3] == 'W' && board[i][j + 4] == 'W') {
					return true;
				}
			}
		
		}
		for(int z=0 ; z<8 ; z++)//check column
		{
			for(int q = 0 ; q<=3;q++)
			{
				if (board[q][z] == 'W' && board[q + 1][z] == 'W' && board[q + 2][z] == 'W' && board[q + 3][z] == 'W' && board[q + 4][z] == 'W') {
					return true;
				}
			}
		}

		for (int k = 0; k <= 3; k++) {//checl diagonal
			for (int l = 0; l <= 3; l++) {
				if (board[k][l] == 'W' && board[k + 1][l+1] == 'W' && board[k + 2][l+2] == 'W' && board[k + 3][l+3] == 'W' && board[k + 4][l+4] == 'W') {
					return true;
				}
			}
		}

		for (int a = 0; a <= 3; a++) { //check anti-diagonal
			for (int b = 4; b < 8; b++) {
				if (board[a][b] == 'W' && board[a + 1][b-1] == 'W' && board[a + 2][b-2] == 'W' && board[a + 3][b - 3] == 'W' && board[a + 4][b - 4] == 'W') {
					return true;
				}
			}
		}
		return false;

	}

	public boolean isPlayer_2_Win() {
		for(int i =0 ; i<8 ; i++)
		{
			for(int j = 0 ; j <= 3 ; j++) //checl row
			{
				if (board[i][j] == 'B' && board[i][j + 1] == 'B' && board[i][j + 2] == 'B' && board[i][j + 3] == 'B' && board[i][j + 4] == 'B') {
					return true;
				}
			}	
		}

		for(int z=0 ; z<8 ; z++) //check column
		{
			for(int q = 0 ; q<3;q++)
			{
				if (board[q][z] == 'B' && board[q + 1][z] == 'B' && board[q + 2][z] == 'B' && board[q + 3][z] == 'B' && board[q + 4][z] == 'B') {
					return true;
				}
			}
		}

		for (int k = 0; k <= 3; k++) { //checl diagonal
			for (int l = 0; l <= 3; l++) {
				if (board[k][l] == 'B' && board[k + 1][l+1] == 'B' && board[k + 2][l+2] == 'B' && board[k + 3][l+3] == 'B' && board[k + 4][l+4] == 'B') {
					return true;
				}
			}
		}

		for (int a = 0; a <= 3; a++) { //check anti-diagonal
			for (int b = 4; b < 8; b++) {
				if (board[a][b] == 'B' && board[a + 1][b-1] == 'B' && board[a + 2][b-2] == 'B' && board[a + 3][b - 3] == 'B' && board[a + 4][b - 4] == 'B') {
					return true;
				}
			}
		}	
		return false;

	}

	public boolean isGameOver() {
		if (isPlayer_1_Win() || isPlayer_2_Win()) {
			System.out.println("Game Over!");
			return true;
		}
		return false;
	}

	public void displayBoard(char [][] B) {
		System.out.println("   │ 1 2 3 4 5 6 7 8 │");
		for (int row = 7; row >= 0; row--) {
			System.out.print((row + 1) + "  │ ");
			for (int col = 0; col < 8; col++) {
				if (B[row][col] == 'W') {
					System.out.print("W ");
				}
				if (B[row][col] == 'B') {
					System.out.print("B ");
				}
				if (B[row][col] == '-') {
					System.out.print("- ");
				}
			}
			System.out.println("│  " + (row + 1));
		}
		System.out.println("   │ 1 2 3 4 5 6 7 8 │");
		System.out.println("\n");
	}

	// New Stuff

	// old maxmin without alpha/beta
	public int[] maxmin(char[][] B, int depth, boolean maximizingPlayer, int row, int col) {
		// Base case: If the game is over or the maximum depth is reached, return the
		// evaluation score
		if (isGameOver() || depth == 0) {
			return new int[] { row, col, evaluate(B) };
		}

		if (maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			int[] evalResult=new int[3];
			int[] bestMove = new int[3]; // Stores the row, column, and evaluation score of the best move
			bestMove[0] = row;
			bestMove[1] = col;
			List<int[]> possibleMoves = getPossibleMoves(B, 'W');
			for (int[] move : possibleMoves) {
				char[][] newBoard = copyBoard(B);
				//System.out.println("move in Max: "+move[0]+","+move[1]);
				makeMove(newBoard, move[0], move[1], 'W');
				evalResult = maxmin(newBoard, depth - 1, false, bestMove[0], bestMove[1]);
				//System.out.println("evalResult in max: "+evalResult[0]+","+evalResult[1]+","+evalResult[2]);
				int evalScore=0;
				if(evalResult[2] != 0)
					evalScore = evalResult[2];
				if (evalScore > maxEval) {
					maxEval = evalScore;
					bestMove[0] = move[0]; // Store the row index of the best move
					bestMove[1] = move[1]; // Store the column index of the best move
					bestMove[2] = evalScore; // Store the evaluation score of the best move
				}
			}
			//System.out.println("Max ="+bestMove[0]+bestMove[1]);
			return bestMove;
		} else {
			int minEval = Integer.MAX_VALUE;
			int[] evalResult=new int[3];
			int[] bestMove = new int[3]; // Stores the row, column, and evaluation score of the best move
			bestMove[0] = row;
			bestMove[1] = col;
			List<int[]> possibleMoves = getPossibleMoves(B, 'B');
			for (int[] move : possibleMoves) {
				char[][] newBoard = copyBoard(B);
				//System.out.println("move in Min: "+move[0]+","+move[1]);
				makeMove(newBoard, move[0], move[1], 'B');
				evalResult = maxmin(newBoard, depth - 1, true, bestMove[0], bestMove[1]);
				//System.out.println("evalResult in min: "+evalResult[0]+","+evalResult[1]+","+evalResult[2]);
				int evalScore=0;
				if(evalResult[2] != 0)
					evalScore = evalResult[2];
				if (evalScore < minEval) {
					minEval = evalScore;
					bestMove[0] = move[0]; // Store the row index of the best move
					bestMove[1] = move[1]; // Store the column index of the best move
					bestMove[2] = evalScore; // Store the evaluation score of the best move
				}
			}
			//System.out.println("Min");
			return bestMove;
		}

	}

	public int heuristic(char[][] B) {
		int player_score = 0;
		int temp = 0;
		char token = 'W';
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < B.length; i++) {
				for (int j = 0; j < B[i].length; j++) {
					///////// check row/////////
					if (i <= 3 && j <= 3) {
						if (B[i][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i][j + 1] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i][j + 1] == token && B[i][j + 2] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i][j + 1] == token && B[i][j + 2] == token
								&& B[i][j + 3] == token) {
							player_score += 50;
						}
					}
					///////// check column/////////
					if (i <= 3 && j <= 3) {
						if (B[i][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j] == token && B[i + 2][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j] == token && B[i + 2][j] == token
								&& B[i + 3][j] == token) {
							player_score += 50;
						}
					}
					///////// check Diagonal/////////
					if (i <= 3 && j <= 3) {
						if (B[i][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j + 1] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j + 1] == token && B[i + 2][j + 2] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j + 1] == token && B[i + 2][j + 2] == token
								&& B[i + 3][j + 3] == token) {
							player_score += 50;
						}
					}
					///////// check AntiDiagonal/////////
					if (i >= 3 && i <= 4 && j >= 3) {
						if (B[i][j] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j - 1] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j - 1] == token && B[i + 2][j - 2] == token) {
							player_score += 5;
						}
						if (B[i][j] == token && B[i + 1][j - 1] == token && B[i + 2][j - 2] == token
								&& B[i + 3][j - 3] == token) {
							player_score += 50;
						}
					}
				}
				if (k>=1)
					return (temp-player_score);
			}
			temp=player_score;
			token='B';
			player_score=0;
		}

		return (temp-player_score);
	}

	public int evaluate(char[][] B) {
		if (isPlayer_1_Win()) {
			return 10000; // Player 1 (W) wins
		} else if (isPlayer_2_Win()) {
			return -10000; // Player 2 (B) wins
		} else {
			//displayBoard(B);
			//System.out.println("h= " + heuristic(B));
			return heuristic(B); // Draw or game not over

		}
	}

	public List<int[]> getPossibleMoves(char[][] Board, char player) {
		List<int[]> possibleMoves = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (Board[i][j] == '-' && is_valid_move(Board, i, j)) {
					possibleMoves.add(new int[] { i, j });
				}
			}
		}
		return possibleMoves;
	}

	//updated minmax with alpha beta
	public int[] maxmin(char[][] B, int depth, int alpha, int beta, boolean maximizingPlayer, int row, int col) {
		// Base case: If the game is over or the maximum depth is reached, return the evaluation score
		if (isGameOver() || depth == 0) {
			return new int[]{row, col, evaluate(B)};
		}

		if (maximizingPlayer) {
			int maxEval = Integer.MIN_VALUE;
			int[] evalResult = new int[3];
			int[] bestMove = new int[3]; // Stores the row, column, and evaluation score of the best move
			bestMove[0] = row;
			bestMove[1] = col;
			List<int[]> possibleMoves = getPossibleMoves(B, 'W');
			for (int[] move : possibleMoves) {
				char[][] newBoard = copyBoard(B);
				makeMove(newBoard, move[0], move[1], 'W');
				evalResult = maxmin(newBoard, depth - 1, alpha, beta, false, bestMove[0], bestMove[1]);
				int evalScore = 0;
				if (evalResult[2] != 0)
					evalScore = evalResult[2];
				if (evalScore > maxEval) {
					maxEval = evalScore;
					bestMove[0] = move[0]; // Store the row index of the best move
					bestMove[1] = move[1]; // Store the column index of the best move
					bestMove[2] = evalScore; // Store the evaluation score of the best move
				}
				alpha = Math.max(alpha, evalScore);
				if (beta <= alpha) {
					break; // Beta cutoff
				}
			}
			return bestMove;
		} else {
			int minEval = Integer.MAX_VALUE;
			int[] evalResult = new int[3];
			int[] bestMove = new int[3]; // Stores the row, column, and evaluation score of the best move
			bestMove[0] = row;
			bestMove[1] = col;
			List<int[]> possibleMoves = getPossibleMoves(B, 'B');
			for (int[] move : possibleMoves) {
				char[][] newBoard = copyBoard(B);
				makeMove(newBoard, move[0], move[1], 'B');
				evalResult = maxmin(newBoard, depth - 1, alpha, beta, true, bestMove[0], bestMove[1]);
				int evalScore = 0;
				if (evalResult[2] != 0)
					evalScore = evalResult[2];
				if (evalScore < minEval) {
					minEval = evalScore;
					bestMove[0] = move[0]; // Store the row index of the best move
					bestMove[1] = move[1]; // Store the column index of the best move
					bestMove[2] = evalScore; // Store the evaluation score of the best move
				}
				beta = Math.min(beta, evalScore);
				if (beta <= alpha) {
					break; // Alpha cutoff
				}
			}
			return bestMove;
		}
	}

}	
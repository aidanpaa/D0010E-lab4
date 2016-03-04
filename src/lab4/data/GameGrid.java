package lab4.data;

import java.util.Observable;

/**
 * Represents the 2-d game grid
 */

public class GameGrid extends Observable{

	private int size;
	public static final int EMPTY = 0;
	public static final int ME = 1;
	public static final int OTHER = -1;
	public static final int INROW = 5;
	
	private int[][] grid;
	
	/**
	 * 
	 * Constructor
	 * 
	 * @param size
	 */
	
	// Gör en tvådimensionell array, size anropas från metoden GomokuGameState i classen GomokuGameState.
	public GameGrid(int size){
		this.size = size;
		grid = new int[size][size];
	}
	
	/**
	 * Reads a location of the grid
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return the value of the specified location
	 */
	
	//Retunerar värdet x och y. Antingen tom, jag ellerk annan. 
	public int getLocation(int x, int y){
		return grid[x][y];
		}
	
	/**
	 * Returns the size of the grid
	 * 
	 * @return the grid size
	 */
	//Den ger längden på griden, detta fall 15 rutor.
	public int getSize(){
		return size;
	}
	
	/**
	 * Enters a move in the game grid
	 * 
	 * @param x the x position
	 * @param y the y position
	 * @param player
	 * @return true if the insertion worked, false otherwise
	 */
	
	//Kollar om positionen av x och y är ledig och tilldelar den till player, and returnerar den false och 'platsen' är upptagen.
	public boolean move(int x, int y, int player){
		if(grid[x][y] == EMPTY){
			grid[x][y]=player;
			setChanged();
			notifyObservers();
			return true;
		}
		return false;
		}
	
	/**
	 * Clears the grid of pieces
	 */
	
	//Lägger ut griden igen, vilket gör att den är tom.
	public void clearGrid(){
		grid = new int[size][size];
		setChanged();
		notifyObservers();
	}
	
	/**
	 * Check if a player has 5 in row
	 * 
	 * @param player the player to check for
	 * @return true if player has 5 in row, false otherwise
	 */
	public boolean isWinner(int player){
		
		for(int i = 0; i<getSize(); i++){
			int inRowH = 0;
			int inRowV = 0;
			
			for(int j = 0; j<getSize(); j++){
				if(grid[i][j]==player){
					inRowV++;
					
					if(inRowV==INROW){
						return true;
					}
				}
				else{
					inRowV = 0;
				}
				
				if(grid[j][i]==player){
					inRowH++;
					
					if(inRowH==INROW){
						return true;
					}
				}
				else{
					inRowH = 0;
				}
			}
		}
		int inRow = 0;
		for(int i = 0; i<getSize(); i++){
			if(grid[getSize()-i-1][i]==player){
				inRow++;
				if(inRow==INROW){
					return true;
				}
			}
			else{
				inRow=0;
			}
			int inRow2 = 0;
			for(int k = 0+i, j = getSize()-1; j>0&&k<getSize(); k++, j--){
				if(grid[j][k]==player){
					inRow2++;
					if(inRow2==INROW){
						return true;
					}
				}
				else{
					inRow2=0;
				}
			}
			int inRow3 = 0;
			for(int k = 0, j = getSize()-1-i; j>=0&&k<getSize(); k++, j--){
				if(grid[j][k]==player){
					inRow3++;
					if(inRow3==INROW){
						return true;
					}
				}
				else{
					inRow3=0;
				}
			}
			
		}
		
		int inRow4 = 0;
		for(int i = 0; i<getSize(); i++){
			if(grid[i][i]==player){
				inRow4++;
				if(inRow4==INROW){
					return true;
				}
			}
			else{
				inRow4=0;
			}
			int inRow5 = 0;
			int inRow6 = 0;
			for(int k = 0, j = i+1; j<getSize(); j++, k++){
				
				if(grid[k][j]==player){
					inRow5++;
					if(inRow5==INROW){
						return true;
					}
				}
				else{
					inRow5=0;
				}
				
				if(grid[j][k]==player){
					inRow6++;
					if(inRow6==INROW){
						return true;
					}
				}
				else{
					inRow6=0;
				}
			}
		}
		
		
		
		return false;
		}
	
	
}
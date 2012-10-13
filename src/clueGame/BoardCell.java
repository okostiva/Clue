package clueGame;

public abstract class BoardCell implements Comparable<BoardCell> {

	private int row;
	private int col;
	
	public BoardCell() {
		// TODO Auto-generated constructor stub
		row = -1;
		col = -1;
	}
	
	public BoardCell(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
	
	public boolean isWalkway()
	{
		return false;
	}
	
	public boolean isRoom()
	{
		return false;
	}
	
	public boolean isDoorway()
	{
		return false;
	}
	
	public int compareTo(BoardCell b)
	{
		BoardCell currentCell = (BoardCell)b;
		
		if (currentCell.row == this.row)
		{
			if (currentCell.col == this.col)
				return 0;
			else if (currentCell.col > this.col)
				return -1;
			else 
				return 1;
		}
		else if (currentCell.row > this.row)
			return -1;
		else 
			return 1;
	}
	
	public abstract void draw();
}

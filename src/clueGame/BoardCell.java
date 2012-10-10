package clueGame;

public abstract class BoardCell {

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
	
	public abstract void draw();
}

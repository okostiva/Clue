package clueGame;

public class WalkwayCell extends BoardCell {

	public WalkwayCell() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public WalkwayCell(int row, int col) {
		super (row, col);
	}
	
	@Override
	public boolean isWalkway()
	{
		return true;
	}

	@Override
	public void draw() {
		return;
	}
}

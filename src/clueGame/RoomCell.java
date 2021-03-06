package clueGame;

public class RoomCell extends BoardCell {

	public enum DoorDirection {UP, DOWN, LEFT, RIGHT, NONE};
	
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public RoomCell() {
		// TODO Auto-generated constructor stub
		super();
		doorDirection = DoorDirection.NONE;
		roomInitial = '?';
	}
	
	public RoomCell(DoorDirection doorDirection, char roomInitial, int row, int col) {
		super(row, col);
		this.doorDirection = doorDirection;
		this.roomInitial = roomInitial;
	}
	
	public DoorDirection getDoorDirection()
	{
		return doorDirection;
	}
	
	public char getRoomInitial()
	{
		return roomInitial;
	}
	
	@Override
	public boolean isRoom()
	{
		return true;
	}
	
	@Override
	public boolean isDoorway()
	{
		if (this.doorDirection.equals(DoorDirection.NONE))
			return false;
		else
			return true;
	}
	
	@Override
	public void draw()
	{
		return;
	}
}

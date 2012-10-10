package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {

	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
		
	public Board() {
		// TODO Auto-generated constructor stub
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		numRows = 0;
		numColumns = 0;
		
		loadConfigFiles();
	}
	
	public BoardCell getCellAt(int index)
	{
		return cells.get(index);
	}
	
	public Map<Character, String> getRooms()
	{
		return rooms;
	}
	
	public int getNumRows()
	{
		return numRows;
	}
	
	public int getNumColumns()
	{
		return numColumns;
	}

	public void loadConfigFiles()
	{
		return;
	}
	
	public int calcIndex (int row, int col)
	{
		return 0;
	}
	
	public RoomCell getRoomCellAt (int row, int col)
	{
		return new RoomCell();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

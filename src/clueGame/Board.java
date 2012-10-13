package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

public class Board {
	
	private Map<Integer, LinkedList<Integer>> adjMatrix;
	private TreeSet<BoardCell> targets;
	private Stack<BoardCell> path;
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private int numRows;
	private int numColumns;
	
	//Use this section when testing my config file
	private static final String LEGEND_FILE = "~/../Legend.csv";
	private static final String CONFIG_FILE = "~/../Layout.csv";
	
	//Use this section when testing the teacher's config files
	//private static final String LEGEND_FILE = "~/../TeacherLegend.csv";
	//private static final String CONFIG_FILE = "~/../TeacherLayout.csv";
	
	public Board() {
		// TODO Auto-generated constructor stub
		adjMatrix = new HashMap<Integer, LinkedList<Integer>>();
		targets = new TreeSet<BoardCell>();
		path = new Stack<BoardCell>();
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		numRows = -1;
		numColumns = -1;
		
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public BoardCell getCellAt(int index)
	{
		if (cells.get(index).isWalkway())
			return (WalkwayCell)cells.get(index);
		else
			return (RoomCell)cells.get(index);
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
		try {
			loadLegend();
			
			loadRoomConfig();
			
		} catch (BadConfigFormatException ex) {
			System.out.println(ex.toString());
		}
		
		return;
	}
	
	public int calcIndex (int row, int col)
	{
		return (row*numColumns) + col;
	}
	
	public RoomCell getRoomCellAt (int row, int col)
	{
		return (RoomCell)cells.get(calcIndex(row, col));
	}
	
	public void loadLegend() throws BadConfigFormatException {		
		try {
			FileReader reader = new FileReader(LEGEND_FILE);
			Scanner scan = new Scanner(reader);
			
			while (scan.hasNext())
			{
				String inputLine = scan.nextLine();
				String [] legendLine = inputLine.split(",");
				char initial;
				String roomName;
				
				if (legendLine.length == 2)
				{
					initial = legendLine[0].trim().charAt(0);
					roomName = legendLine[1].trim();
					
					rooms.put(initial, roomName);
				}
				else
				{
					throw new BadConfigFormatException(LEGEND_FILE, "File contains more than 2 items per line");
				}
			}
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(LEGEND_FILE + " could not be found!");
		}
		
		return;
	}
	
	public void loadRoomConfig() throws BadConfigFormatException {
		try {
			FileReader reader = new FileReader(CONFIG_FILE);
			Scanner scan = new Scanner(reader);
			int currentRow = -1;
			int currentCol = -1;
			
			while (scan.hasNext())
			{
				currentCol = -1;
				currentRow++;
				
				String inputLine = scan.nextLine();
				String [] roomLine = inputLine.split(",");
				
				if (numColumns == -1)
				{
					numColumns = roomLine.length;
				}
				else if (numColumns != roomLine.length)
				{
					throw new BadConfigFormatException(CONFIG_FILE, "Not all of the rows have the same number of columns");
				}
				
				for (String s : roomLine)
				{
					currentCol++;
					
					if (rooms.containsKey(s.trim().charAt(0)))
					{
						if (s.length() > 1)
						{
							if (s.charAt(1) == 'U')
							{
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.UP, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
							else if (s.charAt(1) == 'R')
							{
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.RIGHT, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
							else if (s.charAt(1) == 'D')
							{
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.DOWN, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
							else if (s.charAt(1) == 'L')
							{
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.LEFT, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
							else 
							{
								//This will later be used to determine where the name goes on the room board
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.NONE, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
						}
						else
						{
							if (s.charAt(0) == 'W')
							{
								WalkwayCell newWalkway = new WalkwayCell(currentRow, currentCol);
								cells.add(newWalkway);
							}
							else 
							{
								RoomCell newRoom = new RoomCell(RoomCell.DoorDirection.NONE, s.charAt(0), currentRow, currentCol);
								cells.add(newRoom);
							}
						}
					}
					else
					{
						throw new BadConfigFormatException(CONFIG_FILE, "One or more room initials do not correspond to a valid room");
					}
				}
			}
			
			numRows = currentRow+1;
		}
		catch (FileNotFoundException ex)
		{
			System.out.println(CONFIG_FILE + " could not be found!");
		}
		
		return;
	}
	
	public void calcAdjacencies()
	{
		for (int i=0; i<numRows; i++)
		{
			for (int j=0; j<numColumns; j++)
			{
				LinkedList<Integer> adjacencies = new LinkedList<Integer>();
				int index = calcIndex(i,j);
				BoardCell currentCell = getCellAt(index);
				
				//We need special logic if the current cell is a room
				if (currentCell.isRoom())
				{
					if (((RoomCell) currentCell).isDoorway())
					{
						RoomCell.DoorDirection doorDirection = ((RoomCell) currentCell).getDoorDirection();
						
						if (doorDirection == RoomCell.DoorDirection.UP)
							adjacencies.add(calcIndex((i-1), j));
						else if (doorDirection == RoomCell.DoorDirection.DOWN)
							adjacencies.add(calcIndex((i+1), j));
						else if (doorDirection == RoomCell.DoorDirection.LEFT)
							adjacencies.add(calcIndex(i, (j-1)));
						else if (doorDirection == RoomCell.DoorDirection.RIGHT)
							adjacencies.add(calcIndex(i, (j+1)));
					}
				}
				else
				{
					//We are testing the cell directly above the current cell so we 
					//must test to be sure that the doorway faces down
					if ((i-1) >= 0) {
						int tempIndex = calcIndex((i-1), j);
						BoardCell tempCell = getCellAt(tempIndex);
						if (tempCell.isWalkway())
							adjacencies.add(tempIndex);
						else if (tempCell.isDoorway())
						{
							if (((RoomCell)tempCell).getDoorDirection() == RoomCell.DoorDirection.DOWN)
								adjacencies.add(tempIndex);
						}
					}
					
					//We are testing the cell directly to the left of the current cell
					//so we must test to be sure the doorway faces right
					if ((j-1) >= 0) {
						int tempIndex = calcIndex(i, (j-1));
						BoardCell tempCell = getCellAt(tempIndex);
						if (tempCell.isWalkway())
							adjacencies.add(tempIndex);
						else if (tempCell.isDoorway())
						{
							if (((RoomCell) tempCell).getDoorDirection() == RoomCell.DoorDirection.RIGHT)
								adjacencies.add(tempIndex);
						}
					}
					
					//We are testing the cell directly below the current cell so we
					//must test to be sure the doorway faces up
					if ((i+1) < numRows) {
						int tempIndex = calcIndex((i+1), j);
						BoardCell tempCell = getCellAt(tempIndex);
						if (tempCell.isWalkway())
							adjacencies.add(tempIndex);
						else if (tempCell.isDoorway())
						{
							if (((RoomCell) tempCell).getDoorDirection() == RoomCell.DoorDirection.UP)
								adjacencies.add(tempIndex);
						}
					}
					
					//We are testing the cell directly to the right of the current cell
					//so we must test to be sure that the doorway faces left
					if ((j+1) < numColumns) {
						int tempIndex = calcIndex(i, (j+1));
						BoardCell tempCell = getCellAt(tempIndex);
						if (tempCell.isWalkway())
							adjacencies.add(tempIndex);
						else if (tempCell.isDoorway())
						{
							if (((RoomCell) tempCell).getDoorDirection() == RoomCell.DoorDirection.LEFT);
								adjacencies.add(tempIndex);
						}
					}
				}
				
				adjMatrix.put(index, adjacencies);
			}			
		}
		
		return;
	}

	public void calcTargets(int startLocation, int numSteps)
	{
		if (path.empty())
			targets.clear();
		
		path.push(getCellAt(startLocation));
		
		if ((numSteps+1) == path.size())
		{
			if (!targets.contains(path.lastElement()))
				targets.add(path.lastElement());
		}
		//Check to make sure that we aren't trying to add the starting location to the path
		else if (path.lastElement().isRoom() && (path.size() > 1))
		{
			if (!(((RoomCell) path.lastElement()).getDoorDirection() == RoomCell.DoorDirection.NONE))
				if (!targets.contains(path.lastElement()))
					targets.add(path.lastElement());
		}
		else
		{
			for (Integer a : getAdjList(startLocation))
			{
				if (!path.contains(getCellAt(a)))
					calcTargets(a, numSteps);
			}
		}
		
		path.pop();
		
		return;
	}
	
	public LinkedList<Integer> getAdjList(int index)
	{
		return adjMatrix.get(index);
	}
	
	public TreeSet<BoardCell> getTargets()
	{
		return targets;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}

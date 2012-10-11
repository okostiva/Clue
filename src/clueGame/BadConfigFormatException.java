package clueGame;

public class BadConfigFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2869261736033132885L;
	private String file;
	private String ex;
	
	
	public BadConfigFormatException() {
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String file, String ex) {
		super();
		this.ex = ex;
		this.file = file;
		// TODO Auto-generated constructor stub
	}

	public String toString()
	{
		return "ERROR in " + file + ": " + ex;
	}

}

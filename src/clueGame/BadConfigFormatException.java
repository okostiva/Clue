package clueGame;

public class BadConfigFormatException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2869261736033132885L;
	private String ex;
	
	public BadConfigFormatException() {
		// TODO Auto-generated constructor stub
	}

	public BadConfigFormatException(String ex) {
		super();
		this.ex = ex;
		// TODO Auto-generated constructor stub
	}

	public String toString()
	{
		return ex;
	}

}

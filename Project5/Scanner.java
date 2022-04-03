import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Scanner {

	private BufferedReader input;
	private Core current;
	private int next;
	private String token = "";
	
	// Create an array of String to collect 
	private String[] keywords = {"program", "begin", "end", "new", "int", "define", "endfunc", "class",
									"extends", "endclass", "if", "then", "else", "while", "endwhile",
									"endif", "or", "input", "output", "ref"};
	private Core[] tokenName = {Core.PROGRAM, Core.BEGIN, Core.END, Core.NEW, Core.INT, Core.DEFINE, Core.ENDFUNC,
									Core.CLASS, Core.EXTENDS, Core.ENDCLASS, Core.IF, Core.THEN, Core.ELSE, 
									Core.WHILE, Core.ENDWHILE, Core.ENDIF, Core.OR, Core.INPUT, Core.OUTPUT, 
									Core.REF};
	// Constructor should open the file and find the first token
	Scanner(String filename) {
		try
		{
			this.input = new BufferedReader(new FileReader(filename));
			this.nextToken();
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File not found!");
		}
	}

	// nextToken should advance the scanner to the next token
	public void nextToken() {
		try
		{
			token = "";
			next = this.input.read();
			if(next == -1)
				this.current = Core.EOF;
			else if(Character.isWhitespace((char)next))
				this.nextToken();
			else if((char)next == ';')
				this.current = Core.SEMICOLON;
			else if((char)next == '(')
				this.current = Core.LPAREN;
			else if((char)next == ')')
				this.current = Core.RPAREN;
			else if((char)next == ',')
				this.current = Core.COMMA;
			else if((char)next == '!')
				this.current = Core.NEGATION;
			else if((char)next == '+')
				this.current = Core.ADD;
			else if((char)next == '-')
				this.current = Core.SUB;
			else if((char)next == '*')
				this.current = Core.MULT;
		
			else if((char)next == '=')
			{
				this.input.mark(1);
				next = this.input.read();
				if((char)next == '=')
					this.current = Core.EQUAL;
				else
				{
					this.current = Core.ASSIGN;
					this.input.reset();
				}
			}
			else if((char)next == '<')
			{
				this.input.mark(1);
				next = this.input.read();
				if((char)next == '=')
					this.current = Core.LESSEQUAL;
				else
				{
					this.current = Core.LESS;
					this.input.reset();
				}
			}

			else if(Character.isDigit((char)next))
			{
				token += (char)next;
				while(Character.isDigit((char)next))
				{
					this.input.mark(1);
					next = this.input.read();
					token += (char)next;
				}
				this.input.reset();
				token = token.substring(0, token.length() - 1);
				if(token.length() < 5)
				{
					if(Integer.parseInt(token) < 1024)
						this.current = Core.CONST;
				}
				else
				{
					this.current = Core.ERROR;
					System.out.println("ERROR: Constant too large: " + token);
				}
					
			}
			else if(Character.isLetter((char)next))
			{
				token += (char)next;
				while(Character.isDigit((char)next) || Character.isLetter((char)next))
				{
					this.input.mark(1);
					next = this.input.read();
					token += (char)next;
				}
				this.input.reset();
				token = token.substring(0, token.length() - 1);
				int index = -1;
				for(int i = 0; i < keywords.length; i++)
				{
					if(token.equals(keywords[i]))
					{
						index = i;
						this.current = tokenName[i];
					}
				}
				if(index == -1)
					this.current = Core.ID;
			}
			else
			{
				token += (char)next;
				this.current = Core.ERROR;
				System.out.println("ERROR: Invalid input: \"" + token + "\"");
			}
		}
		catch(IOException e)
		{
			System.out.println("ERROR: File input error!");
			this.current = Core.ERROR;
		}
	}

	// currentToken should return the current token
	public Core currentToken() {
		return current;
	}

	// If the current token is ID, return the string value of the identifier
	// Otherwise, return value does not matter
	public String getID() {
		return token;
	}

	// If the current token is CONST, return the numerical value of the constant
	// Otherwise, return value does not matter
	public int getCONST() {
		return Integer.parseInt(token);
	}

	// Check if the current token is what we want
	// If not, stop running code
	public void isExpectedToken(Core c)
	{
		if(this.currentToken() != c)
		{
			System.out.println("ERROR: Found \"" + current + "\", Expected token \""+ c +"\" not found");
			System.exit(0);
		}
	}
}
class Main {
	public static void main(String[] args) {
		// Initialize the scanner with the input file
		// and the IdCheck and Program classes
		Scanner S = new Scanner(args[0]);
		Scanner Input = new Scanner(args[1]);
		Executor.initialize(Input);
		Program P = new Program();

		// Generally parse the program by using scanner
		P.Parse(S);
		P.Execute();
	}
}
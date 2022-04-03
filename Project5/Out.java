class Out
{
    Expr E = new Expr();
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.nextToken();
        E.Parse(S);
        S.isExpectedToken(Core.SEMICOLON);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        System.out.println(E.Execute());
    }
}

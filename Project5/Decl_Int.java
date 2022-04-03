class Decl_Int
{
    Id_List I = new Id_List();
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.INT);
        S.nextToken();
        I.Parse(S);
        S.isExpectedToken(Core.SEMICOLON);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        I.Execute(Core.INT);
    }
}

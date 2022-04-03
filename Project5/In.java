class In
{
    String Id;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.nextToken();
        S.isExpectedToken(Core.ID);
        Id = S.getID();
        S.nextToken();
        S.isExpectedToken(Core.SEMICOLON);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        Executor.setIdValue(Id, Executor.nextInput());
    }
}

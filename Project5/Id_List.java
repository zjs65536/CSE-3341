class Id_List
{
    Id_List IdL;
    
    // 0 for "id , <id-list>"
    // 1 for "id"
    int OptionNum;
    String Id;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.ID);
        Id = S.getID();
        S.nextToken();
        if(S.currentToken() == Core.COMMA)
        {
            OptionNum = 0;
            S.nextToken();
            IdL = new Id_List();
            IdL.Parse(S);
        }
        else
        {
            OptionNum = 1;
        }
    }

    // The method to execute this part of code
    public void Execute(Core type)
    {
        Executor.declareId(Id, type);
        if(IdL != null)
            IdL.Execute(type);
    }
}

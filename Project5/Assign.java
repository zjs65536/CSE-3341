class Assign
{
    Expr E;

    // 0 for "id = <expr> ;"
    // 1 for "id = new ;"
    // 2 for "id = ref id ;"
    int OptionNum;
    String Id1, Id2;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        Id1 = S.getID();
        S.nextToken();
        S.isExpectedToken(Core.ASSIGN);
        S.nextToken();
        if(S.currentToken() == Core.ID || S.currentToken() == Core.CONST || S.currentToken() == Core.LPAREN)
        {
            OptionNum = 0;
            E = new Expr();
            E.Parse(S);
        }
        else if(S.currentToken() == Core.NEW)
        {
            OptionNum = 1;
            S.nextToken();
        }
        else if(S.currentToken() == Core.REF)
        {
            OptionNum = 2;
            S.nextToken();
            S.isExpectedToken(Core.ID);
            Id2 = S.getID();
            S.nextToken();
        }
        else
        {
            System.out.println("ERROR: Invalid input \"" + S.getID() + "\"");
            System.exit(0);
        }
        S.isExpectedToken(Core.SEMICOLON);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(OptionNum == 0)
            Executor.setIdValue(Id1, E.Execute());
        else if(OptionNum == 1)
            Executor.newRef(Id1);
        else
            Executor.refToRef(Id1, Id2);
    }
}

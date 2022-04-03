class Factor
{
    Expr E;

    // 0 for "id"
    // 1 for "const"
    // 2 for "( <expr> )"
    int OptionNum;
    int Const;
    String Id;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        if(S.currentToken() == Core.ID)
        {
            OptionNum = 0;
            Id = S.getID();
        }
        else if(S.currentToken() == Core.CONST)
        {
            OptionNum = 1;
            Const = S.getCONST();
        }
        else if(S.currentToken() == Core.LPAREN)
        {
            OptionNum = 2;
            E = new Expr();
            S.nextToken();
            E.Parse(S);
            S.isExpectedToken(Core.RPAREN);
        }
        else
        {
            System.out.println("ERROR: Invalid Input: \"" + S.currentToken().toString() + "\"");
            System.exit(0);
        }
        S.nextToken();
    }

    // The method to execute this part of code
    public int Execute()
    {
        int res;
        if(OptionNum == 0)
            res = Executor.getIdValue(Id);
        else if(OptionNum == 1)
            res = Const;
        else
            res = E.Execute();
        return res;
    }
}

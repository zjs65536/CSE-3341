class Expr 
{
    Term T = new Term();
    Expr E;

    // 0 for "<term> + <expr>"
    // 1 for "<term> - <expr>"
    // 2 for "<term>"
    int OptionNum;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        T.Parse(S);
        if(S.currentToken() == Core.ADD || S.currentToken() == Core.SUB)
        {
            if(S.currentToken() == Core.ADD)
                OptionNum = 0;
            else
                OptionNum = 1;

            S.nextToken();
            E = new Expr();
            E.Parse(S);
        }
        else
            OptionNum = 2;
    }

    // The method to execute this part of code
    public int Execute()
    {
        int res = T.Execute();
        if(OptionNum == 0)
            res += E.Execute();
        else if(OptionNum == 1)
            res -= E.Execute();
        return res;
    }
}
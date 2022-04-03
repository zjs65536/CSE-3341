class Cmpr
{
    Expr E1 = new Expr();
    Expr E2 = new Expr();

    // 0 for "<expr> == <expr>"
    // 1 for "<expr> < <expr>"
    // 2 for "<expr> <= <expr>"
    int OptionNum;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        E1.Parse(S);
        if(S.currentToken() == Core.EQUAL)
            OptionNum = 0;
        else if(S.currentToken() == Core.LESS)
            OptionNum = 1;
        else if(S.currentToken() == Core.LESSEQUAL)
            OptionNum = 2;
        
        S.nextToken();
        E2.Parse(S);
    }

    // The method to execute this part of code
    public boolean Execute()
    {
        boolean res = false;
        if(OptionNum == 0)
            res = E1.Execute() == E2.Execute();
        if(OptionNum == 1)
            res = E1.Execute() < E2.Execute();
        if(OptionNum == 2)
            res = E1.Execute() <= E2.Execute();
        return res;
    }
}

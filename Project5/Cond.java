class Cond
{
    Cmpr Cm;
    Cond Co;
    
    // 0 for "! ( <cond> )"
    // 1 for "<cmpr> or <cond>"
    // 2 for "<cmpr>"
    int OptionNum;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        if(S.currentToken() == Core.NEGATION)
        {
            OptionNum = 0;
            S.nextToken();
            S.isExpectedToken(Core.LPAREN);
            S.nextToken();
            Co = new Cond();
            Co.Parse(S);
            S.isExpectedToken(Core.RPAREN);
            S.nextToken();
        }
        else
        {
            Cm = new Cmpr();
            Cm.Parse(S);
            if(S.currentToken() == Core.OR)
            {
                OptionNum = 1;
                S.nextToken();
                Co = new Cond();
                Co.Parse(S);
            }
            else
            {
                OptionNum = 2;
            }
        }
    }

    // The method to execute this part of code
    public boolean Execute()
    {
        boolean res;
        if(OptionNum == 0)
            res = !Co.Execute();
        else if(OptionNum == 1)
            res = Co.Execute() | Cm.Execute();
        else
            res = Cm.Execute();
        return res;
    }
}

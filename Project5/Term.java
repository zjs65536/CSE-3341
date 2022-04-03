class Term
{
    Factor F = new Factor();
    Term T;

    int OptionNum;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        F.Parse(S);
        if(S.currentToken() == Core.MULT)
        {
            OptionNum = 0;
            T = new Term();
            S.nextToken();
            T.Parse(S);
        }
        else
            OptionNum = 1;
    }

    // The method to execute this part of code
    public int Execute()
    {
        int res = F.Execute();
        if(OptionNum == 0)
            res *= T.Execute();
        return res;
    }
}

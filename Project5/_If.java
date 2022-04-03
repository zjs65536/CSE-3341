import java.util.HashMap;

class _If
{
    Cond C;
    Stmt_Seq Ss1;
    Stmt_Seq Ss2;

    // 0 for "if <cond> then <stmt-seq> else <stmt-seq> endif"
    // 1 for "if <cond> then <stmt-seq> endif"
    int OptionNum;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.IF);
        S.nextToken();
        C = new Cond();
        C.Parse(S);
        S.isExpectedToken(Core.THEN);
        S.nextToken();
        Ss1 = new Stmt_Seq();
        Ss1.Parse(S, false);
        if(S.currentToken() == Core.ELSE)
        {
            OptionNum = 0;
            S.nextToken();
            Ss2 = new Stmt_Seq();
            Ss2.Parse(S, false);
        }
        else
        {
            OptionNum = 1;
        }
        S.isExpectedToken(Core.ENDIF);
        S.nextToken();
    }
    // The method to execute this part of code
    public void Execute()
    {
        Executor.stackSpace.push(new HashMap<String, CoreVar>());
        if(C.Execute())
            Ss1.Execute();
        else
            if(OptionNum == 0)
                Ss2.Execute();
        Executor.popScopeGarbageCollect(Executor.stackSpace.pop());
    }
}

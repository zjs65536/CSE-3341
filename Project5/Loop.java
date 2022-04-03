import java.util.HashMap;

class Loop
{
    Cond C = new Cond();
    Stmt_Seq Ss = new Stmt_Seq();
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.WHILE);
        S.nextToken();
        C.Parse(S);
        S.isExpectedToken(Core.BEGIN);
        S.nextToken();
        Ss.Parse(S, false);
        S.isExpectedToken(Core.ENDWHILE);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        while(C.Execute())
        {
            Executor.stackSpace.push(new HashMap<String, CoreVar>());
            Ss.Execute();
            Executor.popScopeGarbageCollect(Executor.stackSpace.pop());
        }
    }
}

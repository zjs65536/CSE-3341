import java.util.HashMap;

class Program
{
    Decl_Seq Ds;
    Stmt_Seq Ss;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.PROGRAM);
        S.nextToken();
        if(S.currentToken() != Core.BEGIN)
        {
            Ds = new Decl_Seq();
            Ds.Parse(S);
        }
        S.isExpectedToken(Core.BEGIN);
        S.nextToken();
        Ss = new Stmt_Seq();
        Ss.Parse(S, false);
        S.isExpectedToken(Core.END);
        S.nextToken();
        S.isExpectedToken(Core.EOF);
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(Ds != null)
            Ds.Execute();
        Executor.stackSpace.push(new HashMap<String, CoreVar>());
        Ss.Execute();
        Executor.popScopeGarbageCollect(Executor.stackSpace.pop());
        Executor.popScopeGarbageCollect(Executor.globalSpace);
    }
}
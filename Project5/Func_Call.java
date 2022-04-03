import java.util.List;

class Func_Call
{
    String FuncName;
    Formals F;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.nextToken();
        S.isExpectedToken(Core.ID);
        FuncName = S.getID();
        S.nextToken();
        S.isExpectedToken(Core.LPAREN);
        S.nextToken();
        F = new Formals();
        F.Parse(S);
        S.isExpectedToken(Core.RPAREN);
        S.nextToken();
        S.isExpectedToken(Core.SEMICOLON);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(!Executor.funcDeclared(FuncName))
        {
            System.out.println("ERROR: Function \"" + FuncName + "\" not declared!");
            System.exit(0);
        }
        Func_Decl Fd = Executor.getFuncDecl(FuncName);
        Formals FuncFormals = Fd.F;
        Stmt_Seq FuncStmtSeq = Fd.Ss;
        List<String> FuncFList = FuncFormals.Execute(true);
        List<String> RealFList = F.Execute(false);
        Executor.addFuncScope(FuncFList, RealFList);
        FuncStmtSeq.Execute();
        Executor.popScopeGarbageCollect(Executor.stackSpace.pop());;
    }
}
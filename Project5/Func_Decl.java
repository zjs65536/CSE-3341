class Func_Decl {

    String FuncName;
    Formals F;
    Stmt_Seq Ss;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        FuncName = S.getID();
        S.nextToken();
        S.isExpectedToken(Core.LPAREN);
        S.nextToken();
        S.isExpectedToken(Core.REF);
        S.nextToken();
        F = new Formals();
        F.Parse(S);
        S.isExpectedToken(Core.RPAREN);
        S.nextToken();
        S.isExpectedToken(Core.BEGIN);
        S.nextToken();

        Ss = new Stmt_Seq();
        Ss.Parse(S, true);
        S.isExpectedToken(Core.ENDFUNC);
        S.nextToken();
    }

    // The method to execute this part of code
    public void Execute()
    {
        Executor.addFunction(FuncName, this);
    }
}

class Stmt_Seq
{
    Stmt St;
    Stmt_Seq Ss;
        
    // The main method to parse the code
    public void Parse(Scanner S, boolean isFuncDecl)
    {
        if(isFuncDecl && S.currentToken() == Core.ENDFUNC)
        {
            System.out.println("ERROR: Function body can't be empty!");
            System.exit(0);
        }
        if(S.currentToken() != Core.END 
            && S.currentToken() != Core.ENDIF 
            && S.currentToken() != Core.ENDWHILE 
            && S.currentToken() != Core.ELSE 
            && S.currentToken() != Core.ENDFUNC)
        {
            St = new Stmt();
            St.Parse(S);
        }
        if(St != null)
        {
            Ss = new Stmt_Seq();
            Ss.Parse(S, false);
        }
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(St != null)
            St.Execute();
        if(Ss != null)
            Ss.Execute();
    }
}
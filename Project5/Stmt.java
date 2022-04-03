class Stmt
{
    Assign A;
    _If If;
    Loop L;
    In In;
    Out O;
    Decl_Int Di;
    Decl_Class Dc;
    Func_Call Fc;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        if(S.currentToken() == Core.ID)
        {
            A = new Assign();
            A.Parse(S);
        }
        else if(S.currentToken() == Core.IF)
        {
            If = new _If();
            If.Parse(S);
        }
        else if(S.currentToken() == Core.WHILE)
        {
            L = new Loop();
            L.Parse(S);
        }
        else if(S.currentToken() == Core.INPUT)
        {
            In = new In();
            In.Parse(S);
        }
        else if(S.currentToken() == Core.OUTPUT)
        {
            O = new Out();
            O.Parse(S);
        }
        else if(S.currentToken() == Core.INT)
        {
            Di = new Decl_Int();
            Di.Parse(S);
        }
        else if(S.currentToken() == Core.REF)
        {
            Dc = new Decl_Class();
            Dc.Parse(S);
        }
        else if(S.currentToken() == Core.BEGIN)
        {
            Fc = new Func_Call();
            Fc.Parse(S);
        }
        else
        {
            System.out.println("ERROR: Invalid statement");
            System.exit(0);
        }
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(A != null)
        {
            A.Execute();
        }
        else if(If != null)
        {
            If.Execute();
        }
        else if(L != null)
        {
            L.Execute();
        }
        else if(In != null)
        {
            In.Execute();
        }
        else if(O != null)
        {
            O.Execute();
        }
        else if(Di != null)
        {
            Di.Execute();
        }
        else if(Dc != null)
        {
            Dc.Execute();
        }
        else if(Fc != null)
        {
            Fc.Execute();
        }
    }
}

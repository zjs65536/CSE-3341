class Decl_Seq
{
    Func_Decl Fd;
    Decl D;
    Decl_Seq Ds;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        if(S.currentToken() == Core.ID)
        {
            Fd = new Func_Decl();
            Fd.Parse(S);
        }
        else if(S.currentToken() != Core.BEGIN)
        {
            D = new Decl();
            D.Parse(S);
        }
        if(Fd != null || D != null)
        {
            Ds = new Decl_Seq();
            Ds.Parse(S);
        }
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(Fd != null)
            Fd.Execute();
        if(D != null)
            D.Execute();
        if(Ds != null)
            Ds.Execute();
    }
}
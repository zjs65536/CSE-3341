class Decl
{
    Decl_Int Di;
    Decl_Class Dc;
        
    // The main method to parse the code
    public void Parse(Scanner S)
    {
        if(S.currentToken() == Core.INT)
        {
            Di = new Decl_Int();
            Di.Parse(S);
        }
        else if(S.currentToken() == Core.REF)
        {
            Dc = new Decl_Class();
            Dc.Parse(S);
        }
        else
        {
            System.out.println("ERROR: Invalid declare sequence");
        }
    }

    // The method to execute this part of code
    public void Execute()
    {
        if(Di != null)
            Di.Execute();
        else
            Dc.Execute();
    }
}
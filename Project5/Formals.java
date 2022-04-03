import java.util.ArrayList;
import java.util.List;

class Formals {
    
    String Id;
    Formals F;

    // The main method to parse the code
    public void Parse(Scanner S)
    {
        S.isExpectedToken(Core.ID);
        Id = S.getID();
        S.nextToken();
        if(S.currentToken() == Core.COMMA)
        {
            S.nextToken();
            F = new Formals();
            F.Parse(S);
        }
    }

    // The method to execute this part of code, 
    // returns a list of Ids in the list of formals.
    public List<String> Execute(boolean isForFuncDecl)
    {
        List<String> params;
        if(F != null)
            params = F.Execute(isForFuncDecl);
        else
            params = new ArrayList<>();
        if(isForFuncDecl)
        {
            for (String param : params) {
                if(param.equals(Id))
                {
                    System.out.println("ERROR: Parameter name \"" + Id + "\" appeared more than once!");
                    System.exit(0);
                }
            }
        }
        params.add(Id);
        return params;
    }
}

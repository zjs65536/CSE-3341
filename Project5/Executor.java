import java.util.*;

class CoreVar {
    Core type;
    Integer value;
}

class Executor {
    static HashMap<String, CoreVar> globalSpace;
    static HashMap<String, Func_Decl> functionSpace;
    static Stack<HashMap<String, CoreVar>> stackSpace;
    static ArrayList<Integer> heapSpace;
    static ArrayList<Integer> refCount;
    static int refCountSize;

    static Scanner InputScanner;

    // Initialize the scopes as stacks and hashmaps
    static void initialize(Scanner S) 
    {
        globalSpace = new HashMap<String, CoreVar>();
        functionSpace = new HashMap<String, Func_Decl>();
        stackSpace = new Stack<HashMap<String, CoreVar>>(); 
        heapSpace = new ArrayList<Integer>();
        refCount = new ArrayList<Integer>();
        refCountSize = 0;
        InputScanner = S;
    }

    // Method to get a ref variable's index in heapSpace
    static int getRefIndex(String Id)
    {
        for (int i = stackSpace.size() - 1; i >= 0; i--)
            if(stackSpace.get(i).containsKey(Id))
                return stackSpace.get(i).get(Id).value;
        if(globalSpace.containsKey(Id))
            return globalSpace.get(Id).value;
        return 0;
    }

    // Method to get the actual value of an Id from scopes
    // If Id's type is INT, directly find its value
    // If Id's type is REF, get its index value and go through heapSpace to get its actual value
    static int getIdValue(String Id)
    {
        for (int i = stackSpace.size() - 1; i >= 0; i--)
        {
            if (stackSpace.get(i).containsKey(Id))
                if(stackSpace.get(i).get(Id).type == Core.INT)
                    return stackSpace.get(i).get(Id).value;
                else
                    return heapSpace.get(stackSpace.get(i).get(Id).value);
        }
        if(globalSpace.containsKey(Id))
        {
            if(globalSpace.get(Id).type == Core.INT)
                return globalSpace.get(Id).value;
            else
                return heapSpace.get(globalSpace.get(Id).value);
        }
        return 0;
    }

    // Method to declare a new Id
    // If Id's type is INT, the initial value will be 0
    // If Id's type is REF, allocate a new position for it in heapSpace, and its initial value will be null
    static void declareId(String Id, Core type)
    {
        CoreVar C = new CoreVar();
        C.type = type;
        if(type == Core.INT)
            C.value = 0;
        else
            C.value = null;
        if(stackSpace.size() == 0)
            globalSpace.put(Id, C);
        else
        {
            HashMap<String, CoreVar> lastScope = stackSpace.pop();
            lastScope.put(Id, C);
            stackSpace.push(lastScope);
        }
    }

    // Method to set Id's value
    // If Id's type is INT, change its value
    // If Id's type is REF, change its actual value in heapSpace
    static void setIdValue(String Id, Integer value)
    {
        boolean revised = false;
        for (int i = stackSpace.size() - 1; i >= 0; i--)
            if (stackSpace.get(i).containsKey(Id))
            {
                if(stackSpace.get(i).get(Id).type == Core.INT)
                    stackSpace.get(i).get(Id).value = value;
                else
                    heapSpace.set(stackSpace.get(i).get(Id).value, value);
                revised = true;
                break;
            }
        if(globalSpace.containsKey(Id) && !revised)
            if(globalSpace.get(Id).type == Core.INT)
                globalSpace.get(Id).value = value;
            else
                heapSpace.set(globalSpace.get(Id).value, value);
    }

    // Method to deal with "ref = new"
    static void newRef(String Id)
    {
        boolean added = false;
        for (int i = stackSpace.size() - 1; i >= 0; i--)
        {
            if(stackSpace.get(i).containsKey(Id))
            {
                stackSpace.get(i).get(Id).value = heapSpace.size();
                heapSpace.add(null);
                added = true;
                break;
            }
        }
        if(globalSpace.containsKey(Id) && !added)
        {
            globalSpace.get(Id).value = heapSpace.size();
            heapSpace.add(null);
        }

        // Add a new integer 1 to refCount, which means a new space of heap was 
        // allocated and only one ref variable is pointing to it now.
        refCount.add(1);
        refCountSize++;
        System.out.println("gc:" + refCountSize);
    }

    // Method to deal with "ref a = b"
    static void refToRef(String Id1, String Id2)
    {
        Integer tmp = -1;
        Integer Id1Pos = -1;
        if(globalSpace.containsKey(Id2))
            tmp = globalSpace.get(Id2).value;
        for(HashMap<String, CoreVar> maps : stackSpace )
            if(maps.containsKey(Id1))
                tmp = maps.get(Id2).value;

        if(globalSpace.containsKey(Id1))
        {
            Id1Pos = globalSpace.get(Id1).value;
            globalSpace.get(Id1).value = tmp;
        }
        for(HashMap<String, CoreVar> maps : stackSpace )
            if(maps.containsKey(Id1))
            {
                Id1Pos = maps.get(Id1).value;
                maps.get(Id1).value = tmp;
            }
        if(Id1Pos != null)
        {
            int count = refCount.get(Id1Pos) - 1;
            refCount.set(Id1Pos, count);
            if(count == 0)
            {
                refCountSize--;
                System.out.println("gc:" + refCountSize);
            }
        }
        if(tmp != null)
            refCount.set(tmp, refCount.get(tmp) + 1);
    }

    // Method to get the next input reading .data file by using the input scanner
    static int nextInput()
    {
        int res = InputScanner.getCONST();
        InputScanner.nextToken();
        return res;
    }

    // Method to store a new declared function in the functionSpace
    static void addFunction(String funcName, Func_Decl Fd)
    {
        for (String name : functionSpace.keySet()) {
            if(name.equals(funcName))
            {
                System.out.println("ERROR: function name \"" + funcName + "\" already declared. Overloading not allowed!");
                System.exit(0);
            }
        }
        functionSpace.put(funcName, Fd);
    }

    // Method to get the stored formals and body by function name
    static Func_Decl getFuncDecl(String funcName)
    {
        return functionSpace.get(funcName);
    }

    // Method to add a new scope to stackSpace
    // First match the two formal list, then put a new hashmap to be the function scope
    // Create new CoreVar for the function formals
    static void addFuncScope(List<String> FuncFList, List<String> RealFList)
    {
        if(FuncFList.size() != RealFList.size())
        {
            System.out.println("ERROR: FuncDecl parameter number doesn't match FuncCall parameter number!");
            System.exit(0);
        }
        HashMap<String, CoreVar> tmp = new HashMap<String, CoreVar>();
        stackSpace.add(tmp);
        for(int i = 0; i < FuncFList.size(); i++)
        {
            String param = FuncFList.get(i);
            CoreVar cVar = new CoreVar();
            cVar.type = Core.REF;
            cVar.value = getRefIndex(RealFList.get(i));
            refCount.set(cVar.value, refCount.get(cVar.value) + 1);
            stackSpace.peek().put(param, cVar);
        }
    }

    // Method to check if the current called function name was declared
    // Return true if declared, false if not declared
    static boolean funcDeclared(String funcName)
    {
        boolean res = false;
        for ( String name : functionSpace.keySet()) {
            if(name.equals(funcName))
                res = true;
        }
        return res;
    }
    
    // Method to check if there are garbage to collect after each time
    // a scope is removed.
    static void popScopeGarbageCollect(HashMap<String, CoreVar> scope)
    {
        for (String Id : scope.keySet())
        {
            if(scope.get(Id).value != null && scope.get(Id).type == Core.REF)
            {
                int count = refCount.get(scope.get(Id).value) - 1;
                refCount.set(scope.get(Id).value, count);
                if(count == 0)
                {
                    refCountSize--;
                    System.out.println("gc:" + refCountSize);
                }
            }
        }
    }
}
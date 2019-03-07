package main.java.fighters;

public class FighterProxy implements Comparable
{
    /** The name of the Fighter this object refers to. */
    private String name;
    /** The ID number of the Fighter this object refers to. */
    private int fighterID;
    
    
    
    @Override
    public int compareTo(Object o)
    {
        if (!(o instanceof Comparable))
        {
            return 1;
        }
        
        return ((FighterProxy) o).toString().compareTo(name);
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}

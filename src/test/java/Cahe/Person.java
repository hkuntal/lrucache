package Cahe;

import Interfaces.CacheValue;

public class Person implements CacheValue {
    private int id;
    private String firstName = "";
    private String lastName = "";
    public long getSize() {
        return lastName.getBytes().length + firstName.getBytes().length;
    }
    public int getId()
    {
        return this.id;
    }
    public void setId(int value)
    {
        this.id = value;
    }
    public String getFirstName()
    {
        return this.firstName;
    }
    public void setFirstName(String fName)
    {
        this.firstName = fName;
    }
    public String getLastName()
    {
        return this.lastName;
    }
    public void setLastName(String lName)
    {
        this.lastName = lName;
    }
}

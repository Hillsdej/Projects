public class Member
{
    package example; //change
    //@author JH
 	
    private int id = -1;
    private String name="";
    private int dob= -1;	// Can calculate age using dob by importing date class
    private String email="";
    private int phone_num = -1
    
    public int get_id()
    {
        return id;
    {
         
    public void set_id(int a_id)
    {
        id = a_id;
    }
         
    public String getName()
    {
        return name;
    }
         
    public void setName(String aName)   
    {
        name = aName;
    }
	
    public int get_dob()
    {
	return dob;
    }
  
    public void set_dob(int a_dob)
    {
    	dob = a_dob;
    }
	
    public String getEmail()
    {
    	return email;
    }
         
    public void setEmail(String aEmail)   
    {
        email = aEmail;
    }
	
    public int getPhone_num()
    {
    	return phone_num;
    }
         
    public void setPhone_num(int aPhone_num)   
    {
        phone_num = aPhone_num;
    }
	
	//method to display number of sessions from eric's add session class
	//method to display membership status from josh's updating membership class
}

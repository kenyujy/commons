
package spring.test;

public class Master { 
    
    private String name;
    private String major;
    
    public Master(){
        this.name="Someone";
        this.major="somemajor";
    }

    public Master(String name, String major){
        this.name=name;
        this.major=major;
    }
  
    public String getName() {  
        return name;  
    }  
  
    public void setName(String name) {  
        this.name = name;  
    }
    
    public void setMajor(String major) {  
        this.major = major;  
    } 
  
    public void displayInfo(){  
        System.out.println("Hello: "+name+","+" Major: "+major);  
    }  
}
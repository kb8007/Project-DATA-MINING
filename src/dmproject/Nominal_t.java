
package dmproject;
public class Nominal_t {
    
    private int id;
    private String label;
    private int count;

    public Nominal_t(int id_t,String label_t,int count_t) {
        id = id_t;
        label = label_t;
        count = count_t;
    }
    
    public int getId(){
        return id;
    }
    
    public void setId(int id_t){
        id = id_t;
    }
    
    public String getLabel(){
        return label;
    }
    
    public void setLabel(String label_t){
        label = label_t;
    }
    
    public int getCount(){
        return count;
    }
    
    public void setCount(int count_t){
        count = count_t;
    }
    
    
}

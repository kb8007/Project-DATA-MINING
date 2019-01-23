
package dmproject;

/**
 *
 * @author kb800
 */
public class Statistic_t {
    
    private String name;
    private float value;

    public Statistic_t(String name_t,float value_t) {
        name = name_t;
        value = value_t;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name_t){
        name = name_t;
    }
    
    public float getValue(){
        return value;
    }
    
    public void setValue(float value_t){
        value = value_t;
    }
    
}

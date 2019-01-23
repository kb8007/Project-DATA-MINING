
package dmproject;

import javafx.beans.property.SimpleStringProperty;

public class Attribute_t {
    
    private SimpleStringProperty id;
    private SimpleStringProperty name;
    
    Attribute_t(String id_t,String name_t){
        id = new SimpleStringProperty(id_t);
        name = new SimpleStringProperty(name_t);
    }
    
    public String getId(){
        return id.get();
    }
    
    public void setId(String id_t){
        id.set(id_t);
    }
    
    public String getName(){
        return name.get();
    }
    
    public void setName(String name_t){
        id.set(name_t);
    }
}

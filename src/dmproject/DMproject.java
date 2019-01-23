
package dmproject;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DMproject extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        
        
        stage.setScene(scene);
        stage.show();
    }

    public void Myfun()
    {
        
        System.out.println("done !");
    }
    public static void main(String[] args) {
       
        launch(args);
    }
    
}

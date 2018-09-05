/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAToolkit;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

/**
 * GUI for linear algebra problems
 * @author Tyler Jorgensen
 */
public class GUI extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        //Fields
        //Text area for creating matrices
        TextArea txt = new TextArea();
        txt.setMaxSize(400 , 400);
        txt.setMinSize(400 , 400);
        
        //Creates a matrix variable for later use, clears text area
        Button createMatrix = new Button("Create Matrix");
        createMatrix.setPadding(new Insets(10,10,10,10));
        
        //Solves the matrix operation
        Button solve = new Button("Solve");
        
        //Panes
        BorderPane border = new BorderPane();
        HBox top = new HBox();
        VBox left = new VBox();
        VBox right = new VBox();
        GridPane center = new GridPane();
        
        //Top layout
        top.setPadding(new Insets(10,10,10,10));
        top.setSpacing(10);
        top.setStyle("-fx-background-color: #336699;");
        
        //Left layout
        left.setPadding(new Insets(10,10,10,10));
        left.setSpacing(10);
        left.setStyle("-fx-background-color: #336699;");
        
        //Right layout
    
        
        //Center layout
        center.setVgap(10); center.setHgap(10);
        center.setPadding(new Insets(10,10,10,10));
        center.add(txt, 0, 0);
        center.add(createMatrix,0,1);
        
        //Pane layouts
        border.setCenter(center);
        border.setTop(top);
        border.setLeft(left);
        border.setRight(right);
       
        
       
        //Initializing scene
        Scene scene = new Scene(border, 800, 600);
        
        //Initializing stage
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

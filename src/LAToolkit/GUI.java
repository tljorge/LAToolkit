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
import javafx.event.*;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * GUI for linear algebra problems
 * @author Tyler Jorgensen
 */



public class GUI extends Application {
    //input state
    static char state ='I';
    //text area
    TextArea input = new TextArea();
    TextArea output = new TextArea();
    
    String exampleText = "Sample: \n A = \n 1 3 5 \n / \n B = \n 1 \n 2 \n 3 \n / \n multiply(A,B)";
    
    
    @Override
    public void start(Stage primaryStage) {
        //Fields
        //Text area for creating matrices
        input.setMaxSize(300 , 300);
        input.setMinSize(300 , 300);
        input.setPromptText(exampleText);
                
        output.setMaxSize(300 , 300);
        output.setMinSize(300 , 300);
        
        output.setEditable(false);
        output.setPromptText("Awaiting output");
        
        
               
        //Creates a matrix variable for later use, clears text area
        Button solve = new Button("Execute");
        solve.setPadding(new Insets(10,10,10,10));
        
        //Label for output
        Label outputLabel = new Label("Output");
        
        
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
        center.add(input, 0, 0);
        center.add(solve,0,1);
        center.add(output,1,0);
        
        
        
        //Pane layouts
        border.setCenter(center);
        border.setTop(top);
        border.setLeft(left);
        border.setRight(right);
       
        //Events
        //Execute event, sends data to parser
        solve.setOnAction((ActionEvent event) -> {
            String s = input.getText();
            StringParser.parse(s);
            state='F';
            output.setEditable(true);
            output.setText(StringParser.output);
            output.setEditable(false);
        });
        
        //Changing spacing
        input.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent e) -> {
            if ((e.getCode() == KeyCode.SPACE)&&state=='M') {
                
                input.insertText(input.getCaretPosition(), "\t"); //inserts the replacement tab
                e.consume();
            } else if(e.getCode().isLetterKey()){
                state='F';
            } else if(e.getCode().isDigitKey()){
                state='M';
            }
        });
        
        input.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent e) -> {
            if (e.getCode() == KeyCode.EQUALS) {
                state='M';
                input.insertText(input.getCaretPosition(), "\n"); //move to newline
                e.consume();
            }
        });
        
       
        //Initializing scene
        Scene scene = new Scene(border, 800, 600);
       
        
        //Initializing stage
        primaryStage.setTitle("Linear Algebra Toolkit");
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

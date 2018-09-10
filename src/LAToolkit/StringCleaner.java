/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAToolkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cleans strings to check if they are properly formatted.
 * If string is formatted correctly a matrix will be created and added to the
 * string cleaner arraylist for use in gui.
 * @author Tyler Jorgensen
 */
public class StringCleaner {
    static String input;
    static ArrayList<Matrix> matrices;

    public static void clean(String s) {
        input = s; // raw input from text area (passed in the GUI)
        Scanner scan = new Scanner(s); //scanner for raw input
        
        //Variables for later use
        String varname = "";
        List<Double> list = new ArrayList<>();
        
        int columnLength = 0; //update as goes
        
        //for initial row length to confirm all rows are equal length
        int initRowLength = 0;
        boolean rowCheck = false;
        
        //If rows are unbalanced matrix creation will not occur
        boolean balancedRows = true;
        
        //regex for variable names
        String varMatch = "[a-zA-Z]+"; //regular expression for variable name
        String matrixIndicator = "=";  //denotes begining of matrix
        
        //works for one matrix, can do for 2+?
        //checking for individual lines
        while (scan.hasNextLine()){
            String l = scan.nextLine();
            Scanner line = new Scanner(l); //scanner for individual lines
            
            
            //checking for variable name
            if (line.hasNext(varMatch)){
                varname = line.next(varMatch);
                System.out.println(varname);
            } else {
                System.out.println("No variable name detected");
            }
            
            //checking for matrix indicator '='
            if (line.hasNext(matrixIndicator)){
                line.next(matrixIndicator);
            } else {
                System.out.println("No matrix indicator"); //neccesary?
                
            }
            //int rowCt = 0; scope issues
            //check for doubles and ints?
            if (line.hasNextDouble() || line.hasNextInt()){
                int rowLength = 0;
                while (line.hasNextDouble() || line.hasNextInt()){
                    if(!rowCheck){
                        initRowLength++;
                    }
                    rowLength++;
                    list.add(line.nextDouble());
                    //need case for integer entry, maybe not?
                }
                if (rowLength != initRowLength) {
                    balancedRows = false;
                }
                rowCheck=true;
                columnLength++;
            }
            
            System.out.println(list.toString());
            
        }
        //System.out.println("Row total: "+ rowTotal);
        System.out.println("Initial row length: "+ initRowLength);
        System.out.println("Column total: "+ columnLength);
        if (balancedRows==true){
            //create matrix and clear variables
            //columnLength is number of rows and rowLength is number of columns
            createMatrix(varname,list,initRowLength,columnLength);
        } else {
            System.out.println("Error in creating matrix: Unbalanced rows");
        }
        
        
    }
    
    /* Creates a matrix */
    private static void createMatrix (String varname, List<Double> l, int rows, int columns){
        Matrix m = new Matrix(varname,rows,columns);
        int listIndexCt = 0;
        
        for(int i=0;i<columns;i++){
            for(int j=0;j<rows;j++){
                double val  = l.get(listIndexCt);
                m.setVal(j, i, val);
                listIndexCt++;
            }
        }
        
        matrices.add(m);
        
    }
}

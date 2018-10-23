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
    static ArrayList<Matrix> matrices = new ArrayList<>();

    public static void clean(String s) {
        input = s; // raw input from text area (passed in the GUI)
        Scanner scan = new Scanner(s); //scanner for raw input
       
        
        //Variables for later use
        String varname = "";
        List<Double> list = new ArrayList<>();
        
        int rowCt = 0; //update as goes
        
        //for initial Column length to confirm all rows are equal length
        int initColumnLength = 0;
        boolean columnCheck = false;
        
        //If rows are unbalanced matrix creation will not occur
        boolean balancedColumns = true;
        
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
                
            } 
            
            //checking for matrix indicator '=' Not needed?
            if (line.hasNext(matrixIndicator)){
                line.next(matrixIndicator);
                
            } 
            //int rowCt = 0; scope issues
            //check for doubles and ints?
            if (line.hasNextDouble()){
                int columnLength = 0;
                while (line.hasNextDouble()){
                    if(!columnCheck){
                        initColumnLength++;
                    }
                    columnLength++;
                    list.add(line.nextDouble());
                    //need case for integer entry, maybe not?
                }
                if (columnLength != initColumnLength) {
                    balancedColumns = false;
                }
                columnCheck=true;
                rowCt++;
            }
            
            System.out.println(list.toString());
            
        }
        //System.out.println("Row total: "+ rowTotal);
        System.out.println("Initial column length: "+ initColumnLength);
        System.out.println("Row total: "+ rowCt);
        if (balancedColumns==true){
            //create matrix and clear variables
            //columnLength is number of rows and rowLength is number of columns
            matrices.add(createMatrix(varname,list,rowCt,initColumnLength));
        } else {
            System.out.println("Error in creating matrix: Unbalanced rows");
        }
        
        
    }
    
    /* Creates a matrix */

    /**
     *
     * @param varname
     * @param l
     * @param rows
     * @param columns
     * @return
     */

    private static Matrix createMatrix (String varname, List<Double> l, int rows, int columns){
        Matrix m = new Matrix(varname,rows,columns);
        int listIndexCt = 0;
        
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                double val  = l.get(listIndexCt);
                System.out.println(val);
                m.setVal(i, j, val);
                listIndexCt++;
            }
        }
        m.print();
        return m;
    }
    
    public static Matrix getMatrix (String varname){
        for (int i=0;i<matrices.size();i++){
            if (matrices.get(i).getVarName().equals(varname)){
                return matrices.get(i);
            }
        }
        
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LAToolkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Cleans strings to check if they are properly formatted. If string is
 * formatted correctly a matrix will be created and added to the string cleaner
 * ArrayList for use in GUI.
 *
 * @author Tyler Jorgensen
 */
public class StringParser {
    //input passed from GUI
    static String input;
    static String output;
    //data structure to hold the created matrix objects
    static HashMap<String, Matrix> matrices = new HashMap();
    
    //regex for variable names (check 'Cases' for more info)
    static final String F_MATCH1 = "[A-Za-z]+\\({1}[A-Za-z0-9]+\\,[A-Za-z0-9]+\\){1}";     //funct(Str,Str)
    static final String F_MATCH2 = "[A-Za-z]+\\({1}[A-Za-z0-9]+\\){1}";                 //funct(Str)
    static final String F_MATCH3 = "[A-Za-z]+\\({1}[0-9]+\\){1}";                    //funct(int)
    static final String F_MATCH4 = "[A-Za-z]+\\({1}[A-Za-z0-9]+\\,[0-9]+\\){1}";        //funct(Str,int)
    static final String F_MATCH5 = "[A-Za-z]+\\({1}[0-9]+\\,[A-Za-z0-9]+\\){1}";        //funct(int,Str)

    
    static final String VAR_MATCH = "([a-zA-Z0-9]+\\={1})|([a-zA-Z0-9]\\s+\\={1})";        //regular expression for variable name
    static final String M_MATCH = "((\\d+|(\\d+(\\.\\d+)?))\\s*)+\\]?";              //regular expression for a line of integers/doubles
    static final String E_MATCH = "\\/";                                             //regular expression for escape character '/'
    
    /* Temporary data for matrix object creation */
   
    //data structure for numerical values in matrix creation, clears structure
    //after the list is consumed and the matrix object is created
    static List<Double> list = new ArrayList<>();
    
    //variable name, same conditions as list
    static String varName = "";
    
    //counter variables for columns and rows
    static int rows = 0;
    static int columns = 0;
    
    
    /**
     * Cleans the raw input into usable information
     * reads line by line checking the contents of each line and executing
     * on a case by case basis.
     * 
     * @param s
     */
    public static void parse(String s) {
        input = s; // raw input from text area (passed in the GUI)
        Scanner scan = new Scanner(s); //scanner for raw input
             
        //reading the entire input, line by line
        while (scan.hasNextLine()){
            String line = scan.nextLine();
            //System.out.println(line); works
            
            //case 1: Reads a line to declare variable
            if (line.matches(VAR_MATCH)){
                parseVariable(line);
            }
            //case 2: Reads a line of doubles/integers
            else if(line.matches(M_MATCH)){    
                rows++;
                parseMatrixNums(line); 
            }
            //case 3: Escape the matrix creation
            else if(line.matches(E_MATCH)){
                parseEscape();
            }
            //case 4: Reads a function command
            else if(line.matches(F_MATCH1)){
                parseFunction(line);
            }else if(line.matches(F_MATCH2)){
                parseFunction(line);
            }else if(line.matches(F_MATCH3)){
                parseFunction(line);
            }else if(line.matches(F_MATCH4)){
                parseFunction(line);
            }else if(line.matches(F_MATCH5)){
                parseFunction(line);
            } 
            //base case: No match/error?
            else{
                System.out.println("Did not match anything");
            }
        }
    }

    /**
     * Case for variable declaration, stores variable name in global field
     * varName.
     * @param line 
     */
    private static void parseVariable(String line) {
        varName = line;
        varName = varName.replace("=","");
        varName = varName.trim();
        
        System.out.println(varName);
    }
    
   /**
    * Case for line of integers/doubles, which are fed sequentially in global list
    * for later use to create matrix objects.
    * @param line 
    */
    private static void parseMatrixNums(String line) {
        //Scanner for individual lines
        Scanner l = new Scanner(line);
        
        //adding nums to list and keeping track of length
        while(l.hasNextDouble()){
            list.add(l.nextDouble());    
        }
        System.out.println(list.toString());
    }

    /**
     * Case for '/' escape character which will notify the program the matrix
     * declaration is complete and create the matrix object and clear the global fields.
     * Note: May not be needed when states are complete.
     */
    private static void parseEscape() {
        System.out.println("Escaped");
        System.out.println("Rows: " + rows);
        
        //allows proper modulo arithmetic for checking balance of matrix
        double tempRows = rows;
        double tempSize = list.size();
        
        //columns = total/rows
        if((tempSize/tempRows)%1==0){
            columns=list.size()/rows;
            createMatrix();
            System.out.println("Matrix "+varName+" successfuly created!");
            clearData();
            
        } else {
            System.out.println("Invalid matrix, please check input.");
            clearData();
        }
        
    }
    /** 
     * TODO
     * @param line 
     */
    private static void parseFunction(String line) {
        //Regular expressions for various functions
        
        //Matrix multiplication regex
        String mRegex = "(multiply\\(){1}[A-Za-z0-9]+\\,{1}[A-Za-z0-9]+\\){1}";
        //Matrix addition regex
        String addRegex = "(add\\(){1}[A-Za-z0-9]+\\,{1}[A-Za-z0-9]+\\){1}";
        //Transpose regex
        String transposeRegex = "transpose\\({1}[A-Za-z0-9]+\\){1}";
        //Creation of identity matrix
        String identityRegex = "identity\\({1}[0-9]+\\){1}";
        
        
        //Getting the indexes for extracting variable names
        int startIndex1 = line.indexOf('(')+1;
        int endIndex1 = line.indexOf(',');
        
        int startIndex2 = line.indexOf(',')+1;
        int endIndex2 = line.indexOf(')');
        
        //function to multiply two matrices
        if(line.matches(mRegex)){
            String var1 = line.substring(startIndex1,endIndex1);
            String var2 = line.substring(startIndex2,endIndex2);
            
            //System.out.println("Variable one is: "+var1);
            //System.out.println("Variable two is: "+var2);
            
            Matrix m = getMatrix(var1).mMultiply(getMatrix(var2));
            output = m.toString();
        }
        //function to add two matrices
        else if (line.matches(addRegex)){
            String var1 = line.substring(startIndex1,endIndex1);
            String var2 = line.substring(startIndex2,endIndex2);
            
            Matrix m = getMatrix(var1).add(getMatrix(var2));
            output = m.toString();
        }
        //transpose a matrix
        else if(line.matches(transposeRegex)){
            String var1 =line.substring(startIndex1,endIndex2);
            
            Matrix m = getMatrix(var1);
            m.transpose();
            output = m.toString();
        }
        //creating identity matrix
        else if(line.matches(identityRegex)){
            String var1 = line.substring(startIndex1,endIndex2);
            int val = Integer.parseInt(var1);
            
            Matrix m = new Matrix(val);
            output = m.toString();
            
        }
    }
    
    /** 
     * Clears the global fields list, rows, columns, and dereferences varName
     * and creates new blank 'varName'.
     */
    private static void clearData(){
        list.clear();
        rows=0;
        columns=0;
        varName = ""; //creates new string, dereferencing old 'varName'
    }
    
    /**
     * Takes data from global fields and creates Matrix object and adds that 
     * matrix to a list of matrices for later use.
     */
    private static void createMatrix() {
        Matrix m = new Matrix(varName, rows, columns);
        int listIndexCt = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                double val = list.get(listIndexCt);
                System.out.println(val);
                m.setVal(i, j, val);
                listIndexCt++;
            }
        }
        m.print();
        addMatrix(varName,m);
    }
    
    /** 
     * Adds matrix to the list, may want to use map instead.
     * Called in createMatrix()
     */
    private static void addMatrix(String varname, Matrix m){
        matrices.put(varname, m);
    }
    
    /**
     * Get function to call matrix object for use.
     * @param varname
     * @return Matrix from list of matrices
     */
    public static Matrix getMatrix (String varname){
        return matrices.get(varname);
    }
}


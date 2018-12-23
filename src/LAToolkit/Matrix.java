
package LAToolkit;

/** -FUNCTIONALITY REQUIREMENTS-
 * -matrix transposition
 * -matrix addition
 * -matrix multiplication w/ scalar
 * -plain matrix multiplication
 * -component wise multiplication
 * -Kronecker multiplication
 * -horizontal concatenation
 * 
 * More functionality
 * -vertical concatenation
 * -eigenvalues
 * @author Tyler Jorgensen - 2017
 */
public class Matrix { 
    private String varname;
    private int r;
    private int c;
    private double[][] matrix;
    
    public Matrix (){ //void matrix: 0x0
        this.r = 0;
        this.c = 0;
        this.matrix = new double[0][0];
    }
    
    public Matrix(String varname, int r,int c){ //define dimensions off matrix w/ varname
        this.varname = varname;
        this.r = r;
        this.c = c;
        this.matrix = new double[r][c];
    }
    
    public Matrix(int r,int c){ //define dimensions off matrix, no fill
        this.r = r;
        this.c = c;
        this.matrix = new double[r][c];
    }
    
    /** Constructor for identity matrix */
    public Matrix(int rc){
        this.r = rc;
        this.c = rc;
        this.matrix = new double[rc][rc];
        
        for (int i=0;i<rc;i++){
            for(int j=0;j<rc;j++){
                if(i==j){
                    this.matrix[i][j]=1;
                }else {
                    this.matrix[i][j]=0;
                }
            }
        }
    }
    
    /**EX(2x3 matrix): setVal(0,3,5) {0, 0, 5
     *                                0, 0, 0}
     * @param r
     * @param c
     * @param val 
     */
    
    public void setVal(int r,int c,double val){ //sets val at certain r,c
        this.matrix[r][c] = val;
    }
    
    public void fillMatrix(){ //fills matrix with 0's
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                this.matrix[i][j] = 0;
            }
        }
    }
    
    public int getC(){ 
        return this.c;
    }

    public int getR(){ 
        return this.r;  
    } 
    
    /** Return val at (r,c) for this matrix
     * @param r
     * @param c
     * @return value at [r][c]*/
    public double getVal(int r,int c){
        return this.matrix[r][c];
    }
    
    public String getVarName(){
        return this.varname;
    }
    
    /** prints the values of this matrix */
    public void print(){
        for(int i=0;i<r;i++){
            System.out.println("");
            for(int j=0;j<c;j++){
                System.out.print(matrix[i][j]+" ");
            }
        }
        System.out.println("");
    } 
    
    /** transposes matrix, DOES NOT SAVE OLD MATRIX */
    public void transpose(){ //matrix transposition
        double temp[][] = new double[c][r];
        for(int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                temp[j][i] = this.matrix[i][j];
            }
        }
        this.matrix = new double[c][r];
        int t = this.r;
        this.r = c;
        this.c =t;
        for (int i=0;i<r;i++){
            for(int j=0;j<c;j++){
                this.matrix[i][j]=temp[i][j];
            }
        }
    }
    
    /** adds this matrix with matrix m
     * @param m
     * @return Matrix */
    public Matrix add (Matrix m){ //matrix addition
        if(this.r!=m.r || this.c!=m.c){
            System.out.println("Matrices are not equal.");
            return null;
        } 
        
        Matrix d = new Matrix(this.r,this.c);
        for (int i=0;i<this.r;i++){
            for (int j=0;j<this.c;j++){
                d.setVal(i, j, this.getVal(i,j)+m.getVal(i, j));
            }
        }    
        return d;
    }
    
    /** Applies scalar multiplication to this matrix
     * 
     * @param scalar */
    public void sMultiply(double scalar){ 
        for (int i=0;i<this.r;i++){
            for (int j=0;j<this.c;j++){
                this.matrix[i][j] = this.matrix[i][j] * scalar;
            }
        }
    }
    
    /** Multiplies this matrix by matrix m: Careful not commutative
     * @param m
     * @return Matrix*/
    public Matrix mMultiply(Matrix m){ 
        if (this.c != m.r){
            System.out.println("Columns of first matrix does not "
                    + "equal rows of second matrix");
            return null;
        }
        
        Matrix d = new Matrix(this.r,m.c);
        
        //naive algorithm O(n^3), can be better
        for (int i = 0; i < this.r; i++) { // aRow
            for (int j = 0; j < m.c; j++) { // bColumn
                for (int k = 0; k < this.c; k++) { // aColumn
                    d.matrix[i][j] += this.matrix[i][k] * m.matrix[k][j]; 
                }
            }
        }
        return d;
    }
    /** COMPONENT WISE/HADAMARD MATRIX MULTPLICATION
     * must have equal dimensions
     * returns new matrix of the Hadamard product
     * @param m
     * @return Matrix */
    public Matrix cwMultiply(Matrix m){ //component wise multiplication
        if (this.r != m.r || this.c != m.c){
            System.out.println("Dimensions of matrices are not equal");
            return null;
        }
        Matrix a = new Matrix(this.r,this.c);
        
        for (int i=0;i<this.r;i++){
            for (int j=0;j<this.c;j++){
                a.matrix[i][j]= this.matrix[i][j]*m.matrix[i][j];
            }
        }
        
        return a;
    }
    
    /** Kronecker Matrix Multiplication
     * @param m
     * @return Matrix */
    public Matrix kMultiply(Matrix m){
        Matrix a = new Matrix(this.r*m.r,this.c*m.c);
        for (int i=0;i<a.r;i++){
            for(int j=0;j<a.c;j++){
                a.matrix[i][j] = this.matrix[0][i] * m.matrix[j][0];
            }
        }       
        return a;
    }
    
    /** appends matrix m onto this matrix 
    * must have same number of rows
     * @param m
     * @return Matrix */
    public Matrix hConcatenate(Matrix m){ 
        if(this.r != m.r){
            System.out.println("Matrices do not have same amount of rows");
            return null;
        }
        Matrix a = new Matrix(this.r,this.c+m.c);
        
        for (int i=0;i<a.r;i++){
            for (int j=0;j<a.c;j++){
                if(j<this.c){
                    a.matrix[i][j]=this.matrix[i][j];
                } else{
                    a.matrix[i][j]= m.matrix[i][j-this.c];
                }
            }
        }        
        return a;
    }
    
    /** returns a copy of this Matrix
     * @return Matrix  */
    public Matrix copy(){
        Matrix b = new Matrix(this.r,this.c);
        for (int i=0;i<this.r;i++){
            for (int j=0;j<this.c;j++){
                b.setVal(i,j,this.getVal(i, j));
            }
        }
        return b;
    }  
}

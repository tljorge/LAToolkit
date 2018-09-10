
package LAToolkit;


public class Test {
    public static void main(String[] args) {
        Matrix m = new Matrix("test",3,3);
        
        m.setVal(0,0,2);
        m.setVal(0,1,2);
        m.setVal(0,2,2);
        m.setVal(1,0,2);
        m.setVal(1,1,2);
        m.setVal(1,2,2);
        m.setVal(2,0,2);
        m.setVal(2,1,2);
        m.setVal(2,2,2);
        
        Matrix b = m.copy();
        m.transpose();
        
        b.print();
        m.print();
        
        Matrix d = m.mMultiply(b);
        Matrix e = b.mMultiply(m);
        Matrix f = m.cwMultiply(b);
        
        Matrix g = e.hConcatenate(f);
        Matrix h = new Matrix(7);
        
        h.print();
       
        d.print();
        e.print();
        f.print();
        g.print();
    }

    
}

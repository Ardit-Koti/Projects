package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 * Assignment is an ActionNode with an identifier that is
 * a reference to an ExpressionNode.
 */
public class Assignment implements ActionNode {
    private String ident;
    private ExpressionNode rhs;

    public Assignment(String ident, ExpressionNode rhs){
        this.ident = ident;
        this.rhs = rhs;
    }

    /**
     * Evaluates rhs and puts int value of rhs
     * in pair with ident in the symbol table.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        int val = rhs.evaluate(symTab);
        symTab.put(ident, val);
    }

    /**
     * Prints the ident string along with the assignment
     * symbol and the infixDisplay of rhs.
     */
    @Override
    public void infixDisplay() {
        System.out.print(ident + " := ");
        rhs.infixDisplay();
    }

    /**
     * Writes Soros instructions to an output stream which
     * compile rhs and Store the value with the ident.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        this.rhs.compile(out);
        out.println(Soros.STORE+" "+this.ident);
    }
}

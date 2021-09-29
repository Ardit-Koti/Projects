package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 *
 * Constant is an ExpressionNode that just has an
 * integer value.
 */
public class Constant implements ExpressionNode {
    private final int value;

    public Constant(int value) {
        this.value = value;
    }

    /**
     * Displays constant by its value.
     */
    @Override
    public void infixDisplay() {
        System.out.print(this.value + " ");
    }

    /**
     * Writes Soros instructions to an output stream
     * which PUSH the constant value onto the
     * Soros Stack.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        out.println(Soros.PUSH + " " + this.value);
    }

    /**
     * Returns the value of the constant.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return value of Constant
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
         return this.value;
    }
}

package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Map;
import dendron.Errors;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 * Variable is an ExpressionNode which
 * has a name.
 */
public class Variable implements ExpressionNode{
    public String name;

    public Variable(String name){this.name = name;}

    /**
     * Displays the variable by its name
     * in infix style.
     */
    @Override
    public void infixDisplay() {
        System.out.print(this.name + " ");
    }

    /**
     * Prints out Soros instructions to an output stream
     * which gets the value of the variable and pushes it onto
     * the Soros stack.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        out.println(Soros.LOAD + " " + this.name);
    }

    /**
     * Gets value of variable
     * @param symTab symbol table, if needed, to fetch variable values
     * @return variable value
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        if(symTab.get(this.name) == null){
            Errors.report(Errors.Type.UNINITIALIZED, this.name);
        }
        return symTab.get(this.name);
    }
}

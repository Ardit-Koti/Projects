package dendron.treenodes;


import dendron.machine.Soros;

import java.io.PrintWriter;
import java.sql.SQLOutput;
import java.util.Map;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 *
 * Print is an ActionNode that contains
 * an ExpressionNode named printee, which
 * is printed out in infixDisplay().
 */
public class Print implements ActionNode {
    public final static String PRINT_PREFIX = "#";
    private ExpressionNode printee;

    public Print(ExpressionNode printee){
        this.printee = printee;
    }

    /**
     * Evaluates printee and prints out the result.
     * @param symTab the table where variable values are stored
     */
    @Override
    public void execute(Map<String, Integer> symTab) {
        int result = this.printee.evaluate(symTab);
        System.out.println("=== " + result);
    }

    /**
     * Displays Print and the ExpressionNode.
     */
    @Override
    public void infixDisplay(){
        System.out.print("Print ");
        this.printee.infixDisplay();
    }

    /**
     * Writes Soros instructions to an output stream
     * which compile the printee and then write
     * PRINT.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        printee.compile(out);
        out.println(Soros.PRINT);
    }
}

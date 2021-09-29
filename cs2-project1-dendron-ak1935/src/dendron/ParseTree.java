package dendron;

import dendron.treenodes.ActionNode;
import dendron.treenodes.Assignment;
import dendron.treenodes.ExpressionNode;
import dendron.treenodes.Program;
import dendron.treenodes.*;


import java.io.PrintWriter;
import java.util.*;

/**
 * Operations that are done on a Dendron code parse tree.
 *
 * @author Ardit Koti
 * ak1935@rit.edu
 */
public class ParseTree {
    public static String ASSIGN = ":=";
    public static String PRINT = "#";
    private Program Tree;

    /**
     * Parses the tokens list to create ActionNodes. Utilizes
     * parseExpression() when required.
     *
     * @param tokens A list of tokens which are parsed
     *               to be put into the program as Nodes.
     * @return An ActionNode which may
     * either be an Assignment or Print.
     */
    private ActionNode parseAction(List<String> tokens) {
        if (tokens.get(0).matches(ASSIGN)) {
            tokens.remove(0);
            if(tokens.isEmpty()) {
                Errors.report(Errors.Type.PREMATURE_END, null);
            }
            String ident = tokens.remove(0);
            return new Assignment(ident, parseExpression(tokens));
        } else if (tokens.get(0).matches(PRINT)) {
            tokens.remove(0);
            return new Print(parseExpression(tokens));
        }
        else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, tokens.get(0));
            return null;
        }
    }

    /**
     * Parses tokens list to create ExpressionNodes.
     *
     * @param tokens The list of tokens which are
     *               parsed.
     * @return An ExpressionNode which may be a
     * Constant, a Variable, a UnaryOperation, or
     * a BinaryOperation.
     */
    private ExpressionNode parseExpression(List<String> tokens) {
        if(tokens.isEmpty()){
            Errors.report(Errors.Type.PREMATURE_END, null);
        }
        if (tokens.get(0).matches("^[a-zA-Z].*")) {
            return new Variable(tokens.remove(0));
        } else if (tokens.get(0).matches("-?\\d+")) {
            return new Constant(Integer.parseInt(tokens.remove(0)));
        } else if (UnaryOperation.OPERATORS.contains(tokens.get(0))) {
            return new UnaryOperation(tokens.remove(0), parseExpression(tokens));
        } else if (BinaryOperation.OPERATORS.contains(tokens.get(0))) {
            return new BinaryOperation(tokens.remove(0), parseExpression(tokens), parseExpression(tokens));
        }else {
            Errors.report(Errors.Type.ILLEGAL_VALUE, tokens.get(0));
            return null;
        }
    }

    /**
     * Parse the entire list of program tokens. The program is a
     * sequence of actions (statements), each of which modifies something
     * in the program's set of variables. The resulting parse tree is
     * stored internally.
     *
     * @param tokens the token list (Strings). This list may be destroyed
     *               by this constructor.
     */


    public ParseTree(List<String> tokens) {
        // TODO
        this.Tree = new Program();
        while(!tokens.isEmpty())
    {
        Tree.addAction(parseAction(tokens));
    }

}

    /**
     * Print the program the tree represents in a more typical
     * infix style, and with one statement per line.
     * @see ActionNode#infixDisplay()
     */
    public void displayProgram() {
        // TODO
        this.Tree.infixDisplay();
    }

    /**
     * Run the program represented by the tree directly
     * @see ActionNode#execute(Map)
     */
    public void interpret(){
        // TODO
        System.out.println("Interpreting the ParseTree... ");
        Map<String, Integer> symTab = new HashMap<String, Integer>();
        this.Tree.execute(symTab);
        System.out.println("Interpretation Complete.");
        Errors.dump(symTab);
    }

    /**
     * Build the list of machine instructions for
     * the program represented by the tree.
     *
     * @param out where to print the Soros instruction list
     */
    public void compileTo( PrintWriter out ) {
        // TODO
        this.Tree.compile(out);
    }
}

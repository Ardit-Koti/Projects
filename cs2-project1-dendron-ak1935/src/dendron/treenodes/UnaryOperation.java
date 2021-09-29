package dendron.treenodes;

import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 *
 * UnaryOperation is an ExpressionNode
 * with an operator and an expressionNode
 * within it that is expr.
 * It has its own OPERATORS list which
 * has unary operators that are used to identify
 * symbols in the tokens list in ParseTree.
 */
public class UnaryOperation implements ExpressionNode{
    public static String NEG = "_";
    public static String SQRT = "%";
    public static Collection<String> OPERATORS = Arrays.asList(NEG, SQRT);
    private final ExpressionNode expr;
    private String operator;

    public UnaryOperation(String operator, ExpressionNode expr){
        this.operator = operator;
        this.expr = expr;
    }

    /**
     * Shows the infix display of the UnaryOperation
     * with the operator followed by the
     * expr's infixDisplay().
     */
    @Override
    public void infixDisplay() {
        System.out.print(operator);
        expr.infixDisplay();
    }

    /**
     * Prints Soros instructions using
     * recursive calls for compile and the
     * printing to a stream the Soros strings.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        expr.compile(out);
        if(this.operator.matches(NEG)){
            out.println(Soros.NEGATE);
        }
        else if(this.operator.matches(SQRT)){
            out.println(Soros.SQUARE_ROOT);
        }
    }

    /**
     *Evaluates expr and then applys operator to it.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return evaluated expr with operator modification (int).
     */
    @Override
    public int evaluate(Map<String, Integer> symTab)
    {
        if(this.operator.matches(NEG)){
            return (-1)*(this.expr.evaluate(symTab));
        }
        else if(this.operator.matches(SQRT)){
            return (int) Math.sqrt(this.expr.evaluate(symTab));
        }else {
            return 0;
        }
    }
}

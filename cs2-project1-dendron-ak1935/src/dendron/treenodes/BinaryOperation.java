package dendron.treenodes;

import dendron.Errors;
import dendron.machine.Soros;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

/**
 * Author: Ardit Koti
 * ak1935@rit.edu
 *
 * BinaryOperation is an Expression Node with
 * an operator, a left child expression node, and
 * a right child expression node.
 *
 * It has an OPERATORS collection which is used
 * in ParseTree in order to identify symbols in
 * the token list.
 */
public class BinaryOperation implements ExpressionNode {
    private static final String ADD = "+";
    private static final String DIV = "/";
    private static final String MUL = "*";
    private static final String SUB = "-";
    public static Collection<String> OPERATORS = Arrays.asList(ADD, DIV, MUL, SUB);
    private String operator;
    private ExpressionNode leftChild;
    private ExpressionNode rightChild;

    public BinaryOperation(String operator, ExpressionNode leftChild, ExpressionNode rightChild) {
        this.operator = operator;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     * Displays expression of leftChild and rightchild
     * with operator in between, which is
     * surrounded by parentheses.
     */
    @Override
    public void infixDisplay() {
        System.out.print("( ");
        leftChild.infixDisplay();
        System.out.print(operator + " ");
        rightChild.infixDisplay();
        System.out.print(") ");
    }

    /**
     * Writes Soros instructions to an output stream
     * which compile the left and right child and
     * then perform the operation on them.
     * @param out the output stream for the compiled code &mdash;
     */
    @Override
    public void compile(PrintWriter out) {
        leftChild.compile(out);
        rightChild.compile(out);
        if(this.operator.equals("+")){
            out.println(Soros.ADD);
        }
        else if(this.operator.equals("*")){
            out.println(Soros.MULTIPLY);
        }
        else if(this.operator.matches("-")){
            out.println(Soros.SUBTRACT);
        }
        else if(this.operator.matches("/")){
            out.println(Soros.DIVIDE);
        }
    }

    /**
     * Evaluates left and right child and returns
     * the result of applying the operation to them.
     * @param symTab symbol table, if needed, to fetch variable values
     * @return integer result of the expression
     */
    @Override
    public int evaluate(Map<String, Integer> symTab) {
        {
            if (this.operator.equals(ADD)) {
                return this.leftChild.evaluate(symTab)
                        + this.rightChild.evaluate(symTab);
            } else if (this.operator.matches(SUB)) {
                return this.leftChild.evaluate(symTab)
                        - this.rightChild.evaluate(symTab);
            } else if (this.operator.equals(MUL)) {
                return this.leftChild.evaluate(symTab)
                        * this.rightChild.evaluate(symTab);
            } else if (this.operator.matches(DIV)) {
                if(rightChild.evaluate(symTab) == 0){
                    Errors.report(Errors.Type.DIVIDE_BY_ZERO,null);
                }
                return this.leftChild.evaluate(symTab)
                / this.rightChild.evaluate(symTab);
            }else {
                return 0;
            }
        }
    }
}

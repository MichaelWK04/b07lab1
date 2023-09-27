import java.io.File;

public class Driver {
    public static void main(String [] args) throws Exception {
// Create two polynomials
double[] coefficients1 = {2, 3, 2};
int[] exponents1 = {0, 1, 2};
Polynomial polynomial1 = new Polynomial(coefficients1, exponents1);

double[] coefficients2 = {1, 2};
int[] exponents2 = {0, 1};
Polynomial polynomial2 = new Polynomial(coefficients2, exponents2);

// Display the polynomials
polynomial1.saveToFile("polynomial1.txt");
polynomial2.saveToFile("polynomial2.txt");

// Perform addition
Polynomial sum = polynomial1.add(polynomial2);
sum.saveToFile("sum");

// Perform multiplication
Polynomial product = polynomial1.multiply(polynomial2);
product.saveToFile("product.txt");

// Evaluate polynomial at x = 2
double x = -0.5;
double result = polynomial1.evaluate(x);
System.out.println("Evaluation at x = " + x + ": " + result);

// Check if multiplied poly has a root at x = -0.5
double root = -0.5;
boolean hasRoot = product.hasRoot(root);
System.out.println("Polynomial has root at x = " + root + ": " + hasRoot);
    }
}
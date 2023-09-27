import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.io.BufferedReader;

public class Polynomial {

	double[] coefficients;
	int[] exponents;

	public Polynomial() {
		coefficients = new double[]{0};
		exponents = new int[]{0};
	}

	public Polynomial (double[] coefficients, int[] exponents) {
		this.coefficients = coefficients;
		this.exponents = exponents;
	}

	public Polynomial (File polynomialFile) throws Exception {
		BufferedReader b = new BufferedReader(new FileReader(polynomialFile));
		String polyString = b.readLine();
		b.close();
		polyString = polyString.replace("-", "+-");
		String[] polynomial = polyString.split("[+]");

		int firstIdxEmpty = 0;

		if (polynomial[0].equals("")) {
			firstIdxEmpty++;
		}

		this.coefficients = new double[polynomial.length - firstIdxEmpty];
		this.exponents = new int[polynomial.length - firstIdxEmpty];

		int idx = 0;

		for (int i = 0; i < polynomial.length; i++) {
			if (!polynomial[i].equals("")){
				String[] temp = polynomial[i].split("[x]");
				this.coefficients[idx] = Double.parseDouble(temp[0]);
				if (temp.length > 1) {
					this.exponents[idx] = Integer.parseInt(temp[1]);
				}
				idx++;
			}
		}
		
	}

	public Polynomial add (Polynomial to_add) {
		// if (this.coefficients.length >= to_add.coefficients.length) {
		// 	Polynomial newPoly = new Polynomial(this.coefficients, this.exponents);
		// 	for (int i = 0; i < to_add.coefficients.length; i++) {
		// 		newPoly.coefficients[i] += to_add.coefficients[i];
		// 	}
		// 	return newPoly;
		// }
		// Polynomial newPoly = new Polynomial(to_add.coefficients, to_add.exponents);
		// for (int i = 0; i < this.coefficients.length; i++) {
		// 	newPoly.coefficients[i] += this.coefficients[i];
		// }
		// return newPoly;
		int idx = 0;
		boolean isExists = false;
		int countZeroCoef = 0;
		double[] sumCoef = new double[this.coefficients.length * to_add.coefficients.length+1];
		int[] sumExp = new int[this.exponents.length * to_add.exponents.length+1];
		for (int i = 0; i < sumCoef.length; i++) {
			sumExp[i] = -1; //Exponents can't be -1, so use this to create a smaller array later.
		}

		for (int i = 0; i < this.exponents.length; i++) {
			isExists = false;
			if (this.coefficients[i] != 0) {
				for (int j = 0; j < sumExp.length; j++) {
					if (sumExp[j] == this.exponents[i]) {
						isExists = true;
						sumCoef[j] += this.coefficients[i];
						break;
					}
				}
				if (!isExists) {
					sumExp[idx] = this.exponents[i];
					sumCoef[idx] = this.coefficients[i];
					idx++;
				}
			}
		}

		for (int i = 0; i < to_add.exponents.length; i++) {
			isExists = false;
			if (to_add.coefficients[i] != 0) {
				for (int j = 0; j < sumExp.length; j++) {
					if (sumExp[j] == to_add.exponents[i]) {
						isExists = true;
						sumCoef[j] += to_add.coefficients[i];
						break;
					}
				}
				if (!isExists) {
					sumExp[idx] = to_add.exponents[i];
					sumCoef[idx] = to_add.coefficients[i];
					idx++;
				}
			}
		}

		for (int i = 0; i < idx; i++) {
			if (sumCoef[i] == 0) {
				countZeroCoef++;
			}
		}

		double[] newCoef = new double[idx-countZeroCoef];
		int[] newExp = new int[idx-countZeroCoef];
		idx = 0;
		for (int i = 0; i < sumCoef.length; i++) {
			if (sumCoef[i] != 0) {
					newCoef[idx] = sumCoef[i];
					newExp[idx] = sumExp[i];
					idx++;
			}
		}

		Polynomial newPoly = new Polynomial(newCoef, newExp);
		return newPoly;
	}

	public double evaluate (double x) {
		double sum = 0;
		for (int i = 0; i < coefficients.length; i++) {
			sum += coefficients[i] * Math.pow(x, exponents[i]);
		}
		return sum;
	}

	public boolean hasRoot (double x) {
		return evaluate(x) == 0;
	}

	public Polynomial multiply (Polynomial polynomial) {

		Polynomial product_poly = new Polynomial();

		for (int i = 0; i < this.coefficients.length; i++) {
			Polynomial temp = new Polynomial();
			for (int j = 0; j < polynomial.coefficients.length; j++) {
				double[] coefficient = {this.coefficients[i] * polynomial.coefficients[j]};
				int[] exponent = {this.exponents[i] + polynomial.exponents[j]};
				temp = temp.add(new Polynomial(coefficient, exponent));
			}
			product_poly = product_poly.add(temp);
		}
		return product_poly;

		// //Add coefficients with same exponent

		// double[] tempCoef = new double[idx];
		// int[] tempExp = new int[idx];
	
		// for (int i = 0; i < tempCoef.length; i++) {
		// 	tempExp[i] = -1; //Exponents can't be negative, use -1 as filler
		// 	tempCoef[i] = 0;
		// }

		// idx = 0;
		// for (int i = 0; i < product_poly.exponents.length; i++) {
		// 	isExists = false;
		// 	for (int j = 0; j < tempExp.length; j++) {
		// 		if (tempExp[j] == product_poly.exponents[i]) {
		// 			isExists = true;
		// 			tempCoef[j] += product_poly.coefficients[i];
		// 			break;
		// 		}
		// 	}
		// 	if (!isExists) {
		// 		tempExp[idx] = product_poly.exponents[i];
		// 		tempCoef[idx] = product_poly.coefficients[i];
		// 		idx++;
		// 	}
		// }

		// double[] newCoef = new double[idx];
		// int[] newExp = new int[idx];
		// idx = 0;
		// //Eliminate -1s

		// while (idx < newExp.length && tempExp[idx] != -1) {
		// 	newCoef[idx] = tempCoef[idx];
		// 	newExp[idx] = tempExp[idx];
		// 	idx++;
		// }

		// Polynomial newPoly = new Polynomial(newCoef, newExp);
		// return newPoly;
	}

	public void saveToFile(String filePoly) throws Exception {
		PrintStream ps = new PrintStream(filePoly);
		String strPoly = "";

		for (int i = 0; i < this.coefficients.length; i++) {
			if (this.coefficients[i] < 0 && i > 0){
				strPoly = strPoly.substring(0, strPoly.length()-1);
			}
			strPoly += coefficients[i];
			if (this.exponents[i] != 0) {
				strPoly += "x" + this.exponents[i];
			} 
			strPoly += "+";
		}
		ps.println(strPoly.substring(0, strPoly.length()-1));
		ps.close();
	}
}

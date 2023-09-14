public class Polynomial {

	double[] coefficients;

	public Polynomial() {
		coefficients = new double[1];
		coefficients[0] = 0;
	}

	public Polynomial (double[] coefficients) {
		this.coefficients = coefficients;
	}

	public Polynomial add (Polynomial to_add) {
		if (this.coefficients.length >= to_add.coefficients.length) {
			Polynomial newPoly = new Polynomial(this.coefficients);
			for (int i = 0; i < to_add.coefficients.length; i++) {
				newPoly.coefficients[i] += to_add.coefficients[i];
			}
			return newPoly;
		}
		Polynomial newPoly = new Polynomial(to_add.coefficients);
		for (int i = 0; i < this.coefficients.length; i++) {
			newPoly.coefficients[i] += this.coefficients[i];
		}
		return newPoly;
	}

	public double evaluate (double x) {
		double sum = 0;
		for (int i = 0; i < coefficients.length; i++) {
			sum += coefficients[i] * Math.pow(x, i);
		}
		return sum;
	}

	public boolean hasRoot (double x) {
		return evaluate(x) == 0;
	}
}

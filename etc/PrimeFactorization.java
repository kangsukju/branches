package test;

import java.math.BigInteger;

public class PrimeFactorization {

	public static void main(String[] args) {

		BigInteger d = new BigInteger("9231241232");
		factorization(d);
	}

	public static void factorization(BigInteger max) {
		BigInteger i = new BigInteger("2");
		
//		System.out.println(max);
		
		while (i.compareTo(max) == 0 || i.compareTo(max) == -1) {
//			System.out.println(max+"  "+i+"   "+max.mod(i));
			if (max.mod(i).equals(BigInteger.ZERO)) {
				System.out.print(i +"*");
				if (max.equals(i)) {
					System.out.print("\n");
					return;
				}
				factorization(max.divide(i));
				return;
			}
			i = i.add(BigInteger.ONE);
		}
	}

}

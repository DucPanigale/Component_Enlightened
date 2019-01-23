import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.math.BigInteger;

public class EnlightenedNumbers {
    private static EnlightenedNumbers instance = new EnlightenedNumbers();
    public Port port;

    private EnlightenedNumbers() {
        port = new Port();
    }

    // todo: hinzufügen bei allen anderen Komponenten
    public static EnlightenedNumbers getInstance() {
        return instance;
    }

    public class Port implements IEnlightenedNumbers {
        private Method[] methods = getClass().getMethods();

        @Override
        public ArrayList<BigInteger> execute(BigInteger rangeFrom, BigInteger rangeTo) {
            return InnerExecute(rangeFrom,rangeTo);
        }

        public void listMethods() {
            System.out.println("--- public methods for " + getClass().getName());
            for (int i = 0; i < methods.length; i++)
                if (!methods[i].toString().contains("Object") && !methods[i].toString().contains("list"))
                    System.out.println(methods[i]);
            System.out.println("---");
        }
    }
    public ArrayList<BigInteger> InnerExecute(BigInteger rangeFrom, BigInteger rangeTo) {
        ArrayList<BigInteger> enlightenedNumbers = new ArrayList<>();

        BigInteger counter = rangeFrom;

        do {
            if (((checkIfNumbersMatch(tdFactors(counter), counter)) != null) && counter.compareTo(new BigInteger("8")) > 0){
                enlightenedNumbers.add(counter);
            }
            counter = counter.add(BigInteger.valueOf(1));
        } while (counter.compareTo(rangeTo) < 0);

        return enlightenedNumbers;
    }


    public BigInteger checkIfNumbersMatch(ArrayList<BigInteger> primeFactors, BigInteger value){
        ArrayList<String> digits = getDigits(value);

        StringBuilder builder = new StringBuilder();

        String factors = "";
        String number = "";

        for (int i = 0; i < primeFactors.size(); i++) {
            builder.append(primeFactors.get(i));
        }

        factors = builder.toString();

        builder.setLength(0);
        if (factors.length() < digits.size()) {
            for (int i = 0; i < factors.length(); i++) {
                builder.append(digits.get(i));
            }

            number = builder.toString();
        }
        if (!(factors.equals(number))) {
            return null;
        }

        return value;
    }

// get prime factors -- works
    public static ArrayList<BigInteger> tdFactors(BigInteger n)
    {
        BigInteger two = BigInteger.valueOf(2);
        ArrayList<BigInteger> fs = new ArrayList<>();

        while (n.mod(two).equals(BigInteger.ZERO))
        {
            if(!fs.contains(two)) {
                fs.add(two);
            }
            n = n.divide(two);
        }

        if (n.compareTo(BigInteger.ONE) > 0)
        {
            BigInteger f = BigInteger.valueOf(3);
            while (f.multiply(f).compareTo(n) <= 0)
            {
                if (n.mod(f).equals(BigInteger.ZERO))
                {
                    if (!fs.contains(f)) {
                        fs.add(f);
                    }
                    n = n.divide(f);
                }
                else
                {
                    f = f.add(two);
                }
            }
            if (!fs.contains(n)) {
                fs.add(n);
            }
        }
        return fs;
    }


    public ArrayList<String> getDigits(BigInteger input) {
        ArrayList<String> output = new ArrayList<>();
        String number = input.toString();

        for (int i = 0; i < number.length(); i++)
            output.add(number.substring(i, Math.min(i + 1, number.length())));

        return output;
    }


    public boolean returnPrime(BigInteger number) {
        //check via BigInteger.isProbablePrime(certainty)
        if (!number.isProbablePrime(5))
            return false;

        //check if even
        BigInteger two = new BigInteger("2");
        if (!two.equals(number) && BigInteger.ZERO.equals(number.mod(two)))
            return false;

        //find divisor if any from 3 to 'number'
        for (BigInteger i = new BigInteger("3"); i.multiply(i).compareTo(number) < 1; i = i.add(two)) { //start from 3, 5, etc. the odd number, and look for a divisor if any
            if (BigInteger.ZERO.equals(number.mod(i))) //check if 'i' is divisor of 'number'
                return false;
        }
        return true;
    }
}

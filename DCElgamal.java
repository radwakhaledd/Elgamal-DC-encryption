
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class DCElgamal
{

    // Function to check if a number is prime
    public static boolean isPrime(int n) {
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    // Function to generate a prime number within the specified range
    public static int generatePrime() {
        int primeRange;
        do {
            primeRange = ThreadLocalRandom.current().nextInt(1000, 9000);
        } while (!isPrime(primeRange));
        return primeRange;
    }

    // Function to generate a generator
    public static int createGenerator(int prime) {
        int generator;
        do {
            generator = ThreadLocalRandom.current().nextInt(2, prime - 1);
        } while (squareAndMultiply(generator, prime - 1, prime) != 1);
        return generator;
    }

    // Function for square and multiply algorithm
    public static long squareAndMultiply(long base, long exponent, long modulo) {
        long result = 1;
        while (exponent > 0) {
            //checks the least significant bit
            if ((exponent & 1) == 1) {
                result = (result * base) % modulo;
            }
            base = (base * base) % modulo;
            //Right shift the exponent
            exponent >>= 1;
        }
        return result;
    }


    // Function to generate public and private keys
    public static long[] generateKeys(int p, int generator) {
        long privateKey = ThreadLocalRandom.current().nextInt(2, p - 2); // d
        long publicKey = squareAndMultiply(generator, privateKey, p); // beta = generator^d mod p
        return new long[]{publicKey, privateKey};
    }


    // Function used to check if gcd(ke, p-1) = 1
    public static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }


    // Function for signature generation

    public static long[] generateSignature(long message, int p, int generator, long privateKey) {
        long ke, r, s;
        do {
            // generate the ephemeral private key
            ke = ThreadLocalRandom.current().nextInt(2, p - 2);
        } while (gcd(ke, p - 1) != 1); // checking if gcd(ke, p-1) = 1
        // computing r = generator^ke mod p
        r = squareAndMultiply(generator, ke, p);
        // to find the inverse of ke
        long[] extended = extendedEuclidean(ke, p - 1);
        // Ensure positive value
        long inverse = extended[1] ;
        if (inverse < 0) {
            inverse += (p - 1); // Ensure s is positive
        }
        // computing s = (x-d*r)ke^-1 mod p-1
        s = ((message - privateKey * r) * inverse) % (p - 1);
        if (s < 0) {
            s += (p - 1); // Ensure s is positive
        }
        return new long[]{r, s, ke};
    }



    // Function for signature verification
    public static boolean verifySignature(long[] signature, long publicKey, long alpha, long message, int p) {
        long r = signature[0];
        long s = signature[1];
        // computing t = (beta^r) * (r^s) mod p
        long t = (squareAndMultiply(publicKey, r, p) * squareAndMultiply(r, s, p)) % p;
        // generator^x mod p
        long alphaPowX = squareAndMultiply(alpha, message, p);
        return t == alphaPowX;
    }

    public static long decrypt(long[] encrypted, long privateKey, int prime) {
        long r = encrypted[0];
        long s = encrypted[1];
        long k = encrypted[2];
        // Compute dr + ks mod (p-1)
        long dr = (privateKey * r) % (prime - 1);
        long ks = (k * s) % (prime - 1);
        long x = (dr + ks) % (prime - 1);
        return x;
    }


    // Function for extended Euclidean algorithm
    public static long[] extendedEuclidean(long a, long b) {
        long t = 0, s = 1, last_t = 1, last_s = 0;
        while (b != 0) {
            long quotient = a / b;
            long temp = b;
            b = a % b;
            a = temp;

            long temp_t = t;
            t = last_t - quotient * t;
            last_t = temp_t;

            long temp_s = s;
            s = last_s - quotient * s;
            last_s = temp_s;
        }
        return new long[]{a, last_t, last_s};
    }

    public static void main(String[] args) {
        // Get the message from the user
        Scanner scanner = new Scanner(System.in);
        int message;
        do {
            System.out.print("Enter a number: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.next(); // Consume the invalid input
                System.out.print("Enter a number: ");
            }
            message = scanner.nextInt();
        } while (message <= 0);

        System.out.println("Message: " + message);

        // Generate prime number and generator
        int p = generatePrime();
        System.out.println("Prime generated: " + p);
        int generator = createGenerator(p);
        System.out.println("alpha: " + generator);

        // Generate keys
        long[] keys = generateKeys(p, generator);
        long publicKey = keys[0];
        long privateKey = keys[1];

        System.out.println("Public Key(beta): " + publicKey);
        System.out.println("Private Key(d): " + privateKey);

        // Generate the signature
        long[] signature = generateSignature(message, p, generator, privateKey);
        System.out.println("Generated Signature (r, s, ke): (" + signature[0] + ", " + signature[1] + ", " + signature[2] + ")");

        // Verify the signature
        boolean verifiedSign = verifySignature(signature, publicKey, generator, message, p);
        if (verifiedSign) {
            System.out.println("Signature Verified");
        } else {
            System.out.println("Signature Not Verified");
        }

        // Decrypt the message
        long decryptedMessage = decrypt(signature, privateKey, p);
        System.out.println("Decrypted message: " + decryptedMessage);

    }

}

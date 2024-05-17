# El-Gamaal-DC-encryption
implementation of El Gamaal DC encryption java code
DCElgamal Java Implementation README
Overview
This Java program implements a simplified version of the ElGamal Digital Signature algorithm. The program includes functionalities to generate prime numbers, create keys, generate and verify digital signatures, and decrypt messages. It uses the square and multiply algorithm for modular exponentiation and the extended Euclidean algorithm for computing modular inverses.

Features
Prime Number Generation: Generates a prime number within a specified range.
Generator Creation: Creates a generator for the cyclic group.
Key Generation: Generates public and private keys.
Signature Generation: Generates a digital signature for a given message.
Signature Verification: Verifies the generated signature.
Decryption: Decrypts the encrypted message.
Code Explanation
Main Functions
isPrime(int n): Checks if a number is prime.
generatePrime(): Generates a prime number within the range [1000, 9000].
createGenerator(int prime): Generates a generator for the given prime number.
squareAndMultiply(long base, long exponent, long modulo): Performs modular exponentiation using the square and multiply algorithm.
generateKeys(int p, int generator): Generates public and private keys.
gcd(long a, long b): Computes the greatest common divisor using the Euclidean algorithm.
generateSignature(long message, int p, int generator, long privateKey): Generates a digital signature for a message.
verifySignature(long[] signature, long publicKey, long alpha, long message, int p): Verifies the generated digital signature.
decrypt(long[] encrypted, long privateKey, int prime): Decrypts the encrypted message.
extendedEuclidean(long a, long b): Computes the extended Euclidean algorithm to find the modular inverse.
Program Execution
The main method coordinates the execution flow:

User Input: Prompts the user to enter a valid number (message).
Prime and Generator Generation: Generates a prime number and a corresponding generator.
Key Generation: Generates public and private keys.
Signature Generation: Generates a digital signature for the input message.
Signature Verification: Verifies the generated signature.
Decryption: Decrypts the message and prints the results.
Example Usage
Upon running the program, follow the prompts:

Enter a positive integer as the message.
The program will generate a prime number, a generator, and keys.
It will generate and print a digital signature for the message.
It will verify the signature and print the result.
Finally, it will decrypt the message and print the decrypted value.
Notes
This implementation uses a simplified range for prime number generation. In practice, larger primes should be used for enhanced security.
The program ensures that all inputs and outputs are within valid ranges and handles invalid inputs gracefully.
The decryption method in this implementation is simplified and intended for demonstration purposes.
License
This project is open-source and available under the MIT License. Feel free to use, modify, and distribute the code as per the terms of the license.

Contact
For any issues or contributions, please feel free to open an issue or a pull request on the project's repository.

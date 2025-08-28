
Features
Decodes large numbers from various bases using BigInteger.

Uses manual string parsing to read input JSON (no external libraries required).

Computes the polynomial constant term precisely with big number arithmetic.

Handles large input cases robustly without overflow.

Getting Started
Prerequisites
Java Development Kit (JDK) 8 or later installed.

Basic terminal or command prompt knowledge.

Running the Project
Clone the repository:

git clone https://github.com/your-username/your-repository.git
cd your-repository

Make sure your input.json file with polynomial roots is in the project directory.

Compile the Java program:

javac CatalogPlacements.java

Run the program:

java CatalogPlacements

The program will output the decoded roots and the secret constant c.

Input File Format
The input file input.json should be in the following format:

{
  "keys": {
    "n": 4,
    "k": 3
  },
  "1": {
    "base": "10",
    "value": "4"
  },
  "2": {
    "base": "2",
    "value": "111"
  },
  "3": {
    "base": "10",
    "value": "12"
  },
  "6": {
    "base": "4",
    "value": "213"
  }
}

n is the number of roots.

k is the minimum number of roots required for interpolation.

Each root entry has:

base: The numeral system base of the encoded y-value.

value: The encoded y-value.

How It Works
The program parses the JSON file manually to avoid external libraries.

It decodes each y-value from its respective base into a BigInteger.

It then calculates the secret polynomial constant term using Lagrange interpolation evaluated at x=0 with BigInteger arithmetic.

License
This project is open source and available under the MIT License.

Feel free to open Issues or Pull Requests if you want to contribute or report bugs.

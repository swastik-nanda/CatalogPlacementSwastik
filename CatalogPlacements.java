import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;

public class CatalogPlacements {

  public static BigInteger decodeBase(String value, int base) {
    value = value.toLowerCase();
    String digits = "0123456789abcdef";
    BigInteger result = BigInteger.ZERO;
    BigInteger bigBase = BigInteger.valueOf(base);

    for (char c : value.toCharArray()) {
      int digit = digits.indexOf(c);
      if (digit < 0 || digit >= base) {
        throw new IllegalArgumentException("Digit '" + c + "' not valid for base " + base);
      }
      result = result.multiply(bigBase).add(BigInteger.valueOf(digit));
    }
    return result;
  }

  public static void main(String[] args) {
    try {
      String content = new String(Files.readAllBytes(Paths.get("input.json")));
      content = content.replaceAll("\\s", "");

      int nStart = content.indexOf("\"n\":") + 4;
      int nEnd = content.indexOf(",", nStart);
      int n = Integer.parseInt(content.substring(nStart, nEnd));

      int kStart = content.indexOf("\"k\":") + 4;
      int kEnd = content.indexOf("}", kStart);
      int k = Integer.parseInt(content.substring(kStart, kEnd));

      Map<Integer, BigInteger> decodedRoots = new TreeMap<>();
      int searchIndex = kEnd;

      while (decodedRoots.size() < n) {
        int keyStart = content.indexOf("\"", searchIndex + 1) + 1;
        if (keyStart == 0)
          break;
        int keyEnd = content.indexOf("\"", keyStart);
        String keyStr = content.substring(keyStart, keyEnd);
        int x = Integer.parseInt(keyStr);

        int baseStart = content.indexOf("\"base\":\"", keyEnd) + 8;
        int baseEnd = content.indexOf("\"", baseStart);
        int base = Integer.parseInt(content.substring(baseStart, baseEnd));

        int valueStart = content.indexOf("\"value\":\"", baseEnd) + 9;
        int valueEnd = content.indexOf("\"", valueStart);
        String valueStr = content.substring(valueStart, valueEnd);

        BigInteger y = decodeBase(valueStr, base);
        decodedRoots.put(x, y);

        searchIndex = valueEnd;
      }

      System.out.println("Minimum roots needed (k): " + k);
      System.out.println("\nCorrectly Decoded Roots:");
      for (Map.Entry<Integer, BigInteger> entry : decodedRoots.entrySet()) {
        System.out.println("x = " + entry.getKey() + ", y = " + entry.getValue());
      }

      BigInteger secretC = calculateSecretC(decodedRoots, k);
      System.out.println("\nSecret c: " + secretC);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static BigInteger calculateSecretC(Map<Integer, BigInteger> roots, int k) {
    Integer[] xVals = new Integer[k];
    BigInteger[] yVals = new BigInteger[k];

    int i = 0;
    for (Map.Entry<Integer, BigInteger> entry : roots.entrySet()) {
      if (i >= k)
        break;
      xVals[i] = entry.getKey();
      yVals[i] = entry.getValue();
      i++;
    }

    BigInteger c = BigInteger.ZERO;

    for (i = 0; i < k; i++) {
      BigInteger numerator = BigInteger.ONE;
      BigInteger denominator = BigInteger.ONE;
      for (int j = 0; j < k; j++) {
        if (i != j) {
          numerator = numerator.multiply(BigInteger.valueOf(-xVals[j]));
          denominator = denominator.multiply(BigInteger.valueOf(xVals[i] - xVals[j]));
        }
      }
      BigInteger term = yVals[i].multiply(numerator).divide(denominator);
      c = c.add(term);
    }

    return c;
  }
}

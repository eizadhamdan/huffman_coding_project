import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the string to encode using Huffman Coding:");
        String str = scanner.nextLine();

        HuffmanCoder hf = new HuffmanCoder(str);

        String cs = hf.encode(str);
        System.out.println("Encoded String: " + cs);

        String dc = hf.decode(cs);
        System.out.println("Decoded String: " + dc);

        scanner.close();
    }
}

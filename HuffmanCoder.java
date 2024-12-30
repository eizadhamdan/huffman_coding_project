import java.util.*;

class HuffmanCoder {
    HashMap<Character, String> encoder;
    HashMap<String, Character> decoder;

    public class Node implements Comparable<Node> {
        Character data;
        int cost; // frequency
        Node left;
        Node right;

        public Node(Character data, int cost) {
            this.data = data;
            this.cost = cost;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(Node other) {
            return this.cost - other.cost;
        }
    }

    private Node root; // Root of the Huffman Tree

	public HashMap<Character, String> getEncoder() {
    	return this.encoder;
	}


    public HuffmanCoder(String feeder) throws Exception {
        HashMap<Character, Integer> fmap = new HashMap<>();

        for (int i = 0; i < feeder.length(); i++) {
            char cc = feeder.charAt(i);
            fmap.put(cc, fmap.getOrDefault(cc, 0) + 1);
        }

        Heap<Node> minHeap = new Heap<>();
        Set<Map.Entry<Character, Integer>> entrySet = fmap.entrySet();

        for (Map.Entry<Character, Integer> entry : entrySet) {
            Node node = new Node(entry.getKey(), entry.getValue());
            minHeap.insert(node);
        }

        while (minHeap.size() != 1) {
            Node first = minHeap.remove();
            Node second = minHeap.remove();

            Node newNode = new Node(null, first.cost + second.cost);
            newNode.left = first;
            newNode.right = second;

            minHeap.insert(newNode);
        }

        this.root = minHeap.remove();

        this.encoder = new HashMap<>();
        this.decoder = new HashMap<>();

        this.initEncoderDecoder(this.root, "");
    }

    private void initEncoderDecoder(Node node, String osf) {
        if (node == null) {
            return;
        }
        if (node.left == null && node.right == null) {
            this.encoder.put(node.data, osf);
            this.decoder.put(osf, node.data);
        }
        initEncoderDecoder(node.left, osf + "0");
        initEncoderDecoder(node.right, osf + "1");
    }

    public void displayTree() {
        System.out.println("Huffman Tree:");
        displayTree(this.root, "");
    }

    private void displayTree(Node node, String indent) {
        if (node == null) {
            return;
        }

        if (node.data != null) {
            System.out.println(indent + "Character: '" + node.data + "', Frequency: " + node.cost);
        } else {
            System.out.println(indent + "Internal Node, Frequency: " + node.cost);
        }

        displayTree(node.left, indent + "  ");
        displayTree(node.right, indent + "  ");
    }

    public String encode(String source) {
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < source.length(); i++) {
            ans.append(encoder.get(source.charAt(i)));
        }

        return ans.toString();
    }

    public String decode(String codedString) {
        StringBuilder key = new StringBuilder();
        StringBuilder ans = new StringBuilder();

        for (int i = 0; i < codedString.length(); i++) {
            key.append(codedString.charAt(i));
            if (decoder.containsKey(key.toString())) {
                ans.append(decoder.get(key.toString()));
                key.setLength(0);
            }
        }

        return ans.toString();
    }

    // Getter for the root of the Huffman Tree
    public Node getRoot() {
        return this.root;
    }
}


import javax.swing.*;
import java.awt.*;

class TreePanel extends JPanel {
    private HuffmanCoder.Node rootNode;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (rootNode != null) {
            drawTree(g, getWidth() / 2, 20, rootNode, getWidth() / 4);
        }
    }

    private void drawTree(Graphics g, int x, int y, HuffmanCoder.Node node, int offset) {
        if (node == null) return;

        g.setColor(Color.BLACK);
        if (node.left != null) {
            g.drawLine(x, y, x - offset, y + 50);
            drawTree(g, x - offset, y + 50, node.left, offset / 2);
        }
        if (node.right != null) {
            g.drawLine(x, y, x + offset, y + 50);
            drawTree(g, x + offset, y + 50, node.right, offset / 2);
        }

        g.setColor(Color.BLUE);
        String text = node.data != null ? String.valueOf(node.data) : " ";
        g.drawOval(x - 15, y - 15, 30, 30);
        g.drawString(text + ":" + node.cost, x - 10, y);
    }

    public void setTree(HuffmanCoder.Node root) {
        this.rootNode = root;
        repaint();
    }
}


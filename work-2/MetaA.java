import java.util.*;
import java.io.*;

class Node {
    public String word;
    public String translatedWord;
    public int accesses;
    public Boolean marker;
    public Node left;
    public Node right;

    public Node(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.accesses = 0;
        this.marker = false;
        this.left = null;
        this.right = null;
    }
}

class BTree {
    public Node root;

    public BTree() {
        this.root = null;
    }

    public void push(String word, String translatedWord) {
        if (this.root == null) {
            this.root = new Node(word, translatedWord);
            return;
        }
        Node currNode = this.root;
        String currWord = currNode.word;
        while(word.compareTo(currWord) != 0) {
            currNode.accesses++;
            if (word.compareTo(currWord) < 0) {
                if (currNode.left != null) {
                    currNode = currNode.left;
                    currWord = currNode.word;
                }
                else {
                    currNode.left = new Node(word, translatedWord);
                    return;
                }
            }
            else {
                if (currNode.right != null) {
                    currNode = currNode.right;
                    currWord = currNode.word;
                }
                else {
                    currNode.right = new Node(word, translatedWord);
                    return;
                }
            }
        }
        System.out.println("PALAVRA JA EXISTENTE");
    }

    public Node search(String word, Node node) {
        if (node == null || word.equals(node.word)) {
            return node;
        }
        if (word.compareTo(node.word) < 0) {
            return search(word, node.left);
        }
        return search(word, node.right);
    }

    public void mark(String wordToMark) {
        Node node = search(wordToMark, this.root);
        if (node != null) {
            node.marker = true;
            System.out.println(wordToMark + " " + "MARCADA");
            return;
        }
        System.out.println("PALAVRA NAO EXISTENTE");
    }

    public void printNode(Node node) {
        if (node == null) {
            return;
        }
        printNode(node.left);
        System.out.println(node.word);
        printNode(node.right);
    }

    public void printMarkedNode(Node node) {
        if (node == null) {
            return;
        }
        printMarkedNode(node.left);
        if (node.marker) {
            System.out.println(node.word);
        }
        printMarkedNode(node.right);
    }

    public void printTreeAlphabeticOrder() {
        printNode(this.root);
    }

    public void printMarkedTreeNodesAlphabeticOrder() {
        printMarkedNode(this.root);
    }
}

public class MetaA {
    public static void main(String[] args) {
        BTree tree = new BTree();
        Node aux;
        Scanner input = new Scanner(System.in);
        String strInput;
        String []inputs;

        while(true) {
            strInput = input.nextLine();
            inputs = strInput.split("\\s+");
            if (strInput.trim().isEmpty()) {
                return;
            }

            if(inputs.length > 3){
                for(int i = 3; i < inputs.length; i++){
                    inputs[2] = inputs[2].concat(" ".concat(inputs[i]));
                }
            }

            if (inputs[0].equals("CARREGA")) {
                while(true) {
                    strInput = input.nextLine();
                    inputs = strInput.split("\\s+");
                    if (inputs[0].equals("fim$dicionario")) {
                        System.out.println("DICIONARIO CARREGADO");
                        break;
                    }
                    tree.push(inputs[0], inputs[1]);
                }
            }
            else if (inputs[0].equals("ACRESCENTA")) {
                tree.push(inputs[1], inputs[2]);
                System.out.println("PALAVRA ACRESCENTADA");
            }
            else if (inputs[0].equals("PESQUISA")) {
                aux = tree.search(inputs[1], tree.root);
                if (aux == null) {
                    System.out.println("PALAVRA NAO EXISTENTE");
                }
                else {
                    System.out.println(aux.word + " " + aux.translatedWord);
                }
            }
            else if (inputs[0].equals("MARCA")) {
                tree.mark(inputs[1]);
            }
            else if (inputs[0].equals("LISTA_ALFANUM")) {
                tree.printTreeAlphabeticOrder();
                System.out.println("FIM LISTA");
            }
            else if (inputs[0].equals("LISTA_MARCADAS")) {
                tree.printMarkedTreeNodesAlphabeticOrder();
                System.out.println("FIM MARCADAS");
            }
        }
    }

}

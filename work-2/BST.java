import java.util.*;

class Node {
    String word;
    String translatedWord;
    int accesses;
    Boolean marker;
    Node left;
    Node right;

    Node(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.accesses = 0;
        this.marker = false;
        this.left = null;
        this.right = null;
    }
}

class BSTree {
    Node root;

    BSTree() {
        this.root = null;
    }

    void push(String word, String translatedWord) {
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
        //System.out.println("PALAVRA JA EXISTENTE");
    }

    Node search(Node node, String word) {
        if (node == null || word.equals(node.word)) {
            return node;
        }
        if (word.compareTo(node.word) < 0) {
            return search(node.left, word);
        }
        return search(node.right, word);
    }

    void mark(String wordToMark) {
        Node node = search(this.root, wordToMark);
        if (node != null) {
            node.marker = true;
            System.out.println(wordToMark + " " + "MARCADA");
            return;
        }
        System.out.println("PALAVRA NAO EXISTENTE");
    }

    void printNode(Node node) {
        if (node == null) {
            return;
        }
        printNode(node.left);
        System.out.println(node.word);
        printNode(node.right);
    }

    void printMarkedNode(Node node) {
        if (node == null) {
            return;
        }
        printMarkedNode(node.left);
        if (node.marker) {
            System.out.println(node.word);
        }
        printMarkedNode(node.right);
    }

    void printTreeAlphabeticOrder() {
        printNode(this.root);
    }

    void printMarkedTreeNodesAlphabeticOrder() {
        printMarkedNode(this.root);
    }
}

public class BST {
    public static void main(String[] args) {
        BSTree tree = new BSTree();
        Node aux;
        Scanner input = new Scanner(System.in);
        String strInput;
        String []inputs;

        while(input.hasNextLine()) {
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
                while(input.hasNextLine()) {
                    strInput = input.nextLine();
                    inputs = strInput.split("\\s+");
                    if (inputs[0].equals("fim$dicionario")) {
                        System.out.println("DICIONARIO CARREGADO");
                        break;
                    }
                    else {
                        tree.push(inputs[0], inputs[1]);
                    }
                }
            }
            else if (inputs[0].equals("ACRESCENTA")) {
                tree.push(inputs[1], inputs[2]);
                System.out.println("PALAVRA ACRESCENTADA");
            }
            else if (inputs[0].equals("PESQUISA")) {
                long startTime = System.nanoTime();
                aux = tree.search(tree.root, inputs[1]);
                long endTime = System.nanoTime();
                System.out.println(endTime - startTime);
                if (aux == null) {
                    //System.out.println("PALAVRA NAO EXISTENTE");
                }
                else {
                    //System.out.println(aux.word + " " + aux.translatedWord);
                }
            }
            else if (inputs[0].equals("MARCA")) {
                tree.mark(inputs[1]);
            }
            else if (inputs[0].equals("LISTA_ALFANUM")) {
                long startTime = System.currentTimeMillis();
                tree.printTreeAlphabeticOrder();
                long endTime = System.currentTimeMillis();
                System.out.println(endTime - startTime);
            }
            else if (inputs[0].equals("LISTA_MARCADAS")) {
                tree.printMarkedTreeNodesAlphabeticOrder();
                System.out.println("FIM MARCADAS");
            }
        }
    }
}

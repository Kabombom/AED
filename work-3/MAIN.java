import java.util.Scanner;

class Node {
    String word, translatedWord;
    int accesses, height;
    Boolean isMarked;
    Node left, right;

    Node(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.accesses = 0;
        this.height = 1;
        this.isMarked = false;
        this.left = null;
        this.right = null;
    }
}

class AVL {
    Node root;

    AVL() {
        this.root = null;
    }

    int max(int firstNumber, int secondNumber) {
        if (firstNumber < secondNumber) {
            return secondNumber;
        }
        return firstNumber;
    }

    int getTreeHeight(Node node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    int getBalance(Node node) {
        if (node == null) {
            return 0;
        }
        return (getTreeHeight(node.left) - getTreeHeight(node.right));
    }

    Node rightRotate(Node node) {
        Node leftChild = node.left;
        Node leftRightChild = leftChild.right ;

        leftChild.right = node;
        node.left = leftRightChild;

        node.height = max(getTreeHeight(node.left), getTreeHeight(node.right)) + 1;
        leftChild.height = max(getTreeHeight(node.left), getTreeHeight(node.right)) + 1;
        return leftChild;
    }

    Node leftRotate(Node node) {
        Node rightChild = node.right;
        Node rightLeftChild = rightChild.left;

        rightChild.left = node;
        node.right = rightLeftChild;

        node.height = max(getTreeHeight(node.left), getTreeHeight(node.right)) + 1;
        rightChild.height = max(getTreeHeight(rightChild.left), getTreeHeight(rightChild.right)) + 1;

        return rightChild;
    }

    Node push(Node node, String word, String translatedWord) {
        if (node == null) {
            return (new Node(word, translatedWord));
        }
        if (word.compareTo(node.word) < 0) {
            node.left = push(node.left, word, translatedWord);
        }
        else if (word.compareTo(node.word) > 0) {
            node.right = push(node.right, word, translatedWord);
        }
        else {
            System.out.println("PALAVRA JA EXISTENTE");
        }

        node.height = max(getTreeHeight(node.left), getTreeHeight(node.right)) + 1;
        int balance = getBalance(node);

        if (balance > 1 && word.compareTo(node.left.word) < 0) {
            return rightRotate(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) > 0) {
            return leftRotate(node);
        }
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
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
            node.isMarked = true;
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
        if (node.isMarked) {
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

public class MAIN {
    public static void main(String[] args) {
        AVL tree = new AVL();
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
                    tree.root = tree.push(tree.root, inputs[0], inputs[1]);
                }
            }
            else if (inputs[0].equals("ACRESCENTA")) {
                tree.root = tree.push(tree.root, inputs[1], inputs[2]);
                System.out.println("PALAVRA ACRESCENTADA");
            }
            else if (inputs[0].equals("PESQUISA")) {
                aux = tree.search(tree.root, inputs[1]);
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

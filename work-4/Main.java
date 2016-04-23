import java.util.Scanner;

class Node {
    String word, translatedWord;
    int accesses, markers, height;
    Node left, right;

    Node(String word, String translatedWord) {
        this.word = word;
        this.translatedWord = translatedWord;
        this.accesses = 0;
        this.markers = 0;
        this.height = 1;
        this.left = null;
        this.right = null;
    }

    int getAccessesAndMarkers() { return this.accesses + this.markers; }
}

class NodesAccessed {
    Node[] array;
    int pushPosition;

    NodesAccessed(int size) {
        this.array = new Node[size];
        this.pushPosition = 0;
    }

    void push(Node node) {
        array[pushPosition] = node;
        pushPosition++;
    }

    void getAccessedNodes(Node node) {
        if (node == null) {
            return;
        }
        getAccessedNodes(node.left);
        if (node.getAccessesAndMarkers() != 0) {
            push(node);
        }
        getAccessedNodes(node.right);
    }
}

class AVL {
    int size;
    Node root;

    AVL() {
        this.size = 0;
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
        if (word.compareTo(node.word) > 0) {
            node.right = push(node.right, word, translatedWord);
        }
        else {
            System.out.println("PALAVRA JA EXISTENTE");
        }

        node.height = max(getTreeHeight(node.left), getTreeHeight(node.right)) + 1;
        int balance = getBalance(node);
        if (node.left != null && node.right != null && node.left.right != null) {
            if (balance > 1 && word.compareTo(node.left.word) < 0) {
                return rightRotate(node);
            }
        }
        if (node.right != null && node.left != null && node.right.left != null) {
            if (balance < -1 && word.compareTo(node.right.word) > 0) {
                return leftRotate(node);
            }
        }
        if (node.left != null && node.right != null && node.right.left != null && node.right.right != null && node.left.right != null && node.left.left != null) {
            if (balance > 1 && word.compareTo(node.left.word) > 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }
        if (node.left != null && node.right != null && node.right.left != null && node.right.right != null && node.left.right != null && node.left.left != null) {
            if (balance < -1 && word.compareTo(node.right.word) < 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }
        return node;
    }

    Node search(Node node, String word) {
        if (node == null || word.equals(node.word)) {
            if (node != null) {

                node.accesses++;
            }
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
            node.accesses--;
            node.markers++;
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
        if (node.markers != 0) {
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

public class Main {
    private static void insertionSort(Node[] array) {
        int arraySize = array.length;
        int j;
        for (int i = 1; i < arraySize; i++) {
            if (array[i] == null) {
                return;
            }
            Node keyNode = array[i];
            j = i - 1;

            while (j >= 0 && array[j].getAccessesAndMarkers() > keyNode.getAccessesAndMarkers()) {
                array[j+1] = array[j];
                j--;
            }
            array[j+1] = keyNode;
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String strInput;
        String []inputs;
        Node aux;
        AVL tree = new AVL();

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

            switch (inputs[0]) {
                case "CARREGA":
                    while (input.hasNextLine()) {
                        strInput = input.nextLine();
                        inputs = strInput.split("\\s+");
                        if (inputs[0].equals("fim$dicionario")) {
                            System.out.println("DICIONARIO CARREGADO");
                            break;
                        }
                        tree.size++;
                        tree.root = tree.push(tree.root, inputs[0], inputs[1]);
                    }
                    break;
                case "ACRESCENTA":
                    tree.root = tree.push(tree.root, inputs[1], inputs[2]);
                    tree.size++;
                    System.out.println("PALAVRA ACRESCENTADA");
                    break;
                case "PESQUISA":
                    aux = tree.search(tree.root, inputs[1]);
                    if (aux == null) {
                        System.out.println("PALAVRA NAO EXISTENTE");
                    } else {
                        System.out.println(aux.word + " " + aux.translatedWord);
                    }
                    break;
                case "MARCA":
                    tree.mark(inputs[1]);
                    break;
                case "LISTA_ALFANUM":
                    tree.printTreeAlphabeticOrder();
                    System.out.println("FIM LISTA");
                    break;
                case "LISTA_MARCADAS":
                    tree.printMarkedTreeNodesAlphabeticOrder();
                    System.out.println("FIM MARCADAS");
                    break;
                case "LISTA_MAIS_ACESOS":
                    NodesAccessed nodesAccessed = new NodesAccessed(tree.size);
                    nodesAccessed.getAccessedNodes(tree.root);
                    insertionSort(nodesAccessed.array);
                    for (int i = nodesAccessed.array.length - 1; i >= 0; i--) {
                        Node node = nodesAccessed.array[i];
                        if (node != null) {
                            System.out.println(node.word + " " + node.getAccessesAndMarkers());
                        }
                    }
            }
        }
    }
}

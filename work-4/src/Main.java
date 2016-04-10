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

    int getAccessesAndMarked() { return this.accesses + this.markers; }
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

class BinaryHeap {
    Node[] heap;
    int pushPosition;

    BinaryHeap(int size) {
        this.heap = new Node[size+1];
        this.pushPosition = 0;
    }

    int getParentNode(int nodeIndex) { return nodeIndex / 2; }
    int getLeftNode(int nodeIndex) { return 2 * nodeIndex + 1; }
    int getRightNode(int nodeIndex) { return 2 * nodeIndex + 2; }

    private void bubbleUp(int nodeIndex) {
        int parentIndex;
        Node currentNode;
        if (nodeIndex != 0 && getParentNode(nodeIndex) != 0 || getParentNode(nodeIndex) != 1) {
            parentIndex = getParentNode(nodeIndex);
            System.out.println("pai: " + parentIndex + " " + heap[parentIndex]);
            System.out.println("filho: " + nodeIndex + " " + heap[nodeIndex]);
            if (heap[nodeIndex].getAccessesAndMarked() < heap[parentIndex].getAccessesAndMarked()) {
                currentNode = heap[nodeIndex];
                heap[parentIndex] = heap[nodeIndex];
                heap[nodeIndex] = currentNode;
                bubbleUp(nodeIndex);
            }
            else if(heap[nodeIndex].getAccessesAndMarked() == heap[parentIndex].getAccessesAndMarked()) {
                if (heap[nodeIndex].word.compareTo(heap[parentIndex].word) < 0) {
                    currentNode = heap[nodeIndex];
                    heap[parentIndex] = heap[nodeIndex];
                    heap[nodeIndex] = currentNode;
                    bubbleUp(nodeIndex);
                }
            }
        }
    }

    void push(Node node) {
        pushPosition++;
        heap[pushPosition] = node;
        if (pushPosition != 1) {
            for (int i = pushPosition; i < heap.length; i++) {
                bubbleUp(i);
            }
        }
    }

    void copyNodesFromTree(Node node) {
        if (node == null) {
            return;
        }
        copyNodesFromTree(node.left);
        push(node);
        copyNodesFromTree(node.right);
    }
}

public class Main {
    private static void swap(Node[] heap, int position1, int position2) {
        Node temp = heap[position1];
        heap[position1] = heap[position2];
        heap[position2] = temp;
    }

    private static void heapsort(Node[] heap) {
        for (int i = heap.length - 1; i > 0; i--) {
            if (heap[0].getAccessesAndMarked() < heap[i].getAccessesAndMarked()) {
                swap(heap, 0, i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String strInput;
        String []inputs;
        Node aux;
        AVL tree = new AVL();
        BinaryHeap binaryHeap;

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
                            binaryHeap = new BinaryHeap(tree.size);
                            binaryHeap.copyNodesFromTree(tree.root);
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

            }
        }
    }
}

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define WORD_SIZE 40;
#define TRANSLATED_WORD_SIZE 100;

typedef struct node {
    char *word;
    char *translatedWord;
    int accesses;
    int marker;
    struct node *left;
    struct node *right;
}Node;

typedef struct bst {
    Node *root;
}BST;

BST *create();
void push(BST *tree, char *word, char *translatedWord);
Node *search(BST *tree, char *toSearch);
void mark(BST *tree, char *wordToMark);
void printNode(Node *node);
void printMarkedNode(Node *node);
void printTreeAlphabeticOrder(BST *tree);
void printMarkedTreeNodesAlphabeticOrder(BST *tree);

int main(int argc, char *argv[]) {
    BST *tree = create();
    push(tree, "cao", "dog");
    push(tree, "ao", "dog");
    push(tree, "tee", "dog");
    push(tree, "t", "dog");
    push(tree, "zeta", "dog");
    push(tree, "teta", "dog");
    push(tree, "adb", "dog");
    push(tree, "badooo", "dog");
    push(tree, "atttc", "dog");
    mark(tree, "badooo");
    mark(tree, "test");
    mark(tree, "teta");
    printMarkedTreeNodesAlphabeticOrder(tree);
    return 0;
}

BST *create() {
    BST *tree = (BST *) malloc(sizeof(BST));
    tree -> root = NULL;
    return tree;
}

void push(BST *tree, char *word, char *translatedWord) {
    if (tree == NULL) {
        return;
    }
    Node *newNode = (Node *) malloc(sizeof(Node));
    newNode -> word = word;
    newNode -> translatedWord = translatedWord;
    newNode -> accesses = 0;
    newNode -> marker = 0;
    newNode -> left = NULL;
    newNode -> right = NULL;
    if (tree -> root == NULL) {
        tree -> root = newNode;
        return;
    }
    Node *node = tree -> root;
    while(strcmp(word, node -> word) != 0) {
        node -> accesses += 1;
        if (strcmp(word, node -> word) < 0) {
            if (node -> left != NULL) {
                node = node -> left;
            }  
            else {
                node -> left = newNode;
                printf("PALAVRA ACRESCENTADA\n");
                return;
            }
        }
        else {
            if (node -> right != NULL) {
                node = node -> right;
            }
            else {
                node -> right = newNode;
                printf("PALAVRA ACRESCENTADA\n");
                return;
            }
        }
    }
    printf("PALAVRA JA EXISTENTE\n");
}

Node *search(BST *tree, char *wordToSearch) {
    if (tree == NULL || tree -> root == NULL) {
        printf("PALAVRA NÃO EXISTENTE\n");
        return NULL;
    }
    Node *node = tree -> root;
    while(strcmp(wordToSearch, node -> word) != 0) {
        node -> accesses += 1;
        if (strcmp(wordToSearch, node -> word) < 0) {
            if (node -> left != NULL) {
                node = node -> left;
            }
            else {
                return NULL;
            }
        }
        else {
            if (node -> right != NULL) {
                node = node -> right;
            }
            else {
                return NULL;
            }
        }
    }
    return node;
}

void mark(BST *tree, char *wordToMark) {
    Node *nodeToMark = search(tree, wordToMark);
    if (nodeToMark != NULL) {
        nodeToMark -> marker += 1;
        printf("%s MARCADA\n", nodeToMark -> word);
        return;
    }
    printf("PALAVRA NÃO EXISTENTE\n");
}

void printNode(Node *node) {
    if (node == NULL) {
        return;
    }
    printNode(node -> left);
    printf("%s >> %s\n", node -> word, node -> translatedWord);
    printNode(node -> right);
}

void printMarkedNode(Node *node) {
    if (node == NULL) {
        return;
    }
    printMarkedNode(node -> left);
    if (node -> marker) {
        printf("%s >> %s\n", node -> word, node -> translatedWord);
    }
    printMarkedNode(node -> right);
}

void printTreeAlphabeticOrder(BST *tree) {
    printNode(tree -> root);
}

void printMarkedTreeNodesAlphabeticOrder(BST *tree) {
    printMarkedNode(tree -> root);
}


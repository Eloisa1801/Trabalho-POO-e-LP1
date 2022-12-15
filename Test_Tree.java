
public class Test_Tree {
    public static void main(String[] args) {
        Tree testTree = new Tree();

        testTree.inserir(9, testTree.root);
        testTree.inserir(5, testTree.root);
        testTree.inserir(14, testTree.root);
        testTree.inserir(2, testTree.root);
        testTree.inserir(7, testTree.root);
        testTree.inserir(11, testTree.root);
        testTree.inserir(4, testTree.root);
        testTree.inserir(1, testTree.root);
        testTree.inserir(6, testTree.root);
        testTree.inserir(8, testTree.root);
        // testTree.remover(7);

        // if(!testTree.procurar(testTree.root, 11, false))
        // testTree.remover(7);
        testTree.remover(testTree.root, 7);
        // System.out.println("Elemento não encontrado");

        System.out.println(testTree);

    }

}
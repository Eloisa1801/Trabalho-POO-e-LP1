import java.sql.SQLException;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws SQLException {
        ConnectionFactory.createConnection();
        Tree tree = new Tree();
        int op = 1;
        int num;
        while (op != 0) {
            System.out.println("Digite a opcap desejada: ");
            System.out.println("\n--------------------------------------------");
            System.out.println(" 0 - Encerrar");
            System.out.println(" 1 - Inserir números na árvore binária e gravar no BD");
            System.out.println(" 2 - Imprimir uma árvore binária existente");
            System.out.println("----------------------------------------------");
            Scanner leitura = new Scanner(System.in);
            op = leitura.nextInt();

            if (op == 0) {
                System.out.println("Encerrando operacao");
            } else if (op == 1) {
                System.out.println("Informe o numero a ser inserido na arvore");
                num = leitura.nextInt();
                tree.inserir(num, tree.root); // ---------------
                tree.inserir_banco(num);
            } else if (op == 2) {
                System.out.println("Exibindo arvore");
                tree.pre_ordem(tree.root);
                tree.in_ordem(tree.root);
                tree.pos_ordem(tree.root);
            } else {
                System.out.println("opcao invalida");
            }
        }
    }
}

// -----------------------------

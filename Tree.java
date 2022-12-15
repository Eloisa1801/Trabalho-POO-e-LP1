
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Tree {
  Node root = null;
  private Connection conectarbd = null;

  public void inserir(int info, Node node) {
    if (node == null) {
      root = new Node(info);
      // System.out.println("Criando a árvore...!\nroot = " + root.info);
      return;
    }
    if (node.info > info) {
      // inserir a esq
      if (node.esq == null) {
        node.esq = new Node(info);
        return;
      }
      // func. recursiva
      inserir(info, node.esq);

    } else {
      // inserir a dir
      if (node.dir == null) {
        node.dir = new Node(info);
        return;
      }

      inserir(info, node.dir);
    }

  }

  public boolean isNode(Node n) {
    return n != null;
  }

  public boolean procurar(Node node, int valor, boolean found) {
    boolean retorno = found;
    // procurar pelo elemento na arvore
    // qdo achar, imprimir se tem filhos e quais são seus filhos
    if (node == null) {
      System.out.println("Árvore Vazia!");
      return false;
    }

    if (valor == node.info) {
      System.out.println("Elemento encontrado!");
      int qto = 0, fEsq = 0, fDir = 0;
      if (node.esq != null) {
        qto++;
        fEsq = node.esq.info;
      }
      if (node.dir != null) {
        qto++;
        fDir = node.dir.info;
      }

      System.out.println("O elemento " +
          node.info + " tem " +
          qto + " filhos\n");

      if (fEsq != 0)
        System.out.println("Filho esq: " + fEsq);
      if (fDir != 0)
        System.out.println("Filho dir: " + fDir);

      return true;

    }

    if (isNode(node.esq))
      retorno = procurar(node.esq, valor, retorno);

    if (isNode(node.dir))
      retorno = procurar(node.dir, valor, retorno);

    return retorno;

  }

  public void pre_ordem(Node node) {
    if (node == null) {
      System.out.println("Árvore Vazia!");
      return;
    }

    System.out.println(node.info);
    if (isNode(node.esq))
      pre_ordem(node.esq);

    if (isNode(node.dir))
      pre_ordem(node.dir);

  }

  public void in_ordem(Node node) {
    if (node == null) {
      System.out.println("Árvore Vazia!");
      return;
    }

    if (isNode(node.esq))
      pre_ordem(node.esq);

    System.out.println(node.info);

    if (isNode(node.dir))
      pre_ordem(node.dir);
  }

  public void pos_ordem(Node node) {
    if (node == null) {
      System.out.println("Árvore Vazia!");
      return;
    }

    if (isNode(node.esq))
      pre_ordem(node.esq);

    if (isNode(node.dir))
      pre_ordem(node.dir);

    System.out.println(node.info);
  }

  // --------- remover Node

  private Node maiorValor(Node node) {
    while (node.dir != null) {
      node = node.dir;
    }

    return node;
  }

  public void remover(int info) {
    remover(this.root, info);
  }

  public Node remover(Node node, int info) {
    // chave não encontrada na árvore
    if (node == null) {
      return node;
    }

    // valor menor, procurar na sub-árvore esquerda
    if (info < node.info) {
      remover(node.esq, info);
    } else if (info > node.info) {
      // valor maior, procurar na sub-árvore direita
      remover(node.dir, info);
    } else { // valor encontrado
      // caso 1: nó é uma folha (não tem filhos)
      if (node.esq == null && node.dir == null) {
        // remove-o (seta a "raiz" deste nó para null)
        return null;
      } else if (node.esq != null && node.dir != null) {
        // caso 3: nó tem 2 filhos
        // encontrar o maior dos filhos que antecede o nó
        Node maiorAntecessor = maiorValor(node.esq);

        // copia o valor do antecessor para este nó
        node.info = maiorAntecessor.info;
        node.esq = null;
        // remove o antecessor recursivamente
        remover(node.esq, maiorAntecessor.info);
      } else {
        // caso 2: nó só tem um filho
        Node child = (node.esq != null) ? node.esq : node.dir;
        node = child;
      }
    }

    return node;
  }

  // inserindo no BD

  /**
   * @param info
   * @throws SQLException
   */
  public void inserir_banco(int info) throws SQLException {

    this.conectarbd = ConnectionFactory.createConnection();
    String noSQL = "INSERT INTO tree (node) VALUES (?)";
    try {
      PreparedStatement ps = conectarbd.prepareStatement(noSQL);
      ps.setInt(1, info);
      ps.executeUpdate();
      System.out.println("num inserido");
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  public void buscar_banco(Tree tree) {
    String arvore = "SELECT * FROM tree";
    ArrayList<Integer> busca_num = new ArrayList<>();

    try {
      PreparedStatement ps = this.conectarbd.prepareStatement(arvore);
      ResultSet rs = ps.executeQuery();
      for (int i = 0; i < busca_num.size(); i++) {
        busca_num.add(rs.getInt("node"));
      }
      for (int i = 0; i < busca_num.size(); i++) {
        tree.inserir(i, root);
      }
      int num = busca_num.get(1);
    } catch (SQLException e) {
      System.out.println(e);
    }
  }

  // -----------------------------------
  public void print(String prefix, Node n, boolean isLeft) {
    if (n != null) {
      print(prefix + "     ", n.dir, false);
      System.out.println(prefix + ("|--> ") + n.info);
      print(prefix + "     ", n.esq, true);
    }
  }

  @Override
  public String toString() {
    // this.in_ordem(root); // creditos by leticia novamente!!!
    this.print("", this.root, false);
    return "";
  }
}

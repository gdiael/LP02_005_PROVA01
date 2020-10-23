// ENUNCIADO:
// Implemente uma árvore binária de busca (deve possuir ao menos o método de inserir elementos). Instacie
// árvores e insira valores inteiros do 1 ao 15 (inclusive) satisfazendo os seguintes casos:
//   a. Uma árvore de altura máxima. Apresente cinco árvores diferentes que possuam altura máxima.
//   b. Uma árvore de altura mínima.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foi reutilizado o código da Lista 02, para implementação da arvore binária de busca.
// A única diferença do código enviado é que juntei todos os arquivos em um único
// Foi adicionada uma função Main a classe Tree e essa função é responsável por instânciar e imprimir
// as 6 situações diferentes da arvore: cinco com alturas máximas e uma com altura mínima:
//   1. Para a primeira situação com altura mínima, foram inseridos os itens normalmente de 1 a 15,
//      como o próximo número é sempre maior que o anterior ele sempre é inserido a direita.
//   2. Para a segunda situação, os itens foram inseridos em ordem decrescente de 15 a 1,
//      como o proximo número sempre é menor que o anterior ele sempre ficará a esquerda.
//   3. Para a terceira situação, os itens foram inseridos na seguinte ordem
//      1 15 2 14 3 13 4 12 5 11 6 10 7 9 8 (formando um zigzag)
//   4. Para a quarta situação, os itens foram inseridos de 1 a 7, em ordem crescente
//      e depois de 15 a 8, em ordem decrescente.
//   5. Para a quarta situação, os itens foram inseridos de 15 a 8, em ordem decrescente
//      e depois de 1 a 7, em ordem crescente.
//   6. Para a sexta situação, temos a arvore com tamanho mínimo
//      para isso os itens foram colocados na seguinte ordem
//      8 4 12 2 6 10 14 1 3 5 7 9 11 13 15 (formando um zigzag)
// 
// a impressão da arvore segue o esquema
//  valor ┬─> [direita ]
//        └─> [esquerda]




import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

public class Tree {
    private Node root;

    public class Node{

        // valor contido no nó (atualmente inteiro)
        public Integer value;
        // referência ao nó filho, à diretia
        public Node right;
        // referência ao nó filho, à esquerda
        public Node left;
    
        // construtor
        public Node(Integer value){
            this.value = value;
        }
    
        // verifica se tem nó filho a direita
        public Boolean hasRight(){
            return this.right != null;
        }
    
        // verifica se tem nó filho a esquerda
        public Boolean hasLeft(){
            return this.left != null;
        }
    
        // verifica se o tem está cheio (tem filho a direita e a esquerda)
        public Boolean isFull(){
            return this.hasRight() && this.hasLeft();
        }
    
        // verifica se o nó é estritamente binario (tem 0 ou dois filhos)
        public Boolean isEstrictBin(){
            return this.isFull() || this.isLeaf();
        }
    
        // verifica se o nó está vazio (não tem filhos)
        public Boolean isLeaf(){
            return !this.hasRight() && !this.hasLeft();
        }
    
        // imprime este nó e seus filhos
        public String toString(){
            String val = this.value.toString();
            String eStr = (this.hasLeft() ? this.left.toString() : "");
            String dStr = (this.hasRight() ? " " + this.right.toString() : "");
            return val + "(" + eStr + "," + dStr + ")";
        }

        public String subTreeString(String prefix){
            String val = this.value.toString();
            if(this.isLeaf()) return val + "\n";
            String valSpace = "";
            for(int i = 0; i < val.length(); i++){
                valSpace += " ";
            }
            String dStr = " \u252C\u2500> " + (this.hasRight() ? this.right.subTreeString(prefix + valSpace + " \u2502   "): "*\n");
            String eStr = prefix + valSpace + " \u2514\u2500> " + (this.hasLeft() ? this.left.subTreeString(prefix + valSpace + "     ") : "*\n");
            return val + dStr + eStr;
        }
    }

    public Tree(){
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Iniciando instancias da classe Tree");
        System.out.println("");

        Tree arv1 = new Tree();
        for(int i = 1;i < 16;i++){
            arv1.add(i);
        }
        System.out.println("Imprimindo a arvore 1 que foi preenchida com valores de 1 a 15 em ordem crescente");
        System.out.println(arv1);
        System.out.println("A altura dessa arvore eh: " + arv1.height().toString());
        System.out.println("");

        Tree arv2 = new Tree();
        for(int i = 15;i > 0;i--){
            arv2.add(i);
        }
        System.out.println("Imprimindo a arvore 2 que foi preenchida com valores de 15 a 1 em ordem decrescente");
        System.out.println(arv2);
        System.out.println("A altura dessa arvore eh: " + arv2.height().toString());
        System.out.println("");

        Tree arv3 = new Tree();
        int[] vec3 = {1, 15, 2, 14, 3, 13, 4, 12, 5, 11, 6, 10, 7, 9, 8};
        for(int i : vec3){
            arv3.add(i);
        }
        System.out.println("Imprimindo a arvore 3 que foi preenchida com valores de 1 a 15 em ordem de zigzag (1 15 2 14 3 13...)");
        System.out.println(arv3);
        System.out.println("A altura dessa arvore eh: " + arv3.height().toString());
        System.out.println("");

        Tree arv4 = new Tree();
        int[] vec4 = {1, 2, 3, 4, 5, 6, 7, 15, 14, 13, 12, 11, 10, 9, 8};
        for(int i : vec4){
            arv4.add(i);
        }
        System.out.println("Imprimindo a arvore 4 que foi preenchida com valores de 1 a 7 em ordem crescente e de 15 a 8 em ordem descrescente");
        System.out.println(arv4);
        System.out.println("A altura dessa arvore eh: " + arv4.height().toString());
        System.out.println("");

        Tree arv5 = new Tree();
        int[] vec5 = {15, 14, 13, 12, 11, 10, 9, 8, 1, 2, 3, 4, 5, 6, 7};
        for(int i : vec5){
            arv5.add(i);
        }
        System.out.println("Imprimindo a arvore 5 que foi preenchida com valores de 15 a 8 em ordem descrescente e de 1 a 7 em ordem crescente");
        System.out.println(arv5);
        System.out.println("A altura dessa arvore eh: " + arv5.height().toString());
        System.out.println("");
        
        Tree arv6 = new Tree();
        int[] vec6 = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for(int i : vec6){
            arv6.add(i);
        }
        System.out.println("Imprimindo a arvore 6 que foi preenchida com valores de modo a ter altura minima");
        System.out.println(arv6);
        System.out.println("A altura dessa arvore eh: " + arv6.height().toString());
        System.out.println("");
    }

    private Node getNode(Node no, Integer value){
        if(value.equals(no.value)){
            return no;
        }
        if(value < no.value){
            if(no.hasLeft()){
                return getNode(no.left, value);
            } else {
                return no;
            }
        } else {
            if(no.hasRight()){
                return getNode(no.right, value);
            } else {
                return no;
            }
        }
    }

    public boolean add(Integer value){
        if(this.root == null){
            this.root = new Node(value);
            return true;
        } else {
            Node no = getNode(root, value);
            if(!value.equals(no.value)){
                Node newNo = new Node(value);
                if(value < no.value){
                    no.left = newNo;
                } else {
                    no.right = newNo;
                }
                return true;
            }
        }
        return false;
    }

    public Boolean contains(Integer value){
        Node no = this.getNode(this.root, value);
        return value.equals(no.value);
    }

    private Integer height(Node no){
		if(no == null){
			return 0;
		} else {
			return 1 + Math.max(
				height(no.left),
				height(no.right)
			);
		}
	}
	
	public Integer height(){
		return height(root);
	}

    private Integer size(Node no){
        if(no == null){
            return 0;
        }
        return 1 + size(no.right) + size(no.left);
    }

    public Integer size(){
        return this.size(this.root);
    }

    public Boolean isEmpty(){
        return (this.size() == 0);
    }

    private void preOrder(Node no){
		if(no != null){
            String dVal = no.value.toString() + " ";
			System.out.print(dVal);
			preOrder(no.left);
			preOrder(no.right);
		}
    }

    private void posOrder(Node no){
        if(no != null){
            String dVal = no.value.toString() + " ";
            posOrder(no.left);
            posOrder(no.right);
            System.out.print(dVal);
		}
    }

    private void inOrder(Node no){
        if(no != null){
            String dVal = no.value.toString() + " ";
            inOrder(no.left);
            System.out.print(dVal);
            inOrder(no.right);
		}
    }

    public void preOrder(){
        this.preOrder(this.root);
        System.out.println("");
    }

    public void posOrder(){
        this.posOrder(this.root);
        System.out.println("");
    }

    public void inOrder(){
        this.inOrder(this.root);
        System.out.println("");
    }

    public void levelOrder(){
        if(this.isEmpty()) return;

        Queue<Node> fila = new LinkedList<Node>();
        fila.offer(this.root);
        String saida = "";
        while(!fila.isEmpty()){
            Node first = fila.poll();
            saida += first.value.toString() + " ";
            if(first.hasLeft()) fila.offer(first.left);
            if(first.hasRight()) fila.offer(first.right);
        }
        System.out.println(saida);
    }

    public void itePreOrder(){
        if(this.isEmpty()){
            return;
        }
        String saida = "";
        Stack<Node> pilha = new Stack<Node>();
        pilha.push(this.root);
		while(!pilha.isEmpty()){
			Node last = pilha.pop();
			saida += last.value.toString() + " ";
			if(last.hasRight()){
				pilha.push(last.right);
			}
			if(last.hasLeft()){
				pilha.push(last.left);
			}
        }
        System.out.println(saida);
    }

    public String toString(){
        if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.subTreeString("");
    }
}

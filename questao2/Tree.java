// ENUNCIADO:
// Adicione um método na árvore binária de busca que determine a quantidade de nós folha da árvore.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foi reutilizado o código da questão 01 para implementação da arvore binária de busca.
// Nesse código foram adicionados dois métodos, um método privado [Integer numberOfLeafs(Node no)]
// que calcula o número de nós folha de uma subarvore, de modo recursivo, ou seja, caso o nó
// passado como argumento não seja um nó folha ele chama o método em seus nós filhos.
// E o segundo método é um método público que retorna o número total de nós folha da árvore,
// para isso ele chama o método privado e passa o nó raiz como argumento.
// O método main foi alterado para testar essa funcionalidade.


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
        System.out.println("A quantidade de folhas dessa arvore eh: " + arv1.numberOfLeafs().toString());
        System.out.println("");
  
        Tree arv2 = new Tree();
        System.out.println("Imprimindo a arvore 2 que estah vazia");
        System.out.println(arv2);
        System.out.println("A quantidade de folhas dessa arvore eh: " + arv2.numberOfLeafs().toString());
        System.out.println("");

        Tree arv3 = new Tree();
        int[] vec3 = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for(int i : vec3){
            arv3.add(i);
        }
        System.out.println("Imprimindo a arvore 3 que foi preenchida com valores de modo a ter altura minima");
        System.out.println(arv3);
        System.out.println("A quantidade de folhas dessa arvore eh: " + arv3.numberOfLeafs().toString());
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

    private Integer numberOfLeafs(Node no){
        if(no == null) return 0;
        if(no.isLeaf()){
            return 1;
        } else {
            return numberOfLeafs(no.right) + numberOfLeafs(no.left);
        }
    }

    public Integer numberOfLeafs(){
        return numberOfLeafs(this.root);
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
        return root.toString();
    }
}

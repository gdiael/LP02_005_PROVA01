// ENUNCIADO:
// Implemente um método para verificar se uma árvore binária é uma árvore binária de busca.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foi reutilizado o código da questão 02 para implementação da arvore binária de busca.
// Nesse código foram adicionados dois métodos, um método privado [Boolean isBinarySearch(Node no)]
// que verifica se o nó está obedecendo a estrutura de arvore binária de busca, ou seja, se o valor
// em seu filho da esquerda é menor e se o valor em seu filho da direita é maior, este método é
// recursivo e é chamado em seus filhos, até que seja encontrado um nó folha.
// E o segundo método é um método público que retorna verdadeiro caso a arvore inteira atenda estes requisitos
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

        Tree arv3 = new Tree();
        int[] vec3 = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for(int i : vec3){
            arv3.add(i);
        }
        System.out.println("Imprimindo a arvore 3 que foi preenchida com valores de modo a ter altura minima");
        System.out.println(arv3);
        System.out.println("Arvore de busca? " + arv3.isBinarySearch().toString());
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

    private Boolean isBinarySearch(Node no){
        if(no == null) return true;
        if(no.isLeaf()) return true;
        if(no.hasRight()){
            if(no.value > no.right.value) return false;
        }
        if(no.hasLeft()){
            if(no.value < no.left.value) return false;
        }
        return isBinarySearch(no.right) && isBinarySearch(no.left);
    }

    public Boolean isBinarySearch(){
        return isBinarySearch(this.root);
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

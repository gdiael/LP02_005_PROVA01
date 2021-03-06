// Esta é a implementação que foi apresentada na questão 3
// aqui ela está sendo utilizado como auxilio para a resolução da questão 06
// a resolição está no arquivo nTree
// a única modificação no original foi a adição de um método para modificar o root, da árvore, já que é uma
// propriedade privada
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
            if(this.isLeaf()) return val;
            
            String eStr = (this.hasLeft() ? this.left.toString() : "*");
            String dStr = (this.hasRight() ? this.right.toString() : "*");
            return val + ">(" + eStr + " " + dStr + ")";
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

    public void setRoot(Node no){
        this.root = no;
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
        if(this.isEmpty()) return;

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

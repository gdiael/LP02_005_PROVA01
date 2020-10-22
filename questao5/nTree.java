// ENUNCIADO:
// Implemente uma estrutura de dados para representar uma ávore em que cada nó pode possuir até n filhos.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foi reutilizado o código da questão 02 para implementação da arvore binária de busca, porém
// a classe Node, que representa os nós da árvore, foi modificado removendo as propriedades dos nós
// a direita e a esquerda e criado um vetor de nós, representando os nós filhos, e uma propriedade chamada
// size para controlar a quantidade de nós filhos que foram inseridos.
// Todos os outros métodos da arvore foram adaptados para essa nova situação.
// A árvore possue vários métodos, sendo que o utilizado na método main para a demonstração da arvore é o de
// adicionar novos valores na árvore, este método percorre a árvore por nível, procurando um nó que tenha
// espaço para mais filhos, e adiciona um novo nó, com o valor a ser adicionado como filho do nó encontrado.


import java.util.Queue;
import java.util.LinkedList;

public class nTree {
    private Node root;
    private int nSize;

    public class Node{

        // valor contido no nó (atualmente inteiro)
        public Integer value;
        // referência ao nós filhos
        public Node[] childs;
        // quantidade de filhos
        public int size;
    
        // construtor
        public Node(Integer value, int nSize){
            this.value = value;
            this.childs = new Node[nSize];
            this.size = 0;
        }
        
        public int capacity(){
            return this.childs.length;
        }

        // verifica se tem filhos
        public Boolean hasChild(){
            return this.size > 0;
        }
       
        // verifica se o tem está cheio (tem filho a direita e a esquerda)
        public Boolean isFull(){
            return this.size == this.capacity();
        }
    
        // verifica se o nó está vazio (não tem filhos)
        public Boolean isLeaf(){
            return this.size == 0;
        }
    
        // imprime este nó e seus filhos recursivamente
        public String toString(){
            String val = this.value.toString();
            if(this.hasChild()){
                val += ">(";
                for(int i = 0; i < this.size; i++){
                    Node child = this.childs[i];
                    if(child != null){
                        val += child.toString() + ((i < this.size-1) ? " " : "");
                    }
                }
                val += ")";
            }
            return val;
        }
    }

    public nTree(int nSize){
        this.nSize = nSize;
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Iniciando instancias da classe Tree");
        System.out.println("");

        nTree arv1 = new nTree(4);
        int[] vec1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for(int i : vec1){
            arv1.add(i);
        }
        System.out.println("Imprimindo a arvore nAria que foi preenchida com valores de 1 a 15:");
        System.out.println(arv1);
        System.out.println("");
    }

    public Node getNode(int value){
        if(this.isEmpty()) return null;

        Queue<Node> fila = new LinkedList<Node>();
        fila.offer(this.root);
        while(!fila.isEmpty()){
            Node first = fila.poll();
            if(first.value.equals(value)) return first;
            for(int i = 0; i < first.size; i++){
                Node child = first.childs[i];
                fila.offer(child);
            }
        }
        return null;
    }

    public Boolean contains(int value){
        Node no = this.getNode(value);
        return no != null;
    }

    public boolean add(int value){
        if(this.root == null){
            this.root = new Node(value, this.nSize);
            return true;
        } else {
            Queue<Node> fila = new LinkedList<Node>();
            fila.offer(this.root);
            while(!fila.isEmpty()){
                Node first = fila.poll();
                if(!first.isFull()){
                    first.childs[first.size] = new Node(value, this.nSize);
                    first.size += 1;
                    return true;
                }
                for(int i = 0; i < first.size; i++){
                    Node child = first.childs[i];
                    fila.offer(child);
                }
            }
        }
        return false;
    }

    private Integer height(Node no){
		if(no == null){
			return 0;
		} else {
            int nHeight = 1;
            for(int i = 0; i < no.size; i++){
                nHeight = Math.max(nHeight, this.height(no.childs[i]));
            }
            return nHeight;
		}
	}
	
	public Integer height(){
		return this.height(root);
	}

    private Integer size(Node no){
		if(no == null){
			return 0;
		} else {
            int nSize = 1;
            for(int i = 0; i < no.size; i++){
                nSize += this.size(no.childs[i]);
            }
            return nSize;
		}
    }

    public Integer size(){
        return this.size(this.root);
    }

    private Integer numberOfLeafs(Node no){
        if(no == null) return 0;
        if(no.isLeaf()){
            return 1;
        } else {
            int nLeaf = 0;
            for(int i = 0; i < no.size; i++){
                nLeaf += numberOfLeafs(no.childs[i]);
            }
            return nLeaf;
        }
    }

    public Integer numberOfLeafs(){
        return numberOfLeafs(this.root);
    }

    public Boolean isEmpty(){
        return this.root == null;
    }

    private void preOrder(Node no){
		if(no != null){
            String dVal = no.value.toString() + " ";
            System.out.print(dVal);
            for(int i = 0; i < no.size; i++){
                preOrder(no.childs[i]);
            }
		}
    }

    private void posOrder(Node no){
        if(no != null){
            String dVal = no.value.toString() + " ";
            for(int i = 0; i < no.size; i++){
                posOrder(no.childs[i]);
            }
            System.out.print(dVal);
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

    public void levelOrder(){
        if(this.isEmpty()) return;

        Queue<Node> fila = new LinkedList<Node>();
        fila.offer(this.root);
        String saida = "";
        while(!fila.isEmpty()){
            Node first = fila.poll();
            saida += first.value.toString() + " ";
            for(int i = 0; i < first.size; i++){
                fila.offer(first.childs[i]);
            }
        }
        System.out.println(saida);
    }

    public String toString(){
        if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.toString();
    }
}

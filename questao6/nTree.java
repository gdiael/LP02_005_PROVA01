// ENUNCIADO:
// Implemente um método para árvore n-ária que retorna a árvore convertida para uma árvore binária. Apresente
// um exemplo de com ao menos 10 elementos.
//
// ESTRATÉGIA PARA RESOLUÇÃO
// Foi reutilizado o código da questão 03 para implementação da arvore binária de busca, para ser usado como
// resultado da conversão, apenas foi adicionado um método público para mudar a raiz chamado setRoot, para
// termos acesso a estrutura da arvore sem mudar o tipo de acesso das suas propriedades.
// Para a arvore n-ária, foi reutilizado o código da questão 05, sendo que foram adicionados dois métodos novos:
// - Um método privado chamado [toBinaryTree(Node no, Tree newTree)]
//   Neste método é fornecido um nó da árvore n-ária e a referência para a nova árvore binária, e então cria um
//   novo nó binário baseado no valor do nó n-ário e retorna esse nó. Se o nó n-ário tiver filhos, esses filhos
//   serão percorridos pelo método de forma recursiva e adicionados como filhos do nó binário da seguinte forma
//   o primeiro filho será inserido como nó a esquerda, e o segundo como filho a direita, e terceito como filho
//   a direita do segundo, o quarto como filho a direta do terceiro e assim por diante.
// - Um método público de mesmo nome, porém sem argumentos e que retorna uma referência para a arvore binária.
//   Esse método instância uma nova referência a uma arvore binária (que será retornada) e aplica o método privado
//   de mesmo nome no nó da arvore n-ária, aplicando o valor retornado por ele no nó raiz da nova arvore binária.
// 
// a impressão da arvore segue o esquema [exemplo com trêS filhos]
//  valor ┬─> [filho 2]
//        ├─> [filho 1]
//        └─> [filho 0]


import java.util.Queue;
import java.util.LinkedList;

public class nTree {
    private Node root;
    private int nSize;

    public class Node{

        // valor contido no nó (atualmente inteiro)
        public Integer value;
        // referência ao nós filhos
        public Node[] children;
        // quantidade de filhos
        public int size;
    
        // construtor
        public Node(Integer value, int nSize){
            this.value = value;
            this.children = new Node[nSize];
            this.size = 0;
        }
        
        public int capacity(){
            return this.children.length;
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
                    Node child = this.children[i];
                    if(child != null){
                        val += child.toString() + ((i < this.size-1) ? " " : "");
                    }
                }
                val += ")";
            }
            return val;
        }

        public String subTreeString(String prefix){
            String val = this.value.toString();
            if(this.isLeaf()) return val + "\n";
            String valSpace = "";
            for(int i = 0; i < val.length(); i++){
                valSpace += " ";
            }
            String arrowIni = (this.size == 1) ? " \u2500\u2500> " : " \u252C\u2500> ";
            String arrowMid = " \u251C\u2500> ";
            String arrowEnd = " \u2514\u2500> ";
            for(int i = this.size-1; i > -1; i--){
                Node child = this.children[i];
                String arrow = (i == this.size-1) ? arrowIni : ((i == 0) ? arrowEnd : arrowMid);
                String afast = (i == 0) ? "     " : " \u2502   ";
                val += ((i == this.size-1) ? "" : prefix + valSpace) + arrow + child.subTreeString(prefix + valSpace + afast);
            }
            return val;
        }

    }

    public nTree(int nSize){
        this.nSize = nSize;
    }

    public static void main(String[] args) {
        System.out.println("");
        System.out.println("Iniciando instancias da classe Tree e nTree");
        System.out.println("");

        nTree arv1 = new nTree(4);
        int[] vec1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        for(int i : vec1){
            arv1.add(i);
        }
        System.out.println("Imprimindo a arvore nAria que foi preenchida com valores de 1 a 14:");
        System.out.println(arv1);
        Tree arv = arv1.toBinaryTree();
        System.out.println("Imprimindo a arvore binaria criada pela arvore nAria:");
        System.out.println(arv);
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
                Node child = first.children[i];
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
                    first.children[first.size] = new Node(value, this.nSize);
                    first.size += 1;
                    return true;
                }
                for(int i = 0; i < first.size; i++){
                    Node child = first.children[i];
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
                nHeight = Math.max(nHeight, this.height(no.children[i]));
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
                nSize += this.size(no.children[i]);
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
                nLeaf += numberOfLeafs(no.children[i]);
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
                preOrder(no.children[i]);
            }
		}
    }

    private void posOrder(Node no){
        if(no != null){
            String dVal = no.value.toString() + " ";
            for(int i = 0; i < no.size; i++){
                posOrder(no.children[i]);
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
                fila.offer(first.children[i]);
            }
        }
        System.out.println(saida);
    }

    private Tree.Node toBinaryTree(Node no, Tree newTree){
        if(no == null) return null;
        Tree.Node newNo = newTree.new Node(no.value);
        if(no.hasChild()){
            Tree.Node lastNo = null;
            for(int i = 0; i < no.size; i++){
                Node child = no.children[i];
                if(lastNo == null){
                    newNo.left = toBinaryTree(child, newTree);
                    lastNo = newNo.left;
                } else {
                    lastNo.right = toBinaryTree(child, newTree);
                    lastNo = lastNo.right;
                }
            }
        }
        return newNo;
    }

    public Tree toBinaryTree(){
        Tree newTree = new Tree();
        newTree.setRoot(this.toBinaryTree(this.root, newTree));
        return newTree;
    }

    public String toString(){
        if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.subTreeString("");
    }
}

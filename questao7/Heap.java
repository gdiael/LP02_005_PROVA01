// ENUNCIADO
// Implemente uma lista de prioridade (heap) utilizando uma implementação recursiva baseada em árvore binária.
// A estrutura de dados deve apresentar as operações de inserção, remoção, busca e alteração de prioridade.
//
// RESOLUÇÃO
// Para adicionar novos elementos ele é percorrido por nível
// e o main foi inserido na propria classe Heap.


import java.util.Queue;
import java.util.LinkedList;

public class Heap {	
	private Node root;

	public class Node {
		private Integer _value;
		public Node parent;
		public Node right;
		public Node left;

		public Node(Integer value){
			this._value = value;
		}

		public Integer getPriority(){
			return this._value;
		}

		public Integer getValue(){
			return this._value;
		}

		public void priorityUp(){
			this._value += 1;
		}

		public void priorityDown(){
			this._value -= (this._value == 0) ? 0 : 1;
		}

		public void setPriority(Integer value){
			this._value = value;
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
	
		// verifica se o nó está vazio (não tem filhos)
		public Boolean isLeaf(){
			return !this.hasRight() && !this.hasLeft();
		}

		// Retorna -1 0 1
		// -1 caso a prioridade de this seja menor que a de other
		// 0 caso a prioridade de this seja igual a de other
		// 1 caso a prioridade de this seja maior que a de other
		public int compare(Node other){
			if(this.getPriority() > other.getPriority()) return 1;
			if(this.getPriority() < other.getPriority()) return -1;
			return 0;
		}

		public String subTreeString(String prefix){
            String val = this._value.toString();
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

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("Questao 07 - Prova 01");
		Heap pList = new Heap();
		int[] vec = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		for(int i : vec){
			pList.add(i);
		}
		System.out.println("Imprimindo Heap com valores de 1 a 12");
		System.out.println(pList);
		System.out.println("O maior valor do Heap eh: " + pList.remove().toString());
		System.out.println("Imprimindo Heap atualizado");
		System.out.println(pList);
		
	}


	public Heap(){
	}

	// Verifica se o vetor base está vazio
	private Boolean isEmpty(){
		return this.root == null;
	}

	// Verifica se o vetor base está vazio e joga uma mensagem de erro
	private Boolean isEmptyWithWarning(){
		if(this.isEmpty()){
			throw new IndexOutOfBoundsException("Esta lista está vazia!");
		}
		return false;
	}

	// verifica se o elemento e seu pai atendem a condição de heap, se não, sobe este elemento
	private void subir(Node no){
		if(this.isEmptyWithWarning()) return;
		if(no == null) return;
		Node parent = no.parent;
		if(parent == null) return; // o inice informado representa o primeiro elemento, por isso não tem pai
		if(no.compare(parent) == 1){ // se o elemento atual for maior que sei pai
			this.trocar(no, parent); // trocamos os dois de posição
			this.subir(parent); // fazemos a verifcação na nova posição
		}
	}
	
	// verifica se o elemento e seus filhos atendem a condição de heap, se não, desce este elemento
	private void descer(Node no){
		if(this.isEmptyWithWarning()) return;
		if(no == null) return;
		if(!no.hasLeft()) return; // se o indice em son está fora do vetor, nada a fazer
		Node child = no.left; // sera o indice do primeiro filho
		
		if(no.hasRight()){// se existe um proximo elemento
			if(no.right.compare(child) == 1){ // verificamos se ele é maior que son
				child = no.right; // se for incrementamos son para o proximo indice
			}
		}
		if(child.compare(no) == 1){ // se o elemento em son é maior que o atual
			this.trocar(no, child); // trocamos de lugar
			descer(child); // procedemos com a verificação na nova posição
		}
	}

	// troca dois elementos de posição
	private void trocar(Node a, Node b){
		if(this.isEmptyWithWarning()) return;

		Integer aux = a.getPriority();
		a.setPriority(b.getPriority());
		b.setPriority(aux);
	}

	public boolean add(Integer value) {
        if(this.root == null){
            this.root = new Node(value);
            return true;
        } else {
            Queue<Node> fila = new LinkedList<Node>();
            fila.offer(this.root);
            while(!fila.isEmpty()){
                Node first = fila.poll();
                if(!first.isFull()){
					if(!first.hasLeft()){
						first.left = new Node(value);
						first.left.parent = first;
						this.subir(first.left);
						return true;
					} else {
						first.right = new Node(value);
						first.right.parent = first;
						this.subir(first.right);
						return true;
					}
                }
                fila.offer(first.left);
				fila.offer(first.right);
            }
        }
        return false;
	}

	private Node minor(Node no){
		if(no == null) return null;
		if(no.isLeaf()) return no;
		if(!no.isFull()) return no.left;
		if(no.left.compare(no.right) == 1){
			return minor(no.right);
		} else {
			return minor(no.left);
		}
	}
	
	private Node last(){
		Queue<Node> fila = new LinkedList<Node>();
		fila.offer(this.root);
		Node first = null;
		while(!fila.isEmpty()){
			first = fila.poll();
			if(first.hasLeft()) fila.offer(first.left);
			if(first.hasRight()) fila.offer(first.right);
		}
		return first;
	}

	// retorna o maior elemento [primeiro], remove-o e reorganiza o vetor para continuar nas condições de heap
	public Integer remove() {
		if(this.isEmptyWithWarning()) return null;

		Integer valor = this.root.getPriority();
		Node last = this.last();
		this.trocar(last, this.root);
		if(last == this.root){
			this.root = null;
		} else {
			if(last.parent.left == last){
				last.parent.left = null;
			} else {
				last.parent.right = null;
			}
			this.descer(this.root);
		}
		return valor;
	}
	
	// retorna o maior elemento [primeiro]
	public Integer get() {
		if(this.isEmptyWithWarning()) return null;

		return this.root.getPriority();
	}
	
	// Converte os valores da lista para string
	public String toString(){
		if(this.isEmpty()) return "Esta arvore eh vazia!";
        return root.subTreeString("");
	}
}
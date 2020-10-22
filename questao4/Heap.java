// ENUNCIADO
// Implemente um algoritmo para fazer um percuso numa árvore de diretórios retornando os cinco dirétórios
// com maior quantidade de arquivos.
//
// RESOLUÇÃO
// Para o Heap foi reutilizado o código da ultima questão da Lista 04, foi removida a herança
// e o main foi inserido na propria classe Heap. Ao invés de ser um Heap de inteiros, foi implementada
// uma classe chamada Element que possue duas propriedades:
//     name - para representar o nome do Element, que no caso será o nome da pasta
//     priority - para representar a prioridade do Element dentro do Heap, que nesse caso será o número de
//                arquivos na pasta
// O Element também implementa um método chamado compare, que é responsável pela comparação de prioridades,
// tirando essa responsabilidade da classe Heap, além disso foram implementados mais dois métodos, para
// incrementar e reduzir a prioridade do Element.
// Na classe Heap o vetor de int foi substituido por um vetor de Element.
// o método público main, chama o método privado walkInSubPaths(String caminho, Heap prioriList) que percorre
// a arvore de arquivos e diretórios, guardando os diretórios na heap, com a prioridade sendo representada
// pelo número de arquivos.


import java.io.File;

public class Heap {	

	public class Element {
		private Integer _priority;
		private String _name;

		public Element(String name){
			this._name = name;
			this._priority = 0;
		}

		public Element(String name, Integer priority){
			this._name = name;
			this._priority = priority;
		}

		public Integer getPriority(){
			return this._priority;
		}

		public String getName(){
			return this._name;
		}

		public void priorityUp(){
			this._priority += 1;
		}

		public void priorityDown(){
			this._priority -= (this._priority == 0) ? 0 : 1;
		}

		// Retorna -1 0 1
		// -1 caso a prioridade de this seja menor que a de other
		// 0 caso a prioridade de this seja igual a de other
		// 1 caso a prioridade de this seja maior que a de other
		public int compare(Element other){
			if(this._priority > other.getPriority()) return 1;
			if(this._priority < other.getPriority()) return -1;
			return 0;
		}

	}

	public static void main(String[] args) {
		System.out.println("");
		System.out.println("Questao 04 - Prova 01");
		Heap prioriList = new Heap(100);
		walkInSubPaths(System.getenv("USERPROFILE") + "\\Downloads", prioriList);
		for(int i = 0; i < Math.min(5, prioriList.size) ; i++){
			Element top = prioriList.remove();
			System.out.println("A pasta [" + top.getName() + "] tem " + top.getPriority().toString() + " arquivos.");
		}
        System.out.println("");
	}

	private static void walkInSubPaths(String caminho, Heap prioriList){
		File dir = new File(caminho);
		if(!dir.exists() || dir.isFile()) return;
		File[] filhos = dir.listFiles();
		Element dirElement = prioriList.new Element(dir.getName()); // crie o Element que vai representar a pasta
		for(File filho : filhos){ // percorra todos os arquivos e pastas dentro de [dir]
			if(filho.isFile()){
				dirElement.priorityUp(); // se filho é um arquivo, aumente em 1 a prioridade da pasta
			} else {
				// se não é arquivo, não é contado na prioridade e percorremos suas subpastas
				walkInSubPaths(filho.getAbsolutePath(), prioriList);
			}
		}
		// depois de percorrer todos os arquivos, o Element já está com a prioridade correta e pode ser adicionado ao Heap
		prioriList.add(dirElement); 
	}

	public static void printPath(String caminho, String prefix){
        File dir = new File(caminho);
		if(dir.exists()){
            System.out.println(prefix + dir.getName());
            if(dir.isDirectory()){
                String[] filhos = dir.list();
                for(String nome : filhos){
                    printPath(caminho + "\\" + nome, prefix + "-> ");
                }
            }
		}else{
            System.out.println("Arquivo não encontrado");
        }
    }

	private Element[] elements;
	private int size;

	public Heap(){
		this.size = 0;
		this.elements = new Element[100];
	}

	public Heap(int capacity){
		this.size = 0;
		this.elements = new Element[capacity];
	}

	public Heap(Element[] vector){
		if(vector.length > 0){
			this.size = vector.length;
			this.elements = vector;
			for(int i = this.size/2-1; i>=0; i--){
				descer(i);
			}
		}else{
			this.size = 0;
			this.elements = new Element[100];
		}
	}
	
	public int capacity(){
		return this.elements.length;
	}

	// Verifica se o vetor base está vazio
	private Boolean isEmpty(){
		return this.size < 1;
	}

	// Verifica se o vetor base está vazio e joga uma mensagem de erro
	private Boolean isEmptyWithWarning(){
		if(this.isEmpty()){
			throw new IndexOutOfBoundsException("Esta lista está vazia!");
		}
		return false;
	}

	// verifica se o elemento e seu pai atendem a condição de heap, se não, sobe este elemento
	private void subir(int i){
		if(this.isEmptyWithWarning()) return;

		int parent = (i+1)/2-1;
		if(parent < 0) return; // o inice informado representa o primeiro elemento, por isso não tem pai
		if(parent >= this.size) return; // caso o indice informado esteja muito fora, quando dividirmos por dois ainda estará fora
		if(this.elements[i].compare(this.elements[parent]) == 1){ // se o elemento atual for maior que sei pai
			this.trocar(i, parent); // trocamos os dois de posição
			this.subir(parent); // fazemos a verifcação na nova posição
		}
	}
	
	// verifica se o elemento e seus filhos atendem a condição de heap, se não, desce este elemento
	private void descer(int i){
		if(this.isEmptyWithWarning()) return;

		int son = (i+1)*2-1; // sera o indice do primeiro filho
		if(son >= this.size) return; // se o indice em son está fora do vetor, nada a fazer
		if(son < this.size-1){ // se existe um proximo elemento
			if(this.elements[son+1].compare(this.elements[son]) == 1){ // verificamos se ele é maior que son
				son++; // se for incrementamos son para o proximo indice
			}
		}
		if(this.elements[son].compare(this.elements[i]) == 1){ // se o elemento em son é maior que o atual
			this.trocar(i, son); // trocamos de lugar
			descer(son); // procedemos com a verificação na nova posição
		}
	}

	// troca dois elementos de posição
	private void trocar(int a, int b){
		if(this.isEmptyWithWarning()) return;

		Element aux = this.elements[a];
		this.elements[a] = this.elements[b];
		this.elements[b] = aux;
	}

	private void resize(){
		Element[] newVector = new Element[this.size*2];
		for(int i=0; i < this.size; i++){
			newVector[i] = this.elements[i];
		}
		this.elements = newVector;
	}

	public boolean add(Element value) {
		if(this.size == this.capacity()){
			this.resize();
		}
		this.elements[this.size] = value;
		this.size += 1;
		subir(this.size-1);
		return true;
	}
	
	// retorna o maior elemento [primeiro], remove-o e reorganiza o vetor para continuar nas condições de heap
	public Element remove() {
		if(this.isEmptyWithWarning()) return null;

		Element valor = this.elements[0];
		this.size -= 1;
		if(this.size != 0){
			this.trocar(0, this.size);
			this.descer(0);
		}
		return valor;
	}
	
	// retorna o maior elemento [primeiro]
	public Element get() {
		if(this.isEmptyWithWarning()) return null;

		return this.elements[0];
	}
	
	// Converte os valores da lista para string
	public String toString(){
		String saida = "";
		for(int i=0; i<this.size; i++){
			saida += this.elements[i] + " ";
		}
		return saida;
	}
}
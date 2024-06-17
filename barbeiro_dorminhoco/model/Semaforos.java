/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: Semaforos
* Funcao...........: classe que possui todos os semaforos 
*   da aplicacao, possibilitanto um melhor gerenciamento 
*   do problema do barbeiro dorminhoco
*************************************************************** */

package model;

import java.util.concurrent.Semaphore;

public class Semaforos {
  // numero de cadeiras disponiveis para espera
  private static final int CADEIRAS = 5;

  // semaforo para controlar numero de clientes presentes na fila de espera da barbearia
  private static Semaphore clientes = new Semaphore(0);

  // semaforo para controlar numero de barbeiros disponiveis para o corte
  private static Semaphore barbeiros = new Semaphore(0);

  // semaforo que garante a exclusao mutua na regiao critica relativa ao numero de pessoas
  // esperando para receber o corte do barbeiro
  private static Semaphore mutex = new Semaphore(1);

  // variavel que vai possuir a quantidade de pessoas esperando na fila da barbearia
  private static int esperando = 0;

  // flag que define se o barbeiro terminou a execucao do corte
  private static boolean barbeiroTerminou = false;

  // vetor que vai guardar todos os processos que estao na fila de espera da barbearia
  public static Cliente[] clientesFila = new Cliente[5];

  private Semaforos(){}

  // metodos get e set
  public static int getCadeiras() {
    return CADEIRAS;
  }
    
  public static Semaphore getClientes() {
    return clientes;
  }

  public static void setClientes(Semaphore numero_clientes) {
    Semaforos.clientes = numero_clientes;
  }

  public static Semaphore getBarbeiros() {
    return barbeiros;
  }
  
  public static void setBarbeiro(Semaphore barbeiro_disponivel) {
    Semaforos.barbeiros = barbeiro_disponivel;
  }

  public static Semaphore getMutex() {
    return mutex;
  }
  
  public static void setMutex(Semaphore mutex) {
    Semaforos.mutex = mutex;
  }

  public static int getEsperando() {
    return esperando;
  }

  public static void setEsperando(int pessoas_esperando) {
    Semaforos.esperando = pessoas_esperando;
  }

  public static void setBarbeiroTerminou(boolean barbeiroTerminouCorte) {
    barbeiroTerminou = barbeiroTerminouCorte;
  }

  public static boolean getBarbeiroTerminou() {
    return barbeiroTerminou;
  }

  /* ***************************************************************
  * Metodo: incrementarEsperando
  * Funcao: incrementa em 1 o valor de pessoas esperando na fila
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public static void incrementarEsperando(){
    esperando = esperando + 1;
  }

  /* ***************************************************************
  * Metodo: incrementarEsperando
  * Funcao: decrementa em 1 o valor de pessoas esperando na fila
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public static void decrementarEsperando(){
    esperando = esperando - 1;
  }

  /* ***************************************************************
  * Metodo: reiniciarFila
  * Funcao: torna todos os valores do vetor de clientes na fila 
  *   como nulos
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public static void reiniciarFila(){
    for(int i = 0; i < 5; i++){
      clientesFila[i] = null;
    }
  }

  /* ***************************************************************
  * Metodo: reiniciarSemaforos
  * Funcao: reinicia para a configuracao original os semaforos da
  *   aplicacao
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public static void reiniciarSemaforos(){
    clientes = new Semaphore(0);
    barbeiros = new Semaphore(0);
    mutex = new Semaphore(1);
    esperando = 0;
    barbeiroTerminou = false;
  }
}

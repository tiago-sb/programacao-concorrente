/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: Barbeiro
* Funcao...........: classe que gerencia o funcionamento 
*   do processo do barbeiro durante a execucao do programa
*************************************************************** */

package model;

import java.lang.Thread;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Barbeiro extends Thread {
  // variaveis relativas as caracteristicas e atributos do barbeiro
  // referencia da imagem do barbeiro
  private ImageView barbeiro;

  // booleano que verifica se o barbeiro esta trabalhando
  private static boolean cortando;

  // controle do slider do barbeiro
  private volatile long controleBarbeiro;
  
  // booleano que verifica se a thread do barbeiro esta em funcionamento
  private volatile boolean barbeiroVivo = true;
  
  // booleano que verifica se a thread do barbeiro esta suspensa
  private volatile boolean suspenso = false;

  // define o tempo geral do corte do barbeiro
  private volatile long tempoCorte = 5000;

  public Barbeiro(ImageView barbeiro){
    this.barbeiro = barbeiro;
  }

  // metodos get e set
  public boolean getSuspenso(){
    return suspenso;
  }
  
  public static boolean getCortando(){
    return cortando;
  }

  public static void setCortando(boolean cortandoCabelo){
    cortando = cortandoCabelo;
  }

  public void setVelocidadeTrabalho(long velocidadeTrabalho) {
    this.controleBarbeiro = velocidadeTrabalho;
  }

  /* ***************************************************************
  * Metodo: suspenderBarbeiro
  * Funcao: setar o booleano suspenso como verdadeiro, tendo como 
  *   consequencia a suspensao do processo do barbeiro
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void suspenderBarbeiro() {
    suspenso = true;
  }

  /* ***************************************************************
  * Metodo: voltarBarbeiro
  * Funcao: faz o barbeiro retornar ao funcionamento natural dele
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void voltarBarbeiro() {
    suspenso = false;
    synchronized(this) {
      this.notify();
    }
  }

  /* ***************************************************************
  * Metodo: controlarPausa
  * Funcao: verifica se o processo do barbeiro esta suspenso, caso
  *   sim, ela fica parado
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  private void controlarPausa() throws InterruptedException {
    synchronized (this) {
      while(this.suspenso) {
        try {
          this.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt(); 
        }
      }
    }
  }

  /* ***************************************************************
  * Metodo: dormirBarbeiro
  * Funcao: atualiza a imagem do barbeiro para dormindo
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  private void dormirBarbeiro() {
    Platform.runLater(() -> {barbeiro.setImage(new Image("img/barbeiro_dormindo.png"));});
  }

  /* ***************************************************************
  * Metodo: acordarBarbeiro
  * Funcao: atualiza a imagem do barbeiro para acordado
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  private void acordarBarbeiro() {
    Platform.runLater(() -> barbeiro.setImage(new Image("img/barbeiro_acordado.png")));
  }
  
  /* ***************************************************************
  * Metodo: cortaCabelo
  * Funcao: faz o processo de corte do cabelo, trava o cliente aqui
  *   e o libera quando o corte esta finalizado
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void cortaCabelo() throws InterruptedException{
    // seta a variavel de corte terminado com falsa, impedindo o cliente de sair da cadeira do barbeiro
    Semaforos.setBarbeiroTerminou(false);
    
    // execucao do corte controlada com o valor do slider
    while(this.tempoCorte > 0) { 
      sleep(1);
      this.tempoCorte = this.tempoCorte - this.controleBarbeiro;
    }

    // reinicializacao do tempo de corte
    this.tempoCorte = 5000;

    // seta a variavel de corte terminado com verdadeira, liberando o cliente a sair da cadeira do barbeiro
    Semaforos.setBarbeiroTerminou(true); 
  }

  public void run(){
    // repete o processo enquando o barbeiro nao esta pausado
    while(this.barbeiroVivo){
      try {
        // controla a pausa do barbeiro, caso ele esteja pausado, fica suspenso aqui
        this.controlarPausa();
        
        // coloca o barbeiro para dormir
        this.dormirBarbeiro();

        // operacao down no semaforo de clientes para verificar se existe cliente na fila de espera
        // se nao existe o barbeiro dorme
        Semaforos.getClientes().acquire();

        // garantindo a exclusao mutua no trecho que modifica a quantidade de pessoas que estao esperando
        Semaforos.getMutex().acquire();

        // diminue o numero de pessoas na fila de espera do barbeiro
        Semaforos.decrementarEsperando();
        this.acordarBarbeiro();

        // aumenta o numero de barbeiros disponiveis para efetuar o corte das pessoas
        Semaforos.getBarbeiros().release();

        // libera a regiao critica para outro processo que queira utilizar o recurso compartilhado
        Semaforos.getMutex().release();

        // controla a pausa do barbeiro, caso ele esteja pausado, fica suspenso aqui
        this.controlarPausa();
        // faz a execucao do corte do cabelo do cliente
        this.cortaCabelo();
        sleep(600);
      } catch (InterruptedException e) {}
    }
  }
}
/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: Produtor
* Funcao...........: classe que vai gerar os clientes
*************************************************************** */

package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Produtor extends Thread {
  // controla o valor contido no slider do cliente 
  private volatile long controleCliente;
  
  // referencia do anchorPane da aplicacao inteira
  private AnchorPane anchorPane;
  
  // referencia da imagem do barbeiro
  private ImageView barbeiro;
  
  // referencia da imagem da cadeira
  private ImageView cadeira;

  // tempo de duracao para a producao de um cliente
  private volatile long tempoProducao = 6000;
  
  // flag para controlar a geracao de clientes
  private volatile boolean gerando = true;

  // flag para efetuar a verificacao do suspendimento da thread de producao de clientes
  private volatile boolean suspensa = false;

  // vetor que vai guardar os clientes que foram gerados 
  private ArrayList<Cliente> clientesGerados = new ArrayList<>();

  public Produtor(AnchorPane anchorPane, ImageView barbeiro, ImageView cadeira){
    this.anchorPane = anchorPane;
    this.barbeiro = barbeiro;
    this.cadeira = cadeira;
  }

  /* ***************************************************************
  * Metodo: controlarPausa
  * Funcao: verifica se o processo do produtor esta suspenso, caso
  *   sim, ela fica parado
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  private void controlarPausa() throws InterruptedException {
    synchronized (this) {
      while(this.suspensa) {
        try {
          this.wait();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt(); 
        }
      }
    }
  }
  
  // metodos get e set
  public boolean getSuspensa(){
    return suspensa;
  }

  public void setVelocidadeProducao(long velocidadeTrabalho) {
    this.controleCliente = velocidadeTrabalho;
  }

  /* ***************************************************************
  * Metodo: suspenderProdutor
  * Funcao: setar o booleano suspenso como verdadeiro, tendo como 
  *   consequencia a suspensao do processo do produtor
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void suspenderProdutor() {
    suspensa = true;
  }

  /* ***************************************************************
  * Metodo: voltarProdutor
  * Funcao: faz o produtor retornar ao funcionamento natural dele
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void voltarProdutor() {
    suspensa = false;
    synchronized(this) {
        this.notify();
    }
  }

  /* ***************************************************************
  * Metodo: resetarClientesGerados
  * Funcao: suspende todos os clientes gerados e limpa a lista de
  *   clientes
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void resetarClientesGerados() throws InterruptedException {
    sleep(2500);
    // percorre a lista de clientes interrompendo eles
    for(Cliente cliente : clientesGerados) {
      cliente.suspenderCliente();
      Platform.runLater(() -> cliente.getImagemCliente().setVisible(false));
    }
    clientesGerados.clear();
  }

  public void run(){
    while(this.gerando){
      try {
        // controla a pausa do produtor, caso ele esteja pausado, fica suspenso aqui
        this.controlarPausa();
      } catch (InterruptedException e) {}

      // configurando a imagem do novo cliente 
      int posicaoBarbeiro = anchorPane.getChildren().indexOf(barbeiro);
      ImageView cliente = new ImageView();
      cliente.setFitHeight(219);
      cliente.setFitWidth(257);
      cliente.setLayoutX(-165);
      cliente.setLayoutY(162);
      cliente.setImage(new Image("img/cliente_entrando.png"));
      cliente.setScaleX(-1);

      // adiciona o cliente na arvore do anchorPane
      Platform.runLater(() -> anchorPane.getChildren().add(posicaoBarbeiro - 1, cliente));

      // instancia o novo cliente
      Cliente novoCliente = new Cliente(cliente, anchorPane, cadeira);
      // coloca o novo cliente em estado de executando
      novoCliente.start();

      // adiciona o novo cliente na lista de clientes
      clientesGerados.add(novoCliente);
      
      // controle do tempo para a geracao de novos clientes com o valor do slider
      while (tempoProducao > 0) { 
        try {
          sleep(2);
          if(this.controleCliente >= 8){
            this.controleCliente = this.controleCliente - 2;
          }
          this.tempoProducao = this.tempoProducao - this.controleCliente;
        } catch (InterruptedException e) {}
      }
      // reinicializacao do tempo de corte
      tempoProducao = 6000;
    }
  }
} 
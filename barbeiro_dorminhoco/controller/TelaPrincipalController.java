/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: tela principal controller
* Funcao...........: Controla os elementos gráficos do JavaFX da 
*   tela principal
*************************************************************** */

package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Cursor;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.ResourceBundle;

import model.Barbeiro;
import model.Produtor;
import model.Semaforos;

public class TelaPrincipalController implements Initializable {
    
  // identificadores dos componentes do arquivo fxml
  @FXML
  private AnchorPane anchorPane;
  
  @FXML
  private ImageView botaoReset;
  
  @FXML
  private ImageView barbeiro;
  
  @FXML
  private ImageView cadeira;

  @FXML
  private ImageView clienteSaindo;

  @FXML
  private Slider sliderBarbeiro;

  @FXML
  private Slider sliderCliente;

  @FXML
  private ImageView clienteCadeiraBarbeiro;
  
  @FXML
  private ImageView botaoPausePlayCliente;

  @FXML
  private ImageView botaoPausePlayBarbeiro;

  // processo do barbeiro que vai estar em execucao durante o funcionamento do programa
  private Barbeiro barbeiroThread;

  // processo que vai desempenhar o papel de produzir novos clientes durante a execucao do programa
  private Produtor novosClientesThread;

  /* ***************************************************************
  * Metodo: mouseEntrouReset
  * Funcao: modificar o estilo do mouse quando ele entra no reset
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   passa no botao reset
  * Retorno: void
  *************************************************************** */
  @FXML
  public void mouseEntrouReset(MouseEvent event) {
    botaoReset.setCursor(Cursor.HAND);
  }
    
  /* ***************************************************************
  * Metodo: botaoResetClicado
  * Funcao: chamar o metodo setTelaEscolha que basicamente abre a 
  *   janela de escolha para o usuario escolher as posicoes iniciais
  *   dos trens
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao reset
  * Retorno: void
  *************************************************************** */
  @FXML
  public void botaoResetClicado(MouseEvent event) throws InterruptedException {
    /*
    novosClientesThread.resetarClientesGerados();
    
    barbeiroThread.suspenderBarbeiro();
    barbeiroThread.voltarBarbeiro();
    
    novosClientesThread.suspenderProdutor();
    novosClientesThread.voltarProdutor();
    
    // reiniciando as variaveis da classe do Semaforo para o valor inicial
    Semaforos.reiniciarSemaforos();

    // chama o metodo de inicializacao das threads
    this.setVariaveisThread();
    */

    // resetando os clientes que foram gerados
    novosClientesThread.resetarClientesGerados();
    
    this.sliderBarbeiro.setValue(2);
    this.sliderCliente.setValue(4);

    // interrompendo as threads
    novosClientesThread.interrupt();
    barbeiroThread.interrupt();
    
    novosClientesThread.suspenderProdutor();
    barbeiroThread.suspenderBarbeiro();

    this.botaoPausePlayBarbeiroClicado(event);
    this.botaoPausePlayClienteClicado(event);
    
    barbeiroThread.voltarBarbeiro();
    novosClientesThread.voltarProdutor();
    
    Semaforos.reiniciarFila(); 
    Semaforos.reiniciarSemaforos();
  }

  /* ***************************************************************
  * Metodo: mouseEntrouPausePlayBarbeiro
  * Funcao: modificar o estilo do mouse quando ele entra no botao
  *   de pause/play do barbeiro
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao pause/play
  * Retorno: void
  *************************************************************** */
  @FXML
  void mouseEntrouPausePlayBarbeiro(MouseEvent event) {
    botaoPausePlayBarbeiro.setCursor(Cursor.HAND);
  }

  /* ***************************************************************
  * Metodo: botaoPausePlayBarbeiroClicado
  * Funcao: modifica a imagem do botao pause/play do barbeiro
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao pause/play
  * Retorno: void
  *************************************************************** */
  @FXML
  void botaoPausePlayBarbeiroClicado(MouseEvent event) {
    // caso a thread do barbeiro nao esta suspensa trocamos a imagem de pause para play e suspendemos a thread
    // caso ela ja esteja suspensa trocamos a imagem de play para pause e reiniciamos a thread
    if(!barbeiroThread.getSuspenso()){
      Platform.runLater(() -> botaoPausePlayBarbeiro.setImage(new Image("img/play.png")));
      barbeiroThread.suspenderBarbeiro();
    } else {
      Platform.runLater(() -> botaoPausePlayBarbeiro.setImage(new Image("img/pause.png")));
      barbeiroThread.voltarBarbeiro();
    }
  }

  /* ***************************************************************
  * Metodo: mouseEntrouPausePlayCliente
  * Funcao: modificar o estilo do mouse quando ele entra no botao
  *   de pause/play do cliente
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao pause/play
  * Retorno: void
  *************************************************************** */
  @FXML
  void mouseEntrouPausePlayCliente(MouseEvent event) {
    botaoPausePlayCliente.setCursor(Cursor.HAND);
  }
  
  /* ***************************************************************
  * Metodo: botaoPausePlayClienteClicado
  * Funcao: modifica a imagem do botao pause/play do cliente
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao pause/play
  * Retorno: void
  *************************************************************** */
  @FXML
  void botaoPausePlayClienteClicado(MouseEvent event) {
    // caso a thread do cliente nao esta suspensa trocamos a imagem de pause para play e suspendemos a thread
    // caso ela ja esteja suspensa trocamos a imagem de play para pause e reiniciamos a thread
    if(!novosClientesThread.getSuspensa()){
      Platform.runLater(() -> botaoPausePlayCliente.setImage(new Image("img/play.png")));
      novosClientesThread.suspenderProdutor();
    } else {
      Platform.runLater(() -> botaoPausePlayCliente.setImage(new Image("img/pause.png")));
      novosClientesThread.voltarProdutor();
    }
  }

  /* ***************************************************************
  * Metodo: mudouVelocidadeBarbeiro
  * Funcao: variar a velocidade de trabalho do barbeiro de acordo
  *   com o valor do slider
  * Parametros: evento sobre o slider do barbeiro
  * Retorno: void
  *************************************************************** */
  @FXML
  void mudouVelocidadeBarbeiro(MouseEvent event) {
    barbeiroThread.setVelocidadeTrabalho((long) sliderBarbeiro.getValue());
  }

  /* ***************************************************************
  * Metodo: mudouVelocidadeCliente
  * Funcao: variar a velocidade de geracao de clientes de acordo
  *   com o valor do slider
  * Parametros: evento sobre o slider do cliente
  * Retorno: void
  *************************************************************** */
  @FXML
  void mudouVelocidadeCliente(MouseEvent event) {
    novosClientesThread.setVelocidadeProducao((long) sliderCliente.getValue());
  }

  /* ***************************************************************
  * Metodo: setVariaveisThread
  * Funcao: inicializar as threads que estarao em funcionamento
  *   durante a execucao do programa
  * Parametros: evento sobre o slider do cliente
  * Retorno: void
  *************************************************************** */
  private void setVariaveisThread() {
    // setar como nulo a fila de clientes que estao na fila de espera
    Semaforos.reiniciarFila();

    // setagem do valor dos sliders do barbeiro e do cliente ao inicializar o programa
    sliderBarbeiro.setValue(2);
    sliderCliente.setValue(4);

    // instanciamento dos objetos das threads do barbeiro e do cliente
    barbeiroThread = new Barbeiro(this.barbeiro);
    novosClientesThread = new Produtor(this.anchorPane, this.barbeiro, this.cadeira);

    // colocando as threads no estado de executando
    barbeiroThread.start();
    novosClientesThread.start();
  }

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: metodo que eh disparado assim que a classe 
  *   tela principal controller eh chamada
  * Parametros: URL url e ResourceBundle resourceBundle - localizar 
  *   os arquivos necessarios da janela como botoes, imagens, slides
  *   , e etc
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // setagem do comportamento do slider do barbeiro
    this.sliderBarbeiro.setMin(1); 
    this.sliderBarbeiro.setMax(10); 
    this.sliderBarbeiro.setCursor(Cursor.HAND);

    // setagem do comportamento do slider do cliente
    this.sliderCliente.setMin(1); 
    this.sliderCliente.setMax(10); 
    this.sliderCliente.setCursor(Cursor.HAND);

    // chama o metodo de inicializacao das threads
    this.setVariaveisThread(); 
  }
}

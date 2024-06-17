/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: Cliente
* Funcao...........: classe que gerencia o funcionamento 
*   do processo do cliente durante a execucao do programa
*************************************************************** */

package model;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Cliente extends Thread {
  // referencia da imagem do cliente
  private ImageView cliente;
  
  // local que vai definir onde o cliente vai senta na fila caso tenha espacao na fila
  private String local;

  // referencia do anchorPane da aplicacao
  private AnchorPane anchorPane;
  
  // referencia da imagem da imagem
  private ImageView cadeira;

  // vetor que guarda as coordenadas que o cliente 
  private int[] posicaoImagem = new int[2];
  
  // valor do cliente na fila de clientes
  private int posicaoFila;

  // vetor de strings que define qual vai ser o local onde o cliente vai sentar
  private static String[] locais = {"primeiro_acento","segundo_acento","terceiro_acento","quarto_acento","quinto_acento"};
  
  // flag para verificar se o cliente esta em execucao
  private volatile boolean clienteVivo = true;

  // flas para verificar se o cliente esta suspenso
  private volatile boolean suspenso = false;

  public Cliente(ImageView cliente, AnchorPane anchorPane, ImageView cadeira){
    this.cliente = cliente;
    this.anchorPane = anchorPane;
    this.cadeira = cadeira;
  }

  // metodos get e set
  public String getLocal(){
    return local;
  }

  public void setLocal(String local){
    this.local = local;
  }

  public ImageView getImagemCliente(){
    return cliente;
  }

  public boolean getSuspenso(){
    return suspenso;
  }

  /* ***************************************************************
  * Metodo: suspenderCliente
  * Funcao: setar o booleano suspenso como verdadeiro, tendo como 
  *   consequencia a suspensao do processo do cliente
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void suspenderCliente() {
    suspenso = true;
  }

  /* ***************************************************************
  * Metodo: voltarCliente
  * Funcao: faz o cliente retornar ao funcionamento natural dele
  * Parametros: Vazio 
  * Retorno: void
  *************************************************************** */
  public void voltarCliente() {
    suspenso = false;
    synchronized(this) {
      this.notify();
    }
  }
  
  /* ***************************************************************
  * Metodo: entrarBarbearia
  * Funcao: movimenta o cliente ate a barbearia
  * Parametros: eixoX, eixoXFinal
  * Retorno: void
  *************************************************************** */
  private void entrarBarbearia(double eixoX, double eixoXFinal) throws InterruptedException {
    // itera enquanto o cliente nao chegou ao eixoX final
    while (eixoX < eixoXFinal) {
      eixoX = eixoX + 2;

      final double finalX = eixoX;
      Platform.runLater(() -> cliente.setLayoutX(finalX));

      Thread.sleep(20);
    }
  }

  /* ***************************************************************
  * Metodo: atualizarFila
  * Funcao: seleciona o local onde o cliente vai sentar e o
  *   movimenta ate o local definido
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  public void atualizarFila() throws InterruptedException {
    // define em qual local da fila ele vai sentar e ficar esperando para ser atendido
    switch (this.calculoLocal()) {
      case "primeiro_acento": {
        this.moverCliente(cliente.getX(), 20, new int[]{40, 162}, "entrada");
        posicaoImagem[0] = 280;
        break;
      }
      case "segundo_acento": {
        this.moverCliente(cliente.getX(), 130, new int[]{50, 162}, "entrada");
        posicaoImagem[0] = 170;
        break;
      }
      case "terceiro_acento": {
        this.moverCliente(cliente.getX(), 260, new int[]{45, 162}, "entrada");
        posicaoImagem[0] = 35;
        break;
      }
      case "quarto_acento": {
        this.moverCliente(cliente.getX(), 380, new int[]{45, 162}, "entrada");
        posicaoImagem[0] = -85;
        break;
      }
      case "quinto_acento": {
        this.moverCliente(cliente.getX(), 500, new int[]{45, 162}, "entrada");
        posicaoImagem[0] = -210;
        break;
      }
      case "nehum_acento": {
        cliente.setVisible(false);
        break;
      }
    }
    posicaoImagem[1] = 291;
  }

  /* ***************************************************************
  * Metodo: calculoLocal
  * Funcao: o cliente busca o primeiro local vazio da fila e senta
  *   naquele local definido
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  private synchronized String calculoLocal(){
    // rastreia ate encontrar uma posicao da fila de clientes que esta desocupada
    for(int posicao = 0; posicao < 5; posicao = posicao + 1) {
      // caso a posicao da fila esteja desocupada 
      if(Semaforos.clientesFila[posicao] == null) { 
        this.posicaoFila = posicao;
        Semaforos.clientesFila[posicao] = this;
        this.local = locais[posicao];
        break;
      }
    }
    
    if(local != null){
      return local;
    }
    
    // tratamento da excessao 
    return "nehum_acento";
  }

  /* ***************************************************************
  * Metodo: calculoLocal
  * Funcao: move o cliente ate um local definido 
  * Parametros: eixoX, posicaoX, posicaoImagem, solicitacao
  * Retorno: void
  *************************************************************** */
  public void moverCliente(double eixoX, double posicaoX, int[] posicaoImagem, String solicitacao) throws InterruptedException {
    // itera enquanto o cliente nao chegou eixo x final definido
    while (eixoX < posicaoX) {
      eixoX = eixoX + 2;
      final double finalX = eixoX;
      // atualiza a interface grafica
      Platform.runLater(() -> cliente.setX(finalX));
        
      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep(20);
    }

    // caso o parametro seja de entrada o cliente senta na cadeira da fila
    if(solicitacao.equals("entrada")){
      Platform.runLater(
        () -> { 
          // talvez precise trocar o layoutX tambem
          cliente.setLayoutX(posicaoImagem[0]); 
          cliente.setLayoutY(posicaoImagem[1]); 
          cliente.setFitWidth(83);
          cliente.setFitHeight(176);
          cliente.setImage(new Image("img/cliente_cabelo_grande.png"));
        }
      );
    }
    // caso o parametro seja de saida o cliente fica invisivel sinalizando que saiu da barbearia
    if(solicitacao.equals("saida")) {
      Platform.runLater(() -> cliente.setVisible(false));
    }
  }

  /* ***************************************************************
  * Metodo: liberaAcento
  * Funcao: libera o acento para que outro processo possa acessa-lo
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  private void liberaAcento() throws InterruptedException {
    Semaforos.clientesFila[posicaoFila] = null;
  }

  /* ***************************************************************
  * Metodo: sentarCadeiraBarbeiro
  * Funcao: movimenta o cliente para a cadeira do barbeiro
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  private void sentarCadeiraBarbeiro() throws InterruptedException {
    sleep(100);
    // troca a camada do cliente no anchorPane da aplicacao
    Platform.runLater(() -> {
      anchorPane.getChildren().remove(cliente);
      int posicaoCliente = anchorPane.getChildren().indexOf(cadeira);
      anchorPane.getChildren().add(posicaoCliente + 1, cliente);
    });

    // move o cliente para a cadeira do barbeiro
    Platform.runLater(() -> {
      cliente.setFitWidth(140); 
      cliente.setFitHeight(300);
      cliente.setLayoutX(posicaoImagem[0]);
      cliente.setLayoutY(posicaoImagem[1]);
    });

    // fica preso aqui enquanto o barbeiro nao terminou o corte do cabelo
    while(!Semaforos.getBarbeiroTerminou()){
      sleep(1);
    }

    // altera a imagem do cliente para cabelo cortado
    Platform.runLater(() -> cliente.setImage(new Image("img/cliente_cabelo_cortado.png")));
    
    sleep(500);
    
    // altera a imagem do cliente para cabelo andando
    Platform.runLater(() -> {
      cliente.setFitHeight(320);
      cliente.setFitWidth(240);
      cliente.setImage(new Image("img/cliente_saindo.gif"));
    });

    // move o cliente para fora da barbearia
    this.moverCliente(cliente.getX(), 350, new int[]{731, 397}, "saida");
  }

  /* ***************************************************************
  * Metodo: sairBarbeariaChateado
  * Funcao: movimenta o cliente para fora da barbearia
  * Parametros: eixoX, posicaoX
  * Retorno: void
  *************************************************************** */
  private void sairBarbeariaChateado(double eixoX, double posicaoX) throws InterruptedException {
    cliente.setScaleX(1);
    Platform.runLater(() -> {cliente.setImage(new Image("img/cliente_furioso.png"));});
    
    // itera enquanto o eixo x nao chegou no eixo x final
    while (eixoX > posicaoX) {
      eixoX = eixoX - 2;
      final double finalX = eixoX;
      // atualiza a interface grafica
      Platform.runLater(() -> cliente.setX(finalX));
          
      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep(20);
    }
    Platform.runLater(() -> cliente.setVisible(false));
  }

  public void run(){
    while (this.clienteVivo) {
      try {
        // movimenta o cliente ate a barbearia
        this.entrarBarbearia(cliente.getLayoutX(), -50);
        
        // entra na regiao critica
        Semaforos.getMutex().acquire();
        if(Semaforos.getEsperando() < Semaforos.getCadeiras()){
          // atualiza a fila conforme o fluxo de clientes 
          this.atualizarFila();

          // aumenta o numero de pessoas esperando na fila da barbearia
          Semaforos.incrementarEsperando();
          
          // aumenta o numero de clientes dando um sinal para o barbeiro
          Semaforos.getClientes().release();

          // libera a regiao critica para outro processo
          Semaforos.getMutex().release();

          // diminui o numero de barbeiros disponiveis 
          Semaforos.getBarbeiros().acquire();
          
          this.liberaAcento();
          this.sentarCadeiraBarbeiro();
        } else {
          // libera a regiao critica para outro processo
          Semaforos.getMutex().release();

          // sai chateado da barbearia
          this.sairBarbeariaChateado(cliente.getX(), -150);
        }
        break;
      } catch (InterruptedException e) {}
    }
  }
}
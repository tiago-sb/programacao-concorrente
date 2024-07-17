/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 19.05.2024
* Ultima alteracao.: 23.05.2024
* Nome.............: Thread trem vermelho
* Funcao...........: Iniciar o processo do trem de acordo com 
*   a direcao escolhida e variar a velocidade ao mudar tendo como
*   referencia o slider
*************************************************************** */

package model;

import java.lang.Thread;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.application.Platform;

public class TremVermelho extends Thread {
  
  // variaveis relativas as caracteristicas do trem
  // referencia a imagem do trem
  private ImageView tremVermelho; 

  // referencia a posicao escolhida para o trem iniciar seu movimento
  private String posicaoEscolhida;
  
  // referencia ao slider responsavel pela velocidade de movimentacao do trem
  private Slider sliderTremVermelho;
  
  // variavel que verifica se o trem esta em movimento ou nao
  private boolean tremPausado;

  // metodo construtor da classe
  public TremVermelho(ImageView tremVermelho, String posicaoEscolhida, Slider sliderTremVermelho){
    this.tremVermelho = tremVermelho;
    this.posicaoEscolhida = posicaoEscolhida;
    this.sliderTremVermelho = sliderTremVermelho;
    // chamando o listenner para monitorar os movimentos do slider
    this.monitorarSlider();
    // usando o cache para a movimentacao da animacao
    this.tremVermelho.setCache(true);
    this.tremPausado = false;

    // setagem da posicao inicial do trem na janela
    if(posicaoEscolhida.equalsIgnoreCase("superior")){
      this.tremVermelho.setLayoutX(455);
      this.tremVermelho.setLayoutY(-50);
      Platform.runLater(() -> this.tremVermelho.setRotate(0));
    }
    if(posicaoEscolhida.equalsIgnoreCase("inferior")){
      this.tremVermelho.setLayoutX(455);
      this.tremVermelho.setLayoutY(610);
      Platform.runLater(() -> this.tremVermelho.setRotate(180));
    }
  }

  // metodos get e set
  public void setPausarThread(boolean tremPausado) {
    this.tremPausado = tremPausado;
  }

  public boolean getPausarThread() {
    return tremPausado;
  }
  
  public String getPosicaoEscolhida(){
    return this.posicaoEscolhida;
  }

  /*********************************************************************
  * Metodo: pararExecucao
  * Funcao: setar o valor da variavel booleana que verifica se o trem 
  *   esta em movimento ou nao como verdade
  * Parametro: void
  * Retorno: void
  ******************************************************************* */
  public void pararExecucao(){
    this.tremPausado = true;
  }

  /*********************************************************************
  * Metodo: controlarPausa
  * Funcao: verifica se o trem em movimentacao para coloca-lo em estado
  *   de esperando estado de espera
  * Parametro: void
  * Retorno: void
  ******************************************************************* */
  private void controlarPausa() throws InterruptedException {
    synchronized (this) {
      while (getPausarThread()) {
        wait();
      }
    }
  }
  
  /*********************************************************************
  * Metodo: monitorarSlider
  * Funcao: monitorar as mudancas no valor do slider e notificar 
  *   todas ao subprocesso que esta esperando por essa mudanca
  * Parametro: void
  * Retorno: void
  ******************************************************************* */
  private void monitorarSlider(){
    sliderTremVermelho.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  /*********************************************************************
  * Metodo: girarTrem
  * Funcao: movimentar a posicao x e y do trem para entrar no trilho unico
  * Parametro: int posicao, string ladoEscolhido
  * Retorno: void
  ******************************************************************* */
  public void girarTrem(int posicao, String ladoEscolhido) throws InterruptedException {
    // variaveis recebendo a posicao do trem atualmente no eixo X e Y
    double eixoX = this.tremVermelho.getX();
    double eixoY = this.tremVermelho.getY();
    // laco que ira transportar o trem ate a posicao especificada
    for (int contador = 0; contador < posicao; contador = contador + 1) {
      this.controlarPausa();
    
      // Verifica se a velocidade eh 0 caso seja interrompe a movimentacao
      synchronized (this) {
        while (sliderTremVermelho.getValue() == 0) {
          wait();
        }
      }
      
      // atualiza a imagem no eixo X e Y com base no valor passado na direcao escolhida
      if(ladoEscolhido.equalsIgnoreCase("direita")) {
        eixoX = eixoX + 1;

        if(this.posicaoEscolhida.equalsIgnoreCase("inferior")){ 
          eixoY = eixoY - 1; 
        } 
        if(this.posicaoEscolhida.equalsIgnoreCase("superior")){ 
          eixoY = eixoY + 1; 
        }
      } else if (ladoEscolhido.equalsIgnoreCase("esquerda")){
        eixoX = eixoX - 1;
        
        if(this.posicaoEscolhida.equalsIgnoreCase("inferior")){ 
          eixoY = eixoY - 1; 
        } 
        if(this.posicaoEscolhida.equalsIgnoreCase("superior")){ 
          eixoY = eixoY + 1; 
        }
      }

      final double finalX = eixoX;
      final double finalY = eixoY;
      
      // atualiza a interface grafica
      Platform.runLater(() -> {
        this.tremVermelho.setX(finalX);
        this.tremVermelho.setY(finalY);
      });

      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep((long) ((sliderTremVermelho.getMax() - sliderTremVermelho.getValue()) + sliderTremVermelho.getMin() >= 28.00 
        ? ((sliderTremVermelho.getMax() - sliderTremVermelho.getValue()) + sliderTremVermelho.getMin()) 
        : (28.00)));
    }
    sleep(60);
  }
  
  /*********************************************************************
  * Metodo: movimentarTrem
  * Funcao: movimenta o trem vermelho pelo eixo Y 
  * Parametro: int posicaoY, String direcaoEscolhida
  * Retorno: void
  ******************************************************************* */
  public void movimentarTremY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    // variavel recebendo a posicao do trem atualmente no eixo Y
    double eixoY = this.tremVermelho.getY();
      
    for (int contador = 0; contador < posicaoY; contador = contador + 1) {
      this.controlarPausa();
      
      // Verifica se a velocidade eh 0 caso seja interrompe a movimentacao
      synchronized (this) {
        while (sliderTremVermelho.getValue() == 0) {
          wait();
        }
      }

      // atualiza a imagem no eixo Y com base no valor passado na direcao escolhida
      if (direcaoEscolhida.equalsIgnoreCase("subir")) {
        eixoY = eixoY - 1;
      } 
      if (direcaoEscolhida.equalsIgnoreCase("descer")) {
        eixoY = eixoY + 1;
      }

      final double finalY = eixoY;
      // atualiza a interface grafica
      Platform.runLater(() -> this.tremVermelho.setY(finalY));
      
      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep((long) ((sliderTremVermelho.getMax() - sliderTremVermelho.getValue()) + sliderTremVermelho.getMin() >= 28.00 
        ? ((sliderTremVermelho.getMax() - sliderTremVermelho.getValue()) + sliderTremVermelho.getMin()) 
        : (28.00)));
    }
    sleep(60);
  }

  /*********************************************************************
  * Metodo: resetarPosicaoInicial
  * Funcao: reseta as posicoes das imagens dos trens 
  *   na interface grafica
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  private void resetarPosicaoInicial() {
    this.tremVermelho.setX(0.0);
    this.tremVermelho.setY(0.0);
  }
  
  /*********************************************************************
  * Metodo: run
  * Funcao: chama as funcoes necessarias para movimentar o trem na tela
  *   de forma infinita
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  public void run(){
    // movimenta os trens na tela enquando a variavel trem pausado tem valor false
    while(!this.tremPausado){ 
      try {     
        // switch que recebe a posicao escolhida pelo usuario e movimenta o trem com base nessa escolha
        switch(this.getPosicaoEscolhida()){
          case "superior": { 
            this.movimentarTremY(130, "descer");
            Platform.runLater(() -> this.tremVermelho.setRotate(45));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremVermelho.setRotate(0));
            this.movimentarTremY(70,"descer");
            
            Platform.runLater(() -> this.tremVermelho.setRotate(315));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremVermelho.setRotate(0));
            this.movimentarTremY(95, "descer");
            Platform.runLater(() -> this.tremVermelho.setRotate(45));
            this.girarTrem(32, "esquerda");
            
            Platform.runLater(() -> this.tremVermelho.setRotate(0));
            this.movimentarTremY(70, "descer");
            Platform.runLater(() -> this.tremVermelho.setRotate(315));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremVermelho.setRotate(0));
            this.movimentarTremY(195, "descer");

            this.resetarPosicaoInicial();
            break; 
          }
          case "inferior": {
            this.movimentarTremY(165, "subir");  
            Platform.runLater(() -> this.tremVermelho.setRotate(135));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremVermelho.setRotate(180));
            this.movimentarTremY(75, "subir");

            Platform.runLater(() -> this.tremVermelho.setRotate(225));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremVermelho.setRotate(180));
            this.movimentarTremY(105, "subir");
            Platform.runLater(() -> this.tremVermelho.setRotate(135));
            this.girarTrem(32, "esquerda");
            
            Platform.runLater(() -> this.tremVermelho.setRotate(180));
            this.movimentarTremY(50, "subir");
            Platform.runLater(() -> this.tremVermelho.setRotate(225));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremVermelho.setRotate(180));
            this.movimentarTremY(175, "subir");
            
            this.resetarPosicaoInicial();
            break;
          }
        }
      } catch(Exception e){
        // caso ocorra alguma excessao durante a movimentacao o laco para
        break;
      }
    }
  }
}
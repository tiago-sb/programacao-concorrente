/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 19.05.2024
* Ultima alteracao.: 23.05.2024
* Nome.............: Thread trem azul
* Funcao...........: Iniciar o processo do trem de acordo com 
*   a direcao escolhida e variar a velocidade ao mudar tendo como
*   referencia o slider
*************************************************************** */

package model;

import java.lang.Thread;

import javafx.application.Platform;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class TremAzul extends Thread{
  
  // variaveis relativas as caracteristicas do trem
  // referencia da imagem do trem
  private ImageView tremAzul;

  // referencia da posicao escolhida para o trem iniciar seu movimento
  private String posicaoEscolhida;

  // referencia ao slider responsavel pela velocidade de movimentacao do trem
  private Slider sliderTremAzul;

  // variavel que verifica se o trem esta em movimento ou nao
  private boolean tremPausado;

  // metodo construtor da classe
  public TremAzul(ImageView tremAzul, String posicaoEscolhida, Slider sliderTremAzul){
    this.tremAzul = tremAzul;
    this.posicaoEscolhida = posicaoEscolhida;
    this.sliderTremAzul = sliderTremAzul;
    // chamando o listenner para monitorar os movimentos do slider
    this.monitorarSlider();
    
    // usando o cache para a movimentacao da animacao
    this.tremAzul.setCache(true);
    this.tremPausado = false;

    // setagem da posicao inicial do trem na janela
    if(posicaoEscolhida.equalsIgnoreCase("superior")){
      this.tremAzul.setLayoutX(390);
      this.tremAzul.setLayoutY(-50);
      Platform.runLater(() -> this.tremAzul.setRotate(0));
    }
    if(posicaoEscolhida.equalsIgnoreCase("inferior")){
      this.tremAzul.setLayoutX(390);
      this.tremAzul.setLayoutY(610);
      Platform.runLater(() -> this.tremAzul.setRotate(180));
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
  * Funcao: verifica se o trem esta em movimentacao para coloca-lo 
  *   em estado de espera caso ele esteja parado
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
    sliderTremAzul.valueProperty().addListener((observable, oldValue, newValue) -> {
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
    double eixoX = this.tremAzul.getX();
    double eixoY = this.tremAzul.getY();
    // laco que ira transportar o trem ate a posicao especificada
    for (int contador = 0; contador < posicao; contador = contador + 1) {
      // chamando o metodo para controlar e monitorar a movimentacao do trem
      this.controlarPausa();

      // Verifica se a velocidade eh 0 caso seja aguarda ate que a velocidade mude
      synchronized (this) {
        while (sliderTremAzul.getValue() == 0) {
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
        this.tremAzul.setX(finalX);
        this.tremAzul.setY(finalY);
      });

      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep((long) ((sliderTremAzul.getMax() - sliderTremAzul.getValue()) + sliderTremAzul.getMin() >= 28.00 
        ? ((sliderTremAzul.getMax() - sliderTremAzul.getValue()) + sliderTremAzul.getMin()) 
        : (28.00)));
    }
    sleep(60);
  }

  /*********************************************************************
  * Metodo: movimentarTrem
  * Funcao: movimenta o trem azul pelo eixo Y 
  * Parametro: int posicaoY, String direcaoEscolhida
  * Retorno: void
  ******************************************************************* */
  public void movimentarTremY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    // variavel recebendo a posicao do trem atualmente no eixo Y
    double eixoY = tremAzul.getY();
      
    for (int contador = 0; contador < posicaoY; contador = contador + 1) {
      this.controlarPausa();
      
      // Verifica se a velocidade eh 0 caso seja aguarda ate que a velocidade mude
      synchronized (this) {
        while (sliderTremAzul.getValue() == 0) {
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
      Platform.runLater(() -> tremAzul.setY(finalY));
      
      // coloca a thread para dormir com o valor determinado pelo slider
      Thread.sleep((long) ((sliderTremAzul.getMax() - sliderTremAzul.getValue()) + sliderTremAzul.getMin() >= 28.00 
        ? ((sliderTremAzul.getMax() - sliderTremAzul.getValue()) + sliderTremAzul.getMin()) 
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
    this.tremAzul.setX(0.0);
    this.tremAzul.setY(0.0);
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
    while (!this.tremPausado) { 
      try {
        switch(this.getPosicaoEscolhida()){
          case "superior": { 
            this.movimentarTremY(130, "descer");
            Platform.runLater(() -> this.tremAzul.setRotate(315));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremAzul.setRotate(0));
            this.movimentarTremY(70,"descer");
            
            Platform.runLater(() -> this.tremAzul.setRotate(45));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremAzul.setRotate(0));
            this.movimentarTremY(95, "descer");
            Platform.runLater(() -> this.tremAzul.setRotate(315));
            this.girarTrem(32, "direita");
            
            Platform.runLater(() -> this.tremAzul.setRotate(0));
            this.movimentarTremY(70, "descer");
            Platform.runLater(() -> this.tremAzul.setRotate(45));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremAzul.setRotate(0));
            this.movimentarTremY(195, "descer");
            
            this.resetarPosicaoInicial();
            break; 
          }
          case "inferior": {
            this.movimentarTremY(165, "subir");  
            Platform.runLater(() -> this.tremAzul.setRotate(225));
            this.girarTrem(32, "direita");
            Platform.runLater(() -> this.tremAzul.setRotate(180));
            this.movimentarTremY(75, "subir");
  
            Platform.runLater(() -> this.tremAzul.setRotate(135));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremAzul.setRotate(180));
            this.movimentarTremY(105, "subir");
            Platform.runLater(() -> this.tremAzul.setRotate(225));
            this.girarTrem(32, "direita");
            
            Platform.runLater(() -> this.tremAzul.setRotate(180));
            this.movimentarTremY(50, "subir");
            Platform.runLater(() -> this.tremAzul.setRotate(135));
            this.girarTrem(32, "esquerda");
            Platform.runLater(() -> this.tremAzul.setRotate(180));
            this.movimentarTremY(175, "subir");
  
            this.resetarPosicaoInicial();
            break;
          }  
        }
      } catch (Exception e) {
        break;
      }
    } 
  }     
}
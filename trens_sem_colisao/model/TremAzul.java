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

import controller.TelaPrincipalController;
import javafx.application.Platform;

import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class TremAzul extends Thread{
  
  // variaveis relativas as caracteristicas do trem
  // referencia da imagem do trem
  private ImageView tremAzul;

  // referencia do sinal de transito da tela
  private ImageView sinalDeTransito01;
  private ImageView sinalDeTransito02;

  // referencia da posicao escolhida para o trem iniciar seu movimento
  private String posicaoEscolhida;
  
  // referencia da solucao escolhida para evitar as colisoes dos trens 
  private String solucaoEscolhida;

  // referencia ao slider responsavel pela velocidade de movimentacao do trem
  private Slider sliderTremAzul;

  // variavel que verifica se o trem esta em movimento ou nao
  private boolean tremPausado;

  // metodo construtor da classe
  public TremAzul(ImageView tremAzul, ImageView sinalDeTransito01, ImageView sinalDeTransito02,  String posicaoEscolhida, String solucaoEscolhida, Slider sliderTremAzul){
    this.tremAzul = tremAzul;
    this.sinalDeTransito01 = sinalDeTransito01;
    this.sinalDeTransito02 = sinalDeTransito02;
    this.posicaoEscolhida = posicaoEscolhida;
    this.solucaoEscolhida = solucaoEscolhida;
    this.sliderTremAzul = sliderTremAzul;
    // chamando o listenner para monitorar os movimentos do slider
    this.monitorarSlider();
    
    // usando o cache para a movimentacao da animacao
    this.tremAzul.setCache(true);
    this.tremPausado = false;

    // setando o sinal de transito como apagado
    this.apagarSinal01();
    this.apagarSinal02();

    // setagem da posicao inicial do trem na janela
    if(posicaoEscolhida.equalsIgnoreCase("superior")){
      Platform.runLater(() -> {
        this.tremAzul.setLayoutX(390);
        this.tremAzul.setLayoutY(-50);  
        this.tremAzul.setRotate(0);
      });
    }
    if(posicaoEscolhida.equalsIgnoreCase("inferior")){
      
      Platform.runLater(() -> {
        this.tremAzul.setLayoutX(390);
        this.tremAzul.setLayoutY(610);
        this.tremAzul.setRotate(180);
      });
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
  * Metodo: acenderSinal01
  * Funcao: coloca o sinal de transito do trilho 01 visivel
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  public void acenderSinal01(){
    sinalDeTransito01.setVisible(true);
  }

  /*********************************************************************
  * Metodo: apagarSinal01
  * Funcao: coloca o sinal de transito do trilho 01 invisivel
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  public void apagarSinal01(){
    sinalDeTransito01.setVisible(false);
  }
  
  /*********************************************************************
  * Metodo: acenderSinal02
  * Funcao: coloca o sinal de transito do trilho 02 visivel
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  public void acenderSinal02(){
    this.sinalDeTransito02.setVisible(true);
  }

  /*********************************************************************
  * Metodo: apagarSinal02
  * Funcao: coloca o sinal de transito do trilho 02 invisivel
  * Parametro: vazio
  * Retorno: void
  ******************************************************************* */
  public void apagarSinal02(){
    this.sinalDeTransito02.setVisible(false);
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
        // switch que recebe a posicao escolhida pelo usuario e movimenta o trem com base nessa escolha
        switch(this.getPosicaoEscolhida()){
          case "superior": { 
            // caso onde a solucao escolhida foi a de variavel de travamento
            if(solucaoEscolhida.equals("variavel_travamento")){
              this.movimentarTremY(130, "descer");
              // laco que faz o trem esperar caso alguem esteja utilizando o trilho simples 01
              while(TelaPrincipalController.getVariavelTravamentoTrilhoSimples01() == 1){
                System.out.print("");
              }
              // objeto chamando o metodo para tornar o trilho simples 01 inacessivel para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples01(1);
              // acende o sinal do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70,"descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // objeto chamando o metodo para tornar o trilho simples 01 livre para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples01(0);
              // apaga o sinal do trilho simples 01
              this.apagarSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(95, "descer");
              
              // laco que faz o trem esperar caso alguem esteja utilizando o trilho simples 02
              while(TelaPrincipalController.getVariavelTravamentoTrilhoSimples02() == 1){
                System.out.print("");
              }
              // objeto chamando o metodo para tornar o trilho simples 02 inacessivel para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples02(1);
              // acende o sinal do trilho simples 02
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70, "descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // objeto chamando o metodo para tornar o trilho simples 02 livre para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples02(0);
              // apaga so sinal do trilho simples 02
              this.apagarSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(195, "descer");
            }
            // caso onde a solucao escolhida foi a de estrita alternancia
            if(solucaoEscolhida.equals("estrita_alternancia")){
              this.movimentarTremY(130, "descer");
              
              // laco que faz o trem esperar enquanto nao for a vez dele utilizar o trilho simples 01
              while(TelaPrincipalController.getVezTrilhoSimples01() != 0){
                System.out.print("");
              }
              // acende o sinal de transito do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70,"descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // apaga o sinal de transito do trilho simples 01
              this.apagarSinal01();
              // modifica o valor da variavel vez permitindo dessa forma que outros possam acessar a regiao critica
              TelaPrincipalController.setVezTrilhoSimples01(1);

              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(95, "descer");
              
              // laco que faz o trem esperar enquanto nao for a vez dele utilizar o trilho simples 02
              while(TelaPrincipalController.getVezTrilhoSimples02() != 0){
                System.out.print("");
              }
              // acende o sinal de transito do trilho simples 02
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70, "descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // apaga o sinal de transito do trilho simples 02
              this.apagarSinal02();
              // modifica o valor da variavel vez permitindo dessa forma que outros possam acessar a regiao critica
              TelaPrincipalController.setVezTrilhoSimples02(1);

              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(195, "descer");
            
            }
            // caso onde a solucao escolhida foi a de peterson
            if(solucaoEscolhida.equals("peterson")){
              this.movimentarTremY(130, "descer");

              // chama o metodo para o controlar a entrada no trilho simples 01
              TelaPrincipalController.entrouTrilhoSimples01(0);
              // acende o sinal de transito do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70,"descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // chama o metodo para o controlar a saida no trilho simples 01
              TelaPrincipalController.saiuTrilhoSimples01(0);
              // apaga o sinal de transito do trilho simples 01
              this.apagarSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(95, "descer");
              
              // chama o metodo para o controlar a entrada no trilho simples 02
              TelaPrincipalController.entrouTrilhoSimples02(0);
              // acende o sinal de transito do trilho simples 01
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(315));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(70, "descer");
              Platform.runLater(() -> this.tremAzul.setRotate(45));
              this.girarTrem(32, "esquerda");
              
              // chama o metodo para o controlar a saida no trilho simples 02
              TelaPrincipalController.saiuTrilhoSimples02(0);
              // apaga o sinal de transito do trilho simples 02
              this.apagarSinal02();
              
              Platform.runLater(() -> this.tremAzul.setRotate(0));
              this.movimentarTremY(195, "descer");
            }

            this.resetarPosicaoInicial();
            break; 
          }
          case "inferior": {
            if(solucaoEscolhida.equals("variavel_travamento")){
              this.movimentarTremY(165, "subir");  
              
              // laco que faz o trem esperar caso alguem esteja utilizando o trilho simples 02
              while(TelaPrincipalController.getVariavelTravamentoTrilhoSimples02() == 1){
                System.out.print("");
              }
              // objeto chamando o metodo para tornar o trilho simples 02 inacessivel para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples02(1);
              // acende o sinal de transito do trilho simples 02
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(75, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");

              // objeto chamando o metodo para tornar o trilho simples 02 livre para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples02(0);
              // apaga o sinal do trilho simples 02
              this.apagarSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(105, "subir");
              
              // laco que faz o trem esperar caso alguem esteja utilizando o trilho simples 01
              while(TelaPrincipalController.getVariavelTravamentoTrilhoSimples01() == 1) {
                System.out.print("");
              }
              // objeto chamando o metodo para tornar o trilho simples 01 inacessivel para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples01(1);
              // acende o sinal do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(50, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");

              // objeto chamando o metodo para tornar o trilho simples 01 livre para caso alguem queira acessar aquela regiao critica
              TelaPrincipalController.setVariavelTravamentoTrilhoSimples01(0);
              // apaga o sinal do trilho simples 01
              this.apagarSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(175, "subir");
            }
            if(solucaoEscolhida.equals("estrita_alternancia")){
              this.movimentarTremY(165, "subir");  
            
              // laco que faz o trem esperar enquanto nao for a vez dele utilizar o trilho simples 02
              while(TelaPrincipalController.getVezTrilhoSimples02() != 0){
                System.out.print("");
              }
              // acender o sinal do trilho simples 02
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(75, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");

              // apaga o sinal do trilho simples 02
              this.apagarSinal02();
              // modifica o valor da variavel vez permitindo dessa forma que outros possam acessar a regiao critica
              TelaPrincipalController.setVezTrilhoSimples02(1);

              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(105, "subir");
              
              // laco que faz o trem esperar enquanto nao for a vez dele utilizar o trilho simples 01
              while(TelaPrincipalController.getVezTrilhoSimples01() != 0){
                System.out.print("");
              }
              // acende o sinal do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(50, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");
              
              // apaga o sinal do trilho simples 01
              this.apagarSinal01();
              // modifica o valor da variavel vez permitindo dessa forma que outros possam acessar a regiao critica
              TelaPrincipalController.setVezTrilhoSimples01(1);

              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(175, "subir");
            }
            if(solucaoEscolhida.equals("peterson")){
              this.movimentarTremY(165, "subir");  

              // chama o metodo para o controlar a entrada no trilho simples 02
              TelaPrincipalController.entrouTrilhoSimples02(0);
              // acende o sinal do trilho simples 02
              this.acenderSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(75, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");

              // chama o metodo para o controlar a saida no trilho simples 02
              TelaPrincipalController.saiuTrilhoSimples02(0);
              // apaga o sinal do trilho simples 02
              this.apagarSinal02();

              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(105, "subir");

              // chama o metodo para o controlar a entrada no trilho simples 01
              TelaPrincipalController.entrouTrilhoSimples01(0);
              // acende o sinal do trilho simples 01
              this.acenderSinal01();

              Platform.runLater(() -> this.tremAzul.setRotate(225));
              this.girarTrem(32, "direita");
              Platform.runLater(() -> this.tremAzul.setRotate(180));
              this.movimentarTremY(50, "subir");
              Platform.runLater(() -> this.tremAzul.setRotate(135));
              this.girarTrem(32, "esquerda");
              Platform.runLater(() -> this.tremAzul.setRotate(180));

              // chama o metodo para o controlar a saida no trilho simples 01
              TelaPrincipalController.saiuTrilhoSimples01(0);
              // apaga o sinal do trilho simples 02
              this.apagarSinal01();

              this.movimentarTremY(175, "subir");
            }

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
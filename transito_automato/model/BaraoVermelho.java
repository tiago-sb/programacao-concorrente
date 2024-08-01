package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class BaraoVermelho extends Thread {
  private ImageView maquina_voadora;
  private Slider sliderBaraoVermelho;
  private static boolean visualizando;
  private volatile static boolean suspenso;

  public BaraoVermelho(ImageView maquina_voadora, Slider sliderBaraoVermelho){
    this.maquina_voadora = maquina_voadora;
    this.sliderBaraoVermelho = sliderBaraoVermelho;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderBaraoVermelho() {
    suspenso = true;
  }

  public void voltarBaraoVermelho() {
    suspenso = false;
    synchronized(this) {
      this.notify();
    }
  }

  public static void visualizar(){
    visualizando = false;
  }

  public static void ocultar(){
    visualizando = true;
  }

  public static boolean getVisualizando(){
    return visualizando;
  }

  private void controlarPausa() throws InterruptedException {
    synchronized (this) {
      while (getSuspenso()) {
        wait();
      }
    }
  }

  private void monitorarSlider(){
    sliderBaraoVermelho.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = maquina_voadora.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderBaraoVermelho.getValue() == 0) {
          wait();
        }
      }

      // atualiza a imagem no eixo Y com base no valor passado na direcao escolhida
      if (direcaoEscolhida.equals("esquerda")) {
        eixoX--;
      }
      if (direcaoEscolhida.equals("direita")) {
        eixoX++;
      }

      final double finalX = eixoX;
      // atualiza a interface grafica
      Platform.runLater(() -> maquina_voadora.setX(finalX));

      double velocidade = (sliderBaraoVermelho.getMax() - sliderBaraoVermelho.getValue()) + sliderBaraoVermelho.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = maquina_voadora.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderBaraoVermelho.getValue() == 0) {
          wait();
        }
      }
      
      // atualiza a imagem no eixo Y com base no valor passado na direcao escolhida
      if (direcaoEscolhida.equals("subir")) {
        eixoY--;
      }
      if (direcaoEscolhida.equals("descer")) {
        eixoY++;
      }

      final double finalY = eixoY;
      
      // atualiza a interface grafica
      Platform.runLater(() -> maquina_voadora.setY(finalY));

      double velocidade = (sliderBaraoVermelho.getMax() - sliderBaraoVermelho.getValue()) + sliderBaraoVermelho.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.maquina_voadora.setX(0.0);
    this.maquina_voadora.setY(0.0);
  }
  
  public void run(){ 
    while(!suspenso){
      try {
        this.movimentarEixoX(50, "direita");
        // Semaforos.carro02_carro10_esquina21.acquire();
        this.movimentarEixoX(130, "direita");
        Semaforos.carro16_carro10_ruaR_ruaS_ruaT.acquire();
        this.movimentarEixoX(130, "direita");
        Semaforos.carro01_carro10_ruaQ.release();
        // Semaforos.carro02_carro10_esquina21.release();
        Semaforos.carro01_carro10_ruaS.acquire();
        // // Semaforos.carro10_carro19_ruaS_ruaT.acquire();
        Semaforos.carro02_carro10_esquina22.acquire();
        Semaforos.carro10_carro17_esquina22.acquire();
        this.movimentarEixoX(270, "direita");
        // Semaforos.carro10_carro19_ruaP_ruaQ.release();
        Semaforos.carro02_carro10_esquina22.release();
        Semaforos.carro10_carro17_esquina22.release();
        Semaforos.carro10_carro09_esquina24.acquire();
        Semaforos.carro01_carro10_lateral_baixo_direita.acquire();
        Semaforos.carro10_carro22_lateral_direita.acquire();
        Semaforos.carro10_carro09_lateral_direita.acquire();
        Semaforos.carro02_carro10_lateral_baixo_direita.acquire();
        this.movimentarEixoX(52, "direita");
        Platform.runLater(() -> this.maquina_voadora.setRotate(180));
        this.movimentarEixoY(140, "descer");
        Semaforos.carro01_carro10_ruaS.release();
        // Semaforos.carro10_carro19_ruaS_ruaT.release();
        Semaforos.carro10_carro09_esquina24.release();
        Semaforos.carro10_carro22_lateral_direita.release();
        Semaforos.carro10_carro09_lateral_direita.release();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.maquina_voadora.setRotate(-90));
        this.movimentarEixoX(180, "esquerda");
        Semaforos.carro01_carro10_lateral_baixo_direita.release();
        Semaforos.carro16_carro10_ruaR_ruaS_ruaT.release();
        this.movimentarEixoX(35, "esquerda");
        Semaforos.carro10_carro19_lateral_baixo.acquire();
        Semaforos.carro10_carro22_lateral_baixo.acquire();
        Semaforos.carro10_carro17_lateral_esquerda_baixo.acquire();
        this.movimentarEixoX(100, "esquerda");
        Semaforos.carro02_carro10_lateral_baixo_direita.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro02_carro10_lateral_esquerda_baixo.acquire();
        this.movimentarEixoX(100, "esquerda");
        Semaforos.carro10_carro19_lateral_baixo.release();
        Semaforos.carro10_carro22_lateral_baixo.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro01_carro10_lateral_esquerda_baixo.acquire();
        this.movimentarEixoX(180, "esquerda");
        Platform.runLater(() -> this.maquina_voadora.setRotate(0));
        this.movimentarEixoY(45, "subir");
        Semaforos.carro10_carro09_lateral_esquerda.acquire();
        Semaforos.carro10_carro22_lateral_esquerda.acquire();
        this.movimentarEixoY(100, "subir");
        // Semaforos.carro10_carro19_ruaP_ruaQ.acquire();
        this.movimentarEixoY(45, "subir");
        Platform.runLater(() -> this.maquina_voadora.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro10_carro17_lateral_esquerda_baixo.release();
        Semaforos.carro01_carro10_lateral_esquerda_baixo.release();
        Semaforos.carro02_carro10_lateral_esquerda_baixo.release();
        Semaforos.carro10_carro09_lateral_esquerda.release();
        Semaforos.carro10_carro22_lateral_esquerda.release();
        this.resetarPosicaoInicial();
      } catch(Exception e){
        break;
      }
    }
  }
}

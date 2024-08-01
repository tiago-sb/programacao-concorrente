package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class TioTomas extends Thread {
  private ImageView carroca_a_vapor;
  private Slider sliderTioTomas;
  private static boolean visualizando;
  private volatile static boolean suspenso;

  public TioTomas(ImageView carroca_a_vapor, Slider sliderTioTomas){
    this.carroca_a_vapor = carroca_a_vapor;
    this.sliderTioTomas = sliderTioTomas;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderTioTomas() {
    suspenso = true;
  }

  public void voltarTioTomas() {
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
    sliderTioTomas.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }
  
  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = carroca_a_vapor.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderTioTomas.getValue() == 0) {
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
      Platform.runLater(() -> carroca_a_vapor.setX(finalX));

      double velocidade = (sliderTioTomas.getMax() - sliderTioTomas.getValue()) + sliderTioTomas.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = carroca_a_vapor.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderTioTomas.getValue() == 0) {
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
      Platform.runLater(() -> carroca_a_vapor.setY(finalY));

      double velocidade = (sliderTioTomas.getMax() - sliderTioTomas.getValue()) + sliderTioTomas.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.carroca_a_vapor.setX(0.0);
    this.carroca_a_vapor.setY(0.0);
  }

  public void run(){ 
    while (!suspenso) { 
      try {
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro01_carro16_ruaS.acquire();
        this.movimentarEixoX(130, "esquerda");
        // Semaforos.carro02_carro16_esquina22.acquire();
        // Semaforos.carro16_carro17_esquina22.acquire();
        this.movimentarEixoX(100, "esquerda");
        // Semaforos.carro02_carro16_esquina22.release();
        // Semaforos.carro16_carro17_esquina22.release();
        Semaforos.carro16_carro19_lateral_direita_ruaS_ruaT.release();
        Semaforos.carro01_carro16_ruaS.release();
        this.movimentarEixoX(45, "esquerda");
        Semaforos.carro01_carro16_rua15.acquire();
        Semaforos.carro02_carro16_rua15.acquire();
        Semaforos.carro16_carro19_esquina21.acquire();
        this.movimentarEixoX(40, "esquerda");
        Platform.runLater(() -> this.carroca_a_vapor.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro16_carro10_ruaR_ruaS_ruaT.release();
        Semaforos.carro16_carro19_esquina21.release();
        Semaforos.carro09_carro16_esquina15.acquire();
        Semaforos.carro16_carro19_lateral_cima_rua03_rua09.acquire();
        this.movimentarEixoY(90, "subir");
        Semaforos.carro09_carro16_esquina15.release();
        Semaforos.carro01_carro16_rua15.release();
        Semaforos.carro02_carro16_rua15.release();
        Semaforos.carro16_carro22_lateral_cima_rua03.acquire();
        Semaforos.carro01_carro16_rua03.acquire();
        this.movimentarEixoY(100, "subir");
        Semaforos.carro02_carro16_lateral_direita_cima.acquire();
        this.movimentarEixoY(50, "subir");
        Platform.runLater(() -> this.carroca_a_vapor.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro16_rua03.release();
        this.movimentarEixoX(30, "direita");
        Semaforos.carro01_carro16_lateral_direita_cima.acquire();
        this.movimentarEixoX(100, "direita");
        Semaforos.carro16_carro22_lateral_cima_rua03.release();
        Semaforos.carro16_carro19_lateral_cima_rua03_rua09.release();
        this.movimentarEixoX(225, "direita");
        Platform.runLater(() -> this.carroca_a_vapor.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro16_carro22_lateral_direita.acquire();
        Semaforos.carro16_carro10_ruaR_ruaS_ruaT.acquire();
        this.movimentarEixoY(90, "descer");
        Semaforos.carro16_carro09_lateral_direita.acquire();
        Semaforos.carro16_carro19_lateral_direita_ruaS_ruaT.acquire();
        this.movimentarEixoY(100, "descer");
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carroca_a_vapor.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro01_carro16_lateral_direita_cima.release();
        Semaforos.carro02_carro16_lateral_direita_cima.release();
        Semaforos.carro16_carro22_lateral_direita.release();
        Semaforos.carro16_carro09_lateral_direita.release();
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }
  }
}

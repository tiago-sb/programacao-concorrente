package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class IrmaosRocha extends Thread {
  private ImageView pedramobil;
  private Slider sliderIrmaosRocha;
  private static boolean visualizando;
  private volatile static boolean suspenso;
  
  public IrmaosRocha(ImageView pedramobil, Slider sliderIrmaosRocha){
    this.pedramobil = pedramobil;
    this.sliderIrmaosRocha = sliderIrmaosRocha;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderIrmaosRocha() {
    suspenso = true;
  }

  public void voltarIrmaosRocha() {
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
    sliderIrmaosRocha.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }
  
  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = pedramobil.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderIrmaosRocha.getValue() == 0) {
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
      Platform.runLater(() -> pedramobil.setX(finalX));

      double velocidade = (sliderIrmaosRocha.getMax() - sliderIrmaosRocha.getValue()) + sliderIrmaosRocha.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = pedramobil.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderIrmaosRocha.getValue() == 0) {
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
      Platform.runLater(() -> pedramobil.setY(finalY));

      double velocidade = (sliderIrmaosRocha.getMax() - sliderIrmaosRocha.getValue()) + sliderIrmaosRocha.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.pedramobil.setX(0.0);
    this.pedramobil.setY(0.0);
  }

  public void run(){
    while(!suspenso){
      try {
        this.movimentarEixoX(170, "esquerda");
        Semaforos.carro02_carro19_lateral_cima.acquire();
        Semaforos.carro02_carro22_lateral_cima.acquire();
        this.movimentarEixoX(135, "esquerda");
        Semaforos.carro01_carro02_lateral_baixo_direita_cima.release();
        Semaforos.carro01_carro02_lateral_cima_esquerda_baixo.acquire();
        this.movimentarEixoX(95, "esquerda");
        Semaforos.carro02_carro16_lateral_direita_cima.release();
        Semaforos.carro02_carro19_lateral_cima.release();
        Semaforos.carro02_carro22_lateral_cima.release();
        this.movimentarEixoX(225, "esquerda");
        Platform.runLater(() -> this.pedramobil.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro02_carro22_lateral_esquerda.acquire(); 
        this.movimentarEixoY(95, "descer");
        Semaforos.carro02_carro19_lateral_esquerda.acquire();
        Semaforos.carro02_carro09_lateral_esquerda.acquire();
        Semaforos.carro02_carro17_lateral_esquerda_baixo.acquire();
        this.movimentarEixoY(95, "descer");
        Semaforos.carro02_carro10_lateral_esquerda_baixo.acquire();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro02_carro19_lateral_esquerda.release();       
        this.movimentarEixoY(95, "descer");
        Semaforos.carro02_carro22_lateral_esquerda.release();
        Semaforos.carro02_carro09_lateral_esquerda.release();
        this.movimentarEixoY(52, "descer");
        Platform.runLater(() -> this.pedramobil.setRotate(90));
        this.movimentarEixoX(180, "direita");
        Semaforos.carro01_carro02_lateral_cima_esquerda_baixo.release();
        this.movimentarEixoX(45, "direita");
        Semaforos.carro02_carro19_rua21_rua27.acquire();
        Semaforos.carro02_carro22_rua27.acquire();
        this.movimentarEixoX(47, "direita");
        Platform.runLater(() -> this.pedramobil.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro02_carro17_lateral_esquerda_baixo.release();
        Semaforos.carro02_carro10_lateral_esquerda_baixo.release();
        Semaforos.carro02_carro09_esquina27.acquire();
        this.movimentarEixoY(90, "subir");
        Semaforos.carro02_carro09_esquina27.release();
        Semaforos.carro02_carro22_rua27.release();
        Semaforos.carro02_carro10_esquina21.acquire();
        Semaforos.carro02_carro16_rua15.acquire();
        Semaforos.carro01_carro02_rua15_ruaM_rua16.acquire();
        Semaforos.carro02_carro19_rua21_rua27.release();
        this.movimentarEixoY(100, "subir");
        Semaforos.carro02_carro10_esquina21.release();
        Semaforos.carro02_carro09_ruaM.acquire();
        Semaforos.carro02_carro17_ruaM.acquire();
        Semaforos.carro02_carro19_esquina15.acquire();
        this.movimentarEixoY(55, "subir");
        Platform.runLater(() -> this.pedramobil.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro02_carro19_esquina15.release();
        Semaforos.carro02_carro16_rua15.release();
        this.movimentarEixoX(40, "direita");
        Semaforos.carro02_carro19_esquina16.acquire();
        this.movimentarEixoX(40, "direita");
        Platform.runLater(() -> this.pedramobil.setRotate(180));
        this.movimentarEixoY(55, "descer");
        Semaforos.carro02_carro19_esquina16.release();
        Semaforos.carro02_carro09_ruaM.release();
        // Semaforos.carro02_carro10_esquina22.acquire();
        // Semaforos.carro02_carro16_esquina22.acquire();
        Semaforos.carro02_carro19_rua22_rua28.acquire();
        this.movimentarEixoY(85, "descer");
        // Semaforos.carro02_carro10_esquina22.release();
        Semaforos.carro01_carro02_rua15_ruaM_rua16.release();
        // Semaforos.carro02_carro16_esquina22.release();
        Semaforos.carro02_carro09_esquina28.acquire();
        Semaforos.carro02_carro22_esquina28.acquire();
        this.movimentarEixoY(105, "descer");
        Semaforos.carro02_carro09_esquina28.release();
        Semaforos.carro02_carro22_esquina28.release();
        Semaforos.carro02_carro10_lateral_baixo_direita.acquire();
        this.movimentarEixoY(45, "descer");
        Platform.runLater(() -> this.pedramobil.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro02_carro17_ruaM.release();
        Semaforos.carro02_carro19_rua22_rua28.release();
        Semaforos.carro01_carro02_lateral_baixo_direita_cima.acquire();
        this.movimentarEixoX(225, "direita");
        Platform.runLater(() -> this.pedramobil.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro02_carro22_lateral_direita.acquire();
        Semaforos.carro02_carro09_lateral_direita.acquire();
        this.movimentarEixoY(100, "subir");
        Semaforos.carro02_carro16_lateral_direita_cima.acquire();
        Semaforos.carro02_carro19_lateral_direita.acquire();
        this.movimentarEixoY(190, "subir");
        Semaforos.carro02_carro19_lateral_direita.release();
        Semaforos.carro02_carro09_lateral_direita.release();
        Semaforos.carro02_carro10_lateral_baixo_direita.release();
        this.movimentarEixoY(95, "subir");
        Semaforos.carro02_carro22_lateral_direita.release();
        this.movimentarEixoY(50, "subir");
        Platform.runLater(() -> this.pedramobil.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }
  }
}

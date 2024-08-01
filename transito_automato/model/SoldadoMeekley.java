package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class SoldadoMeekley extends Thread {
  private ImageView carro_tanque;
  private Slider sliderSoldadoMeekley;
  private static boolean visualizando;
  private volatile static boolean suspenso;

  public SoldadoMeekley(ImageView carro_tanque, Slider sliderSoldadoMeekley){
    this.carro_tanque = carro_tanque;
    this.sliderSoldadoMeekley = sliderSoldadoMeekley;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderSoldadoMeekley() {
    suspenso = true;
  }

  public void voltarSoldadoMeekley() {
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
    sliderSoldadoMeekley.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }
  
  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = carro_tanque.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderSoldadoMeekley.getValue() == 0) {
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
      Platform.runLater(() -> carro_tanque.setX(finalX));

      double velocidade = (sliderSoldadoMeekley.getMax() - sliderSoldadoMeekley.getValue()) + sliderSoldadoMeekley.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }
  
  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = carro_tanque.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderSoldadoMeekley.getValue() == 0) {
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
      Platform.runLater(() -> carro_tanque.setY(finalY));

      double velocidade = (sliderSoldadoMeekley.getMax() - sliderSoldadoMeekley.getValue()) + sliderSoldadoMeekley.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.carro_tanque.setX(0.0);
    this.carro_tanque.setY(0.0);
  }

  public void run(){ 
    while (!suspenso) {
      try {
        this.movimentarEixoX(170, "direita");
        Semaforos.carro01_carro22_rua03.acquire();
        Semaforos.carro16_carro22_lateral_cima_rua03.acquire();
        Semaforos.carro19_carro22_rua03_ruaC_rua07.acquire();
        this.movimentarEixoX(50, "direita");
        Platform.runLater(() -> this.carro_tanque.setRotate(0));
        this.movimentarEixoY(40, "subir");
        Semaforos.carro02_carro22_lateral_cima.acquire();
        this.movimentarEixoY(60, "subir");
        Platform.runLater(() -> this.carro_tanque.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro22_rua03.release();
        this.movimentarEixoX(40, "direita");
        Semaforos.carro01_carro22_rua04.acquire();
        this.movimentarEixoX(50, "direita");
        Platform.runLater(() -> this.carro_tanque.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro02_carro22_lateral_cima.release();
        Semaforos.carro16_carro22_lateral_cima_rua03.release();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carro_tanque.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro22_rua04.release();
        Semaforos.carro19_carro22_rua03_ruaC_rua07.release();
        this.movimentarEixoX(160, "direita");
        Semaforos.carro01_carro22_lateral_direita.acquire();
        Semaforos.carro02_carro22_lateral_direita.acquire();
        Semaforos.carro16_carro22_lateral_direita.acquire();
        this.movimentarEixoX(55, "direita");
        Platform.runLater(() -> this.carro_tanque.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro09_carro22_lateral_direita.acquire();
        Semaforos.carro19_carro22_lateral_direita.acquire();
        this.movimentarEixoY(90, "descer");
        Semaforos.carro10_carro22_lateral_direita.acquire();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro10_carro22_lateral_direita.release();
        Semaforos.carro19_carro22_lateral_direita.release();
        Semaforos.carro16_carro22_lateral_direita.release();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carro_tanque.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro01_carro22_lateral_direita.release();
        Semaforos.carro02_carro22_lateral_direita.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro01_carro22_esquina29.acquire();
        this.movimentarEixoX(90, "esquerda");
        Semaforos.carro01_carro22_esquina29.release();
        this.movimentarEixoX(48, "esquerda");
        Semaforos.carro02_carro22_rua28.acquire();
        Semaforos.carro17_carro22_lateral_baixo_rua28.acquire(); // --------------------------------
        Semaforos.carro19_carro22_rua27_ruaBB_rua28.acquire();
        this.movimentarEixoX(40, "esquerda");
        Platform.runLater(() -> this.carro_tanque.setRotate(180));
        this.movimentarEixoY(45, "descer");
        Semaforos.carro09_carro22_lateral_direita.release();
        Semaforos.carro10_carro22_lateral_baixo.acquire();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carro_tanque.setRotate(-90));
        this.movimentarEixoX(52, "esquerda");
        Semaforos.carro02_carro22_rua28.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro02_carro22_rua27.acquire();
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.carro_tanque.setRotate(0));
        this.movimentarEixoY(40, "subir");
        Semaforos.carro10_carro22_lateral_baixo.release();
        Semaforos.carro17_carro22_lateral_baixo_rua28.release();
        // Semaforos.carro09_carro22_lateral_esquerda_ruaU_ruaV.acquire();
        this.movimentarEixoY(55, "subir");
        Platform.runLater(() -> this.carro_tanque.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro02_carro22_rua27.release();
        Semaforos.carro19_carro22_rua27_ruaBB_rua28.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro01_carro22_esquina26.acquire();
        this.movimentarEixoX(100, "esquerda");
        Semaforos.carro01_carro22_esquina26.release();
        this.movimentarEixoX(33, "esquerda");
        Semaforos.carro10_carro22_lateral_esquerda.acquire();
        Semaforos.carro17_carro22_lateral_esquerda.acquire();
        Semaforos.carro01_carro22_lateral_esquerda.acquire();
        Semaforos.carro02_carro22_lateral_esquerda.acquire();
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.carro_tanque.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro19_carro22_lateral_esquerda.acquire();
        this.movimentarEixoY(90, "subir");
        Semaforos.carro10_carro22_lateral_esquerda.release();
        this.movimentarEixoY(100, "subir");
        Semaforos.carro19_carro22_lateral_esquerda.release();
        // Semaforos.carro09_carro22_lateral_esquerda_ruaU_ruaV.release();
        Semaforos.carro09_carro22_lateral_direita.release();
        Semaforos.carro17_carro22_lateral_esquerda.release();
        this.movimentarEixoY(50, "subir");
        Platform.runLater(() -> this.carro_tanque.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro22_lateral_esquerda.release();
        Semaforos.carro02_carro22_lateral_esquerda.release();
        this.resetarPosicaoInicial();       
      } catch (Exception e) {
        break;
      }
    }    
  }
}

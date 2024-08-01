package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class Penelope extends Thread {
  private ImageView carrinho_pra_frente;
  private Slider sliderPenelope;
  private static boolean visualizando;
  private volatile static boolean suspenso;
  
  public Penelope(ImageView carrinho_pra_frente, Slider sliderPenelope){
    this.carrinho_pra_frente = carrinho_pra_frente;
    this.sliderPenelope = sliderPenelope;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderPenelope() {
    suspenso = true;
  }

  public void voltarPenelope() {
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
    sliderPenelope.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = carrinho_pra_frente.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderPenelope.getValue() == 0) {
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
      Platform.runLater(() -> carrinho_pra_frente.setX(finalX));

      double velocidade = (sliderPenelope.getMax() - sliderPenelope.getValue()) + sliderPenelope.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = carrinho_pra_frente.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderPenelope.getValue() == 0) {
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
      Platform.runLater(() -> carrinho_pra_frente.setY(finalY));

      double velocidade = (sliderPenelope.getMax() - sliderPenelope.getValue()) + sliderPenelope.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.carrinho_pra_frente.setX(0.0);
    this.carrinho_pra_frente.setY(0.0);
  }
  
  public void run(){
    while (!suspenso) { 
      try {
        this.movimentarEixoX(170, "esquerda");
        Semaforos.carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM.acquire();
        Semaforos.carro01_carro09_ruaM.acquire();
        Semaforos.carro02_carro09_ruaM.acquire();
        this.movimentarEixoX(100, "esquerda");
        Semaforos.carro09_carro19_lateral_direita_ruaN_ruaO.release();
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro09_carro19_lateral_esquerda_ruaK_ruaL.acquire();
        Semaforos.carro09_carro16_esquina15.acquire();
        this.movimentarEixoX(100, "esquerda");
        Semaforos.carro09_carro16_esquina15.release();
        Semaforos.carro01_carro09_ruaM.release();
        Semaforos.carro02_carro09_ruaM.release();
        this.movimentarEixoX(160, "esquerda");
        Semaforos.carro01_carro09_lateral_esquerda.acquire();
        Semaforos.carro02_carro09_lateral_esquerda.acquire();
        // Semaforos.carro09_carro22_lateral_direita.acquire();
        Semaforos.carro09_carro22_lateral_esquerda_ruaU_ruaV.acquire();
        this.movimentarEixoX(58, "esquerda");
        Platform.runLater(() -> this.carrinho_pra_frente.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro10_carro09_lateral_esquerda.acquire();        
        this.movimentarEixoY(93, "descer");
        Semaforos.carro09_carro19_lateral_esquerda_ruaK_ruaL.release();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carrinho_pra_frente.setRotate(90));
        this.movimentarEixoX(45, "direita");
        Semaforos.carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM.release();
        Semaforos.carro01_carro09_lateral_esquerda.release();
        Semaforos.carro02_carro09_lateral_esquerda.release();
        Semaforos.carro10_carro09_lateral_esquerda.release();
        this.movimentarEixoX(170, "direita");
        Semaforos.carro02_carro09_esquina27.acquire();
        Semaforos.carro09_carro19_esquina27.acquire();
        this.movimentarEixoX(100, "direita");
        Semaforos.carro02_carro09_esquina27.release();
        Semaforos.carro09_carro19_esquina27.release();
        Semaforos.carro09_carro22_lateral_esquerda_ruaU_ruaV.release();
        this.movimentarEixoX(50, "direita");
        Semaforos.carro02_carro09_esquina28.acquire();
        Semaforos.carro09_carro19_esquina28.acquire();
        Semaforos.carro09_carro17_esquina28.acquire();
        Semaforos.carro09_carro22_ruaX_ruaY.acquire();
        this.movimentarEixoX(130, "direita");
        Semaforos.carro02_carro09_esquina28.release();
        Semaforos.carro09_carro19_esquina28.release();
        Semaforos.carro09_carro17_esquina28.release();
        Semaforos.carro01_carro09_esquina29.acquire();
        Semaforos.carro01_carro09_lateral_direita.acquire();
        this.movimentarEixoX(136, "direita");
        Semaforos.carro01_carro09_esquina29.release();
        Semaforos.carro02_carro09_lateral_direita.acquire();
        Semaforos.carro09_carro22_lateral_direita.acquire();
        Semaforos.carro10_carro09_lateral_direita.acquire();
        this.movimentarEixoX(46, "direita");
        Platform.runLater(() -> this.carrinho_pra_frente.setRotate(0));
        this.movimentarEixoY(45, "subir");
        // Semaforos.carro09_carro22_ruaX_ruaY.release();
        Semaforos.carro09_carro19_lateral_direita_ruaN_ruaO.acquire();
        Semaforos.carro16_carro09_lateral_direita.acquire();
        this.movimentarEixoY(146, "subir");
        Platform.runLater(() -> this.carrinho_pra_frente.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro10_carro09_lateral_direita.release();
        Semaforos.carro16_carro09_lateral_direita.release();
        // Semaforos.carro09_carro22_lateral_direita.release();
        Semaforos.carro01_carro09_lateral_direita.release();
        Semaforos.carro02_carro09_lateral_direita.release();
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }   
  }
}

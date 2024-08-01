package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class QuadrilhaMorte extends Thread {
  private ImageView carro_a_prova_de_balas;
  private Slider sliderQuadrilhaMorte;
  private static boolean visualizando;
  private volatile static boolean suspenso;

  public QuadrilhaMorte(ImageView carro_a_prova_de_balas, Slider sliderQuadrilhaMorte){
    this.carro_a_prova_de_balas = carro_a_prova_de_balas;
    this.sliderQuadrilhaMorte = sliderQuadrilhaMorte;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderQuadrilhaMorte() {
    suspenso = true;
  }

  public void voltarQuadrilhaMorte() {
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
    sliderQuadrilhaMorte.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = carro_a_prova_de_balas.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderQuadrilhaMorte.getValue() == 0) {
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
      Platform.runLater(() -> carro_a_prova_de_balas.setX(finalX));

      double velocidade = (sliderQuadrilhaMorte.getMax() - sliderQuadrilhaMorte.getValue()) + sliderQuadrilhaMorte.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = carro_a_prova_de_balas.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderQuadrilhaMorte.getValue() == 0) {
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
      Platform.runLater(() -> carro_a_prova_de_balas.setY(finalY));

      double velocidade = (sliderQuadrilhaMorte.getMax() - sliderQuadrilhaMorte.getValue()) + sliderQuadrilhaMorte.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.carro_a_prova_de_balas.setX(0.0);
    this.carro_a_prova_de_balas.setY(0.0);
  }

  public void run(){
    while (!suspenso) { 
      try {
        this.movimentarEixoX(40, "esquerda");
        Semaforos.carro02_carro17_lateral_esquerda_baixo.acquire();
        this.movimentarEixoX(90, "esquerda");
        Semaforos.carro17_carro19_lateral_baixo_rua22_rua28.release();
        Semaforos.carro17_carro22_lateral_baixo_rua28.release();
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro01_carro17_lateral_esquerda_baixo.acquire();
        this.movimentarEixoX(175, "esquerda");
        Platform.runLater(() -> this.carro_a_prova_de_balas.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro17_carro22_lateral_esquerda.acquire();
        Semaforos.carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM.acquire();
        this.movimentarEixoY(95, "subir");
        Semaforos.carro17_carro19_lateral_esquerda_ruaK_ruaL.acquire();
        this.movimentarEixoY(95, "subir");
        Semaforos.carro10_carro17_lateral_esquerda_baixo.release();
        this.movimentarEixoY(50, "subir");
        Platform.runLater(() -> this.carro_a_prova_de_balas.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro17_lateral_esquerda_baixo.release();
        Semaforos.carro02_carro17_lateral_esquerda_baixo.release();
        Semaforos.carro17_carro22_lateral_esquerda.release();
        this.movimentarEixoX(170, "direita");
        Semaforos.carro01_carro17_ruaM_rua16.acquire();
        Semaforos.carro02_carro17_ruaM.acquire();
        Semaforos.carro17_carro16_rua15.acquire();
        Semaforos.esquina15.acquire();
        this.movimentarEixoX(100, "direita");
        Semaforos.esquina15.release();
        Semaforos.carro17_carro16_rua15.release();
        Semaforos.carro17_carro19_lateral_esquerda_ruaK_ruaL.release();
        this.movimentarEixoX(40, "direita");
        Semaforos.carro17_carro19_esquina16.acquire();
        this.movimentarEixoX(47, "direita");
        Platform.runLater(() -> this.carro_a_prova_de_balas.setRotate(180));
        this.movimentarEixoY(55, "descer");
        Semaforos.carro17_carro19_esquina16.release();
        Semaforos.carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM.release();
        Semaforos.carro17_carro19_lateral_baixo_rua22_rua28.acquire();
        Semaforos.carro_16_carro17_esquina22.acquire();
        this.movimentarEixoY(90, "descer");
        Semaforos.carro01_carro17_ruaM_rua16.release();
        Semaforos.carro_16_carro17_esquina22.release();
        Semaforos.carro17_carro22_lateral_baixo_rua28.acquire();
        Semaforos.esquina28.acquire();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro10_carro17_lateral_esquerda_baixo.acquire();
        this.movimentarEixoY(43, "descer");
        Platform.runLater(() -> this.carro_a_prova_de_balas.setRotate(-90));
        this.movimentarEixoX(55, "esquerda");
        Semaforos.esquina28.release();
        Semaforos.carro02_carro17_ruaM.release();
        Semaforos.carro02_carro16_esquina22.acquire();
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }
  }
}

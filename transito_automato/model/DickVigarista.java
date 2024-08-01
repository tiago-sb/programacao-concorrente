package model;

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class DickVigarista extends Thread {
  private ImageView maquina_do_mal;
  private Slider sliderDickVigarista;
  private static boolean visualizando;
  
  // booleano que verifica se a thread do barbeiro esta suspensa
  private volatile static boolean suspenso;

  public DickVigarista(ImageView maquina_do_mal, Slider sliderDickVigarista){
    this.maquina_do_mal = maquina_do_mal;
    this.sliderDickVigarista = sliderDickVigarista;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }
  
  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderDickVigarista() {
    suspenso = true;
  }

  public void voltarDickVigarista() {
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
    sliderDickVigarista.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = maquina_do_mal.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderDickVigarista.getValue() == 0) {
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
      Platform.runLater(() -> maquina_do_mal.setX(finalX));

      double velocidade = (sliderDickVigarista.getMax() - sliderDickVigarista.getValue()) + sliderDickVigarista.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2.0 ? 2.0 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = maquina_do_mal.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderDickVigarista.getValue() == 0) {
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
      Platform.runLater(() -> maquina_do_mal.setY(finalY));

      double velocidade = (sliderDickVigarista.getMax() - sliderDickVigarista.getValue()) + sliderDickVigarista.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2.0 ? 2.0 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.maquina_do_mal.setX(0.0);
    this.maquina_do_mal.setY(0.0);
  }

  public void run(){ 
    while(!suspenso){
      try {
        this.movimentarEixoY(100, "descer");
        Semaforos.carro01_carro19_lateral_esquerda.acquire();
        Semaforos.carro01_carro17_lateral_esquerda_baixo.acquire();
        Semaforos.carro01_carro09_lateral_esquerda.acquire();
        this.movimentarEixoY(90, "descer");
        Semaforos.carro01_carro10_lateral_esquerda_baixo.acquire();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro01_carro19_lateral_esquerda.release();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro01_carro22_lateral_esquerda.release();
        Semaforos.carro01_carro09_lateral_esquerda.release();
        this.movimentarEixoY(40, "descer");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(90));
        this.movimentarEixoX(135, "direita");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro01_carro10_lateral_esquerda_baixo.release();
        Semaforos.carro01_carro17_lateral_esquerda_baixo.release();
        Semaforos.carro01_carro09_esquina26.acquire();
        Semaforos.carro01_carro22_esquina26.acquire();
        Semaforos.carro01_carro02_lateral_cima_esquerda_baixo.release();
        this.movimentarEixoY(90, "subir");
        Semaforos.carro01_carro09_esquina26.release();
        Semaforos.carro01_carro22_esquina26.release();
        Semaforos.carro01_carro10_ruaQ.acquire(); // ---------------------------------------------
        Semaforos.carro01_carro19_ruaQ.acquire();
        this.movimentarEixoY(50, "subir");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(90));
        this.movimentarEixoX(90, "direita");
        Semaforos.carro01_carro16_rua15.acquire();
        Semaforos.carro01_carro02_rua15_ruaM_rua16.acquire();
        this.movimentarEixoX(47, "direita");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro01_carro19_esquina15.acquire();
        Semaforos.carro01_carro09_ruaM.acquire();
        Semaforos.carro01_carro17_ruaM_rua16.acquire();
        Semaforos.carro01_carro10_ruaQ.release();
        Semaforos.carro01_carro19_ruaQ.release();
        this.movimentarEixoY(43, "subir");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro16_rua15.release();
        Semaforos.carro01_carro19_esquina15.release();
        this.movimentarEixoX(80, "direita");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(180));
        this.movimentarEixoY(45, "descer");
        Semaforos.carro01_carro09_ruaM.release();
        Semaforos.carro01_carro19_ruaS.acquire();
        Semaforos.carro01_carro10_ruaS.acquire();
        Semaforos.carro01_carro16_ruaS.acquire();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro17_ruaM_rua16.release();
        Semaforos.carro01_carro02_rua15_ruaM_rua16.release();
        this.movimentarEixoX(85, "direita");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(180));
        this.movimentarEixoY(45, "descer");
        Semaforos.carro01_carro22_esquina29.acquire();
        Semaforos.carro01_carro09_esquina29.acquire();
        Semaforos.carro01_carro19_ruaS.release();
        Semaforos.carro01_carro10_ruaS.release();
        Semaforos.carro01_carro16_ruaS.release();
        this.movimentarEixoY(100, "descer");
        Semaforos.carro01_carro22_esquina29.release();
        Semaforos.carro01_carro09_esquina29.release();
        Semaforos.carro01_carro02_lateral_baixo_direita_cima.acquire(); // ----------------------------------------
        Semaforos.carro01_carro10_lateral_baixo_direita.acquire();
        this.movimentarEixoY(40, "descer");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(90));
        this.movimentarEixoX(140, "direita");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(0));
        this.movimentarEixoY(50, "subir");
        Semaforos.carro01_carro09_lateral_direita.acquire();
        Semaforos.carro01_carro22_lateral_direita.acquire();
        this.movimentarEixoY(90, "subir");
        Semaforos.carro01_carro19_lateral_direita.acquire();
        Semaforos.carro01_carro16_lateral_direita_cima.acquire();
        this.movimentarEixoY(95, "subir");
        Semaforos.carro01_carro10_lateral_baixo_direita.release();
        this.movimentarEixoY(95, "subir");
        Semaforos.carro01_carro09_lateral_direita.release();
        Semaforos.carro01_carro19_lateral_direita.release();
        this.movimentarEixoY(100, "subir");
        Semaforos.carro01_carro22_lateral_direita.release();
        this.movimentarEixoY(47, "subir");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(-90));
        this.movimentarEixoX(230, "esquerda");
        Semaforos.carro01_carro19_rua04.acquire();
        Semaforos.carro01_carro22_rua04.acquire();
        this.movimentarEixoX(47, "esquerda");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(180));
        this.movimentarEixoY(45, "descer");
        Semaforos.carro01_carro02_lateral_baixo_direita_cima.release();
        Semaforos.carro01_carro16_lateral_direita_cima.release();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(-90));
        this.movimentarEixoX(80, "esquerda");
        Semaforos.carro01_carro19_rua04.release();
        Semaforos.carro01_carro22_rua04.release();
        Semaforos.carro01_carro16_rua03.acquire();
        Semaforos.carro01_carro22_rua03.acquire();
        Semaforos.carro01_carro19_rua03.acquire();
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(0));
        this.movimentarEixoY(40, "subir");
        Semaforos.carro01_carro02_lateral_cima_esquerda_baixo.acquire(); // ---------------------------------------
        Semaforos.carro01_carro16_rua03.release();
        Semaforos.carro01_carro22_rua03.release();
        Semaforos.carro01_carro19_rua03.release();
        this.movimentarEixoY(57, "subir");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(-90));
        this.movimentarEixoX(40, "esquerda");
        this.movimentarEixoX(180, "esquerda");
        Platform.runLater(() -> this.maquina_do_mal.setRotate(180));
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }
  }
}

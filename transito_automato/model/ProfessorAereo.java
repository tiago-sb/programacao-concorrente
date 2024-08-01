package model;

    // 

import javafx.application.Platform;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;

public class ProfessorAereo extends Thread {
  private ImageView carro_magico;
  private Slider sliderProfessorAereo;
  private static boolean visualizando;
  private volatile static boolean suspenso;

  public ProfessorAereo(ImageView carro_magico, Slider sliderProfessorAereo){
    this.carro_magico = carro_magico;
    this.sliderProfessorAereo = sliderProfessorAereo;
    suspenso = false;
    visualizando = false;
    this.monitorarSlider();
  }

  // metodos get e set
  public static boolean getSuspenso(){
    return suspenso;
  }
  
  public void suspenderProfessorAereo() {
    suspenso = true;
  }

  public void voltarProfessorAereo() {
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
    sliderProfessorAereo.valueProperty().addListener((observable, oldValue, newValue) -> {
      synchronized (this) {
        notifyAll();
      }
    });
  }

  public void movimentarEixoX(int posicaoX, String direcaoEscolhida) throws InterruptedException {
    double eixoX = carro_magico.getX();
    for (int contador = 0; contador < posicaoX; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderProfessorAereo.getValue() == 0) {
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
      Platform.runLater(() -> carro_magico.setX(finalX));

      double velocidade = (sliderProfessorAereo.getMax() - sliderProfessorAereo.getValue()) + sliderProfessorAereo.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void movimentarEixoY(int posicaoY, String direcaoEscolhida) throws InterruptedException {
    double eixoY = carro_magico.getY();
    for (int contador = 0; contador < posicaoY; contador++) {
      this.controlarPausa();

      synchronized (this) {
        while (this.sliderProfessorAereo.getValue() == 0) {
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
      Platform.runLater(() -> carro_magico.setY(finalY));

      double velocidade = (sliderProfessorAereo.getMax() - sliderProfessorAereo.getValue()) + sliderProfessorAereo.getMin();
      // coloca a thread para dormir com o valor determinado pelo slider
      sleep((long) (velocidade <= 2 ? 2 : velocidade));
    }
  }

  public void resetarPosicaoInicial() {
    this.carro_magico.setX(0.0);
    this.carro_magico.setY(0.0);
  }

  public void run(){
    while (!suspenso) {  
      try {
        this.movimentarEixoX(170, "direita");
        Semaforos.carro02_carro19_esquina15.acquire();
        Semaforos.carro01_carro19_esquina15.acquire();
        Semaforos.carro16_carro19_lateral_cima_rua03_rua09.acquire();
        this.movimentarEixoX(50, "direita");
        Platform.runLater(() -> this.carro_magico.setRotate(0));
        this.movimentarEixoY(45, "subir");
        Semaforos.carro02_carro19_esquina15.release();
        Semaforos.carro01_carro19_esquina15.release();
        Semaforos.carro17_carro19_lateral_esquerda_ruaK_ruaL.release();
        Semaforos.carro09_carro19_lateral_esquerda_ruaK_ruaL.release();
        Semaforos.carro01_carro19_rua03.acquire();
        Semaforos.carro19_carro22_rua03_ruaC_rua07.acquire();
        this.movimentarEixoY(150, "subir");
        Platform.runLater(() -> this.carro_magico.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro19_rua03.release();
        this.movimentarEixoX(35, "direita");
        Semaforos.carro01_carro19_rua04.acquire();
        this.movimentarEixoX(50, "direita");
        Platform.runLater(() -> this.carro_magico.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro16_carro19_lateral_cima_rua03_rua09.release();
        Semaforos.carro19_carro22_rua03_ruaC_rua07.release();
        Semaforos.carro01_carro19_rua04.release();
        Semaforos.carro09_carro19_lateral_direita_ruaN_ruaO.acquire(); // -------------------------------
        Semaforos.carro17_carro19_esquina16.acquire();
        Semaforos.carro02_carro19_esquina16.acquire();
        Semaforos.carro01_carro19_esquina16.acquire();
        this.movimentarEixoY(147, "descer");
        Platform.runLater(() -> this.carro_magico.setRotate(90));
        this.movimentarEixoX(53, "direita");
        Semaforos.carro17_carro19_esquina16.release();
        Semaforos.carro02_carro19_esquina16.release();
        Semaforos.carro01_carro19_esquina16.release();
        Semaforos.carro09_carro19_lateral_direita_ruaN_ruaO.release(); // ----------------------------
        this.movimentarEixoX(170, "direita");
        Semaforos.carro01_carro19_lateral_direita.acquire();
        Semaforos.carro02_carro19_lateral_direita.acquire();
        Semaforos.carro19_carro22_lateral_direita.acquire();
        Semaforos.carro16_carro19_lateral_direita_ruaS_ruaT.acquire(); // -----------------------------------
        this.movimentarEixoX(50, "direita");
        Platform.runLater(() -> this.carro_magico.setRotate(180));
        this.movimentarEixoY(50, "descer");
        Semaforos.carro10_carro19_ruaS_ruaT.acquire();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carro_magico.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro01_carro19_lateral_direita.release();
        Semaforos.carro02_carro19_lateral_direita.release();
        // Semaforos.carro19_carro22_lateral_direita.release();
        this.movimentarEixoX(30, "esquerda");
        Semaforos.carro01_carro19_ruaS.acquire();
        this.movimentarEixoX(140, "esquerda");
        Semaforos.carro17_carro19_lateral_baixo_rua22_rua28.acquire(); // -----------------------------------
        Semaforos.carro02_carro19_rua22_rua28.acquire();
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.carro_magico.setRotate(180));
        this.movimentarEixoY(40, "descer");
        Semaforos.carro10_carro19_ruaS_ruaT.release();
        Semaforos.carro19_carro22_rua27_ruaBB_rua28.acquire();
        Semaforos.carro01_carro19_ruaS.release();
        Semaforos.carro09_carro19_esquina28.acquire();
        Semaforos.carro16_carro19_lateral_direita_ruaS_ruaT.release(); // -------------------------------------
        this.movimentarEixoY(105, "descer");
        Semaforos.carro10_carro19_lateral_baixo.acquire();
        this.movimentarEixoY(50, "descer");
        Platform.runLater(() -> this.carro_magico.setRotate(-90));
        this.movimentarEixoX(55, "esquerda");
        Semaforos.carro02_carro19_rua22_rua28.release();
        Semaforos.carro09_carro19_esquina28.release();
        this.movimentarEixoX(30, "esquerda");
        Semaforos.carro02_carro19_rua21_rua27.acquire();
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.carro_magico.setRotate(0));
        this.movimentarEixoY(147, "subir");
        Semaforos.carro19_carro22_rua27_ruaBB_rua28.release();
        Semaforos.carro10_carro19_lateral_baixo.release();
        Semaforos.carro17_carro19_lateral_baixo_rua22_rua28.release();
        Semaforos.carro01_carro19_ruaQ.acquire();
        Semaforos.carro16_carro19_esquina21.acquire();
        // Semaforos.carro10_carro19_ruaP_ruaQ.acquire(); // -------------------------------------------------------
        this.movimentarEixoY(55, "subir");
        Platform.runLater(() -> this.carro_magico.setRotate(-90));
        this.movimentarEixoX(50, "esquerda");
        Semaforos.carro02_carro19_rua21_rua27.release();
        Semaforos.carro16_carro19_esquina21.release();
        this.movimentarEixoX(145, "esquerda");
        Semaforos.carro01_carro19_ruaQ.release();
        this.movimentarEixoX(30, "esquerda");
        Semaforos.carro01_carro19_lateral_esquerda.acquire();
        Semaforos.carro02_carro19_lateral_esquerda.acquire();
        Semaforos.carro19_carro22_lateral_esquerda.acquire();
        Semaforos.carro17_carro19_lateral_esquerda_ruaK_ruaL.acquire(); // ---------------------------------------
        this.movimentarEixoX(50, "esquerda");
        Platform.runLater(() -> this.carro_magico.setRotate(0));
        this.movimentarEixoY(50, "subir");
        // Semaforos.carro10_carro19_ruaP_ruaQ.release();
        this.movimentarEixoY(42, "subir"); 
        Platform.runLater(() -> this.carro_magico.setRotate(90));
        this.movimentarEixoX(50, "direita");
        Semaforos.carro01_carro19_lateral_esquerda.release();
        Semaforos.carro02_carro19_lateral_esquerda.release();
        Semaforos.carro19_carro22_lateral_esquerda.release();
        this.resetarPosicaoInicial();
      } catch (Exception e) {
        break;
      }
    }  
  }
}
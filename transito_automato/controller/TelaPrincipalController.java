/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: tela principal controller
* Funcao...........: Controla os elementos gráficos do JavaFX da 
*   tela principal
*************************************************************** */

package controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import model.BaraoVermelho;
import model.DickVigarista;
import model.IrmaosRocha;
import model.Penelope;
import model.ProfessorAereo;
import model.QuadrilhaMorte;
import model.Semaforos;
import model.SoldadoMeekley;
import model.TioTomas;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

public class TelaPrincipalController implements Initializable {
  @FXML
  private AnchorPane anchorPane;

  @FXML
  private ImageView botao_resetar;

  @FXML
  private ImageView carroca_a_vapor;

  @FXML
  private ImageView carro_tanque;

  @FXML
  private ImageView carro_a_prova_de_balas;

  @FXML
  private ImageView carro_magico;

  @FXML
  private ImageView carrinho_pra_frente;

  @FXML
  private ImageView pedramobil;

  @FXML
  private ImageView maquina_do_mal;

  @FXML
  private ImageView maquina_voadora;
  
  @FXML
  private ImageView pause_play_percurso_01;
  
  @FXML
  private ImageView percurso_01;
  
  @FXML
  private ImageView ver_percurso_01;

  @FXML
  private ImageView pause_play_percurso_02;

  @FXML
  private ImageView percurso_02;
  
  @FXML
  private ImageView ver_percurso_02;

  @FXML
  private ImageView pause_play_percurso_09;

  @FXML
  private ImageView percurso_09;
  
  @FXML
  private ImageView ver_percurso_09;
  
  @FXML
  private ImageView pause_play_percurso_10;

  @FXML
  private ImageView percurso_10;
  
  @FXML
  private ImageView ver_percurso_10;
  
  @FXML
  private ImageView pause_play_percurso_16;

  @FXML
  private ImageView percurso_16;
  
  @FXML
  private ImageView ver_percurso_16;
  
  @FXML
  private ImageView pause_play_percurso_17;

  @FXML
  private ImageView percurso_17;

  @FXML
  private ImageView ver_percurso_17;

  @FXML
  private ImageView pause_play_percurso_19;

  @FXML
  private ImageView percurso_19;
  
  @FXML
  private ImageView ver_percurso_19;
  
  @FXML
  private ImageView pause_play_percurso_22;

  @FXML
  private ImageView percurso_22;

  @FXML
  private ImageView ver_percurso_22;

  @FXML
  private Slider sliderDickVigarista;

  @FXML
  private Slider sliderIrmaosRocha;

  @FXML
  private Slider sliderBaraoVermelho;

  @FXML
  private Slider sliderPenelope;

  @FXML
  private Slider sliderTioTomas;

  @FXML
  private Slider sliderQuadrilhaMorte;

  @FXML
  private Slider sliderSoldadoMeekley;

  @FXML
  private Slider sliderProfessorAereo;

  private BaraoVermelho baraoVermelho;

  private DickVigarista dickVigarista;

  private IrmaosRocha irmaosRocha;

  private Penelope penelope;

  private ProfessorAereo professorAereo;

  private QuadrilhaMorte quadrilhaMorte;

  private SoldadoMeekley soldadoMeekley;

  private TioTomas tioTomas;

  @FXML
  void clicouResetar(MouseEvent event) throws InterruptedException {
    this.baraoVermelho.suspenderBaraoVermelho();
    this.dickVigarista.suspenderDickVigarista();
    this.irmaosRocha.suspenderIrmaosRocha();
    this.penelope.suspenderPenelope();
    this.professorAereo.suspenderProfessorAereo();
    this.quadrilhaMorte.suspenderQuadrilhaMorte();
    this.soldadoMeekley.suspenderSoldadoMeekley();
    this.tioTomas.suspenderTioTomas();

    this.sliderBaraoVermelho.setValue(4);
    this.sliderDickVigarista.setValue(4);
    this.sliderIrmaosRocha.setValue(4);
    this.sliderPenelope.setValue(4);
    this.sliderProfessorAereo.setValue(4);
    this.sliderQuadrilhaMorte.setValue(4);
    this.sliderSoldadoMeekley.setValue(4);
    this.sliderTioTomas.setValue(4);

    this.baraoVermelho.interrupt();
    this.dickVigarista.interrupt();
    this.irmaosRocha.interrupt();
    this.penelope.interrupt();
    this.professorAereo.interrupt();
    this.quadrilhaMorte.interrupt();
    this.soldadoMeekley.interrupt();
    this.tioTomas.interrupt();

    this.anchorPane.getChildren().removeAll(carroca_a_vapor, carro_tanque, carro_a_prova_de_balas,
    carro_magico, carrinho_pra_frente, pedramobil, maquina_do_mal, maquina_voadora);
    
    Platform.runLater(() -> this.carroca_a_vapor.setX(0));
    Platform.runLater(() -> this.carroca_a_vapor.setY(0));
    Platform.runLater(() -> this.carroca_a_vapor.setRotate(-90));
    
    Platform.runLater(() -> this.carro_tanque.setX(0)); 
    Platform.runLater(() -> this.carro_tanque.setY(0)); 
    Platform.runLater(() -> this.carro_tanque.setRotate(90));

    Platform.runLater(() -> this.carro_a_prova_de_balas.setX(0));
    Platform.runLater(() -> this.carro_a_prova_de_balas.setY(0));
    Platform.runLater(() -> this.carro_a_prova_de_balas.setRotate(-90));
    
    Platform.runLater(() -> this.carro_magico.setX(0));
    Platform.runLater(() -> this.carro_magico.setY(0));
    Platform.runLater(() -> this.carro_magico.setRotate(90));
    
    Platform.runLater(() -> this.carrinho_pra_frente.setX(0));
    Platform.runLater(() -> this.carrinho_pra_frente.setY(0));
    Platform.runLater(() -> this.carrinho_pra_frente.setRotate(-90));
    
    Platform.runLater(() -> this.pedramobil.setX(0)); 
    Platform.runLater(() -> this.pedramobil.setY(0)); 
    Platform.runLater(() -> this.pedramobil.setRotate(-90)); 
    
    Platform.runLater(() -> this.maquina_do_mal.setX(0)); 
    Platform.runLater(() -> this.maquina_do_mal.setY(0)); 
    Platform.runLater(() -> this.maquina_do_mal.setRotate(180)); 
    
    Platform.runLater(() -> this.maquina_voadora.setX(0)); 
    Platform.runLater(() -> this.maquina_voadora.setY(0)); 
    Platform.runLater(() -> this.maquina_voadora.setRotate(90));
    
    this.inicializarSemaforos();
    this.setVariaveisThread();

    this.anchorPane.getChildren().addAll(carroca_a_vapor, carro_tanque, carro_a_prova_de_balas,
    carro_magico, carrinho_pra_frente, pedramobil, maquina_do_mal, maquina_voadora);    
  }

  @FXML
  private void clicouPausePlayDickVigarista(MouseEvent event) {
    if(!DickVigarista.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_01.setImage(new Image("img/dick_vigarista/play.png"));
        dickVigarista.suspenderDickVigarista();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_01.setImage(new Image("img/dick_vigarista/pause.png"));
        dickVigarista.voltarDickVigarista();
      });
    }
  }

  @FXML
  private void clicouVisualizarDickVigarista(MouseEvent event) {
    if(!DickVigarista.getVisualizando()){
      Platform.runLater(() -> {
        percurso_01.setVisible(true);
        ver_percurso_01.setImage(new Image("img/dick_vigarista/fechar_percurso.png"));
      });
      DickVigarista.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_01.setVisible(false);
        ver_percurso_01.setImage(new Image("img/dick_vigarista/ver_percurso.png"));
      });
      DickVigarista.visualizar();
    }
  }
  
  @FXML
  private void clicouPausePlayIrmaosRocha(MouseEvent event) {
    if(!IrmaosRocha.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_02.setImage(new Image("img/irmaos_rocha/play.png"));
        irmaosRocha.suspenderIrmaosRocha();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_02.setImage(new Image("img/irmaos_rocha/pause.png"));
        irmaosRocha.voltarIrmaosRocha();
      });
    }
  }

  @FXML
  private void clicouVisualizarIrmaosRocha(MouseEvent event) {
    if(!IrmaosRocha.getVisualizando()){
      Platform.runLater(() -> {
        percurso_02.setVisible(true);
        ver_percurso_02.setImage(new Image("img/irmaos_rocha/fechar_percurso.png"));
      });
      IrmaosRocha.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_02.setVisible(false);
        ver_percurso_02.setImage(new Image("img/irmaos_rocha/ver_percurso.png"));
      });
      IrmaosRocha.visualizar();
    }
  }

  @FXML
  private void clicouPausePlayPenelope(MouseEvent event) {
    if(!Penelope.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_09.setImage(new Image("img/penelope/play.png"));
        penelope.suspenderPenelope();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_09.setImage(new Image("img/penelope/pause.png"));
        penelope.voltarPenelope();
      });
    }
  }

  @FXML
  private void clicouVisualizarPenelope(MouseEvent event) {
    if(!Penelope.getVisualizando()){
      Platform.runLater(() -> {
        percurso_09.setVisible(true);
        ver_percurso_09.setImage(new Image("img/penelope/fechar_percurso.png"));
      });
      Penelope.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_09.setVisible(false);
        ver_percurso_09.setImage(new Image("img/penelope/ver_percurso.png"));
      });
      Penelope.visualizar();
    }
  }

  @FXML
  private void clicouPausePlayBaraoVermelho(MouseEvent event) {
    if(!BaraoVermelho.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_10.setImage(new Image("img/barao_vermelho/play.png"));
        baraoVermelho.suspenderBaraoVermelho();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_10.setImage(new Image("img/barao_vermelho/pause.png"));
        baraoVermelho.voltarBaraoVermelho();
      });
    }
  }

  @FXML
  private void clicouVisualizarBaraoVermelho(MouseEvent event) {
    if(!BaraoVermelho.getVisualizando()){
      Platform.runLater(() -> {
        percurso_10.setVisible(true);
        ver_percurso_10.setImage(new Image("img/barao_vermelho/fechar_percurso.png"));
      });
      BaraoVermelho.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_10.setVisible(false);
        ver_percurso_10.setImage(new Image("img/barao_vermelho/ver_percurso.png"));
      });
      BaraoVermelho.visualizar();
    }
  }
  
  @FXML
  private void clicouPausePlayTioTomas(MouseEvent event) {
    if(!TioTomas.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_16.setImage(new Image("img/tio_tomas/play.png"));
        tioTomas.suspenderTioTomas();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_16.setImage(new Image("img/tio_tomas/pause.png"));
        tioTomas.voltarTioTomas();
      });
    }
  }

  @FXML
  private void clicouVisualizarTioTomas(MouseEvent event) {
    if(!TioTomas.getVisualizando()){
      Platform.runLater(() -> {
        percurso_16.setVisible(true);
        ver_percurso_16.setImage(new Image("img/tio_tomas/fechar_percurso.png"));
      });
      TioTomas.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_16.setVisible(false);
        ver_percurso_16.setImage(new Image("img/tio_tomas/ver_percurso.png"));
      });
      TioTomas.visualizar();
    }
  }
  
  @FXML
  private void clicouPausePlayQuadrilhaMorte(MouseEvent event) {
    if(!QuadrilhaMorte.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_17.setImage(new Image("img/quadrilha_morte/play.png"));
        quadrilhaMorte.suspenderQuadrilhaMorte();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_17.setImage(new Image("img/quadrilha_morte/pause.png"));
        quadrilhaMorte.voltarQuadrilhaMorte();
      });
    }
  }

  @FXML
  private void clicouVisualizarQuadrilhaMorte(MouseEvent event) {
    if(!QuadrilhaMorte.getVisualizando()){
      Platform.runLater(() -> {
        percurso_17.setVisible(true);
        ver_percurso_17.setImage(new Image("img/quadrilha_morte/fechar_percurso.png"));
      });
      QuadrilhaMorte.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_17.setVisible(false);
        ver_percurso_17.setImage(new Image("img/quadrilha_morte/ver_percurso.png"));
      });
      QuadrilhaMorte.visualizar();
    }
  }

  @FXML
  private void clicouPausePlayProfessorAereo(MouseEvent event) {
    if(!ProfessorAereo.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_19.setImage(new Image("img/professor_aereo/play.png"));
        professorAereo.suspenderProfessorAereo();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_19.setImage(new Image("img/professor_aereo/pause.png"));
        professorAereo.voltarProfessorAereo();
      });
    }
  }

  @FXML
  private void clicouVisualizarProfessorAereo(MouseEvent event) {
    if(!ProfessorAereo.getVisualizando()){
      Platform.runLater(() -> {
        percurso_19.setVisible(true);
        ver_percurso_19.setImage(new Image("img/professor_aereo/fechar_percurso.png"));
      });
      ProfessorAereo.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_19.setVisible(false);
        ver_percurso_19.setImage(new Image("img/professor_aereo/ver_percurso.png"));
      });
      ProfessorAereo.visualizar();
    }
  }
  
  @FXML
  private void clicouPausePlaySoldadoMeekley(MouseEvent event) {
    if(!SoldadoMeekley.getSuspenso()){
      Platform.runLater(() -> {
        pause_play_percurso_22.setImage(new Image("img/soldado_meekley/play.png"));
        soldadoMeekley.suspenderSoldadoMeekley();
      });
    } else {
      Platform.runLater(() -> {
        pause_play_percurso_22.setImage(new Image("img/soldado_meekley/pause.png"));
        soldadoMeekley.voltarSoldadoMeekley();
      });
    }
  }

  @FXML
  private void clicouVisualizarSoldadoMeekley(MouseEvent event) {
    if(!SoldadoMeekley.getVisualizando()){
      Platform.runLater(() -> {
        percurso_22.setVisible(true);
        ver_percurso_22.setImage(new Image("img/soldado_meekley/fechar_percurso.png"));
      });
      SoldadoMeekley.ocultar();
    } else {
      Platform.runLater(() -> {
        percurso_22.setVisible(false);
        ver_percurso_22.setImage(new Image("img/soldado_meekley/ver_percurso.png"));
      });
      SoldadoMeekley.visualizar();
    }
  }

  private void setVariaveisThread() {
    this.baraoVermelho = new BaraoVermelho(this.maquina_voadora, this.sliderBaraoVermelho);
    this.dickVigarista = new DickVigarista(this.maquina_do_mal, this.sliderDickVigarista);
    this.irmaosRocha = new IrmaosRocha(this.pedramobil, this.sliderIrmaosRocha);
    this.penelope = new Penelope(this.carrinho_pra_frente, this.sliderPenelope);
    this.professorAereo = new ProfessorAereo(this.carro_magico, this.sliderProfessorAereo);
    this.quadrilhaMorte = new QuadrilhaMorte(this.carro_a_prova_de_balas, this.sliderQuadrilhaMorte);
    this.soldadoMeekley = new SoldadoMeekley(this.carro_tanque, this.sliderSoldadoMeekley);
    this.tioTomas = new TioTomas(this.carroca_a_vapor, this.sliderTioTomas);

    this.baraoVermelho.start();
    this.dickVigarista.start();
    this.irmaosRocha.start();
    this.penelope.start();
    this.professorAereo.start();
    this.quadrilhaMorte.start();
    this.soldadoMeekley.start();
    this.tioTomas.start();
  }

  public void inicializarSemaforos(){
    Semaforos.carro01_carro02_lateral_cima_esquerda_baixo = new Semaphore(0);
    Semaforos.carro01_carro22_lateral_esquerda = new Semaphore(0);
    Semaforos.carro01_carro10_lateral_esquerda_baixo = new Semaphore(1);
    Semaforos.carro01_carro09_lateral_esquerda = new Semaphore(1);
    Semaforos.carro01_carro17_lateral_esquerda_baixo = new Semaphore(1);
    Semaforos.carro01_carro19_lateral_esquerda = new Semaphore(1);
    Semaforos.carro01_carro02_lateral_baixo_direita_cima = new Semaphore(0);
    Semaforos.carro01_carro10_lateral_baixo_direita = new Semaphore(1);
    Semaforos.carro01_carro16_lateral_direita_cima = new Semaphore(1);
    Semaforos.carro01_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro01_carro09_lateral_direita = new Semaphore(1);
    Semaforos.carro01_carro19_lateral_direita = new Semaphore(1);
    Semaforos.carro01_carro10_ruaQ = new Semaphore(0);
    Semaforos.carro01_carro19_ruaQ = new Semaphore(1);
    Semaforos.carro01_carro16_rua15 = new Semaphore(1);
    Semaforos.carro01_carro09_ruaM = new Semaphore(1);
    Semaforos.carro01_carro17_ruaM_rua16 = new Semaphore(1);
    Semaforos.carro01_carro02_rua15_ruaM_rua16 = new Semaphore(1);
    Semaforos.carro01_carro16_ruaS = new Semaphore(1);
    Semaforos.carro01_carro10_ruaS = new Semaphore(1);
    Semaforos.carro01_carro19_ruaS = new Semaphore(1);
    Semaforos.carro01_carro16_rua03 = new Semaphore(1);
    Semaforos.carro01_carro22_rua03 = new Semaphore(1);
    Semaforos.carro01_carro19_rua03 = new Semaphore(1);
    Semaforos.carro01_carro22_rua04 = new Semaphore(1);
    Semaforos.carro01_carro19_rua04 = new Semaphore(1);
    Semaforos.carro01_carro09_esquina26 = new Semaphore(1);
    Semaforos.carro01_carro22_esquina26 = new Semaphore(1);
    Semaforos.carro01_carro22_esquina29 = new Semaphore(1);
    Semaforos.carro01_carro09_esquina29 = new Semaphore(1);
    Semaforos.carro02_carro22_lateral_esquerda = new Semaphore(1);
    Semaforos.carro02_carro10_lateral_esquerda_baixo = new Semaphore(1);
    Semaforos.carro02_carro09_lateral_esquerda = new Semaphore(1);
    Semaforos.carro02_carro17_lateral_esquerda_baixo = new Semaphore(1);
    Semaforos.carro02_carro19_lateral_esquerda = new Semaphore(1);
    Semaforos.carro02_carro10_lateral_baixo_direita = new Semaphore(1);
    Semaforos.carro02_carro16_lateral_direita_cima = new Semaphore(0);
    Semaforos.carro02_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro02_carro09_lateral_direita = new Semaphore(1);
    Semaforos.carro02_carro19_lateral_direita = new Semaphore(1);
    Semaforos.carro02_carro22_lateral_cima = new Semaphore(1);
    Semaforos.carro02_carro19_lateral_cima = new Semaphore(1);
    Semaforos.carro02_carro19_rua21_rua27 = new Semaphore(1);
    Semaforos.carro02_carro16_rua15 = new Semaphore(1);
    Semaforos.carro02_carro22_rua27 = new Semaphore(1);
    Semaforos.carro02_carro09_ruaM = new Semaphore(1);
    Semaforos.carro02_carro17_ruaM = new Semaphore(1);
    Semaforos.carro02_carro22_esquina28 = new Semaphore(1);
    Semaforos.carro02_carro19_rua22_rua28 = new Semaphore(1);
    Semaforos.carro02_carro22_rua28 = new Semaphore(1);
    Semaforos.carro02_carro09_esquina27 = new Semaphore(1);
    Semaforos.carro02_carro10_esquina21 = new Semaphore(1);
    Semaforos.carro02_carro16_esquina22 = new Semaphore(1);
    Semaforos.carro02_carro10_esquina22 = new Semaphore(1);
    Semaforos.carro02_carro09_esquina28 = new Semaphore(1);
    Semaforos.carro02_carro19_esquina15 = new Semaphore(1);
    Semaforos.carro02_carro19_esquina16 = new Semaphore(1);
    Semaforos.carro16_carro22_lateral_cima_rua03 = new Semaphore(1);
    Semaforos.carro16_carro19_lateral_cima_rua03_rua09 = new Semaphore(1);
    Semaforos.carro16_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro16_carro09_lateral_direita = new Semaphore(1);
    Semaforos.carro16_carro19_lateral_direita_ruaS_ruaT = new Semaphore(0);
    Semaforos.carro16_carro10_ruaR_ruaS_ruaT = new Semaphore(0);
    Semaforos.carro16_carro19_esquina21 = new Semaphore(1);
    Semaforos.carro02_carro19_rua03_rua09 = new Semaphore(1);
    Semaforos.carro02_carro22_rua03 = new Semaphore(1);
    Semaforos.carro16_carro17_esquina22 = new Semaphore(1);
    Semaforos.carro10_carro22_lateral_esquerda = new Semaphore(1);
    Semaforos.carro10_carro09_lateral_esquerda = new Semaphore(1);
    Semaforos.carro10_carro17_lateral_esquerda_baixo = new Semaphore(0);
    Semaforos.carro10_carro22_lateral_baixo = new Semaphore(1);
    Semaforos.carro10_carro19_lateral_baixo = new Semaphore(1);
    Semaforos.carro10_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro10_carro09_lateral_direita = new Semaphore(1);
    Semaforos.carro10_carro19_ruaS_ruaT = new Semaphore(1);
    Semaforos.carro10_carro19_ruaP_ruaQ = new Semaphore(0);
    Semaforos.carro10_carro17_esquina22 = new Semaphore(1);
    Semaforos.carro10_carro09_esquina24 = new Semaphore(1);
    Semaforos.carro09_carro17_lateral_esquerda_ruaK_ruaL_ruaM = new Semaphore(1);
    Semaforos.carro09_carro19_lateral_esquerda_ruaK_ruaL = new Semaphore(0);
    Semaforos.carro09_carro19_lateral_direita_ruaN_ruaO = new Semaphore(0);
    Semaforos.carro09_carro22_lateral_esquerda_ruaU_ruaV = new Semaphore(1);
    Semaforos.carro09_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro09_carro22_ruaX_ruaY = new Semaphore(1);
    Semaforos.carro09_carro19_esquina27 = new Semaphore(1);
    Semaforos.carro09_carro19_esquina28 = new Semaphore(1);
    Semaforos.carro09_carro16_esquina15 = new Semaphore(1);
    Semaforos.carro01_carro19_esquina15 = new Semaphore(1);
    Semaforos.carro01_carro19_esquina16 = new Semaphore(1);
    Semaforos.carro09_carro17_esquina28 = new Semaphore(1);
    Semaforos.carro01_carro09_esquina29 = new Semaphore(1);
    Semaforos.carro17_carro19_lateral_esquerda_ruaK_ruaL = new Semaphore(0);
    Semaforos.carro17_carro22_lateral_esquerda = new Semaphore(1);
    Semaforos.carro17_carro22_lateral_baixo_rua28 = new Semaphore(0);
    Semaforos.carro17_carro19_lateral_baixo_rua22_rua28 = new Semaphore(0);
    Semaforos.carro17_carro16_rua15 = new Semaphore(1);
    Semaforos.carro17_carro19_esquina16 = new Semaphore(1);
    Semaforos.carro19_carro22_lateral_esquerda = new Semaphore(1);
    Semaforos.carro19_carro22_rua27_ruaBB_rua28 = new Semaphore(1);
    Semaforos.carro19_carro22_lateral_direita = new Semaphore(1);
    Semaforos.carro19_carro22_rua03_ruaC_rua07 = new Semaphore(1);
    Semaforos.carro_16_carro17_esquina22 = new Semaphore(1);
    Semaforos.esquina28 = new Semaphore(1);
    Semaforos.esquina15 = new Semaphore(1);
  }

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: metodo que eh disparado assim que a classe 
  *   tela principal controller eh chamada
  * Parametros: URL url e ResourceBundle resourceBundle - localizar 
  *   os arquivos necessarios da janela como botoes, imagens, slides
  *   , e etc
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    this.inicializarSemaforos();
    this.setVariaveisThread();
  }  
}

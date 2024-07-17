/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 19.05.2024
* Ultima alteracao.: 23.05.2024
* Nome.............: tela principal controller
* Funcao...........: Controla os elementos gráficos do JavaFX da 
*   tela principal: botoes, slides, ...
*************************************************************** */

package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Slider;
import javafx.scene.Cursor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import model.TremAzul;
import model.TremVermelho;

public class TelaPrincipalController implements Initializable {
    
  // identificadores dos componentes do arquivo fxml
  @FXML
  private ImageView botaoReset;
  
  @FXML
  private ImageView tremAzul;

  @FXML
  private ImageView tremVermelho;
  
  @FXML
  private Slider sliderTremVermelho;

  @FXML
  private Slider sliderTremAzul;
  
  // objeto responsavel pela thread do trem azul
  private TremAzul tremAzulThread;
  // objeto responsavel pela thread do trem vermelho
  private TremVermelho tremVermelhoThread;
  
  /* ***************************************************************
  * Metodo: mouseEntrouReset
  * Funcao: modificar o estilo do mouse quando ele entra no reset
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   passa no botao reset
  * Retorno: void
  *************************************************************** */
  @FXML
  public void mouseEntrouReset(MouseEvent event) {
    botaoReset.setCursor(Cursor.HAND);
  }
    
  /* ***************************************************************
  * Metodo: botaoResetClicado
  * Funcao: chamar o metodo setTelaEscolha que basicamente abre a 
  *   janela de escolha para o usuario escolher as posicoes iniciais
  *   dos trens
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao reset
  * Retorno: void
  *************************************************************** */
  @FXML
  public void botaoResetClicado(MouseEvent event) throws IOException {
    // objeto chamando metodo que para a execucao da thread
    tremAzulThread.pararExecucao();
    tremVermelhoThread.pararExecucao();
    
    // metodo que abre a tela de escolha 
    this.setTelaEscolha();
    
    // setando as posicoes dos trens iniciais
    this.tremAzul.setX(0.0); 
    this.tremAzul.setY(0.0);
    this.tremVermelho.setX(0.0); 
    this.tremVermelho.setY(0.0);
    
    // metodo para colocar a thread para executar
    this.setVariaveisThread();
  }

  /* ***************************************************************
  * Metodo: setVariaveisThread
  * Funcao: metodo que coloca a thread pronta e coloca ela pra 
  *   executar
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  private void setVariaveisThread(){
    // objeto que vai trazer a escolha do usuario relativa a posicao dos trens
    TelaEscolhaController escolhaUsuario = new TelaEscolhaController();    
    // setagem do valor inicial presente no slider do azul e do vermelho
    this.sliderTremAzul.setValue(30);
    this.sliderTremVermelho.setValue(30);
    
    // criacao da thread
    tremAzulThread = new TremAzul(this.tremAzul, escolhaUsuario.getPosicaoEscolhidaTremAzul(), this.sliderTremAzul);
    // coloca a thread no estado de execucao
    tremAzulThread.start();
    
    // criacao da thread
    tremVermelhoThread = new TremVermelho(this.tremVermelho, escolhaUsuario.getPosicaoEscolhidaTremVermelho(), this.sliderTremVermelho);
    // coloca a thread no estado de execucao
    tremVermelhoThread.start();
  }

  /* ***************************************************************
  * Metodo: setTelaEscolha
  * Funcao: abre a janela de escolha para o usuario escolher 
  *   as posicoes iniciais dos trens
  * Parametros: vazio
  * Retorno: void
  *************************************************************** */
  public void setTelaEscolha() throws IOException {
    // instanciando objeto para poder carregar o arquivo fxml
    FXMLLoader loader = new FXMLLoader();
    // objeto setando a localizacao do arquivo fxml da tela de escolha
    loader.setLocation(getClass().getResource("/view/tela_escolha.fxml"));
    
    // instanciando o objeto controller para poder seta-lo como controlador do fxml tela_escolha
    TelaEscolhaController controller = new TelaEscolhaController(); 
    loader.setController(controller);  
    
    // pegando a localizacao e alocando essa arvore hierarquica na variavel raiz
    Parent raiz = loader.load();

    // instanciando novo palco que sera responsavel pela tela de escolha
    Stage palcoEscolha = new Stage();
    // objeto chamando o metodo para setar o titulo da janela
    palcoEscolha.setTitle("Escolha a posicao");
    // objeto chamando o metodo para impedir da jenela ser redimencionada
    palcoEscolha.setResizable(false);
    // objeto chama metodo que retira a decoracao da pagina impedindo que o usuario feche a janela sem escolher uma opcao
    palcoEscolha.initStyle(StageStyle.UNDECORATED);
    // objeto chama metodo que impede que o usuario interaja com a outra tela da aplicacao enquanto essa estiver aberta
    palcoEscolha.initModality(Modality.APPLICATION_MODAL);
    // objeto chamando metodo para setar uma nova cena para o programa nas dimensoes 700x400
    palcoEscolha.setScene(new Scene(raiz, 700, 400));

    // objeto chamando o metodo para mostrar o conteudo da janela
    palcoEscolha.showAndWait();
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
    // bloco try-catch que dispara o catch caso ocorra algum erro durante a execucao do programa
    try {
      // chamando o metodo para setar as posicoes dos trens
      this.setTelaEscolha();
    } catch (IOException e) {
      // disparar erro
      e.printStackTrace();
      return;
    }    
    
    // metodo para setar o valor minimo e maximo aceitado pelo slider do trem azul - [1, 100]
    this.sliderTremAzul.setMin(0); 
    this.sliderTremAzul.setMax(100); 
    // metodo para modifica o estilo do cursor quando o mouse passa pelo slider do trem azul
    this.sliderTremAzul.setCursor(Cursor.HAND);

    // metodo para setar o valor minimo e maximo aceitado pelo slider do trem vermelho - [1, 100]
    this.sliderTremVermelho.setMin(0); 
    this.sliderTremVermelho.setMax(100); 
    // metodo para modifica o estilo do cursor quando o mouse passa pelo slider do trem vermelho
    this.sliderTremVermelho.setCursor(Cursor.HAND);
    
    // chamando metodo que ira setar os atributos necessarios para os respectivos trens. Posicao, velocidade, ...
    this.setVariaveisThread();
  }
}

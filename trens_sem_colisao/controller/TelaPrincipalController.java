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
  
  @FXML
  private ImageView sinalDeTransito01;

  @FXML
  private ImageView sinalDeTransito02;

  // objeto responsavel pela thread do trem azul
  private TremAzul tremAzulThread;
  // objeto responsavel pela thread do trem vermelho
  private TremVermelho tremVermelhoThread;
  
  // variaveis necessarias para efetuar a solucao de variavel de travamento
  // variavel de travamento para o trilho simples 01
  private static int variavelTravamentoTrilhoSimples01 = 0;
  // variavel de travamento para o trilho simples 02
  private static int variavelTravamentoTrilhoSimples02 = 0;

  // variaveis necessarias para efetuar a solucao de estrita alternancia
  // variavel de travamento para o trilho simples 01
  // trem azul - vez 0
  // trem vermelho - vez 1
  private static int vezTrilhoSimples01 = 0; 
  // variavel de travamento para o trilho simples 02
  private static int vezTrilhoSimples02 = 0;

  // variaveis necessarias para efetuar a solucao de solucao de Peterson
  // indice 0 - trem azul
  // indice 1 - trem vermelho
  private static boolean interesseTrilhoSimples01[] = new boolean[2];
  private static boolean interesseTrilhoSimples02[] = new boolean[2];
  private static int vezTrilhoSimples01_Peterson;
  private static int vezTrilhoSimples02_Peterson;

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
    
    // resetando o valor da variavel de travamento
    setVariavelTravamentoTrilhoSimples01(0);
    setVariavelTravamentoTrilhoSimples02(0);
    
    // resetando o valor da estrita alternancia
    setVezTrilhoSimples01(0);
    setVezTrilhoSimples02(0);

    // resetando o valor do vetor de interesses dos processos
    interesseTrilhoSimples01[0] = false;
    interesseTrilhoSimples01[1] = false;
    interesseTrilhoSimples02[0] = false;
    interesseTrilhoSimples02[1] = false;

    // criacao da thread
    tremAzulThread = new TremAzul(this.tremAzul, this.sinalDeTransito01, this.sinalDeTransito02, escolhaUsuario.getPosicaoEscolhidaTremAzul(), escolhaUsuario.getSolucao(), this.sliderTremAzul);
    // coloca a thread no estado de execucao
    tremAzulThread.start();
    
    // criacao da thread
    tremVermelhoThread = new TremVermelho(this.tremVermelho, this.sinalDeTransito01, this.sinalDeTransito02, escolhaUsuario.getPosicaoEscolhidaTremVermelho(), escolhaUsuario.getSolucao(), this.sliderTremVermelho);
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
  
  /*********************************************************************
  * Metodo: entrouTrilhoSimples01
  * Funcao: setar o interesse como verdadeiro para o processo que 
  *   chamou o metodo e verificar se pode ou não entrar na 
  *   regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public static void entrouTrilhoSimples01(int identificador) {
    int outroProcesso = 1 - identificador;
    interesseTrilhoSimples01[identificador] = true; 
    vezTrilhoSimples01_Peterson = identificador;
    // enquanto o outro processo esta passando na regiao critica que eu desejo atravessar eu fico esperando
    // ela sair de la para eu utilizar essa regiao critica
    while (vezTrilhoSimples01_Peterson == identificador && interesseTrilhoSimples01[outroProcesso] == true){
      System.out.print("");
    }
  }
  
  /*********************************************************************
  * Metodo: saiuTrilhoSimples01
  * Funcao: setar o interesse como falso para thread que chamou 
  *   o metodo, ou seja, saiu da regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public static void saiuTrilhoSimples01(int identificador) {
    interesseTrilhoSimples01[identificador] = false;
  }
  
  /*********************************************************************
  * Metodo: entrouTrilhoSimples02
  * Funcao: setar o interesse como verdadeiro para o processo 
  *   que chamou o metodo e verificar se pode ou nao
  *   acessar essa regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public static void entrouTrilhoSimples02(int identificador) {
    int outroProcesso = 1 - identificador;
    interesseTrilhoSimples02[identificador] = true;
    vezTrilhoSimples02_Peterson = identificador;
    // enquanto o outro processo esta passando na regiao critica que eu desejo atravessar eu fico esperando
    // ela sair de la para eu utilizar essa regiao critica
    while(vezTrilhoSimples02_Peterson == identificador && interesseTrilhoSimples02[outroProcesso] == true){
      System.out.print("");
    }
  }
  
  /*********************************************************************
  * Metodo: saiuTrilhoSimples02
  * Funcao: setar o interesse como falso para o processo que chamou 
  *   o metodo, significa que saiu dessa regiao critica
  * Parametros: int id
  * Retorno: void
  ******************************************************************* */
  public static void saiuTrilhoSimples02(int identificador) {
    interesseTrilhoSimples02[identificador] = false;
  }
  
  // metodos get e set
  public static int getVariavelTravamentoTrilhoSimples01(){
    return variavelTravamentoTrilhoSimples01;
  }

  public static void setVariavelTravamentoTrilhoSimples01(int variavelTravamento){
    variavelTravamentoTrilhoSimples01 = variavelTravamento;
  }

  public static int getVariavelTravamentoTrilhoSimples02(){
    return variavelTravamentoTrilhoSimples02;
  }

  public static void setVariavelTravamentoTrilhoSimples02(int variavelTravamento){
    variavelTravamentoTrilhoSimples02 = variavelTravamento;
  }
  
  public static int getVezTrilhoSimples01(){
    return vezTrilhoSimples01;
  }

  public static void setVezTrilhoSimples01(int vezTrilhoSimples){
    vezTrilhoSimples01 = vezTrilhoSimples;
  }

  public static int getVezTrilhoSimples02(){
    return vezTrilhoSimples02;
  }

  public static void setVezTrilhoSimples02(int vezTrilhoSimples){
    vezTrilhoSimples02 = vezTrilhoSimples;
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

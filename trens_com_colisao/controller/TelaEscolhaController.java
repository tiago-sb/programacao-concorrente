/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 19.05.2024
* Ultima alteracao.: 23.05.2024
* Nome.............: tela escolha controller
* Funcao...........: Controla os elementos gráficos do JavaFX da 
*   tela escolha: botoes, slides, ...
*************************************************************** */

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Cursor;

public class TelaEscolhaController implements Initializable {
  
  // identificadores dos componentes do arquivo fxml
  @FXML
  private ImageView botaoEscolha;

  @FXML
  private Label escolhaSemValor;
  
  @FXML
  private ChoiceBox<String> posicaoDoTremChoiceBox;

  @FXML
  private ChoiceBox<String> solucaoDoTremChoiceBox;
  
  // arranjo que contem todas as posiveis posicoes dos trens
  private String[] posicoesTrens = { "Superior esquerdo e Superior direito", "Inferior esquerdo e Inferior direito", 
                                     "Superior esquerdo e Inferior direito", "Inferior direito e Superior esquerdo" };
  
  // variaveis que irao alocarar o texto que destaca a posicao escolhida para a movimentacao do trem
  private static String posicaoEscolhidaTremAzul;
  private static String posicaoEscolhidaTremVermelho;

  /* ***************************************************************
  * Metodo: mouseEntrouEscolher
  * Funcao: modificar o estilo do cursor do mouse quando ele entrar
  *   na regiao do botao 'escolher'
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao iniciar
  * Retorno: void
  *************************************************************** */
  @FXML
  public void mouseEntrouEscolher(MouseEvent event) {
    botaoEscolha.setCursor(Cursor.HAND);
  }

  /* ***************************************************************
  * Metodo: mouseEntrouPosicao
  * Funcao: modificar o estilo do cursor do mouse quando ele entrar
  *   na regiao do choice box escolher posicao
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao iniciar
  * Retorno: void
  *************************************************************** */
  @FXML
  public void mouseEntrouPosicao(MouseEvent event) {
    posicaoDoTremChoiceBox.setCursor(Cursor.HAND);
  }

  /* ***************************************************************
  * Metodo: mouseEntrouSolucao
  * Funcao: modificar o estilo do cursor do mouse quando ele entrar
  *   na regiao do choice box escolher solucao
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao iniciar
  * Retorno: void
  *************************************************************** */
  @FXML
  public void mouseEntrouSolucao(MouseEvent event) {
    solucaoDoTremChoiceBox.setCursor(Cursor.HAND);
  }
    
  /* ***************************************************************
  * Metodo: botaoClicado
  * Funcao: setar as posicoes para os trens 
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao reset
  * Retorno: void
  *************************************************************** */
  @FXML
  public void botaoClicado(MouseEvent event) {
    try{
      // aninhamento de ifs que compara o valor selecionado pelo usuario com o vetor de opcoes que podem ser escolhidas
      // caso onde o usuario escolheu Superior esquerdo e Superior direito
      if(posicaoDoTremChoiceBox.getValue().equals(posicoesTrens[0])){
        this.setPosicaoEscolhidaTremAzul("superior");
        this.setPosicaoEscolhidaTremVermelho("superior");
      } 
      // caso onde o usuario escolheu Inferior esquerdo e Inferior direito
      if(posicaoDoTremChoiceBox.getValue().equals(posicoesTrens[1])){
        this.setPosicaoEscolhidaTremAzul("inferior");
        this.setPosicaoEscolhidaTremVermelho("inferior");
      }
      // caso onde o usuario escolheu Superior esquerdo e Inferior direito
      if(posicaoDoTremChoiceBox.getValue().equals(posicoesTrens[2])){
        this.setPosicaoEscolhidaTremAzul("superior");
        this.setPosicaoEscolhidaTremVermelho("inferior");
      }
      // caso onde o usuario escolheu Inferior direito e Superior esquerdo
      if(posicaoDoTremChoiceBox.getValue().equals(posicoesTrens[3])){
        this.setPosicaoEscolhidaTremAzul("inferior");
        this.setPosicaoEscolhidaTremVermelho("superior");
      }
      
      // pega a referencia da janela de escolha
      Stage palcoEscolha = (Stage) botaoEscolha.getScene().getWindow();
      // objeto fecha a janela de escolha
      palcoEscolha.close();
    } catch(Exception exception){
      // trata a excecao mostrando o erro para o usuario 
      escolhaSemValor.setStyle("-fx-background-color: #F78181; -fx-background-radius: 30; -fx-font-weight: bold;");
      escolhaSemValor.setText("Erro: Escolha uma opcao");
      escolhaSemValor.setPadding(new Insets(0, 0, 0, 10));
      
      // metodo que da foco para um no específico na janela
      posicaoDoTremChoiceBox.requestFocus();
    }
  }
  
  // metodos get e set
  public String getPosicaoEscolhidaTremAzul(){
    return TelaEscolhaController.posicaoEscolhidaTremAzul;
  }

  public void setPosicaoEscolhidaTremAzul(String posicaoEscolhidaTremAzul){
    TelaEscolhaController.posicaoEscolhidaTremAzul = posicaoEscolhidaTremAzul;
  }
  
  public String getPosicaoEscolhidaTremVermelho(){
    return posicaoEscolhidaTremVermelho;
  }
  
  public void setPosicaoEscolhidaTremVermelho(String posicaoEscolhidaTremVermelho){
    TelaEscolhaController.posicaoEscolhidaTremVermelho = posicaoEscolhidaTremVermelho;
  }

  /* ***************************************************************
  * Metodo: initialize
  * Funcao: metodo que eh disparado assim que a classe 
  *   tela escolha controller eh chamada
  * Parametros: URL url e ResourceBundle resourceBundle - localizar 
  *   os arquivos necessarios da janela como botoes, imagens, slides
  *   , e etc
  * Retorno: void
  *************************************************************** */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // colocando na caixa de escolha os indices contidos no arranjo de strings 'posicoesTrens'
    posicaoDoTremChoiceBox.getItems().addAll(posicoesTrens);
  }
}
/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 19.05.2024
* Ultima alteracao.: 23.05.2024
* Nome.............: tela inicial controller
* Funcao...........: Controla os elementos gráficos do JavaFX da 
*   tela inicial
*************************************************************** */

package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Cursor;

public class TelaInicialController {
  
  // identificadores dos componentes do arquivo fxml
  @FXML 
  private ImageView botaoIniciar;
  
  /* ***************************************************************
  * Metodo: mouseEntrouIniciar
  * Funcao: modificar o estilo do cursor do mouse quando ele entrar
  *   na regiao do botao 'iniciar'
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   entra no botao iniciar
  * Retorno: void
  *************************************************************** */
  @FXML
  void mouseEntrouIniciar(MouseEvent event) {
    botaoIniciar.setCursor(Cursor.HAND);
  }
  
  /* ***************************************************************
  * Metodo: botaoIniciarClicado
  * Funcao: carregar o fxml da tela principal da aplicao quando o
  *   botao Iniciar for clicado
  * Parametros: MouseEvent event - evento que dispara quando o mouse
  *   clica no botao iniciar
  * Retorno: void
  *************************************************************** */
  @FXML
  public void botaoIniciarClicado(MouseEvent event) throws IOException {
    // pega a referencia da janela inicial que possui o botao iniciar 
    Stage palcoInicial = (Stage) botaoIniciar.getScene().getWindow();

    // carrega o arquivo fxml da tela principal
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela_principal.fxml"));
    // seta a hierarquia do fxml na variavel raiz 
    Parent raiz = loader.load();

    // instanciando o objeto controller para poder seta-lo como controlador do fxml tela_principal
    TelaPrincipalController controller = new TelaPrincipalController(); 
    loader.setController(controller);  
    
    // instanciando o objeto palcoPrincipal que sera o novo palco da aplicao
    Stage palcoPrincipal = new Stage();
    
    // objeto chamando o metodo para setar o titulo da janela
    palcoPrincipal.setTitle("Trabalho 01");
    // objeto chamando o metodo para impedir da jenela ser redimencionada
    palcoPrincipal.setResizable(false);
    // objeto chamando metodo para setar uma nova cena para o programa nas dimensoes 700x400
    palcoPrincipal.setScene(new Scene(raiz, 700, 600));
    // objeto chamando o metodo para mostrar o conteudo da janela
    palcoPrincipal.show();
    
    // fechando a janela inicial do programa
    palcoInicial.close();

    // objeto encerrando todas as instancias relacionadas ao palco principal quando a janela eh fechada
    palcoPrincipal.setOnCloseRequest(t -> {
      Platform.exit();
      System.exit(0);
    });

    // linha para localizar o caminho do LOGO.png do programa e atribuir isso a uma variavel
    Image icone = new Image(getClass().getResourceAsStream("/img/logo.png")); 
    // difinicao do icone para a aplicacao
    palcoPrincipal.getIcons().add(icone);
  }
}
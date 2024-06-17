/* ***************************************************************
* Autor............: Tiago Santos Bela
* Matricula........: 202220722
* Inicio...........: 09.06.2024
* Ultima alteracao.: 17.06.2024
* Nome.............: O barbeiro dorminhoco
* Funcao...........: Simular atraves de uma interface grafica o
*   funcionamento do problema classico de programacao concorrente
*   do barbeiro dorminhoco utilizando semaforos
*************************************************************** */

import controller.TelaInicialController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Principal extends Application {

  /* ***************************************************************
  * Metodo: start
  * Funcao: inicia o processo do javafx para a minha aplicacao
  * posicao dos trens quando acionado
  * Parametros: Stage palcoPrincipal - sera o palco principal da 
  *   minha aplicacao
  * Retorno: void
  *************************************************************** */
  @Override
  public void start(Stage palcoPrincipal) throws Exception {
    // linha para carregar o arquivo fxml da tela inicial do programa
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tela_inicial.fxml"));
    
    // linha para localizar o caminho do LOGO.png do programa e atribuir isso a uma variavel
    Image icone = new Image(getClass().getResourceAsStream("/img/logo.png")); 
    // difinicao do icone para a aplicacao
    palcoPrincipal.getIcons().add(icone);

    // instanciando o objeto controller para poder seta-lo como controlador do fxml tela_inicial
    TelaInicialController controller = new TelaInicialController(); 
    loader.setController(controller);  
    
    // carregando arquivo fxml em uma variavel raiz que vai ter a arvore hierarquica do fxml
    Parent raiz = loader.load();
    // objeto chamando o metodo para setar o titulo da janela
    palcoPrincipal.setTitle("Tela de Inicio");
    
    // objeto chamando o metodo para impedir da jenela ser redimencionada
    palcoPrincipal.setResizable(false);
    // objeto chamando metodo para setar uma nova cena para o programa nas dimensoes 700x400
    palcoPrincipal.setScene(new Scene(raiz, 700, 400));

    // objeto chamando o metodo para mostrar o conteudo da janela
    palcoPrincipal.show();
  }
  
  /* ***************************************************************
  * Metodo: main
  * Funcao: inicia o processo principal de toda a aplicacao
  * posicao dos trens quando acionado
  * Parametros: String[] args - vetor de argumentos para 
  *   serem utilizados na main
  * Retorno: void
  *************************************************************** */
  public static void main(String[] args) {
    // metodo statico que chama o metodo start
    launch(args);  
  }
}


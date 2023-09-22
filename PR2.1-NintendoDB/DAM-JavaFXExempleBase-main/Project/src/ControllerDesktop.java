import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.Initializable;

public class ControllerDesktop  implements Initializable{
    //----------------2
    @FXML
    private ChoiceBox<String> choiceBox;

    @FXML
    private VBox yPane = new VBox();

    @FXML
    private AnchorPane info;

    //---------------------3
    String opcions[] = {"Personatges", "Jocs", "Consoles"};

    //---------------------9
    public void loadList() {
        //Obtenir opcio seleccionada
        String opcio = choiceBox.getValue();
            //Obtenir referencia a AppData que gestiona les dades
        AppData appData = AppData.getInstance();
            //Mostrar missatge de carrega
        showLoading();
            //Demanar led dades
        appData.load(opcio, (result) -> {
            if (result == null) {
                System.out.println("controllerDesktop: Error loading.");
            } else {
                showList(opcio);
            }
        });
    }

    public void showList(String opcioCarregada) {
        //Si sha carregat altra opcio, no fem res
          // (perque el callback pot arribar despres de que usuari hagi canviat dopcio)
        String opcioSeleccionada = choiceBox.getValue();
        if (opcioCarregada.compareTo(opcioSeleccionada) != 0) {
            return;
        }

          //Obtenir una referencia a l'objecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();
          //Obtenir les dades de l'opcio seleccionada
        JSONArray dades = appData.getData(opcioCarregada);
          //Esborrar llista actual
        yPane.getChildren().clear();
          //Carregar la llista de dades
        for (int i = 0; i< dades.length(); i++){
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                Label label = new Label(nom);
                yPane.getChildren().add(label);
            }
        }
    }

    //------------------------------------------10
    appData.load(opcio, (result) -> {
        if (result == null) {
            System.out.println("ControllerDesktop: Error loading data.");
        } else {
            //Cal afegir el try a la crida de showList
            try{
                showList(option);
            } catch (Exception e) {
                System.out.println("ControllerDesktop: Error showing list.");
            }
        }
    });

    //------------------------------------------11 (29)
      //Carregar plantilla
    URL resource = this.getClass().getResource("assets/template_list_item.fxml");
      //Esborra llista actual
    yPane.getChildren().clear();
      //Carregar la llista amb les dades
    for (int i = 0; i < dades.length(); i++) {
        JSONObject consoleObject = dades.getJSONObject(i);

        if (consoleObject.has("nom")) {
            String nom = consoleObject.getString("nom");
            String imatge = "assets/images/"+consoleObject.getString("imatge");
            FXML loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerListItem itemController  = loader.getController();
            itemController.setText(nom);
            itemController.setImage(imatge);

            yPane.getChildren().add(itemTemplate);
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Afegeix les opcions al ChoiceBox
        choiceBox.getItems().addAll(opcions);
        //Selecciona la primera opcio
        choiceBox.setValue(opcions[0]);
        //Callback que s'executa quan l'usuari escull una opcio
        choiceBox.setOnAction((event) -> {loadList();} );
        //Carregar automaticament les dades de 'Personatges'
        loadList();
    }

}
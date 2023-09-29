import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
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
            //Demanar les dades
        //------------------------------------------10
        appData.load(opcio, (result) -> {
            if (result == null) {
                System.out.println("ControllerDesktop: Error loading data.");
            } else {
                //Cal afegir el try a la crida de showList
                try{
                    showList(opcio);
                } catch (Exception e) {
                    System.out.println("ControllerDesktop: Error showing list.");
                }
            }
        });
    }

    public void showList(String opcioCarregada) throws IOException {
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
          //Carregar la plantilla
        URL resource = this.getClass().getResource("assets/template_list_item.fxml");
          //Esborrar llista actual
        yPane.getChildren().clear();
          //Carregar la llista de dades
        for (int i = 0; i< dades.length(); i++){
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                String imatge = "assets/images/" + consoleObject.getString("imatge");
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();
                itemController.setText(nom);
                itemController.setImage(imatge);
                  // Defineix el callback que sexecutara quan lusuari seleccioni un element
                  // (cal passar final perquè es pugui accedir des del callback)
                final String type = opcioSeleccionada;
                final int index = i;
                itemTemplate.setOnMouseClicked(event -> {
                  showInfo(type, index);
                });
                yPane.getChildren().add(itemTemplate);
            }
        }

    }

    public void showLoading() {
          // Esborra la llista actual
        yPane.getChildren().clear();
          // Afegeix un indicador de progres com a primer element de la llista
        ProgressIndicator progressIndicator = new ProgressIndicator();
        yPane.getChildren().add(progressIndicator);
    }

    void showInfo(String type, int index) {
          //Obtenir referencia al objecte AppData que gestiona les dades
        AppData appData = AppData.getInstance();
          //Obtenir les dades de lopcio seleccionada
        JSONObject dades = appData.getItemData(type, index);
          //Carrega la plantilla
        URL resource = this.getClass().getResource("assets/template_info_.fxml");;
        switch (type){
          case "Consoles":
            resource = this.getClass().getResource("assets/template_info_consoles.fxml");
            break;
          case "Jocs":
            resource = this.getClass().getResource("assets/template_info_jocs.fxml");
            break;
          case "Personatges":
            resource = this.getClass().getResource("assets/template_info_personatges.fxml");
            break;
        }
        
          //Esborra la info actual
        info.getChildren().clear();

          //Carregar la llista amb dades
        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerInfoItem itemController = loader.getController();
            itemController.setImage("assets/images/" + dades.getString("imatge"));
            itemController.setTitle(dades.getString("nom"));
            switch (type) {
                case "Consoles": 
                  itemController.setText(dades.getString("procesador")); 
                  itemController.setColor(dades.getString("color"));
                  itemController.setData(dades.getString("data"));
                  itemController.setVenudes(dades.getInt("venudes"));
                  break;
                case "Jocs": 
                  itemController.setText(dades.getString("descripcio")); 
                  itemController.setAny(dades.getInt("any"));
                  itemController.setTipus(dades.getString("tipus"));
                  break;
                case "Personatges": 
                  itemController.setText(dades.getString("nom_del_videojoc"));
                  itemController.setColor(dades.getString("color"));
                  itemController.setNomJoc(dades.getString("nom_del_videojoc"));
                  break;
            }

              // Afegeix informacio a la vista
            info.getChildren().add(itemTemplate);
              //stableix que la mida de itemTemplaate s'ajusti a la mida de info
            AnchorPane.setTopAnchor(itemTemplate, 0.0);
            AnchorPane.setRightAnchor(itemTemplate, 0.0);
            AnchorPane.setBottomAnchor(itemTemplate, 0.0);
            AnchorPane.setLeftAnchor(itemTemplate, 0.0);

        } catch (Exception e) {
            System.out.println("ControllerDesktop: Error showing info.");
            System.out.println(e);
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
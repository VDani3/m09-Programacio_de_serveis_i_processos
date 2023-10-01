import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ControllerMobile2 implements Initializable{
    
    @FXML
    Label name = new Label();

    @FXML
    Label color = new Label();

    @FXML
    Label serie = new Label();

    @FXML
    Button atras = new Button();

    @FXML
    AnchorPane info = new AnchorPane();

    public void loadList() {
        //Obtenir opcio seleccionada
        String opcio = Options.categorySel;
            //Obtenir referencia a AppData que gestiona les dades
        AppData appData = AppData.getInstance();
            //Demanar les dades
        //------------------------------------------10
        appData.load(opcio, (result) -> {
            if (result == null) {
                System.out.println("ControllerDesktop: Error loading data.");
            } else {
                //Cal afegir el try a la crida de showList
                try{
                    showInfo(cat, id);
                } catch (Exception e) {
                    System.out.println("ControllerDesktop: Error showing list.");
                }
            }
        });
    }

    int id = Options.charSel;
    String cat = Options.categorySel;

    void showInfo(String c, int i){
        AppData appData = AppData.getInstance();
        JSONObject dades = appData.getItemData(c, i);
        URL resource = this.getClass().getResource("assets/template_info_item.fxml");
        switch(c){
            case "Consoles":
                resource = this.getClass().getResource("assets/template_info_consoles_m.fxml");
                break;
            case "Jocs":
                resource = this.getClass().getResource("assets/template_info_jocs_m.fxml");
                break;
            case "Personatges":
                resource = this.getClass().getResource("assets/template_info_personatges_m.fxml");
                break;
        }

        info.getChildren().clear();

        try {
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerInfoItemM itemController = loader.getController();
            itemController.setImage("assets/images/"+dades.get("imatge"));
            itemController.setName(dades.getString("nom"));

            switch(c) {
                case "Personatges":
                    itemController.setColor(dades.getString("color"));
                    itemController.setSerie(dades.getString("nom_del_videojoc"));
                    break;
                case "Jocs":
                    itemController.setAny(Integer.toString(dades.getInt("any")));
                    itemController.setTipus(dades.getString("tipus"));
                    itemController.setDescripcio(dades.getString("descripcio"));
                    break;
                case "Consoles":
                    itemController.setAny(dades.getString("data"));
                    itemController.setColor(dades.getString("color"));
                    itemController.setProcesador(dades.getString("procesador"));
                    itemController.setVenudes(dades.getInt("venudes"));
                    break;
            }

            info.getChildren().add(itemTemplate);
            AnchorPane.setTopAnchor(itemTemplate, 0.0);
            AnchorPane.setRightAnchor(itemTemplate, 0.0);
            AnchorPane.setBottomAnchor(itemTemplate, 0.0);
            AnchorPane.setLeftAnchor(itemTemplate, 0.0);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void returnTo(){
        atras.setOnMouseClicked(event -> {
            UtilsViews.setViewAnimating("Mobile1");
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //Carregar automaticament les dades de 'Personatges'
        loadList();
        returnTo();
    }

}

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControllerMobile1 implements Initializable{
    
    @FXML
    private VBox yPane = new VBox();

    @FXML
    private Button atras = new Button();

    String opcions[] = {"Personatges", "Jocs", "Consoles"};
    Options opt = Options.getInstance();
    String optSel = Options.categorySel;
    public static String charSeleccionat;

    public void loadList(String optSel) {
        AppData appData = AppData.getInstance();
        //Cal afegir el try a la crida de showList
        appData.load(optSel, (result) -> {
            if (result == null) {
                System.out.println("ControllerDesktop: Error loading data.");
            } else {
                //Cal afegir el try a la crida de showList
                try{
                    showList(optSel);
                } catch (Exception e) {
                    System.out.println("ControllerDesktop: Error showing list.");
                }
            }
        });
    }


    private void showList(String opt) throws IOException {
        AppData appData = AppData.getInstance();
        JSONArray dades = appData.getData(opt);
        URL resource = this.getClass().getResource("assets/template_list_item.fxml");
        //Esborrar llista actual
        yPane.getChildren().clear();
         //Cargar la lista de datos
        for (int i= 0; i < dades.length(); i++) {
            JSONObject consoleObject = dades.getJSONObject(i);
            if (consoleObject.has("nom")) {
                String nom = consoleObject.getString("nom");
                String imatge = "assets/images/"+ consoleObject.getString("imatge");
                FXMLLoader loader = new FXMLLoader(resource);
                Parent itemTemplate = loader.load();
                ControllerListItem itemController = loader.getController();
                itemController.setText(nom);
                itemController.setImage(imatge);

                final int id = i;
                itemTemplate.setOnMouseClicked(event -> {
                    Options.changeSel(id);
                    refresh();
                    UtilsViews.setViewAnimating("Mobile2");
                });

                yPane.getChildren().add(itemTemplate);
            }
        }

    }

    public void returnTo(){
        atras.setOnMouseClicked(event -> {
            UtilsViews.setViewAnimating("Mobile0");
        });
    }

    public void refresh(){
        try{
            UtilsViews.parentContainer.getChildren().clear();
            UtilsViews.addView(getClass(), "Mobile0", "assets/layout_mobile_0.fxml");
            UtilsViews.addView(getClass(), "Desktop", "assets/layout_desktop.fxml");
            UtilsViews.addView(getClass(), "Mobile0", "assets/layout_mobile_0.fxml");
            UtilsViews.addView(getClass(), "Mobile1", "assets/layout_mobile_1.fxml");
            UtilsViews.addView(getClass(), "Mobile2", "assets/layout_mobile_2.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb){
        loadList(optSel);
        returnTo();
    }

}

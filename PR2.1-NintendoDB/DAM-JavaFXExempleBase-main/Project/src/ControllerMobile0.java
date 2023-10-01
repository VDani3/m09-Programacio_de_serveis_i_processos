import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class ControllerMobile0 implements Initializable{
    
    @FXML
    private VBox yPane = new VBox();

    String opcions[] = {"Personatges", "Jocs", "Consoles"};
    String opcions2[] = {"personatge", "jocs", "consoles"};
    Options opt = Options.getInstance();

    public void loadList() {
        //Cal afegir el try a la crida de showList
        try{
            showList();
            } catch (Exception e) {
                System.out.println("ControllerDesktop: Error showing list.");
            }
    }

    private void showList() throws IOException {
        URL resource = this.getClass().getResource("assets/template_list_item.fxml");
        //Esborrar llista actual
        yPane.getChildren().clear();
         //Cargar la lista de datos
        for (int i= 0; i < 4; i++) {
            String nom = opcions[i];
            String image = "assets/images/"+opcions2[i]+".png";
            FXMLLoader loader = new FXMLLoader(resource);
            Parent itemTemplate = loader.load();
            ControllerListItem itemController = loader.getController();
            itemController.setText(nom);
            itemController.setImage(image);

            itemTemplate.setOnMouseClicked(event -> {
                Options.changeCat(nom);
                refresh();
                UtilsViews.setViewAnimating("Mobile1");
            });

            yPane.getChildren().add(itemTemplate);
        }

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
        loadList();
    }


}

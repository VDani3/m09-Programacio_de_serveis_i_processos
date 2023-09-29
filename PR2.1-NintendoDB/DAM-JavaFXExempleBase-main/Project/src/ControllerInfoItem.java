import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ControllerInfoItem {

    @FXML
    private ImageView img;

    @FXML
    private Label title = new Label();

    @FXML
    private Label text = new Label();

    @FXML
    private Label data = new Label();

    @FXML
    private Label color = new Label();

    @FXML
    private Label venudes = new Label();

    @FXML
    private Label any = new Label();

    @FXML
    private Label tipus = new Label();

    @FXML
    private Label nomJoc = new Label();

    public void setImage(String resourceName) {
        // Obte una referencia al recurs dins del jar
      ClassLoader classLoader = getClass().getClassLoader();
      Image image = new Image(classLoader.getResourceAsStream(resourceName));
      // Estableix la imatge a ImageView
      img.setImage(image);
    }

    public void setTitle(String text) {
          // Estableix el contingut del Label
        this.title.setText(text);
    }

    public void setText(String text) {
           // Estableix el contingut del Label
        this.text.setText(text);
    }

    public void setData(String text) {
           // Estableix el contingut del Label
        this.data.setText(text);
    }

    public void setColor(String text){
      this.color.setText(text);;
    }

    public void setVenudes(int ven){
      this.venudes.setText(Integer.toString(ven));
    }

    public void setAny(int ven){
      this.any.setText(Integer.toString(ven));
    }

    public void setTipus(String text){
      this.tipus.setText(text);
    }

    public void setNomJoc(String text) {
      this.nomJoc.setText(text);
    }
}


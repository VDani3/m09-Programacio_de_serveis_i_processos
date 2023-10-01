import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerInfoItemM {
    @FXML
    private Label name = new Label();

    @FXML
    private Label color = new Label();

    @FXML
    private Label serie = new Label();

    @FXML
    private ImageView img = new ImageView();

    @FXML
    private Label any = new Label();

    @FXML
    private Label tipus = new Label();

    @FXML
    private Label descrip = new Label();

    @FXML
    private Label procesador = new Label();

    @FXML
    private Label venudes = new Label();

    public void setName(String n) {
        this.name.setText(n);
    }

    public void setColor(String c) {
        this.color.setText(c);
    }

    public void setSerie(String s) {
        this.serie.setText(s);
    }

    public void setImage(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        Image image = new Image(classLoader.getResourceAsStream(resourceName));
        img.setImage(image);
    }

    public void setAny(String a) {
        this.any.setText(a);
    }

    public void setTipus(String t) {
        this.tipus.setText(t);
    }

    public void setDescripcio(String d){
        this.descrip.setText(d);
    }

    public void setProcesador(String p) {
        this.procesador.setText(p);
    }

    public void setVenudes(int v) {
        this.venudes.setText(Integer.toString(v));
    }

}

package aj;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.event.*;

public class AboutController {
	private Stage dialog;
	public void setDialog(Stage dialog) {
		this.dialog = dialog;
	}

	@FXML
	private void buttonClicked(ActionEvent event) {
		this.dialog.close();
	}
}

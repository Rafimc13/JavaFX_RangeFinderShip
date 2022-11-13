package com.rafailbalis;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MessageOKCancel {
    Stage stage;
    Label lbl;
    Button btnOK, btnCancel;
    boolean ReturnValue;
    String message;
    String title;

    public MessageOKCancel(String message, String title) {
        this.message = message;
        this.title = title;
        show();
    }

    public void show () {
        lbl = new Label(message);

        btnOK = new Button("OK");
        btnOK.setDefaultButton(true);
//        btnOK = new Button();
//        Image img = new Image("ok.png");
//        ImageView view = new ImageView(img);
//        btnOK.setGraphic(view);
//        btnOK.setDefaultButton(true);
//        btnOK.setPrefSize(60, 25);
        btnOK.setOnAction(e -> okMethod());

        btnCancel = new Button("Cancel");
        btnCancel.setDefaultButton(true);
        btnCancel.setOnAction(e -> CancelMethod());

        VBox pane = new VBox();


        HBox pane2 = new HBox();
        pane2.setSpacing(3);
        pane2.setPadding(new Insets(5));
        pane2.getChildren().addAll(btnOK, btnCancel);
        pane2.setAlignment(Pos.CENTER);

        pane.getChildren().addAll(lbl, pane2);
        pane.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane);

        stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }

    public void okMethod () {
        ReturnValue = true;
        stage.close();
    }
    public void CancelMethod () {
        ReturnValue = false;
        stage.close();
    }

    public boolean getResponse () {
        return ReturnValue;
    }
}
package com.rafailbalis;

import javafx.application.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.*;
import java.sql.*;

public class Main extends Application{
    Label lbShiptype, lbPerLines, lbLogoMat;
    ComboBox<String> comShipsGRC, comShipsTUR;
    TextField txtPerLines;
    TextField txtresults;

    ChoiceBox<String> chozoom;

    Button btnEnter, btnPinakas;

    RadioButton[] rdcountry;
    ToggleGroup togcountry;

    @Override
    public void start(Stage stage) {

        //My main pane!
        GridPane pane = new GridPane();
        pane.setVgap(3);
        pane.setHgap(3);
        pane.setPadding(new Insets(3, 3, 3, 3));

        //ComboBoxes for the ships!!
        comShipsGRC = new ComboBox<>();
        comShipsGRC.setEditable(false);

        comShipsTUR = new ComboBox<>();
        comShipsTUR.setEditable(false);

        //RadioButtons for Countries
        togcountry = new ToggleGroup();
        rdcountry = new RadioButton[2];
        rdcountry[0] = new RadioButton("Greece");
        rdcountry[0].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        rdcountry[1] = new RadioButton("Turkey");
        rdcountry[1].setFont(Font.font("Arial", FontWeight.BOLD, 12));
        rdcountry [0].setToggleGroup(togcountry);
        rdcountry [1].setToggleGroup(togcountry);

        togcountry.selectToggle(rdcountry[0]);
        comShipsTUR.setVisible(false);

        rdcountry[0].setOnAction(e->{
            comShipsGRC.setVisible(true);
            comShipsTUR.setVisible(false);
        });

        rdcountry[1].setOnAction(e-> {
            comShipsTUR.setVisible(true);
            comShipsGRC.setVisible(false);
        });
        // Call function for the names of ships!!
        Shipname();



        //Labels...
        lbShiptype = new Label("Ship type:");
        lbShiptype.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbPerLines = new Label("Periscope Lines:");
        lbPerLines.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        lbLogoMat = new Label("Υ/Β ΜΑΤΡΩΖΟΣ");
        lbLogoMat.setFont(Font.font("Arial", FontWeight.BOLD, 12));


        //TextBox and ChoiceBox!!
        txtPerLines = new TextField();
        chozoom = new ChoiceBox<>();
        txtresults = new TextField();
        txtresults.setPromptText("Result...");
        txtresults.setEditable(false);
        chozoom.getItems().addAll("L", "M", "H");
        chozoom.getSelectionModel().select(0);


        //Buttons!
        btnEnter = new Button("ENTER");
        btnEnter.setFont(Font.font("Arial", FontWeight.BOLD, 12));
        btnPinakas = new Button("Πίνακας Αποστάσεων");

        btnEnter.setOnAction(e -> {
            if (validate()) {
                outputData();
            }
        });


        //VBox for a Pane for Countries!!
        VBox countPane = new VBox();
        countPane.getChildren().addAll(rdcountry[0],rdcountry[1]);

        //GridPane work...
        pane.add(countPane, 1,0);
        GridPane.setHalignment(countPane, HPos.CENTER);
        GridPane.setValignment(countPane, VPos.CENTER);

        pane.add(lbShiptype, 0, 1);
        GridPane.setHalignment(lbShiptype, HPos.RIGHT);
        GridPane.setValignment(lbShiptype, VPos.CENTER);
        pane.add(comShipsGRC, 1, 1);
        GridPane.setHalignment(comShipsGRC, HPos.LEFT);
        GridPane.setValignment(comShipsGRC, VPos.CENTER);
        GridPane.setColumnSpan(comShipsGRC, 1);

        pane.add(comShipsTUR, 1, 1);
        GridPane.setHalignment(comShipsTUR, HPos.LEFT);
        GridPane.setValignment(comShipsTUR, VPos.CENTER);
        GridPane.setColumnSpan(comShipsTUR, 1);

        pane.add(lbPerLines, 0, 2);
        GridPane.setHalignment(lbPerLines, HPos.RIGHT);
        GridPane.setValignment(lbPerLines, VPos.CENTER);
        pane.add(txtPerLines, 1, 2);
        GridPane.setHalignment(txtPerLines, HPos.LEFT);
        pane.add(chozoom, 2, 2);
        GridPane.setHalignment(chozoom, HPos.LEFT);
        GridPane.setValignment(chozoom, VPos.CENTER);

        pane.add(lbLogoMat, 2, 4);
        GridPane.setHalignment(lbLogoMat, HPos.RIGHT);
        GridPane.setValignment(lbLogoMat, VPos.BOTTOM);

        pane.add(btnPinakas, 0, 4);
        GridPane.setHalignment(btnPinakas, HPos.LEFT);
        GridPane.setValignment(btnPinakas, VPos.BOTTOM);
        GridPane.setColumnSpan(btnPinakas, 2);

        //HBox for the Results...
        HBox paneResults = new HBox();
        paneResults.getChildren().addAll(btnEnter, txtresults);
        paneResults.setSpacing(3);

        pane.add(paneResults, 1, 3);
        GridPane.setValignment(paneResults, VPos.CENTER);
        GridPane.setHalignment(paneResults, HPos.LEFT);
        GridPane.setColumnSpan(paneResults, 2);



        //Να χωρίσουμε και λίγο στήλες και γραμμές!!!
        ColumnConstraints col1 = new ColumnConstraints(100, 150, 200);
        col1.setHgrow(Priority.SOMETIMES);
        ColumnConstraints col2 = new ColumnConstraints(100, 150, 200);
        col2.setHgrow(Priority.SOMETIMES);
        ColumnConstraints col3 = new ColumnConstraints(100, 150, 200);

        Region rEmpty = new Region();
        pane.add(rEmpty, 0, 3);
        col3.setHgrow(Priority.SOMETIMES);


        RowConstraints row1 = new RowConstraints(30);
        RowConstraints row2 = new RowConstraints(30);
        RowConstraints row3 = new RowConstraints(30);
        RowConstraints row4 = new RowConstraints(50);
        RowConstraints row5 = new RowConstraints(50);


        pane.getRowConstraints().addAll(row1, row2, row3, row4, row5);
        pane.getColumnConstraints().addAll(col1, col2, col3);


        //Να φτιάξουμε και λίγο το σκηνικό
        Scene scene = new Scene(pane);

        stage.setScene(scene);
        stage.setTitle("Range Finder");
        stage.setWidth(500);
        stage.setHeight(250);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Validation for lines of Periscope!!
    public boolean validate() {
        String errors = "";
        try {
            int lines = Integer.valueOf(txtPerLines.getText());
            if (lines <= 0) {
                errors += "Αριθμός γραμμών πρέπει να είναι μεγαλύτερος από μηδέν\n";
            } else if (lines > 100) {
                errors += "Αριθμός γραμμών πρέπει να είναι μικρότερος από 100\n";
            }
        } catch (NumberFormatException b) {
            errors += "Οι Γραμμές πρέπει να είναι αριθμός\n";
        }
        if (errors.length() > 0) {
            MessageOKCancel m = new MessageOKCancel(errors, "ERRORS");
            return false;
        }
        return true;
    }

    //Κλήση για τα ονόματα των πλοίων...
    public void Shipname() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/application_orf",

                        "rafailORF", "javauser1234")) {

            ComboBox<String> test = new ComboBox<>();

            String queryGRC = "SELECT shipname \n" +
                    "FROM ship \n" +
                    "WHERE country = 'GRC'; " ;

            String queryTUR = "SELECT shipname\n" +
                    "FROM ship\n" +
                    "WHERE country = 'TUR'; " ;

            Statement stmt = conn.createStatement();
            ResultSet resultGRC = stmt.executeQuery(queryGRC);
            while (resultGRC.next()) {
                comShipsGRC.getItems().add(resultGRC.getString("shipname"));
            }

            ResultSet resultTUR = stmt.executeQuery(queryTUR);
            while (resultTUR.next()) {
                comShipsTUR.getItems().add(resultTUR.getString("shipname"));
            }
            resultTUR.close();
            resultGRC.close();

        } catch (SQLException b) {
            System.err.println(b);
        }
    }


    //Κλήση για να πάρω το Sid...
    public String sidShip() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/application_orf",

                        "rafailORF", "javauser1234")) {
            String sidQuery = "SELECT sid \n" +
                    "from ship\n" +
                    "where shipname = ? ";
            PreparedStatement stmt = conn.prepareStatement(sidQuery);
            stmt.setString(1, comShipsGRC.getSelectionModel().getSelectedItem());
            ResultSet result = stmt.executeQuery();
            result.next();
            String sidship = result.getString(1);
            return sidship;

        } catch (SQLException b) {
            return "error";
        }
    }

    //Τελευταία και καλή κλήση για να πάρω τα ονόματα!!
    public void outputData() {
        try (
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/application_orf",
                        "rafailORF", "javauser1234")) {
          String zoom = chozoom.getSelectionModel().getSelectedItem();
          String table = "datalow";

            if (zoom == "M") {
                table = "datamed";
            } else if (zoom== "H") {
                table= "datahigh";
            } else table = "datalow";

            String datQuery = "SELECT Distance \n" +
                    "FROM " + table + " \n" +
                    "where Perlines = ? and sid=?";

            PreparedStatement stmt2 = conn.prepareStatement(datQuery);
            stmt2.setString(1, txtPerLines.getText());
            stmt2.setString(2, sidShip());

            ResultSet dataResult = stmt2.executeQuery();
            dataResult.next();

            txtresults.setText(dataResult.getString(1));

        } catch (SQLException b) {
            System.err.println(b);
        }
    }
}
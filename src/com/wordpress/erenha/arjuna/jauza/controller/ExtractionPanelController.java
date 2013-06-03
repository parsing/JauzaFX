/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wordpress.erenha.arjuna.jauza.controller;

import com.wordpress.erenha.arjuna.jauza.model.CurrentSelection;
import com.wordpress.erenha.arjuna.jauza.model.Individual;
import com.wordpress.erenha.arjuna.jauza.model.IndividualDetail;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Hindarwan
 */
public class ExtractionPanelController implements Initializable {

    @FXML //  fx:id="current"
    private TableView<CurrentSelection> current; // Value injected by FXMLLoader
    @FXML //  fx:id="currentContent"
    private TableColumn<CurrentSelection, String> currentContent; // Value injected by FXMLLoader
    @FXML //  fx:id="currentID"
    private TableColumn<CurrentSelection, String> currentID; // Value injected by FXMLLoader
    @FXML //  fx:id="inv"
    private TableView<Individual> inv; // Value injected by FXMLLoader
    @FXML //  fx:id="invClass"
    private TableColumn<Individual, String> invClass; // Value injected by FXMLLoader
    @FXML //  fx:id="invDetails"
    private TableView<IndividualDetail> invDetails; // Value injected by FXMLLoader
    @FXML //  fx:id="invDetailsID"
    private TableColumn<IndividualDetail, String> invDetailsID; // Value injected by FXMLLoader
    @FXML //  fx:id="invDetailsProperty"
    private TableColumn<IndividualDetail, String> invDetailsProperty; // Value injected by FXMLLoader
    @FXML //  fx:id="invDetailsValue"
    private TableColumn<IndividualDetail, String> invDetailsValue; // Value injected by FXMLLoader
    @FXML //  fx:id="invID"
    private TableColumn<Individual, String> invID; // Value injected by FXMLLoader
    @FXML //  fx:id="invSelected"
    private TableColumn<Individual, Boolean> invSelected; // Value injected by FXMLLoader
    private MainController mainController;
    //tabel model
//    private ObservableList<CurrentSelection> currentSelections;
    private ObservableList<Individual> individuals;
    private ObservableList<IndividualDetail> individualDetails;
    //Class
    private ObservableList<String> classes = FXCollections.observableArrayList("Dataset", "Catalog", "Distribution", "Observation");
    private ObservableList<String> properties = FXCollections.observableArrayList("provinsi", "tahun", "jenisKelamin", "persentasePenduduk");
    private File file;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // table
        initCurrent();
        initInv();
        initInvDetails();
        // table model
        initModel();

    }

    public TableView<CurrentSelection> getCurrent() {
        return current;
    }

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void initModel() {
        individuals = FXCollections.observableList(new ArrayList<Individual>());
        inv.setItems(individuals);
        individualDetails = FXCollections.observableList(new ArrayList<IndividualDetail>());
        invDetails.setItems(individualDetails);
        inv.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Individual>() {
            @Override
            public void changed(ObservableValue<? extends Individual> ov, Individual t, Individual t1) {
                individualDetails.clear();
                individualDetails.addAll(t1.getIndividualDetail());
            }
        });

    }

    private void initCurrent() {
        currentID.setCellValueFactory(new PropertyValueFactory("id"));
        currentContent.setCellValueFactory(new PropertyValueFactory("content"));
    }

    private void initInv() {
        invID.setCellValueFactory(new PropertyValueFactory("id"));
        invClass.setCellValueFactory(new PropertyValueFactory("typeOf"));
        invClass.setCellFactory(new Callback<TableColumn<Individual, String>, TableCell<Individual, String>>() {
            @Override
            public TableCell<Individual, String> call(TableColumn<Individual, String> p) {
                TableCell<Individual, String> cell = new ComboBoxTableCell<>(classes);
                return cell;
            }
        });
        invSelected.setCellValueFactory(new PropertyValueFactory("selected"));
        invSelected.setCellFactory(CheckBoxTableCell.forTableColumn(invSelected));
    }

    private void initInvDetails() {
        invDetailsID.setCellValueFactory(new PropertyValueFactory("id"));
        invDetailsProperty.setCellValueFactory(new PropertyValueFactory("property"));
        invDetailsValue.setCellValueFactory(new PropertyValueFactory("value"));
        invDetailsProperty.setCellFactory(new Callback<TableColumn<IndividualDetail, String>, TableCell<IndividualDetail, String>>() {
            @Override
            public TableCell<IndividualDetail, String> call(TableColumn<IndividualDetail, String> p) {
                TableCell<IndividualDetail, String> cell = new ComboBoxTableCell<>(properties);
                return cell;
            }
        });
    }

    @FXML
    public void importOntology(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("OWL files ", "*.rdf", "*.ttl", "*.n3", "*.owl", "*.nt");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        file = fileChooser.showOpenDialog(null);

        System.out.println(file.getPath());
    }

    @FXML
    public void createInvAction(ActionEvent event) {
        int i = individuals.size();
        List<IndividualDetail> l = new ArrayList<>();
        for (CurrentSelection currentSelection : mainController.getCurrentSelections()) {
            l.add(new IndividualDetail(currentSelection.getId(), currentSelection.getContent(), ""));
        }
        individuals.add(new Individual("individual" + i, "", l));
    }
    private boolean step = false;
    private List<Integer> step1;

    @FXML
    public void suggestNextAction(ActionEvent event) {
        if (!step) {
            step1 = getStep();
        }
        List<IndividualDetail> individualDetail = individuals.get(individuals.size() - 1).getIndividualDetail();
        for (int i = 0; i < step1.size(); i++) {
//            selectElementByJFXID(step1.get(i) + Integer.valueOf(individualDetail.get(i).getId()));
        }
//        getSelectedElement();
        createInvAction(event);
    }

    private List<Integer> getStep() {
        List<Integer> step = new ArrayList<>();
        List<Integer> id1 = new ArrayList<>();
        List<Integer> id2 = new ArrayList<>();

        boolean sufficient = false;
        for (int i = 0; i < individuals.size(); i++) {
            if (individuals.get(i).isSelected()) {
                List<IndividualDetail> individualDetail = individuals.get(i).getIndividualDetail();
                for (IndividualDetail individualDetail1 : individualDetail) {
                    if (!sufficient) {
                        id1.add(Integer.valueOf(individualDetail1.getId()));
                    } else {
                        id2.add(Integer.valueOf(individualDetail1.getId()));
                    }
                }
                sufficient = true;
            }
        }
        for (int i = 0; i < id1.size(); i++) {
            step.add(id2.get(i) - id1.get(i));
        }
        this.step = true;
        return step;
    }
}

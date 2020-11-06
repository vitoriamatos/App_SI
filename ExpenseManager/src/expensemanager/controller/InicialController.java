/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package expensemanager.controller;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import expensemanager.entities.AnaliseVariables;
import expensemanager.entities.Values;

import expensemanager.utils.CurrencyField;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import expensemanager.utils.Utils;


public class InicialController implements Initializable {

    private final Utils utils = new Utils();

    private String[] listFixed = {"Terreno","Instalações", "Maquinária de Produção", "Equipamentos de escritório", "Água, energia, etc", "Seguro", "Total"};
    private String[] valueFixed = {"$42500.00", "$332500.00", "$532000.00","$212800.00","$30500.00","$99700.00", "$1250000.00"};


    private String[] listVariable = {"Mão-de-obra", "Propaganda", "Expedição e recebimento", "Total"};
    private String[] valueVariable= {"$15.00", "$1.00", "$5.00", "$21.00"};

   private double [] custos = {12.0,13.0,14.0,15.0,16.0,17.0};
   private double [] faixa = {90000,100000,110000};


    double [] variable = new double[7];
    double [] fixed = {42500.00, 332500.00, 532000.00, 212800.00, 30500.00,99700.00, 1250000.00};


    @FXML
    private Label pontoEquilibrio;
    @FXML
    private Label custoFixo;

    @FXML
    private Label custoVariaveis;

    @FXML
    private Label precoVenda;

    @FXML
    private Label margemContribuicao;




    @FXML
    private Pane resultPane;
    @FXML
    private TableView<Values> tableFixa;
    @FXML
    private TableView<Values> tableVariavel;

    @FXML
    private TableView<AnaliseVariables>table_faixa_data;


    @FXML
    private TableColumn<Values,String> categoriaFixa;

    @FXML
    private TableColumn<Values, String> valueFixa;

    @FXML
    private TableColumn<Values,String> categoriaVariavel;

    @FXML
    private TableColumn<Values,String> valueVariavel;

    @FXML
    private TextField precoProduto;


    @FXML
    private TableColumn<AnaliseVariables, String> table_faixa;

    @FXML
    private TableColumn<AnaliseVariables, String> table_v1;

    @FXML
    private TableColumn<AnaliseVariables, String> table_v2;

    @FXML
    private TableColumn<AnaliseVariables, String> table_v3;

    @FXML
    private TableColumn<AnaliseVariables, String> table_v4;

    @FXML
    private TableColumn<AnaliseVariables, String> table_v5;

    @FXML
    private TableColumn<AnaliseVariables, AnaliseVariables> table_v6;

    @FXML
    private Label alert;

    @FXML
    private void inputField() {

    }

    @FXML
    void calcule(ActionEvent event) {

        if(precoProduto.getText().toString()== "" || precoProduto.getText().isEmpty()){
            alert.setVisible(true);
        }else {
            alert.setVisible(false);
            DecimalFormat decimal = new DecimalFormat("0.00");
            resultPane.setVisible(true);
            pontoEquilibrio.setText(String.valueOf(pontodeEquilibrio()));
            margemContribuicao.setText("$" + decimal.format(margemContribuicao()));
            custoFixo.setText("$" + decimal.format(calcularCustoFixo()));
            custoVariaveis.setText("$" + decimal.format(calcularCustoVariavel()));
            precoVenda.setText("$" + decimal.format(Double.parseDouble(precoProduto.getText())));
            loadDataResult(Double.parseDouble(precoProduto.getText()));
            initColResult();

        }


    }
    @FXML
    void back(ActionEvent event) {
        resultPane.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTable();
        loadData();

    }


    @FXML
    void maximize(ActionEvent event) {
        utils.maximize(event);
    }

    @FXML
    void minimize(ActionEvent event) {
        utils.minimize(event);
    }

    @FXML
    void close() throws IOException {
        utils.close();
    }


    public void initTable(){
    initCol();
    }

    public void initCol(){
        categoriaFixa.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valueFixa.setCellValueFactory(new PropertyValueFactory<>("valor"));

        categoriaVariavel.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        valueVariavel.setCellValueFactory(new PropertyValueFactory<>("valor"));


        editableColumsFixed();
    }

    public void initColResult(){
       table_faixa.setCellValueFactory(new PropertyValueFactory<>("faixa"));
       table_v1.setCellValueFactory(new PropertyValueFactory<>("v1"));
       table_v2.setCellValueFactory(new PropertyValueFactory<>("v2"));
       table_v3.setCellValueFactory(new PropertyValueFactory<>("v3"));
       table_v4.setCellValueFactory(new PropertyValueFactory<>("v4"));
       table_v5.setCellValueFactory(new PropertyValueFactory<>("v5"));
       table_v6.setCellValueFactory(new PropertyValueFactory<>("v6"));
    }
    private void editableColumsFixed(){
        categoriaFixa.setCellFactory(TextFieldTableCell.forTableColumn());
        categoriaFixa.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setValor(e.getNewValue());
        });

        valueFixa.setCellFactory(TextFieldTableCell.forTableColumn());
        valueFixa.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setValor(e.getNewValue());
        });



        categoriaVariavel.setCellFactory(TextFieldTableCell.forTableColumn());
        categoriaVariavel.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setValor(e.getNewValue());
        });

        valueVariavel.setCellFactory(TextFieldTableCell.forTableColumn());
        valueVariavel.setOnEditCommit(e->{
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setValor(e.getNewValue());
        });
        tableVariavel.setEditable(true);

    }




    private void loadData(){


        ObservableList<Values> data_table_f = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v = FXCollections.observableArrayList();


        for(int i =0; i<7;i++){
            data_table_f.add(new Values(String.valueOf(listFixed[i]), String.valueOf(valueFixed[i])));
            //fized[i] = Double.parseDouble(valueFixed[i].substring (1));
        }

        for(int j =0; j<4;j++){
            data_table_v.add(new Values(String.valueOf(listVariable[j]), String.valueOf( valueVariable[j])));
           // variable[j] = Double.parseDouble(valueVariable[j].substring (1));
        }

        tableFixa.setItems(data_table_f);
        tableVariavel.setItems(data_table_v);

    }


    private void loadDataResult(double preco){

        ObservableList<AnaliseVariables> data_table_faixa = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v1 = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v2 = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v3 = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v4 = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v5 = FXCollections.observableArrayList();
        ObservableList<Values> data_table_v6 = FXCollections.observableArrayList();

        for(int i =0; i<3;i++){
            data_table_faixa.add(new AnaliseVariables("$"+String.valueOf(faixa[i]), String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[0]+6)))),
                    String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[1]+6)))),String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[2]+6)))),
                    String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[3]+6)))), String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[4]+6)))),
                    String.valueOf(Math.ceil((calcularPontoEquilibrioVariavel()+faixa[i])/(preco-(custos[5]+6))))));
        }
        table_faixa_data.setItems(data_table_faixa);

    }

    public double calcularPontoEquilibrioVariavel(){
        double cont = 0;


        for(int i=0; i<=4; i++){
            cont += fixed[i];
        }
         return Math.ceil(cont);
    }



    public double calcularCustoFixo(){
        double cont = 0;
        String custo;
        for(int i=0; i<6; i++) {
            cont += Double.parseDouble(valueFixed[i].substring(1));
        }

            return cont;


    }


    public double calcularCustoVariavel(){

        double cont = 0;
        double custo;
        for(int i=0; i<3; i++) {
            cont += Double.parseDouble(valueVariable[i].substring(1));

        }

        return cont;
    }



  public double margemContribuicao(){
      DecimalFormat decimal = new DecimalFormat( "0.00" );
      double margem = Double.parseDouble(precoProduto.getText())-calcularCustoVariavel();

      return margem;
    }


    public double pontodeEquilibrio(){

        double margem = calcularCustoFixo()/margemContribuicao();


        return Math.ceil(margem);
    }






}

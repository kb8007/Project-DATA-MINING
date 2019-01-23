/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dmproject;

import com.sun.jmx.remote.util.OrderClassLoaders;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
//666666666666666
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.OrdinalToNumeric;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.gui.beans.DataSetEvent;


public class FXMLDocumentController implements Initializable {
    
    @FXML private Button B_openFile;
    @FXML private Button B_startapriori;
    @FXML private Button B_startknn;
    @FXML private Button B_missingvalues;
    @FXML private Button B_puredata;
    @FXML private Button B_codification;
    @FXML private Button B_normalisation;
    @FXML private Button B_startdbscan;
    @FXML private Label L_relation;
    @FXML private Label L_sym;
    @FXML private Label L_errorratio;
    @FXML private Label L_instances;
    @FXML private Label L_attributes;
    @FXML private Label L_name;
    @FXML private Label L_missing;
    @FXML private Label L_distinct;
    @FXML private Label L_type;
    @FXML private Label L_unique;
    @FXML private TextArea TA_content;
    @FXML private TextArea TA_content1;
    @FXML private TextArea TA_content2;
    @FXML private TextArea TA_content3;
    @FXML private TableView T_attributes;
    @FXML private TextField TF_minsupport;
    @FXML private TextField TF_minconfidence;
    @FXML private TextField TF_modelsize;
    @FXML private TextField TF_k;
    @FXML private TextField TF_minpoints;
    @FXML private TextField TF_radius;
    @FXML private TextArea TA_resultdbscan;
    @FXML private TextArea TA_items;
    @FXML private TextArea TA_model;
    @FXML private TextArea TA_train;
    @FXML private TextArea TA_intraclass;
    @FXML private TextArea TA_interclass;
    @FXML private TextArea TA_class;
    @FXML private TextArea TA_transactions;
    @FXML private TextArea TA_freqitems;
    @FXML private TextArea TA_associationrules;
    @FXML private Label L_nbitems;
    @FXML private Label L_timeapriori;
    @FXML private Label L_timeknn;
    @FXML private Label L_timedbscan;
    @FXML private Label L_nbtransactions;
    @FXML private TableView T_data;
    @FXML private Tab P_apriori;
    @FXML private Tab P_knn;
    @FXML private Tab P_filecontent;
    @FXML private Tab P_dbscan;
    
    
    Vector<String> candidates=new Vector<String>();
    List<String> itemSet = new ArrayList<String>();
    List<String> finalFrequentItemSet = new ArrayList<>();
    HashMap<String,Integer> frequentItems = new HashMap<String, Integer>();
    String newLine = System.getProperty("line.separator");
    int itemsCount,countItemOccurrence=0,displayFrequentItemSetNumber=2,displayTransactionNumber=1;
    List<String> data;

//mytable ------------------------------------------
    @FXML private TableView dataset= new TableView<>();
//----------------------------------------------------------------
    @FXML private TableView T_selectedAttribite;
    @FXML private TitledPane P_histogram;
    private Instances instances,ins,ins_pure,ins_missing,ins_codification,ins_normalisation;
    private ArrayList<Statistic_t> list_statistic;
    private ArrayList<Nominal_t> list_nominal;
    Vector<Instance> VisitList = new Vector<Instance>();
    public Vector<List> resultList = new Vector<List>();
    public Vector<List> trl = new Vector<List>();
    public Vector<Instance> pointList ;		 	    
    public Vector<Instance> Neighbours ;
    Vector<Instance> hset = new Vector<Instance>();
    int minpoints;
    double radius;


    // my functions ----------------------------------------------------
        public void fillTableView(Instances data){
            
        ArrayList<TableColumn<Instance, String>> atrributes = new ArrayList<>();
        ArrayList <Instance> instances = new ArrayList<>();
        int i=0;
        for ( i =0;i<data.size();i++)
        {
            instances.add(data.get(i));

        }
        ObservableList<Instance> tableContent = FXCollections.observableArrayList(instances);


        for ( i = 0; i < data.numAttributes(); i++) {
            TableColumn<Instance, String> column = new TableColumn<Instance,String>(data.attribute(i).name());
            final int attIndex = i ;
            column.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().toString(attIndex)));
            atrributes.add(column);

        }
        dataset.getColumns().clear();
        dataset.getColumns().addAll(atrributes);
        dataset.setItems(tableContent);
        dataset.setVisible(true);

    }
        //-----------------------------
     
     @FXML
     public void  fillPureData(MouseEvent e ) 
     {
         fillTableView(ins_pure);
         B_puredata.setDisable(true);
            B_missingvalues.setDisable(false);
            B_codification.setDisable(false);
            B_normalisation.setDisable(false);
     }
     
     @FXML
     public void  fillMissingValues(MouseEvent e ) 
     {
         fillTableView(ins_missing);
         B_puredata.setDisable(false);
            B_missingvalues.setDisable(true);
            B_codification.setDisable(false);
            B_normalisation.setDisable(false);
     }
     
     @FXML
     public void  fillCodification(MouseEvent e ) 
     {
         fillTableView(ins_codification);
         B_puredata.setDisable(false);
            B_missingvalues.setDisable(false);
            B_codification.setDisable(true);
            B_normalisation.setDisable(false);
     }
     
     @FXML
     public void  fillNormalisation(MouseEvent e ) 
     {
         fillTableView(ins_normalisation);
         B_puredata.setDisable(false);
            B_missingvalues.setDisable(false);
            B_codification.setDisable(false);
            B_normalisation.setDisable(true);
     }
//----------------------
     @FXML
     public void Normalisation(){
         int i,j;
         double max_values[] = new double[ins_normalisation.numAttributes()-1];
         
         for(i = 0 ; i < max_values.length ; i++)
             max_values[i] = 0;
         
         for( i = 0; i < ins_normalisation.numInstances() ; i++){
             for(j = 0 ; j < ins_normalisation.instance(i).numAttributes() - 1 ; j++){
                 if(max_values[j] < ins_normalisation.instance(i).value(j))
                     max_values[j] = ins_normalisation.instance(i).value(j);
             }
         }
         for(i = 0; i < ins_normalisation.numInstances() ; i++){
             for(j = 0; j < max_values.length ; j++){
                 ins_normalisation.instance(i).setValue(j,ins_normalisation.instance(i).value(j)/max_values[j]);
             }
         }
     }
     
    
        //
   /* public void normalisation(){
        for (int i = 0; i <get_number_attribute() ; i++) {
            if(Data.attribute(i).isNumeric()){
                double[] Vals=get_Instance(i);
                double min=get_Min(i);
                double max=get_Max(i);
                for (int j = 0; j <Vals.length ; j++) {
                    double calc=(Vals[j]-min)/(max-min);
                    Data.instance(j).setValue(i,calc);
                }
            }

        }
    }
        */

//-------------------------



    public void openFileAction(MouseEvent event) throws Exception {
        if(event.getClickCount() == 1){
            
            L_sym.setText("---------------");
            FileChooser choixFich = new FileChooser();
            choixFich.setTitle("Select a dataSet");
            ExtensionFilter extension = new ExtensionFilter("fichier dataSet", "*.arff");
            choixFich.getExtensionFilters().add(extension);

            File file = new File("./data");
            
            choixFich.setInitialDirectory(file);
            file = choixFich.showOpenDialog(new Stage());
            String path = file.getAbsolutePath();
          
            
// loading data  
            

            DataSource source = new DataSource(path);
            instances = source.getDataSet();
            ins = source.getDataSet();
            ins_pure = source.getDataSet();
            fillTableView(ins_pure);
            B_puredata.setDisable(true);
            B_missingvalues.setDisable(false);
            B_codification.setDisable(false);
            ins_missing = source.getDataSet();
            ins_codification = source.getDataSet();
            ins_normalisation = source.getDataSet();
            String s=instances.toString();
            
            P_apriori.setDisable(false);
            P_knn.setDisable(false);
            P_filecontent.setDisable(false);
            P_dbscan.setDisable(false);
           
            

// zeyadaaaaaaaaaaaaaaa
BufferedWriter writer = new BufferedWriter(new FileWriter("t.arff"));
writer.write(s);

writer.close();
//-----------------------------------------



// wrting data 
            
  
   

int ms=0;

 for (int ii=0;ii<s.length();ii++)
 {
     if((s.charAt(ii)=='?') )
     {
         ms++;
     }
     
 }

// Redo it please  
ReplaceMissingValues fix=new ReplaceMissingValues();
            fix.setInputFormat(instances);
            instances=Filter.useFilter(instances, fix);
            fix.setInputFormat(ins_missing);
            ins_missing=Filter.useFilter(ins_missing, fix);
            fix.setInputFormat(ins_codification);
            ins_codification=Filter.useFilter(ins_codification, fix);
            fix.setInputFormat(ins_normalisation);
            ins_normalisation=Filter.useFilter(ins_normalisation, fix);
            
            PrintWriter output1 = new PrintWriter("outputForApriori.txt", "UTF-8");
            for(Instance i : ins_missing){
                output1.println(i.toString().replace(",", " "));
            }
            output1.close();
            
            s=instances.toString();
           // TA_content1.setText(s);


   ReplaceMissingValues ffix=new ReplaceMissingValues();
            ffix.setInputFormat(ins);
            ins=Filter.useFilter(ins, ffix);
//codification  redo it please-------------------------------------------
            OrdinalToNumeric a=new OrdinalToNumeric();
		a.setInputFormat(ins);
		ins=Filter.useFilter(ins, a);
                a.setInputFormat(ins_codification);
		ins_codification=Filter.useFilter(ins_codification, a);
                a.setInputFormat(ins_normalisation);
                ins_normalisation=Filter.useFilter(ins_normalisation, a);
                Normalisation();
                
                PrintWriter output2 = new PrintWriter("outputForKNN.txt", "UTF-8");
            for(Instance i : ins_normalisation){
                output2.println(i.toString().replace(",", " "));
            }
            output2.close();
                
                s=ins.toString();

                
            
            //TA_content2.setText(s);
           
     /* for (int i = 0; i <ins.numAttributes() ; i++) {
            if(ins.attribute(i).isNumeric()){
                double[] Vals=get_Instance(i);
                double min=get_Min(i);
                double max=get_Max(i);
                for (int j = 0; j <Vals.length ; j++) {
                    double calc=(Vals[j]-min)/(max-min);
                    Data.instance(j).setValue(i,calc);
                }
            }

        }
    */

L_missing.setText(" "+ms);

String ss="";            
            // remplissage des lable 
            
            L_relation.setText(instances.relationName());
            L_instances.setText(String.valueOf(instances.numInstances()));
            L_attributes.setText(String.valueOf(instances.numAttributes()));
 //remplisage de tableux 
 
            TableColumn col_no = new TableColumn("id");
            TableColumn col_name = new TableColumn("Name");
            
            T_attributes.getColumns().clear();
            T_attributes.getColumns().addAll(col_no,col_name);
        
            ArrayList<Attribute_t> list_attribute = new ArrayList<>();
          
//aficher les valeur sur la table t_att.............
        
        for(int i = 0 ; i < instances.numAttributes() ; i++)
        {  
           list_attribute.add(new Attribute_t(Integer.toString(i+1),instances.attribute(i).name()));
        }
            
            
            //***************tableu avant  et aprés our les donnés avec button et na7i text area 
             
            int i=0;
           
            
 //************** table max min q ...............     
            ObservableList<Attribute_t> data1 = FXCollections.observableArrayList(list_attribute);
        
            col_no.setCellValueFactory(new PropertyValueFactory<Attribute_t,String>("id"));
            col_name.setCellValueFactory(new PropertyValueFactory<Attribute_t,String>("name"));
        
            T_attributes.setItems(data1);
            T_attributes.getSelectionModel().select(0);
            L_name.setText(((Attribute_t)T_attributes.getSelectionModel().getSelectedItem()).getName());
            if(instances.attribute(((Attribute_t)T_attributes.getSelectionModel().getSelectedItem()).getName()).isNominal())
                L_type.setText("Nominal");
            
            if(instances.attribute(((Attribute_t)T_attributes.getSelectionModel().getSelectedItem()).getName()).isNumeric())
                L_type.setText("Numeric");
        
            if(instances.attribute(T_attributes.getSelectionModel().getSelectedIndex()).isNumeric())
            {setData2_Numeric();
            }
            if(instances.attribute(T_attributes.getSelectionModel().getSelectedIndex()).isNominal())
            {
                setData2_Nominal(T_attributes.getSelectionModel().getSelectedIndex());
                drawHistogram();
            }
        }
    }
    
    @FXML
    private void setData2_Numeric(){
        TableColumn col_statistic = new TableColumn("statistic");
        TableColumn col_value = new TableColumn("Value");
        T_selectedAttribite.getColumns().clear();
        T_selectedAttribite.getColumns().addAll(col_statistic,col_value);
        list_statistic = new ArrayList<>();
        MinMax(T_attributes.getSelectionModel().getSelectedIndex());
        Median_Q1_Q3(T_attributes.getSelectionModel().getSelectedIndex());
        mode(T_attributes.getSelectionModel().getSelectedIndex());
        moy(T_attributes.getSelectionModel().getSelectedIndex());
        
        ObservableList<Statistic_t> data2 = FXCollections.observableArrayList(list_statistic);
        col_statistic.setCellValueFactory(new PropertyValueFactory<Statistic_t,String>("name"));
        col_value.setCellValueFactory(new PropertyValueFactory<Statistic_t,String>("value"));
        
        T_selectedAttribite.setItems(data2);
    }
    
    @FXML
    public void setData2_Nominal(int selectedAttribute){
        if(instances.attribute(selectedAttribute).isNominal()){  
            list_nominal = new ArrayList<>();
            ArrayList<String> list_label  = new ArrayList<>();
            ArrayList<Integer> list_count  = new ArrayList<>();
            ArrayList<String> list_t;
            int code=1;
            Enumeration<Object> E=instances.attribute(selectedAttribute).enumerateValues();
            while(E.hasMoreElements())
                list_label.add((String)E.nextElement());
            
            for(int i=0;i<list_label.size();i++)
                list_count.add(0);
            
            for(int j=0;j<instances.numInstances();j++){
                String value_t = instances.instance(j).stringValue(selectedAttribute);
                for(int k=0;k<list_label.size();k++){
                    if(list_label.get(k) == value_t){
                        list_count.add(k,list_count.get( k)+1);
                    
                    }
                }
            }
            
            int distinct = 0;
            for(int v=0;v<list_label.size();v++)
            {
                list_nominal.add(new Nominal_t(v+1,list_label.get(v),list_count.get(v)));
                if(list_count.get(v) > 0)
                    distinct++;
            
            }
            
            L_distinct.setText(String.valueOf(distinct));
            
                
            TableColumn col_id = new TableColumn("Id");
            TableColumn col_label = new TableColumn("Label");
            TableColumn col_count = new TableColumn("Count");
            T_selectedAttribite.getColumns().clear();
            T_selectedAttribite.getColumns().addAll(col_id,col_label,col_count);
            ObservableList<Nominal_t> data2 = FXCollections.observableArrayList(list_nominal);
            col_id.setCellValueFactory(new PropertyValueFactory<Nominal_t,String>("id"));
            col_label.setCellValueFactory(new PropertyValueFactory<Nominal_t,String>("label"));
            col_count.setCellValueFactory(new PropertyValueFactory<Nominal_t,String>("count"));
        
            T_selectedAttribite.setItems(data2);
        }
    }
    
    @FXML
    public void mode (int selactedAttribute)
    {
        if(instances.attribute(selactedAttribute).isNumeric())
        {
		double [] vecteur;
		vecteur = new double [instances.numInstances()];
		for(int j=0;j<instances.numInstances();j++){
                    vecteur[j]=instances.instance(j).value(selactedAttribute);
		}
  list_statistic.add(new Statistic_t("Mode ", (float) getMode(vecteur,instances.numInstances())));
            
      }
    }
    
    
     public void moy (int selactedAttribute)
    {
        if(instances.attribute(selactedAttribute).isNumeric())
        {
		double [] vecteur;
		vecteur = new double [instances.numInstances()];
		for(int j=0;j<instances.numInstances();j++){
                    vecteur[j]=instances.instance(j).value(selactedAttribute);
		}
  list_statistic.add(new Statistic_t("Mean", (float) getMoy(vecteur,instances.numInstances())));
               
      }
    }
    
    @FXML
    public double getQ1 (double [] vecteur, int taille)
    {
        return vecteur[Math.round(taille/4)];
    }
    
    @FXML
    public double getQ3 (double [] vecteur, int taille)
    {
        return vecteur[Math.round(taille*3/4)];
    }
    
    @FXML
    public double getMedian (double [] vecteur, int taille){
        return vecteur[Math.round(taille/2)];
    }
 
    @FXML
    float t;
    public void Median_Q1_Q3 (int selectedAttribute){
        if(instances.attribute(selectedAttribute).isNumeric()){
            double [] vecteur;
            vecteur = new double [instances.numInstances()];
            for(int j=0;j<instances.numInstances();j++){
                vecteur[j]=instances.instance(j).value(selectedAttribute);
            }
            vecteur = trier(vecteur,instances.numInstances());           
            list_statistic.add(new Statistic_t("Median",(float) getMedian(vecteur,instances.numInstances())));
            list_statistic.add(new Statistic_t("Q1", (float) getQ1(vecteur,instances.numInstances())));
            list_statistic.add(new Statistic_t("Q3", (float) getQ3(vecteur,instances.numInstances())));
	}
    }
   
    
    @FXML
    public double[] trier (double [] vecteur, int taille){
	
	for(int tailleMax=taille;tailleMax>0;tailleMax--){
            for(int i=1;i<tailleMax;i++){
		if(vecteur[i-1]>vecteur[i]){
                    double stock=vecteur[i-1];
                    vecteur[i-1]= vecteur[i];
                    vecteur[i]=stock;
		}
            }
	}
        return vecteur;
    }
    
    @FXML
    public double getMode(double [] vecteur, int taille){
	LinkedHashMap<Double, Integer> tabFreq = new LinkedHashMap<Double,Integer>();
	tabFreq.clear();
	Vector<Double> PossibleVals = new Vector<Double>();
	PossibleVals.clear();
	for(int i =0;i<taille;i++){
            if(!PossibleVals.contains(vecteur[i])){
		PossibleVals.add(vecteur[i]);
            }
	}
	for(int i=0; i<taille;i++){
            if(!tabFreq.containsKey(vecteur[i])){
		tabFreq.put(vecteur[i], 1);
            }else{
		tabFreq.replace(vecteur[i], tabFreq.get(vecteur[i])+1);
            }
	}
        
	double indMax=PossibleVals.get(0);
	for(int i=1;i<PossibleVals.size();i++){
            if(tabFreq.get(indMax)<tabFreq.get(PossibleVals.get(i)))
		indMax = PossibleVals.get(i);
            }
	return indMax;
    }
    
    
    public double getMoy(double [] vecteur, int taille){
	
        double sum =0;
	for(int i=0; i<taille;i++){
            sum+=vecteur[i];
	}
	
	return sum/taille;
    }
    
    
    @FXML
    public void MinMax(int i){
	if(instances.attribute(i).isNumeric()){
            float min = (float) instances.instance(0).value(i);
            float max = (float) instances.instance(0).value(i);

            for(int j=1;j<instances.numInstances();j++){
		if(min > (float)instances.instance(j).value(i))
                    min = (float)instances.instance(j).value(i);
		if(max < (float)instances.instance(j).value(i))
                    max = (float)instances.instance(j).value(i);
            }
            list_statistic.add(new Statistic_t("Maximum",max));
            list_statistic.add(new Statistic_t("Minimum",min));
            list_statistic.add(new Statistic_t("MidRange",((max+min)/2)));
        }
    }
    
    
    
    @FXML
    public void drawHistogram(){
     
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
        
        bc.setTitle(instances.attribute(T_attributes.getSelectionModel().getSelectedIndex()).name());
        xAxis.setLabel("Label");       
        yAxis.setLabel("Count");
 
        XYChart.Series series1 = new XYChart.Series();
   for(int i=0;i<list_nominal.size();i++)
            series1.getData().add(new XYChart.Data(list_nominal.get(i).getLabel(),list_nominal.get(i).getCount()));

        bc.getData().addAll(series1);
        P_histogram.setContent(bc);           
    }
    @FXML
    void draw (){BoxAndWhiskerPlot boxplots = new BoxAndWhiskerPlot("Boxplots", ins_normalisation);
		boxplots.render();
     }
    
    
    @FXML
    public void getSelectedAttribute(MouseEvent event){
        if(event.getClickCount() == 1){ 
            String selectedAttribute = ((Attribute_t)T_attributes.getSelectionModel().getSelectedItem()).getName();
            L_name.setText(selectedAttribute);
            if(instances.attribute(selectedAttribute).isNominal()){
                L_type.setText("Nominal");
                setData2_Nominal(T_attributes.getSelectionModel().getSelectedIndex());
                
                drawHistogram();
            }
            
            if(instances.attribute(selectedAttribute).isNumeric()){
                          sym(T_attributes.getSelectionModel().getSelectedIndex());

                L_type.setText("Numeric");
                setData2_Numeric();
            sym(T_attributes.getSelectionModel().getSelectedIndex());
            }
        }
    
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    private boolean isnumrical() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
void miss(){
try{
    String s="";
BufferedWriter wr;
    wr = new BufferedWriter(new FileWriter("tt.arff"));
wr.write("");
 double[] vecteur = null;
 for (int ii=0;ii<s.length();ii++)
 {
     if(this.isnumrical())
  //   {
      if(s.charAt(ii)=='?')
      {
          wr.append(""+getMoy(vecteur, ii));
      }
      else{   
        
  //mode
             wr.append(getMode(vecteur, ii)+"");
  }
    
     
     }
  
     }catch (Exception e){
        }
        }



	public void codification(){
		for(int i =0 ;i<instances.numAttributes();i++){
			if(instances.attribute(i).isNominal()){
				int code=1;
				Enumeration<Object> E=instances.attribute(i).enumerateValues();
				while(E.hasMoreElements()){
					String oneValue = (String)E.nextElement();
					for(int j=0;j<instances.size();j++){
						if(instances.instance(j).stringValue(i).contentEquals(oneValue)){
							instances.attribute(i).addStringValue(Integer.toString(code));
							instances.instance(j).setValue(i, Integer.toString(code));
						}
					}
					code++;
				}

			}
		}
	}



 public void sym(int selactedAttribute)
 {
        int a=0,b=0,c=0;
        
                if(ins.attribute(selactedAttribute).isNumeric())
                {
                    double [] vecteur;
		vecteur = new double [instances.numInstances()];
		for(int j=0;j<instances.numInstances();j++){
                    vecteur[j]=instances.instance(j).value(selactedAttribute);
               }
               a=(int) getMoy(vecteur,instances.numInstances());
               b=(int) getMedian(vecteur,instances.numInstances());
               c=(int) getMode(vecteur,instances.numInstances());
     
               if((a-c)==(3*(c-b)))
               {L_sym.setText("Symetric");}
               else 
               {L_sym.setText("Asymétrique");}
 }
               }
              
    @FXML
   public void AprioriAlgorithm(MouseEvent event){
       if(event.getClickCount() == 1){
           long start  = System.currentTimeMillis();
           BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		countItemOccurrence=0;
                displayFrequentItemSetNumber=2;
                displayTransactionNumber=1;
                candidates=new Vector<String>();
                itemSet = new ArrayList<String>();
                finalFrequentItemSet = new ArrayList<>();
                frequentItems = new HashMap<String, Integer>();
		int noOfTransactions,minimumSupport;
		double minimumConfidence;
		List<String> transactions = new ArrayList<String>();
		
		String newLine = System.getProperty("line.separator");
		
		minimumSupport = Integer.parseInt(TF_minsupport.getText()); 	
		minimumConfidence = Double.parseDouble(TF_minconfidence.getText());
		minimumConfidence = minimumConfidence/100;
		
		File file = new File("outputForApriori.txt");
		Scanner sc = null;
           try {
               sc = new Scanner(file);
           } catch (FileNotFoundException ex) {
               Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
           }
		
		while (sc.hasNextLine())
		{
			String str = sc.nextLine();
			transactions.add(str);
		}
	
		noOfTransactions = transactions.size();
                
		TA_items.setText("");
                TA_transactions.setText("");
                TA_freqitems.setText("");
                TA_associationrules.setText("");
		display(noOfTransactions, transactions, minimumSupport, minimumConfidence);
                sc.close();
                long end = System.currentTimeMillis();
            long diff = end - start;

            long diffMiliSeconds = diff / 10 % 100;
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            L_timeapriori.setText(diffHours + ":" + diffMinutes + ":" + diffSeconds + ":" + diffMiliSeconds);
       }
   }             
    
   public void display(int noOfTransactions, List<String> transactions, int minimumSupport, double minimumConfidence)
	{
		for(int i = 0; i<noOfTransactions;i++)
		{
			String str = transactions.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			for(int j=0;j<count;j++)
			{
				if(i==0)
				{
					itemSet.add(words[j]);
				}
				else
				{ 
					if(!(itemSet.contains(words[j])))
					{
						itemSet.add(words[j]);
					}
				}
			}
		}
		
		itemsCount = itemSet.size(); 
		L_nbitems.setText(String.valueOf(itemsCount));
                L_nbtransactions.setText(String.valueOf(noOfTransactions));		
		for(String i : itemSet)
		{
			TA_items.appendText(i+"\n");
		}
		
		for(String i : transactions)
		{
			TA_transactions.appendText(displayTransactionNumber+" :  "+i+"\n");
			displayTransactionNumber++;
		}
		firstFrequentItemSet(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	}
	
	/* firstFrequentItemSet(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	 * it calculates Frequent Item Set 1 and stores in HashMap frequentItems with items
	 * and corresponding support values if support is greater than minimum support
	 */
	public void firstFrequentItemSet(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence)
	{
		System.out.println();
		/* calculates the occurrence of individual items in the transactional database,
		* each item is then checked in all transactions and then the occurrence is then
		* compared with minimum support and if it is greater, then added in HashMap "frequentItems"
		*/
		for(int items=0;items<itemSet.size();items++)
		{
			countItemOccurrence=0;
			String itemStr = itemSet.get(items);
			for(int t=0;t<noOfTransactions;t++)
			{
				String transactionStr = transactions.get(t);
				if(transactionStr.contains(itemStr))
				{
					countItemOccurrence++;
				}
			}
			if(countItemOccurrence >= minimumSupport)
			{
				TA_freqitems.appendText("Frequent Itemset 1 => Item = '"+itemStr+"' => support = "+countItemOccurrence+"\n");
				finalFrequentItemSet.add(itemStr);
				frequentItems.put(itemStr, countItemOccurrence);
			}
		}
		
		aprioriStart(noOfTransactions,transactions,minimumSupport,minimumConfidence);
	}
	
	public void aprioriStart(int noOfTransactions,List<String> transactions,int minimumSupport, double minimumConfidence)
	{
		int itemsetNumber=1;
		
		/* add all the items in candidates vector which would be required for generating combinations */
		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			String str = finalFrequentItemSet.get(i);
			candidates.add(str);
		} 
		
		do
		{
			itemsetNumber++;
			generateCombinations(itemsetNumber);
			checkFrequentItems(noOfTransactions,transactions,minimumSupport);
		}
		while(candidates.size()>1);
		
		generateAssociationRules(noOfTransactions, transactions, minimumConfidence);
	}
	
	/* generateCombinations(itemSetNumber)
	* input parameters :- itemsetNumber - iteration number i.e. combination of 2 or 3 or 4......
	*/
	private void generateCombinations(int itemsetNumber)
	{
		Vector<String> candidatesTemp = new Vector<String>();
		String s1, s2;
		StringTokenizer strToken1, strToken2;
		if(itemsetNumber==2)
		{
			for(int i=0; i<candidates.size(); i++)
			{
				strToken1 = new StringTokenizer(candidates.get(i));
				s1 = strToken1.nextToken();
				for(int j=i+1; j<candidates.size(); j++)
				{
					strToken2 = new StringTokenizer(candidates.elementAt(j));
					s2 = strToken2.nextToken();
					String addString = s1+" "+s2;
					candidatesTemp.add(addString);
				}
			}
		}
		else
		{
			for(int i=0; i<candidates.size(); i++)
			{
				for(int j=i+1; j<candidates.size(); j++)
				{
					s1 = new String();
					s2 = new String();
					
					strToken1 = new StringTokenizer(candidates.get(i));
					strToken2 = new StringTokenizer(candidates.get(j));
					
					for(int s=0; s<itemsetNumber-2; s++)
					{
						s1 = s1 + " " + strToken1.nextToken();
						s2 = s2 + " " + strToken2.nextToken();
					}
					
					if(s2.compareToIgnoreCase(s1)==0)
					{
						String addString = (s1 + " " + strToken1.nextToken() + " " + strToken2.nextToken()).trim();
						candidatesTemp.add(addString);
					}
				}
			}
		}
		candidates.clear();
		candidates = new Vector<String>(candidatesTemp);
		candidatesTemp.clear();
		System.out.println();
	}

	
	public void checkFrequentItems(int noOfTransactions,List<String> transactions, int minimumSupport)
	{
		List<String> combList = new ArrayList<String>();
		for(int i=0;i<candidates.size();i++)
		{
			String str = candidates.get(i);
			combList.add(str);
		}
		
		/* below mentioned for loop takes into account each item in list which is then split up and stored in words[],
		* each word is compared with each transaction and if all the words of item set is present in that particular
		* transaction, then itemSetOccurence is incremented. This runs till all the transactions are checked and thus
		* the support for that particular item set is calculated and if it is above minimum support, then it is stored
		* in frequentItems HashMap along with the support, also in finalFrequentItemSet list.
		*/

		for(int i=0;i<combList.size();i++)
		{
			String str = combList.get(i);
			String[] words = str.split(" ");
			int count = words.length;
			int flag = 0, itemSetOccurence=0;
			for(int t=0;t<noOfTransactions;t++)
			{
				String transac = transactions.get(t);
				for(int j=0;j<count;j++)
				{
					String wordStr = words[j];
					if(transac.contains(wordStr))
					{
						flag++;
					}
				}
				if(flag==count)
				{
					itemSetOccurence++;
				}
				flag=0;
			}
			if(itemSetOccurence>=minimumSupport)
			{
				TA_freqitems.appendText("Frequent Itemset "+displayFrequentItemSetNumber+" => Itemset = '"+ str+"' => support = "+itemSetOccurence+"\n");
				frequentItems.put(str, itemSetOccurence);
				finalFrequentItemSet.add(str);
			}
			itemSetOccurence=0;
		}
		displayFrequentItemSetNumber++;
	}
	
	public void generateAssociationRules(int noOfTransactions,List<String> transactions,double minimumConfidence)
	{
		double confidence,confidence1;
		
		/* below mentioned for loop takes each item set from finalFrequentItemSet List, 
		* checks it's support from frequentItems HashMap,
		* splits the item set and stores individual words into words[]. 
		* If wordCount is 2, then only 2 association rules can be generated (i.e. a->b, b->a). 
		* Then it checks support of first word and finds confidence and if it is above minimumConfidence,
		* then displays it. Same flow for wordCount>2. In case of wordCount>2, it generates combinations of rules.
		*/

		for(int i=0;i<finalFrequentItemSet.size();i++)
		{
			String itemSetStr = finalFrequentItemSet.get(i);
			double value = frequentItems.get(itemSetStr);
			String str = "",str1="";
			String[] words = itemSetStr.split(" ");
			int wordCountInString = words.length;
			if(wordCountInString==2) /* for FrequentItemSet = 2*/
			{
				double s = frequentItems.get(words[0]);
				confidence = value/s;
				if(confidence>=minimumConfidence)
				{
					TA_associationrules.appendText("'"+words[0]+" -> "+words[1]+"' = {Confidence = "+ confidence +" and Support = "+(int)value+"}"+"\n");
				}
				double s1 = frequentItems.get(words[1]);
				confidence = value/s1;
				if(confidence>=minimumConfidence)
				{
					TA_associationrules.appendText("'"+words[1]+" -> "+words[0]+"' = {Confidence = "+ confidence+" and Support = "+(int)value+"}"+"\n");
				}
			}
			else /* for FrequentItemSet > 2 */
			{
				for(int a=0;a<wordCountInString-1;a++)
				{
					if(a==0)
					{
						str = str+words[a];
					}
					else
					{
						str = str+" "+words[a];
					}
					for(int j=a+1;j<wordCountInString;j++)
					{
						{
							str1=str1+" "+words[j];
						}
					}
					double s = frequentItems.get(str);
					confidence = value/s;
					String st = str1.trim();
					double s1 = frequentItems.get(st);
					confidence1 = value/s1;
					if(confidence>=minimumConfidence)
					{
						TA_associationrules.appendText("'"+str+" -> "+str1+"' = {Confidence = "+confidence+" and Support = "+(int)value+"}"+"\n");
					}
					if(confidence1>=minimumConfidence)
					{
						TA_associationrules.appendText("'"+st+" -> "+str+"' = {Confidence = "+confidence1+" and Support = "+(int)value+"}"+"\n");
					}
					str1="";
				}
				str="";str1="";
			}
		}
	}
        
        
        
        class element{
            double distance;
            double classValue;
            element(double distance_t,double classValue_t){
                distance = distance_t;
                classValue = classValue_t;
            }
        }
        
        
        public double distance(Instance x,Instance y,int size){
            double distance = 0;
            for(int i = 0; i < size; i++){
                if(ins_missing.attribute(i).isNominal()){
                    if(x.value(i) == y.value(i))
                        distance += 1;
                }else{
                    distance += Math.sqrt(Math.pow(x.value(i) - y.value(i),2));
                }
            }
            return distance;
        }
        
        public int occurence(List<Double> list_t,double str){
            int s = 0;
            for(int i = 0; i < list_t.size(); i++){
                if(list_t.get(i).equals(str))
                    s++;
            }
            return s;
        }
        
        public boolean existElementt(List<element> list_t,double element){
            for(int i = 0 ; i < list_t.size() ; i++){
                if(list_t.get(i).classValue == element)
                    return true;
            }
            return false;
        }
        
        @FXML
        public void KnnAlgorithm(MouseEvent event){
            if(event.getClickCount() == 1){
                long start = System.currentTimeMillis();
                int percentage = Integer.parseInt(TF_modelsize.getText());
                int k = Integer.parseInt(TF_k.getText());
                List<element> tableResult = new ArrayList<>();
                List<Instance> model_normaliser = new ArrayList<>();
                List<Instance> train_normaliser = new ArrayList<>();
                List<Instance> train_missing = new ArrayList<>();
                int i;
                int size_model = (int) ins_normalisation.numInstances()*percentage/100;
                
                TA_model.setText("");
                TA_train.setText("");
                TA_class.setText("");
                
                for(i = 0 ; i < size_model ; i++){
                    model_normaliser.add(ins_normalisation.instance(i));
                    TA_model.appendText(ins_missing.instance(i).toString() + "\n");
                }
                for(i = size_model ; i < ins_normalisation.size() ; i++){
                    train_normaliser.add(ins_normalisation.instance(i));
                    train_missing.add(ins_missing.instance(i));
                }
                System.out.println(model_normaliser.size() + " *** " + train_normaliser.size());
                double correct = 0;
                for(i = 0; i < train_normaliser.size() ; i++){
                    for(int j = 0; j < model_normaliser.size(); j++){
                        tableResult.add(new element(distance(train_normaliser.get(i),model_normaliser.get(j),ins_normalisation.numAttributes()-1),model_normaliser.get(i).value(ins_normalisation.numAttributes()-1)));
                    }
                    
                    Collections.sort(tableResult, new Comparator<element>() {
                    @Override
                    public int compare(element a, element b) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return a.distance < b.distance ? -1 : (a.distance > b.distance) ? 1 : 0;
                    }
                });
                List<Double> list_t = new ArrayList<>();
                for(int n =0 ; n < k ; n++){
                    list_t.add(tableResult.get(n).classValue);
                }
                List<element> list_class_occ = new ArrayList<>();
                for(int m = 0 ; m < list_t.size() ; m++){
                    if(!existElementt(list_class_occ,list_t.get(m)))
                        list_class_occ.add(new element(occurence(list_t,list_t.get(m)),list_t.get(m)));
                }
                Collections.sort(list_class_occ, new Comparator<element>() {
                    @Override
                    public int compare(element a, element b) {
                    // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
                    return a.distance < b.distance ? -1 : (a.distance > b.distance) ? 1 : 0;
                    }
                });
                
                TA_train.appendText((i+1) + " : " + train_missing.get(i).toString() + "\n");
                
                if(list_class_occ.size() == 1){
                    String class_train = ins_missing.attribute(ins_missing.numAttributes()-1).value((int)list_class_occ.get(0).classValue);
                    TA_class.appendText((i+1) + " : " + class_train + "\n");
                    if(class_train.equals(train_missing.get(i).attribute(train_missing.get(i).numAttributes()-1).value((int)train_normaliser.get(i).value(train_missing.get(i).numAttributes()-1))))
                        correct++;
                }else{
                    if(list_class_occ.get(0).distance > list_class_occ.get(1).distance){
                    String class_train = ins_missing.attribute(ins_missing.numAttributes()-1).value((int)list_class_occ.get(0).classValue);
                    TA_class.appendText((i+1) + " : " + class_train + "\n");
                    if(class_train.equals(train_missing.get(i).attribute(train_missing.get(i).numAttributes()-1).value((int)train_normaliser.get(i).value(train_missing.get(i).numAttributes()-1))))
                        correct++;
                }else{
                    TA_class.appendText((i+1) + " : we can't decide ! \n");
                }
                }   
            }
            L_errorratio.setText(String.valueOf(1 - (correct/train_normaliser.size())));
            long end = System.currentTimeMillis();
            long diff = end - start;

            long diffMiliSeconds = diff / 10 % 100;
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            L_timeknn.setText(diffHours + ":" + diffMinutes + ":" + diffSeconds + ":" + diffMiliSeconds);
        }
    }
    
     public Vector<Instance> getNeighbours(Instance p)

	{

		Vector<Instance> neigh =new Vector<Instance>();

		Iterator<Instance> points = pointList.iterator();

		while(points.hasNext()){

				Instance q = points.next();

				if(distance(p,q,q.numAttributes())<= radius){

				neigh.add(q);

				}

		}

		return neigh;

	}



	public void Visited(Instance d){

	VisitList.add(d);

	}



	public boolean isVisited(Instance c){
            if (VisitList.contains(c)){
                return true;
            }
            else{
                return false;
            }

	}



	public Vector<Instance> Merge(Vector<Instance> a,Vector<Instance> b){

            Iterator<Instance> it5 = b.iterator();

            while(it5.hasNext()){
		Instance t = it5.next();
		if (!a.contains(t) ){
                    a.add(t);
		}
            }
            return a;
	} 

        public void copyTohset(){
            for(Instance i : ins_normalisation)
                hset.add(i);
        }

	public Vector<Instance> getList() {

            Vector<Instance> newList =new Vector<Instance>();

            newList.clear();

            copyTohset();
            newList.addAll(hset);

            return newList;

	}
        
        public ArrayList<Double> CalculateCentroid(List cluster){
            ArrayList<Double> centeroid = new ArrayList<>();
            int num_attributes = ins_normalisation.numAttributes();
            int i;
            for(i = 0 ; i < num_attributes ; i++)
                centeroid.add(new Double(0));
            
            Iterator<Instance> j = cluster.iterator();
                
            while(j.hasNext()){
                Instance w = j.next();
                for(i = 0 ; i < num_attributes ; i++){
                    double old_value = centeroid.remove(i);
                    centeroid.add(i,new Double((old_value+(w.value(i)/num_attributes))));
                }
            }
            System.out.println(centeroid.size() + "\n");
            return centeroid;
        }
        
        public double SumDistanceCluster2(ArrayList<Double> centroid,Instance w){
            double distance = 0;
            for(int i = 0; i < w.numAttributes(); i++){
                if(w.attribute(i).isNominal()){
                    if(centroid.get(i) == w.value(i))
                        distance += 1;
                }else{
                    distance += Math.sqrt(Math.pow(centroid.get(i) - w.value(i),2));
                }
            }
            return Math.pow(distance,2);
        }
        
        public double CalculateIntraClassCluser(List cluster){
            Iterator<Instance> j = cluster.iterator();
            
            double l = 0; 
            while(j.hasNext()){
                Instance w = j.next();
                l += SumDistanceCluster2(CalculateCentroid(cluster),w);
            }
            return l;
        }
        
        public double CalculateInterClass(ArrayList<ArrayList<Double>> L_centeroid){
             
            ArrayList<Double> centeroid = new ArrayList<>();
            int num_attributes = ins_normalisation.numAttributes();
            int i;
            for(i = 0 ; i < num_attributes ; i++)
                centeroid.add(new Double(0));
            
            for(ArrayList<Double> elt : L_centeroid){
                for(i = 0 ; i < num_attributes ; i++){
                    double old_value = centeroid.remove(i);
                    centeroid.add(i,new Double((old_value+(elt.get(i)/num_attributes))));
                }
            }
            
            double T = 0;
            for(ArrayList<Double> elt : L_centeroid){
                double distance = 0;
                for(i = 0; i < num_attributes; i++)
                    distance += Math.sqrt(Math.pow(centeroid.get(i) - elt.get(i),2));
                
                T += Math.pow(distance,2);
            }
            return T;
        }
        
        @FXML
        public void DbscanAlgorithm(MouseEvent event){
            
            if(event.getClickCount() == 1){
                long start = System.currentTimeMillis();
                TA_resultdbscan.setText("");
                minpoints = Integer.parseInt(TF_minpoints.getText());
                radius = Double.parseDouble(TF_radius.getText());
            
                hset.clear();
                resultList.clear();
                pointList = new Vector<Instance>();
                VisitList.clear();
                pointList = getList();
            
                int index2 =0;

                while (pointList.size()>index2){
                
                    Instance p =pointList.get(index2);
                    if(!isVisited(p)){
                        Visited(p);	
                        Neighbours =getNeighbours(p);
                    
                        if (Neighbours.size()>=minpoints){
                            int ind=0;
                        
                            while(Neighbours.size()>ind){
                                Instance r = Neighbours.get(ind);
                            
                                if(!isVisited(r)){
                                    Visited(r);
                                    Vector<Instance> Neighbours2 = getNeighbours(r);
                                
                                    if(Neighbours2.size() >= minpoints){
                                        Neighbours=Merge(Neighbours, Neighbours2);
                                    }

                                } 
                                ind++;
                            }
                            System.out.println("N"+Neighbours.size());
                            resultList.add(Neighbours);
                        }
                    }
                    index2++;
                }
            
                trl.clear();
                trl.addAll(resultList);
                int index1 = 0;
                
                ArrayList<ArrayList<Double>> L_centeroid = new ArrayList<ArrayList<Double>>();
                double SumW = 0;
                for(List l : trl){
                    TA_resultdbscan.appendText("Cluster  :" + (index1 + 1) + "\n");
                    Iterator<Instance> j = l.iterator();
                
                    while(j.hasNext()){
                        Instance w = j.next();
                        TA_resultdbscan.appendText(w.toString() + "\n");
                    }
                    
                    L_centeroid.add(CalculateCentroid(l));
                    SumW += CalculateIntraClassCluser(l);
                    
                    TA_resultdbscan.appendText("------------------------------------------------------- \n");
                    index1++;
                }
                TA_intraclass.setText("");
                TA_interclass.setText("");
                TA_intraclass.appendText(String.valueOf(SumW));
                TA_interclass.appendText(String.valueOf(CalculateInterClass(L_centeroid)));
                long end = System.currentTimeMillis();
                long diff = end - start;

                long diffMiliSeconds = diff / 10 % 100;
                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                L_timedbscan.setText(diffHours + ":" + diffMinutes + ":" + diffSeconds + ":" + diffMiliSeconds);
            }
        }
        
}
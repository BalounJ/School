package trie_vyskyt_slov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Třída {@code Gui} tvoří GUI programu. Stará se o vstupy a zobrazuje výstupy.
 * @author Josef Baloun, Petra Štumpfová
 * @version 1.1
 * */
public class Gui extends Application {
	/** Odkaz na slovník s kterým pracuje.
	 * */
	private Slovnik slovnik;
	
	
	/** Odkaz na poslední slovo, které bylo vyhledávané.
	 * */
	private String poslHledaneSlovo;
	
	/** Odkaz na primary stage.
	 * */
	private Stage stage;
	/** Odkaz na kořenový layout.
	 * */
	private BorderPane root;
	/** Tlačítko pro vytvoření slovníku z textu.
	 * */
	private Button btnText;
	/** Tlačítko pro vytvoření slovníku ze souboru .txt.
	 * */
	private Button btnSoubor;
	/** Tlačítko pro načtení slovníku ze souboru .dic.
	 * */
	private Button btnSlovnik;
	/* Tlačítko pro přesun zpět na okno slovník.
	 * */
	//private Button btnVratit;
	/** TA pro vstup při vytvoření slovníku z textu.
	 * */
	private TextArea taText;
	/** Přepínač volby pro vytvoření slovníku z textu.
	 * */
	private RadioButton rbText;
	/** Přepínač volby pro vytvoření slovníku ze souboru .txt.
	 * */
	private RadioButton rbSoubor;
	/** Přepínač volby pro načtení slovníku ze souboru .dic.
	 * */
	private RadioButton rbSlovnik;
	
//oddělení komponent pro 2. okno****************************************************************************
	
	/** TF pro vyhledávání.
	 * */
	private TextField tfHledej;
	/* Tlačítko pro vyhledávání.
	 * */
	//private Button btnHledej;
	/* Tlačítko pro výpis slovníku.
	 * */
	//private Button btnVypis;
	/** Tlačítko pro přidání nenalezeného slova do slovníku.
	 * */
	private Button btnPridat;
	/* Tlačítko pro uložení slovníku do souboru .dic.
	 * */
	//private Button btnUlozit;
	/* Tlačítko pro návrat na startovní obrazovku pro vytvoření nového slovníku.
	 * */
	//private Button btnZpet;
	/** TA pro informace o hledání a pokyny pro práci.
	 * */
	private TextArea taInfo;
	
	
	/** Main metoda.
	 * @param args Předané argumenty. 
	 * */
	public static void main(String[] args) {
		launch(args);
	}
	
	/** The main entry point for all JavaFX applications. The start method is called after the init method has returned, and after the system is ready for the application to begin running.
	 * @param stage The primary stage for this application, onto which the application scene can be set. The primary stage will be embedded in the browser if the application was launched as an applet. Applications may create other stages, if needed, but they will not be primary stages and will not be embedded in the browser. 
	 * */
	@Override
	public void start(Stage stage) throws Exception {
		this.stage=stage;
		stage.setTitle(Lib.TITLE);
		root = new BorderPane();
		root.setCenter(getStart());	
		stage.setScene(new Scene(root));
		stage.show();
		stage.setMinHeight(stage.getHeight());
		stage.setMinWidth(stage.getWidth());
	}
		
	/**Vytvoří první okno po spuštění.
	 *  @return Layout s rozvrženými komponentami.
	 * */
	private Node getStart() {
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20));

		ToggleGroup toggleGroup = new ToggleGroup();
		rbText = new RadioButton("text");
		rbSoubor = new RadioButton("soubor .txt");
		rbSlovnik = new RadioButton("načíst .dic");		
		toggleGroup.selectedToggleProperty().addListener(e -> radioChange());
		rbText.setToggleGroup(toggleGroup);
		rbSoubor.setToggleGroup(toggleGroup);
		rbSlovnik.setToggleGroup(toggleGroup);
		
		taText = new TextArea();		
			taText.setWrapText(true);
			taText.setVisible(false);
			
		btnText = new Button("Vytvořit z textu");
			btnText.setPrefWidth(Lib.BUTTON_WIDTH);
			btnText.setVisible(false);
			btnText.setOnAction(e->fromText());
		
		btnSoubor = new Button("Vybrat soubor");
			btnSoubor.setPrefWidth(Lib.BUTTON_WIDTH);
			btnSoubor.setVisible(false);
			btnSoubor.setOnAction(e->fromFileTxt());
			
		btnSlovnik = new Button("Načíst slovník");
			btnSlovnik.setPrefWidth(Lib.BUTTON_WIDTH);
			btnSlovnik.setVisible(false);
			btnSlovnik.setOnAction(e->fromFileDic());
			
		Button btnVratit = new Button("Pokračovat");
			btnVratit.setPrefWidth(Lib.BUTTON_WIDTH);
			btnVratit.setVisible(slovnik!=null);
			btnVratit.setOnAction(e->pokracovatNaSlovnik());

		Label lbl=new Label("Vytvořit slovník z: ");
		
		grid.add(lbl, 0, 0);
		grid.add(rbSoubor, 0, 1);
		grid.add(rbSlovnik, 0, 2);
		grid.add(rbText, 0, 3);
		grid.add(taText, 1, 4);
		grid.add(btnSoubor, 1, 1);
		grid.add(btnSlovnik, 1, 2);
		grid.add(btnText, 1, 3);	
		
		grid.add(btnVratit, 0, 5);
		return grid;
	}
	
	/** Pokud je vytvořen slovník překreslí okno pro manipulaci se slovníkem.
	 * */
	private void pokracovatNaSlovnik() {
		if(slovnik!=null) {
			root.setTop(null);
			root.setLeft(null);
			root.setBottom(null);
			root.setRight(null);
			root.setCenter(getSlovnikPane());
		}
		else{
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Nenalezen slovník.");
			alert.setHeaderText("Nelze pracovat se slovníkem.");
			alert.setContentText("Prosím vytvořte nový slovník.");
			alert.show();
		}
	}
	
	/** Obsluha stisku tlačítka {@code btnText} pro vytvoření slovníku z textu.
	 * */
	private void fromText() {
		String text=taText.getText();	
		if(text.length()!=0) {	
			taText.setText(text);			
			slovnik = new Slovnik(text);		
			pokracovatNaSlovnik();
		}
		else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Chybí vstup.");
			alert.setHeaderText("Vstup je prázdný.");
			alert.setContentText("Prosím vyplňte nejdříve vstup.");
			alert.show();
		}
	}
	
	/** Obsluha stisku tlačítka {@code btnSoubor} pro vytvoření slovníku z .txt.
	 * */
	private void fromFileTxt() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().clear();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".txt", "*.txt"));
		
		File file = fc.showOpenDialog(stage);

		if (file != null) {
			FileReader reader;
			BufferedReader input;
			try {
				reader = new FileReader(file);
				input = new BufferedReader(reader);				
				
				String line = input.readLine();
				String text = "";
				while (line != null) {
					text += line+" ";
					line = input.readLine();	
				}

				input.close();
				reader.close();	

				slovnik = new Slovnik(text);
				
				pokracovatNaSlovnik();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("Chyba při čtení.");
				alert.setContentText("Při čtení souboru došlo k chybě: "+e+" "+e.getMessage());
				alert.showAndWait();
			} 		
		}
	}
	
	/** Obsluha stisku tlačítka {@code btnSlovnik} pro načtení slovníku z .dic.
	 * */
	private void fromFileDic() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().clear();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".dic", "*.dic"));
		
		File file = fc.showOpenDialog(stage);

		if (file != null) {
			try {
				slovnik=Slovnik.nactiSlovnik(file);
				pokracovatNaSlovnik();
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Chyba při čtení.");
				alert.setHeaderText("Slovník se nepodařilo načíst. Soubor může být poškozen, nebo nemusí odpovídat verze.");
				alert.setContentText(e.getMessage()+System.lineSeparator()+e);
				alert.showAndWait();
			}
		}
	}

	/** Obsluha stisku radio buttonu nastaví viditelnost komponent.
	 * */
	private void radioChange() {
		if(rbSoubor.isSelected()){
			btnText.setVisible(false);
			taText.setVisible(false);
			btnSoubor.setVisible(true);
			btnSlovnik.setVisible(false);
		}else if(rbSlovnik.isSelected()){
			btnText.setVisible(false);
			taText.setVisible(false);
			btnSoubor.setVisible(false);
			btnSlovnik.setVisible(true);
		}else if(rbText.isSelected()){
			btnText.setVisible(true);
			taText.setVisible(true);
			btnSoubor.setVisible(false);
			btnSlovnik.setVisible(false);
		}		
	}
	
	
//oddělení oken***********************************************************
	
	/**Vytvoří druhé okno pro práci s vytvořeným slovníkem.
	 *  @return Layout s rozvrženými komponentami.
	 * */
	private Node getSlovnikPane() {
		BorderPane bp = new BorderPane();
			bp.setPadding(new Insets(20));
			
		tfHledej = new TextField();
			tfHledej.setOnAction(e->hledejSlovo());
		
		Button btnHledej = new Button("Hledat slovo");
			btnHledej.setPrefWidth(Lib.BUTTON_WIDTH);
			btnHledej.setOnAction(e->hledejSlovo());
			
		Button btnVypis = new Button("Výpis slovníku");
			btnVypis.setPrefWidth(Lib.BUTTON_WIDTH);
			btnVypis.setOnAction(e->vypisSlovniku());
		
		btnPridat = new Button("Přidat");
			btnPridat.setPrefWidth(Lib.BUTTON_WIDTH);
			btnPridat.setVisible(false);
			btnPridat.setOnAction(e->pridatSlovo());
			BorderPane.setMargin(btnPridat, new Insets(10, 0, 10, 0));
		
		Button btnUlozit = new Button("Uložit");
			btnUlozit.setPrefWidth(Lib.BUTTON_WIDTH);
			btnUlozit.setOnAction(e->ulozitSlovnik());
		
		Button btnZpet = new Button("Zpět");
			btnZpet.setPrefWidth(Lib.BUTTON_WIDTH);
			btnZpet.setOnAction(e->zpetNaStart());
		
		taInfo = new TextArea();
			taInfo.setEditable(false);
			BorderPane.setMargin(taInfo, new Insets(10, 10, 10, 0));
							
		HBox topHB=new HBox(10);
		topHB.getChildren().add(tfHledej);
		topHB.getChildren().add(btnHledej);
		topHB.getChildren().add(btnVypis);
		HBox bottomHB=new HBox(10);	
		bottomHB.getChildren().add(btnZpet);
		bottomHB.getChildren().add(btnUlozit);
	
		bp.setTop(topHB);
		bp.setCenter(taInfo);
		bp.setRight(btnPridat);
		bp.setBottom(bottomHB);
		
		return bp;
	}

	/** Obsluha stisku tlačítka {@code btnVypis} pro výpis slovníku.
	 * */
	private void vypisSlovniku() {
		taInfo.setText(slovnik.slova());
	}

	/** Obsluha stisku tlačítka {@code btnZpet} pro návrat na startovní okno pro načtení slovníku.
	 * */
	private void zpetNaStart() {
		root.setTop(null);
		root.setLeft(null);
		root.setBottom(null);
		root.setRight(null);
		root.setCenter(getStart());
	}

	/** Obsluha stisku tlačítka {@code btnUlozit} pro uložení slovníku do .dis.
	 * */
	private void ulozitSlovnik() {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().clear();
		fc.getExtensionFilters().add(new FileChooser.ExtensionFilter(".dic", "*.dic"));
		
		File file = fc.showSaveDialog(stage);

		if (file != null) {
			try {
				slovnik.ulozSlovnik(file);
				taInfo.setText("Úspěšně uloženo.");
			} catch (Exception e) {	
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Neúspěšný pokus o uložení.");
						alert.setHeaderText("Slovník se nepovedlo uložit, zkuste to prosím znovu.");
						alert.setContentText(e.getMessage()+System.lineSeparator()+e);
						alert.showAndWait();	
			}
		}
	}

	/** Obsluha stisku tlačítka {@code btnHledej} pro hledání ve slovníku.
	 * */
	private void hledejSlovo() {
		btnPridat.setVisible(false);

		String vstup=tfHledej.getText();
		poslHledaneSlovo=Lib.normalizujSlovo(vstup);
		
		if(!poslHledaneSlovo.equals(vstup)){
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Normalizace vstupu");
			alert.setHeaderText("Byla nutná normalizace vstupu.");
			alert.setContentText("Vstup: '"+vstup+"' byl převeden na '"+poslHledaneSlovo+"'.");
			alert.show();
			tfHledej.setText(poslHledaneSlovo);
		}
		
		if(poslHledaneSlovo.length()>0){
			int[] vyskyty = slovnik.hledejSlovo(poslHledaneSlovo);	
			String infoText;
			if(vyskyty==null) {
				infoText="Slovo \""+poslHledaneSlovo+"\" nenalezeno, můžete ho přidat ->."+System.lineSeparator()+
						"Podobná slova:"+System.lineSeparator();
				
				infoText+=slovnik.podobnaSlova(poslHledaneSlovo);
				
				btnPridat.setVisible(true);
			}
			else {
				infoText="Počet výskytů \""+poslHledaneSlovo+"\": "+vyskyty.length+System.lineSeparator()
					+"Na indexech od - do:";
				for (int j = 0; j < vyskyty.length; j++) {
					infoText+= System.lineSeparator() +(j+1)+". výskyt: ";
					if(vyskyty[j]<0) {
						infoText+="přidáno uživatelem";
					}
					else {
						infoText+=vyskyty[j]+" - "+(vyskyty[j]+poslHledaneSlovo.length()-1);
					}	
				}
			}
			taInfo.setText(infoText);
			
			
			File kam=new File(Lib.CESTA_VYPIS);
			try {
				FileWriter writer =new FileWriter(kam, true);
				BufferedWriter output = new BufferedWriter(writer);

				output.write(infoText);		
				output.newLine();
				output.newLine();
				output.close(); 
			} catch (FileNotFoundException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Problém při práci se souborem.");
				alert.setHeaderText("Při zápisu výsledku hledání došlo k chybě.");
				alert.setContentText(e.toString());
				alert.show();
			} catch (IOException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("Problém při práci se souborem.");
				alert.setHeaderText("Při zápisu výsledku hledání došlo k chybě.");
				alert.setContentText(e.toString());
				alert.show();
			}
			
		}
		else {
			taInfo.setText("Prázdný vstup.");
		}
	}

	/** Obsluha stisku tlačítka {@code btnPridat} pro přidání slova do slovníku.
	 * */
	private void pridatSlovo() {
		if(slovnik.pridejSlovoUser(poslHledaneSlovo)) {
			btnPridat.setVisible(false);
			taInfo.setText("Slovo \""+poslHledaneSlovo+"\" přidáno.");
		}
		else {
			taInfo.setText("Slovo \""+poslHledaneSlovo+"\" se nepodařilo přidat.");
		}
	}

	

}

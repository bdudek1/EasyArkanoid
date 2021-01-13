package com.example.arkanoid.client.config;

import java.util.logging.Level;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class UICreator {
	private static Button startButton;
	private static ListBox levelSelect;
	private static ListBox rowsNumber;
	private static ListBox columnsNumber;
	private static String optionsStyle;
	
	public static Canvas createCanvas() {
		  Canvas canvas = Canvas.createIfSupported();
		  if (canvas == null) {
			  Configuration.log(Level.SEVERE, Configuration.CANVAS_NOT_SUPPORTED_WARNING);
		        RootPanel.get(Configuration.CANVAS_HOLDER_ID)
		        		 .add(new Label(Configuration.CANVAS_NOT_SUPPORTED_WARNING));
		        return null;
		  }
		  return canvas;
	}
	
	public static void setStartButton(Button button) {
		startButton = button;
	}
	
	//uzywanie forów do inicjalizacji crashowalo aplikacje, dlatego w ten sposob
	public static ListBox createLevelSelector() {
	    levelSelect = new ListBox();
	    	levelSelect.addItem("1");
	    	levelSelect.addItem("2");
	    	levelSelect.addItem("3");
	    	levelSelect.addItem("4");
	    	levelSelect.addItem("5");

	    	levelSelect.setItemText(0, "Difficulty 1");
	    	levelSelect.setItemText(1, "Difficulty 2");
	    	levelSelect.setItemText(2, "Difficulty 3");
	    	levelSelect.setItemText(3, "Difficulty 4");
	    	levelSelect.setItemText(4, "Difficulty 5");
	    	
	    	RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("level").add(levelSelect);
	    	
	    	return levelSelect;
	}
	
	public static ListBox createColumnsNumberSelector() {
		 columnsNumber = new ListBox();
			 columnsNumber.addItem("1");
			 columnsNumber.addItem("2");
			 columnsNumber.addItem("3");
			 columnsNumber.addItem("4");
			 columnsNumber.addItem("5");
			 columnsNumber.addItem("6");
			 columnsNumber.addItem("7");
			 columnsNumber.addItem("8");
			 
			 columnsNumber.setItemText(0, "1 Brick Columns");
			 columnsNumber.setItemText(1, "2 Brick Columns");
			 columnsNumber.setItemText(2, "3 Brick Columns");
			 columnsNumber.setItemText(3, "4 Brick Columns");
			 columnsNumber.setItemText(4, "5 Brick Columns");
			 columnsNumber.setItemText(5, "6 Brick Columns");
			 columnsNumber.setItemText(6, "7 Brick Columns");
			 columnsNumber.setItemText(7, "8 Brick Columns");

			 columnsNumber.setSelectedIndex(6);
			 
			 RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("columns").add(columnsNumber);
			 
			 return columnsNumber;
	}
	
	public static ListBox createRowsNumberSelector() {
		 rowsNumber = new ListBox();
			 rowsNumber.addItem("1");
			 rowsNumber.addItem("2");
			 rowsNumber.addItem("3");
			 rowsNumber.addItem("4");
			 rowsNumber.addItem("5");
			 rowsNumber.addItem("6");
			 rowsNumber.addItem("7");
			 rowsNumber.addItem("8");
			 
			 rowsNumber.setItemText(0, "1 Brick Rows");
			 rowsNumber.setItemText(1, "2 Brick Rows");
			 rowsNumber.setItemText(2, "3 Brick Rows");
			 rowsNumber.setItemText(3, "4 Brick Rows");
			 rowsNumber.setItemText(4, "5 Brick Rows");
			 rowsNumber.setItemText(5, "6 Brick Rows");
			 rowsNumber.setItemText(6, "7 Brick Rows");
			 rowsNumber.setItemText(7, "8 Brick Rows");
			 
			 rowsNumber.setSelectedIndex(4);
			 
			 RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("rows").add(rowsNumber);
			 
			 return rowsNumber;
	}
	
	public static String getOptionsStyle() {
		return optionsStyle;
	}
	
	public static void setOptionsStyle(String style) {
		optionsStyle = style;
	}
	
	public static void hideMenu() {
		RootPanel.get("options").setStyleName("display:none");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).setStyleName("canvas");
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("start").clear();
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("level").clear();
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("columns").clear();
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("rows").clear();
	}
	
	public static void showMenu() {
		RootPanel.get("options").setStyleName(getOptionsStyle());
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("start").add(startButton);
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("level").add(levelSelect);
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("columns").add(columnsNumber);
		RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("rows").add(rowsNumber);
	}
	
}

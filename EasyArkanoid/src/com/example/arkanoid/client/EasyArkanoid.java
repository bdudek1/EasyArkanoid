package com.example.arkanoid.client;

import com.example.arkanoid.client.config.Configuration;
import com.example.arkanoid.client.config.UICreator;
import com.example.arkanoid.client.util.GameState;
import com.example.arkanoid.client.util.GameThread;
import com.example.arkanoid.client.util.SoundBoard;
import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

public class EasyArkanoid implements EntryPoint {
	 private Canvas canvas;
	 private GameThread game;
	 private ListBox levelSelect;
	 private ListBox rowsNumber;
	 private ListBox columnsNumber;
	 private Button startButton;
	 
	 @Override
	 public void onModuleLoad() {	 
		  canvas = UICreator.createCanvas();
		  SoundBoard.loadSounds();
		  Configuration.loadBitmaps();
		  configureStartButton();
		  levelSelect = UICreator.createLevelSelector();
		  rowsNumber = UICreator.createRowsNumberSelector();
		  columnsNumber = UICreator.createColumnsNumberSelector();
		  UICreator.setOptionsStyle(RootPanel.get("options").getStyleName());
	 }
	 
	 private void configureStartButton() {
		  startButton = new Button("Start game", new ClickHandler() {
		      public void onClick(ClickEvent event) {
		    	  if(game == null || game.getGameState()!=GameState.RUNNING) {
		        		game = new GameThread(canvas);
				        Configuration.configureCanvasAndControls(canvas, game);
				        Configuration.setLevel(Integer.valueOf(levelSelect.getSelectedValue()));
				        Configuration.setBrickRowsNumber(
				        		Byte.valueOf(rowsNumber.getSelectedValue()));
				        Configuration.setBrickColumnsNumber(
				        		Byte.valueOf(columnsNumber.getSelectedValue()));
				        UICreator.hideMenu();
				  	  	game.startGame();
		    	  }
		      }
		  });
		  UICreator.setStartButton(startButton);
		  RootPanel.get(Configuration.CANVAS_HOLDER_ID).get("start").add(startButton);
	 }
}

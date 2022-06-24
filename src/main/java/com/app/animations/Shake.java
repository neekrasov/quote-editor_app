package com.app.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition translateTransition;
    public Shake(Node node){
        translateTransition = new TranslateTransition(Duration.millis(50), node);
        translateTransition.setFromX(0);
        translateTransition.setByX(10);
        translateTransition.setCycleCount(6);
        translateTransition.setAutoReverse(true);
    }
    public void play(){
        translateTransition.playFromStart();
    }
}

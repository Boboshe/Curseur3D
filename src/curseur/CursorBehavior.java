/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package curseur;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/**
 *
 * @author terooabo
 */
public class CursorBehavior extends Behavior {

    /**
     * Condition d'activation.
     */
    private WakeupOnElapsedTime condition = new WakeupOnElapsedTime(20); //Toutes les 20 millisecondes

    private Curseur3d curseur;
    private CurseurKeyListener curseurKeyListener;
    private double pas;

    public double getPas() {
        return pas;
    }

    public CursorBehavior(Curseur3d curseur, CurseurKeyListener curseurKeyListener) {
        this(curseur, curseurKeyListener, 0.1); //Par défaut le pas est égal à 0.1
    }

    public CursorBehavior(Curseur3d curseur, CurseurKeyListener curseurKeyListener, double pas) {
        this.curseur = curseur;
        this.curseurKeyListener = curseurKeyListener;
        this.pas = pas;
    }

    @Override
    public void initialize() {
        this.wakeupOn(this.condition);
        this.setSchedulingBounds(new BoundingSphere(new Point3d(), 10.0));
    }

    @Override
    public void processStimulus(Enumeration enmrtn) {
        Vector3d v3d = this.curseur.getPosition();

        //Si curseurKeyListener = true => on incrémente du pas
        //Sinon on ne fit rien
        // x : Droite/Gauche
        if (this.curseurKeyListener.allezADroite()) {
            v3d.x += this.pas;
        }
        if (this.curseurKeyListener.allezAGauche()) {
            v3d.x -= this.pas;
        }

        // y : Haut/Bas
        if (this.curseurKeyListener.allezEnHaut()) {
            v3d.y += this.pas;
        }
        if (this.curseurKeyListener.allezEnBas()) {
            v3d.y -= this.pas;
        }

        // z : Avant/Arrière
        if (this.curseurKeyListener.allezEnAvant()) {
            v3d.z += this.pas;
        }
        if (this.curseurKeyListener.allezEnArriere()) {
            v3d.z -= this.pas;
        }
        
        //Quand c'est ouvert je le grapb pas, mais quand c'est fermé je grab ^^
        this.curseur.setOuvert(!this.curseurKeyListener.grab());

        //On set la position du curseur
        this.curseur.setPosition(v3d);

        this.wakeupOn(this.condition);
    }

}

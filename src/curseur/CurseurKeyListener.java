package curseur;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * ***************
 */
/* Classe CurseurKeyListener */
/**
 * ***************
 */
/**
 * La classe <code>CurseurKeyListener</code> ...
 *
 * @author	<a href="mailto:berro@univ-tlse1.fr">Alain Berro</a>
 * @version	11 f�vr. 2005
 */
public class CurseurKeyListener implements KeyListener {
    /*---------*/
    /* Donn�es */
    /*---------*/

    private boolean droite;
    private boolean gauche;

    private boolean haut;
    private boolean bas;

    private boolean avant;
    private boolean arriere;

    private boolean saisi;


    /*--------------*/
    /* Constructeur */
    /*--------------*/
    /**
     * Construit et initialise.
     */
    public CurseurKeyListener() {
    }


    /*----------*/
    /* M�thodes */
    /*----------*/
    public boolean allezADroite() {
        return this.droite;
    }

    public boolean allezAGauche() {
        return this.gauche;
    }

    public boolean allezEnHaut() {
        return this.haut;
    }

    public boolean allezEnBas() {
        return this.bas;
    }

    public boolean allezEnArriere() {
        return this.arriere;
    }

    public boolean allezEnAvant() {
        return this.avant;
    }

    public boolean grab() {
        return this.saisi;
    }

    /*---------------------------------------------*/
    /* M�thodes abstraites h�rit�es de KeyListener */
    /*---------------------------------------------*/
    /**
     *
     */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                this.droite = true;
                break;
            case KeyEvent.VK_LEFT:
                this.gauche = true;
                break;

            case KeyEvent.VK_UP:
                this.haut = true;
                break;
            case KeyEvent.VK_DOWN:
                this.bas = true;
                break;

            case KeyEvent.VK_PAGE_UP:
                this.arriere = true;
                break;
            case KeyEvent.VK_PAGE_DOWN:
                this.avant = true;
                break;
            case KeyEvent.VK_SPACE:
                this.saisi = true;
                break;

            default:
        }
    }

    /**
     *
     */
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                this.droite = false;
                break;
            case KeyEvent.VK_LEFT:
                this.gauche = false;
                break;

            case KeyEvent.VK_UP:
                this.haut = false;
                break;
            case KeyEvent.VK_DOWN:
                this.bas = false;
                break;

            case KeyEvent.VK_PAGE_UP:
                this.arriere = false;
                break;
            case KeyEvent.VK_PAGE_DOWN:
                this.avant = false;
                break;
            case KeyEvent.VK_SPACE:
                this.saisi = false;
                break;

            default:
        }
    }

    /**
     *
     */
    public void keyTyped(KeyEvent e) {
    }

} /*----- Fin de la classe CurseurKeyListener -----*/

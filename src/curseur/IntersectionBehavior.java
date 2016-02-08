package curseur;

import java.util.Enumeration;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.PolygonAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnElapsedTime;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

/* Classe IntersectionBehavior */
/**
 *
 */
public class IntersectionBehavior extends Behavior {
    /*---------*/
    /* Donn�es */
    /*---------*/

    /**
     * Condition d'activation.
     */
    private WakeupOnElapsedTime condition = new WakeupOnElapsedTime(20);

    private double seuil;

    /**
     * R�f�rence sur le TransformGroup qui va subir la modification.
     */
    private TransformGroup tgCyl;
    private TransformGroup tgCone;
    private TransformGroup tgSphere;

    private Curseur3d curseur;
    private AppCurseur3d app3D;
    private CurseurKeyListener curseurKeyListener;

    //Pour rester cohérent, le pas de l'IntersectionBehavior doit être identique
    //au pas du CursorBehavior = 0.1
    private double pas;

    /*--------------*/
    /* Constructeur */
    /*--------------*/
    public IntersectionBehavior(AppCurseur3d app3D,
            TransformGroup tgCyl,
            TransformGroup tgCone,
            TransformGroup tgSphere,
            Curseur3d curseur,
            CurseurKeyListener curseurKeyListener) {
        this(app3D, tgCyl, tgCone, tgSphere, curseur, curseurKeyListener, 1.75, 0.1);
    }

    public IntersectionBehavior(AppCurseur3d app3D,
            TransformGroup tgCyl,
            TransformGroup tgCone,
            TransformGroup tgSphere,
            Curseur3d curseur,
            CurseurKeyListener curseurKeyListener,
            double seuil, double pas) {
        this.tgCyl = tgCyl;
        this.tgCone = tgCone;
        this.tgSphere = tgSphere;
        this.curseur = curseur;
        this.app3D = app3D;
        this.curseurKeyListener = curseurKeyListener;
        this.seuil = seuil;
        this.pas = pas;
    }

    /*------------------------------------------------*/
    /* D�finition des m�thodes abstraites de Behavior */
    /*------------------------------------------------*/
    /**
     * Initialisation du type de comportement.
     */
    public void initialize() {
        this.wakeupOn(this.condition);
        this.setSchedulingBounds(new BoundingSphere(new Point3d(), 10.0));
    }

    /**
     * R�ponse � l'�v�nement.
     */
    public void processStimulus(Enumeration criteria) {

//        versionAutomatique();
        versionPressed(this.curseur.isOuvert());

    }

    /**
     * Calcule la distance entre deux points de l'espace.
     */
    private static double distance(Vector3d v0, Vector3d v1) {
        return Math.sqrt((v0.x - v1.x) * (v0.x - v1.x) + (v0.y - v1.y) * (v0.y - v1.y) + (v0.z - v1.z) * (v0.z - v1.z));
    }

    private void versionAutomatique() {
        boolean ouvert = true;

        /*----- Position du curseur -----*/
        Vector3d posCurseur = this.curseur.getPosition();

        Transform3D t3d = new Transform3D();
        Vector3d v3d1 = new Vector3d();
        /*----- Position du cône -----*/
        //On récupère le TG de l'objet dont on souhaite savoir
        //si il va y avoir une intersection avec ou pas,
        this.tgCone.getTransform(t3d);
        //On récupère sa position
        t3d.get(v3d1);

        //On calcul la distance entre le curseur et la position de l'objet,
        //Et si elle est inférieure à 
        if (distance(v3d1, posCurseur) < seuil) {
            ouvert = false;
            app3D.setAppearance(0, PolygonAttributes.POLYGON_LINE);
//            this.tgCone.setTransform(deplacerObjet(v3d1)); //A ne pas activer => Colle tout => C'est narmol
        } else {
            app3D.setAppearance(0, PolygonAttributes.POLYGON_FILL);
        }

        /*----- Position de la sphère -----*/
        this.tgSphere.getTransform(t3d);
        t3d.get(v3d1);

        if (distance(v3d1, posCurseur) < seuil) {
            ouvert = false;
            app3D.setAppearance(1, PolygonAttributes.POLYGON_LINE);
//            this.tgSphere.setTransform(deplacerObjet(v3d1)); //A ne pas activer => Colle tout => C'est narmol
        } else {
            app3D.setAppearance(1, PolygonAttributes.POLYGON_FILL);
        }

        /*----- Position du cylindre -----*/
        this.tgCyl.getTransform(t3d);
        t3d.get(v3d1);

        if (distance(v3d1, posCurseur) < seuil) {
            ouvert = false;
            app3D.setAppearance(2, PolygonAttributes.POLYGON_LINE);
//            this.tgCyl.setTransform(deplacerObjet(v3d1)); //A ne pas activer => Colle tout => C'est narmol
        } else {
            app3D.setAppearance(2, PolygonAttributes.POLYGON_FILL);
        }

        this.curseur.setOuvert(ouvert);
        this.wakeupOn(this.condition);
    }

    private void versionPressed(boolean ouvert) {

        /*----- Position du curseur -----*/
        Vector3d posCurseur = this.curseur.getPosition();

        Transform3D t3d = new Transform3D();
        Vector3d v3d1 = new Vector3d();
        /*----- Position du cône -----*/
        this.tgCone.getTransform(t3d);
        t3d.get(v3d1);

        if (distance(v3d1, posCurseur) < seuil) {
            app3D.setAppearance(0, PolygonAttributes.POLYGON_LINE);
            //Grab l'objet
            if (!ouvert) {
                this.tgCone.setTransform(deplacerObjet(v3d1));
            }
        } else {
            app3D.setAppearance(0, PolygonAttributes.POLYGON_FILL);
        }

        /*----- Position de la sphère -----*/
        this.tgSphere.getTransform(t3d);
        t3d.get(v3d1);

        if (distance(v3d1, posCurseur) < seuil) {
            app3D.setAppearance(1, PolygonAttributes.POLYGON_LINE);
            //Grab l'objet
            if (!ouvert) {
                this.tgSphere.setTransform(deplacerObjet(v3d1));
            }
        } else {
            app3D.setAppearance(1, PolygonAttributes.POLYGON_FILL);
        }

        /*----- Position du cylindre -----*/
        this.tgCyl.getTransform(t3d);
        t3d.get(v3d1);

        if (distance(v3d1, posCurseur) < seuil) {
            app3D.setAppearance(2, PolygonAttributes.POLYGON_LINE);
            //Grab l'objet
            if (!ouvert) {
                this.tgCyl.setTransform(deplacerObjet(v3d1));
            }
        } else {
            app3D.setAppearance(2, PolygonAttributes.POLYGON_FILL);
        }

        this.wakeupOn(this.condition);

        //Fin_versionPressed
    }

    /**
     * Fonction permettant d'appliquer un déplacement à un objet, en lui passant
     * son vecteur position.
     *
     * @param v3d
     */
    private Transform3D deplacerObjet(Vector3d v3d) {
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
        Transform3D t3d = new Transform3D();
        t3d.set(v3d);

        return t3d;
    }

} /*----- Fin de la classe IntersectionBehavior -----*/

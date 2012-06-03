package view;

import inc.Functions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import ground.Grille;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import process.Traitement;
import process.TraitementSimple;


public class FenetreResultats extends JFrame
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel bigContainer = new JPanel();
	private JPanel container = new JPanel();
	private boolean weiter;
	private int nbR;
	
	private Functions f = new Functions();
	
	public FenetreResultats(Grille g) {
		
		this.setTitle("R�sultats - EnsiStats");
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		initComposant(g);
		
		if(weiter) {
			this.setSize(nbR*125, 300);
			bigContainer.setLayout(new BorderLayout());
			bigContainer.add(container, BorderLayout.CENTER);
			
			this.setContentPane(bigContainer);
			this.setVisible(true);
		}
		
	}
	
	private void initComposant(Grille g) {
		Traitement t = new TraitementSimple();
		Grille[] r = t.traite(g);
		if (r[0] != null) {
			weiter = true;
			int nbR = 0;
			for (int i=0; i<9; i++) if (r[i] != null) nbR++;
			this.nbR = nbR;
			
			container.setLayout(new GridLayout(1, nbR));
				
			for (int k=0; k<nbR; k++) {
				JLabel ecran[] = new JLabel[r[k].getNbMatchs()];
				JLabel ecran_prono[] = new JLabel[r[k].getNbMatchs()];
				JPanel legend = new JPanel();
				JPanel ligne[] = new JPanel[r[k].getNbMatchs()];
				JPanel lignes = new JPanel();
				
				lignes.setLayout(new GridLayout(8,1));
				lignes.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				
				JLabel legendeMatch = new JLabel();
				legendeMatch.setText("Grille "+ (k+1));
				legendeMatch.setHorizontalAlignment(JLabel.CENTER);
				legend.setLayout(new BorderLayout());
				legend.add(legendeMatch, BorderLayout.CENTER);
				lignes.add(legend);
				
				for (int i=0; i<g.getNbMatchs(); i++) {			
					ecran[i] = new JLabel();
					//ecran[i].setBorder(BorderFactory.createLineBorder(Color.green));
					ecran[i].setText(displayMatch(r[k], i));
					
					ecran_prono[i] = new JLabel();
					ecran_prono[i].setText(displayProno(r[k], i));
					ecran_prono[i].setHorizontalAlignment(JLabel.CENTER);
					
					ligne[i] = new JPanel();
					//ligne[i].setBorder(BorderFactory.createLineBorder(Color.black));
					ligne[i].setLayout(new BorderLayout());
					ligne[i].add(ecran[i], BorderLayout.WEST);
					ligne[i].add(ecran_prono[i], BorderLayout.CENTER);
					lignes.add(ligne[i]);
				}
				container.add(lignes);
			}
			
			
		}
		else {
			weiter = false;
			JOptionPane.showMessageDialog(null, "Oups, �a n'a pas march� !\n �tes vous s�r d'avoir bien rempli la grille ?", "Erreur ! - EnsiStats", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public String displayMatch(Grille g, int i) {
		return "\t\t" +  f.intToString(i+1) + ".\t\t";
	}
	public String displayProno(Grille g, int i) {
		return g.getMatch(i).getPronostique().toString();
	}
}
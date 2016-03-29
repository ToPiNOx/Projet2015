package fr.univavignon.courbes.inter.stats;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.ui.ApplicationFrame;

import fr.univavignon.courbes.stats.stockStats;

public class afficheStats extends ApplicationFrame
{
	final double[][] donnees = new double[5][5];
	int joueur_actuel = 0;

public afficheStats(final String titre)
{
	super(titre);
        
	for (int i = 0; i<2; i++)
	{
		if (i == 0) matrice("Fitz");
		if (i == 1) matrice("Toto");
	}
	System.out.print(donnees[0][0]);
	System.out.print(donnees[1][0]);
        
	final CategoryDataset ensembleDonnees = diagramme();
	final JFreeChart diag = diagramme(ensembleDonnees);

	final ChartPanel diagP = new ChartPanel(diag);
	diagP.setPreferredSize(new java.awt.Dimension(700, 470));
	setContentPane(diagP);
}

private CategoryDataset diagramme()
{
	try {}
	catch (Exception e)
	{
	System.out.println(e.toString());
	}
	
	return DatasetUtilities.createCategoryDataset("Joueur: ", "Statistique: ", donnees);
}

private void matrice(String nom)
{
	try
	{
		InputStream A = new FileInputStream(nom+".txt");
		InputStreamReader B = new InputStreamReader(A);
		BufferedReader C = new BufferedReader(B);
		String ligne;
			
		int i = 0;
		ligne = C.readLine();
		ligne = C.readLine();
		while ((ligne = C.readLine()) != null)
		{
			if (i==4)
			{
				donnees[joueur_actuel][i] = Integer.parseInt(ligne.substring(0));
				donnees[joueur_actuel][i] /= 35;
			}
			else if (i==2 || i==3)
			{
				donnees[joueur_actuel][i] = Integer.parseInt(ligne.substring(0));
				donnees[joueur_actuel][i] /= 7;
			}
			else 
				donnees[joueur_actuel][i] = Integer.parseInt(ligne.substring(0));
				i++;
		}
		C.close();
		joueur_actuel++;
	}
	catch (Exception x)
	{
		System.out.println(x.toString());
	}
}

private JFreeChart diagramme(final CategoryDataset ensembleDonnees)
{
	final JFreeChart diag = ChartFactory.createBarChart3D("Les statistiques joueurs", "Les tatistiques", "Les valeur", ensembleDonnees, PlotOrientation.VERTICAL, true, true, false);
	final CategoryPlot point = diag.getCategoryPlot();
	final CategoryAxis axe = point.getDomainAxis();
	axe.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 8.0));
	final BarRenderer3D restitution = (BarRenderer3D) point.getRenderer();
	restitution.setDrawBarOutline(false);
        
	return diag;

}
 
public static void main(final String[] args)
{
	final afficheStats a = new afficheStats("histogramme 3D");
	a.pack();
	RefineryUtilities.centerFrameOnScreen(a);
	a.setVisible(true);

}
}

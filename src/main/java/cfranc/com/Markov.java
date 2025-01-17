package cfranc.com;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/**
 * La Classe Markov représente un dictionnaire/Annuaire mettant en rélation des couples
 * de mots et des listes de mots pour permettre la génération de texte.
 * @author jm512325
 */
public class Markov {

	HashMap<Couple, List<String>> couples;      // Dictionnaire de couple

        /**
         * Constructeur de Markov (Dictionnaire/Annuaire entre des couples de
         * mots et des listes de chaines de caractères) 
         */
	public Markov() {
		this.couples = new HashMap<Couple, List<String>>();
	}
        
	@SuppressWarnings("unused")
	static <E> E randomElement(Collection<E> c) {
		int n = (int) (Math.random() * c.size());
		for (E x : c)
			if (n-- == 0) {
				return x;

			}
		return null;
	}

        /**
         * Recurpèrer un element (String) aleatoirement dans la liste passé en paramètre
         * @param v : Liste de String
         * @return  : Chaine de caractères aléatoirement sélectionné
         */
	private String randomElement(List<String> v) {
		int n = (int) (Math.random() * v.size());
		return v.get(n);
	}

        /**
         * Lit un fichier depuis un chemin système et génère les couples de mot qu'il contient.
         * @param path : Chemin vers le fichier
         */
	public void readFile(String path) {
		Scanner sc;
		try {
			sc = new Scanner(new File(path));

			String w1 = null;
			String w2 = null;
			Couple prev = new Couple(w1, w2);
			if (sc.hasNext()) {
				w1 = sc.next();
				if (sc.hasNext()) {
					w2 = sc.next();
					prev = new Couple(w1, w2);
					while (sc.hasNext()) {
						String w3 = sc.next();
						List<String> l = this.couples.get(prev);
						if(l == null) {
							l = new ArrayList<String>();
							this.couples.put(prev, l);
						}
						l.add(w3);
						Couple e = new Couple(prev.getSecond(), w3);
						prev = e;
					}
				}
			}
			sc.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

	}

        /**
         * Génère un texte à partir d'un couple de mot et d'un nombre de mot
         * @param p     : Le couple générateur
         * @param words : Nombre de mots à généré.
         * @return le texte généré.
         */
	public String generateText(Couple p, int words) {
		String res = p.getFirst() + " " + p.getSecond() + " ";
	    words -= 2;
	    while (words-- > 0) {
	      List<String> l = this.couples.get(p);
	      if (l == null) {
	    	  break;
	      }
	      String s = randomElement(l);
	      res += s + " ";
	      p = new Couple(p.getSecond(), s);
	    }

		return res;
	}


}

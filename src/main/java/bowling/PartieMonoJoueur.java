package bowling;

import java.util.ArrayList;
import java.util.List;

public class PartieMonoJoueur {

	private final int[][] lancers = new int[10][3]; // Tableau des lancers (max 3 par tour)
	private int tourCourant = 0; // Tour actuel (de 0 à 9)
	private int lancerCourant = 0; // Lancer en cours dans le tour
	private boolean estTerminee = false; // Indique si la partie est terminée

	public PartieMonoJoueur() {
	}

	public boolean enregistreLancer(int nombreDeQuillesAbattues) {
		if (estTerminee) {
			throw new IllegalStateException("La partie est terminée.");
		}

		// Enregistrer le lancer actuel
		lancers[tourCourant][lancerCourant] = nombreDeQuillesAbattues;

		// Gestion des tours normaux (1 à 9)
		if (tourCourant < 9) {
			if (lancerCourant == 0 && nombreDeQuillesAbattues == 10) {
				// Strike : le tour est terminé immédiatement
				tourCourant++;
				lancerCourant = 0;
				return false; // Le tour est terminé
			} else if (lancerCourant == 1) {
				// Deuxième lancer : le tour est terminé
				tourCourant++;
				lancerCourant = 0;
				return false; // Le tour est terminé
			} else {
				// Passe au deuxième lancer
				lancerCourant++;
				return true; // Le tour continue
			}
		} else {
			// Gestion du dernier tour (10ᵉ)
			if (lancerCourant == 0 && nombreDeQuillesAbattues == 10) {
				// Strike au premier lancer
				lancerCourant++;
				return true; // Le tour continue
			} else if (lancerCourant == 1 && (lancers[9][0] + nombreDeQuillesAbattues >= 10)) {
				// Spare au deuxième lancer ou deuxième strike
				lancerCourant++;
				return true; // Le tour continue
			} else if (lancerCourant == 2 || (lancerCourant == 1 && lancers[9][0] + nombreDeQuillesAbattues < 10)) {
				// Fin du jeu après 2 ou 3 lancers
				estTerminee = true;
				return false; // La partie est terminée
			} else {
				lancerCourant++;
				return true; // Le tour continue
			}
		}
	}


	public int score() {
		int scoreTotal = 0;

		for (int i = 0; i < 10; i++) {
			int tourScore = lancers[i][0] + lancers[i][1];

			if (i < 9) {
				// Traitement des 9 premiers tours
				if (lancers[i][0] == 10) {
					// Strike : ajoute les deux prochains lancers
					tourScore += getBonus(i, 2);
				} else if (tourScore == 10) {
					// Spare : ajoute le prochain lancer
					tourScore += getBonus(i, 1);
				}
			} else {
				// Traitement du dernier tour
				tourScore += lancers[i][2]; // Inclure le troisième lancer
			}

			scoreTotal += tourScore;
		}

		return scoreTotal;
	}

	
	private int getBonus(int tourIndex, int throwsToCount) {
		int bonus = 0;
		int throwsCounted = 0;

		for (int i = tourIndex + 1; i < 10 && throwsCounted < throwsToCount; i++) {
			for (int j = 0; j < 3 && throwsCounted < throwsToCount; j++) {
				if (lancers[i][j] > 0) {
					bonus += lancers[i][j];
					throwsCounted++;
				}
			}
		}

		return bonus;
	}
	

	public boolean estTerminee() {
		return estTerminee;
	}

	public int numeroTourCourant() {
		return estTerminee ? 0 : tourCourant + 1;
	}

	public int numeroProchainLancer() {
		return estTerminee ? 0 : lancerCourant + 1;
	}
}

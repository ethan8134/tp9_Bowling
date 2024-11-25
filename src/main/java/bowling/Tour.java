package bowling;

public class Tour {
	private final int[] lancers = new int[3]; // Jusqu'à 3 lancers par tour (dernier tour inclus)
	private int lancerActuel = 0; // Lancer en cours dans le tour
	private boolean estStrike = false; // Indique si le tour est un strike
	private boolean estSpare = false; // Indique si le tour est un spare

	public void ajouterLancer(int nombreDeQuilles) {
		if (lancerActuel >= 3) {
			throw new IllegalStateException("Impossible d'ajouter un autre lancer à ce tour.");
		}

		lancers[lancerActuel] = nombreDeQuilles;
		lancerActuel++;

		// Vérifie si le tour est un strike ou un spare
		if (lancerActuel == 1 && nombreDeQuilles == 10) {
			estStrike = true;
		} else if (lancerActuel == 2 && lancers[0] + lancers[1] == 10) {
			estSpare = true;
		}
	}

	public int calculerScoreSuivant(int[] lancersSuivants) {
		int score = lancers[0] + lancers[1];
		if (estStrike) {
			score += lancersSuivants[0] + lancersSuivants[1];
		} else if (estSpare) {
			score += lancersSuivants[0];
		}
		return score;
	}

	public boolean estTermine(boolean dernierTour) {
		if (dernierTour) {
			// Dernier tour : terminé après 3 lancers ou 2 lancers sans bonus
			return lancerActuel == 3 || (lancerActuel == 2 && lancers[0] + lancers[1] < 10);
		} else {
			// Tours normaux : terminé après un strike ou 2 lancers
			return estStrike || lancerActuel == 2;
		}
	}

	public boolean isStrike() {
		return estStrike;
	}

	public boolean isSpare() {
		return estSpare;
	}

	public int[] getLancers() {
		return lancers;
	}

	public int getNombreLancers() {
		return lancerActuel;
	}
}

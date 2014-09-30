package logic;

import model.Contribution;
import dao.ContributionDAO;

public class EntryLogic {
	public boolean excute(Contribution cont) {
		ContributionDAO dao = new ContributionDAO();
		Contribution entry = dao.entryContributions(cont);
		return entry != null;
	}
}

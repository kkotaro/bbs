package logic;

import javax.servlet.http.HttpServletRequest;

import model.Contribution;
import dao.ContributionDAO;

public class EntryLogic {
	
	public HttpServletRequest request;
	public int userId;
	public String released;
	
	public EntryLogic(HttpServletRequest request, int userId) {
		this.request = request;
		this.userId = userId;
	}
	
	public boolean excute() {
		if (request.getParameter("conReleasedAppoint") != null) {
			this.released = formatTime(request.getParameter("year"), request.getParameter("month"), 
					request.getParameter("day"), request.getParameter("hour"), request.getParameter("minute"));
		}
		Contribution cont = new Contribution(request.getParameter("title"),
									request.getParameter("text"), userId, this.released);
		ContributionDAO dao = new ContributionDAO();
		Contribution entry = dao.entryContributions(cont);
		return entry != null;
	}
	
	private String formatTime(String year, String month, String day, String hour, String minute) {
		return String.format("%d-%02d-%02d %02d:%02d:00", Integer.parseInt(year), Integer.parseInt(month), 
				Integer.parseInt(day), Integer.parseInt(hour), Integer.parseInt(minute));
	}
}

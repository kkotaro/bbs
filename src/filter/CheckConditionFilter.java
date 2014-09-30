package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;
import dao.AccountDAO;

/**
 * Servlet Filter implementation class CheckConditionFilter
 */
@WebFilter(filterName = "CheckConditionFilter",
           urlPatterns = {
				"/home",
				"/Comment",
				"/ControlUserServlet", 
				"/entry", 
				"/ManageUsers",
				"/RegistUser"
             }
)
public class CheckConditionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CheckConditionFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		try{
			//操作途中で凍結された時
			HttpSession session = ((HttpServletRequest)request).getSession();
			AccountDAO account = new AccountDAO();
			if (session.getAttribute("userInfo") != null) { //ログイン時にセッションに格納されたユーザ情報を取得した
				Account userInfo = (Account) session.getAttribute("userInfo");
				int userId = userInfo.getUserId();
				//データベースからユーザIDの状態を取得
				int userCondition = account.CheckCondition(userId);
				if (userCondition == 1) { //停止されているとき
					//ログアウトにリダイレクト
					((HttpServletResponse) response).sendRedirect("/bbs/Logout");
					return;
				}
			}
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}catch (ServletException se){
			
		}catch (IOException e){
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}

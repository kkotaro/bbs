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

/**
 * Servlet Filter implementation class CheckLoginFilter
 * ログインチェックのフィルター
 */
@WebFilter(filterName = "CheckLoginFilter",
urlPatterns = {
		"/home",
		"/Comment",
		"/ControlUserServlet", 
		"/entry", 
		"/ManageUsers",
		"/RegistUser"
  }
)
public class CheckLoginFilter implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//セッション
		HttpSession session = ((HttpServletRequest)request).getSession();
		if (session.getAttribute("userInfo") == null) { //ログイン時にセッションに格納するユーザ情報が入っていないとき
			//ログアウトにリダイレクト
			((HttpServletResponse) response).sendRedirect("/bbs/Logout");
			return;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}
}

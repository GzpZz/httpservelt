package web.filter;

import com.filter.FilterConfig;
import com.filter.HttpFilter;
import com.filter.HttpFilterChain;
import com.request.HttpRequest;
import com.response.HttpResponse;

public class EncodingFilter implements HttpFilter {
	
	String encoding = null;

	@Override
	public void init(FilterConfig config) {
		encoding = config.getParameter("encoding");
	}

	@Override
	public void doFilter(HttpRequest rq, HttpResponse rp, HttpFilterChain chain) {
		System.out.println("coming ....");
		rq.setCharacterEncoding(encoding);
		chain.doFilter(rq, rp);
		System.out.println("leaving ....");
	}

}

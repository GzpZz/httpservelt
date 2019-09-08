package web.filter;

import com.filter.FilterConfig;
import com.filter.HttpFilter;
import com.filter.HttpFilterChain;
import com.request.HttpRequest;
import com.response.HttpResponse;

public class TestFilter implements HttpFilter {

	@Override
	public void init(FilterConfig config) {
		
	}

	@Override
	public void doFilter(HttpRequest rq, HttpResponse rp, HttpFilterChain chain) {
		System.out.println("testFilter before");
		chain.doFilter(rq, rp);
		System.out.println("testFilter after");
	}

}

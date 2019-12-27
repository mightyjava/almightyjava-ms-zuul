# almightyjava-ms-zuul
almightyjava microservices zuul

# Almighty Java
    URL - http://localhost:8080/mightyjava
    UserName - mightyjava
    Password - password

# Book Rest API
    URL - http://localhost:8081/rest/books
    Zuul URL - http://localhost:8080/mightyjava/rest/books

# Zuul API Gateway
Here are the steps need to follow to enable Zuul API Gateway

1 - pom.xml - zuul dependency

    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>

2 - application.properties

    zuul.routes.book-rest-api.path=/rest/books/**
    zuul.routes.book-rest-api.url=http://localhost:8081/rest/books

3 - Application.java

    1. Add @EnableZuulProxy annotation at class level
    2. Create @Bean 

    @Bean
	public ZuulFilter postFilter() {
		return new ZuulFilter() {

			@Override
			public boolean shouldFilter() {
				return true;
			}

			@Override
			public Object run() throws ZuulException {
				System.out.println("Pre Filter - run");
				HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
				System.out.println("Request Method : "+request.getMethod());
				System.out.println("Request URL : "+request.getRequestURL().toString());
				return null;
			}

			@Override
			public String filterType() {
				return "pre";
			}

			@Override
			public int filterOrder() {
				return 1;
			}
		};
	}
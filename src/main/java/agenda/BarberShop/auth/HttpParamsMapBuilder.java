package agenda.BarberShop.auth;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


public class HttpParamsMapBuilder {
	
	 private final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

	    public static HttpParamsMapBuilder builder(){
	        return new HttpParamsMapBuilder();
	    }

	    public HttpParamsMapBuilder withClient(String clientId){
	        params.add("client_id", clientId);
	        return this;
	    }

	    public HttpParamsMapBuilder withSecret(String clientSecret){
	        params.add("client_secret", clientSecret);
	        return this;
	    }

	    public HttpParamsMapBuilder withGrantType(String grantType){
	        params.add("grant_type", grantType);
	        return this;
	    }


	    public HttpParamsMapBuilder withUsername(String username){
	        params.add("username", username);
	        return this;
	    }

	    public HttpParamsMapBuilder withPassword(String password){
	        params.add("password", password);
	        return this;
	    }

	    public HttpParamsMapBuilder withRefreshToken(String refreshToken){
	        params.add("refresh_token", refreshToken);
	        return this;
	    }

	    public MultiValueMap<String, String> build(){
	        return params;
	    }

}

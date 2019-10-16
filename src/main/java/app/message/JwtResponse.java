package app.message;

public class JwtResponse {
	 private String token;
	    private String type = "Bearer";

	    public JwtResponse(String accessToken) {
	        this.token = accessToken;
	    }

	public JwtResponse( String type, String token) {
		this.token = token;
		this.type = type;
	}

	public String getAccessToken() {
	        return token;
	    }

	    public void setAccessToken(String accessToken) {
	        this.token = accessToken;
	    }

	    public String getTokenType() {
	        return type;
	    }

	    public void setTokenType(String tokenType) {
	        this.type = tokenType;
	    }
}

package decider;

public class Response {
	private Boolean accept;
	private String stacion;
	
	public Response(Boolean a, String r) {
		accept = a;
		stacion = r;
	}
	
	public Boolean getAccept() {
		return accept;
	}
	
	public String getReject() {
		return stacion;
	}
	
	public void print(String userId, String state) {
		if(state.contentEquals("Go")) {
			System.out.println(userId +  " Aceitou: "+accept +" e partiu da estação: " + stacion);
		}
		else if(state.contentEquals("Leave")) {
			System.out.println(userId +  " Aceitou: "+accept +" e chegou a estação: " + stacion);
		}
	}
}
package payload;

import java.io.Serializable;

public class DocumentPayloadDTO implements Serializable {
	private boolean ok;
	private ResultDTO result;

	public void setOk(boolean ok){
		this.ok = ok;
	}

	public boolean isOk(){
		return ok;
	}

	public void setResult(ResultDTO result){
		this.result = result;
	}

	public ResultDTO getResult(){
		return result;
	}

	@Override
 	public String toString(){
		return 
			"DocumentPayloadDTO{" + 
			"ok = '" + ok + '\'' + 
			",result = '" + result + '\'' + 
			"}";
		}
}
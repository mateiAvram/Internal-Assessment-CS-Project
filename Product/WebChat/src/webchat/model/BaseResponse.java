package webchat.model;

public class BaseResponse {

	/**
	 * The error code can be 0 (if ok) or 999 (for now) which means there are some problems on the server-side.
	 * Of course, it is possible to create more error codes to better identify the exact problem.
	 */
	private int errorCode;
	
	/**
	 * The error message is the error description generated by the exception raised
	 * (for instance: a request to receive an account with an accountkID for which there is no account in the database)
	 */
	private String errorMessage;
	
	/**
	 * Default constructor
	 */
	public BaseResponse() {
		this.errorCode = 0;
		this.errorMessage = "OK";
	}
	
	/**
	 * @return the errorCode
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return the data
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * @param data the data to set
	 */
	public void setErrorMessage(String data) {
		this.errorMessage = data;
	}
	
	public void setErrorByException(Exception e) {
		this.errorCode = 999;
		this.errorMessage = e.getMessage();
	}


	
	
}

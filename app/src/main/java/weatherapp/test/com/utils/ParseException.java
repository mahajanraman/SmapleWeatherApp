package weatherapp.test.com.utils;


public class ParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6960356363457525853L;

	private final String message;
	private Object tag;
	private int serverCode;

    public Object getTag() {
        return tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public ParseException(String message, int serverCode) {

		this.message = message;
		this.serverCode = serverCode;
	}

	public int getServerCode() {
		return serverCode;
	}

	/**
	 * 
	 */
	public String getMessage() {
		return message;
	}


	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder("[ParseException] ");

		if (serverCode != 0) {
			builder.append(" Server Code: " + serverCode);
		}
		builder.append(" Message: " + message);
		return builder.toString();
	}

	/**
	 * 
	 * @param e
	 * @return
	 */
	public static ParseException makeException(Exception e) {
		if (e instanceof ParseException) {
			return (ParseException) e;
		}
		return new ParseException(e.getMessage(), -1);
	}

}

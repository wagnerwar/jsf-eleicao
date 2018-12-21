package pacote.utils;

public class Utils {
	public static String getExtension(String contentType) {
		String retorno = "";
		switch(contentType) {
		case "image/jpeg":
			retorno = "jpg";
			break;
		case "image/gif":
			retorno = "gif";
			break;
		case "image/png":
			retorno = "png";
			break;
		default:
			retorno = "";
		}
		return retorno;
	}

}

package pe.gob.pj.depositos.domain.utils;

public class ProjectConstants {

	public static final String METHOD_CORTA_ULTIMA_BARRA_INVERTIDA = "PUT";
	
	public static final int DEFAULT_PAGINATION_PAGE_SIZE = 10;
	
	public static final int NRO_VECES_REFRESH_CON_TOKEN_EXPIRADO = 100;
	
	public static final String APLICATIVO_NOMBRE = "seguimientoDepositosJudiciales-api";
	public static final String APLICATIVO_VERSION = "1.0.0";
	
	public class Esquema {
		public static final String SIJ_002 = "\"USRSINAREJ\"";
	}
	
	public static final String AUDITORIA_B_INSTER = "I"; 
	
	public static final String ESTADO_ACTIVO = "1";
	public static final String ESTADO_ACTIVO_S = "S";
	public static final String ESTADO_DJ_PENDIENTE = "P";
	public static final String ESTADO_DJ_COMPLETO = "C";
	
	
	public static final String ESTADO_DJ_C = "C";
	public static final String ESTADO_DJ_D = "D";
	public static final String ESTADO_DJ_P = "P";
	public static final String ESTADO_DJ_Q = "Q";
	public static final String ESTADO_DJ_E = "E";
	
	public static final String ESTADO_OP_X = "X";
	public static final String ESTADO_OP_C = "C";
	public static final String ESTADO_OP_F = "F";

	public static final String DESCRIPCION_ESTADO_DJ_C = "Deposito cobrado por completo";
	public static final String DESCRIPCION_ESTADO_DJ_D = "Depositado en banco";
	public static final String DESCRIPCION_ESTADO_DJ_P = "Se encuentra en la corte superior - mesa de partes";
	public static final String DESCRIPCION_ESTADO_DJ_Q = "Deposito cobrado parcialmente";
	public static final String DESCRIPCION_ESTADO_DJ_E = "Deposito Extornado";
	
	public static final String PATTERN_NUMBER = "\\d+";
	public static final String PATTERN_ALPHANUMBER = "[a-zA-Z0-9]+";
	public static final String PATTERN_S_N = "[SN]";
	public static final String PATTERN_FORMATO_EXPEDIENTE = "(\\d{5})[-](\\d{4})[-](\\d{1,4})[-](\\d{4})[-]([A-Za-z]{2})[-]([A-Za-z]{2})[-](\\d{2})";
	public static final String PATTERN_FECHA = "([0][1-9]|[1-2][0-9]|[3][0-1])/([0][1-9]|[1][0-2])/(\\d{4})";
	public static final String PATTERN_FECHA_DD_MM_YYYY_HH_MM_SS_SSS = "(\\d{2})/(\\d{2})/(\\d{4}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})";
	public static final String PATTERN_FECHA_YYYY_MM_DD_HH_MM_SS_SSS = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})\\.(\\d{3})";
	public static final String PATTERN_EMAIL = "([A-Za-z0-9]+[._-]?[A-Za-z0-9]+)+@([A-Za-z0-9]+[-]?[A-Za-z0-9]+\\.)*[A-Za-z0-9]{2,6}";
	public static final String PATTERN_IP = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	public static final String PATTERN_MAC = "([0-9A-F]{2}[:-]){5}([0-9A-F]{2})";

	public static final String FORMATO_FECHA_YYYYMMDD = "yyyyMMdd";
	public static final String FORMATO_FECHA_YYYY_MM_DD = "yyyy/MM/dd";
	public static final String FORMATO_FECHA_YYYY_MM_DD_ = "yyyy-MM-dd";
	public static final String FORMATO_FECHA_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM_AMPM = "dd/MM/yyyy hh:mm a";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM = "dd/MM/yyyy HH:mm";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS = "dd/MM/yyyy hh:mm:ss";
	public static final String FORMATO_FECHA_DD_MM_YYYY_HH_MM_SS_SSS="dd/MM/yyyy HH:mm:ss.SSS";
	public static final String FORMATO_FECHA_YYYY_MM_DD_HH_MM_SS_SSS="yyyy-MM-dd HH:mm:ss.SSS";

	public static final String AUD_CUO = "auditoriaCuo";
	public static final String AUD_IP = "auditoriaIp";
	public static final String AUD_JWT = "auditoriaJwt";
	public static final String REMOTE_IP = "ipRemota";
	public static final String TOKEN_ACCESO_EXTERNO = "out";
	public static final String TOKEN_ACCESO_INTERNO = "in";
	public static final String TOKEN_ACCESO_NEUTRO = "io";
	public static final String TOKEN_OPERACION_SPLIT = "@";

	public static final String CLAIM_ROL = "rol";
	public static final String CLAIM_USUARIO = "usuario";
	public static final String CLAIM_IP = "remoteIp";
	public static final String CLAIM_NUMERO = "numero";
	public static final String CLAIM_ACCESO = "log_";
	public static final String CLAIM_LIMIT= "limit";

	public static final String C_500 = "500";
	public static final String C_404 = "404";
	public static final String C_405 = "405";
	public static final String C_200 = "200";
	public static final String C_400 = "400";
	public static final String C_401 = "401";
	public static final String C_403 = "403";
	
	public static final String C_ERROR = "E";
	public static final String C_EXITO  = "0000";
	public static final String C_0001 = "0001";
	public static final String C_E000 = "E000";
	public static final String C_E001 = "E001";
	public static final String C_E002 = "E002";
	public static final String C_E003 = "E003";
	public static final String C_E004 = "E004";
	public static final String C_E005 = "E005";
	public static final String C_E013 = "E013";
	public static final String C_E014 = "E014";
	public static final String C_E015 = "E015";

	public static final String X_TRAZA_LOG ="TRAZA-LOG";	
	public static final String X_CML_NOT_FOUND = "ERROR: No se puede identificar las CLASE-METODO-LINEA.";
	
	public static final String X_EXITO = "La operación se realizo de manera exitosa.";
	public static final String X_0001 = "Datos no encontrados al ";
	public static final String X_E000 = " - Error inesperado.";
	public static final String X_E001 = "Lo sentimos, la sesión ha expirado.";
	public static final String X_E002 = "Lo sentimos, hubo un problema al validar el token.";
	public static final String X_E003 = "Error al generar un nuevo token, el tiempo límite para refrescar el token enviado a expirado.";
	public static final String X_ERROR = "Error al ";
	public static final String X_E004 = " - Problemas en la ejecución del SP: ";
	public static final String X_E005 = " - No se estableció conexión con la BD PJ Seguridad.";
	public static final String X_E013 = " - Problemas con el consumo del endpoint: ";
	public static final String X_E014 = " - El token captcha es nulo o no es válido. ";
	public static final String X_E015 = " - problemas en la ejecución de la sentencia JPA. ";
	

	
	public class Proceso {
		public static final String CONSULTA_CEJ_UNICO= "Consultar al servicio CEJ";
		public static final String CONSULTA_DEPOSITO_JUDICIAL = "Consultar Deposito Judicial";
		public static final String CONSULTA_ORDEN_PAGO = "Consultar Orden de pago";
	}
	
	public class Mensajes {
		public static final String MSG_ERROR_GENERICO_CONVERSION = "El tipo de dato de entrada es incorrecto";
	}

}

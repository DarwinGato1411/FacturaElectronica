package com.ec.untilitario;

import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.DetalleCompra;
import com.ec.entidad.Parametrizar;
import com.ec.entidad.Producto;
import com.ec.entidad.Proveedores;
import com.ec.entidad.TipoIdentificacionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.entidad.sri.CabeceraCompraSri;
import com.ec.entidad.sri.DetalleCompraSri;
import com.ec.seguridad.EnumSesion;
import com.ec.seguridad.UserCredential;
import com.ec.servicio.HelperPersistencia;
import com.ec.servicio.ServicioEstadoFactura;
import com.ec.servicio.ServicioParametrizar;
import com.ec.servicio.ServicioProducto;
import com.ec.servicio.ServicioProveedor;
import com.ec.servicio.ServicioTipoIdentificacionCompra;

import ec.gob.sri.comprobantes.util.xml.LectorXPath;
import ec.gob.sri.comprobantes.util.xml.XStreamUtil;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.aut.Autorizacion;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import javax.persistence.EntityManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.w3c.dom.Node;
import org.zkoss.zk.ui.Executions;

import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathExpressionException;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import org.w3c.dom.DOMException;

import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.List;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.tls.Certificates;

import okhttp3.tls.HandshakeCertificates;

public class ArchivoUtils {

      static final String certGestDoc = "-----BEGIN CERTIFICATE-----\n"
            + "MIIGQDCCBSigAwIBAgIQVki3e1buTq5ZzIKsz3j3oDANBgkqhkiG9w0BAQsFADBM\n"
            + "MQswCQYDVQQGEwJMVjENMAsGA1UEBxMEUmlnYTERMA8GA1UEChMIR29HZXRTU0wx\n"
            + "GzAZBgNVBAMTEkdvR2V0U1NMIFJTQSBEViBDQTAeFw0yNTA1MjIwMDAwMDBaFw0y\n"
            + "NjA1MDMyMzU5NTlaMCUxIzAhBgNVBAMMGiouZ2VzdGlvbmRvY3VtZW50YWwuZ29i\n"
            + "LmVjMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0QxPvmhtStV0PxwB\n"
            + "xRJFxnE6bv3XZm7b9XfOHyI+6CZwtH69YA1ajj4xuex7I2mArRvilA52EcVj9Jm8\n"
            + "Y9FE+F56n9b28HYT8vh0MldpXJF/PciLRcwDJyinRX5qrLz088LSVhY4Btkcfo+Y\n"
            + "rlveIy0Kp2PP8JIwVgTNQTezHhHjVAiKfu/HDacd/6j9PWUFfVQ3vqcudUgtnqBM\n"
            + "TuEagXYl9hp5MK6VaSdkZFBqZSrhtwYGIQuKUJIZV+AjC+6I7iolQ+GfJcXFy74S\n"
            + "/GXllkLIl/wsiTR2fGQCGdscmpqUL8gHQ4SShSYPVj0Fz0BgCM2dkEicWiB50+rU\n"
            + "tzpBJQIDAQABo4IDQzCCAz8wHwYDVR0jBBgwFoAU+ftQxItnu2dk/oMhpqnOP1WE\n"
            + "k5kwHQYDVR0OBBYEFEufHEbmui4qMHEaoRIfs+Sz7ZkfMA4GA1UdDwEB/wQEAwIF\n"
            + "oDAMBgNVHRMBAf8EAjAAMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjBL\n"
            + "BgNVHSAERDBCMDYGCysGAQQBsjEBAgJAMCcwJQYIKwYBBQUHAgEWGWh0dHBzOi8v\n"
            + "Y3BzLnVzZXJ0cnVzdC5jb20wCAYGZ4EMAQIBMD0GA1UdHwQ2MDQwMqAwoC6GLGh0\n"
            + "dHA6Ly9jcmwudXNlcnRydXN0LmNvbS9Hb0dldFNTTFJTQURWQ0EuY3JsMG8GCCsG\n"
            + "AQUFBwEBBGMwYTA4BggrBgEFBQcwAoYsaHR0cDovL2NydC51c2VydHJ1c3QuY29t\n"
            + "L0dvR2V0U1NMUlNBRFZDQS5jcnQwJQYIKwYBBQUHMAGGGWh0dHA6Ly9vY3NwLnVz\n"
            + "ZXJ0cnVzdC5jb20wPwYDVR0RBDgwNoIaKi5nZXN0aW9uZG9jdW1lbnRhbC5nb2Iu\n"
            + "ZWOCGGdlc3Rpb25kb2N1bWVudGFsLmdvYi5lYzCCAYAGCisGAQQB1nkCBAIEggFw\n"
            + "BIIBbAFqAHcAlpdkv1VYl633Q4doNwhCd+nwOtX2pPM2bkakPw/KqcYAAAGW+KhD\n"
            + "uAAABAMASDBGAiEAobFYf4s7QRgSo8tKMcecW+XTc7V+0N5nysqOQCDktSECIQDC\n"
            + "/JatbrksmjOfsZS89G7XB0JWRPzQeNzB4l7TXfMmtAB2ABmG1Mcoqm/+ugNveCpN\n"
            + "AZGqzi1yMQ+uzl1wQS0lTMfUAAABlvioQ5gAAAQDAEcwRQIgK8oU3AjmkXbs19T2\n"
            + "KCyxEGDlfI4tYR0lOqUDR82oWtICIQDOnISqu4ZUFGHKFHq2Tpdk/x9+YrKLiIR3\n"
            + "44P163UqtAB3AA5XlLzzrqk+MxssmQez95Dfm8I9cTIl3SGpJaxhxU4hAAABlvio\n"
            + "Q88AAAQDAEgwRgIhAOpQBJHxU5Ekb8XHVjDtGcp4SfZKcUFFFRVofUH0mUcZAiEA\n"
            + "/3UxFyERJCjl1CFYW9R8kEBmwmXTKFoflYSiCMdciDYwDQYJKoZIhvcNAQELBQAD\n"
            + "ggEBAFpVCid5EJpjt+SFUik6I433Iy9IP698w1IDOChWZKDzKsnIBzlo9Njl66yT\n"
            + "Mzw6Y4BQU56UC9KB7p6gh88Sgb6SdBgZ8uSXcIHBVzgDKJ7jHVamPEcWrWGOIHDe\n"
            + "u/T12Qu47dq98dcOrsEu89GrJA9RzYtuEqn3N6w0KSOhqX42Cjf8AeDmFmLFYIQL\n"
            + "JEjM//VzYUnclOTbKqBnj3xl9iH2e/U/uimx0Up3qiVbiGLS57OYinI5YQ12G9kI\n"
            + "mLqhtFcX7roWCtqzvr0jwCYjg5IfYouQwlyRlz0NclqO5Yxu9MoQIa7tZq1lucKp\n"
            + "ujSKzfd5RrKuTce856Lcs8FcB0E=\n"
            + "-----END CERTIFICATE-----";

    static final String certSRI = "-----BEGIN CERTIFICATE-----\n"
            + "MIIG7DCCBdSgAwIBAgIQDq1uovBFe87I1sEjFGd/rTANBgkqhkiG9w0BAQsFADBZ\n"
            + "MQswCQYDVQQGEwJVUzEVMBMGA1UEChMMRGlnaUNlcnQgSW5jMTMwMQYDVQQDEypE\n"
            + "aWdpQ2VydCBHbG9iYWwgRzIgVExTIFJTQSBTSEEyNTYgMjAyMCBDQTEwHhcNMjQx\n"
            + "MjIzMDAwMDAwWhcNMjYwMTIzMjM1OTU5WjB0MQswCQYDVQQGEwJFQzEOMAwGA1UE\n"
            + "CBMFQXp1YXkxDzANBgNVBAcTBkN1ZW5jYTEkMCIGA1UEChMbU0VSVklDSU8gREUg\n"
            + "UkVOVEFTIElOVEVSTkFTMR4wHAYDVQQDExVzcmllbmxpbmVhLnNyaS5nb2IuZWMw\n"
            + "ggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCx3qNrIfml3GXdXYcWu1Th\n"
            + "YPSEjL24JORo3hgjkaYRBmFKtBeWtmNNnE8Mp3J+aIF2cnMx2fRwWUXoryG2X/Be\n"
            + "vZYxEAZfM6OXqo61HA+zqujdlLoiMtNPbdKMQoulHNyMFXDF8KvcksWQUsNro6aZ\n"
            + "SchuPDtzVFZWskXVXR6KAds/aBJWtSYmGWPhkNN9IdUX297vIPYozy6p1WwcXoMZ\n"
            + "tRZgjh5PHvjZA6tiHZFfjddjjIiyf/P3cAmjSvY/ARjTVJi6FK0QvlbKIWv84p2z\n"
            + "mDF9QojghaR1svRe196BksY5Oaxj48HRn2YfyCM2YA6xZV4RrqsGh1a1RtQ9yv3t\n"
            + "AgMBAAGjggOTMIIDjzAfBgNVHSMEGDAWgBR0hYDAZsffN97PvSk3qgMdvu3NFzAd\n"
            + "BgNVHQ4EFgQUiJfMAB34G5Xj24SHgTzknw2LCIUwIAYDVR0RBBkwF4IVc3JpZW5s\n"
            + "aW5lYS5zcmkuZ29iLmVjMD4GA1UdIAQ3MDUwMwYGZ4EMAQICMCkwJwYIKwYBBQUH\n"
            + "AgEWG2h0dHA6Ly93d3cuZGlnaWNlcnQuY29tL0NQUzAOBgNVHQ8BAf8EBAMCBaAw\n"
            + "HQYDVR0lBBYwFAYIKwYBBQUHAwEGCCsGAQUFBwMCMIGfBgNVHR8EgZcwgZQwSKBG\n"
            + "oESGQmh0dHA6Ly9jcmwzLmRpZ2ljZXJ0LmNvbS9EaWdpQ2VydEdsb2JhbEcyVExT\n"
            + "UlNBU0hBMjU2MjAyMENBMS0xLmNybDBIoEagRIZCaHR0cDovL2NybDQuZGlnaWNl\n"
            + "cnQuY29tL0RpZ2lDZXJ0R2xvYmFsRzJUTFNSU0FTSEEyNTYyMDIwQ0ExLTEuY3Js\n"
            + "MIGHBggrBgEFBQcBAQR7MHkwJAYIKwYBBQUHMAGGGGh0dHA6Ly9vY3NwLmRpZ2lj\n"
            + "ZXJ0LmNvbTBRBggrBgEFBQcwAoZFaHR0cDovL2NhY2VydHMuZGlnaWNlcnQuY29t\n"
            + "L0RpZ2lDZXJ0R2xvYmFsRzJUTFNSU0FTSEEyNTYyMDIwQ0ExLTEuY3J0MAwGA1Ud\n"
            + "EwEB/wQCMAAwggGABgorBgEEAdZ5AgQCBIIBcASCAWwBagB3AJaXZL9VWJet90OH\n"
            + "aDcIQnfp8DrV9qTzNm5GpD8PyqnGAAABk/D5VK4AAAQDAEgwRgIhAMxhciSMAh/K\n"
            + "naI237TBD7qcK2sNR+Yrxp33dqKSLILoAiEA2UssmF0Ht6eUovbbIWLN1aQKtd8H\n"
            + "r1vBX6UWUoRGMpIAdgBkEcRspBLsp4kcogIuALyrTygH1B41J6vq/tUDyX3N8AAA\n"
            + "AZPw+VSNAAAEAwBHMEUCIQD0TLLzkg10q58G7qdlf5ug2OBvkpUsxznFVjb5S0+i\n"
            + "HwIgfRb8cLoqf8pQycew38pE9lO5vXi77L7U6OqyO4FwceQAdwBJnJtp3h187Pw2\n"
            + "3s2HZKa4W68Kh4AZ0VVS++nrKd34wwAAAZPw+VSgAAAEAwBIMEYCIQCBxShySgnw\n"
            + "IokvNdWDrWLhsmwv/7YtXgwB5YeEBKTA/QIhAKFXnwA41iIBExm0xID3oMsHfLx8\n"
            + "NmZtYrog0p+NkT42MA0GCSqGSIb3DQEBCwUAA4IBAQB+Lzwu+Coogn4fzmMPTSER\n"
            + "V1nr/lIOqJZnGbLeqVD+5o19aYexqDquVjdMIojHwrAy7Xx7Jr0wk6R5fGO9FF2i\n"
            + "tGnwOPufIwXeQa2c2mYFPvly4boC8Gga3unKVdQ+STxZe4Dueel0QU23slBA17nb\n"
            + "ymPhkwZ7RUTmqbRbNur054EidD9oRNLUrj+ED262sUBQsz1OrYopRqYHaFmQzCC+\n"
            + "5iLU7Zfcvzaq+SEaxtGXD2oIuR6wTSr8Em1ad5hli7CtcpPNP5yn+jFhE8xT7t54\n"
            + "N6Cd6ThtNlsLoQfEGP0rUXgllxVh8z/0twIEdZfTfNnrc7eZdNMyt4zdS81EZYaK\n"
            + "-----END CERTIFICATE-----";


    private static UserCredential credential = new UserCredential();
    private static ServicioEstadoFactura servicioEstadoFactura = new ServicioEstadoFactura();
    private static ServicioProveedor servicioProveedor = new ServicioProveedor();
    private static ServicioTipoIdentificacionCompra servicioTipoIdentificacionCompra = new ServicioTipoIdentificacionCompra();
    private static ServicioProducto servicioProducto = new ServicioProducto();
    private static ServicioParametrizar servicioParametrizar = new ServicioParametrizar();

    public ArchivoUtils() {
        Session sess = Sessions.getCurrent();
        credential = (UserCredential) sess.getAttribute(EnumSesion.userCredential.getNombre());

    }

    public static String archivoToString(String rutaArchivo) {
        /*  70 */ StringBuffer buffer = new StringBuffer();
        try {
            /*  73 */ FileInputStream fis = new FileInputStream(rutaArchivo);
            /*  74 */ InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            /*  75 */ Reader in = new BufferedReader(isr);
            int ch;
            /*  77 */ while ((ch = in.read()) > -1) {
                /*  78 */ buffer.append((char) ch);
            }
            /*  80 */ in.close();
            /*  81 */ return buffer.toString();
        } catch (IOException e) {
            /*  83 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
            /*  84 */        }
        return null;
    }

    public static File stringToArchivo(String rutaArchivo, String contenidoArchivo) {
        /*  97 */ FileOutputStream fos = null;
        /*  98 */ File archivoCreado = null;
        try {
            /* 102 */ fos = new FileOutputStream(rutaArchivo);
            /* 103 */ OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
            /* 104 */ for (int i = 0; i < contenidoArchivo.length(); i++) {
                /* 105 */ out.write(contenidoArchivo.charAt(i));
            }
            /* 107 */ out.close();

            /* 109 */ archivoCreado = new File(rutaArchivo);
        } catch (Exception ex) {
            int i;
            /* 112 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            /* 113 */ return null;
        } finally {
            try {
                /* 116 */ if (fos != null) /* 117 */ {
                    fos.close();
                }
            } catch (Exception ex) {
                /* 120 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* 123 */ return archivoCreado;
    }

    public static byte[] archivoToByte(File file)
            throws IOException {
        /* 136 */ byte[] buffer = new byte[(int) file.length()];
        /* 137 */ InputStream ios = null;
        try {
            /* 139 */ ios = new FileInputStream(file);
            /* 140 */ if (ios.read(buffer) == -1) /* 141 */ {
                throw new IOException("EOF reached while trying to read the whole file");
            }
        } finally {
            try {
                /* 145 */ if (ios != null) /* 146 */ {
                    ios.close();
                }
            } catch (IOException e) {
                /* 149 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        /* 153 */ return buffer;
    }

    public static boolean byteToFile(byte[] arrayBytes, String rutaArchivo) {
        /* 164 */ boolean respuesta = false;
        try {
            /* 166 */ File file = new File(rutaArchivo);
            /* 167 */ file.createNewFile();
            /* 168 */ FileInputStream fileInputStream = new FileInputStream(rutaArchivo);
            /* 169 */ ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayBytes);
            /* 170 */ OutputStream outputStream = new FileOutputStream(rutaArchivo);
            int data;
            /* 173 */ while ((data = byteArrayInputStream.read()) != -1) {
                /* 174 */ outputStream.write(data);
            }

            /* 177 */ fileInputStream.close();
            /* 178 */ outputStream.close();
            /* 179 */ respuesta = true;
        } catch (IOException ex) {
            /* 181 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 183 */ return respuesta;
    }

    public static String obtenerValorXML(File xmlDocument, String expression) {
        /* 195 */ String valor = null;
        try {
            /* 198 */ LectorXPath reader = new LectorXPath(xmlDocument.getPath());
            /* 199 */ valor = (String) reader.leerArchivo(expression, XPathConstants.STRING);

        } catch (Exception e) {
            /* 202 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        /* 205 */ return valor;
    }

    public static Object obtenerValorXMLObject(File xmlDocument, String expression) {
        /* 195 */ Object valor = null;
        try {
            /* 198 */ LectorXPath reader = new LectorXPath(xmlDocument.getPath());
            /* 199 */ valor = reader.leerArchivo(expression, XPathConstants.STRING);
        } catch (Exception e) {
            /* 202 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, e);
        }

        /* 205 */ return valor;
    }

    public static String obtieneClaveAccesoAutorizacion(Autorizacion item) {
        /* 433 */ String claveAcceso = null;

        /* 435 */ String xmlAutorizacion = XStreamUtil.getRespuestaLoteXStream().toXML(item);
        /* 436 */ File archivoTemporal = new File("temp.xml");

        /* 438 */ stringToArchivo(archivoTemporal.getPath(), xmlAutorizacion);
        /* 439 */ String contenidoXML = decodeArchivoBase64(archivoTemporal.getPath());

        /* 441 */ if (contenidoXML != null) {
            /* 442 */ stringToArchivo(archivoTemporal.getPath(), contenidoXML);
            /* 443 */ claveAcceso = obtenerValorXML(archivoTemporal, "/*/infoTributaria/claveAcceso");
        }

        /* 447 */ return claveAcceso;
    }

    public static String decodeArchivoBase64(String pathArchivo) {
        /* 458 */ String xmlDecodificado = null;
        try {
            /* 460 */ File file = new File(pathArchivo);
            /* 461 */ if (file.exists()) {
                /* 463 */ String encd = obtenerValorXML(file, "/*/comprobante");

                /* 466 */ xmlDecodificado = encd;
            } else {
                /* 469 */ System.out.print("File not found!");
            }
        } catch (Exception e) {
        }
        /* 474 */ return xmlDecodificado;
    }

    public static boolean anadirMotivosRechazo(File archivo, RespuestaSolicitud respuestaRecepcion) {
        /* 489 */ boolean exito = false;
        /* 490 */ File respuesta = new File("respuesta.xml");
///* 491 */     Java2XML.marshalRespuestaSolicitud(respuestaRecepcion, respuesta.getPath());
/* 492 */ if (adjuntarArchivo(respuesta, archivo) == true) {
            /* 493 */ exito = true;
            /* 494 */ respuesta.delete();
        }
        /* 496 */ return exito;
    }

    public static boolean adjuntarArchivo(File respuesta, File comprobante) {
        /* 507 */ boolean exito = false;
        try {
            /* 510 */ Document document = merge("*", new File[]{comprobante, respuesta});

            /* 512 */ DOMSource source = new DOMSource(document);

            /* 514 */ StreamResult result = new StreamResult(new OutputStreamWriter(new FileOutputStream(comprobante), "UTF-8"));

            /* 516 */ TransformerFactory transFactory = TransformerFactory.newInstance();
            /* 517 */ Transformer transformer = transFactory.newTransformer();

            /* 519 */ transformer.transform(source, result);
        } catch (Exception ex) {
            /* 522 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 524 */ return exito;
    }

    private static Document merge(String exp, File[] files)
            throws Exception {
        /* 537 */ XPathFactory xPathFactory = XPathFactory.newInstance();
        /* 538 */ XPath xpath = xPathFactory.newXPath();
        /* 539 */ XPathExpression expression = xpath.compile(exp);

        /* 541 */ DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        /* 542 */ docBuilderFactory.setIgnoringElementContentWhitespace(true);
        /* 543 */ DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        /* 544 */ Document base = docBuilder.parse(files[0]);

        /* 546 */ Node results = (Node) expression.evaluate(base, XPathConstants.NODE);
        /* 547 */ if (results == null) {
            /* 548 */ throw new IOException(files[0] + ": expression does not evaluate to node");
        }

        /* 551 */ for (int i = 1; i < files.length; i++) {
            /* 552 */ Document merge = docBuilder.parse(files[i]);
            /* 553 */ Node nextResults = (Node) expression.evaluate(merge, XPathConstants.NODE);
            /* 554 */ results.appendChild(base.importNode(nextResults, true));
        }

        /* 563 */ return base;
    }

    public static boolean copiarArchivo(File archivoOrigen, String pathDestino) {
        /* 574 */ FileReader in = null;
        /* 575 */ boolean resultado = false;
        try {
            /* 578 */ File outputFile = new File(pathDestino);
            /* 579 */ in = new FileReader(archivoOrigen);
            /* 580 */ FileWriter out = new FileWriter(outputFile);
            int c;
            /* 582 */ while ((c = in.read()) != -1) {
                /* 583 */ out.write(c);
            }
            /* 585 */ in.close();
            /* 586 */ out.close();
            /* 587 */ resultado = true;
        } catch (Exception ex) {
            /* 590 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                /* 593 */ in.close();
            } catch (IOException ex) {
                /* 595 */ Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        /* 598 */ return resultado;
    }

    public static byte[] ConvertirBytes(String pathArchivo) {
        try {
//            tipoAmbiente();
            FileInputStream in = null;
            //in = new FileInputStream(Utilidades.DirXMLPrincipal + Utilidades.DirFirmados + prefijo + establecimiento + "-" + puntoemision + "-" + secuencial + ".xml");
            in = new FileInputStream(pathArchivo);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            DocumentBuilder builder
                    = factory.newDocumentBuilder();
            Document document = builder.parse(in);

            Source source = new DOMSource(document);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Result result = new StreamResult(out);
            TransformerFactory factoryT = TransformerFactory.newInstance();
            Transformer transformer = factoryT.newTransformer();
            transformer.transform(source, result);
            return out.toByteArray();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Tipoambiente.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (ParserConfigurationException e) {
            Logger.getLogger(Tipoambiente.class
                    .getName()).log(Level.SEVERE, null, e);

        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Tipoambiente.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (TransformerException ex) {
            Logger.getLogger(Tipoambiente.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ArchivoUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public static void FileCopy(String sourceFile, String destinationFile) {
        System.out.println("Desde: " + sourceFile);
        System.out.println("Hacia: " + destinationFile);

        try {
            File inFile = new File(sourceFile);
            File outFile = new File(destinationFile);

            FileInputStream in = new FileInputStream(inFile);
            FileOutputStream out = new FileOutputStream(outFile);

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            System.err.println("Hubo un error de entrada/salida!!!");
        }
    }

    public static String zipFile(File archivo, String pathArchivo) {
        try {

            ZipFile zipFile = new ZipFile(pathArchivo.replace(".xml", ".zip"));
            ZipParameters parameters = new ZipParameters();

            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);

            zipFile.addFile(archivo, parameters);
//            zipFile.addFile(archivo, parameters);
            return pathArchivo.replace(".xml", ".zip");
        } catch (ZipException ex) {
            ex.getStackTrace();
        }
        return "";
    }

    public static String zipFiles(List<String> archivos, String pathZip) {
        try {

            ZipFile zipFile = new ZipFile(pathZip.replace(".xml", ".zip"));
            ZipParameters parameters = new ZipParameters();

            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);

            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            File archivo = null;
            for (String item : archivos) {
                archivo = new File(item);
                zipFile.addFile(archivo, parameters);
                if (archivo.delete()) {
                    System.out.println("El fichero ha sido borrado satisfactoriamente");
                } else {
                    System.out.println("El fichero no pudó ser borrado");
                }
            }

//            zipFile.addFile(archivo, parameters);
            return pathZip.replace(".xml", ".zip");
        } catch (ZipException ex) {
            ex.getStackTrace();
        }
        return "";
    }

    public static void reporteGeneralPdfMail(String pathPDF, Integer numeroFactura, String tipo) throws JRException, IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
        EntityManager emf = HelperPersistencia.getEMF();
        Connection con = null;
        try {
            String reportFile = Executions.getCurrent().getDesktop().getWebApp()
                    .getRealPath("/reportes");
            String reportPath = "";
            emf.getTransaction().begin();
            con = emf.unwrap(Connection.class);
            if (tipo.contains("FACT")) {
                reportPath = reportFile + File.separator + "factura.jasper";
            } else if (tipo.contains("NCRE")) {
                reportPath = reportFile + File.separator + "notacr.jasper";
            } else if (tipo.contains("RET")) {
                reportPath = reportFile + File.separator + "retencion.jasper";
            } else if (tipo.contains("GUIA")) {
                reportPath = reportFile + File.separator + "guia.jasper";
            }

            Map<String, Object> parametros = new HashMap<String, Object>();

            //  parametros.put("codUsuario", String.valueOf(credentialLog.getAdUsuario().getCodigoUsuario()));
            parametros.put("numfactura", numeroFactura);

            if (con != null) {
                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
            }
            FileInputStream is = null;
            is = new FileInputStream(reportPath);

//                byte[] buf = JasperRunManager.runReportToPdf(is, parametros, con);
            JasperPrint print = JasperFillManager.fillReport(reportPath, parametros, con);
            JasperExportManager.exportReportToPdfFile(print, pathPDF);
        } catch (FileNotFoundException e) {
            System.out.println("Error en generar el reporte file " + e.getMessage());
        } catch (JRException e) {
            System.out.println("Error en generar el reporte JRE  " + e.getMessage());
        } finally {
            if (con != null) {
                con.close();
            }
            if (emf != null) {
                emf.close();
                System.out.println("cerro entity");
            }
        }

    }

    /*AGREGA LO DESEADO AL FINAL DEL TAG REALIZA UN INCREMENT MAS NO UN ADD EN UNA POSICION ESPECIFICA*/
    public static void modificarXMLAutorizado(String URI, String estado, String autorizacion, Date fecha, String URISalida) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            // 1. cargar el XML original
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(URI));
            /*AGREGAMOS EL ESTODO, AUTORIZACION Y FECHA DE AUTORIZACION*/
            //Le añadimos una característica
            Element tagEsadto = doc.createElement("estado");
            //Le añadimos su valor
            Text valor = doc.createTextNode(estado);

            Element numeroAutorizacion = doc.createElement("numeroAutorizacion");
            //Le añadimos su valor
            Text valorAutorizacion = doc.createTextNode(autorizacion);

            Element fechaAutorizacion = doc.createElement("fechaAutorizacion");
            //Le añadimos su valor
            Text valorFecha = doc.createTextNode(formato.format(fecha));

// 2. buscar y eliminar el elemento <enfermera id="3"> de entre 
//    muchos elementos <enfermera> ubicados en cualquier posicion del documento
            NodeList items = doc.getElementsByTagName("factura");
            for (int ix = 0; ix < items.getLength(); ix++) {
                Element element = (Element) items.item(ix);
                Node cnode = items.item(ix);
//                if (cnode.getNodeType() == Node.ELEMENT_NODE) {
//                    Element elem = (Element) cnode; // 'elem' Element after which the insertion should be made
//                    if (elem.getAttribute("name").equals("factura")) {
//                        Element newElement = doc.createElement("estado"); // Element to be inserted 
//                        newElement.setAttribute("name", "AUTORIZADO");
//                        // CODE HERE
//                    }
//                }
                System.out.println("imprimir --> " + element.getAttribute("id"));
                element.getParentNode().insertBefore(tagEsadto, element.getNextSibling());
                element.insertBefore(tagEsadto, element);
                element.appendChild(tagEsadto);
                tagEsadto.appendChild(valor);
                element.appendChild(numeroAutorizacion);
                numeroAutorizacion.appendChild(valorAutorizacion);
                element.appendChild(fechaAutorizacion);
                fechaAutorizacion.appendChild(valorFecha);
            }

// 3. Exportar nuevamente el XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(new File(URISalida));
            Source input = new DOMSource(doc);
            transformer.transform(input, output);
        } catch (IOException e) {
            System.out.println("ERROR " + e.getMessage());
        } catch (ParserConfigurationException e) {
            System.out.println("ERROR " + e.getMessage());
        } catch (TransformerException e) {
            System.out.println("ERROR " + e.getMessage());
        } catch (DOMException e) {
            System.out.println("ERROR " + e.getMessage());
        } catch (SAXException e) {
            System.out.println("ERROR " + e.getMessage());
        }
    }

    public static String generaraClaveTemporal() {
        int claveTemporal = 0;

        try {
            Random rng = new Random();
            claveTemporal = rng.nextInt(900) + 100; //siempre 3 digitos
        } catch (Exception e) {
            System.out.println("ERROR AL GENERAR CLAVE EN UTILITARI0 " + e.getMessage());
        }
        return ("" + claveTemporal).trim();
    }

    /*CREAR LA CLAVE DE ACCESO*/
    public static String generaClave(Date fechaEmision,
            String tipoComprobante,
            String ruc,
            String ambiente,
            String serie,
            String numeroComprobante,
            String codigoNumerico, String tipoEmision) {
        String claveGenerada = "";
        int verificador = 0;

        String numeroCedulaText = "";
        for (int i = ruc.length(); i < 13; i++) {
            numeroCedulaText = numeroCedulaText + "0";
        }

        ruc = numeroCedulaText + ruc;
        System.out.println("RUC CON CEROS " + ruc);
//        if ((ruc != null) && (ruc.length() < 13)) {
//            ruc = String.format("%013d", new Object[]{ruc});
//           
//        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String fecha = dateFormat.format(fechaEmision);

        StringBuilder clave = new StringBuilder(fecha);
        clave.append(tipoComprobante);
        clave.append(ruc);
        clave.append(ambiente);
        clave.append(serie);
        clave.append(numeroComprobante);
        clave.append(codigoNumerico);
        clave.append(tipoEmision);

        verificador = generaDigitoModulo11(clave.toString());

        clave.append(Integer.valueOf(verificador));
        claveGenerada = clave.toString();

        if (clave.toString().length() != 49) {
            claveGenerada = null;
        }
        return claveGenerada;
    }

    public static int generaDigitoModulo11(String cadena) {
        int baseMultiplicador = 7;
        System.out.println("CADENA-->" + cadena);
        int[] aux = new int[cadena.length()];
        int multiplicador = 2;
        int total = 0;
        int verificador = 0;
        for (int i = aux.length - 1; i >= 0; i--) {
            aux[i] = Integer.parseInt("" + cadena.charAt(i));
            aux[i] *= multiplicador;
            multiplicador++;
            if (multiplicador > baseMultiplicador) {
                multiplicador = 2;
            }
            total += aux[i];
        }

        if ((total == 0) || (total == 1)) {
            verificador = 0;
        } else {
            verificador = 11 - total % 11 == 11 ? 0 : 11 - total % 11;
        }

        if (verificador == 10) {
            verificador = 1;
        }

        return verificador;
    }

    /*APROXIMACION DE DECIMALES*/
    public static BigDecimal redondearDecimales(BigDecimal valorInicial, int numeroDecimales) {
        double parteEntera, resultado;
        resultado = valorInicial.doubleValue();
        parteEntera = Math.floor(resultado);
        Double resutl = BigDecimal.valueOf(resultado).subtract(BigDecimal.valueOf(parteEntera)).doubleValue();
        resultado = resutl * Math.pow(10, numeroDecimales);
        resultado = Math.round(resultado);
        BigDecimal resulDes = BigDecimal.valueOf(resultado);
        BigDecimal divide = BigDecimal.valueOf(resulDes.doubleValue()).divide(BigDecimal.valueOf(Math.pow(10, numeroDecimales)));
        resultado = (divide.add(BigDecimal.valueOf(parteEntera))).doubleValue();
        return BigDecimal.valueOf(resultado);
    }

    public static AduanaJson obtenerdatoAduana(String cedulaParam) throws URISyntaxException, IOException, XPathExpressionException, JSONException {
        AduanaJson respuesta = new AduanaJson();
//        String stubsApiBaseUri = "https://srienlinea.sri.gob.ec/movil-servicios/api/v1.0/deudas/porIdentificacion/" + cedulaParam;
        String stubsApiBaseUri = "https://www.gestiondocumental.gob.ec/Administracion/usuarios/validar_datos_registro_civil.php?cedula=" + cedulaParam;

        /**/
        OkHttpClient clienta = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://www.gestiondocumental.gob.ec/Administracion/usuarios/validar_datos_registro_civil.php?cedula=1718264839")
                .method("GET", body)
                .addHeader("Cookie", "incap_ses_107_2469631=+FHWYq3ECVynosTb9SN8AZKO+WMAAAAAl/wZzC5pXuH+g3dHo8TOQw==; visid_incap_2469631=Np3QXN8/Q36e+TjMcuHoRIyB+WMAAAAAQUIPAAAAAAAL6yHkCnk+WYtPsR55wz4E")
                .build();
        Response response = clienta.newCall(request).execute();
        /**/

        HttpClient client = HttpClients.createDefault();

        URIBuilder builder = new URIBuilder(stubsApiBaseUri);

        String listStubsUri = builder.build().toString();
        HttpGet getStubMethod = new HttpGet(listStubsUri);
        HttpResponse getStubResponse = client.execute(getStubMethod);
        int getStubStatusCode = getStubResponse.getStatusLine()
                .getStatusCode();
        if (getStubStatusCode < 200 || getStubStatusCode >= 300) {
            // Handle non-2xx status code
            respuesta.setCedula("");
            respuesta.setNombre("");
            respuesta.setMensaje("");
        }
        String contenido = EntityUtils
                .toString(getStubResponse.getEntity());

        System.out.println(contenido);

        //JSONObject outlineArray = new JSONObject(contenido);
        try {
            if (!contenido.equals("")) {
                JSONObject appObject = new JSONObject(contenido);

                JSONObject appObjectInf = appObject.getJSONObject("contribuyente");
                String nombre = appObjectInf.getString("nombreComercial");
                String mensaje = appObjectInf.getString("clase");
                String cedula = appObjectInf.getString("identificacion");

                respuesta.setCedula(cedula);
                respuesta.setNombre(nombre);
                respuesta.setMensaje(mensaje);
            } else {
                respuesta.setCedula("");
                respuesta.setNombre("");
                respuesta.setMensaje("");
            }
        } catch (JSONException e) {
            respuesta.setCedula("");
            respuesta.setNombre("");
            respuesta.setMensaje("");
        }

        return respuesta;
    }

    public static CabeceraCompra compraSriToCompra(CabeceraCompraSri valor) {
        CabeceraCompra compra = new CabeceraCompra();
        Proveedores prov = servicioProveedor.findProvCedula(valor.getCabRucProveedor());
        if (prov != null) {
            compra.setIdProveedor(prov);
        } else {
            Proveedores provNuevo = new Proveedores();
            //DEPENDE DEL PROVEEDOR SI ES CEDULA O RUC
            TipoIdentificacionCompra identificacionCompra = null;
            if (valor.getCabRucProveedor().length() == 13) {
                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("04");
            } else if (valor.getCabRucProveedor().length() == 10) {
                identificacionCompra = servicioTipoIdentificacionCompra.findByCedulaRuc("05");
            }
            provNuevo.setIdTipoIdentificacionCompra(identificacionCompra);
            provNuevo.setProvBanco("S/N");
            provNuevo.setProvNombre(valor.getCabProveedor());
            provNuevo.setProvNomComercial(valor.getCabProveedor());
            provNuevo.setProvCedula(valor.getCabRucProveedor());
            provNuevo.setProvDireccion(valor.getCabDireccion());
            servicioProveedor.crear(provNuevo);
            compra.setIdProveedor(provNuevo);
//            prov = provNuevo;
        }

        compra.setCabDescripcion("PROCESADA");
        compra.setIdUsuario(credential.getUsuarioSistema());
        compra.setIdEstado(servicioEstadoFactura.findByEstCodigo("PA"));
        compra.setCabNumFactura(valor.getCabNumFactura());
        compra.setCabEstado("PE");

        compra.setCabFecha(valor.getCabFecha());
        compra.setCabSubTotal(valor.getCabSubTotal());
        compra.setCabIva(valor.getCabIva());
        compra.setCabTotal(valor.getCabTotal());
        compra.setCabSubTotalCero(valor.getCabSubTotalCero());

        compra.setCabEstado("PE");
        compra.setCabProveedor(valor.getCabProveedor());
        compra.setCabClaveAcceso(valor.getCabAutorizacion());
        compra.setCabAutorizacion(valor.getCabAutorizacion());
        compra.setCabFechaEmision(valor.getCabFecha());
        compra.setDrcCodigoSustento("01");
        compra.setCabRetencionAutori("N");
        compra.setCabTraeSri(Boolean.TRUE);
        compra.setCabEstablecimiento(valor.getCabEstablecimiento());
        compra.setCabPuntoEmi(valor.getCabPuntoEmision());

        return compra;
    }

    public static DetalleCompra detalleSriToDetalleCompra(DetalleCompraSri valor, CabeceraCompra compra) {
        DetalleCompra detalleCom = new DetalleCompra();
        Parametrizar parametrizar = new Parametrizar();
        parametrizar = servicioParametrizar.FindALlParametrizar();
        BigDecimal factorIva = BigDecimal.ONE.add(parametrizar.getParIvaActual().divide(BigDecimal.valueOf(100)));
        BigDecimal factorUtilidad = BigDecimal.ONE.add(BigDecimal.valueOf(0.47));
        Producto buscado = servicioProducto.findByProdCodigo(valor.getIprodCodigoProducto());
        Producto nuevoProd = new Producto();
        if (buscado == null) {
            BigDecimal costoInicial = valor.getIprodSubtotal().setScale(2, RoundingMode.CEILING);
            BigDecimal calCostoCompr = valor.getIprodGrabaIva() ? (costoInicial.divide(factorIva, 3, RoundingMode.FLOOR)) : costoInicial;

            System.out.println("PRODUCTO NUEVO " + valor.getIprodDescripcion());
            nuevoProd = new Producto();
            nuevoProd.setPordCostoCompra(calCostoCompr);
            nuevoProd.setPordCostoVentaFinal(costoInicial.multiply(factorUtilidad).setScale(4, RoundingMode.CEILING));
            nuevoProd.setPordCostoVentaRef(costoInicial.setScale(4, RoundingMode.CEILING));
            nuevoProd.setProdAbreviado("");
            nuevoProd.setProdCantMinima(BigDecimal.TEN);
            nuevoProd.setProdCantidadInicial(BigDecimal.TEN);
            nuevoProd.setProdCodigo(valor.getIprodCodigoProducto().length() > 199 ? valor.getIprodCodigoProducto().substring(0, 199).toUpperCase() : valor.getIprodCodigoProducto().toUpperCase());
            nuevoProd.setProdCostoPreferencial(BigDecimal.ZERO);
            nuevoProd.setProdCostoPreferencialDos(BigDecimal.ZERO);
            nuevoProd.setProdCostoPreferencialTres(BigDecimal.ZERO);
            nuevoProd.setProdIsPrincipal(Boolean.FALSE);
            nuevoProd.setProdEsproducto(Boolean.TRUE);
            nuevoProd.setProdIva(valor.getIprodGrabaIva() ? parametrizar.getParIvaActual() : BigDecimal.ZERO);
            nuevoProd.setProdManoObra(BigDecimal.ZERO);
            nuevoProd.setProdNombre(valor.getIprodDescripcion().length() > 199 ? valor.getIprodDescripcion().substring(0, 199).toUpperCase() : valor.getIprodDescripcion().toUpperCase());
            nuevoProd.setProdTrasnporte(BigDecimal.ZERO);
            nuevoProd.setProdUtilidadNormal(BigDecimal.ZERO);
            nuevoProd.setProdUtilidadPreferencial(BigDecimal.ZERO);
            nuevoProd.setProdPrecioSinSubsidio(BigDecimal.ZERO);
            nuevoProd.setProdTieneSubsidio("N");
            nuevoProd.setProdGrabaIva(valor.getIprodGrabaIva());

            servicioProducto.crear(nuevoProd);
            detalleCom.setIdProducto(nuevoProd);
        }

        if (buscado != null) {
            detalleCom.setIdProducto(buscado);
        }
        detalleCom.setIdCabecera(compra);
        detalleCom.setIprodCantidad(valor.getIprodCantidad());
        detalleCom.setDetDescripcion(valor.getIprodDescripcion());
        detalleCom.setIprodSubtotal(valor.getIprodSubtotal());
        detalleCom.setIprodTotal(valor.getIprodTotal());
        detalleCom.setDetValorInicial(BigDecimal.ONE);
        detalleCom.setDetFactor(BigDecimal.ONE);

        return detalleCom;
    }

    public static String obtenerPorRuc(String cedula) {
        if (cedula.length() == 10) {
            cedula = cedula + "001";
        }

        String contenido = "";
        String direccion = "";
        System.out.println("gestion doc");

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            HandshakeCertificates certificates = new HandshakeCertificates.Builder()
                    .addTrustedCertificate(letsEncryptCertificateAuthoritySRI)
                    .addTrustedCertificate(entrustRootCertificateAuthoritySRI)
                    .addTrustedCertificate(comodoRsaCertificationAuthoritySRI)
                    // Uncomment if standard certificates are also required.
                    //.addPlatformTrustedCertificates()
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
                    .build();
            Request request = new Request.Builder()
                    .url("https://srienlinea.sri.gob.ec/sri-catastro-sujeto-servicio-internet/rest/Persona/obtenerPersonaDesdeRucPorIdentificacion?numeroRuc=" + cedula)
                    .build();
            System.out.println("EEEE");

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    throw new IOException("Unexpected code " + response);
                }

                String contenidoObt = response.body().string();
                JSONObject jsonObject = new JSONObject(contenidoObt);
                return jsonObject.get("nombreCompleto").toString();
            } catch (IOException e) {
                System.out.println("ERROR IOException " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//           

        return "";

    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static InfoPersona obtenerPorCedula(String cedula) {
        String contenido = "";
        String direccion = "";
        System.out.println("gestion doc");

        X509TrustManager trustManager;
        SSLSocketFactory sslSocketFactory;
        try {
            HandshakeCertificates certificates = new HandshakeCertificates.Builder()
                    .addTrustedCertificate(letsEncryptCertificateAuthority)
                    .addTrustedCertificate(entrustRootCertificateAuthority)
                    .addTrustedCertificate(comodoRsaCertificationAuthority)
                    // Uncomment if standard certificates are also required.
                    //.addPlatformTrustedCertificates()
                    .build();
            OkHttpClient client = new OkHttpClient.Builder()
                    .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
                    .build();
            Request request = new Request.Builder()
                    .url("https://www.gestiondocumental.gob.ec/Administracion/usuarios/validar_datos_registro_civil.php?cedula=" + cedula)
                    .build();
            System.out.println("EEEE");

            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    throw new IOException("Unexpected code " + response);
                }

                String contenidoObt = response.body().string();
                System.out.println(contenidoObt);
                String obtNombre[] = contenidoObt.split("lbl_datos_rc_nombre");

//                   contenido += inputLine.replace("lbl_datos_rc_apellido", "@");
//                        String[] exploded = contenido.split("@");
                contenido = obtNombre[1] + ">";
                String contDos[] = contenido.split("</span>");
                contenido = contDos[0].replace("\">", "");

                System.out.println("contenido " + contenido);
                String obtDireccion[] = contenidoObt.split("lbl_datos_rc_direccion");

                direccion = obtDireccion[1] + ">";
                direccion = direccion.replaceAll("\\<.*?\\>", "").trim();
                direccion = direccion.replace("\">", "");
                direccion = direccion.replace(">", "").trim();
                System.out.println("direccion " + direccion);

            } catch (IOException e) {
                System.out.println("ERROR IOException " + e.getMessage());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
//            

        return new InfoPersona(contenido, direccion);
    }
    
     public static Date recuperarFecha(Date fecha, String tipo) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String fechaSinHoraStr = sdf.format(fecha);
        String horaStr = "00:00:00";
        // Hora a agregar
        if (tipo.equals("fin")) {
            horaStr = "23:59:59";
        }

        // Formato de fecha y hora
        SimpleDateFormat formatoFechaHora = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // Convertir fecha sin hora a objeto Date
            Date fechaSinHora = formatoFechaHora.parse(fechaSinHoraStr + " 00:00:00");

            // Concatenar la hora a la fecha sin hora
            String fechaConHoraStr = fechaSinHoraStr + " " + horaStr;
            System.out.println(fechaConHoraStr);

            // Convertir la cadena con fecha y hora a objeto Date
            Date fechaConHora = formatoFechaHora.parse(fechaConHoraStr);
            System.out.println(fechaConHora);

            // Imprimir los objetos Date resultantes
            return fechaConHora;
        } catch (ParseException e) {
            e.printStackTrace();
            return fecha;
        }
    }

//    public static String token(String cedula) {
//        String contenido = "";
//        String direccion = "";
//        System.out.println("gestion doc");
//
//        X509TrustManager trustManager;
//        SSLSocketFactory sslSocketFactory;
//        try {
//            HandshakeCertificates certificates = new HandshakeCertificates.Builder()
//                    .addTrustedCertificate(letsEncryptCertificateAuthorityALPHA)
//                    .addTrustedCertificate(letsEncryptCertificateAuthorityALPHA)
//                    .addTrustedCertificate(letsEncryptCertificateAuthorityALPHA)
//                    // Uncomment if standard certificates are also required.
//                    //.addPlatformTrustedCertificates()
//                    .build();
//
////            OkHttpClient client = new OkHttpClient.Builder()
////                        .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager())
////                        .build();
////            Request request = new Request.Builder()
////                        .url("https://www.gestiondocumental.gob.ec/Administracion/usuarios/validar_datos_registro_civil.php?cedula=" + cedula)
////                        .build();
//            System.out.println("EEEE");
//
//            try {
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                MediaType mediaType = MediaType.parse("application/json;charset=utf-8");
//                RequestBody body = RequestBody.create(mediaType, "{\r\n  \"api_key\": \"689652829f001d7d\",\r\n  \"api_secret\": \"d7f286ac80dd40ac4df7db7e6e7186d5467985b6\"\r\n}");
//                Request request = new Request.Builder()
//                        .url("https://emea.api.hvca.globalsign.com:8443/v2/login")
//                        .method("POST", body)
//                        .addHeader("Content-Type", "application/json;charset=utf-8")
//                        .build();
//                Response response = client.newCall(request).execute();
//
//            } catch (IOException e) {
//                System.out.println("ERROR IOException " + e.getMessage());
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
////            
//
//        return "";
//    }
    static final X509Certificate comodoRsaCertificationAuthority = Certificates.decodeCertificatePem(certGestDoc);
//
    static final X509Certificate entrustRootCertificateAuthority = Certificates.decodeCertificatePem(certGestDoc);

    static final X509Certificate letsEncryptCertificateAuthority = Certificates.decodeCertificatePem(certGestDoc);
//    static final X509Certificate letsEncryptCertificateAuthorityALPHA = Certificates.decodeCertificatePem(certGestDoc);

    static final X509Certificate comodoRsaCertificationAuthoritySRI = Certificates.decodeCertificatePem(certSRI);
//
    static final X509Certificate entrustRootCertificateAuthoritySRI = Certificates.decodeCertificatePem(certSRI);

    static final X509Certificate letsEncryptCertificateAuthoritySRI = Certificates.decodeCertificatePem(certSRI);
}

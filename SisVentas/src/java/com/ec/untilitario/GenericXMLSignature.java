/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

/**
 *
 * @author Darwin
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.UnrecoverableKeyException;
import java.util.Enumeration;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class GenericXMLSignature {

    //Path de la firma electronica
    private String pathSignature;
    //calve de la firma electronica
    private String passSignature;

    /**
     *
     * Ejecución del ejemplo. La ejecución consistirá en la firma de los datos
     * creados por el método abstracto createDataToSign mediante el certificado
     * declarado en la constante PKCS12_FILE. El resultado del proceso de firma
     * será almacenado en un fichero XML en el directorio correspondiente a la
     * constante OUTPUT_DIRECTORY del usuario bajo el nombre devuelto por el
     * método abstracto getSignFileName
     *
     *
     */
    /*Metodos Getters y Setters (Propiedades)*/
    public String getPathSignature() {
        return pathSignature;
    }

    public void setPathSignature(String pathSignature) {
        this.pathSignature = pathSignature;
    }

    public String getPassSignature() {
        return passSignature;
    }

    public void setPassSignature(String passSignature) {
        this.passSignature = passSignature;
    }

    protected void execute() {

        // Obtencion del gestor de claves
        KeyStore keyStore = getKeyStore();

        if (keyStore == null) {
            System.err.println("No se pudo obtener almacen de firma.");
            return;
        }
        String alias = getAlias(keyStore);

        // Obtencion del certificado para firmar. Utilizaremos el primer
        // certificado del almacen.           
        X509Certificate certificate = null;
        try {
            certificate = (X509Certificate) keyStore.getCertificate(alias);
            if (certificate == null) {
                System.err.println("No existe ningún certificado para firmar.");
                return;
            }
        } catch (KeyStoreException e1) {
            e1.printStackTrace();
        }

        // Obtención de la clave privada asociada al certificado
        PrivateKey privateKey = null;
        KeyStore tmpKs = keyStore;
        try {
            privateKey = (PrivateKey) tmpKs.getKey(alias, this.passSignature.toCharArray());
        } catch (UnrecoverableKeyException e) {
            System.err.println("No existe clave privada para firmar.");
            e.printStackTrace();
        } catch (KeyStoreException e) {
            System.err.println("No existe clave privada para firmar.");
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("No existe clave privada para firmar.");
            e.printStackTrace();
        }

        // Obtención del provider encargado de las labores criptográficas
        Provider provider = keyStore.getProvider();

        /*
           * Creación del objeto que contiene tanto los datos a firmar como la
           * configuración del tipo de firma
         */
        DataToSign dataToSign = createDataToSign();

        /*
           * Creación del objeto encargado de realizar la firma
         */
        FirmaXML firma = new FirmaXML();

        // Firmamos el documento
        Document docSigned = null;
        try {
            Object[] res = firma.signFile(certificate, dataToSign, privateKey, provider);
            docSigned = (Document) res[0];
        } catch (Exception ex) {
            System.err.println("Error realizando la firma");
            ex.printStackTrace();
            return;
        }

        // Guardamos la firma a un fichero en el home del usuario
        String filePath = getPathOut() + File.separator + getSignatureFileName();
        System.out.println("Firma salvada en: " + filePath);

        saveDocumenteDisk(docSigned, filePath);
    }

    /**
     *
     * Crea el objeto DataToSign que contiene toda la información de la firma
     * que se desea realizar. Todas las implementaciones deberán proporcionar
     * una implementación de este método
     *
     *
     *
     * @return El objeto DataToSign que contiene toda la información de la firma
     * a realizar
     */
    protected abstract DataToSign createDataToSign();

    protected abstract String getSignatureFileName();

    protected abstract String getPathOut();

    protected Document getDocument(String resource) {
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        File file = new File(resource);
        try {

            InputStream inputStream = new FileInputStream(resource);
            Reader reader = new InputStreamReader(inputStream, "ISO-8859-1");
            InputSource is = new InputSource(reader);
            is.setEncoding("ISO-8859-1");

//            verifica el sistema ioperativo
            String sSistemaOperativo = System.getProperty("os.name");
            System.out.println(sSistemaOperativo);

//        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbf.newDocumentBuilder();
            if (sSistemaOperativo.toLowerCase().contains("wind")) {
                doc = dBuilder.parse(is);
            } else {
                doc = dBuilder.parse(file);
            }

            //  DocumentBuilder db = dbf.newDocumentBuilder();
            //doc=db.parse(file);
        } catch (ParserConfigurationException ex) {
            System.err.println("Error al parsear el documento ParserConfigurationException " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        } catch (SAXException ex) {
            System.err.println("Error al parsear el documento SAXException " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        } catch (IOException ex) {
            System.err.println("Error al parsear el documento IOException " + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        } catch (IllegalArgumentException ex) {
            System.err.println("Error al parsear el documento IllegalArgumentException" + ex.getMessage());
            ex.printStackTrace();
            System.exit(-1);
        }
        return doc;
    }

    private KeyStore getKeyStore() {
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("PKCS12");
            ks.load(new FileInputStream(pathSignature), passSignature.toCharArray());
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ks;
    }

    private static String getAlias(KeyStore keyStore) {
        String alias = null;
        Enumeration nombres;
        try {
            nombres = keyStore.aliases();

            while (nombres.hasMoreElements()) {
                String tmpAlias = (String) nombres.nextElement();
                if (keyStore.isKeyEntry(tmpAlias)) {
                    alias = tmpAlias;
                }
            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        return alias;
    }

    public static void saveDocumenteDisk(Document document, String pathXml) {
        try {
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(pathXml));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer;
            transformer = transformerFactory.newTransformer();
            transformer.transform(source, result);
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TransformerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

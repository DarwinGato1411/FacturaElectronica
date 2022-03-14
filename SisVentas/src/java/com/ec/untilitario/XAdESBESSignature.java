/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Tipoambiente;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.xml.refs.InternObjectToSign;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.File;
import org.w3c.dom.Document;

/**
 *
 * @author Darwin
 */
public class XAdESBESSignature extends GenericXMLSignature {

    private static String nameFile;
    private static String pathFile;

    /**
     *
     * Recurso a firmar
     *
     *
     */
    private String fileToSign;

    public XAdESBESSignature() {
    }

    /**
     *
     * Fichero donde se desea guardar la firma
     *
     *
     */
    public XAdESBESSignature(String fileToSign) {
        super();
        this.fileToSign = fileToSign;
    }

    /**
     *
     * Punto de entrada al programa
     *
     *
     *
     * @param args Argumentos del programa
     */
    public static void firmar(String xmlPath, String nomreArchivoFirmado, String ClaveFima, Tipoambiente amb, String FOLDER_BASE_FIRMADO) {
        XAdESBESSignature signature = new XAdESBESSignature(xmlPath);
        //clave para la firma electronica
        signature.setPassSignature(ClaveFima);
        //ruta de la firma electronica
        signature.setPathSignature(amb.getAmDirBaseArchivos() +File.separator+amb.getAmFolderFirma()+ File.separator + amb.getAmDirFirma());
        //ruta donde se ubicara los documentos firmados
        pathFile = FOLDER_BASE_FIRMADO;
        //en el caso de no existir el directorio lo crea
        File folder = new File(pathFile);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        //nombre del archivo firmado
        nameFile = nomreArchivoFirmado;

        signature.execute();
    }

    @Override
    protected DataToSign createDataToSign() {

        DataToSign datosAFirmar = new DataToSign();

        datosAFirmar.setXadesFormat(es.mityc.javasign.EnumFormatoFirma.XAdES_BES);
        datosAFirmar.setEsquema(XAdESSchemas.XAdES_132);
        datosAFirmar.setXMLEncoding("UTF-8");
        datosAFirmar.setEnveloped(true);
        datosAFirmar.addObject(new ObjectToSign(new InternObjectToSign("comprobante"), "contenido comprobante", null, "text/xml", null));
        datosAFirmar.setParentSignNode("comprobante");

        Document docToSign = getDocument(fileToSign);
        datosAFirmar.setDocument(docToSign);

        return datosAFirmar;
    }

    @Override
    protected String getSignatureFileName() {
        return XAdESBESSignature.nameFile;
    }

    @Override
    protected String getPathOut() {
        return XAdESBESSignature.pathFile;
    }

}

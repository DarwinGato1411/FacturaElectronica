/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.DetalleFactura;
import com.ec.entidad.DetalleGuiaremision;
import com.ec.entidad.DetalleNotaDebitoCredito;
import com.ec.entidad.DetalleRetencionCompra;
import com.ec.entidad.Factura;
import com.ec.entidad.Guiaremision;
import com.ec.entidad.NotaCreditoDebito;
import com.ec.entidad.RetencionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioDetalleFactura;
import com.ec.servicio.ServicioDetalleGuia;
import com.ec.servicio.ServicioDetalleNotaCredito;
import com.ec.servicio.ServicioDetalleRetencionCompra;
import com.ec.servicio.ServicioTipoAmbiente;
import ec.gob.sri.comprobantes.exception.RespuestaAutorizacionException;
import ec.gob.sri.comprobantes.util.AutorizacionComprobantesWs;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOffline;
import ec.gob.sri.comprobantes.ws.RecepcionComprobantesOfflineService;
import ec.gob.sri.comprobantes.ws.RespuestaSolicitud;
import ec.gob.sri.comprobantes.ws.aut.RespuestaComprobante;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.namespace.QName;

/**
 *
 * @author Darwin
 */
public class AutorizarDocumentos {

    ServicioDetalleFactura servicioDetalleFactura = new ServicioDetalleFactura();
    ServicioDetalleGuia servicioDetalleGuia = new ServicioDetalleGuia();
    ServicioDetalleNotaCredito servicioDetalleNotaCredito = new ServicioDetalleNotaCredito();
    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();

    ServicioDetalleRetencionCompra servicioDetalleRetencionCompra = new ServicioDetalleRetencionCompra();

    public static String removeCaracteres(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ$&¨\"";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcCxy''";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }//remove1

    public String generaClave(Date fechaEmision,
                String tipoComprobante,
                String ruc,
                String ambiente,
                String serie,
                String numeroComprobante,
                String codigoNumerico, String tipoEmision) /*     */ {
        String claveGenerada = "";
        /*  37 */ int verificador = 0;
        /*     */
// if ((ruc != null) && (ruc.length() < 13)) {
//    ruc = String.format("%013d", new Object[]{ruc});
//       }

        String numeroCedulaText = "";
        for (int i = ruc.length(); i < 13; i++) {
            numeroCedulaText = numeroCedulaText + "0";
        }

        ruc = numeroCedulaText + ruc;
        System.out.println("RUC CON CEROS AUTO DOC" + ruc);

        /*  44 */ SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        /*  45 */ String fecha = dateFormat.format(fechaEmision);
        /*     */
 /*  47 */ StringBuilder clave = new StringBuilder(fecha);
        /*  48 */ clave.append(tipoComprobante);
        /*  49 */ clave.append(ruc);
        /*  50 */ clave.append(ambiente);
        /*  51 */ clave.append(serie);
        /*  52 */ clave.append(numeroComprobante);
        /*  53 */ clave.append(codigoNumerico);
        /*  54 */ clave.append(tipoEmision);
        /*     */
 /*  57 */ verificador = generaDigitoModulo11(clave.toString());
        /*     */
 /*  59 */ clave.append(Integer.valueOf(verificador));
        /*  60 */ claveGenerada = clave.toString();
        /*     */
 /*  62 */ if (clave.toString().length() != 49) {
            /*  63 */ claveGenerada = null;
            /*     */        }
        /*  65 */ return claveGenerada;
        /*     */    }

    public int generaDigitoModulo11(String cadena) {
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

    public RespuestaSolicitud validar(byte[] datos) {
        try {

            //System.setProperty("https.protocols", "SSLv3");
            //System.setProperty(org.apache.axis2.transport.http.HTTPConstants.CHUNKED, Boolean.FALSE);
            URL url = new URL("https://" + servicioTipoAmbiente.FindALlTipoambiente().getAmUrlsri() + "/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
            QName qname = new QName("http://ec.gob.sri.ws.recepcion", "RecepcionComprobantesOfflineService");
            RecepcionComprobantesOfflineService service = new RecepcionComprobantesOfflineService(url, qname);
            RecepcionComprobantesOffline portRec = service.getRecepcionComprobantesOfflinePort();
            return portRec.validarComprobante(datos);

        } catch (MalformedURLException ex) {
            RespuestaSolicitud response = new RespuestaSolicitud();
            response.setEstado("ERROR SRI: " + ex.getMessage());
            return response;
        }

    }

    public RespuestaComprobante autorizarComprobante(String claveDeAcceso) throws RespuestaAutorizacionException {

        try {
            RespuestaComprobante repuesta = new AutorizacionComprobantesWs("https://" + servicioTipoAmbiente.FindALlTipoambiente().getAmUrlsri() + "/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl").llamadaWSAutorizacionInd(claveDeAcceso);
            return repuesta;
        } catch (Exception ex) {
            RespuestaComprobante response = new RespuestaComprobante();
            response.setNumeroComprobantes(ex.getMessage());
            return response;
        }

    }

//    public RespuestaComprobante autoriza(String clave) {
//        try {
//            URL url = new URL("https://"+servicioTipoAmbiente.FindALlTipoambiente().getAmUrlsri()+"/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl");
//            QName qname = new QName("http://ec.gob.sri.ws.autorizacion", "AutorizacionComprobantesOfflineService");
//            AutorizacionComprobantesOfflineService service = new AutorizacionComprobantesOfflineService(url, qname);
//
//            AutorizacionComprobantesOffline portAut = service.getAutorizacionComprobantesOfflinePort();
//            return portAut.autorizacionComprobante(clave);
//
//        } catch (MalformedURLException ex) {
////            Logger.getLogger(GeneracionXMLFacade.class
////                    .getName()).log(Level.SEVERE, null, ex);
//        }
//        return null;
//    }
    public void instalarCertificado() {
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }};

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AutorizarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc=" ARMAR FACTURA"> 
    public String generaXMLFactura(Factura valor, Tipoambiente amb, String folderDestino, String nombreArchivoXML, Boolean autorizada, Date fechaAutorizacion) {
        try {
            FileOutputStream out = null;
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            StringBuilder build = new StringBuilder();
            String linea = "";
            DecimalFormat df = new DecimalFormat("#.##");

            //            String claveAcceso = generaClave(new Date(), "01", empresa.getRucempresa(), "1", serie, cabdoc.getSecuencialcar(), "12345678", "1");
            //fecha de emision, tipo comprobante, RUC,tipo ambiente, serie(001001)Estabecimiento 002 emision001,tipo de emision comprobante
            String claveAcceso = generaClave(valor.getFacFecha(), "01", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab() + amb.getAmPtoemi(), valor.getFacNumeroText(), "12345678", "1");
            String tipoAmbiente = "";
            if (amb.getAmCodigo().equals("1")) {
                tipoAmbiente = "PRUEBAS";

            } else {
                tipoAmbiente = "PRODUCCION";
            }
            linea = ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<factura id=\"comprobante\" version=\"1.1.0\">\n");
            build.append(linea);
            linea = "";
            if (autorizada) {
                linea = (" <estado>AUTORIZADO</estado>\n"
                            + " <numeroAutorizacion>" + claveAcceso + "</numeroAutorizacion>\n"
                            + " <fechaAutorizacion>" + formato.format(fechaAutorizacion) + "</fechaAutorizacion>\n"
                            + " <ambiente>" + tipoAmbiente + "</ambiente>\n");
            }
            build.append(linea);
            linea = ("<infoTributaria>\n"
                        + "        <ambiente>" + amb.getAmCodigo() + "</ambiente>\n"
                        + "        <tipoEmision>1</tipoEmision>\n"
                        + "        <razonSocial>" + removeCaracteres(amb.getAmRazonSocial()) + "</razonSocial>\n"
                        + "        <nombreComercial>" + removeCaracteres(amb.getAmNombreComercial()) + "</nombreComercial>\n"
                        + "        <ruc>" + amb.getAmRuc() + "</ruc>\n"
                        + "        <claveAcceso>" + claveAcceso + "</claveAcceso>\n"
                        + "        <codDoc>01</codDoc>\n"
                        /*001 estab y punto emision*/
                        + "        <estab>" + amb.getAmEstab() + "</estab>\n"
                        + "        <ptoEmi>" + amb.getAmPtoemi() + "</ptoEmi>\n"
                        + "        <secuencial>" + valor.getFacNumeroText() + "</secuencial>\n"
                        + "        <dirMatriz>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirMatriz>\n"
                        + (amb.getAmMicroEmp() ? "     <regimenMicroempresas>CONTRIBUYENTE R\u00c9GIMEN MICROEMPRESAS</regimenMicroempresas>\n" : "")
                        + (amb.getAmAgeRet() ? "<agenteRetencion>1</agenteRetencion>\n" : "")
                        //  + "        <agenteRetencion>12345678</agenteRetencion>\n"
                        + "</infoTributaria>\n"
                        + "<infoFactura>\n"
                        + "        <fechaEmision>" + formato.format(valor.getFacFecha()) + "</fechaEmision>\n"
                        + "        <dirEstablecimiento>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirEstablecimiento>\n"
                        //   + "        <contribuyenteEspecial>0047</contribuyenteEspecial>\n"
                        + "        <obligadoContabilidad>" + amb.getLlevarContabilidad() + "</obligadoContabilidad>\n"
                        + "        <tipoIdentificacionComprador>" + valor.getIdCliente().getIdTipoIdentificacion().getTidCodigo() + "</tipoIdentificacionComprador>\n"
                        + "        <razonSocialComprador>" + removeCaracteres(valor.getIdCliente().getCliNombre()) + "</razonSocialComprador>\n"
                        + "        <identificacionComprador>" + valor.getIdCliente().getCliCedula() + "</identificacionComprador>\n"
                        + "        <totalSinImpuestos>" + ArchivoUtils.redondearDecimales(valor.getFacSubtotal(), 2) + "</totalSinImpuestos>\n"
                        + "         <totalSubsidio>" + valor.getFacSubsidio().setScale(2, RoundingMode.FLOOR) + "</totalSubsidio>\n"
                        + "        <totalDescuento>" + valor.getFacDescuento().setScale(2, RoundingMode.FLOOR) + "</totalDescuento>\n"
                        + "        <totalConImpuestos>\n"
                        + "            <totalImpuesto>\n"
                        + "                <codigo>" + valor.getFacCodIva() + "</codigo>\n"
                        + "                <codigoPorcentaje>0</codigoPorcentaje>\n"
                        + "                <baseImponible>" + valor.getFacTotalBaseCero().setScale(2, RoundingMode.FLOOR) + "</baseImponible>\n"
                        + "                <tarifa>0</tarifa>\n"
                        + "                <valor>0.00</valor>\n"
                        + "             </totalImpuesto>\n"
                        + "             <totalImpuesto>\n"
                        /*CODIGO DEL IVA 2, ICE 3 IRBPNR 6*/
                        + "             <codigo>" + valor.getFacCodIva() + "</codigo>\n"
                        /*CODIGO VALOR DEL IVA SI ES IVA 
                    0 --> 0 
                    SI 12-->2 
                    SI 14-->3 
                    No Objeto de Impuesto -->6 
                    EXENTO DE IVA 7   */
                        + "                 <codigoPorcentaje>" + valor.getCodigoPorcentaje() + "</codigoPorcentaje>\n"
                        + "                 <baseImponible>" + valor.getFacTotalBaseGravaba().setScale(2, RoundingMode.FLOOR) + "</baseImponible>\n"
                        + "                 <tarifa>" + valor.getFacPorcentajeIva() + "</tarifa>\n"
                        + "                 <valor>" + valor.getFacIva().setScale(2, RoundingMode.FLOOR) + "</valor>\n"
                        + "              </totalImpuesto>\n"
                        + "         </totalConImpuestos>\n"
                        + "                 <propina>0</propina>\n"
                        + "                 <importeTotal>" + ArchivoUtils.redondearDecimales(valor.getFacTotal(), 2) + "</importeTotal>\n"
                        + "                 <moneda>" + valor.getFacMoneda() + "</moneda>\n"
                        + "         <pagos>\n"
                        + "                 <pago>\n"
                        + "                     <formaPago>" + valor.getIdFormaPago().getForCodigo() + "</formaPago>\n"
                        + "                     <total>" + ArchivoUtils.redondearDecimales(valor.getFacTotal(), 2) + "</total>\n"
                        + "                     <plazo>" + valor.getFacPlazo().setScale(2, RoundingMode.FLOOR) + "</plazo>\n"
                        + "                     <unidadTiempo>" + valor.getFacUnidadTiempo() + "</unidadTiempo>\n"
                        + "                 </pago>\n"
                        + "         </pagos>\n"
                        + "         <valorRetIva>" + 0.00 + "</valorRetIva>\n"
                        + "         <valorRetRenta>" + 0.00 + "</valorRetRenta>\n"
                        + "    </infoFactura>\n");
            build.append(linea);
            linea = ("     <detalles>\n");
            build.append(linea);

            List<DetalleFactura> listaDetalle = servicioDetalleFactura.findDetalleForIdFactuta(valor);
            for (DetalleFactura item : listaDetalle) {

                String subsidio = "            <precioSinSubsidio>" + item.getIdProducto().getProdPrecioSinSubsidio() + "</precioSinSubsidio>\n";

                linea = ("        <detalle>\n"
                            + "            <codigoPrincipal>" + removeCaracteres(item.getIdProducto().getProdCodigo()) + "</codigoPrincipal>\n"
                            + "            <descripcion>" + removeCaracteres(item.getDetDescripcion()) + "</descripcion>\n"
                            //+ "            <descripcion>" + removeCaracteres(item.getIdProducto().getProdNombre()) + "</descripcion>\n"
                            + "            <cantidad>" + item.getDetCantidad().setScale(2, RoundingMode.FLOOR) + "</cantidad>\n"
                            + "            <precioUnitario>" + ArchivoUtils.redondearDecimales(item.getDetSubtotal(), 5) + "</precioUnitario>\n"
                            +                    (item.getIdProducto().getProdTieneSubsidio().equals("S") ? subsidio : "")
                            + "            <descuento>" + ArchivoUtils.redondearDecimales(item.getDetCantpordescuento(), 2) + "</descuento>\n"
                            + "            <precioTotalSinImpuesto>" + ArchivoUtils.redondearDecimales(item.getDetSubtotaldescuento().multiply(item.getDetCantidad()), 2) + "</precioTotalSinImpuesto>\n"
                            + "            <impuestos>\n"
                            + "                <impuesto>\n"
                            + "                    <codigo>" + item.getDetCodIva() + "</codigo>\n"
                            + "                    <codigoPorcentaje>" + item.getDetCodPorcentaje() + "</codigoPorcentaje>\n"
                            + "                    <tarifa>" + item.getDetTarifa() + "</tarifa>\n"
                            + "                    <baseImponible>" + ArchivoUtils.redondearDecimales(item.getDetSubtotaldescuento().multiply(item.getDetCantidad()), 2) + "</baseImponible>\n"
                            + "                    <valor>" + item.getDetIva().setScale(2, RoundingMode.FLOOR) + "</valor>\n"
                            + "                </impuesto>\n"
                            + "            </impuestos>\n"
                            + "        </detalle>\n");
                build.append(linea);
            }

//            build.append(linea);
            linea = ("    </detalles>\n");
            build.append(linea);
            linea = ("    <infoAdicional>\n"
                        + (valor.getIdCliente().getCliDireccion().length() > 0 ? "<campoAdicional nombre=\"DIRECCION\">" + removeCaracteres(valor.getIdCliente().getCliDireccion()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliCorreo().length() > 0 ? "<campoAdicional nombre=\"E-MAIL\">" + removeCaracteres(valor.getIdCliente().getCliCorreo()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliApellidos().length() > 0 ? "<campoAdicional nombre=\"APELLIDO\">" + removeCaracteres(valor.getIdCliente().getCliApellidos()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliNombres().length() > 0 ? "<campoAdicional nombre=\"NOMBRE\">" + removeCaracteres(valor.getIdCliente().getCliNombres()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliNombre().length() > 0 ? "<campoAdicional nombre=\"NOMBRECOMERCIAL\">" + removeCaracteres(valor.getIdCliente().getCliNombre()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCiudad().length() > 0 ? "<campoAdicional nombre=\"CIUDAD\">" + removeCaracteres(valor.getIdCliente().getCiudad()) + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliTelefono().length() > 0 ? "<campoAdicional nombre=\"TELEFONO\">" + valor.getIdCliente().getCliTelefono() + "</campoAdicional>\n" : " ")
                        //                    + (valor.getIdCliente().getCliMovil().length() > 0 ? "<campoAdicional nombre=\"CELULAR\">" + valor.getIdCliente().getCliMovil() + " </campoAdicional>\n" : " ")
                        + "<campoAdicional nombre=\"PLAZO\"> DIAS</campoAdicional>\n"
                        + (valor.getFacPlazo().toString().length() > 0 ? "<campoAdicional nombre=\"DIAS\">" + valor.getFacPlazo().setScale(0) + "</campoAdicional>\n" : " ")
                        + (valor.getFacPorcentajeIva().length() > 0 ? "<campoAdicional nombre=\"TARIFAIMP\">" + valor.getFacPorcentajeIva() + "</campoAdicional>\n" : " ")
                       + (amb.getAmRimpe() ? "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN RIMPE\">CONTRIBUYENTE REGIMEN RIMPE</campoAdicional>\n" : "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN GENERAL\">CONTRIBUYENTE REGIMEN GENERAL</campoAdicional>\n")
                        // + (amb.getAmAgeRet() ? "<campoAdicional nombre=\"Agente de Retencion\">Agente de Retencion Resolucion Nro. NAC-DNCRASC20-00000001</campoAdicional>\n" : "")
                        + "   </infoAdicional>\n"
                        + "</factura>\n");
            build.append(linea);
            /*IMPRIME EL XML DE LA FACTURA*/
            System.out.println("XML " + build);
            String pathArchivoSalida = "";

            /*ruta de salida del archivo XML 
            generados o autorizados para enviar al cliente 
            dependiendo la ruta enviada en el parametro del metodo */
            pathArchivoSalida = folderDestino
                        + nombreArchivoXML;

            //String pathArchivoSalida = "D:\\";
            out = new FileOutputStream(pathArchivoSalida);
            out.write(build.toString().getBytes());
            //GRABA DATOS EN FACTURA//
            return pathArchivoSalida;
            //return Utilidades.DirXMLPrincipal + Utilidades.DirSinFirmas + "FACT-" + cabdoc.getEstablecimientodocumento() + "-" + cabdoc.getPuntoemisiondocumento() + "-" + cabdoc.getSecuencialcar() + ".xml";
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML FACTURA  FileNotFoundException" + ex);
        } catch (IOException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML FACTURA IOException " + ex);
        }
        return null;
    }
//</editor-fold > 
    //<editor-fold defaultstate="collapsed" desc=" ARMAR NOTA DE CREDITO"> 

    public String generaXMLNotaCreditoDebito(NotaCreditoDebito valor, Tipoambiente amb, String folderDestino, String nombreArchivoXML, String NCoND) {
        FileOutputStream out;
        try {
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            String motivo = "DEVOLUCION";
            String claveAcceso = generaClave(valor.getFacFecha(), "04", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab() + amb.getAmPtoemi(), valor.getFacNumeroText(), "12345678", "1");
            String tipoDocumento = "";
            if (NCoND.equals("04")) {
                tipoDocumento = "Credito";
            } else if (NCoND.equals("05")) {
                tipoDocumento = "Debito";
            }
            StringBuilder build = new StringBuilder();
            String linea = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
                        + "<nota" + tipoDocumento + " id=\"comprobante\" version=\"1.1.0\">\n"
                        + "    <infoTributaria>\n"
                        + "        <ambiente>" + amb.getAmCodigo() + "</ambiente>\n"
                        + "        <tipoEmision>1</tipoEmision>\n"
                        + "        <razonSocial>" + removeCaracteres(amb.getAmRazonSocial()) + "</razonSocial>\n"
                        + "        <nombreComercial>" + removeCaracteres(amb.getAmNombreComercial()) + "</nombreComercial>\n"
                        + "        <ruc>" + amb.getAmRuc() + "</ruc>\n"
                        + "        <claveAcceso>" + claveAcceso + "</claveAcceso>\n"
                        + "        <codDoc>04</codDoc>\n"
                        + "        <estab>" + amb.getAmEstab() + "</estab>\n"
                        + "        <ptoEmi>" + amb.getAmPtoemi() + "</ptoEmi>\n"
                        + "        <secuencial>" + valor.getFacNumeroText() + "</secuencial>\n"
                        + "        <dirMatriz>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirMatriz>\n"
                        + "    </infoTributaria>\n"
                        //depende si es nota de credito o debito
                        + "    <infoNota" + tipoDocumento + ">\n"
                        + "        <fechaEmision>" + formato.format(valor.getFacFecha()) + "</fechaEmision>\n"
                        + "        <dirEstablecimiento>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirEstablecimiento>\n"
                        + "        <tipoIdentificacionComprador>" + valor.getIdFactura().getIdCliente().getIdTipoIdentificacion().getTidCodigo() + "</tipoIdentificacionComprador>\n"
                        + "        <razonSocialComprador>" + removeCaracteres(valor.getIdFactura().getIdCliente().getCliRazonSocial()) + "</razonSocialComprador>\n"
                        + "        <identificacionComprador>" + valor.getIdFactura().getIdCliente().getCliCedula() + "</identificacionComprador>\n"
                        //+ "        <contribuyenteEspecial>5368</contribuyenteEspecial>\n"
                        + "        <obligadoContabilidad>" + amb.getLlevarContabilidad() + "</obligadoContabilidad>\n"
                        + "        <codDocModificado>" + valor.getTipodocumentomod() + "</codDocModificado>\n"
                        + "        <numDocModificado>" + valor.getCodestablecimiento() + "-" + valor.getPuntoemision() + "-" + valor.getIdFactura().getFacNumeroText() + "</numDocModificado>\n"
                        + "        <fechaEmisionDocSustento>" + formato.format(valor.getFacFechaSustento()) + "</fechaEmisionDocSustento>\n"
                        + "        <totalSinImpuestos>" + valor.getFacSubtotal().setScale(2, RoundingMode.FLOOR) + "</totalSinImpuestos>\n"
                        + "        <valorModificacion>" + valor.getFacTotal().setScale(2, RoundingMode.FLOOR) + "</valorModificacion>\n"
                        + (tipoDocumento.equals("Credito") ? "<moneda>" + valor.getFacMoneda().toUpperCase() + "</moneda>\n" : " ")
                        + "        <totalConImpuestos>\n"
                        + "            <totalImpuesto>\n"
                        + "                <codigo>" + valor.getFacCodIva() + "</codigo>\n"
                        + "                <codigoPorcentaje>" + valor.getCodigoPorcentaje() + "</codigoPorcentaje>\n"
                        + "                 <baseImponible>" + valor.getFacTotalBaseGravaba().setScale(2, RoundingMode.FLOOR) + "</baseImponible>\n"
                        + "                 <valor>" + valor.getFacIva().setScale(2, RoundingMode.FLOOR) + "</valor>\n"
                        + "              </totalImpuesto>\n"
                        + "        </totalConImpuestos>\n"
                        + "        <motivo>" + motivo + "</motivo>\n" //Motivo
                        + "    </infoNota" + tipoDocumento + ">\n");
            build.append(linea);
            if (tipoDocumento.equals("Credito")) {
                List<DetalleNotaDebitoCredito> listaDetalle = servicioDetalleNotaCredito.findDetalleForIdFactuta(valor);

//                List<Detalledocumento> det = detalledocumentoFacade.getDetalleDcto(cabdoc.getIdcabeceradocumentos());
                build.append("    <detalles>\n");
                if (listaDetalle.isEmpty()) {
                    linea = ("        <detalle>\n"
                                + "            <codigoInterno>" + motivo + "</codigoInterno>\n"
                                + "            <descripcion>" + motivo + "</descripcion>\n"
                                + "            <cantidad>" + ("1") + "</cantidad>\n"
                                + "            <precioUnitario>" + valor.getFacSubtotal().setScale(2, RoundingMode.FLOOR) + "</precioUnitario>\n"
                                + "            <descuento>" + valor.getFacDescuento().setScale(2, RoundingMode.FLOOR) + "</descuento>\n"
                                + "            <precioTotalSinImpuesto>" + valor.getFacSubtotal().setScale(2, RoundingMode.FLOOR) + "</precioTotalSinImpuesto>\n"
                                + "            <impuestos>\n"
                                + "                <impuesto>\n"
                                + "                    <codigo>" + valor.getFacCodIva() + "</codigo>\n"
                                + "                    <codigoPorcentaje>")
                                + ((valor.getFacTotalBaseGravaba().doubleValue() > 0 ? valor.getFacCodIva() : "0") + "</codigoPorcentaje>\n")
                                + ("                    <tarifa>" + (valor.getFacTotalBaseGravaba().doubleValue() > 0 ? valor.getFacPorcentajeIva() : "0") + "</tarifa>\n")
                                + ("                    <baseImponible>" + (valor.getFacTotalBaseGravaba().doubleValue() > 0 ? valor.getFacTotalBaseGravaba().setScale(2, RoundingMode.FLOOR) : valor.getFacTotalBaseCero().setScale(2, RoundingMode.FLOOR)) + "</baseImponible>\n")
                                + ("                    <valor>" + valor.getFacIva().setScale(2, RoundingMode.FLOOR) + "</valor>\n")
                                + ("                </impuesto>\n"
                                + "            </impuestos>\n"
                                + "        </detalle>\n");
                    build.append(linea);
                }
                for (DetalleNotaDebitoCredito item : listaDetalle) {

                    linea = ("        <detalle>\n"
                                + "            <codigoInterno>" + removeCaracteres(item.getIdProducto().getProdCodigo()) + "</codigoInterno>\n"
                                + "            <descripcion>" + removeCaracteres(item.getIdProducto().getProdNombre()) + "</descripcion>\n"
                                + "            <cantidad>" + item.getDetCantidad().setScale(2, RoundingMode.FLOOR) + "</cantidad>\n"
                                + "            <precioUnitario>" + item.getDetSubtotal() + "</precioUnitario>\n"
                                + "            <descuento>" + item.getDetCantpordescuento().setScale(2, RoundingMode.FLOOR) + "</descuento>\n"
                                + "            <precioTotalSinImpuesto>" + (item.getDetSubtotaldescuento().multiply(item.getDetCantidad())).setScale(2, RoundingMode.FLOOR) + "</precioTotalSinImpuesto>\n"
                                + "            <impuestos>\n"
                                + "                <impuesto>\n"
                                + "                    <codigo>" + valor.getFacCodIva() + "</codigo>\n"
                                + "                    <codigoPorcentaje>" + (item.getIdProducto().getProdGrabaIva() ? "2" : "0") + "</codigoPorcentaje>\n"
                                + "                    <tarifa>" + (item.getIdProducto().getProdGrabaIva() ? "12" : "0") + "</tarifa>\n"
                                + "                    <baseImponible>" + (item.getDetSubtotaldescuento().multiply(item.getDetCantidad())).setScale(2, RoundingMode.FLOOR) + "</baseImponible>\n"
                                + "                    <valor>" + item.getDetIva().setScale(2, RoundingMode.FLOOR) + "</valor>\n"
                                + "                </impuesto>\n"
                                + "            </impuestos>\n"
                                + "        </detalle>\n");

                    build.append(linea);
                }
                build.append("    </detalles>\n");
            }

            linea = ("    <infoAdicional>\n"
                        //                    + (valor.getIdCliente().getCliDireccion().length() > 0 ? "<campoAdicional nombre=\"TELEFONO\">" + removeCaracteres(valor.getIdCliente().getCliMovil()) + "</campoAdicional>\n" : " ")
                        + (valor.getIdFactura().getIdCliente().getCliCorreo().length() > 0 ? "<campoAdicional nombre=\"E-MAIL\">" + removeCaracteres(valor.getIdFactura().getIdCliente().getCliCorreo()) + "</campoAdicional>\n" : " ")
                        + (amb.getAmMicroEmp() ? "<campoAdicional nombre=\"Contribuyente Regimen Microempresas\">Contribuyente Regimen Microempresas</campoAdicional>\n" : "")
                        + (amb.getAmAgeRet() ? "<campoAdicional nombre=\"Agente de Retencion\">Agente de Retencion Resolucion Nro. NAC-DNCRASC20-00000001</campoAdicional>\n" : "")
                        + "   </infoAdicional>\n"
                        + "</nota" + tipoDocumento + ">");
            build.append(linea);

            /*IMPRIME EL XML DE LA NOTA DE CREDITO O DEBITO*/
            System.out.println("XML " + build);
            String pathArchivoSalida = "";

            /*ruta de salida del archivo XML 
            generados o autorizados para enviar al cliente 
            dependiendo la ruta enviada en el parametro del metodo */
            pathArchivoSalida = folderDestino
                        + nombreArchivoXML;

            //String pathArchivoSalida = "D:\\";
            out = new FileOutputStream(pathArchivoSalida);
            out.write(build.toString().getBytes());
            //GRABA DATOS EN FACTURA//
            return pathArchivoSalida;

        } catch (FileNotFoundException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML DEBITO O CREDITO  FileNotFoundException" + ex);
        } catch (IOException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML DEBITO O CREDITO IOException " + ex);
        }
        return "";
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" ARMAR GUIA DE REMISION">  
    public String generaXMLGuiaRemision(Guiaremision valor, Tipoambiente amb, String folderDestino, String nombreArchivoXML) {
        FileOutputStream out;
        try {

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

            String tipoemision = "1"; //en offline solo existe emision normal
            String motivo = "TRASPORTE DE MERCADERIA";
//            String claveAcceso = generaClave(valor.getFacFecha(), valor.getTipodocumento(), amb.getAmRuc(), amb.getAmCodigo(), "002001", valor.getFacNumeroText(), "12345678", "1");
            String claveAcceso = generaClave(valor.getFacFecha(), "06", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab() + amb.getAmPtoemi(), valor.getFacNumeroText(), "12345678", "1");
            StringBuilder build = new StringBuilder();
            String linea;
            linea = ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<guiaRemision id=\"comprobante\" version=\"1.1.0\">\n"
                        + "    <infoTributaria>\n"
                        + "        <ambiente>" + amb.getAmCodigo() + "</ambiente>\n"
                        + "        <tipoEmision>1</tipoEmision>\n"
                        + "        <razonSocial>" + removeCaracteres(amb.getAmRazonSocial()) + "</razonSocial>\n"
                        + "        <nombreComercial>" + removeCaracteres(amb.getAmNombreComercial()) + "</nombreComercial>\n"
                        + "        <ruc>" + amb.getAmRuc() + "</ruc>\n"
                        + "        <claveAcceso>" + claveAcceso + "</claveAcceso>\n"
                        + "        <codDoc>06</codDoc>\n"
                        + "        <estab>" + amb.getAmEstab() + "</estab>\n"
                        + "        <ptoEmi>" + amb.getAmPtoemi() + "</ptoEmi>\n"
                        + "        <secuencial>" + valor.getFacNumeroText() + "</secuencial>\n"
                        + "        <dirMatriz>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirMatriz>\n"
                        + "    </infoTributaria>\n"
                        + "    <infoGuiaRemision>\n"
                        + "        <dirEstablecimiento>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirEstablecimiento>\n"
                        + "        <dirPartida>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirPartida>\n"
                        + "        <razonSocialTransportista>" + removeCaracteres(valor.getIdTransportista().getTrpRazonSocial()) + "</razonSocialTransportista>\n"
                        + "        <tipoIdentificacionTransportista>" + valor.getIdTransportista().getIdTipoIdentificacion().getTidCodigo() + "</tipoIdentificacionTransportista>\n"
                        + "        <rucTransportista>" + valor.getIdTransportista().getTrpCedula() + "</rucTransportista>\n"
                        + "        <obligadoContabilidad>" + amb.getLlevarContabilidad() + "</obligadoContabilidad>\n"
                        //  + "        <contribuyenteEspecial>5368</contribuyenteEspecial>\n"
                        + "        <fechaIniTransporte>" + formato.format(valor.getFechainitranspguia()) + "</fechaIniTransporte>\n"
                        + "        <fechaFinTransporte>" + formato.format(valor.getFechafintranspguia()) + "</fechaFinTransporte>\n"
                        + "        <placa>" + valor.getNumplacaguia() + "</placa>\n"); //verificar placa transporte

            build.append(linea);
            linea = ("    </infoGuiaRemision>\n"
                        + "    <destinatarios>\n"
                        + "        <destinatario>\n"
                        + "            <identificacionDestinatario>" + valor.getIdCliente().getCliCedula() + "</identificacionDestinatario>\n"
                        + "            <razonSocialDestinatario>" + removeCaracteres(valor.getIdCliente().getCliRazonSocial()) + "</razonSocialDestinatario>\n"
                        + "            <dirDestinatario>" + removeCaracteres(valor.getIdCliente().getCliDireccion()) + "</dirDestinatario>\n"
                        + "            <motivoTraslado>" + motivo + "</motivoTraslado>\n")
                        + (valor.getDocumentoaduanerounico() == null ? "" : "            <docAduaneroUnico>" + valor.getDocumentoaduanerounico() + "</docAduaneroUnico>\n"
                        + "            <codEstabDestino>001</codEstabDestino>\n"
                        + "            <ruta>" + amb.getAmCiudad() + "-" + valor.getIdCliente().getCiudad() + "</ruta>\n");
            build.append(linea);
            try {
                if (valor.getIdFactura() != null) {
                    linea = ("            <codDocSustento>" + valor.getIdFactura().getTipodocumento() + "</codDocSustento>\n"
                                + "            <numDocSustento>" + valor.getIdFactura().getCodestablecimiento() + "-" + valor.getIdFactura().getPuntoemision() + "-" + valor.getIdFactura().getFacNumeroText() + "</numDocSustento>\n"
                                + "            <numAutDocSustento>" + valor.getIdFactura().getFacClaveAutorizacion() + "</numAutDocSustento>\n"
                                + "            <fechaEmisionDocSustento>" + formato.format(valor.getIdFactura().getFacFecha()) + "</fechaEmisionDocSustento>\n");
                    build.append(linea);
                }
            } catch (Exception e) {
            }
            linea = "<detalles>\n";
            build.append(linea);
            List<DetalleGuiaremision> det = servicioDetalleGuia.findDetalleForIdGuia(valor);
            for (DetalleGuiaremision detalle : det) {
                linea = ("                <detalle>\n"
                            + "                    <codigoInterno>" + removeCaracteres(detalle.getIdProducto().getProdCodigo()) + "</codigoInterno>\n"
                            + "                    <descripcion>" + removeCaracteres(detalle.getIdProducto().getProdNombre()) + "</descripcion>\n"
                            + "                    <cantidad>" + detalle.getDetCantidad().setScale(2, RoundingMode.FLOOR) + "</cantidad>\n"
                            + "                </detalle>\n");
                build.append(linea);
            }
            linea = ("            </detalles>\n"
                        + "        </destinatario>\n"
                        + "    </destinatarios>\n")
                        + ("    <infoAdicional>\n")
                        + ("        <campoAdicional nombre=\"E-MAIL\">" + removeCaracteres(valor.getIdCliente().getCliCorreo()) + "</campoAdicional>\n")
                        + ("        <campoAdicional nombre=\"DIRECCION\">" + removeCaracteres(valor.getIdCliente().getCliDireccion()) + "</campoAdicional>\n")
                        + ("        <campoAdicional nombre=\"TELEFONO\">" + valor.getIdCliente().getCliTelefono() + "</campoAdicional>\n")
                        + ("        <campoAdicional nombre=\"CIUDAD\">" + valor.getIdCliente().getCiudad() + "</campoAdicional>\n"
                        + (amb.getAmMicroEmp() ? "<campoAdicional nombre=\"Contribuyente Regimen Microempresas\">Contribuyente Regimen Microempresas</campoAdicional>\n" : "")
                        + (amb.getAmAgeRet() ? "<campoAdicional nombre=\"Agente de Retencion\">Agente de Retencion Resolucion Nro. NAC-DNCRASC20-00000001</campoAdicional>\n" : "")
                   + (amb.getAmRimpe() ? "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN RIMPE\">CONTRIBUYENTE REGIMEN RIMPE</campoAdicional>\n" : "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN GENERAL\">CONTRIBUYENTE REGIMEN GENERAL</campoAdicional>\n")
                        + "    </infoAdicional>\n"
                        + "</guiaRemision>");
            build.append(linea);
            /*IMPRIME EL XML GUIA DE REMISION*/
            System.out.println("XML " + build);
            String pathArchivoSalida = "";

            /*ruta de salida del archivo XML 
            generados o autorizados para enviar al cliente 
            dependiendo la ruta enviada en el parametro del metodo */
            pathArchivoSalida = folderDestino
                        + nombreArchivoXML;

            //String pathArchivoSalida = "D:\\";
            out = new FileOutputStream(pathArchivoSalida);
            out.write(build.toString().getBytes());
            //GRABA DATOS EN FACTURA//
            return pathArchivoSalida;

        } catch (FileNotFoundException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML DEBITO O CREDITO  FileNotFoundException" + ex);
        } catch (IOException ex) {
            System.out.println("ERROR EN LA GENERACION DE XML DEBITO O CREDITO IOException " + ex);
        }
        return "";
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" ARMAR RETENCION">
    public String generaXMLComprobanteRetencion(RetencionCompra retencion, Tipoambiente amb, String folderDestino, String nombreArchivoXML) {
        FileOutputStream out;
        try {
//            Empresa empresa = empresaFacade.findById(retencion.getIdempresa().longValue());
            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoPeriodo = new SimpleDateFormat("MM/yyyy");
            //tipoEmision(); //dependiendo de la coneccion al sri.
            String tipoemision = "1"; //en offline solo existe emision normal
            String claveAcceso = generaClave(retencion.getRcoFecha(), "07", amb.getAmRuc(), amb.getAmCodigo(), amb.getAmEstab().trim() + amb.getAmPtoemi().trim(), retencion.getRcoSecuencialText(), "12345678", "1");
//            String claveAcceso = generaClave(retencion.getRcoFecha(), "07", MB.getRucempresa(), ambienteTb.getCodigotipoamb(), serie, retencion.getSecuencialcaracter(), "12345678", tipoemision);
//            String claveAcceso = !contingencia
//                    ? generaClave(retencion.getFechaemision(), retencion.getCodigodcto(), empresa.getRucempresa(), ambienteTb.getCodigotipoamb(), serie, retencion.getSecuencialcaracter(), "12345678", tipoemision)
//                    //                    : generaClave(new Date(), "07", empresa.getRucempresa(), "1", serie, retencion.getSecuencialCaracter(), "12345678", "1");
//                    : generaClaveContingencia(retencion.getFechaemision(), retencion.getCodigodcto(), tipoemision);
            StringBuilder build = new StringBuilder();
            String linea = "";
            linea = ("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n"
                        + "<comprobanteRetencion id=\"comprobante\" version=\"1.0.0\">\n"
                        + "     <infoTributaria>\n"
                        + "        <ambiente>" + amb.getAmCodigo() + "</ambiente>\n"
                        + "        <tipoEmision>" + tipoemision + "</tipoEmision>\n"
                        + "        <razonSocial>" + removeCaracteres(amb.getAmRazonSocial()) + "</razonSocial>\n"
                        + "        <nombreComercial>" + removeCaracteres(amb.getAmNombreComercial()) + "</nombreComercial>\n"
                        + "        <ruc>" + amb.getAmRuc() + "</ruc>\n"
                        + "        <claveAcceso>" + claveAcceso + "</claveAcceso>\n"
                        + "        <codDoc>07</codDoc>\n"
                        + "        <estab>" + amb.getAmEstab() + "</estab>\n"
                        + "        <ptoEmi>" + amb.getAmPtoemi() + "</ptoEmi>\n"
                        + "        <secuencial>" + retencion.getRcoSecuencialText() + "</secuencial>\n"
                        + "        <dirMatriz>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirMatriz>\n"
                        + "    </infoTributaria>\n"
                        + "    <infoCompRetencion>\n"
                        + "        <fechaEmision>" + formato.format(retencion.getRcoFecha()) + "</fechaEmision>\n"
                        + "        <dirEstablecimiento>" + removeCaracteres(amb.getAmDireccionMatriz()) + "</dirEstablecimiento>\n"
                        //        + "        <contribuyenteEspecial>5368</contribuyenteEspecial>\n"
                        + "        <obligadoContabilidad>" + amb.getLlevarContabilidad() + "</obligadoContabilidad>\n"
                        + "        <tipoIdentificacionSujetoRetenido>" + retencion.getIdCabecera().getIdProveedor().getIdTipoIdentificacionCompra().getTicCodigo() + "</tipoIdentificacionSujetoRetenido>\n"
                        + "        <razonSocialSujetoRetenido>" + removeCaracteres(retencion.getIdCabecera().getIdProveedor().getProvNombre()) + "</razonSocialSujetoRetenido>\n"
                        + "        <identificacionSujetoRetenido>" + retencion.getIdCabecera().getIdProveedor().getProvCedula() + "</identificacionSujetoRetenido>\n"
                        + "        <periodoFiscal>" + formatoPeriodo.format(retencion.getRcoFecha()) + "</periodoFiscal>\n"
                        + "    </infoCompRetencion>\n"
                        + "<impuestos>\n");
            build.append(linea);
            Float suma = Float.MIN_VALUE;

            List<DetalleRetencionCompra> dret = servicioDetalleRetencionCompra.findByCanRetencion(retencion);
            for (DetalleRetencionCompra detalle : dret) {
                suma = suma + detalle.getDrcValorRetenido().setScale(2, RoundingMode.FLOOR).floatValue();

                linea = ("  <impuesto>\n"
                            + "         <codigo>" + detalle.getDrcCodImpuestoAsignado() + "</codigo>\n"
                            + "         <codigoRetencion>" + (detalle.getDrcDescripcion().equals("RENTA") ? detalle.getTireCodigo().getTireCodigo() : detalle.getIdTipoivaretencion().getTipivaretValor()) + "</codigoRetencion>\n"
                            + "         <baseImponible>" + ArchivoUtils.redondearDecimales(detalle.getDrcBaseImponible(), 2) + "</baseImponible>\n"
                            + "         <porcentajeRetener>" + detalle.getDrcPorcentaje() + "</porcentajeRetener>\n"
                            + "         <valorRetenido>" + ArchivoUtils.redondearDecimales(detalle.getDrcValorRetenido(), 2) + "</valorRetenido>\n"
                            + "         <codDocSustento>" + detalle.getRcoCodigo().getIdCabecera().getDrcCodigoSustento() + "</codDocSustento>\n"
                            + "         <numDocSustento>" + detalle.getRcoCodigo().getIdCabecera().getCabEstablecimiento().trim() + detalle.getRcoCodigo().getIdCabecera().getCabPuntoEmi().trim() + detalle.getRcoCodigo().getIdCabecera().getCabNumFactura().trim() + "</numDocSustento>\n"
                            + "         <fechaEmisionDocSustento>" + formato.format(detalle.getRcoCodigo().getIdCabecera().getCabFechaEmision()) + "</fechaEmisionDocSustento>\n"
                            + "   </impuesto>\n");
                build.append(linea);
            }

            linea = ("</impuestos>\n"
                        + " <infoAdicional>\n"
                        + ("<campoAdicional nombre=\"SUMA\">" + suma.toString() + "</campoAdicional>\n")
                        + (amb.getAmMicroEmp() ? "<campoAdicional nombre=\"Contribuyente Regimen Microempresas\">Contribuyente Regimen Microempresas</campoAdicional>\n" : "")
                        + (amb.getAmAgeRet() ? "<campoAdicional nombre=\"Agente de Retencion\">Agente de Retencion Resolucion Nro. NAC-DNCRASC20-00000001</campoAdicional>\n" : "")
                     + (amb.getAmRimpe() ? "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN RIMPE\">CONTRIBUYENTE REGIMEN RIMPE</campoAdicional>\n" : "<campoAdicional nombre=\"CONTRIBUYENTE REGIMEN GENERAL\">CONTRIBUYENTE REGIMEN GENERAL</campoAdicional>\n")
                        + "    </infoAdicional>\n"
                        + "</comprobanteRetencion>");
            build.append(linea);

            /*IMPRIME EL XML DE LA NOTA DE CREDITO O DEBITO*/
            System.out.println("XML " + build);
            String pathArchivoSalida = "";

            /*ruta de salida del archivo XML 
            generados o autorizados para enviar al cliente 
            dependiendo la ruta enviada en el parametro del metodo */
            pathArchivoSalida = folderDestino
                        + nombreArchivoXML;

            //String pathArchivoSalida = "D:\\";
            out = new FileOutputStream(pathArchivoSalida);
            out.write(build.toString().getBytes());
            //GRABA DATOS EN FACTURA//
            return pathArchivoSalida;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AutorizarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AutorizarDocumentos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    //</editor-fold>
}

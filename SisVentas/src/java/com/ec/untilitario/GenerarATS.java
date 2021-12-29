/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import com.ec.entidad.Acumuladoventas;
import com.ec.entidad.CabeceraCompra;
import com.ec.entidad.DetalleRetencionCompra;
import com.ec.entidad.RetencionCompra;
import com.ec.entidad.Tipoambiente;
import com.ec.servicio.ServicioDetalleRetencionCompra;
import com.ec.servicio.ServicioRetencionCompra;
import com.ec.servicio.ServicioTipoAmbiente;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Darwin
 */
public class GenerarATS {

    ServicioTipoAmbiente servicioTipoAmbiente = new ServicioTipoAmbiente();
    ServicioRetencionCompra servicioRetencionCompra = new ServicioRetencionCompra();
    ServicioDetalleRetencionCompra servicioDetalleRetencionCompra = new ServicioDetalleRetencionCompra();
    private static String PATH_BASE = "";

    public String generaXMLFactura(List<Acumuladoventas> detalleFactura, BigDecimal totalVentas, List<CabeceraCompra> detalleCompra, Date inicio, Date fin) {
        try {
            Tipoambiente amb = servicioTipoAmbiente.FindALlTipoambiente();
            PATH_BASE = amb.getAmDirBaseArchivos() + File.separator
                    + amb.getAmDirXml();

            String folderATS = PATH_BASE + File.separator + amb.getAmDirAts()
                    + File.separator + new Date().getYear()
                    + File.separator + new Date().getMonth();

            File folderGen = new File(folderATS);
            if (!folderGen.exists()) {
                folderGen.mkdirs();
            }

            FileOutputStream out = null;
//                        

            SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat formatoAnio = new SimpleDateFormat("yyyy");
            SimpleDateFormat formatoMes = new SimpleDateFormat("MM");

            StringBuilder build = new StringBuilder();
            String linea = "";
            DecimalFormat df = new DecimalFormat("#.##");

            linea = ("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                    + "<iva>\n");
            build.append(linea);
            linea = "";

            linea = (" <TipoIDInformante>R</TipoIDInformante>\n"
                    + " <IdInformante>" + amb.getAmRuc() + "</IdInformante>\n"
                    + " <razonSocial>" + AutorizarDocumentos.removeCaracteres(amb.getAmRazonSocial()) + "</razonSocial>\n"
                    + " <Anio>" + formatoAnio.format(inicio) + "</Anio>\n"
                    + " <Mes>" + formatoMes.format(inicio) + "</Mes>\n"
                    + " <numEstabRuc>" + amb.getAmEstab() + "</numEstabRuc>\n");
            build.append(linea);
            if (detalleFactura.size() > 0) {
                linea = (" <totalVentas>" + totalVentas.setScale(2, RoundingMode.HALF_UP) + "</totalVentas>\n");
                build.append(linea);
            }
            linea = (" <codigoOperativo>IVA</codigoOperativo>\n");

            build.append(linea);

            /*INICIO DEL ATS COMPRAS*/
            linea = ("  <compras>\n");
            build.append(linea);

            for (CabeceraCompra item : detalleCompra) {
                String tpIdProv = "01";
                switch (item.getIdProveedor().getProvCedula().length()) {
                    case 13:
                        tpIdProv = "01";
                        break;
                    case 12:
                        tpIdProv = "02";
                        break;
                    default:
                        tpIdProv = "03";
                        break;
                }

                BigDecimal subTotalGravaNograva = item.getCabSubTotal().add(item.getCabSubTotalCero());
                linea = ("        <detalleCompras>\n"
                        + "            <codSustento>01</codSustento>\n"
                        + "            <tpIdProv>" + tpIdProv + "</tpIdProv>\n"
                        + "            <idProv>" + item.getIdProveedor().getProvCedula() + "</idProv>\n"
                        + "            <tipoComprobante>01</tipoComprobante>\n"
                        + "            <parteRel>NO</parteRel>\n"
                        + "            <fechaRegistro>" + formato.format(item.getCabFechaEmision()) + "</fechaRegistro>\n"
                        + "            <establecimiento>" + item.getCabEstablecimiento().trim() + "</establecimiento>\n"
                        + "            <puntoEmision>" + item.getCabPuntoEmi() + "</puntoEmision>\n"
                        + "            <secuencial>" + item.getCabNumFactura() + "</secuencial>\n"
                        + "            <fechaEmision>" + formato.format(item.getCabFechaEmision()) + "</fechaEmision>\n"
                        + "            <autorizacion>" + item.getCabAutorizacion().trim() + "</autorizacion>\n"
                        + "            <baseNoGraIva>" + item.getCabSubTotalCero().setScale(2, RoundingMode.FLOOR) + "</baseNoGraIva>\n"
                        + "            <baseImponible>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</baseImponible>\n"
                        + "            <baseImpGrav>" + item.getCabSubTotal().setScale(2, RoundingMode.FLOOR) + "</baseImpGrav>\n"
                        + "            <baseImpExe>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</baseImpExe>\n"
                        + "            <montoIce>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</montoIce>\n"
                        + "            <montoIva>" + item.getCabIva().setScale(2, RoundingMode.HALF_UP) + "</montoIva>\n"
                        + "            <valRetBien10>0.00</valRetBien10>\n"
                        + "            <valRetServ20>0.00</valRetServ20>\n"
                        + "            <valorRetBienes>0.00</valorRetBienes>\n"
                        + "            <valRetServ50>0.00</valRetServ50>\n"
                        + "            <valorRetServicios>0.00</valorRetServicios>\n"
                        + "            <valRetServ100>0.00</valRetServ100>\n"
                        + "            <totbasesImpReemb>0.00</totbasesImpReemb>\n"
                        + "            <pagoExterior>\n"
                        + "                    <pagoLocExt>01</pagoLocExt>\n"
                        + "                    <paisEfecPago>NA</paisEfecPago>\n"
                        + "                    <aplicConvDobTrib>NA</aplicConvDobTrib>\n"
                        + "                    <pagExtSujRetNorLeg>NA</pagExtSujRetNorLeg>\n"
                        + "            </pagoExterior>\n"
                        + ((subTotalGravaNograva.doubleValue() >= 1000) ? "  <formasDePago>\n"
                        + "                <formaPago>01</formaPago>\n"
                        + "            </formasDePago>" : ""));

                build.append(linea);

                RetencionCompra rc = servicioRetencionCompra.findByCabeceraCompra(item);
                List< DetalleRetencionCompra> listaDetalleRetencion = servicioDetalleRetencionCompra.findByCanRetencion(rc);;
                if (listaDetalleRetencion.size() > 0) {
                    linea = ("            <air>\n");
                    build.append(linea);

                    for (DetalleRetencionCompra rcom : listaDetalleRetencion) {
                        if (!rcom.getTireCodigo().getTireCodigo().equals("001")) {
                            linea = ("              <detalleAir>\n"
                                    + "                    <codRetAir>" + rcom.getTireCodigo().getTireCodigo() + "</codRetAir>\n"
                                    + "                    <baseImpAir>" + rcom.getDrcBaseImponible().setScale(2, RoundingMode.HALF_UP) + "</baseImpAir>\n"
                                    + "                    <porcentajeAir>" + rcom.getDrcPorcentaje().setScale(2, RoundingMode.HALF_UP) + "</porcentajeAir>\n"
                                    + "                    <valRetAir>" + rcom.getDrcValorRetenido().setScale(2, RoundingMode.HALF_UP) + "</valRetAir>\n"
                                    + "              </detalleAir>\n");

                            build.append(linea);
                        }
                    }

                    linea = ("            </air>\n"
                            + "            <estabRetencion1>" + amb.getAmEstab() + "</estabRetencion1>\n"
                            + "            <ptoEmiRetencion1>" + amb.getAmPtoemi() + "</ptoEmiRetencion1>\n"
                            + "            <secRetencion1>" + rc.getRcoSecuencial() + "</secRetencion1>\n"
                            + "            <autRetencion1>" + rc.getRcoAutorizacion() + "</autRetencion1>\n"
                            + "            <fechaEmiRet1>" + formato.format(rc.getRcoFecha()) + "</fechaEmiRet1>\n");

                    build.append(linea);
                }
                linea = ("        </detalleCompras>\n");
                build.append(linea);
            }
            linea = ("    </compras>\n");
            build.append(linea);
            /*FIN DE ATS COMPRAS*/

 /*INICIO ATS VENTAS*/
            if (detalleFactura.size() > 0) {
                linea = ("  <ventas>\n");
                build.append(linea);

                for (Acumuladoventas item : detalleFactura) {

                    linea = ("        <detalleVentas>\n"
                            + "            <tpIdCliente>" + item.getTidCodigo() + "</tpIdCliente>\n"
                            + "            <idCliente>" + item.getCliCedula() + "</idCliente>\n");
                    build.append(linea);
                    if (!item.getTidCodigo().equals("07")) {
                        linea = ("            <parteRelVtas>NO</parteRelVtas>\n");
                        build.append(linea);
                    }
                    linea = ("            <tipoComprobante>18</tipoComprobante>\n"
                            + "            <tipoEmision>F</tipoEmision>\n"
                            + "            <numeroComprobantes>" + 1 + "</numeroComprobantes>\n"
                            + "            <baseNoGraIva>" + BigDecimal.ZERO + "</baseNoGraIva>\n"
                            + "            <baseImponible>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</baseImponible>\n"
                            + "            <baseImpGrav>" + item.getFacTotalBaseGravaba().setScale(2, RoundingMode.HALF_UP) + "</baseImpGrav>\n"
                            + "            <montoIva>" + item.getFacIva().setScale(2, RoundingMode.HALF_UP) + "</montoIva>\n"
                            + "            <montoIce>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</montoIce>\n"
                            + "            <valorRetIva>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</valorRetIva>\n"
                            + "            <valorRetRenta>" + BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP) + "</valorRetRenta>\n"
                            + "         <formasDePago>\n"
                            + "            <formaPago>" + item.getForCodigo() + "</formaPago>\n"
                            + "         </formasDePago>\n"
                            + "        </detalleVentas>\n");
                    build.append(linea);
                }

//            build.append(linea);
                linea = ("    </ventas>\n"
                        + "   <ventasEstablecimiento>\n"
                        + "      <ventaEst>\n"
                        + "         <codEstab>" + amb.getAmEstab() + "</codEstab>\n"
                        + "         <ventasEstab>" + totalVentas.setScale(2, RoundingMode.HALF_UP) + "</ventasEstab>\n"
                        + "         <ivaComp>0.00</ivaComp>\n"
                        + "      </ventaEst>\n"
                        + "   </ventasEstablecimiento>\n");
                build.append(linea);

            }

            linea = (" </iva>");
            build.append(linea);

            /*FIN DE ATS VENTAS*/
            System.out.println("XML " + build);
            String pathArchivoSalida = folderATS
                    + File.separator + "ATS_"
                    + formatoAnio.format(inicio) + "_"
                    + formatoMes.format(inicio) + ".xml";

            /*ruta de salida del archivo XML 
            generados o autorizados para enviar al cliente 
            dependiendo la ruta enviada en el parametro del metodo */
//            pathArchivoSalida = folderDestino
//                    + nombreArchivoXML;
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
}

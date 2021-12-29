/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.controlador;

import com.ec.entidad.MailMasivo;
import com.ec.servicio.ServicioMailMasivo;
import com.ec.untilitario.MailerClass;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.image.AImage;
import org.zkoss.io.Files;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author gato
 */
public class PublicidadViewModel {

    ServicioMailMasivo servicioMailMasivo = new ServicioMailMasivo();
//    ServicioParametros servicioParametros = new ServicioParametros();
    private List<MailMasivo> listaMail = new ArrayList<MailMasivo>();
    private ListModelList<MailMasivo> listaMailModel;
    private Set<MailMasivo> registrosSeleccionados;
    private String filePath = "";
    AMedia fileContent = null;
    private AImage foto1;
    File f = null;
    byte[] buffer = new byte[9 * 1024 * 1024];
    private String mensaje = "";
    private String buscarMail = "";
    private String asuntoMensage = "";
    private String nombreArchivoMail;
    private String categoria;

    public PublicidadViewModel() {
        consultarMail();
        getObtenerMail();
    }

    private void getObtenerMail() {
        setListaMailModel(new ListModelList<MailMasivo>(getListaMail()));
        ((ListModelList<MailMasivo>) listaMailModel).setMultiple(true);
    }

    @Command
    public void registrosSeleccionados() {
        registrosSeleccionados = ((ListModelList<MailMasivo>) getListaMailModel()).getSelection();
    }

    private void consultarMail() {
        listaMail = servicioMailMasivo.FindAllLike(buscarMail);
    }
    private void consultarMailCategoria() {
        listaMail = servicioMailMasivo.FindAllCategoria(categoria);
    }

    public ListModelList<MailMasivo> getListaMailModel() {
        return listaMailModel;
    }

    public void setListaMailModel(ListModelList<MailMasivo> listaMailModel) {
        this.listaMailModel = listaMailModel;
    }

    public Set<MailMasivo> getRegistrosSeleccionados() {
        return registrosSeleccionados;
    }

    public void setRegistrosSeleccionados(Set<MailMasivo> registrosSeleccionados) {
        this.registrosSeleccionados = registrosSeleccionados;
    }

    public List<MailMasivo> getListaMail() {
        return listaMail;
    }

    public void setListaMail(List<MailMasivo> listaMail) {
        this.listaMail = listaMail;
    }

    @Command
    public void enviarMail() {
        boolean estadoEnvio = true;
        int smsEnviados = 0;
        try {

            subirArchivoFTP(nombreArchivoMail, filePath);
            List<String> listaEnvio = new ArrayList<String>();
            for (MailMasivo contactos : registrosSeleccionados) {
                listaEnvio.add(contactos.getEmail());
            }
            if (listaEnvio.size() > 0) {
            } else {
                Messagebox.show("Seleccione al menos un contacto para enviar", "Atención", Messagebox.OK, Messagebox.ERROR);
                return;
            }
            MailerClass mailerClass = new MailerClass();
//            mailerClass.sendMail("darwinvinicio14_11@hotmail.com", "IMAGENDIGITAL", pathAdjunto);

//            estadoEnvio = mailerClass.sendMail(listaEnvio, mensaje, "", asuntoMensage, nombreArchivoMail, rutaFTP);
//            for (int i = 0; i < 30; i++) {
            for (String email : listaEnvio) {

//                estadoEnvio = mailerClass.sendMailSimple(email, mensaje, "", asuntoMensage, nombreArchivoMail, rutaFTP);
                smsEnviados++;
            }
//            }



            if (estadoEnvio) {
                Messagebox.show("Se enviaron " + smsEnviados + " mails masivamentes");
                Executions.sendRedirect("/administrar/publicidad.zul");
            } else {
                Messagebox.show("Ocurrio un error en envio", "Atención", Messagebox.OK, Messagebox.ERROR);
//                Executions.sendRedirect("/cotizacion/cotizacion.zul");
            }

        } catch (Exception e) {
            Messagebox.show("Fallo envio" + e.toString(), "Atención", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    @Command
    @NotifyChange("foto1")
    public void subirArchivo() throws InterruptedException, IOException {

        org.zkoss.util.media.Media media = Fileupload.get();

        nombreArchivoMail = media.getName().toString();
        System.out.println("Nombre archivo " + nombreArchivoMail);
        if (media instanceof org.zkoss.image.Image) {
//            if (media != null && nombre.contains("pdf")) {
            if (media.getByteData().length > 9 * 1024 * 1024) {
                Messagebox.show("El arhivo seleccionado sobrepasa el tamaño de 9 MB.\n Por favor seleccione un archivo más pequeño.", "Atención", Messagebox.OK, Messagebox.ERROR);

                return;
            }

            Calendar now = Calendar.getInstance();
            int year = now.get(Calendar.YEAR);
            int month = now.get(Calendar.MONTH);// Note: zero based!
            int day = now.get(Calendar.DAY_OF_MONTH);
//                filePath = Executions.getCurrent().getDesktop().getWebApp()
//                        .getRealPath("/");
            filePath = "F:";
            String yearPath = year + File.separator + month + File.separator;
            filePath = filePath + yearPath;

            File baseDir = new File(filePath);
            if (!baseDir.exists()) {
                baseDir.mkdirs();
            }
//                Filedownload.save(media);
            Files.copy(new File(filePath + media.getName()),
                    media.getStreamData());

            filePath = filePath + media.getName();
            System.out.println("pathhhhhhh " + filePath);
            f = new File(filePath);
            // Messagebox.show(" dfdfdfdsfdsf" + filePath);

            FileInputStream fs = new FileInputStream(f);
            fs.read(buffer);
            fs.close();

//                ByteArrayInputStream is = new ByteArrayInputStream(buffer);
//                fileContent = new AMedia("report", "pdf", "application/pdf", is);
            foto1 = new AImage("foto", media.getByteData());

//                byte[] bFile = new byte[(int) f.length()];
//                FileInputStream fileInputStream = null;
            try {
                //convert file into array of bytes
//                    fileInputStream = new FileInputStream(f);
//                    fileInputStream.read(bFile);
//                    fileInputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Messagebox.show("El arhivo seleccionado no es un archivo valido.\n Seleccione un archivo pdf", "Atención", Messagebox.OK, Messagebox.ERROR);
        }


    }
    private String rutaFTP = "";

    public void subirArchivoFTP(String nombre, String ruta) {
        try {
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(InetAddress.getByName("dereckylaydy.com"));
            ftpClient.login("imagenec@dereckylaydy.com", "Dereckandre02");

            //Verificar conexión con el servidor.

            int reply = ftpClient.getReplyCode();

            System.out.println("Respuesta recibida de conexión FTP:" + reply);

            if (FTPReply.isPositiveCompletion(reply)) {
                System.out.println("Conectado Satisfactoriamente");
            } else {
                System.out.println("Imposible conectarse al servidor");
            }

            //Verificar si se cambia de direcotirio de trabajo

//            ftpClient.changeWorkingDirectory("/");//Cambiar directorio de trabajo
//            System.out.println("Se cambió satisfactoriamente el directorio");

            //Activar que se envie cualquier tipo de archivo

            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

            BufferedInputStream buffIn = null;
            buffIn = new BufferedInputStream(new FileInputStream(ruta));//Ruta del archivo para enviar
            ftpClient.enterLocalPassiveMode();
            ftpClient.storeFile(nombreArchivoMail, buffIn);//Ruta completa de alojamiento en el FTP
            System.out.println("Subio correctamenet");
            buffIn.close(); //Cerrar envio de arcivos al FTP
            ftpClient.logout(); //Cerrar sesión
            ftpClient.disconnect();//Desconectarse del servidor
            rutaFTP = "https://dereckylaydy.com/dereckylaydy.com/imagenec/" + nombreArchivoMail;
//            rutaFTP = "https://imagenec.com/imagenec.com/imagenftp/" + nombreArchivoMail;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Algo malo sucedió" + e);
        }

    }

    public AMedia getFileContent() {
        return fileContent;
    }

    public void setFileContent(AMedia fileContent) {
        this.fileContent = fileContent;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getBuscarMail() {
        return buscarMail;
    }

    public void setBuscarMail(String buscarMail) {
        this.buscarMail = buscarMail;
    }

    public String getNombreArchivoMail() {
        return nombreArchivoMail;
    }

    public void setNombreArchivoMail(String nombreArchivoMail) {
        this.nombreArchivoMail = nombreArchivoMail;
    }

    @Command
    @NotifyChange({"listaMailModel", "buscarMail"})
    public void buscarMailing() {
        consultarMail();
        getObtenerMail();
    }
    @Command
    @NotifyChange({"listaMailModel", "buscarMail"})
    public void buscarMailingCategoria() {
        consultarMailCategoria();
        getObtenerMail();
    }

    public String getAsuntoMensage() {
        return asuntoMensage;
    }

    public void setAsuntoMensage(String asuntoMensage) {
        this.asuntoMensage = asuntoMensage;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public AImage getFoto1() {
        return foto1;
    }

    public void setFoto1(AImage foto1) {
        this.foto1 = foto1;
    }

    public String getRutaFTP() {
        return rutaFTP;
    }

    public void setRutaFTP(String rutaFTP) {
        this.rutaFTP = rutaFTP;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.untilitario;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.sql.DataSource;

/**
 *
 * @author gato
 */
public class ConexionReportes {

    public static class Conexion {

        private static Connection con = null;

//        public static Connection conexion() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
//
//            Class.forName("com.mysql.jdbc.Driver").newInstance();
//         
////            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cotizacionimprenta", "extdbuser", "qELbV0d3Q2");
//            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/cotizacionimprenta", "root", "root");
//
//            if (con != null) {
//                System.out.println("Conexión Realizada Correctamenteeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
//                return con;
//            } else {
//                return con;
//            }
//        }
        public static Connection conexion() throws NamingException {

            Connection cnn = null;



            try {

                InitialContext initialContext = new InitialContext();

                DataSource ds = (DataSource) initialContext.lookup("jdbc/ventas");
                

                try {

                    con = ds.getConnection();

                } catch (SQLException sqle) {

                    System.out.println("Error obteniendo la conexión en ConexionReportes: " + sqle.getMessage());

                }

            } catch (NamingException ne) {

                System.out.println("Error en el método getConnection() de la clase ConexionReportes: " + ne.getMessage());

            }



            return con;

        }
    }
}

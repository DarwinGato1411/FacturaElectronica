/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.seguridad;

public interface AutentificadorService {

	/**login with account and password**/
	public boolean login(String account, String password);
	
	/**logout current user**/
	public void logout();
	
	/**get current user credential**/
	public UserCredential getUserCredential();
	
}

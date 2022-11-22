/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import pojos.RespuestaLogin;

/**
 *
 * @author andre
 */
@Path("acceso")
@Produces(MediaType.APPLICATION_JSON)
public class AccesoWS {
    @Context
    private UriInfo context;
    
    public AccesoWS(){
        
    }
    
    @Path("escritorio")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaLogin iniciarSesionEscritorio(@FormParam("noPersonal") String noPersonal,
                                                  @FormParam("password") String password){
           RespuestaLogin resp = new RespuestaLogin();
           
           if(noPersonal.equals("admin") && password.equals("12345")){
               resp.setError(false);
               resp.setMensaje("Usuario encontrado...");
               resp.setNombre("Saraí");
               resp.setApellidoPaterno("Mávil");
           }else{
               resp.setError(true);
               resp.setMensaje("Número de personal y/o contraseña incorrectos... ");
           }
           return resp;
    }
    
    @Path("movil")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public RespuestaLogin iniciarSesionMovil(@FormParam("usuario") String usuario,
                                             @FormParam("password") String password){
        RespuestaLogin respuesta = new RespuestaLogin();
        //Temporal
        if(usuario.equals("Sarai") && password.equals("12345")){
               respuesta.setError(false);
               respuesta.setMensaje("Paciente encontrado...");
               respuesta.setNombre("Saraí");
               respuesta.setApellidoPaterno("Mávil");
               respuesta.setApellidoMaterno("Cano");
           }else{
               respuesta.setError(true);
               respuesta.setMensaje("No exixte registro de ningun paciente con esos datos...");
           }
           return respuesta;
    }
}

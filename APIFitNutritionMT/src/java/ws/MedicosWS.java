/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.util.List;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import mybatis.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;
import pojos.Medico;
import pojos.Respuesta;

/**
 * REST Web Service
 *
 * @author andre
 */
@Path("medicos")
public class MedicosWS {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of MedicosWS
     */
    public MedicosWS() {
    }
    
    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Medico> buscarTodos(){
        List<Medico> medicos = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                medicos = conn.selectList("medico.getAllMedicos");
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return medicos;
    }
    
    @Path("byid/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Medico buscarById(@PathParam("id") Integer idMedico){
        Medico medicoResultado = null;
        SqlSession conn = MyBatisUtil.getSession();
        if(conn != null){
            try{
                medicoResultado = conn.selectOne("medico.getById", idMedico);
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                conn.close();
            }
        }
        return medicoResultado;
    }
    
    @Path("registrar")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta registrar(
            @FormParam("nombre") String nombre,
            @FormParam("apellidoPaterno") String apellidoPaterno,
            @FormParam("apellidoMaterno") String apellidoMaterno,
            @FormParam("fechaNacimiento") String fechaNacimiento,
            @FormParam("genero") String genero,
            @FormParam("domicilio") String domicilio,
            @FormParam("numPersonal") String numPersonal,
            @FormParam("cedula") String cedula,
            @FormParam("password") String password,
            @FormParam("rol") Integer idRol) {

        Medico medicoRegistro = new Medico();
        medicoRegistro.setNombre(nombre);
        medicoRegistro.setApellidoPaterno(apellidoPaterno);
        medicoRegistro.setApellidoMaterno(apellidoMaterno);
        medicoRegistro.setFechaNacimiento(fechaNacimiento);
        medicoRegistro.setGenero(genero);
        medicoRegistro.setDomicilio(domicilio);
        medicoRegistro.setNoPersonal(numPersonal);
        medicoRegistro.setCedula(cedula);
        medicoRegistro.setPassword(password);
        medicoRegistro.setIdRol(idRol);

        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int resultadoMapper = conexionBD.insert("medico.registrar", medicoRegistro);
                conexionBD.commit();
                if (resultadoMapper > 0) {
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Médico registrado correctamente...");
                } else {
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("No se pudo guardar el registro enviado...");
                }
            } catch (Exception e) {
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuestaWS.setError(true);
            respuestaWS.setMensaje("Sin conexión con el sistema...");
        }
        return respuestaWS;
    }
    
    @Path("modificar")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public String modificar(@FormParam("idMedico") String idMedico,
                     @FormParam("nombre") String nombre, 
                     @FormParam("apellidoPaterno") String apellidoPaterno, 
                     @FormParam("apellidoMaterno") String apellidoMaterno, 
                     @FormParam("fechaNacimiento") String fechaNacimiento,
                     @FormParam("genero") String genero,
                     @FormParam("domicilio") String domicilio,
                     @FormParam("cedula") String cedula,
                     @FormParam("password") String password,
                     @FormParam("rol") Integer idRol){
        
        return "";
    }
    
    @Path("eliminar")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Respuesta eliminar(@FormParam("idMedico") Integer idMedico) {
        Respuesta respuestaWS = new Respuesta();
        SqlSession conexionBD = MyBatisUtil.getSession();
        if (conexionBD != null) {
            try {
                int resultadoMapper = conexionBD.update("medico.eliminar", idMedico);
                conexionBD.commit();
                if (resultadoMapper > 0) {
                    respuestaWS.setError(false);
                    respuestaWS.setMensaje("Médico eliminado correctamente...");
                } else {
                    respuestaWS.setError(true);
                    respuestaWS.setMensaje("El identificador del médico enviado, no existe...");
                }
            } catch (Exception e) {
                respuestaWS.setError(true);
                respuestaWS.setMensaje(e.getMessage());
            } finally {
                conexionBD.close();
            }
        } else {
            respuestaWS.setError(true);
            respuestaWS.setMensaje("Sin conexión con el sistema...");
        }
        return respuestaWS;
    }
}

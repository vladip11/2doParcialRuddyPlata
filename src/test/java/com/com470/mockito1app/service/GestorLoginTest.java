
package com.com470.mockito1app.service;

import com.com470.mockito1app.controller.ExcepcionCuentaEnUso;
import com.com470.mockito1app.controller.ExcepcionUsuarioDesconocido;
import com.com470.mockito1app.controller.ICuenta;
import com.com470.mockito1app.controller.IRepositorioCuentas;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author VladimirP11
 * 
 * Link del repositorio https://github.com/vladip11/2doParcialRuddyPlata.git
 */
public class GestorLoginTest {
    
    public GestorLoginTest() {
    }
    
    @Mock
    private ICuenta cuenta;
    
    @Mock
    private IRepositorioCuentas repo;
    
    @InjectMocks
    GestorLogin gestorLogin;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
  
    @Test
    public void testInicio() {
        
       Mockito.when(repo.buscar("pepe")).thenReturn(cuenta);
       
    }
    
    @Test
    public void testAccesoConcedidoprimera() {
       Mockito.when(cuenta.claveCorrecta("1234")).thenReturn(true);
       
    }
    
    @Test
    public void testAccesoDenegadoPrimera() {
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("4567")).thenReturn(false);
       
       gestorLogin.acceder("jaime", "4567");
    }
    
    @Test(expected = ExcepcionUsuarioDesconocido.class)
    public void testUsuarioIncorrecto() {
       when(repo.buscar("bartolo")).thenReturn(null);
       gestorLogin.acceder("bartolo", "1234");
       
    }
    
    @Test
    public void testBloqueo3Intentos() {
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("4567")).thenReturn(false);
       
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("7896")).thenReturn(false);
       
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("4656")).thenReturn(false);
       
       Mockito.doNothing().when(cuenta).bloquearCuenta();
       
       gestorLogin.acceder("jaime", "4567");
       gestorLogin.acceder("jaime", "7896");
       gestorLogin.acceder("jaime", "4656");
       
    }
    
    @Test
    public void testAccede2Intentos() {
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("4567")).thenReturn(false);
       
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("1234")).thenReturn(true);
       
       Mockito.doNothing().when(cuenta).entrarCuenta();
       gestorLogin.acceder("jaime", "4567");
       gestorLogin.acceder("jaime", "1234");
    }
    
    @Test
    public void testAccedeUnIntento() {
       Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
       Mockito.when(cuenta.estaBloqueada()).thenReturn(false);
       Mockito.when(cuenta.claveCorrecta("4567")).thenReturn(true);
       Mockito.doNothing().when(cuenta).entrarCuenta();
       gestorLogin.acceder("jaime", "4567");
       
    }
    
    @Test(expected = ExcepcionUsuarioDesconocido.class)
    public void testBloquearTrasCuatroIntentos() {
       Mockito.when(cuenta.claveCorrecta("1234")).thenReturn(false);
       gestorLogin.acceder("pepe", "1234");
    }
    
    @Test
    public void testAccesoDenegadoCuentaBloqueada() {
        Mockito.when(repo.buscar("jaime")).thenReturn(cuenta);
        Mockito.when(cuenta.estaBloqueada()).thenReturn(true);
        gestorLogin.acceder("jaime", "1234");
       
    }
    
    @Test(expected = ExcepcionCuentaEnUso.class)
    public void testCuentaEnUso() {
       Mockito.when(repo.buscar("pepe")).thenReturn(cuenta);
       Mockito.when(cuenta.estaEnUso()).thenReturn(true);
       gestorLogin.acceder("pepe", "1234");
    }
    
    //Link del repositorio https://github.com/vladip11/2doParcialRuddyPlata.git
}

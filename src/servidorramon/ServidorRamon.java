/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorramon;

import java.io.*;
import java.net.*;
import java.util.Random;

/**
 *
 * @author 2_web9_06
 */
public class ServidorRamon {

    public void iniciaConexion() throws IOException {
        
        ServerSocket socketServidor = new ServerSocket(7777);
        Socket conexion;
        
        boolean centinela = true;
                
        do {
            conexion = socketServidor.accept();
            
            System.out.println("Esperando cliente");
            System.out.println("Aceptado transporte desde " + conexion.getInetAddress() + ":" + conexion.getPort());
            
            BufferedReader desdeCliente = 
                    new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            PrintWriter haciaCliente = new PrintWriter(conexion.getOutputStream());
            
            do {
                String[] frases = {
                    "A quien madruga dios le ayuda", 
                    "No por mucho madrugar amanece mas temprano", 
                    "A buen entendedor, pocas palabras bastan",
                    "A caballo regalado, no le mires el diente",
                    "A cada cerdo le llega su San Martín"
                };
                
                if(desdeCliente.readLine().equals("FORTUNE")) {
                    Random frase = new Random();
                    int posicion = frase.nextInt(5);
                    haciaCliente.println(frases[posicion]);
                    
                } else if(desdeCliente.readLine().equals("RANDOM")) {
                    Random r = new Random();
                    int numeroRandom = r.nextInt(1000);
                    haciaCliente.println(numeroRandom);
                } else if(desdeCliente.readLine().equals("BYE")) {
                    conexion.close();
                } else {
                    haciaCliente.println("ERROR");
                }
            } while(!(desdeCliente.readLine().equals("BYE")));
            
            
        } while(!centinela);
    }
    
    public void run() throws IOException {
        iniciaConexion();
    }
    
    public static void main(String[] args) throws Exception {
        new ServidorRamon().run();
        
    
    }
    
}

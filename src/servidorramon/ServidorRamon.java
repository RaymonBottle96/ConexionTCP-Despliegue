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
        String fraseCliente;
        ServerSocket socketServidor = new ServerSocket(7777);
        Socket conexion;

        do {

            System.out.println("Esperando cliente...");
            conexion = socketServidor.accept();
            System.out.println("Aceptado transporte desde " + conexion.getInetAddress() + ":"
                    + conexion.getPort());
            BufferedReader desdeCliente
                    = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            
            PrintWriter haciaCliente = new PrintWriter(conexion.getOutputStream(), true);

            String[] frases = {
                "A quien madruga dios le ayuda",
                "No por mucho madrugar amanece mas temprano",
                "A buen entendedor, pocas palabras bastan",
                "A caballo regalado, no le mires el diente",
                "A cada cerdo le llega su San Martín"
            };

            do {
                fraseCliente = desdeCliente.readLine();

                if (fraseCliente.equals("FORTUNE")) {
                    Random frase = new Random();
                    int posicion = frase.nextInt(5);
                    haciaCliente.println(frases[posicion]);

                } else if (fraseCliente.equals("RANDOM")) {
                    Random r = new Random();
                    int numeroRandom = r.nextInt(1000);
                    haciaCliente.println(numeroRandom);
                } 
                
            } while (!(fraseCliente.equals("BYE") || fraseCliente.equals("SHUTDOWN") ));
            System.out.println("Conexion con el cliente cortada");
            conexion.close();
        } while (!(fraseCliente.equals("SHUTDOWN")));
        System.out.println("Iniciando apagado del servidor");
    }
    
    public void run() throws IOException {
        iniciaConexion();
    }
    
    public static void main(String[] args) throws Exception {
        new ServidorRamon().run();
        
    
    }
    
}

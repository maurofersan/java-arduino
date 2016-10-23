package com.mauro.javaduino.conection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.swing.JOptionPane;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ReaderWriterArduino {

    private final int TIME_OUT = 2000; //Milisegundos
    private final int DATA_RATE = 9600;
    //variables de conexion
    private SerialPort serialPort;
    private final String PUERTO = "COM3";
    private OutputStream output = null;
    private InputStream input = null;
    
    private String ultimoDato;

    public void inicializarConexion() {
        CommPortIdentifier puertoID = null;
        Enumeration<?> puertoEnum = CommPortIdentifier.getPortIdentifiers();
        while (puertoEnum.hasMoreElements()) {
            CommPortIdentifier actualPuertoID = (CommPortIdentifier) puertoEnum.nextElement();
            if (actualPuertoID.getName().equals(PUERTO)) {
                puertoID = actualPuertoID;
                break;
            }
        }
        if (puertoID == null) {
            mostrarError("No se puede conectar al puerto");
        }
        try {
            serialPort = (SerialPort) puertoID.open(ReaderWriterArduino.class.getName(), TIME_OUT);
            //Parametros Puerto Serie
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            output = serialPort.getOutputStream();
            input = serialPort.getInputStream();
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    public void enviarDatos(String dato) {
        try {
            output.write(dato.getBytes());
        } catch (Exception e) {
            mostrarError("Error al enviar datos");
        }
    }
    
    public void recibir() {
        String resultado = "";
        try {
            while (true) {
                int caracterInt = input.read();
                System.out.println("Recibido el dato.");
                if (caracterInt == -1) {
                    continue;
                }
                if (caracterInt == 10 || caracterInt == 13) {
                    if (resultado.isEmpty()) {
                        continue;
                    }
                    ultimoDato = resultado;
                    resultado = "";
                    continue;
                }
                resultado += (char) caracterInt;
            }
        } catch (IOException ex) {
        	ex.printStackTrace();
        }
    }

    private void mostrarError(String message) {
        JOptionPane.showMessageDialog(null, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
    
    public String getUltimoDato() {
        return ultimoDato;
    }

    public void iniciarLectura() {
        new Thread(() -> ReaderWriterArduino.this.recibir()).start();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ReaderWriterArduino.this.recibir();
//            }
//        }).start();
        
//        new Thread() {
//            @Override
//            public void run() {
//                ReaderWriterArduino.this.recibir();
//            }
//        }.start();
    }
}



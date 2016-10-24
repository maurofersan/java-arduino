package com.mauro.javaduino.conection;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JOptionPane;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class ReaderWriterArduino {
	
    private final int TIME_OUT = 2000; //Milisegundos
    private final int DATA_RATE = 9600;
    //variables de conexion
    private SerialPort serialPort;
    private final String PUERTO = "COM3";
    private OutputStream output;
    private InputStream input;
    private String ultimoDato;
    private Map<String, String> ultimoDatoPorPrefijo = new ConcurrentHashMap<>();
    
    private static ReaderWriterArduino instance;

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
        	e.printStackTrace();
            mostrarError(e.getMessage());
        }
    }

    public void enviarDatos(String dato) {
        try {
            output.write(dato.getBytes());
        } catch (Exception e) {
        	e.printStackTrace();
            mostrarError("Error al enviar datos");
        }
    }
    
    public void recibir() {
        String resultado = "";
        try {
            while (true) {
                int caracterInt = input.read();

                if (caracterInt == -1) {
                    continue;
                }
                if (caracterInt == 10 || caracterInt == 13) {
                    if (resultado.isEmpty()) {
                        continue;
                    }
                    ultimoDato = resultado;

                    String[] splitedData = ultimoDato.split("\\|");
                    String key = splitedData[0];
                    String value =  splitedData[1];
                    String currentValue = ultimoDatoPorPrefijo.get(key);
                    if (!value.equals(currentValue)) {
                    	ultimoDatoPorPrefijo.put(key, value);
                    }

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
    
    public String getUltimoDato(String prefijo) {
        return ultimoDatoPorPrefijo.get(prefijo);
    }

    public String resetUltimoDato(String prefijo) {
        return ultimoDatoPorPrefijo.remove(prefijo);
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
    
    public void close() {
    	try {
			if (input != null) {
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	try {
			if (output != null) {
				output.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static ReaderWriterArduino getInstance() {
    	if (instance == null) {
    		instance = new ReaderWriterArduino();
    		instance.inicializarConexion();
    		instance.iniciarLectura();
    	}
    	return instance;
    }

}



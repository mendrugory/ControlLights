/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllights;

//import para la entrada por teclado:
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;



/**
 *
 * @author mendrugory
 *
 *
 *
 */
public abstract class SerialPortCommunicator implements SerialPortEventListener
{

    private boolean connected = false;
    protected SerialPort serialPort;
    /**
     * The port we're normally going to use.
     */
    private String portName;
    /**
     * Buffered input stream from the port
     */
    protected InputStream input;
    /**
     * The output stream to the port
     */
    protected OutputStream output;
    /**
     * Parameters
     */
    protected int timeOut; // = 2000;
    protected int dataRate;// = 115200;
    protected int dataBits; //SerialPort.DATABITS_8
    protected int stopBits; // SerialPort.STOPBITS_1
    protected int parity; // SerialPort.PARITY_NONE

    protected void setParameters(SerialPortParameters parameters)
    {
        timeOut = parameters.getTimeOut();
        dataRate = parameters.getDataRate();
        dataBits = parameters.getDataBits();
        stopBits = parameters.getStopBits();
        parity = parameters.getParity();
    }
    
    protected void connect()
    {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
       
        // iterate through, looking for the port
        while (portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals(getPortName()))
            {
                portId = currPortId;
            }
        }

        if (portId == null)
        {
            connected = false;
            System.out.println("Could not find port.");
        } else
        {

            try
            {
                // open serial port, and use class name for the appName.
                serialPort = (SerialPort) portId.open(this.getClass().getName(),
                        timeOut);

                // set port parameters
                serialPort.setSerialPortParams(dataRate,dataBits,stopBits,parity);

                // open the streams
                input = serialPort.getInputStream();
                output = serialPort.getOutputStream();

                // add event listeners
                serialPort.addEventListener(this);
                serialPort.notifyOnDataAvailable(true);
                connected = true;
            } 
            catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
    }

    /**
     * This should be called when you stop using the port. This will prevent
     * port locking on platforms like Linux.
     */
    protected synchronized void close()
    {
        if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
        }
        connected = false;
    }

    protected synchronized boolean isConnected()
    {
        if (connected)
        { // puedo sustituirlo por serialPort !=null
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Handle an event on the serial port. Read the data and print it.
     */
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
        {
            try
            {
                int available = input.available();
                byte chunk[] = new byte[available];
                input.read(chunk, 0, available);

                // Displayed results are codepage dependent
                System.out.println("");
                System.out.println("Arduino says: " + new String(chunk));
            } catch (Exception e)
            {
                System.err.println(e.toString());
            }
        }
        // Ignore all the other eventTypes, but you should consider the other ones.
    }

    public synchronized void sendMessage(String cadena)
    {
        byte[] asd;
        asd = cadena.getBytes();
        try
        {
            output.write(asd);
        } catch (IOException ex)
        {
            System.out.println("Problems sending message: " + ex.toString());
        }
    }

    /**
     * @return the portName
     */
    public String getPortName()
    {
        return portName;
    }

    /**
     * @param portName the portName to set
     */
    public void setPortName(String portName)
    {
        this.portName = portName;
    }
}
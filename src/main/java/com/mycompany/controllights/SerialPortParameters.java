/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllights;

/**
 *
 * @author mendrugory
 */
public class SerialPortParameters
{
    private int timeOut;
    private int dataRate;
    private int dataBits;
    private int stopBits;
    private int parity;

    /**
     * @return the timeOut
     */
    public int getTimeOut()
    {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(int timeOut)
    {
        this.timeOut = timeOut;
    }

    /**
     * @return the dataRate
     */
    public int getDataRate()
    {
        return dataRate;
    }

    /**
     * @param dataRate the dataRate to set
     */
    public void setDataRate(int dataRate)
    {
        this.dataRate = dataRate;
    }

    /**
     * @return the dataBits
     */
    public int getDataBits()
    {
        return dataBits;
    }

    /**
     * @param dataBits the dataBits to set
     */
    public void setDataBits(int dataBits)
    {
        this.dataBits = dataBits;
    }

    /**
     * @return the stopBits
     */
    public int getStopBits()
    {
        return stopBits;
    }

    /**
     * @param stopBits the stopBits to set
     */
    public void setStopBits(int stopBits)
    {
        this.stopBits = stopBits;
    }

    /**
     * @return the parity
     */
    public int getParity()
    {
        return parity;
    }

    /**
     * @param parity the parity to set
     */
    public void setParity(int parity)
    {
        this.parity = parity;
    }
    
}

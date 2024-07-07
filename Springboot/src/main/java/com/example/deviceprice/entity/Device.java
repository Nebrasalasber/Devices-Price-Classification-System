package com.example.deviceprice;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int batteryPower;
    private int blue;
    private double clockSpeed;
    private int dualSim;
    private int fc;
    private int fourG;
    private int intMemory;
    private double mDep;
    private int mobileWt;
    private int nCores;
    private int pc;
    private int pxHeight;
    private int pxWidth;
    private int ram;
    private int scH;
    private int scW;
    private int talkTime;
    private int threeG;
    private int touchScreen;
    private int wifi;
    private int priceRange;
}

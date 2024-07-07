package com.example.deviceprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    private final String pythonApiUrl = "http://localhost:5000/predict";

    @GetMapping
    public List<Device> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{id}")
    public Device getDeviceById(@PathVariable Long id) {
        return deviceService.getDeviceById(id);
    }

    @PostMapping
    public Device addDevice(@RequestBody Device device) {
        return deviceService.addDevice(device);
    }

    @PostMapping("/predict/{deviceId}")
    public Device predictDevicePrice(@PathVariable Long deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        if (device == null) {
            throw new RuntimeException("Device not found");
        }

        int predictedPrice = callPythonApiForPrediction(device);
        device.setPriceRange(predictedPrice);
        return deviceService.addDevice(device);
    }

    private int callPythonApiForPrediction(Device device) {
        RestTemplate restTemplate = new RestTemplate();
        PredictionRequest request = new PredictionRequest(device);

        PredictionResponse response = restTemplate.postForObject(pythonApiUrl, request, PredictionResponse.class);
        return response.getPriceRange();
    }

    static class PredictionRequest {
        private int battery_power;
        private int blue;
        private double clock_speed;
        private int dual_sim;
        private int fc;
        private int four_g;
        private int int_memory;
        private double m_dep;
        private int mobile_wt;
        private int n_cores;
        private int pc;
        private int px_height;
        private int px_width;
        private int ram;
        private int sc_h;
        private int sc_w;
        private int talk_time;
        private int three_g;
        private int touch_screen;
        private int wifi;

        public PredictionRequest(Device device) {
            this.battery_power = device.getBatteryPower();
            this.blue = device.getBlue();
            this.clock_speed = device.getClockSpeed();
            this.dual_sim = device.getDualSim();
            this.fc = device.getFc();
            this.four_g = device.getFourG();
            this.int_memory = device.getIntMemory();
            this.m_dep = device.getMDep();
            this.mobile_wt = device.getMobileWt();
            this.n_cores = device.getNCores();
            this.pc = device.getPc();
            this.px_height = device.getPxHeight();
            this.px_width = device.getPxWidth();
            this.ram = device.getRam();
            this.sc_h = device.getScH();
            this.sc_w = device.getScW();
            this.talk_time = device.getTalkTime();
            this.three_g = device.getThreeG();
            this.touch_screen = device.getTouchScreen();
            this.wifi = device.getWifi();
        }

    }

    static class PredictionResponse {
        private int price_range;

        public int getPriceRange() {
            return price_range;
        }

        public void setPriceRange(int price_range) {
            this.price_range = price_range;
        }
    }
}

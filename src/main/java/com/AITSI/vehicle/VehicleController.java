package com.AITSI.vehicle;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    String vehicles(Model model) {
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehicle";
    }

    @GetMapping("/{type}")
    String vehiclesByType(@PathVariable("type") String type, Model model) {
        model.addAttribute("vehicles", vehicleService.findByVehicleType(type));
        return "vehicle";
    }

    @PostMapping
    String addVehicle(Vehicle vehicle, HttpServletRequest request) {
        vehicleService.saveVehicle(vehicle);
        String referer = request.getHeader("Referer");
        return "redirect:"+ referer;
    }

    @GetMapping("/delete/{id}")
    String deleteVehicle(@PathVariable("id") Long id, Model model){
        vehicleService.deleteVehicle(id);
        model.addAttribute("vehicles", vehicleService.findAll());
        return "vehicle";
    }

    @PostMapping("/rent/{id}")
    @ResponseBody
    Boolean rentVehicle(@PathVariable("id") Long id, VehicleOrder vehicleOrder, Model model, HttpServletRequest request){
        model.addAttribute("vehicles", vehicleService.findAll());
        return vehicleService.rentVehicle(id, vehicleOrder);
    }

    @GetMapping("/history/{id}")
    @ResponseBody
    List<VehicleOrder> getOrders(@PathVariable("id") Long id){
        return vehicleService.getVehicleOrdersByVehicleId(id);
    }
}

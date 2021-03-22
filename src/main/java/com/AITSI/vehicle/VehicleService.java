package com.AITSI.vehicle;

import com.AITSI.user.User;
import com.AITSI.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final UserService userService;

    public List<Vehicle> findAll(){
        return vehicleRepository.findAll();
    }

    public List<Vehicle> findByVehicleType(String vehicleType){
        return vehicleRepository.findAllByVehicleType(vehicleType);
    }

    public List<VehicleOrder> getVehicleOrdersByVehicleId(Long id){
        return vehicleRepository.findById(id).get().getOrders();
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle.getImageLink() == null || vehicle.getImageLink().equals("")){
            vehicle.setImageLink("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNTfD2wRryIG8_Jlhywgr_mb99HIAakjL8GQ&usqp=CAU");
        }
        return vehicleRepository.save(vehicle);
    }

    public void deleteVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.getById(vehicleId);
        vehicleRepository.delete(vehicle);
    }

    public Boolean rentVehicle(Long id, VehicleOrder vehicleOrder){
        Vehicle vehicle = vehicleRepository.getById(id);
        if (checkIfVehicleIsAvailable(vehicle, vehicleOrder)) {
            if (vehicle != null) {
                VehicleOrder toSave = new VehicleOrder();
                toSave.setDateFinish(vehicleOrder.getDateFinish());
                toSave.setDateStart(vehicleOrder.getDateStart());
                toSave.setUser(userService.getLoggedUser());
                List<VehicleOrder> orders = vehicle.getOrders();
                orders.add(toSave);
                vehicleRepository.save(vehicle);
                return true;
            }
        }
        return false;
    }

    public List<VehicleOrderDto> findAllByUser(){
        User user = userService.getLoggedUser();
        List<VehicleOrderDto> dtos = new ArrayList<>();
        List<Vehicle> vehicles = findAll();
        vehicles.forEach(vehicle -> {
            vehicle.getOrders().forEach(vehicleOrder -> {
                if (vehicleOrder.getUser().equals(user))
                    dtos.add(toDto(vehicle, vehicleOrder, user));
            });
        });
        System.out.println(dtos.size());
        return dtos;
    }

    private VehicleOrderDto toDto(Vehicle vehicle, VehicleOrder order, User user){
        return VehicleOrderDto.builder()
                .dateStart(order.getDateStart())
                .dateFinish(order.getDateFinish())
                .vehicle(vehicle)
                .user(user)
                .build();
    }

    private Boolean checkIfVehicleIsAvailable(Vehicle vehicle, VehicleOrder order){
        if (order.getDateFinish().isAfter(order.getDateStart())) {
            List<VehicleOrder> orders = vehicle.getOrders();
            List<VehicleOrder> newList = orders.stream()
                    .filter(vehicleOrder -> {
                        return compareOrders(vehicleOrder, order);
                    })
                    .collect(Collectors.toList());
            if (newList.size() > 0) return false;
            else return true;
        } else return false;
    }

    private Boolean compareOrders(VehicleOrder existOrder, VehicleOrder newOrder){
        if (newOrder.getDateStart().isBefore(existOrder.getDateStart()) && newOrder.getDateFinish().isBefore(existOrder.getDateStart())) {
            return false;
        } else if (newOrder.getDateStart().isAfter(existOrder.getDateFinish()) && newOrder.getDateFinish().isAfter(existOrder.getDateFinish())) {
            return false;
        } else {
            return true;
        }
    }
}

package com.AITSI;

import com.AITSI.user.User;
import com.AITSI.user.UserRepository;
import com.AITSI.vehicle.Vehicle;
import com.AITSI.vehicle.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class DataBaseInit implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createUser("Bartek", passwordEncoder.encode("bartek123"),  "USER");
        createUser("admin", passwordEncoder.encode("admin123"),  "ADMIN");

        createVehicle("Osobowy","https://i.wpimg.pl/1920x0/m.autokult.pl/ferrari-f8-tributo-2-bd125c228bd,0,0,0,0.jpg",
                "Ferrari", LocalDate.ofYearDay(2020,50), "Czerwony", 2000, 1500000L);
        createVehicle("Osobowy", "https://i.iplsc.com/porsche-911-gt3-rs/0007JM3DVHQA78IY-C122-F4.jpg",
                "Porsche", LocalDate.ofYearDay(2020,100), "Zielony", 1800, 200000L);
        createVehicle("Ciezarowy","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNTfD2wRryIG8_Jlhywgr_mb99HIAakjL8GQ&usqp=CAU",
                "Scania", LocalDate.ofYearDay(2020,50), "Niebieski", 7000, 45000L);
        createVehicle("Motocykl","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTNTfD2wRryIG8_Jlhywgr_mb99HIAakjL8GQ&usqp=CAU",
                "Yamaha", LocalDate.ofYearDay(2020,50), "Czarny", 1000, 20000L);
    }

    private User createUser(String name, String password, String roles){
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);
        return userRepository.save(user);
    };

    private Vehicle createVehicle(String type, String link, String brand, LocalDate productionYear, String colour, Integer engineCap, Long price){
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleType(type);
        vehicle.setImageLink(link);
        vehicle.setBrand(brand);
        vehicle.setProductionYear(productionYear);
        vehicle.setColour(colour);
        vehicle.setEngineCapacity(engineCap);
        vehicle.setPrice(price);
        return vehicleRepository.save(vehicle);
    }
}

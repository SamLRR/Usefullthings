package com.infotrans.osk.usefullthings.controller;

import com.infotrans.osk.usefullthings.domain.User;
import com.infotrans.osk.usefullthings.domain.WorkStation;
import com.infotrans.osk.usefullthings.repo.WorkStationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("workstations")
public class WSController {

    private final WorkStationRepo workStationRepo;

    @Autowired
    public WSController(WorkStationRepo workStationRepo) {
        this.workStationRepo = workStationRepo;
    }

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping
    public String getWorkStations(Model model) {
        List<WorkStation> workstations = workStationRepo.findAll();
        model.addAttribute("workstations", workstations);
        return "workstations";
    }

    @GetMapping("addWS")
    public String showAddWSPage(Model model) {
        WorkStation workStation = new WorkStation();
        model.addAttribute("workStation", workStation);
        return "addWS";
    }


    @PostMapping("addWS")
    public String saveWS(@AuthenticationPrincipal User user,
                         @ModelAttribute("workStation") WorkStation workStation,
                         Model model,
                         @RequestParam("file") MultipartFile file) throws IOException {
        if (workStation.getName() == "") {
            model.addAttribute("message", "Field doesn't be empty");
            return "addWS";
        }
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            workStation.setFilename(resultFilename);
        }
        workStation.setAuthor(user);
        workStationRepo.save(workStation);
        return "redirect:/workstations";
    }

    @GetMapping("edit/{id}")
    public String Edit(Model model, @PathVariable Long id) {
        WorkStation wsFromDB = workStationRepo.getOne(id);
        model.addAttribute("workStation", wsFromDB);
        return "editWS";
    }

    @PostMapping("edit/{id}")
    public String update(@AuthenticationPrincipal User user,
                         @Validated WorkStation workStation) {
        workStation.setAuthor(user);
        workStationRepo.save(workStation);
        return "redirect:/workstations";
    }

    @GetMapping("remove/{id}")
    public String remove(@Validated WorkStation workStation) {
        workStationRepo.delete(workStation);
        return "redirect:/workstations";
    }
}

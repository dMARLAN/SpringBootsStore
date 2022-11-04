package com.marlan.springbootsstore.controllers;

import com.marlan.springbootsstore.dto.BootDTO;
import com.marlan.springbootsstore.entities.Boot;
import com.marlan.springbootsstore.enums.BootType;
import com.marlan.springbootsstore.exceptions.QueryNotSupportedException;
import com.marlan.springbootsstore.services.BootService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {

    private final BootService bootService;

    public BootController(BootService bootService) {
        this.bootService = bootService;
    }

    @GetMapping("/")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Iterable<Boot> getAllBoots() {
        return bootService.getAllBoots();
    }

    @GetMapping("/types")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<BootType> getBootTypes() {
        return bootService.getBootTypes();
    }

    @PostMapping("/")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public Boot addBoot(@Valid @RequestBody BootDTO bootDTO) {
        return bootService.addBoot(bootDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(org.springframework.http.HttpStatus.NO_CONTENT)
    public void deleteBoot(@PathVariable("id") Integer id) {
        bootService.deleteBoot(id);
    }

    @PutMapping("/{id}/quantity/increment")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        return bootService.incrementQuantityByOne(id);
    }

    @PutMapping("/{id}/quantity/decrement")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        return bootService.decrementQuantityByOne(id);
    }

    @GetMapping("/search")
    @ResponseStatus(org.springframework.http.HttpStatus.OK)
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type,
                                  @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity
                                  ) throws QueryNotSupportedException {
        return bootService.searchBoots(material, type, size, minQuantity);
    }
}

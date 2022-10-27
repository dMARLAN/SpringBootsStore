package com.marlan.springbootsstore.controllers;

import com.marlan.springbootsstore.dto.BootDTO;
import com.marlan.springbootsstore.entities.Boot;
import com.marlan.springbootsstore.enums.BootType;
import com.marlan.springbootsstore.exceptions.QueryNotSupportedException;
import com.marlan.springbootsstore.repositories.BootRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/boots")
public class BootController {

    private final BootRepository bootRepository;

    public BootController(final BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    @GetMapping("/")
    public Iterable<Boot> getAllBoots() {
        return this.bootRepository.findAll();
    }

    @GetMapping("/types")
    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    @PostMapping("/")
    public Boot addBoot(@RequestBody BootDTO boot) {
        Boot newBoot = new Boot();
        newBoot.setMaterial(boot.getMaterial());
        newBoot.setType(boot.getType());
        newBoot.setSize(boot.getSize());
        newBoot.setQuantity(boot.getQuantity());
        return this.bootRepository.save(newBoot);
    }

    @DeleteMapping("/{id}")
    public Boot deleteBoot(@PathVariable("id") Integer id) {
        Optional<Boot> optionalBoot = this.bootRepository.findById(id);
        if (optionalBoot.isPresent()) {
            this.bootRepository.delete(optionalBoot.get());
            return optionalBoot.get();
        } else {
            return null;
        }
    }

    @PutMapping("/{id}/quantity/increment")
    public Boot incrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> optionalBoot = this.bootRepository.findById(id);
        if (optionalBoot.isPresent()) {
            Boot boot = optionalBoot.get();
            boot.setQuantity(boot.getQuantity() + 1);
            return this.bootRepository.save(boot);
        } else {
            return null;
        }
    }

    @PutMapping("/{id}/quantity/decrement")
    public Boot decrementQuantity(@PathVariable("id") Integer id) {
        Optional<Boot> optionalBoot = this.bootRepository.findById(id);
        if (optionalBoot.isPresent()) {
            Boot boot = optionalBoot.get();
            boot.setQuantity(boot.getQuantity() - 1);
            return this.bootRepository.save(boot);
        } else {
            return null;
        }
    }

    @GetMapping("/search")
    public List<Boot> searchBoots(@RequestParam(required = false) String material,
                                  @RequestParam(required = false) BootType type, @RequestParam(required = false) Float size,
                                  @RequestParam(required = false, name = "quantity") Integer minQuantity) throws QueryNotSupportedException {
        if (Objects.nonNull(material)) {
            if (Objects.nonNull(type) && Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                return this.bootRepository.findByMaterialAndSizeAndTypeAndQuantityGreaterThanEqual(material, size, type, minQuantity);
            } else if (Objects.nonNull(type) && Objects.nonNull(size)) {
                return this.bootRepository.findByMaterialAndSizeAndType(material, size, type);
            } else if (Objects.nonNull(type) && Objects.nonNull(minQuantity)) {
                return this.bootRepository.findByMaterialAndTypeAndQuantityGreaterThanEqual(material, type, minQuantity);
            } else if (Objects.nonNull(type)) {
                return this.bootRepository.findByMaterialAndType(material, type);
            } else {
                return this.bootRepository.findByMaterial(material);
            }
        } else if (Objects.nonNull(type)) {
            if (Objects.nonNull(size) && Objects.nonNull(minQuantity)) {
                return this.bootRepository.findByTypeAndSizeAndQuantityGreaterThanEqual(type, size, minQuantity);
            } else if (Objects.nonNull(size)) {
                return this.bootRepository.findByTypeAndSize(type, size);
            } else if (Objects.nonNull(minQuantity)) {
                return this.bootRepository.findByTypeAndQuantityGreaterThanEqual(type, minQuantity);
            } else {
                return this.bootRepository.findByType(type);
            }
        } else if (Objects.nonNull(size)) {
            if (Objects.nonNull(minQuantity)) {
                return this.bootRepository.findBySizeAndQuantityGreaterThanEqual(size, minQuantity);
            } else {
                return this.bootRepository.findBySize(size);
            }
        } else if (Objects.nonNull(minQuantity)) {
            return this.bootRepository.findByQuantityGreaterThanEqual(minQuantity);
        } else {
            throw new QueryNotSupportedException("This query is not supported! Try a different combination of search parameters.");
        }
    }
}

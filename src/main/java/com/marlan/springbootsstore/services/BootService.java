package com.marlan.springbootsstore.services;

import com.marlan.springbootsstore.dto.BootDTO;
import com.marlan.springbootsstore.entities.Boot;
import com.marlan.springbootsstore.enums.BootType;
import com.marlan.springbootsstore.exceptions.QueryNotSupportedException;
import com.marlan.springbootsstore.repositories.BootRepository;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class BootService {
    private final BootRepository bootRepository;

    public BootService(BootRepository bootRepository) {
        this.bootRepository = bootRepository;
    }

    public Iterable<Boot> getAllBoots() {
        return bootRepository.findAll();
    }

    public List<BootType> getBootTypes() {
        return Arrays.asList(BootType.values());
    }

    public Boot addBoot(@Valid BootDTO bootDTO) {
        return bootRepository.save(new Boot(bootDTO));
    }

    public void deleteBoot(Integer id) {
        if (bootRepository.existsById(id)) {
            throw new QueryNotSupportedException("Boot with id " + id + " not found!");
        }
        bootRepository.deleteById(id);
    }

    public Boot incrementQuantityByOne(Integer id) {
        return adjustQuantity(id, 1);
    }

    public Boot decrementQuantityByOne(Integer id) {
        return adjustQuantity(id, -1);
    }

    private Boot adjustQuantity(Integer id, Integer adjustment){
        if (bootRepository.existsById(id)) {
            throw new QueryNotSupportedException("Boot with id " + id + " not found!");
        }
        Boot boot = bootRepository.findById(id).orElseThrow(() -> new QueryNotSupportedException("Boot with id " + id + " not found!"));
        boot.setQuantity(boot.getQuantity() + adjustment);
        return bootRepository.save(boot);
    }

    public List<Boot> searchBoots(String material, BootType type, Float size, Integer minQuantity) {
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

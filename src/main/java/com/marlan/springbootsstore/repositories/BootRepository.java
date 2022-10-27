package com.marlan.springbootsstore.repositories;

import com.marlan.springbootsstore.entities.Boot;
import com.marlan.springbootsstore.enums.BootType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BootRepository extends CrudRepository<Boot, Integer> {
    List<Boot> findBySize(Float size);
    List<Boot> findByMaterial(String material);
    List<Boot> findByType(BootType type);
    List<Boot> findByQuantityGreaterThanEqual(Integer quantity);
    List<Boot> findBySizeAndQuantityGreaterThanEqual(Float size, Integer minQuantity);
    List<Boot> findByTypeAndQuantityGreaterThanEqual(BootType type, Integer minQuantity);
    List<Boot> findByTypeAndSize(BootType type, Float size);
    List<Boot> findByTypeAndSizeAndQuantityGreaterThanEqual(BootType type, Float size, Integer minQuantity);
    List<Boot> findByMaterialAndType(String material, BootType type);
    List<Boot> findByMaterialAndTypeAndQuantityGreaterThanEqual(String material, BootType type, Integer minQuantity);
    List<Boot> findByMaterialAndSizeAndType(String material, Float size, BootType type);
    List<Boot> findByMaterialAndSizeAndTypeAndQuantityGreaterThanEqual(String material, Float size, BootType type, Integer minQuantity);
}
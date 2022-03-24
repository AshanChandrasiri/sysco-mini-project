package com.sysco.miniproject.service.impl;

import com.sysco.miniproject.data.dao.Manufacturer;
import com.sysco.miniproject.data.dto.request.CreateManufacturerDto;
import com.sysco.miniproject.respository.ManufacturerRepository;
import com.sysco.miniproject.service.ManufacturerService;
import com.sysco.miniproject.shared.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("manufacturerServiceImpl")
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository manufacturerRepository;

    @Override
    @Transactional
    public void create(CreateManufacturerDto dto) {
        Manufacturer manufacturer = new Manufacturer();
        if (dto.getId() != null) {
            manufacturer = getManufactureById(dto.getId());
        }
        manufacturer.setAddress(dto.getAddress());
        manufacturer.setImage(dto.getImage());
        manufacturer.setName(dto.getName());
        manufacturerRepository.save(manufacturer);

    }

    @Override
    public Manufacturer getManufactureById(Long id) {
        return manufacturerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Manufacturer not found for id %d", id));
    }


}

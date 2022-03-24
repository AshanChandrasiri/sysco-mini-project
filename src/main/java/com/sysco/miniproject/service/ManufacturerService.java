package com.sysco.miniproject.service;

import com.sysco.miniproject.data.dao.Manufacturer;
import com.sysco.miniproject.data.dto.request.CreateManufacturerDto;

public interface ManufacturerService {

    void create(CreateManufacturerDto dto);

    Manufacturer getManufactureById(Long id);

}

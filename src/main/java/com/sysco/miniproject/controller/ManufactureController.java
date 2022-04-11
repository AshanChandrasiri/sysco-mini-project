package com.sysco.miniproject.controller;

import com.sysco.miniproject.data.dao.Manufacturer;
import com.sysco.miniproject.data.dto.request.CreateManufacturerDto;
import com.sysco.miniproject.service.ManufacturerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/manufacture")
@RequiredArgsConstructor
@Slf4j
public class ManufactureController {

    private final ManufacturerService manufacturerService;

    @PostMapping("/create")
    public ResponseEntity<Manufacturer> createManufacturer(@Valid @RequestBody CreateManufacturerDto req) {
        log.info("request to create manufacturer, {}", req);
        manufacturerService.create(req);
        return ResponseEntity.ok().build();
    }

}

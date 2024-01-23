package com.shivdairy.company.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shivdairy.company.dto.SupplierDTO;
import com.shivdairy.company.repository.SupplierRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
class SupplierServiceImplTest {

    @InjectMocks
    private SupplierServiceImpl supplierService;

    @Mock
    private EntityManager entityManager;

    @Mock
    private SupplierRepository supplierRepository;

    private SupplierDTO supplierDTO;
    private final ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() throws IOException {
        supplierDTO = mapper.readerFor(SupplierDTO.class)
                .readValue(new File("src/test/resources/supplierDTO.json"));
    }

    @Test
    void save(){
        when(supplierRepository.save(any())).thenReturn(supplierDTO.toEntity());
        SupplierDTO result = supplierService.save(supplierDTO);
        log.info("result {}", result);
        assertNotNull(result);
        assertEquals(supplierDTO.getName(), result.getName());
    }

    @Test
    void findById() {
        when(supplierRepository.findById(any())).thenReturn(supplierDTO.toEntity());
        SupplierDTO result = supplierService.findById(supplierDTO.getId());
        log.info("result {}", result);
        assertNotNull(result);
    }

    @Test
    void search() {
        when(supplierRepository.search()).thenReturn(Collections.singletonList(supplierDTO.toEntity()));
        List<SupplierDTO> result = supplierService.search();
        log.info("result {}", result);
        assertNotNull(result);
    }
}

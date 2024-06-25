package br.com.sizer.controller;

import br.com.sizer.utill.CompanySpecificationsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import br.com.sizer.model.Company;
import br.com.sizer.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    // -------------------create company -------------------
    @Operation(summary = "Criar Empresa")
    @ApiResponse(responseCode = "200", description = "Retorna a empresa criada", content = @Content)
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para criar uma empresa")
    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")

    @PostMapping("/company")
    ResponseEntity<?> create(@RequestBody Company request) {
        Company response = companyService.create(request);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // -------read the company in json format with pagination----------
    @Operation(summary = "List Companies")
    @ApiResponse(responseCode = "200", description = "Listar Empresas")
    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso")
    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")

    @RequestMapping(value = "/companies", method = RequestMethod.GET, produces = { "application/json" })
    ResponseEntity<?> getAllCompany(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "limit", defaultValue = "5") int limit) {
        // Create a Pageable instance
        Pageable pageable = PageRequest.of(page, limit);
        Page<Company> response = companyService.findAll(pageable);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // --------------------update the company --------------------
    @RequestMapping(value = "/company/{id}", method = RequestMethod.PUT, consumes = { "application/json" }, produces = {
            "application/json" })
    public ResponseEntity<?> updateCompany(@PathVariable(value = "id") Long companyId,
            @RequestBody Company companyDetails) {
        Company response = companyService.update(companyId, companyDetails);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    // ---------------------delete the company ---------------------
    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(value = "id") Long companyId) {
        companyService.delete(companyId);
        return ResponseEntity.ok().build();
    }

    // ------------------------search company ------------------------
    @RequestMapping(method = RequestMethod.GET, value = "/company")
    public ResponseEntity<?> search(@RequestParam(value = "search") String search) {
        CompanySpecificationsBuilder builder = new CompanySpecificationsBuilder();
        Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?),");
        Matcher matcher = pattern.matcher(search + ",");
        while (matcher.find()) {
            builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }
        Specification<Company> spec = builder.build();
        List<Company> response = companyService.findAll(spec);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }
}

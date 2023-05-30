package com.Travel.Travel.api.controllers;

import com.Travel.Travel.infraestructure.abstract_services.ModifyUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/user")
@Tag(name = "User")
public class AppUserController {
    private final ModifyUserService modifyUserService;

    public AppUserController(ModifyUserService modifyUserService) {
        this.modifyUserService = modifyUserService;
    }

    @Operation(summary = "Enabled or disabled user")
    @PatchMapping(path = "enabled_or_disabled")
    public ResponseEntity<Map<String, Boolean>> enabledOrDisabled(@RequestParam String username) {
        return ResponseEntity.ok(modifyUserService.enabled(username));
    }

    @Operation(summary = "Add role user")
    @PatchMapping(path = "add_role")
        public ResponseEntity<Map<String, Set<String>>> addRole(@RequestParam String username, @RequestParam String role) {
        return ResponseEntity.ok(this.modifyUserService.addRole(username, role));
    }

    @Operation(summary = "Add role user")
    @PatchMapping(path = "remove_role")
    public ResponseEntity<Map<String, Set<String>>> removeRole(@RequestParam String username, @RequestParam String role) {
        return ResponseEntity.ok(this.modifyUserService.removeRole(username, role));
    }
}
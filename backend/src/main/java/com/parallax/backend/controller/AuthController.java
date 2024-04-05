    package com.parallax.backend.controller;

    import com.parallax.backend.dto.Register;
    import com.parallax.backend.service.AuthService;
    import com.parallax.backend.service.UserService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.validation.Valid;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;

    @Slf4j
    @RestController
    @RequestMapping("/api/v1/users")
    @CrossOrigin(origins = "http://localhost:4200")
    @Tag(name = "Authentication Controller")
    public class AuthController {

        final AuthService authService;

        final
        UserService service;

        public AuthController(UserService service,AuthService authService) {
            this.service = service;
            this.authService = authService;
        }

        @Operation(
                description = "Endpoint for user register",
                responses = {
                        @ApiResponse(
                                description = "Logged successfully",
                                responseCode = "200"
                        ),
                        @ApiResponse(
                                description = "Wrong credentials",
                                responseCode = "400"
                        )
                }
        )
        @PostMapping("/register")
        public ResponseEntity<Object> register(@Valid @RequestBody Register register){
            return authService.registerNewUserAccount(register);
        }

    }

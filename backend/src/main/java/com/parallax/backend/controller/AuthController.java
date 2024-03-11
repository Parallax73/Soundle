    package com.parallax.backend.controller;

    import com.parallax.backend.dto.LoginUser;
    import com.parallax.backend.dto.NewUser;
    import com.parallax.backend.entity.Message;
    import com.parallax.backend.service.AuthService;
    import com.parallax.backend.service.UserService;
    import io.swagger.v3.oas.annotations.Operation;
    import io.swagger.v3.oas.annotations.responses.ApiResponse;
    import io.swagger.v3.oas.annotations.tags.Tag;
    import jakarta.servlet.http.HttpServletResponse;
    import jakarta.validation.Valid;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.data.repository.query.Param;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;

    import javax.management.relation.RoleNotFoundException;

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
                description = "Endpoint for user login",
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
        @PostMapping("/login")
        public ResponseEntity<Object> login(HttpServletResponse httpServletResponse,
                                            @Valid @RequestBody LoginUser loginUser, BindingResult bidBindingResult){
            return authService.login(httpServletResponse,loginUser,bidBindingResult);
        }

        @Operation(
                description = "Endpoint for user creation",
                responses = {
                        @ApiResponse(
                                description = "User created successfully",
                                responseCode = "201"
                        ),
                        @ApiResponse(
                                description = "Wrong credentials while creating a new user",
                                responseCode = "400"
                        )
                }
        )
        @PostMapping("/register")
        public ResponseEntity<Object> register(@Valid @RequestBody NewUser newUser, BindingResult bindingResult) {
            try {
                return authService.registerUser(newUser, bindingResult);
            } catch (RoleNotFoundException e) {
                return new ResponseEntity<>(new Message("Role not found"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @Operation(
                description = "Endpoint for getting the details from cookie",
                responses = {
                        @ApiResponse(
                                description = "User created successfully",
                                responseCode = "200"
                        ),
                        @ApiResponse(
                                description = "User not found",
                                responseCode = "404"
                        )
                }
        )
        @GetMapping("/details")
        public ResponseEntity<Object> getUserDetails(){
            return authService.getUserDetails();
        }

        @Operation(
                description = "Endpoint for user logoff",
                responses = {
                        @ApiResponse(
                                description = "Logged out successfully",
                                responseCode = "200"
                        )
                }
        )
        @GetMapping("/logout")
        public ResponseEntity<Message> logOut(HttpServletResponse httpServletResponse){
            return authService.logoutUser(httpServletResponse);
        }

        @Operation(
                description = "Endpoint for adding score to a designed user",
                responses = {
                        @ApiResponse(
                                description = "Changed out successfully",
                                responseCode = "200"
                        ),
                        @ApiResponse(
                                description = "Isn't authenticated",
                                responseCode = "403"
                        )
                }
        )
        @PatchMapping("/addScore")
        public ResponseEntity<?> addScore(@Param("score") int score){
            return authService.addScore(score);
        }

        @Operation(
                description = "Endpoint for changing the streak of a designed user",
                responses = {
                        @ApiResponse(
                                description = "Changed out successfully",
                                responseCode = "200"
                        ),
                        @ApiResponse(
                                description = "Isn't authenticated",
                                responseCode = "403"
                        )
                }
        )
        @PatchMapping("/addStreak")
        public ResponseEntity<?> addStreak(@Param("streak") int streak){
            return authService.addStreak(streak);
        }
    }

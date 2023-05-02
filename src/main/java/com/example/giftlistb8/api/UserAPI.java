package com.example.giftlistb8.api;

import com.example.giftlistb8.dto.PaginationResponse;
import com.example.giftlistb8.dto.SimpleResponse;
import com.example.giftlistb8.dto.feed.response.FeedResponse;
import com.example.giftlistb8.dto.notification.response.NotificationResponse;
import com.example.giftlistb8.dto.user.requests.ResetPasswordRequest;
import com.example.giftlistb8.dto.user.response.UserResponseGetAll;
import com.example.giftlistb8.dto.user.response.UserResponseGetById;
import com.example.giftlistb8.entities.User;
import com.example.giftlistb8.services.NotificationService;
import com.example.giftlistb8.services.UserInfoService;
import com.example.giftlistb8.services.UserService;
import com.google.firebase.remoteconfig.internal.TemplateResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAPI {
    private final UserInfoService userService;
    private final UserService service;
    private final NotificationService notificationService;

    @PostMapping("/forgot_password")
    public SimpleResponse processForgotPassword(@RequestParam String email) {
        return userService.updateResetPasswordToken(email);
    }

    @PostMapping("/reset_password")
    public SimpleResponse processResetPassword(@RequestParam String token, @RequestBody @Valid ResetPasswordRequest request) {
        return userService.getByResetPasswordToken(token, request.getPassword(), request.getConfirmPassword());
    }

    @GetMapping("/notifications")
    public List<NotificationResponse> getAllNotifications() {
        return notificationService.getAllMyNotifications();
    }

    @PostMapping()
    public SimpleResponse seenOrNot() {
        return notificationService.seenOrNot();
    }

    @Operation(summary = "Get all users", description = "Returns a paginated list of all users.")
    @GetMapping
    public PaginationResponse<UserResponseGetAll> getAll(@RequestParam(defaultValue = "1") int page,
                                                         @RequestParam(defaultValue = "6") int size) {
        return service.getAllUsers(page, size);
    }

    @Operation(summary = "Get user by id", description = "Returns a single user  and user's holiday,wishes,charities.")
    @GetMapping("/{userId}")
    private UserResponseGetById getByIdWish(@PathVariable Long userId) {
        return service.getById(userId);
    }

    @Operation(summary = "Delete user by id", description = "Delete user with all information by id")
    @DeleteMapping("/{userId}")
    private SimpleResponse deleteById(@PathVariable Long userId) {
        return service.deleteById(userId);
    }

    @Operation(summary = "Block user", description = "User block or un block method")
    @PutMapping("/{userId}/block")
    public SimpleResponse blockUser(@PathVariable Long userId, @RequestBody boolean blocked) {
        return service.updateBlockedStatus(userId, blocked);
    }
}

package is.job.airlines.web;


import is.job.airlines.model.binding.UserRegisterBindingModel;
import is.job.airlines.model.service.UserRegisterServiceModel;
import is.job.airlines.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Period;

@Controller
@RequestMapping("/users")
public class UsersController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login-error")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                                      String username,
                              RedirectAttributes attributes) {

        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("username", username);
        return "redirect:/users/login";

    }

    @GetMapping("/register")
    public String register(Model model) {
        if (!model.containsAttribute("userRegisterBindingModel")) {
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }
        return "register";

    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("userRegisterBindingModel") UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors() ||
                (Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18)) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            if (userRegisterBindingModel.getDateOfBirth() != null &&
                    Period.between(userRegisterBindingModel.getDateOfBirth(), LocalDate.now()).getYears() < 18) {
                bindingResult.rejectValue("dateOfBirth", "error.userRegisterBindingModel", "You must be at least 18 years old to register");
            }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "register";
        }

        if (this.userService.emailExists(userRegisterBindingModel.getEmail())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            bindingResult.rejectValue("email", "userExistsError", "This email is already used by another user");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel",
                    bindingResult);
            return "register";
        }

        UserRegisterServiceModel userRegisterServiceModel = this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class);
        this.userService.registerAndLoginUser(userRegisterServiceModel);
        return "redirect:/home";

    }
}
